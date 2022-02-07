package xyz.view;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;

public class WinnerComponent  extends JFrame{
    private static Font font;
    private String result;
    public boolean isTurnUp;

    static {
        try {
            font = Font.createFont( Font.TRUETYPE_FONT,
                    new FileInputStream("src/xyz/view/Font/Denise Sans.ttf") );
        } catch(Exception e) {
            e.printStackTrace();
        }
        font = font.deriveFont(Font.PLAIN, 30);
    }

    public WinnerComponent() {
        setTitle("Winner Board");
        JLabel addback=new JLabel();
        Icon back=new ImageIcon("src/xyz/view/pic/winner.png");
        addback.setIcon(back);
        addback.setVisible(true);
        addback.setSize(400,150);
        add(addback);
        setSize(400, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        this.getContentPane().setBackground(Color.WHITE);
        this.setFont(font);
        result = " ";
        this.isTurnUp=false;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawString("Winner:", 60, 80);
        g.drawString("" + result, 220, 80);
    }

    public void Result(String result){
        this.result = result;
    }
}

