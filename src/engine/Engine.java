package engine;

import engine.graphics.Renderer;
import engine.input.InputHandler;
import engine.levels.*;
import engine.ui.UI;
import game.levels.*;
import game.ui.DeathScreen;
import game.ui.MainMenu;

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
    // private static MainMenu mainMenu ;
    // private static DeathScreen deathScreen = new DeathScreen(screenWidth * scale,
    // screenHeight * scale, inputHandler);

    // Levels
    public static Level currentLevel;


    // GAME STATE
    public static int gameState = 0;
    public static int menu = 1;
    public static int gameplay = 2;
    public static int endGame = 3;
    public static int gamePause = 4;

    public Engine() {
        Dimension size = new Dimension(screenWidth * scale, screenHeight * scale);
        setPreferredSize(size);
        window = new JFrame();
        inputHandler = new InputHandler();
        addKeyListener(inputHandler);
        renderer = new Renderer(screenWidth, screenHeight);
        gameState = menu;
    }

    public static void restartLevel() {

    }

    public static void setCurrentLevel(int level) {
        if (level == 1) {
            currentLevel = new Level1("assets/world/MapWaterEdge.png", inputHandler);
        } else if (level == 2) {
            currentLevel = new Level2("assets/world/MapWaterEdgeBig.png", inputHandler);
        } else if (level == 3) {
            currentLevel = new Level3("assets/world/MapWaterHard200.png", inputHandler);
        }
    }

    public static void setCurrentUI(int ui) {
        if (ui == 1) {
            currentUI = new MainMenu(screenWidth * scale, screenHeight * scale, inputHandler);
        } else if (ui == 2) {
            currentUI = new DeathScreen(screenWidth * scale, screenHeight * scale, inputHandler);
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
        if (gameState == menu || gameState == gamePause) {
            currentUI.update();
        } else if (gameState == gameplay) {
            currentLevel.update();
        } else if (gameState == endGame) {
            currentUI.update();
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

        if (gameState == gameplay || gameState == gamePause) {
            renderer.clear();
            currentLevel.render(renderer);
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.drawImage(renderer.frame, 0, 0, screenWidth * scale, screenHeight * scale, null);

            if (gameState == gamePause) {
                currentUI.render(g);
            }

            g.dispose();
            bs.show();

        } else if (gameState == menu) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
            currentUI.render(g);
            g.dispose();
            bs.show();
        } else if (gameState == endGame) {
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
