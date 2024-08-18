package mino;

import main.KeyHandler;
import main.PlayMenager;

import java.awt.*;
import java.awt.event.KeyListener;
import java.lang.annotation.Target;
import java.security.Key;

public class Mino {

    public Blocos b[] = new Blocos[4];
    public Blocos tempB[] = new Blocos[4];
    int autoDropCounter = 0;
    public int direction = 1; // existem 4 posições possíveis
    boolean leftCollision, rightCollision, bottomCollision;
    public boolean active = true;
    public boolean deactivating;
    int deactivateCounter = 0;



    // Matrizes
    public void create(Color c) {
        b[0] = new Blocos(c);
        b[1] = new Blocos(c);
        b[2] = new Blocos(c);
        b[3] = new Blocos(c);
        tempB[0] = new Blocos(c);
        tempB[1] = new Blocos(c);
        tempB[2] = new Blocos(c);
        tempB[2] = new Blocos(c);
        tempB[3] = new Blocos(c);
        tempB[3] = new Blocos(c);

    }

    public void setXY(int x, int y) {
    }

    public void updateXY(int direction) {

        checkMovementCollision();

        if (leftCollision == false && rightCollision == false && bottomCollision == false) {

            this.direction = direction;
            b[0].x = tempB[0].x;
            b[0].y = tempB[0].y;
            b[1].x = tempB[1].x;
            b[1].y = tempB[1].y;
            b[2].x = tempB[2].x;
            b[2].y = tempB[2].y;
            b[3].x = tempB[3].x;
            b[3].y = tempB[3].y;
        }
    }

    public void getDirection1() {
    }

    public void getDirection2() {
    }

    public void getDirection3() {
    }

    public void getDirection4() {
    }

    public void checkMovementCollision() {
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;


        // checa a colisão dos blocos já caidos

        checkStaticBlocksCollison();

        // aqui vai checar o frame de colisão

        // colisão lado esquerdo
        for (int i = 0; i < b.length; i++) {
            if (b[i].x == PlayMenager.left_x) {
                leftCollision = true;
            }
        }

        // colisão lado direito
        for (int i = 0; i < b.length; i++) {
            if (b[i].x + Blocos.SIZE == PlayMenager.right_x) {
                rightCollision = true;
            }
        }

        // colisão em baixo
        for (int i = 0; i < b.length; i++) {
            if (b[i].y + Blocos.SIZE == PlayMenager.bottom_y) {
                bottomCollision = true;
            }
        }
    }

    public void checkRotationCollision() {

        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        // checa a colisão dos blocos já caidos
        checkMovementCollision();

        // aqui vai checar o frame de colisão

        // colisão lado esquerdo
        for (int i = 0; i < b.length; i++) {
            if (tempB[i].x < PlayMenager.left_x) {
                leftCollision = true;
            }
        }

        // colisão lado direito
        for (int i = 0; i < b.length; i++) {
            if (tempB[i].x + Blocos.SIZE > PlayMenager.right_x) {
                rightCollision = true;
            }
        }

        // colisão em baixo
        for (int i = 0; i < b.length; i++) {
            if (b[i].y + Blocos.SIZE > PlayMenager.bottom_y) {
                bottomCollision = true;
            }
        }
    }
    private void checkStaticBlocksCollison() {

        for (int i = 0; i < PlayMenager.staticBlocos.size(); i++) {

            int targetX = PlayMenager.staticBlocos.get(i).x;
            int targetY = PlayMenager.staticBlocos.get(i).y;

            // checa pra baixo

            for (int ii = 0; ii < b.length; ii++) {
                if (b[ii].y + Blocos.SIZE == targetY && b[ii].x == targetX) {
                    bottomCollision = true;
                }
            }
            // checa pra esquerda
            for (int ii = 0; ii < b.length; ii++) {
                if (b[ii].x - Blocos.SIZE == targetX && b[ii].y == targetY) {
                    leftCollision = true;
                }
            }
            for (int ii = 0; ii < b.length; ii++) {
                if (b[ii].x + Blocos.SIZE == targetX && b[ii].y == targetY) {
                    rightCollision = true;
                }
            }
        }
     }
    public void update() {

        if (deactivating) {
            deactivating();
        }

        // movimento do mino
        if (KeyHandler.upPressed) {
            switch (direction) {
                case 1:
                    getDirection2();
                    break;
                case 2:
                    getDirection3();
                    break;
                case 3:
                    getDirection4();
                    break;
                case 4:
                    getDirection1();
                    break;
            }
            KeyHandler.upPressed = false;
        }

        checkMovementCollision();

        if (KeyHandler.downPressed) {
            // se o mino não estiver colidindo ele irá pra baixo

            if (bottomCollision == false) {

                b[0].y += Blocos.SIZE;
                b[1].y += Blocos.SIZE;
                b[2].y += Blocos.SIZE;
                b[3].y += Blocos.SIZE;

                // quando se mover para baixo, o contador irá resetar
                autoDropCounter = 0;
            }

            KeyHandler.downPressed = false;
        }
        if (KeyHandler.leftPressed) {

            if (leftCollision == false) {

                b[0].x -= Blocos.SIZE;
                b[1].x -= Blocos.SIZE;
                b[2].x -= Blocos.SIZE;
                b[3].x -= Blocos.SIZE;
            }

            KeyHandler.leftPressed = false;
        }
        if (KeyHandler.rightPressed) {

            if (rightCollision == false) {

                b[0].x += Blocos.SIZE;
                b[1].x += Blocos.SIZE;
                b[2].x += Blocos.SIZE;
                b[3].x += Blocos.SIZE;
            }

            KeyHandler.rightPressed = false;
        }

        if (bottomCollision) {
            deactivating = true;
        } else {
            autoDropCounter++; // o contador é incrementado a cada frame
            if (autoDropCounter == PlayMenager.dropInterval) {
                // O mino irá para baixo
                b[0].y += Blocos.SIZE;
                b[1].y += Blocos.SIZE;
                b[2].y += Blocos.SIZE;
                b[3].y += Blocos.SIZE;
                autoDropCounter = 0;
            }
        }

    }
    private void deactivating() {
        deactivateCounter++;

        // Espera 45 frames para desativar
        if (deactivateCounter == 45) {
            deactivateCounter = 0;
            checkMovementCollision();

            // se esrtiver tendo colisão após 45 segundos, o mino será considerado inativo
            if (bottomCollision) {
                active = false;
            }
        }
    }

    public void draw(Graphics2D g2) {

        int margin = 2;
        g2.setColor(b[0].c);
        g2.fillRect(b[0].x+margin, b[0].y+margin, Blocos.SIZE- (margin *2), Blocos.SIZE- (margin *2));
        g2.fillRect(b[1].x+margin, b[1].y+margin, Blocos.SIZE- (margin *2), Blocos.SIZE- (margin *2));
        g2.fillRect(b[2].x+margin, b[2].y+margin, Blocos.SIZE- (margin *2), Blocos.SIZE- (margin *2));
        g2.fillRect(b[3].x+margin, b[3].y+margin, Blocos.SIZE- (margin *2), Blocos.SIZE- (margin *2));

    }

}
