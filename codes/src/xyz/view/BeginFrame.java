package xyz.view;

import xyz.database.DatabaseUtil;
import xyz.database.UserInformation;

import javax.swing.*;
import java.awt.*;

public class BeginFrame extends JFrame {
    public static int model1 = 1;
    public static int model2 = 2;
    public static int model3 = 3;
    public static int model4 = 4;
    static JFrame choiceFrame;
    JPasswordField writePassword;
    JTextField writeID;

    public BeginFrame() {

        setResizable(false);
        setTitle("Magic Mine Clearance Game");
        setSize(700, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        Dimension d1 = new Dimension(340, 300);
        Dimension d2 = new Dimension(50, 50);
        Dimension d3 = new Dimension(250, 30);
        Dimension d4 = new Dimension(100, 40);

        Font tFont = new Font("微软雅黑", Font.BOLD, 30);

        JLabel ID = new JLabel();
        ID.setText("账号：");
        ID.setFont(tFont);
        ID.setPreferredSize(d2);
        ID.setBounds(190, 400, 100, 100);
        ID.setForeground(new Color(120, 70, 8));
        getContentPane().add(ID);


        writeID = new JTextField();
        writeID.setPreferredSize(d3);
        add(writeID);
        writeID.setBounds(305, 432, 180, 40);
        writeID.setOpaque(false);
        writeID.setBorder(null);
        writeID.setFont(new Font("微软雅黑", Font.BOLD, 22));
        writeID.setForeground(Color.white);


        JLabel statusLabel2 = new JLabel("");
        Icon icon32 = new ImageIcon("src/xyz/view/pic/text.png");
        statusLabel2.setBounds(285, 425, 200, 60);
        statusLabel2.setIcon(icon32);
        statusLabel2.setOpaque(false);
        add(statusLabel2);

        JLabel password = new JLabel();
        password.setText("密码：");
        password.setFont(tFont);
        password.setPreferredSize(d2);
        password.setForeground(new Color(120, 70, 8));
        password.setBounds(190, 475, 100, 100);
        getContentPane().add(password);

        writePassword = new JPasswordField();
        writePassword.setPreferredSize(d3);
        add(writePassword);
        writePassword.setPreferredSize(d3);
        add(writePassword);
        writePassword.setBounds(305, 507, 180, 40);
        writePassword.setOpaque(false);
        writePassword.setBorder(null);
        writePassword.setFont(new Font("微软雅黑", Font.BOLD, 22));
        writeID.setForeground(Color.white);


        JLabel statusLabel3 = new JLabel("");
        Icon icon33 = new ImageIcon("src/xyz/view/pic\\text.png");
        statusLabel3.setBounds(285, 500, 200, 60);
        statusLabel3.setIcon(icon33);
        statusLabel3.setOpaque(false);
        add(statusLabel3);

        JLabel background = new JLabel();
        background.setLocation(0, 0);
        Icon iconBack = new ImageIcon("src/xyz/view/pic/background_副本.jpg");
        background.setIcon(iconBack);
        background.setOpaque(false);
        background.setSize(700, 700);


        //button 开始游戏↓
        Icon iconOpen = new ImageIcon("src/xyz/view/pic/open.png");
        Icon iconsign = new ImageIcon("src/xyz/view/pic/注册.png");
        JButton buttonOpen = new JButton();
        JButton buttonsign = new JButton();
        buttonOpen.setIcon(iconOpen);
        buttonsign.setIcon(iconsign);
        buttonOpen.setHideActionText(true);
        buttonsign.setHideActionText(true);
        buttonOpen.setBorderPainted(false);
        buttonsign.setBorderPainted(false);
        buttonOpen.setOpaque(false);
        buttonsign.setOpaque(false);
        MusicPlayer musicPlayer = new MusicPlayer();
        musicPlayer.musicIni();
        MusicPlayer.aau5.loop();
        buttonOpen.addActionListener((e) -> check());
        buttonsign.addActionListener((e) -> signUp());
        buttonOpen.setLocation(420, 80);
        buttonsign.setLocation(10, 145);
        buttonOpen.setBackground(Color.WHITE);
        buttonsign.setBackground(Color.WHITE);
        buttonOpen.setSize(250, 225);
        buttonsign.setSize(150, 142);
        buttonOpen.setVisible(true);
        buttonsign.setVisible(true);
        add(buttonOpen);
        add(buttonsign);
        add(background);


        DatabaseUtil database = new DatabaseUtil();
    }

    public void check() {
        if (DatabaseUtil.register(writeID.getText(), writePassword.getText()))
            ButtonBegin();
    }

    public void signUp() {
        JFrame signup = new JFrame();
        signup.setVisible(true);
        signup.setTitle("Sign Up");
        signup.setResizable(false);
        signup.setLocation(500, 250);
        signup.setDefaultCloseOperation(HIDE_ON_CLOSE);
        signup.setSize(500, 400);

        JLabel back = new JLabel();
        back.setLocation(500, 250);
        Icon temp = new ImageIcon("src/xyz/view/pic/注册背景_副本.jpg");
        back.setIcon(temp);
        back.setOpaque(false);
        back.setSize(500, 400);

        Dimension d2 = new Dimension(50, 50);
        Dimension d3 = new Dimension(250, 30);
        Font tFont = new Font("微软雅黑", Font.BOLD, 30);

        JLabel name = new JLabel();
        signup.add(name);
        name.setText("昵称：");
        name.setFont(tFont);
        name.setPreferredSize(d2);
        name.setBounds(80, 30, 100, 100);
        name.setForeground(new Color(120, 70, 8));
        signup.add(name);


        JTextField writeNa = new JTextField();
        writeNa.setPreferredSize(d3);

        writeNa.setBounds(240, 55, 180, 40);
        writeNa.setOpaque(false);
        writeNa.setBorder(null);
        writeNa.setFont(new Font("微软雅黑", Font.BOLD, 22));
        writeNa.setForeground(new Color(120, 70, 8));
        signup.add(writeNa);

        JLabel statusLabel1 = new JLabel("");
        Icon icon31 = new ImageIcon("src/xyz/view/pic/text.png");
        statusLabel1.setBounds(230, 53, 200, 60);
        statusLabel1.setIcon(icon31);
        statusLabel1.setOpaque(false);
        signup.add(statusLabel1);


        JLabel ID = new JLabel();
        signup.add(ID);
        ID.setText("账号：");
        ID.setFont(tFont);
        ID.setPreferredSize(d2);
        ID.setBounds(80, 100, 100, 100);
        ID.setForeground(new Color(120, 70, 8));
        signup.add(ID);


        JTextField writeid = new JTextField();
        writeid.setPreferredSize(d3);

        writeid.setBounds(240, 125, 180, 40);
        writeid.setOpaque(false);
        writeid.setBorder(null);
        writeid.setFont(new Font("微软雅黑", Font.BOLD, 22));
        writeid.setForeground(Color.white);
        signup.add(writeid);

        JLabel statusLabel2 = new JLabel("");
        Icon icon32 = new ImageIcon("src/xyz/view/pic/text.png");
        statusLabel2.setBounds(230, 123, 200, 60);
        statusLabel2.setIcon(icon32);
        statusLabel2.setOpaque(false);
        signup.add(statusLabel2);

        JLabel password = new JLabel();
        password.setText("密码：");
        password.setFont(tFont);
        password.setPreferredSize(d2);
        password.setForeground(new Color(120, 70, 8));
        password.setBounds(80, 170, 100, 100);
        signup.add(password);

        JPasswordField writePass = new JPasswordField();
        writePass.setPreferredSize(d3);
        writePass.setBounds(240, 195, 180, 40);
        writePass.setOpaque(false);
        writePass.setBorder(null);
        writePass.setFont(new Font("微软雅黑", Font.BOLD, 22));
        writePass.setForeground(Color.white);
        signup.add(writePass);

        JLabel statusLabel3 = new JLabel("");
        Icon icon33 = new ImageIcon("src/xyz/view/pic\\text.png");
        statusLabel3.setBounds(230, 193, 200, 60);
        statusLabel3.setIcon(icon33);
        statusLabel3.setOpaque(false);
        signup.add(statusLabel3);

        Icon iconsure = new ImageIcon("src/xyz/view/pic/确定.png");
        JButton iconsu = new JButton();
        iconsu.setIcon(iconsure);
        iconsu.setHideActionText(true);
        iconsu.setBorderPainted(false);
        iconsu.setOpaque(false);
        iconsu.addActionListener((e) -> successSign(signup, writeNa.getText(), writeid.getText(), writePass.getText()));
        iconsu.setLocation(175, 250);
        iconsu.setBackground(Color.WHITE);
        iconsu.setSize(120, 120);
        iconsu.setVisible(true);
        signup.add(iconsu);

        signup.add(back);
    }

    public void ButtonBegin() {

        choiceFrame = new JFrame();
        MusicPlayer.aau5.stop();
        MusicPlayer.aau6.play();
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Next...");
        Menu menu1 = new Menu("Help");
        menuBar.add(menu);
        menuBar.add(menu1);
        choiceFrame.setMenuBar(menuBar);
        MenuItem item1 = new MenuItem("Return");
        MenuItem item11 = new MenuItem("Help");
        item1.addActionListener((e) -> MenuFunction.setBefore(2));
        menu.add(item1);
        menu1.add(item11);


        choiceFrame.setTitle("Model choosing");
        JLabel temp = new JLabel();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        Dimension frameSize = choiceFrame.getSize();

        if (frameSize.height > screenSize.height)
            frameSize.height = screenSize.height;
        if (frameSize.width > screenSize.width)
            frameSize.width = screenSize.width;
        choiceFrame.setSize(415, 330);
        temp.setSize(415, 330);
        choiceFrame.add(temp);
        choiceFrame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        choiceFrame.setLocationRelativeTo(this);
        Icon iconBack = new ImageIcon("src/xyz/view/pic/选择模式.png");
        temp.setIcon(iconBack);
        temp.setOpaque(false);


        Icon choiceOne = new ImageIcon("src/xyz/view/pic/单机模式button.png");
        Icon choiceMany = new ImageIcon("src/xyz/view/pic/多人模式button.png");
        Icon choiceConnection = new ImageIcon("src/xyz/view/pic/联机模式button.png");
        Icon choiceComputer = new ImageIcon("src/xyz/view/pic/人机模式button.png");

        JButton choiceOneB = new JButton();
        JButton choiceManyB = new JButton();
        JButton choiceConnectionB = new JButton();
        JButton choiceComputerB = new JButton();
        BeginFrame.ChoiceButton(choiceOneB, choiceOne, 30, 10, model1);
        BeginFrame.ChoiceButton(choiceManyB, choiceMany, 210, 10, model2);
        BeginFrame.ChoiceButton(choiceComputerB, choiceComputer, 30, 130, model3);
        BeginFrame.ChoiceButton(choiceConnectionB, choiceConnection, 210, 130, model4);
        choiceFrame.setResizable(false);
        choiceFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(false);

        choiceFrame.add(choiceOneB);
        choiceFrame.add(choiceManyB);
        choiceFrame.add(choiceComputerB);
        choiceFrame.add(choiceConnectionB);
        choiceFrame.add(temp);
        choiceFrame.setVisible(true);
        System.err.println(DatabaseUtil.player.detail());
    }


    public static void ChoiceButton(JButton choiceB, Icon choice, int x, int y, int model) {
        choiceB.setIcon(choice);
        choiceB.setHideActionText(true);
        choiceB.setBorderPainted(false);
        choiceB.setOpaque(false);
        choiceB.setLocation(x, y);
        choiceB.setBackground(Color.WHITE);
        choiceB.setSize(160, 113);
        choiceB.setVisible(true);
        choiceB.addActionListener((e) -> Type(model));
    }

    public static int mode;
    public static String idSent = "";

    public static void Type(int model) {
//        if (model == 1)
//            Thread(new VideoPlayer(1)).start();
//        else if (model == 2)
//            Thread(new VideoPlayer(2)).start();
//        else if (model == 3)
//            Thread(new VideoPlayer(3)).start();
//        else if (model == 4)
//            new VideoPlayer(4);
        mode = model;
        TypeFrame.TypeChoice(choiceFrame, model);

    }

    public void successSign(JFrame sign, String userName, String userid, String userPassword) {
        int temp = 0;
        if (userid.equals("")) {
            Object[] options = {"OK "};
            JOptionPane.showOptionDialog(null, "You haven't typed your name! ", "Prompt", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, options, options[0]);
        } else temp++;

        if (userPassword.equals("")) {
            Object[] options = {"OK "};
            JOptionPane.showOptionDialog(null, "You haven't typed your password! ", "Prompt", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, options, options[0]);

        } else temp++;

        if (userName.equals("")) {
            Object[] options = {"OK "};
            JOptionPane.showOptionDialog(null, "You haven't typed your name! ", "Prompt", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, options, options[0]);
        } else temp++;

        if (temp == 3) {
            sign.setVisible(false);
            idSent = userid;
            DatabaseUtil.signUp(userid, userPassword, userName);
        }
    }

}
