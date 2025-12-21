package game.entities;

import java.util.*;
import engine.entities.Entity;
import engine.graphics.*;
import engine.levels.tile.Tile;
import engine.utilities.Node;
import engine.utilities.Vector2i;

public class Enemy extends Entity {

    private int xAxis, yAxis;
    private boolean walking = true;
    private Animation anim_down = new Animation(SpriteSheet.enemyAnimDown, 16, 16, 2);
    private Animation anim_up = new Animation(SpriteSheet.enemyAnimUp, 16, 16, 2);
    private Animation anim_left = new Animation(SpriteSheet.enemyAnimLeft, 16, 16, 2);
    private Animation anim_right = new Animation(SpriteSheet.enemyAnimRight, 16, 16, 2);
    private Animation anim = anim_down;

    private Entity target;
    private List<Node> path = null;
    private int nextPos; // Index of the node we are currently walking toward
    private double speed = 2.5;
    private int time = 0;

    // private List<Vector2i> debugPath = new ArrayList<>();

    public Enemy(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void target(Entity target) {

        this.target = target;

    }

    public void update() {
        time++;
        xAxis = 0;
        yAxis = 0;

        pathFindingMove();
        animation();

        // Debug visualization
        // if (path != null) {
        //     debugPath.clear();
        //     for (Node n : path) {
        //         debugPath.add(new Vector2i((n.tile.getX() << 4) + 8, (n.tile.getY() << 4) + 8));
        //     }
        // }
    }

    int X;
    int Y;

    private void pathFindingMove() {

        X = getPviot().getX();
        Y = getPviot().getY();

        Vector2i startTile = new Vector2i(X >> 4, Y >> 4);
        double px = target.getPviot().getX();
        double py = target.getPviot().getY();
        Vector2i destinationTile = new Vector2i((int) px >> 4, (int) py >> 4);

        // --- (1. Pathfinding Initiation) ---
        if (time % 15 == 0 || true) {
            if (!startTile.equals(destinationTile)) {
                path = findPath(startTile, destinationTile);
                if (path != null && !path.isEmpty()) {
                    nextPos = path.size() - 1;
                } else {
                    path = null;
                }
            } else {
                path = null;
            }
        }

        // --- (2. Path Following Logic) ---
        if (path != null) { 

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
                // --- Direction (axis) ---
                if (Math.abs(dx) > Math.abs(dy)) {
                    xAxis = dx > 0 ? 1 : -1;
                } else {
                    yAxis = dy > 0 ? 1 : -1;
                }
                // --- Movement ---
                double stepX = nx * speed;
                double stepY = ny * speed;
                if (!collision(stepX, 0)) {
                    x += stepX;
                }
                if (!collision(0, stepY)) {
                    y += stepY;
                }
            }
        } else if (path != null) {
            path = null;
        }

        if (xAxis > 0) {
            dir = Direction.RIGHT;
            walking = true;

        } else if (xAxis < 0) {
            dir = Direction.LEFT;
            walking = true;

        } else if (yAxis > 0) {
            dir = Direction.DOWN;
            walking = true;

        } else if (yAxis < 0) {
            dir = Direction.UP;
            walking = true;
        } else {
            walking = false;
        }
    }

    // A* Algorithm Pathfinding
    public List<Node> findPath(Vector2i start, Vector2i end) {

        PriorityQueue<Node> openList = new PriorityQueue<>(nodeSorter);
        Map<Vector2i, Double> gCosts = new HashMap<>();

        Node startNode = new Node(start, null, 0, getDistance(start, end));
        openList.add(startNode);
        gCosts.put(start, 0.0);

        int iterations = 0;
        while (!openList.isEmpty()) {
            iterations++;
            if (iterations > 10000)
                break;

            Node current = openList.poll();

            if (current.tile.equals(end)) {
                List<Node> result = new ArrayList<>();
                while (current.parent != null) {
                    result.add(current);
                    current = current.parent;
                }
                return result;
            }

            for (int i = 0; i < 9; i++) {
                if (i == 4)
                    continue;
                int xi = (i % 3) - 1;
                int yi = (i / 3) - 1;

                if (xi != 0 && yi != 0) {
                    if (level.getTile(current.tile.getX() + xi, current.tile.getY()).solid() ||
                            level.getTile(current.tile.getX(), current.tile.getY() + yi).solid())
                        continue;
                }

                Vector2i neighbor = new Vector2i(current.tile.getX() + xi, current.tile.getY() + yi);
                Tile tile = level.getTile(neighbor.getX(), neighbor.getY());
                if (tile == null || tile.solid())
                    continue;

                double stepCost = (xi != 0 && yi != 0) ? 1.414 : 1.0;
                double totalGCost = current.gCost + stepCost;

                if (!gCosts.containsKey(neighbor) || totalGCost < gCosts.get(neighbor)) {
                    gCosts.put(neighbor, totalGCost);
                    Node node = new Node(neighbor, current, totalGCost, getDistance(neighbor, end));
                    openList.add(node);
                }
            }
        }
        return null;
    }

    private Comparator<Node> nodeSorter = (n0, n1) -> Double.compare(n0.fCost, n1.fCost);

    private double getDistance(Vector2i tile, Vector2i goal) {
        double dx = tile.getX() - goal.getX();
        double dy = tile.getY() - goal.getY();
        return Math.sqrt((dx * dx) + (dy * dy));
    }

    void animation() {

        if (walking) {
            anim.update();
        } else {
            anim.resetAnimation();
        }

        if (dir == Direction.DOWN) {
            anim = anim_down;
        } else if (dir == Direction.UP) {
            anim = anim_up;
        } else if (dir == Direction.LEFT) {
            anim = anim_left;
        } else if (dir == Direction.RIGHT) {
            anim = anim_right;
        }
    }

    @Override
    public void render(Renderer renderer) {
        sprite = anim.getSprite();
        // renderer.camera.cameraTarget(x, y, sprite.getWidth(), sprite.getHeight());
        int drawX = (int) Math.round(x - renderer.camera.getxOffset());
        int drawY = (int) Math.round(y - renderer.camera.getyOffset());
        renderer.renderSprite(drawX, drawY, sprite, false);

        // print the path for debugging
        // for (int i = 0; i < debugPath.size(); i++) {
        //     renderer.renderPixel(debugPath.get(i).getX(), debugPath.get(i).getY(), 0xfffff000);
        // }
    }
}
