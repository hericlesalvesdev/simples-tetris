package mino;

import java.awt.*;

public class Mino_L2 extends Mino {

    public Mino_L2() {
        create(Color.blue);

    }
    public void setXY(int x, int y) {
        //    o
        //    o
        //  o o

        b[0].x = x;
        b[0].y = y;
        b[1].x = b[0].x;
        b[1].y = b[0].y - Blocos.SIZE;
        b[2].x = b[0].x;
        b[2].y = b[0].y + Blocos.SIZE;
        b[3].x = b[0].x - Blocos.SIZE;
        b[3].y = b[0].y + Blocos.SIZE;
    }
    public void getDirection1() {
        //   o
        //   o
        // o o

        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x;
        tempB[1].y = b[0].y - Blocos.SIZE;
        tempB[2].x = b[0].x;
        tempB[2].y = b[0].y + Blocos.SIZE;
        tempB[3].x = b[0].x - Blocos.SIZE;
        tempB[3].y = b[0].y + Blocos.SIZE;

        updateXY(1);
    }
    public void getDirection2() {
        // o
        // o o o
        //

        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x + Blocos.SIZE;
        tempB[1].y = b[0].y;
        tempB[2].x = b[0].x - Blocos.SIZE;
        tempB[2].y = b[0].y;
        tempB[3].x = b[0].x - Blocos.SIZE;
        tempB[3].y = b[0].y - Blocos.SIZE;

        updateXY(2);

    }
    public void getDirection3() {
        // o o
        // o
        // o

        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x;
        tempB[1].y = b[0].y + Blocos.SIZE;
        tempB[2].x = b[0].x;
        tempB[2].y = b[0].y - Blocos.SIZE;
        tempB[3].x = b[0].x + Blocos.SIZE;
        tempB[3].y = b[0].y - Blocos.SIZE;

        updateXY(3);


    }
    public void getDirection4() {
        //
        // o o o
        //     o

        tempB[0].x = b[0].x;
        tempB[0].y = b[0].y;
        tempB[1].x = b[0].x - Blocos.SIZE;
        tempB[1].y = b[0].y;
        tempB[2].x = b[0].x + Blocos.SIZE;
        tempB[2].y = b[0].y;
        tempB[3].x = b[0].x + Blocos.SIZE;
        tempB[3].y = b[0].y + Blocos.SIZE;

        updateXY(4);
    }

}
