package main;

import javax.swing.*;
import engine.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame("Game");
        Engine engine = new Engine();
        window.add(engine.getRenderer());
        window.pack();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(true);
        window.setVisible(true);
        engine.startGameThread();
    }
}
