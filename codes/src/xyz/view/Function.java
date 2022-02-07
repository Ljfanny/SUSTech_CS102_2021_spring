package xyz.view;

import xyz.model.Board;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Function {
    public static boolean alwaysUse = false;
    public static class Toushi extends JButton {

        public static boolean canUse = false;

        public Toushi() {
            Icon toushi = new ImageIcon("src/xyz/view/pic/透视.png");
            setIcon(toushi);
            setHideActionText(true);
            setBorderPainted(false);
            setOpaque(false);
            addActionListener((e) -> Save.getModel().setToushi());
            setVisible(true);
        }
    }

    public static class Zhadan extends JButton {
        public static boolean canUse = false;
        public static boolean open = false;
        public Zhadan() {
            Icon zhadan = new ImageIcon("src/xyz/view/pic/炸开.png");
            setIcon(zhadan);
            setHideActionText(true);
            setBorderPainted(false);
            setOpaque(false);
            addActionListener((e) -> {
                try {
                    Save.getModel().setZhadan();
                } catch (IOException | InterruptedException ioException) {
                    ioException.printStackTrace();
                }
            });
            setVisible(true);
        }
    }

    public static class addOpenPoint extends JButton {
        public static boolean canUse = false;


        public addOpenPoint() {
            Icon add1;
            if (DifficultyDecision.model == 1||DifficultyDecision.model == 3)
                add1 = new ImageIcon("src/xyz/view/pic/add1buyong.png");
            else add1 = new ImageIcon("src/xyz/view/pic/add1.png");
            setIcon(add1);
            setHideActionText(true);
            setBorderPainted(false);
            setOpaque(false);
            addActionListener((e) -> Save.getModel().setaddonepoint());
            setVisible(true);
        }
    }

    public static class decreaseOnePoint extends JButton {
        public static boolean canUse = false;


        public decreaseOnePoint() {
            Icon decrease1;
            if (DifficultyDecision.model == 1||DifficultyDecision.model == 3)
                decrease1 = new ImageIcon("src/xyz/view/pic/-1buyong.png");
            else decrease1 = new ImageIcon("src/xyz/view/pic/-1.png");
            setIcon(decrease1);
            setHideActionText(true);
            setBorderPainted(false);
            setOpaque(false);

            addActionListener((e) -> Save.getModel().setdecreaseonepoint());

            setVisible(true);
        }
    }

    public static class addTwoPoint extends JButton {
        public static boolean canUse = false;
        public addTwoPoint() {
            Icon add2;
            if (DifficultyDecision.model == 1||DifficultyDecision.model == 3)
                add2 = new ImageIcon("src/xyz/view/pic/add2buyong.png");
            else add2 = new ImageIcon("src/xyz/view/pic/add2.png");
            setIcon(add2);
            setHideActionText(true);
            setBorderPainted(false);
            setOpaque(false);

            addActionListener((e) -> Save.getModel().setaddtwopoint());

            setVisible(true);
        }
    }

    public static class decreaseTwoPoint extends JButton {
        public static boolean canUse = false;


        public decreaseTwoPoint() {
            Icon decrease2;
            if (DifficultyDecision.model == 1||DifficultyDecision.model == 3)
                decrease2 = new ImageIcon("src/xyz/view/pic/-2buyong.png");
            else decrease2 = new ImageIcon("src/xyz/view/pic/-2.png");
            setIcon(decrease2);
            setHideActionText(true);
            setBorderPainted(false);
            setOpaque(false);
            addActionListener((e) -> Save.getModel().setdecreasetwopoint());
            setVisible(true);
        }
    }

}
