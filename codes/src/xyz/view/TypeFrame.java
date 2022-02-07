package xyz.view;

import javax.swing.*;
import java.awt.*;

public class TypeFrame extends JFrame {
    static JFrame typeFrame;
    public static boolean firsttime = true;

    public static void TypeChoice(JFrame father, int model) {


        if (model == 2 && firsttime) {
            InputBox temp = new InputBox();
            temp.createGUI(model, father);
        }
        if (model != 2 || (model == 2 && !firsttime)) {
            typeFrame = new JFrame();

            MenuBar menuBar = new MenuBar();
            Menu menu = new Menu("Next...");
            Menu menu1 = new Menu("Help");
            menuBar.add(menu);
            menuBar.add(menu1);
            typeFrame.setMenuBar(menuBar);
            MenuItem item1 = new MenuItem("Return");
            MenuItem item11 = new MenuItem("Help");
            item1.addActionListener((e) -> MenuFunction.setBefore(3));
            menu.add(item1);
            menu1.add(item11);

            typeFrame.setTitle("Difficulty choosing");
            JLabel temp = new JLabel();
            typeFrame.setSize(415, 330);
            temp.setSize(415, 330);
            typeFrame.add(temp);
            Icon iconBack = new ImageIcon("src/xyz/view/pic/选择模式.png");
            temp.setIcon(iconBack);
            temp.setOpaque(false);


            Icon easy = new ImageIcon("src/xyz/view/pic/easy.png");
            Icon middle = new ImageIcon("src/xyz/view/pic/middle.png");
            Icon difficult = new ImageIcon("src/xyz/view/pic/difficult.png");
            Icon yourself = new ImageIcon("src/xyz/view/pic/yourself.png");

            JButton easyButton = new JButton();
            JButton middleButton = new JButton();
            JButton yoursButton = new JButton();
            JButton difficultButton = new JButton();

            TypeFrame.TypeButton(easyButton, easy, 30, 10, "easy", model);
            TypeFrame.TypeButton(middleButton, middle, 210, 10, "middle", model);
            TypeFrame.TypeButton(difficultButton, difficult, 30, 130, "difficult", model);
            TypeFrame.TypeButton(yoursButton, yourself, 210, 130, "yourself", model);

            typeFrame.add(easyButton);
            typeFrame.add(middleButton);
            typeFrame.add(difficultButton);
            typeFrame.add(yoursButton);
            typeFrame.add(temp);
            typeFrame.setLocationRelativeTo(father);
            typeFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            father.setVisible(false);
            typeFrame.setResizable(false);
            typeFrame.setVisible(true);
        }

    }

    public static void TypeButton(JButton typeB, Icon type, int x, int y, String model, int choice) {
        typeB.setIcon(type);
        typeB.setHideActionText(true);
        typeB.setBorderPainted(false);
        typeB.setOpaque(false);
        typeB.setLocation(x, y);
        typeB.setBackground(Color.WHITE);
        typeB.setSize(160, 113);
        typeB.setVisible(true);
        switch (model) {
            case "easy":
                if (choice == 1)
                    typeB.addActionListener((e) -> DifficultyDecision.easyModel(choice));
                else if (choice == 2)
                    typeB.addActionListener((e) -> DifficultyDecision.easyModel(choice));
                else if (choice == 3)
                    typeB.addActionListener((e) -> DifficultyDecision.easyModel(choice));
                else if (choice == 4)
                    typeB.addActionListener((e) -> DifficultyDecision.easyModel(choice));
                break;
            case "middle":
                if (choice == 1)
                    typeB.addActionListener((e) -> DifficultyDecision.middleModel(choice));
                else if (choice == 2)
                    typeB.addActionListener((e) -> DifficultyDecision.middleModel(choice));
                else if (choice == 3)
                    typeB.addActionListener((e) -> DifficultyDecision.middleModel(choice));
                else if (choice == 4)
                    typeB.addActionListener((e) -> DifficultyDecision.middleModel(choice));
                break;
            case "difficult":
                if (choice == 1)
                    typeB.addActionListener((e) -> DifficultyDecision.difficultModel(choice));
                else if (choice == 2)
                    typeB.addActionListener((e) -> DifficultyDecision.difficultModel(choice));
                else if (choice == 3)
                    typeB.addActionListener((e) -> DifficultyDecision.difficultModel(choice));
                else if (choice == 4)
                    typeB.addActionListener((e) -> DifficultyDecision.difficultModel(choice));
                break;
            case "yourself":
                if (choice == 1)
                    typeB.addActionListener((e) -> DifficultyDecision.yourselfModel(choice));
                else if (choice == 2)
                    typeB.addActionListener((e) -> DifficultyDecision.yourselfModel(choice));
                else if (choice == 3)
                    typeB.addActionListener((e) -> DifficultyDecision.yourselfModel(choice));
                else if (choice == 4)
                    typeB.addActionListener((e) -> DifficultyDecision.yourselfModel(choice));
                break;
        }
    }

    public static void typeFrame() {
        TypeFrame.typeFrame.setVisible(false);
    }


}
