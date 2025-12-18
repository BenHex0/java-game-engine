package engine;

import engine.graphics.Renderer;
import engine.input.InputHandler;
import engine.levels.*;
import engine.ui.UI;
import game.levels.SpwanLevel;
import java.awt.*;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;

public class Engine extends Canvas implements Runnable {

    // screen settings
    final double aspectRatio = 16.0 / 9.0;
    final int screenWidth = 500;
    final int screenHeight = (int) (screenWidth / aspectRatio);
    final int scale = 3;

    private Thread gameThread;
    JFrame window;
    private boolean running = false;

    // modules
    private InputHandler inputHandler;
    private Renderer renderer;
    private Level currentLevel;
    private UI ui;

    // Levels
    SpwanLevel spwanLevel;

    // GAME STATE
    public int gameState = 1;
    public final int gameState2 = 2;


    public Engine() {
        Dimension size = new Dimension(screenWidth * scale, screenHeight * scale);
        setPreferredSize(size);
        window = new JFrame();
        inputHandler = new InputHandler();
        addKeyListener(inputHandler);
        renderer = new Renderer(screenWidth, screenHeight);

        ui = new UI(screenWidth, screenHeight);

        spwanLevel = new SpwanLevel("assets/world/MapWaterEdge.png", inputHandler);
        currentLevel = spwanLevel;
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
        currentLevel.update();

        // if (inputHandler.isKeyPressed(Key.DOWN)) {
        // gameState = 2;
        // } else if (inputHandler.isKeyPressed(Key.UP)) {
        // gameState = 1;
        // }
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        if (gameState == 1) {
            renderer.clear();

            currentLevel.render(renderer);

            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.drawImage(renderer.frame, 0, 0, screenWidth * scale, screenHeight * scale, null);
            g.dispose();
            bs.show();
        }

        if (gameState == 2) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
            ui.render(g);
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
