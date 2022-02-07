package xyz.view;

import xyz.controller.GameController;
import xyz.model.Board;
import xyz.database.*;
import xyz.server.NetTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Logger;

public class DifficultyDecision {
    public static int model;
    public static Button b1;
    public static int ro = 0;
    public static int col = 0;
    public static int roll = 0;
    public static int coll = 0;
    public static JLabel player1b = new JLabel();
    public static JLabel player2b = new JLabel();
    public static JLabel player3b = new JLabel();
    public static JLabel player4b = new JLabel();
    public static JLabel computerb = new JLabel();
    public static JLabel player1d = new JLabel();
    public static JLabel player2d = new JLabel();
    public static JLabel player3d = new JLabel();
    public static JLabel player4d = new JLabel();
    public static JLabel computerd = new JLabel();
    public static int AItype = 1;
    public static JLabel nextRound = new JLabel("NEXT ROUND!");
    public static boolean isNew = true;
    public static boolean isReturn = true;

    public static Icon c1 = new ImageIcon("src/xyz/view/pic/colordididi.png");
    public static Icon c2 = new ImageIcon("src/xyz/view/pic/colordididi1.jpg");
    public static Icon c3 = new ImageIcon("src/xyz/view/pic/colordididi2.jpg");
    public static Icon c4 = new ImageIcon("src/xyz/view/pic/colordididi3.jpg");
    public static Icon c5 = new ImageIcon("src/xyz/view/pic/colordididi4.jpg");
    public static Icon d1 = new ImageIcon("src/xyz/view/pic/colorr.png");
    public static Icon d2 = new ImageIcon("src/xyz/view/pic/colorr1.jpg");
    public static Icon d3 = new ImageIcon("src/xyz/view/pic/colorr2.jpg");
    public static Icon d4 = new ImageIcon("src/xyz/view/pic/colorr3.jpg");
    public static Icon d5 = new ImageIcon("src/xyz/view/pic/colorr4.jpg");

    public static Icon nowd = new ImageIcon("src/xyz/view/pic/colorr.png");
    public static Icon nowc = new ImageIcon("src/xyz/view/pic/colordididi.png");


    public static int getModel() {
        return model;
    }

    public static void setModel(int model) {
        DifficultyDecision.model = model;
    }

    public static void easyModel(int model) {
        MusicPlayer.aau6.stop();
        new Thread() {
            @Override
            public void run() {

            }
        }.start();
        MusicPlayer.aau8.loop();
        TypeFrame.typeFrame();
        setLevel(9, 9, Board.getNUMBER_0F_MIND_LOW_LEVEL(), 600, 600, model);
        ItemComponent.setFontSize(80);
        ItemComponent.setFontX(18);
        ItemComponent.setFontY(55);
        setModel(model);
    }

    public static void middleModel(int model) {
        MusicPlayer.aau6.stop();
        MusicPlayer.aau8.play();
        TypeFrame.typeFrame();
        setLevel(16, 16, Board.getNUMBER_0F_MIND_MEDIUM_LEVEL(), 600, 600, model);
        ItemComponent.setFontSize(45);
        ItemComponent.setFontX(18);
        ItemComponent.setFontY(25);
        setModel(model);
    }

    public static void yourselfModel(int model) {
        MusicPlayer.aau6.stop();
        MusicPlayer.aau8.play();
        setModel(model);
        if (model == 4) {
            Object[] options = {"OK "};
            JOptionPane.showOptionDialog(null, "This model doesn't support it! ", "Prompt", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, options, options[0]);
        } else {
            InputBox temp = new InputBox();
            temp.createGUI(model);
            TypeFrame.typeFrame();
            ItemComponent.setFontSize(45);
            ItemComponent.setFontX(18);
            ItemComponent.setFontY(25);
        }
    }

    public static void difficultModel(int model) {
        if (model==4){
            Object[] options = {"OK "};
            JOptionPane.showOptionDialog(null, "It is not open! ", "Prompt", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, options, options[0]);
        }else {
            MusicPlayer.aau6.stop();
            MusicPlayer.aau8.play();
            setModel(model);
            TypeFrame.typeFrame();
            setLevel(30, 16, 99, 1000, 500, model);
            ItemComponent.setFontSize(60);
            ItemComponent.setFontX(18);
            ItemComponent.setFontY(25);
        }
    }


