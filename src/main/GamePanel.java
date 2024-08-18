package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // aqui estamos definindo o tamanho da tela

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public int FPS = 60;
    Thread gameThread;
    PlayMenager pm;

    public GamePanel() {

        // configuração do painel
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT)); // largura e altura
        this.setBackground(Color.black); // cor da tela de fundo
        this.setLayout(null); // layout
        // implementação do evento do teclado
        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);

        pm = new PlayMenager();
    }

    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start();

    }

    @Override
    public void run() {

        // Loop do jogo
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime -  lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    private void update() {

        if (KeyHandler.pausePressed == false && pm.gameOver == false) {
            pm.update();
        }
    }

    public void paintComponent (Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        pm.draw(g2);


    }
}
