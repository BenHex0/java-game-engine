package game.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import engine.entities.Entity;
import engine.graphics.Renderer;
import engine.graphics.Sprite;
import engine.levels.tiles.Tile;
import engine.utilities.Node;
import engine.utilities.Vector2i;


public class Enemy extends Entity {
    private int xAxis, yAxis;
    private int animate = 0;
    private boolean walking = true;
    private Entity target;
    private List<Node> path = null;
    private int nextPos;
    private double speed = 2.5;
    private int time = 0;

    private List<Vector2i> debugPath = new ArrayList<Vector2i>();

    public Enemy(double x, double y) {
        this.x = x;
        this.y = y;
        sprite = Sprite.player;
    }

    public void update() {
        xAxis = 0;
        yAxis = 0;
        pathFindingMove();

        if (path != null) {
            debugPath.clear();
            for (int i = 0; i < path.size(); i++) {
                int x = (path.get(i).tile.getX() * 16) + 8;
                int y = (path.get(i).tile.getY() * 16) + 8;
                debugPath.add(new Vector2i(x, y));
            }
        }
    }


    public void target(Entity target) {
        this.target = target;
    }

    int X;
    int Y;

    private void pathFindingMove() {
        X = getPviot().getX();
        Y = getPviot().getY();
        xAxis = 0;
        yAxis = 0;

        // Vector2i startTile = new Vector2i(getPivot().getX() >> 4, getPivot().getY()
        // >> 4);
        Vector2i startTile = new Vector2i(X >> 4, Y >> 4);
        double px = target.getPviot().getX();
        double py = target.getPviot().getY();
        Vector2i destinationTile = new Vector2i((int) px >> 4, (int) py >> 4);
        // --- (1. Pathfinding Initiation) ---
        // Assuming startTile, destinationTile, and goal are handled correctly outside
        // of this block
        if (time % 15 == 0) {
            // Use the commented out tile coordinate calculation
            if (!startTile.equals(destinationTile)) {
                path = findPath(startTile, destinationTile);

                if (path != null && !path.isEmpty()) {
                    // FIX 1: Initialize to the *first* node in the path (index 0)
                    nextPos = path.size() - 1;
                } else {
                    path = null;
                }
            } else {
                path = null;
            }
        }

        // --- (2. Path Following Logic) ---
        if (path != null) { // Check if path exists and we haven't reached the end
            Vector2i targetTile = path.get(nextPos).tile;
            // Calculate the target pixel coordinates (tile * 16)
            int targetX = (targetTile.getX() * 16) + 8;
            int targetY = (targetTile.getY() * 16) + 8;

            double dx = targetX - getPviot().getX();
            double dy = targetY - getPviot().getY();
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance > 0) {
                double nx = dx / distance;
                double ny = dy / distance;

                x += nx * speed;
                y += ny * speed;
            }

            if (distance <= speed) {
                x += dx;
                y += dy;
                nextPos--;
            }

            if (nextPos < 0) {
                path = null;
                walking = false;
                return;
            }
        } else if (path != null) {
            // Path exists but nextPos is out of bounds (path finished)
            path = null;
        }
    }

    void move(int xAxis, int yAxis, double speed) {

        if (xAxis > 0)
            dir = Direction.RIGHT;
        else if (xAxis < 0)
            dir = Direction.LEFT;
        else if (yAxis > 0)
            dir = Direction.DOWN;
        else if (yAxis < 0)
            dir = Direction.UP;

        // Move X
        if (xAxis != 0) {
            x += Math.signum(xAxis) * speed;
        }

        // Move Y
        if (yAxis != 0) {
            y += (Math.signum(yAxis) * speed);
        }
    }

    // A* Algorithm pathfinding
    public List<Node> findPath(Vector2i start, Vector2i end) {
        List<Node> openList = new ArrayList<Node>();
        List<Node> closeList = new ArrayList<Node>();

        // Initial node (parent is null, gCost is 0)
        Node current = new Node(start, null, 0, getDistance(start, end));
        openList.add(current);

        int iteration = 0;
        while (openList.size() > 0) {
            iteration++;

            // Safety break (kept from original code)
            if (iteration >= 10000) {
                System.out.println("iteration: " + iteration);
                break;
            }

            // 1. Sort the open list to get the lowest F-cost node (INEFFICIENT - O(N log
            // N))
            Collections.sort(openList, nodeSorter);
            current = openList.get(0);

            // 2. Goal Check
            if (current.tile.equals(end)) {
                List<Node> path = new ArrayList<Node>();
                while (current.parent != null) {
                    // Add nodes in reverse order (from end to start)
                    path.add(current);
                    current = current.parent;
                }

                // Note: Clearing lists is unnecessary for local variables, but kept for parity.
                openList.clear();
                closeList.clear();
                return path;
            }

            // 3. Move current node to closed list
            openList.remove(0); // Remove the lowest F-cost node
            closeList.add(current);

            // 4. Check neighbors
            for (int i = 0; i < 9; i++) {
                // if (i == 0 || i == 2 || i == 4 || i == 6 || i == 8)
                if (i == 4)
                    continue; // Skip the center tile (current node itself)

                // **CRITICAL BUG FIX HERE:**
                int currentX = current.tile.getX();
                int currentY = current.tile.getY(); // CORRECT: Fetch Y-coordinate

                int xi = (i % 3) - 1; // Neighbor X offset
                int yi = (i / 3) - 1; // Neighbor Y offset

                Vector2i neighborCoords = new Vector2i(currentX + xi, currentY + yi);
                
                Tile at = level.getTile(neighborCoords.getX(), neighborCoords.getY());

                if (at == null || at.solid() || at == Tile.voidTile)
                    continue; // Cannot move here

                // Calculate costs for the path *through* the current node to the neighbor
                double tentativeGCost = current.gCost + getDistance(current.tile, neighborCoords);
                double hCost = getDistance(neighborCoords, end);

                // Check if this neighbor is already processed (closed list)
                Node existingClosedNode = getVecInlist(closeList, neighborCoords);
                if (existingClosedNode != null && tentativeGCost >= existingClosedNode.gCost) {
                    // The new path is worse than the one already processed/closed
                    continue;
                }

                // Check if this neighbor is already being considered (open list)
                Node existingOpenNode = getVecInlist(openList, neighborCoords);

                if (existingOpenNode == null) {
                    // Not in the open list: Found a new node, add it.
                    Node newNode = new Node(neighborCoords, current, tentativeGCost, hCost);
                    openList.add(newNode);
                } else if (tentativeGCost < existingOpenNode.gCost) {
                    // Already in the open list, but the new path is CHEAPER.
                    // Update the existing node's parent and costs (path rewiring).
                    existingOpenNode.gCost = tentativeGCost;
                    existingOpenNode.hCost = hCost; // H-cost won't change, but F-cost will.
                    existingOpenNode.parent = current;

                    // Since the cost has changed, re-sorting in the next iteration will handle the
                    // new priority.
                }
            }
        }

        // Path not found
        closeList.clear();
        return null;
    }

    private Comparator<Node> nodeSorter = new Comparator<Node>() {
        public int compare(Node n0, Node n1) {
            if (n1.fCost < n0.fCost)
                return +1;
            if (n1.fCost > n0.fCost)
                return -1;
            return 0;
        }
    };

    private double getDistance(Vector2i tile, Vector2i goal) {
        double dx = tile.getX() - goal.getX();
        double dy = tile.getY() - goal.getY();
        return Math.sqrt((dx * dx) + (dy * dy));
    }

    private Node getVecInlist(List<Node> list, Vector2i vector) {
        for (Node node : list) {
            if (node.tile.equals(vector)) {
                return node;
            }
        }
        return null;
    }

    @Override
    public void render(Renderer renderer) {
        int drawX = (int) Math.round(x - renderer.camera.getxOffset());
        int drawY = (int) Math.round(y - renderer.camera.getyOffset());
        renderer.renderSprite(drawX, drawY, sprite, false);

        for (int i = 0; i < debugPath.size(); i++) {
            renderer.renderPixel(debugPath.get(i).getX(), debugPath.get(i).getY(), 0xfffff000);
        }
    }
}
