package xyz.view;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;

public class HelpBoard extends JFrame {
    private static Font font;
    static {
        try {
            font = Font.createFont( Font.TRUETYPE_FONT,
                    new FileInputStream("src/xyz/view/Font/稚行书体.ttf") );
        } catch(Exception e) {
            e.printStackTrace();
        }
        font = font.deriveFont(Font.PLAIN, 30);
    }

    public HelpBoard() {
        setTitle("Help");
        setSize(400, 640);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setLocation(1500,0);
        setLayout(null);
        this.getContentPane().setBackground(Color.WHITE);
        this.setFont(font);
        this.setForeground(new Color(250, 255, 200));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        String str = null;
        str =""+"你走进森林，经过摸索，你发现：\n"+
                "地面上的数字可以显示周围怪物的数量\n"+
                "左击空地不会有事，若是惊扰到怪物则会使自己受伤，积分减少\n"+
                "右击则能提前杀死怪物，积分增加并且有几率掉落宝箱获得道具\n"+
                "苹果可以兑换一个积分\n"+
                "肉排可以兑换两个积分\n"+
                "小刀会伤害你扣除一个积分\n"+
                "大剑会伤害你扣除两个积分\n"+
                "戒指可以一次性打开十字型的地图\n"+
                "火炬可以获得一秒钟怪物们的位置\n";
        g.drawString(str, 10, 10);
    }
}
