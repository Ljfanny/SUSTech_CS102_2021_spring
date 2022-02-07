package xyz.view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.*;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

public class ItemComponent extends JComponent {

    private static Font font;
    private int num;
    private static int fontSize;
    private static int fontX;
    private static int fontY;


    {
        try {
            font = Font.createFont( Font.TRUETYPE_FONT,
                    new FileInputStream("src/xyz/view/Font/Denise Sans.ttf") );
        } catch(Exception e) {
            e.printStackTrace();
        }
        int size= getFontSize();
        font = font.deriveFont(Font.PLAIN, size);

    }

    public ItemComponent (int num) {
        this.num = num;
    }

    @Override
    public void paintComponent (Graphics g) {
        super.paintComponent(g);
        painting(g);
    }

    private void painting (Graphics g) {
        int spacing = (int) (getWidth() * 0.05);
        Image image = ItemUtil.genItem(num);
        if (image != null) {
            g.drawImage(image, spacing, spacing, getWidth() - 2 * spacing, getHeight() - 2 * spacing, this);
        } else {
            if (num == 0) return;
            g.setFont(font);
            g.drawString("" + num, getFontX(), getFontY());
        }
    }

    public int getFontSize() {
        return fontSize;
    }

    public static void setFontSize(int fontSize) {
        ItemComponent.fontSize = fontSize;
    }

    public int getFontX() {
        return fontX;
    }

    public static void setFontX(int fontX) {
        ItemComponent.fontX = fontX;
    }

    public int getFontY() {
        return fontY;
    }

    public static void setFontY(int fontY) {
        ItemComponent.fontY = fontY;
    }


}
