package game.levels;

import engine.Engine;
import engine.database.Database;
import engine.entities.Entity;
import engine.input.InputHandler;
import engine.levels.Level;
import engine.sound.Sound;
import engine.utilities.TileCoordinate;
import game.entities.*;

public class Level05 extends Level {

    private int timer = 0;
    boolean startOnce = true;
    boolean doOnce = true;
    Database database;
    Sound sound;
    Sound enmeySound;
    Player player;
    Enemy enemy;
    TileCoordinate end;

    public Level05(int width, int height, InputHandler input) {
        super(width, height, input);
        initiateLevel();
    }

    public Level05(String path, InputHandler input) {
        super(path, input);
        initiateLevel();
    }

    void initiateLevel() {
        deleteAllEntities();
        sound = new Sound();
        enmeySound = new Sound();
        TileCoordinate playerPosition = new TileCoordinate(22, 25);
        player = new Player(playerPosition.x(), playerPosition.y(), input);
        TileCoordinate enemyPosition = new TileCoordinate(18, 17);
        enemy = new Enemy(enemyPosition.x(), enemyPosition.y());
        database = new Database();
        add(player);
        add(enemy);
        enemy.target(player);
        end = new TileCoordinate(69, 63);
        sound.setFile(0);
        enmeySound.setFile(2);
        enmeySound.changeVolume(3);
    }

    void startOnce() {
        if (startOnce) {
            sound.loop();
            startOnce = false;
        }
    }

    private boolean isEnemySoundPlaying = false;

    @Override
    public void currentLevelUpdate() {
        startOnce();
        timer++;
        kill(player, enemy);

        double dist = getDistance(enemy.getX(), enemy.getY(), player.getX(), player.getY());
        double soundRadius = 360.0;

        if (dist < soundRadius) {
            if (!isEnemySoundPlaying && !player.die) {
                enmeySound.loop();
                isEnemySoundPlaying = true;
            }
        } else {
            if (isEnemySoundPlaying) {
                enmeySound.stop();
                isEnemySoundPlaying = false;
            }
        }

        if (player.die && isEnemySoundPlaying) {
            enmeySound.stop();
            isEnemySoundPlaying = false;
        }

        if (player.die) {
            if (doOnce) {
                sound.stop();
                doOnce = false;
            }
            if (timer % 120 == 0) {
                Engine.setCurrentUI(3);
                Engine.current_state = Engine.gamePause_state;
            }
        }

        // end of the level
        if (player.getPviot().getX() / 16 == end.getXInTile() && player.getPviot().getY() / 16 == end.getYInTile()) {
            // database.saveScore(50);
            database.close();
            stop = true;
            System.out.println("win!");
            Engine.current_state = Engine.ui_state;
            sound.stop();
            enmeySound.stop();
            Engine.setCurrentUI(Engine.winScreen);
            Engine.setCurrentLevel(Engine.level2);
        }

    }

    @Override
    public void restartLevel() {
        doOnce = true;
        startOnce = true;
        initiateLevel();
    }

    boolean isColliding(Entity a, Entity b) {
        return a.getX() < b.getX() + b.getSprite().getWidth()
                && a.getX() + a.getSprite().getWidth() > b.getX()
                && a.getY() < b.getY() + b.getSprite().getHeight()
                && a.getY() + a.getSprite().getHeight() > b.getY();
    }

    void kill(Entity e1, Entity e2) {
        if (isColliding(e1, e2)) {
            player.die = true;
        }
    }

    private double getDistance(double sx, double sy, double ex, double ey) {
        double dx = sx - ex;
        double dy = sy - ey;
        return Math.sqrt((dx * dx) + (dy * dy));
    }

}
