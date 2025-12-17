package tilemap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import engine.graphics.Tile;

// public class TileMap {
//     private Tile[] tiles;
//     private int width, height;
//     private int tileSize;

//     public TileMap(int mapTileNumber) {
//         tiles = new Tile[mapTileNumber];
//         // mapTileNumber = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];
//         // getTileImage();
//         // loadMap("assets/maps/world02.txt");
//     }

//     public void loadMap(String pathName) {
//         try {
//             File file = new File(pathName);
//             BufferedReader br = new BufferedReader(new FileReader(file));
//             int col = 0;
//             int row = 0;

//             String line;
//             while ((line = br.readLine()) != null) {
//                 String[] numbers = line.split(" ");

//                 for (col = 0; col < gamePanel.maxWorldCol && col < numbers.length; col++) {
//                     int num = Integer.parseInt(numbers[col]);
//                     mapTileNumber[col][row] = num;
//                 }

//                 row++;
//             }

//             br.close();
//             System.out.println("Map loaded successfully: " + pathName);

//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }

// package tilemap;

// import java.awt.Graphics2D;

// public class TileMap {
//     private Tile[] tiles;
//     private int width, height;
//     private int tileSize;

//     public TileMap(int width, int height, int tileSize) {
//         this.width = width;
//         this.height = height;
//         this.tileSize = tileSize;
//         tiles = new Tile[tileSize];
//     }

//     public void setTile(int x, int y, Tile tile) {
//         if (x >= 0 && x < width && y >= 0 && y < height) {
//             tiles[y][x] = tile;
//         }
//     }

//     public Tile getTile(int x, int y) {
//         if (x < 0 || x >= width || y < 0 || y >= height)
//             return null;
//         return tiles[y][x];
//     }

//     public int getTileSize() {
//         return tileSize;
//     }

//     public void draw(Graphics2D g2, int cameraX, int cameraY, int screenWidth,
//             int screenHeight) {
//         int startCol = cameraX / tileSize;
//         int startRow = cameraY / tileSize;
//         int endCol = (cameraX + screenWidth) / tileSize + 1;
//         int endRow = (cameraY + screenHeight) / tileSize + 1;

//         for (int y = startRow; y < endRow; y++) {
//             for (int x = startCol; x < endCol; x++) {
//                 Tile tile = getTile(x, y);
//                 if (tile != null) {
//                     int screenX = x * tileSize - cameraX;
//                     int screenY = y * tileSize - cameraY;
//                     tile.draw(g2, screenX, screenY, tileSize);
//                 }
//             }
//         }
//     }
// }
