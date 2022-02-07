package xyz.view;

import javax.swing.*;
import java.awt.*;

public class Tools extends JComponent {
    public GIF locations;
    public static Image too1;
    public static Image too2;
    public static Image too3;
    public static Image too4;
    public static Image too5;
    public static Image too6;
    public static Image now;
   String haha;
   Tools(GIF gif){
       locations=gif;
      setLayout(null);
      setSize(999,999);
       gif.start();
   }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        if (Save.gameController.boxNumber!=0)
//        System.out.println("woshishabi");
        g.drawImage(now,(int) locations.x,(int) locations.y,100,100,null);

    }
   public void changeImage(int tooNum){

       if (tooNum==1)
           now=too1;
       else if (tooNum==2)
           now=too2;
       else if (tooNum==3)
           now=too3;
       else if (tooNum==4)
           now=too4;
       else if (tooNum==5)
           now=too5;
       else if (tooNum==6)
           now=too6;
   }

    static {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        too1 = toolkit.getImage("src/xyz/view/pic/zero.png");
        too2 = toolkit.getImage("src/xyz/view/pic/one.png");
        too3 = toolkit.getImage("src/xyz/view/pic/two.png");
        too4 = toolkit.getImage("src/xyz/view/pic/three.png");
        too5 = toolkit.getImage("src/xyz/view/pic/four.png");
        too6 = toolkit.getImage("src/xyz/view/pic/five.png");
    }


}