    public static Tools tools;
    public static GameFrame gameFrame;
    public static Function.Toushi toushi;
    public static Function.Zhadan zhadan;
    public static Function.addOpenPoint add1;
    public static Function.addTwoPoint add2;
    public static Function.decreaseOnePoint dec1;
    public static Function.decreaseTwoPoint dec2;
    public static TimeThread timer;
    public static JLabel timeTable;
    public static JLabel gameOver = gameover();
    public static JLabel gameOver1 = gameover1();
    public static boolean gameOverIsTurnUp = false;
    public static boolean gameOverIsTurnUp1 = false;


    protected static void setLevel(int row, int col, int mindNum, int rowle, int colle, int model) {


        SwingUtilities.invokeLater(() -> {
            ro = row;
            DifficultyDecision.col = col;
            coll = colle;
            roll = rowle;
            ScoreBoard scoreBoard = new ScoreBoard();
            HelpBoard helpBoard = new HelpBoard();
            WinnerComponent winnerComponent = new WinnerComponent();
            BoardComponent boardComponent = new BoardComponent(row, col, rowle, colle);
            Board board = new Board(row, col);
            board.setNumberOfMind(mindNum);
            GameController gameController = new GameController(boardComponent, board, scoreBoard, winnerComponent);
            Save.saver(gameController, gameController.getView1(), gameController.getView2(), gameController.getModel(), gameController.getCurrentPlayer());


            if (model != 4) {
                if ((col == 16 && row == 30)) {
                    gameFrame = new GameFrame("difficult");
                    boardComponent.setLocation(30, 15);
                } else
                    gameFrame = new GameFrame();
            } else
                gameFrame = new PVPGameFrame();

            gameFrame.setVisible(true);


            //菜单栏
            MenuBar menuBar = new MenuBar();
            Menu menu = new Menu("Next...");
            Menu menu1 = new Menu("Help");
            menuBar.add(menu);
            menuBar.add(menu1);
            gameFrame.setMenuBar(menuBar);

            MenuItem item1 = new MenuItem("Return");
            MenuItem item2 = new MenuItem("New");
            MenuItem item3 = new MenuItem("Save");
            MenuItem item4 = new MenuItem("Load");
            MenuItem item5 = new MenuItem("Reset");
            MenuItem item6 = new MenuItem("Playback");
            MenuItem item11 = new MenuItem("Help");
            MenuItem item12 = new MenuItem("Ranking List");
            Menu item13 = new Menu("AI Type");
            MenuItem item15 = new MenuItem("Detailed Information");
            MenuItem item131 = new MenuItem("Easy");
            MenuItem item132 = new MenuItem("Middle");
            MenuItem item133 = new MenuItem("Difficult");
            Menu item14 = new Menu("Background Change");

            MenuItem item231 = new MenuItem("Dreamy forest");
            MenuItem item232 = new MenuItem("Pleasant trees");
            MenuItem item233 = new MenuItem("The dark cellar");
            MenuItem item234 = new MenuItem("A magical wonderland");
            MenuItem item235 = new MenuItem("Dark forest");

            menu1.add(item15);

            menu1.add(item14);
            item14.add(item231);
            item14.add(item232);
            item14.add(item233);
            item14.add(item234);
            item14.add(item235);


            if (model == 3) {
                menu1.add(item13);
                item13.add(item131);
                item13.add(item132);
                item13.add(item133);
                item131.addActionListener((e) -> {
                    AItype = 1;
                });
                item132.addActionListener((e) -> {
                    AItype = 2;
                });
                item133.addActionListener((e) -> {
                    AItype = 3;
                });
            }


            item1.addActionListener((e) -> {
                gameController.cnt=0;
                GameController.playback = new ArrayList<Integer>();
                if (DifficultyDecision.model == 4) {
                    Object[] options = {"OK "};
                    JOptionPane.showOptionDialog(null, "This model doesn't support it! ", "Prompt", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                } else {
                    Function.alwaysUse = false;
                    gameFrame.remove(b1);
                    scoreBoard.setVisible(false);
                    helpBoard.setVisible(false);
                    winnerComponent.setVisible(false);
                    Save.gameController.setCurrentPlayer(1);
                    isNew = false;
                    isReturn = false;
                    MenuFunction.setBefore(4);
                }
            });

            item15.addActionListener(e -> new DetailInformation());

            item2.addActionListener((e) -> {
                gameController.cnt=0;
                GameController.playback = new ArrayList<Integer>();
                if (DifficultyDecision.model == 4) {
                    Object[] options = {"OK "};
                    JOptionPane.showOptionDialog(null, "This model doesn't support it! ", "Prompt", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                }
                gameOverIsTurnUp = false;
                gameOverIsTurnUp1 = false;
                DifficultyDecision.gameOver.setVisible(false);
                DifficultyDecision.gameOver1.setVisible(false);
                helpBoard.setVisible(false);
                gameFrame.setVisible(false);
                GameController.counterrr = 0;
                GameController.counterr = 0;
                GameController.coun1 = 0;
                GameController.coun2 = 0;
                GameController.coun3 = 0;
                GameController.coun4 = 0;
                isNew = false;
                isReturn = false;
                if (mindNum == Board.getNUMBER_0F_MIND_LOW_LEVEL()) {
                    Save.gameController.setCurrentPlayer(1);
                    scoreBoard.setVisible(false);
                    winnerComponent.setVisible(false);
                    setLevel(9, 9, Board.getNUMBER_0F_MIND_LOW_LEVEL(), 600, 600, model);
                    Save.gameController.first = true;
                    Function.alwaysUse = false;
                    AItype = 1;

                } else if (mindNum == Board.getNUMBER_0F_MIND_MEDIUM_LEVEL()) {
                    Save.gameController.setCurrentPlayer(1);
                    scoreBoard.setVisible(false);
                    winnerComponent.setVisible(false);
                    gameFrame.remove(b1);
                    setLevel(16, 16, Board.getNUMBER_0F_MIND_MEDIUM_LEVEL(), 600, 600, model);
                    Save.gameController.first = true;
                    Function.alwaysUse = false;
                    AItype = 1;

                } else {
                    Save.gameController.setCurrentPlayer(1);
                    scoreBoard.setVisible(false);
                    winnerComponent.setVisible(false);
                    gameFrame.remove(b1);
                    setLevel(row, col, mindNum, rowle, colle, model);
                    Save.gameController.first = true;
                    Function.alwaysUse = false;
                    AItype = 1;
                }
            });

            item3.addActionListener((e) -> {
                if (DifficultyDecision.model == 4) {
                    Object[] options = {"OK "};
                    JOptionPane.showOptionDialog(null, "This model doesn't support it! ", "Prompt", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                }

                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setCurrentDirectory(new File(""));
                int result = jFileChooser.showSaveDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = jFileChooser.getSelectedFile();
                    try {
                        Save.output(file);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });

            item4.addActionListener((e) -> {
                if (DifficultyDecision.model == 4) {
                    Object[] options = {"OK "};
                    JOptionPane.showOptionDialog(null, "This model doesn't support it! ", "Prompt", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                }
                JFileChooser fd = new JFileChooser();
                fd.showOpenDialog(null);
                File f = fd.getSelectedFile();
                isNew = false;
                isReturn = false;
                if (f != null) {
                    try {
                        gameController.initialGameState(f);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
            if (model == 2 || model == 3) {
                if ((col == 16 && row == 30)) {
                    JLabel daojishi = new JLabel();
                    daojishi.setText("倒计时：");
//                    Icon timepic = new ImageIcon("src/xyz/view/pic/倒计时.png");
                    daojishi.setFont(new Font("微软雅黑", Font.BOLD, 40));
                    daojishi.setForeground(new Color(255, 255, 255));
                    daojishi.setOpaque(false);
                    daojishi.setBounds(30, 600, 180, 120);
                    timeTable = new JLabel();
                    timer = new TimeThread(timeTable);
                    timeTable.setFont(new Font("宋体", Font.BOLD, 30));
                    timeTable.setBounds(190, 640, 500, 50);
                    timeTable.setForeground(Color.white);
                    gameFrame.add(timeTable);
                    gameFrame.add(daojishi);
                    daojishi.setVisible(true);
                    timeTable.setVisible(true);
                    timer.start();
                } else {
                    JLabel daojishi = new JLabel();
                    daojishi.setText("倒计时：");
                    daojishi.setFont(new Font("微软雅黑", Font.BOLD, 40));
                    daojishi.setForeground(new Color(255, 255, 255));
                    daojishi.setOpaque(false);
                    daojishi.setBounds(30, 730, 180, 120);

                    timeTable = new JLabel();
                    timer = new TimeThread(timeTable);
                    timeTable.setFont(new Font("宋体", Font.BOLD, 30));
                    timeTable.setForeground(Color.white);
                    timeTable.setBounds(190, 770, 500, 50);
                    gameFrame.add(timeTable);
                    gameFrame.add(daojishi);
                    timeTable.setVisible(true);
                    timer.start();
                }
            }

            item5.addActionListener((e -> {
                for (int i = 0; i < Save.model.getRow(); i++) {
                    for (int i1 = 0; i1 < Save.model.getColumn(); i1++) {
                        Save.view1.getGridAt(Save.model.getGrid()[i][i1].getLocation()).setBorder(BorderFactory.createEmptyBorder());
                    }
                }
                File file = new File("src\\xyz\\view\\test\\reset.txt");
                try {
                    gameController.initialGameState(file);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }));

            item11.addActionListener((e -> {
                helpBoard.setVisible(true);
                helpBoard.repaint();
            }));

            item6.addActionListener((e -> {
                File file = new File("src\\xyz\\view\\test\\reset.txt");
                try {
                    gameController.initialGameState(file);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                new Thread(()->{ for (int i = 0; i < GameController.playback.size(); i++) {
                    File file1 = new File(String.format("src\\xyz\\view\\playback\\play%d.txt", GameController.playback.get(i)));
                    try {
                        gameController.initialGameState(file1);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                }}).start();

            }));

            item12.addActionListener((e -> {
                if (model == 1) {
                    switch (Save.model.getNumberOfMind()) {
                        case 10:
                            RankList rankList = new RankList("easy");
                            break;
                        case 40:
                            RankList rankList1 = new RankList("middle");
                            break;
                        case 99:
                            RankList rankList2 = new RankList("difficult");
                            break;
                    }
                } else {
                    RankList rankList = new RankList("players,haha");
                }

            }));

            menu.add(item1);
            menu.add(item3);
            menu.add(item4);
            menu.add(item2);
            menu.add(item6);
            if (model == 1)
                menu.add(item5);
            menu1.add(item11);
            menu1.add(item12);

            b1 = new Button();
            if (model == 1 || model == 3) {
                gameFrame.add(b1);
                b1.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        int keyCode = e.getKeyCode();
//                        Logger.getGlobal().warning(String.valueOf(keyCode));
                        if (keyCode == KeyEvent.VK_C && !Function.alwaysUse) {
                            Function.alwaysUse = true;
                            Object[] options = {"OK "};
                            JOptionPane.showOptionDialog(null, "You opened the cheating mode!", "Prompt", JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.WARNING_MESSAGE, null, options, options[0]);

                        } else if (keyCode == KeyEvent.VK_C) {
                            Function.alwaysUse = false;
                            Function.Zhadan.open = false;
                            Object[] options = {"OK "};
                            JOptionPane.showOptionDialog(null, "You closed the cheating mode!", "Prompt", JOptionPane.DEFAULT_OPTION,
                                    JOptionPane.WARNING_MESSAGE, null, options, options[0]);

                        }
                    }
                });
            }

            if ((model == 2 || model == 4) && isNew && isReturn) {
                GIF location = new GIF();
                tools = new Tools(location);
                location.tools = tools;
                gameFrame.add(tools);
                gameFrame.add(GIF.donttai);
            }
//            if ((model == 2 || model == 4) && !isNew && !isReturn) {
//                GIF location = new GIF();
//                tools = new Tools(location);
//                location.tools = tools;
//                gameFrame.add(tools);
//                gameFrame.add(GIF.donttai);
//            }

            JLabel hb = new JLabel();
            Icon helpb = new ImageIcon("src/xyz/view/pic/help.png");
            hb.setIcon(helpb);
            hb.setVisible(true);
            hb.setOpaque(false);
            hb.setBounds(0, 0, 400, 600);

            JLabel sbbg1 = new JLabel();
            Icon bg1 = new ImageIcon("src/xyz/view/pic/计分板背景1.png");
            sbbg1.setIcon(bg1);
            sbbg1.setVisible(true);
            sbbg1.setOpaque(false);
            sbbg1.setBounds(0, 0, 400, 240 + 80);

            JLabel sbbg2 = new JLabel();
            Icon bg2 = new ImageIcon("src/xyz/view/pic/计分板背景2.png");
            sbbg2.setIcon(bg2);
            sbbg2.setVisible(true);
            sbbg2.setOpaque(false);
            sbbg2.setBounds(0, 0, 400, 240 * 2 + 80);

            JLabel sbbg3 = new JLabel();
            Icon bg3 = new ImageIcon("src/xyz/view/pic/计分板背景3.png");
            sbbg3.setIcon(bg3);
            sbbg3.setVisible(true);
            sbbg3.setOpaque(false);
            sbbg3.setBounds(0, 0, 400, 240 * 3 + 80);

            JLabel sbbg4 = new JLabel();
            Icon bg4 = new ImageIcon("src/xyz/view/pic/计分板背景4.png");
            sbbg4.setIcon(bg4);
            sbbg4.setVisible(true);
            sbbg4.setOpaque(false);
            sbbg4.setBounds(0, 0, 400, 240 * 4 + 80);

            Icon p1b = new ImageIcon("src/xyz/view/pic/soldier1bright.png");
            player1b.setIcon(p1b);
            player1b.setVisible(true);
            player1b.setOpaque(false);
            if (Save.model.getNumberOfMind() != 99)
                player1b.setBounds(825, 30, 150, 150);
            else
                player1b.setBounds(1250, 30, 150, 150);

            Icon p2b = new ImageIcon("src/xyz/view/pic/soldier2bright.png");
            player2b.setIcon(p2b);
            player2b.setVisible(false);
            player2b.setOpaque(false);
            if (Save.model.getNumberOfMind() != 99)
                player2b.setBounds(825, 200, 150, 150);
            else
                player2b.setBounds(1250, 200, 150, 150);

            Icon p3b = new ImageIcon("src/xyz/view/pic/soldier3bright.png");
            player3b.setIcon(p3b);
            player3b.setVisible(false);
            player3b.setOpaque(false);
            if (Save.model.getNumberOfMind() != 99)
                player3b.setBounds(825, 370, 150, 150);
            else
                player3b.setBounds(1250, 370, 150, 150);

            Icon p4b = new ImageIcon("src/xyz/view/pic/soldier4bright.png");
            player4b.setIcon(p4b);
            player4b.setVisible(false);
            player4b.setOpaque(false);
            if (Save.model.getNumberOfMind() != 99)
                player4b.setBounds(825, 540, 150, 150);
            else
                player4b.setBounds(1250, 540, 150, 150);

            Icon cpb = new ImageIcon("src/xyz/view/pic/computerbright.png");
            computerb.setIcon(cpb);
            computerb.setVisible(false);
            computerb.setOpaque(false);
            if (Save.model.getNumberOfMind() != 99)
                computerb.setBounds(825, 370, 150, 150);
            else
                computerb.setBounds(1250, 370, 150, 150);

            Icon p1d = new ImageIcon("src/xyz/view/pic/soldier1dark.png");
            player1d.setIcon(p1d);
            player1d.setVisible(false);
            player1d.setOpaque(false);
            if (Save.model.getNumberOfMind() != 99)
                player1d.setBounds(825, 30, 150, 150);
            else
                player1d.setBounds(1250, 30, 150, 150);

            Icon p2d = new ImageIcon("src/xyz/view/pic/soldier2dark.png");
            player2d.setIcon(p2d);
            player2d.setVisible(false);
            player2d.setOpaque(false);
            if (Save.model.getNumberOfMind() != 99)
                player2d.setBounds(825, 200, 150, 150);
            else
                player2d.setBounds(1250, 200, 150, 150);

            Icon p3d = new ImageIcon("src/xyz/view/pic/soldier3dark.png");
            player3d.setIcon(p3d);
            player3d.setVisible(false);
            player3d.setOpaque(false);
            if (Save.model.getNumberOfMind() != 99)
                player3d.setBounds(825, 370, 150, 150);
            else
                player3d.setBounds(1250, 370, 150, 150);

            Icon p4d = new ImageIcon("src/xyz/view/pic/soldier4dark.png");
            player4d.setIcon(p4d);
            player4d.setVisible(false);
            player4d.setOpaque(false);
            if (Save.model.getNumberOfMind() != 99)
                player4d.setBounds(825, 540, 150, 150);
            else
                player4d.setBounds(1250, 540, 150, 150);

            Icon cpd = new ImageIcon("src/xyz/view/pic/computerdark.png");
            computerd.setIcon(cpd);
            computerd.setVisible(true);
            computerd.setOpaque(false);
            if (Save.model.getNumberOfMind() != 99)
                computerd.setBounds(825, 370, 150, 150);
            else
                computerd.setBounds(1250, 370, 150, 150);

            //切换回合：
            nextRound.setFont(new Font("Monotype Corsiva", Font.BOLD, 72));
            nextRound.setForeground(new Color(23, 204, 227));
            gameFrame.add(nextRound);
            nextRound.setVisible(false);
            if (Save.model.getNumberOfMind() == 99)
                nextRound.setBounds(240, -140, 1500, 1000);
            else nextRound.setBounds(120, -150, 1150, 1000);


            //透视工具↓
            toushi = new Function.Toushi();
            gameFrame.add(toushi);
            toushi.setVisible(true);
            Function.Toushi.canUse = false;
            if ((col == 16 && row == 30))
                toushi.setBounds(1080, 550, 90, 90);
            else
                toushi.setBounds(700, 610, 90, 90);
            //炸弹工具↓
            zhadan = new Function.Zhadan();
            gameFrame.add(zhadan);
            zhadan.setVisible(true);
            Function.Zhadan.canUse = false;
            Function.Zhadan.open = false;
            if ((col == 16 && row == 30))
                zhadan.setBounds(1080, 450, 90, 90);
            else
                zhadan.setBounds(700, 510, 90, 90);
            //加一分↓
            add1 = new Function.addOpenPoint();
            gameFrame.add(add1);
            add1.setVisible(true);
            Function.addOpenPoint.canUse = false;
            if ((col == 16 && row == 30))
                add1.setBounds(1080, 50, 90, 90);
            else
                add1.setBounds(700, 110, 90, 90);
            //加2分↓
            add2 = new Function.addTwoPoint();
            gameFrame.add(add2);
            add2.setVisible(true);
            Function.addTwoPoint.canUse = false;
            if ((col == 16 && row == 30))
                add2.setBounds(1080, 150, 90, 90);
            else
                add2.setBounds(700, 210, 90, 90);
            //减一分↓
            dec1 = new Function.decreaseOnePoint();
            gameFrame.add(dec1);
            dec1.setVisible(true);
            Function.decreaseOnePoint.canUse = false;
            if ((col == 16 && row == 30))
                dec1.setBounds(1080, 250, 90, 90);
            else
                dec1.setBounds(700, 310, 90, 90);
            //减2分↓
            dec2 = new Function.decreaseTwoPoint();
            gameFrame.add(dec2);
            dec2.setVisible(true);
            Function.decreaseTwoPoint.canUse = false;
            if ((col == 16 && row == 30))
                dec2.setBounds(1080, 350, 90, 90);
            else
                dec2.setBounds(700, 410, 90, 90);


            if ((col == 16 && row == 30)) {
                gameFrame.add(getTimelabel("difficult"));
                if (model != 1)
                    gameFrame.add(getMinelabel("difficult"));
            } else {
                gameFrame.add(getTimelabel(""));
                if (model != 1)
                    gameFrame.add(getMinelabel(""));
            }

            if (model == 1) {
                gameFrame.add(player1d);
                gameFrame.add(player1b);
            } else if (ScoreBoard.getPerson() == 2 && model != 3 && model != 4) {
                gameFrame.add(player1d);
                gameFrame.add(player1b);
                gameFrame.add(player2d);
                gameFrame.add(player2b);
            } else if (ScoreBoard.getPerson() == 2 && model == 3) {
                gameFrame.add(player1d);
                gameFrame.add(player1b);
                gameFrame.add(computerd);
                gameFrame.add(computerb);
            } else if (ScoreBoard.getPerson() == 2 && model == 4) {
                gameFrame.add(player1d);
                gameFrame.add(player1b);
                gameFrame.add(player4d);
                gameFrame.add(player4b);
            } else if (ScoreBoard.getPerson() == 3) {
                gameFrame.add(player1d);
                gameFrame.add(player1b);
                gameFrame.add(player2d);
                gameFrame.add(player2b);
                gameFrame.add(player3d);
                gameFrame.add(player3b);
            } else if (ScoreBoard.getPerson() == 4) {
                gameFrame.add(player1d);
                gameFrame.add(player1b);
                gameFrame.add(player2d);
                gameFrame.add(player2b);
                gameFrame.add(player3d);
                gameFrame.add(player3b);
                gameFrame.add(player4d);
                gameFrame.add(player4b);
            }

            gameFrame.add(gameOver);
            gameFrame.add(gameOver1);
            helpBoard.add(hb);

            JLabel tem = new JLabel();
            JLabel tem1 = new JLabel();
            if ((col == 16 && row == 16) || (col == 9 && row == 9)) {
                JLabel bk = new JLabel();
                Icon iconbk = new ImageIcon("src/xyz/view/pic/backboaard.png");
                bk.setIcon(iconbk);
                bk.setOpaque(false);
                bk.add(boardComponent);
                boardComponent.setSize(600, 600);
                boardComponent.setLocation(32, 28);
                gameFrame.add(bk);
                bk.setSize(650, 650);
                bk.setLocation(20, 100);
                tem.setIcon(nowd);
                tem.setVisible(true);
                tem.setOpaque(false);
                tem.setBounds(0, 0, 1050, 900);
                gameFrame.add(tem);
            } else if (!(col == 16 && row == 30)) {
                gameFrame.add(boardComponent);
                boardComponent.setLocation(40, 100);
                tem.setIcon(nowd);
                tem.setVisible(true);
                tem.setOpaque(false);
                tem.setBounds(0, 0, 1050, 900);
                gameFrame.add(tem);
            } else {
                JLabel bk = new JLabel();
                Icon iconbk = new ImageIcon("src/xyz/view/pic/backboard.png");
                bk.setIcon(iconbk);
                bk.setOpaque(false);
                bk.add(boardComponent);
                boardComponent.setSize(1010, 500);
                boardComponent.setLocation(35, 25);
                gameFrame.add(bk);
                bk.setSize(1020, 550);
                bk.setLocation(20, 75);
                tem1.setIcon(nowc);
                tem1.setVisible(true);
                tem1.setOpaque(false);
                tem1.setBounds(0, 0, 1450, 900);
                gameFrame.add(tem1);
            }

            item231.addActionListener((e) -> {
                nowc = c1;
                nowd = d1;
                tem.setIcon(nowd);
                tem1.setIcon(nowc);
            });
            item232.addActionListener((e) -> {
                nowc = c2;
                nowd = d2;
                tem.setIcon(nowd);
                tem1.setIcon(nowc);
            });
            item233.addActionListener((e) -> {
                nowc = c3;
                nowd = d3;
                tem.setIcon(nowd);
                tem1.setIcon(nowc);
            });
            item234.addActionListener((e) -> {
                nowc = c4;
                nowd = d4;
                tem.setIcon(nowd);
                tem1.setIcon(nowc);
            });
            item235.addActionListener((e) -> {
                nowc = c5;
                nowd = d5;
                tem.setIcon(nowd);
                tem1.setIcon(nowc);
            });


            if (BeginFrame.mode != 1)
                scoreBoard.setVisible(true);

            if (InputBox.players == 1)
                scoreBoard.add(sbbg1);
            if (InputBox.players == 2)
                scoreBoard.add(sbbg2);
            if (InputBox.players == 3)
                scoreBoard.add(sbbg3);
            if (InputBox.players == 4)
                scoreBoard.add(sbbg4);
        });
    }

    static double timme;
    static double fiTime;

    public static JLabel timelabel;
    public static Timer time;

    private static JLabel getTimelabel(String x) {
        timelabel = new JLabel("");
        if (BeginFrame.mode != 4)
            timelabel.setBounds(675, 710, 200, 30);
        else
            timelabel.setBounds(200, 780, 200, 30);
        timelabel.setFont(new Font("微软雅黑", Font.BOLD, 30));
        timelabel.setForeground(new Color(243, 233, 172));
        if (x.equals("difficult"))
            timelabel.setBounds(1045, 650, 200, 30);
        time = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (!Save.gameController.first)
                    timelabel.setText(new SimpleDateFormat("mm:ss:SS").format(System.currentTimeMillis() - timme));
                else {
                    timelabel.setText("00:00:000");
                    timme = System.currentTimeMillis();
                }
            }
        });
        time.start();
        timelabel.setVisible(true);
        if (Save.view2.getFounedmine() == Save.model.getNumberOfMind())
            fiTime = System.currentTimeMillis() - timme;
        return timelabel;
    }


    private static JLabel getMinelabel(String x) {
        JLabel minelabel;
        minelabel = new JLabel("");
        minelabel.setBounds(670, 760, 400, 30);
        minelabel.setFont(new Font("微软雅黑", Font.BOLD, 30));
        minelabel.setForeground(new Color(243, 233, 172));
        if (x.equals("difficult"))
            minelabel.setBounds(760, 650, 400, 30);
        Timer time = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                minelabel.setText(String.format("已发现雷数: %d/%d", Save.view2.getFounedmine(), Save.model.getNumberOfMind()));
            }
        }
        );
        time.start();
        if (BeginFrame.mode != 1)
            minelabel.setVisible(true);
        return minelabel;
    }

    public static JLabel gameover() {
        JLabel gameov = new JLabel();
        Icon go = new ImageIcon("src/xyz/view/pic/gameover.png");
        gameov.setIcon(go);
        gameov.setOpaque(false);
        gameov.setVisible(false);

        gameov.setBounds(200, -150, 1150, 1150);
        return gameov;
    }

    public static JLabel gameover1() {
        JLabel gameov = new JLabel();
        Icon go = new ImageIcon("src/xyz/view/pic/gameover.png");
        gameov.setIcon(go);
        gameov.setOpaque(false);
        gameov.setVisible(false);
        gameov.setBounds(400, -250, 1150, 1150);

        return gameov;
    }


//    public static String id;

//    static class registerBox extends JFrame {
//        JLabel label1 = new JLabel("账号：");
//        JLabel label2 = new JLabel("密码：");
//
//        //创建JTextField，16表示16列，用于JTextField的宽度显示而不是限制字符个数
//        JTextField textField1 = new JTextField(18);
//        JTextField textField2 = new JTextField(18);
//
//        JButton button1 = new JButton("登录");

//
//        public registerBox(String title) {
//            //继承父类，
//            super(title);
//
//            //内容面板
//            Container contentPane = getContentPane();
//            contentPane.setLayout(new FlowLayout());
//            setResizable(false);
//            //添加控件
//
//            contentPane.add(label1);
//            contentPane.add(textField1);
//            contentPane.add(label2);
//            contentPane.add(textField2);
//            contentPane.add(button1);
//
//
//            button1.addActionListener((e) -> {
//                int temp = 0;
//                String userid = textField1.getText();
//                String userPassword = textField2.getText();

//                if (userid.equals("")) {
//                    Object[] options = {"OK "};
//                    JOptionPane.showOptionDialog(null, "You haven't typed your name! ", "Prompt", JOptionPane.DEFAULT_OPTION,
//                            JOptionPane.WARNING_MESSAGE, null, options, options[0]);
//                }else temp++;
//
//                if (userPassword.equals("")) {
//                    Object[] options = {"OK "};
//                    JOptionPane.showOptionDialog(null, "You haven't typed your password! ", "Prompt", JOptionPane.DEFAULT_OPTION,
//                            JOptionPane.WARNING_MESSAGE, null, options, options[0]);
////
////                }
//
//                if (DatabaseUtil.register(userid, userPassword)) {
//                    this.setVisible(false);
//                }
//            });
//        }
//
//    }

//    static class signInBox extends JFrame {
//        JLabel label2 = new JLabel("账号：");
//        JLabel label3 = new JLabel("密码：");
//        JLabel label1 = new JLabel("姓名：");
//        //创建JTextField，16表示16列，用于JTextField的宽度显示而不是限制字符个数
//        JTextField textField1 = new JTextField(18);
//        JTextField textField2 = new JTextField(18);
//        JTextField textField3 = new JTextField(18);
//
//        JButton button1 = new JButton("确认");
//
//
//        public signInBox(String title) {
//            //继承父类，
//            super(title);
//
//            //内容面板
//            Container contentPane = getContentPane();
//            contentPane.setLayout(new FlowLayout());
//            setResizable(false);
//            //添加控件
//
//            contentPane.add(label2);
//            contentPane.add(textField1);
//            contentPane.add(label3);
//            contentPane.add(textField2);
//            contentPane.add(label1);
//            contentPane.add(textField3);
//            contentPane.add(button1);
//
//            button1.addActionListener((e) -> {
//                int temp = 0;
//                String userid = textField1.getText();
//                String userPassword = textField2.getText();
//                String userName = textField3.getText();
//
//                if (userid.equals("")) {
//                    Object[] options = {"OK "};
//                    JOptionPane.showOptionDialog(null, "You haven't typed your name! ", "Prompt", JOptionPane.DEFAULT_OPTION,
//                            JOptionPane.WARNING_MESSAGE, null, options, options[0]);
//                } else temp++;
//
//                if (userPassword.equals("")) {
//                    Object[] options = {"OK "};
//                    JOptionPane.showOptionDialog(null, "You haven't typed your password! ", "Prompt", JOptionPane.DEFAULT_OPTION,
//                            JOptionPane.WARNING_MESSAGE, null, options, options[0]);
//
//                } else temp++;
//
//                if (userName.equals("")) {
//                    Object[] options = {"OK "};
//                    JOptionPane.showOptionDialog(null, "You haven't typed your name! ", "Prompt", JOptionPane.DEFAULT_OPTION,
//                            JOptionPane.WARNING_MESSAGE, null, options, options[0]);
//                } else temp++;
//
//                if (temp == 3) {
//                    setVisible(false);
//                    id = userid;
//                    DatabaseUtil.signUp(userid, userPassword, userName);
//                }
//
//            });
//        }
//    }
}
