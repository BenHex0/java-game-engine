package game.levels;

import engine.database.Database;
import engine.entities.Entity;
import engine.input.InputHandler;
import engine.levels.Level;
import engine.sound.Sound;
import engine.utilities.TileCoordinate;
import engine.Engine;
import game.entities.*;

public class Level1 extends Level {

    private int timer = 0;

    Database database;
    Sound sound;
    Player player;
    Enemy enemy;
    TileCoordinate end;

    public Level1(int width, int height, InputHandler input) {
        super(width, height, input);
        start();
    }

    public Level1(String path, InputHandler input) {
        super(path, input);
        start();
    }

    void start() {
        deleteAllEntities();
        sound = new Sound();
        TileCoordinate playerPosition = new TileCoordinate(10, 10);
        player = new Player(playerPosition.x(), playerPosition.y(), input);
        TileCoordinate enemyPosition = new TileCoordinate(5, 5);
        enemy = new Enemy(enemyPosition.x(), enemyPosition.y());
        database = new Database();
        add(player);
        add(enemy);
        enemy.target(player);
        end = new TileCoordinate(70, 68);
        sound.setFile(0);
        sound.play();

    }

    @Override
    public void currentLevelUpdate() {

        timer++;
        kill(player, enemy);

        if (input.isKeyPressed(InputHandler.Key.JUMP)) {
            System.out.println("Save Score");
            database.saveScore("Hello", 69);
            database.close();
        }

        // System.out.println("player: " + player.getPviot().getX() / 16 + " " +
        // player.getPviot().getY() / 16);
        // System.out.println("end: " + end.getXInTile() + " " + end.getYInTile());

        if (player.getPviot().getX() / 16 == end.getXInTile() && player.getPviot().getY() / 16 == end.getYInTile()) {
            stop = true;
            System.out.println("win!");
            Engine.gameState = Engine.endGame;
            sound.stop();
            Engine.setCurrentLevel(2);
        }

    }

    @Override
    public void restartLevel() {
        start();
    }

    boolean isColliding(Entity a, Entity b) {
        return a.getX() < b.getX() + b.getSprite().getWidth()
                && a.getX() + a.getSprite().getWidth() > b.getX()
                && a.getY() < b.getY() + b.getSprite().getHeight()
                && a.getY() + a.getSprite().getHeight() > b.getY();
    }

    void kill(Entity e1, Entity e2) {
        if (isColliding(e1, e2)) {
            System.out.println("Dead!");
            player.die = true;
            sound.stop();
            if (timer % 120 == 0) {
                Engine.setCurrentUI(2);
                Engine.gameState = Engine.gamePause;
            }
        }
    }

}
