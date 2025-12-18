package game.levels;

import engine.entities.Entity;
import engine.input.InputHandler;
import engine.levels.Level;
import game.entities.*;
import engine.sound.Sound;
import engine.utilities.TileCoordinate;
import engine.database.Database;

public class SpwanLevel extends Level {

    private int timer = 0;

    Database database;
    Sound sound = new Sound();
    Player player;
    Enemy enemy;

    public SpwanLevel(int width, int height, InputHandler input) {
        super(width, height, input);
        start();
    }

    public SpwanLevel(String path, InputHandler input) {
        super(path, input);
        start();
    }

    void start() {
        TileCoordinate playerPosition = new TileCoordinate(10, 10);
        player = new Player(playerPosition.x(), playerPosition.y(), input);
        TileCoordinate enemyPosition = new TileCoordinate(5, 5);
        enemy = new Enemy(enemyPosition.x(), enemyPosition.y());
        database = new Database();
        add(player);
        add(enemy);
        enemy.target(player);

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
    }

    boolean isColliding(Entity a, Entity b) {
        return a.getX() < b.getX() + b.getSprite().getWidth() &&
                a.getX() + a.getSprite().getWidth() > b.getX() &&
                a.getY() < b.getY() + b.getSprite().getHeight() &&
                a.getY() + a.getSprite().getHeight() > b.getY();
    }

    void kill(Entity e1, Entity e2) {
        if (isColliding(e1, e2)) {
            System.out.println("Dead!");
            player.die = true;
        }
    }

}
