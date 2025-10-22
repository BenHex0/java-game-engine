package engine;

import java.util.ArrayList;
import entities.Entity;
import entities.Player;

public class Engine implements Runnable {

    ArrayList<Entity> entities;
    Renderer renderer = new Renderer();
    Camera camera = new Camera(renderer.screenWidth, renderer.screenHeight);
    InputHandler inputHandler = new InputHandler();
    Player player = new Player();
    Thread gameThread;

    public Engine() {
        entities = new ArrayList<>();
        entities.add(player);
        renderer.addObjects(entities);
        renderer.addKeyListener(inputHandler);
        renderer.setFocusable(true);
        renderer.setCamera(camera);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public Renderer getRenderer() {
        return renderer;
    }

    // Game Loop
    @Override
    public void run() {
        final int FPS = 60;
        final double TIME_PER_FRAME = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        for (Entity e : entities) {
            e.start();
            if (e.input != null) {
                e.input = inputHandler;
            }
            if (e.camera != null) {
                e.camera = camera;
            }
        }

        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / TIME_PER_FRAME;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                inputHandler.update();
                // 1. UPDATE: update game information
                for (Entity e : entities) {
                    e.update();
                }
                // 2. REDRAW: draw the screen with the updated information
                renderer.repaint();

                // handle time
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }
}
