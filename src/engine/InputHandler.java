package engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class InputHandler implements KeyListener {

    public enum Key {
        UP(KeyEvent.VK_W),
        DOWN(KeyEvent.VK_S),
        LEFT(KeyEvent.VK_A),
        RIGHT(KeyEvent.VK_D),
        JUMP(KeyEvent.VK_SPACE),
        ESCAPE(KeyEvent.VK_ESCAPE);

        private final int keyCode;

        Key(int keyCode) {
            this.keyCode = keyCode;
        }

        public int getKeyCode() {
            return keyCode;
        }
    }

    private final Map<Integer, Boolean> currentKeys = new HashMap<>();
    private final Map<Integer, Boolean> previousKeys = new HashMap<>();

    // Call this every frame before processing input
    public void update() {
        previousKeys.clear();
        previousKeys.putAll(currentKeys);
    }

    public boolean isKeyDown(int keyCode) {
        return currentKeys.getOrDefault(keyCode, false);
    }

    public boolean wasKeyDown(int keyCode) {
        return previousKeys.getOrDefault(keyCode, false);
    }

    public boolean isKeyPressed(int keyCode) {
        return isKeyDown(keyCode) && !wasKeyDown(keyCode);
    }

    public boolean isKeyReleased(int keyCode) {
        return !isKeyDown(keyCode) && wasKeyDown(keyCode);
    }

    // --- KeyListener methods ---
    @Override
    public void keyPressed(KeyEvent e) {
        currentKeys.put(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        currentKeys.put(e.getKeyCode(), false);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    // --- Overloads using Enum ---
    public boolean isKeyDown(Key key) {
        return isKeyDown(key.getKeyCode());
    }

    public boolean isKeyPressed(Key key) {
        return isKeyPressed(key.getKeyCode());
    }

    public boolean isKeyReleased(Key key) {
        return isKeyReleased(key.getKeyCode());
    }
}
