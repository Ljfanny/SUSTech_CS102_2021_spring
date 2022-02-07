package xyz.view;

import java.awt.*;
import java.util.Random;

public class ItemUtil {
    private final static Image mask1;
    private final static Image mask2;
    private final static Image mask3;
    private final static Image mask4;
    private final static Image mask5;
    private final static Image grass1;
    private final static Image flag;
    private final static Image Boom;
    private final static Image Boom1;
    private final static Image Boom2;
    private final static Image Boom3;
    private final static Image Boom4;
    private final static Image Boom5;
    private final static Image Boom6;
    private final static Image Boom7;
    private final static Image Boom8;
    private final static Image Boom9;
    private final static Image Boom10;
    private final static Image Boom11;
    private final static Image Boom12;
    private final static Image Boom13;
    private final static Image Boom14;
    private final static Image move1;
    private final static Image move2;
    public final static int maskNum = -2;
    public final static int flagNum = -100;
    public final static int boomNum = -3;



    static {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        mask1 = toolkit.getImage("src/xyz/view/pic/1.png");
        mask2 = toolkit.getImage("src/xyz/view/pic/2.png");
        mask3 = toolkit.getImage("src/xyz/view/pic/3.png");
        mask4 = toolkit.getImage("src/xyz/view/pic/4.png");
        mask5 = toolkit.getImage("src/xyz/view/pic/5.png");
        flag = toolkit.getImage("src/xyz/view/pic/flag.png");
        Boom = toolkit.getImage("src/xyz/view/pic/monster1.png");
        Boom1 = toolkit.getImage("src/xyz/view/pic/monster2.png");
        Boom2 = toolkit.getImage("src/xyz/view/pic/monster3.png");
        Boom3 = toolkit.getImage("src/xyz/view/pic/monster4.png");
        Boom4 = toolkit.getImage("src/xyz/view/pic/monster5.png");
        Boom5 = toolkit.getImage("src/xyz/view/pic/monster6.png");
        Boom6 = toolkit.getImage("src/xyz/view/pic/monster7.png");
        Boom7 = toolkit.getImage("src/xyz/view/pic/monster8.png");
        Boom8 = toolkit.getImage("src/xyz/view/pic/monster9.png");
        Boom9 = toolkit.getImage("src/xyz/view/pic/monster10.png");
        Boom10 = toolkit.getImage("src/xyz/view/pic/monster11.png");
        Boom11 = toolkit.getImage("src/xyz/view/pic/monster12.png");
        Boom12 = toolkit.getImage("src/xyz/view/pic/monster13.png");
        Boom13 = toolkit.getImage("src/xyz/view/pic/monster14.png");
        Boom14 = toolkit.getImage("src/xyz/view/pic/monster15.png");
        grass1 = toolkit.getImage("src/xyz/view/pic/wall.png");
        move1 = toolkit.getImage("src/xyz/view/pic/move.png");
        move2 = toolkit.getImage("src/xyz/view/pic/move2.png");
    }


    public static Image genItem(int i) {
        if (i == flagNum)
            return flag;
        else if (i == maskNum)
            return grass1;

        else if (i == boomNum)
            return Boom;
        else if (i ==-4)
            return Boom1;
        else if (i==-5)
            return Boom2;
        else if (i==-6)
            return Boom3;
        else if (i==-7)
            return Boom4;
        else if (i==-8)
            return Boom5;
        else if (i==-9)
            return Boom6;
        else if (i==-10)
            return Boom7;
        else if (i==-11)
            return Boom8;
        else if (i==-12)
            return Boom9;
        else if (i==-13)
            return Boom10;
        else if (i==-14)
            return Boom11;
        else if (i==-15)
            return Boom12;
        else if (i==-16)
            return Boom13;
        else if (i==-17)
            return Boom14;

        else if (i==1)
            return mask1;
        else if (i==2)
            return mask2;
        else if (i==3)
            return mask3;
        else if (i==4)
            return mask4;
        else if (i==5)
            return mask5;
        else if (i==66)
            return move1;
        else if (i==88)
            return move2;
            return null;
        // TODO: This is just a sample. You should implement the method here to provide the 可见的 component according to the argument i

    }
}
