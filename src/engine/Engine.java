package engine;

import engine.graphics.Renderer;
import engine.input.InputHandler;
import engine.levels.*;
import engine.ui.UI;
import game.levels.*;
import game.ui.DeathScreen;
import game.ui.EndScreen;
import game.ui.LevelFinished;
import game.ui.MainMenu;
import game.ui.ScoreUI;
import java.awt.*;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

public class Engine extends Canvas implements Runnable {

    // screen settings
    final static double aspectRatio = 16.0 / 9.0;
    final static int screenWidth = 500;
    final static int screenHeight = (int) (screenWidth / aspectRatio);
    final static int scale = 3;

    private Thread gameThread;
    JFrame window;
    private boolean running = false;

    // modules
    private static InputHandler inputHandler;
    private Renderer renderer;

    // UI
    private static UI currentUI;
    public static int level1 = 1;
    public static int level2 = 2;
    public static int level3 = 3;
    public static int level4 = 4;
    public static int level5 = 5;
    public static int level6 = 6;

    // Levels
    public static Level currentLevel;
    public static int mainMenuScreen = 1;
    public static int scoreScreen = 2;
    public static int deathScreen = 3;
    public static int winScreen = 4;
    public static int endScreen = 5;

    // GAME STATES
    public static int current_state = 0;
    public static int ui_state = 1;
    public static int gameplay_state = 2;
    public static int endGame = 3;
    public static int gamePause_state = 4;

    public Engine() {
        Dimension size = new Dimension(screenWidth * scale, screenHeight * scale);
        setPreferredSize(size);
        window = new JFrame();
        inputHandler = new InputHandler();
        addKeyListener(inputHandler);
        renderer = new Renderer(screenWidth, screenHeight);
        current_state = ui_state;
    }

    public static void setCurrentLevel(int level) {
        if (level == 1) {
            currentLevel = new Level01("assets/world/Map01-Forest.png", inputHandler);
        } else if (level == 2) {
            currentLevel = new Level02("assets/world/Map02-Dun.png", inputHandler);
        } else if (level == 3) {
            currentLevel = new Level03("assets/world/Map03-Dun.png", inputHandler);
        } else if (level == 4) {
            currentLevel = new Level04("assets/world/Map04-island.png", inputHandler);
        } else if (level == 5) {
            currentLevel = new Level05("assets/world/Map05-island.png", inputHandler);
        } else if (level == 6) {
            currentLevel = new Level06("assets/world/Map06-island.png", inputHandler);
        }
    }

    public static void setCurrentUI(int ui) {
        if (ui == 1) {
            currentUI = new MainMenu(screenWidth * scale, screenHeight * scale, inputHandler);
        } else if (ui == 2) {
            currentUI = new ScoreUI(screenWidth * scale, screenHeight * scale, inputHandler);
        } else if (ui == 3) {
            currentUI = new DeathScreen(screenWidth * scale, screenHeight * scale, inputHandler);
        } else if (ui == 4) {
            currentUI = new LevelFinished(screenWidth * scale, screenHeight * scale, inputHandler);
        } else if (ui == 5) {
            currentUI = new EndScreen(screenWidth * scale, screenHeight * scale, inputHandler);
        }
    }

    public synchronized void start() {
        running = true;
        gameThread = new Thread(this, "Display");
        gameThread.start();
    }

    public synchronized void stop() {
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    // Game Loop
    @Override
    public void run() {
        final int FPS = 60;
        final double ns = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        int frames = 0;
        int updates = 0;
        requestFocus();

        setCurrentLevel(1);
        setCurrentUI(1);

        // main game loop
        while (running) {
            long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / ns;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                updates++;
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                window.setTitle("ups " + updates + ", " + frames + " FPS");
                timer += 1000;
                updates = 0;
                frames = 0;
            }
        }
    }

    public void update() {
        if (current_state == ui_state || current_state == gamePause_state) {
            currentUI.update();
        } else if (current_state == gameplay_state) {
            currentLevel.update();
        }
        inputHandler.update();
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        if (current_state == gameplay_state || current_state == gamePause_state) {
            renderer.clear();
            currentLevel.render(renderer);
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.drawImage(renderer.frame, 0, 0, screenWidth * scale, screenHeight * scale, null);

            if (current_state == gamePause_state) {
                currentUI.render(g);
            }

            g.dispose();
            bs.show();

        } else if (current_state == ui_state) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
            currentUI.render(g);
            g.dispose();
            bs.show();
        }
    }

    public void startGame() {
        window.add(this);
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(true);
        window.setVisible(true);
        this.start();
    }
}
