package main;

import mino.*;

import java.awt.*;
import java.security.Key;
import java.util.ArrayList;
import java.util.Random;

public class PlayMenager {

    // Principal área do jogo
    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    // Mino
    Mino currentMino;
    final int MINO_START_X;
    final int MINO_START_Y;
    Mino nextMino;
    final int NEXTMINO_X;
    final int NEXTMINO_Y;
    public static ArrayList<Blocos> staticBlocos = new ArrayList<>();

    // Outros
    public static int dropInterval = 60;
    boolean gameOver;

    // Efeito
    boolean effectCounterOn;
    int effectCounter;
    ArrayList<Integer> effectY = new ArrayList<>();

    // Pontuação

    int level = 1;
    int lines;
    int score;


    public PlayMenager() {
        left_x = (GamePanel.WIDTH/2) - (WIDTH/2); // 1280/2 - 360/2 = 460
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        MINO_START_X = left_x + (WIDTH/2) - Blocos.SIZE;
        MINO_START_Y = top_y + Blocos.SIZE;

        NEXTMINO_X = right_x + 175;
        NEXTMINO_Y = top_y + 500;

        // definindo quando o mino começa
        currentMino = pickMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);
        nextMino = pickMino();
        nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);
    }

    private Mino pickMino() {
        Mino mino = null;
        int i = new Random().nextInt(7);
        switch (i) {
            case 0:
                mino = new Mino_L1();
                break;
            case 1:
                mino = new Mino_L2();
                break;
            case 2:
                mino = new Mino_Square();
                break;
            case 3:
                mino = new Mino_Bar();
                break;
            case 4:
                mino = new Mino_T();
                break;
            case 5:
                mino = new Mino_Z1();
                break;
            case 6:
                mino = new Mino_Z2();
                break;
        }

        return mino;
    }
    public void update() {

        // Conferindo se o Mino está ativo
        if (currentMino.active == false) {

            // Se o mino estiver inativo iremos jogar ele no staticBlocks
            staticBlocos.add(currentMino.b[0]);
            staticBlocos.add(currentMino.b[1]);
            staticBlocos.add(currentMino.b[2]);
            staticBlocos.add(currentMino.b[3]);

            // Checando q game over
            if (currentMino.b[0].x == MINO_START_X && currentMino.b[0].y == MINO_START_Y) {

                // Não haverá mais espaço para o mino e assim o jogo se encerra
                gameOver = true;
            }


            currentMino.deactivating = false;


            // Substituir o mino atual pelo próximo
            currentMino = nextMino;
            currentMino.setXY(MINO_START_X, MINO_START_Y);
            nextMino = pickMino();
            nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);

            // Quando o mino se torna inativo, checa se as linhas foram deletadas
            checkDelete();

        } else {

            currentMino.update();
        }
    }
    private void checkDelete() {

        int x = left_x;
        int y = top_y;
        int blockCount = 0;
        int lineCount = 0;

        while (x < right_x && y < bottom_y) {

            for (int i = 0; i < staticBlocos.size(); i++) {
                if (staticBlocos.get(i).x == x && staticBlocos.get(i).y == y) {
                    blockCount++;
                }
            }

            x += Blocos.SIZE;

            if (x == right_x) {

                if (blockCount == 12) {

                    effectCounterOn = true;
                    effectY.add(y);

                    for (int i = staticBlocos.size()-1; i > 0; i--) {

                        if (staticBlocos.get(i).y == y) {
                            staticBlocos.remove(i);
                        }
                    }

                    lineCount++;
                    lines++;

                    if (lines % 10 == 0 && dropInterval > 1) {

                        level++;
                        if (dropInterval > 10) {
                            dropInterval -= 10;
                        } else {
                            dropInterval -= 1;
                        }
                    }

                    for (int i = 0; i < staticBlocos.size(); i++) {

                        if (staticBlocos.get(i).y < y) {
                            staticBlocos.get(i).y += Blocos.SIZE;
                        }
                    }
                }

                blockCount = 0;
                x = left_x;
                y += Blocos.SIZE;
            }
        }

        // Adicionando o score
        if (lineCount > 0) {
            int singleLineScore = 10 + level;
            score += singleLineScore * lineCount;
        }
    }

    public void draw(Graphics2D g2) {

        // Aqui vamos desenhar a área do jogo "retangulo"
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x-4, top_y-4, WIDTH+8, HEIGHT+8);

        // Aqui vamos desenhar o próximo quadro
        int x = right_x + 100;
        int y = bottom_y - 200;
        g2.drawRect(x, y, 200, 200);
        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("NEXT", x+60, y+60 );

        // Desenho da pontuação
        g2.drawRect(x, top_y, 250, 300);
        x += 40;
        y = top_y + 90;
        g2.drawString("LEVEL: " + level, x, y); y+= 70;
        g2.drawString("LINES: " + lines, x, y); y+= 70;
        g2.drawString("SCORE: " + score, x, y);


        // Desenho do "currentMino"
        if(currentMino != null) {
            currentMino.draw(g2);
        }

        // Desenho do próximo mino
        nextMino.draw(g2);


        // Desenho do Static Blocks
        for (int i = 0; i < staticBlocos.size(); i++) {
            staticBlocos.get(i).draw(g2);
        }

        // Desenho do efeito
        if (effectCounterOn) {
            effectCounter++;

            g2.setColor(Color.red);
            for (int i = 0; i < effectY.size(); i++) {
                g2.fillRect(left_x, effectY.get(i), WIDTH, Blocos.SIZE);
            }

            if (effectCounter == 10) {
                effectCounterOn = false;
                effectCounter = 0;
                effectY.clear();
            }
        }


        // Desenho do pause ou game over
        g2.setColor(Color.yellow);
        g2.setFont(g2.getFont().deriveFont(50f));
        if (gameOver) {
            x = left_x + 25;
            y = top_y + 320;
            g2.drawString("GAME OVER", x, y);
            g2.setColor(Color.red);
        }
        else if (KeyHandler.pausePressed) {
            x = left_x + 70;
            y = top_y + 320;
            g2.drawString("PAUSED", x, y);
        }

        // Desenho do titulo do jogo
        x = 35;
        y = top_y + 320;
        g2.setColor(Color.yellow);
        g2.setFont(new Font("Arial", Font.ITALIC, 40));
        g2.drawString("TETRIS SIMPLES", x, y);
    }
}
