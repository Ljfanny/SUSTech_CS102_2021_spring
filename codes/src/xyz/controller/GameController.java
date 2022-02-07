package xyz.controller;

import xyz.database.DatabaseUtil;
import xyz.database.UserInformation;
import xyz.listener.GameListener;
import xyz.model.*;
import xyz.server.*;
import xyz.view.*;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameController implements GameListener {
    private final BoardComponent view1;
    private final ScoreBoard view2;
    private final WinnerComponent view3;
    private final Board model;
    public int currentPlayer = 1;
    public boolean first = true;
    String result = null;
    public int clickable;
    public static String usertime = "";
    public static int addTime = 0;
    public static boolean isAiTurn = false;
    public int x = 0;
    public int y = 0;
    public static ArrayList<Integer> playback = new ArrayList<Integer>();
    public int cnt = 0;

    public int boxNumber = 0;


    public BoardComponent getView1() {
        return view1;
    }

    public ScoreBoard getView2() {
        return view2;
    }

    public WinnerComponent getView3() {
        return view3;
    }

    public Board getModel() {
        return model;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int a) {
        currentPlayer = a;
    }

    public GameController(BoardComponent component, Board board, ScoreBoard scoreBoard, WinnerComponent winnerComponent) {
        this.view1 = component;
        this.view2 = scoreBoard;
        this.model = board;
        view3 = winnerComponent;

        view1.registerListener(this);
        try {
            initialGameState(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void initialGameState(File file) throws IOException {
        if (file == null) {
            currentPlayer = 1;
            int num;
            BoardLocation location;
            for (int row = 0; row < model.getRow(); row++) {
                for (int col = 0; col < model.getColumn(); col++) {
                    location = new BoardLocation(row, col);
                    num = model.getNumAt(location);
                    view1.setItemAt(location, num);

                }
            }
            view1.repaint();
        } else {
            first = false;
            FileReader fr = new FileReader(file);
            BufferedReader bf = new BufferedReader(fr);
            int row = Integer.parseInt(bf.readLine());
            int column = Integer.parseInt(bf.readLine());
            int totalNum = row * column;
            ArrayList<String> list = new ArrayList<>();
            String t = null;
            while ((t = bf.readLine()) != null) {
                list.add(t);
            }
            int cnt1 = 0;
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    BoardLocation location = new BoardLocation(i, j);
                    String[] temp = list.get(cnt1).split(" ");
//                    Save.model.setNumberOfMind(Integer.parseInt(temp[3]));
                    if (temp[0].equals("true")) {
                        view1.setItemAt(location, Integer.parseInt(temp[3]));
                    }
                    if (temp[0].equals("true") && temp[1].equals("true") && temp[2].equals("true"))
                        view1.setItemAt(location, ItemUtil.flagNum);
                    if (!temp[0].equals("true"))
                        view1.setItemAt(location, -2);
                    if (cnt1 == totalNum)
                        break;
                    cnt1++;
                }
            }
            int cnt2 = 0;
            for (int i = 0; i < model.getRow(); i++) {
                for (int j = 0; j < model.getColumn(); j++) {
                    BoardLocation location = new BoardLocation(i, j);
                    String[] temp = list.get(cnt2).split(" ");
                    if (temp[0].equals("true"))
                        model.getGridAt(location).setOpened(true);
                    else
                        model.getGridAt(location).setOpened(false);
                    if (temp[1].equals("true"))
                        model.getGridAt(location).setFlag(true);
                    else
                        model.getGridAt(location).setFlag(false);
                    if (temp[2].equals("true"))
                        model.getGridAt(location).setHasLandMine(true);
                    else
                        model.getGridAt(location).setHasLandMine(false);
                    if (temp[5].equals("true"))
                        model.getGridAt(location).setClick(true);
                    else
                        model.getGridAt(location).setClick(false);
                    model.getGridAt(location).setNumberOfLandMine(Byte.parseByte(temp[3]));
                    model.getGridAt(location).setBoxNum(Integer.parseInt(temp[6]));
                    if (temp[7].equals("true"))
                        model.getGridAt(location).setBox(true);
                    else
                        model.getGridAt(location).setBox(false);
                    if (cnt2 == totalNum)
                        break;
                    cnt2++;
                }
            }
            int cnt3 = 0;
            for (int i = 0; i < ScoreBoard.getPerson(); i++) {
//                System.out.println(Integer.parseInt(list.get(totalNum + cnt3)));
                view2.setScoreBoard(0, i, Integer.parseInt(list.get(totalNum + cnt3)));
                cnt3++;
//                System.out.println(Integer.parseInt(list.get(totalNum + cnt3)));
                view2.setScoreBoard(1, i, Integer.parseInt(list.get(totalNum + cnt3)));
                cnt3++;
//                System.out.println(Integer.parseInt(list.get(totalNum + cnt3)));
                view2.setScoreBoard(2, i, Integer.parseInt(list.get(totalNum + cnt3)));
                cnt3++;
            }
            currentPlayer = Integer.parseInt(list.get(totalNum + ScoreBoard.getPerson() * 3));
//            System.out.println(Integer.parseInt(list.get(totalNum + ScoreBoard.getPerson() * 3)));
            view2.setFounedmine(Integer.parseInt(list.get(totalNum + ScoreBoard.getPerson() * 3 + 1)));
//            System.out.println((Integer.parseInt(list.get(totalNum + ScoreBoard.getPerson() * 3 + 1))));
            if (BeginFrame.mode == 2)
                InputBox.setRounds(Integer.parseInt(list.get(totalNum + ScoreBoard.getPerson() * 3 + 2)));
            view1.repaint();
            view2.repaint();
            bf.close();
            fr.close();
        }
    }

    public static int counterr = 0;
    public static int counterrr = 0;

    public void nextPlayer() {
//        System.out.println(currentPlayer);
        if (currentPlayer == ScoreBoard.getPerson()) {
            if (DifficultyDecision.model != 1 && DifficultyDecision.model != 4) {
                DifficultyDecision.timer.interrupt();
                DifficultyDecision.timer = new TimeThread(DifficultyDecision.timeTable);
                DifficultyDecision.timer.start();
            }

            if (DifficultyDecision.model == 2) {
                if (counterrr < InputBox.rounds)
                    counterrr++;
            }
            if (counterrr == InputBox.rounds) {
                currentPlayer = 1;
                counterrr = 0;
            }
            if (DifficultyDecision.model == 3 || DifficultyDecision.model == 4)
                currentPlayer = 1;
//            System.out.println(currentPlayer);
        } else {
            if (DifficultyDecision.model != 1 && DifficultyDecision.model != 4) {
                DifficultyDecision.timer.interrupt();
                DifficultyDecision.timer = new TimeThread(DifficultyDecision.timeTable);
                DifficultyDecision.timer.start();
            }

            if (DifficultyDecision.model == 2) {
                if (counterr < InputBox.rounds)
                    counterr++;
            }
            if (counterr == InputBox.rounds) {
                currentPlayer++;
                counterr = 0;
            }
            if (DifficultyDecision.model == 3 || DifficultyDecision.model == 4)
                currentPlayer++;
        }
    }


    @Override
    public void onPlayerLeftClick(BoardLocation location) throws IOException, InterruptedException {
        if (location.getRow() < 0 || location.getColumn() < 0 || location.getRow() >= model.getRow() || location.getColumn() >= model.getColumn())
            return;
        printMessage(location, "left");
        if (first) {
            model.iniItem(location.getRow(), location.getColumn());
            JFileChooser jFileChooser = new JFileChooser();
            File file = new File("src\\xyz\\view\\test\\reset.txt");
            Save.output(file);
            first = false;
            if (DifficultyDecision.model == 2 || DifficultyDecision.model == 4)
                model.getBox();
            if (BeginFrame.mode == 4 && PVPGameFrame.getIp() != null) {
                StringBuilder message = new StringBuilder("FIRST,");
                for (Square[] squares : model.getGrid()) {
                    for (Square square : squares) {
                        message.append(square.getNumberOfLandMine()).append("a");
                    }
                    message.append("b");
                }
                NetTool.sendUDPBroadCast(PVPGameFrame.getIp(), message.toString());
                NetTool.sendUDPBroadCast(PVPGameFrame.getIp(), "LEFT" + "," + location.getRow() + "," + location.getColumn() + "," + "local");
            }
        }
        if (DifficultyDecision.gameOverIsTurnUp) {
            return;
        }
        if (DifficultyDecision.gameOverIsTurnUp1) {
            return;
        }
        if (DifficultyDecision.model == 3 && currentPlayer == 2)
            return;

        if (model.getGridAt(location).isClick() && !model.getGridAt(location).isOpened() && !model.getGridAt(location).isFlag() && model.getGridAt(location).hasLandMine() && Function.Zhadan.open) {
            onPlayerRightClick(location);
        }


        if (model.getGridAt(location).hasLandMine() && model.getGridAt(location).isClick() && !model.getGridAt(location).isOpened()) {
            model.getGridAt(location).setOpened(true);
            model.getGridAt(location).setClick(false);
            view1.setItemAt(location, model.getNumAt(location));
            if (BeginFrame.mode == 1) {
                if (Save.model.getNumberOfMind() != 99) {
                    Timer a = new Timer();
                    a.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            GameController.ShowAllMinds();
                            DifficultyDecision.gameOver.setVisible(true);
                            DifficultyDecision.gameOverIsTurnUp = true;
                            DifficultyDecision.time.stop();
                            Function.Zhadan.open = false;
                            Function.alwaysUse = false;
                            DifficultyDecision.gameFrame.remove(DifficultyDecision.b1);
                        }
                    }, 200);
                } else {
                    Timer a = new Timer();
                    a.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            GameController.ShowAllMinds();
                            DifficultyDecision.gameOver1.setVisible(true);
                            DifficultyDecision.gameOverIsTurnUp1 = true;
                            DifficultyDecision.time.stop();
                            Function.Zhadan.open = false;
                            Function.alwaysUse = false;
                            DifficultyDecision.gameFrame.remove(DifficultyDecision.b1);
                        }
                    }, 200);
                }
                view1.repaint();
            }
            if (DifficultyDecision.model == 2)
                PlayerColor(currentPlayer, location);
            view1.repaint();
            MusicPlayer.aau4.play();
            view2.Delete(currentPlayer - 1);
            view2.findMine();
            view1.repaint();
            view2.repaint();
            nextPlayer();
        }

        if (!model.getGridAt(location).isClick() || model.getGridAt(location).isFlag() || view3.isTurnUp)
            return;


        if (!model.getGridAt(location).isOpened() && model.getGridAt(location).isClick()) {
            model.getGridAt(location).setClick(false);
            model.getGridAt(location).setOpened(true);
        }

        int[][] visited = new int[model.getRow()][model.getColumn()];

        if (model.getNumAt(location) == 0) {
            if (DifficultyDecision.model == 2)
                PlayerColor(currentPlayer, location);
            fill(model, model.getGrid(), location.getRow(), location.getColumn(), visited);
            nextPlayer();

        } else if (model.getNumAt(location) > 0) {
            if (DifficultyDecision.model == 2)
                PlayerColor(currentPlayer, location);
            model.calculateNum(location.getRow(), location.getColumn());
            int num = model.getNumAt(location);
            view1.setItemAt(location, num);
            nextPlayer();
        }

//        if (DifficultyDecision.model == 2 && !Save.model.getGrid()[location.getRow()][location.getColumn()].hasLandMine())
//            PlayerColor(currentPlayer, location);


        if (BeginFrame.mode == 3 && currentPlayer == 2) {
            AiTurn(DifficultyDecision.AItype);
        }

        if (!view3.isTurnUp) {
            win();
        }
        result = Winner();
        File file1 = new File(String.format("src\\xyz\\view\\playback\\play%d.txt",cnt));
        Save.output(file1);
        playback.add(cnt);
        cnt++;
        view1.repaint();
        view2.repaint();
    }

    @Override
    public void onPlayerRightClick(BoardLocation location) throws IOException, InterruptedException {
        if (location.getRow() < 0 || location.getColumn() < 0 || location.getRow() >= model.getRow() || location.getColumn() >= model.getColumn())
            return;
        if (view3.isTurnUp)
            return;
        if (DifficultyDecision.model == 3 && currentPlayer == 2)
            return;
        if (DifficultyDecision.gameOverIsTurnUp) {
            return;
        }
        if (DifficultyDecision.gameOverIsTurnUp1) {
            return;
        }

        if (model.getGridAt(location).getBoxNum() != 0 && (DifficultyDecision.model == 2 || DifficultyDecision.model == 4)) {
            boxNumber = model.getGridAt(location).getBoxNum();
            DifficultyDecision.tools.changeImage(boxNumber);

        }

        printMessage(location, "right");

        if (!model.getGridAt(location).isFlag() && !model.getGridAt(location).isOpened()) {
            if (DifficultyDecision.model == 2 || DifficultyDecision.model == 4) {

                if (model.getGridAt(location).getBoxNum() == 1) {
                    Function.Toushi.canUse = true;
                    if (DifficultyDecision.model == 4 && currentPlayer == 1) {
                        Timer temp1 = new Timer();
                        temp1.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Save.model.setToushi();
                            }
                        }, 1000);

                    } else if (DifficultyDecision.model == 2) {
                        Timer temp1 = new Timer();
                        temp1.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Save.model.setToushi();
                            }
                        }, 1000);
                    }
                } else if (model.getGridAt(location).getBoxNum() == 2) {

                    if (currentPlayer == 2 && DifficultyDecision.model == 4) {
                        Function.Zhadan.canUse = true;
                    } else {
                        Function.Zhadan.canUse = true;
                        Save.model.setZhadan();
                    }

                } else if (model.getGridAt(location).getBoxNum() == 3) {
                    Function.addOpenPoint.canUse = true;
                    Save.model.setaddonepoint();
                } else if (model.getGridAt(location).getBoxNum() == 4) {
                    Function.decreaseOnePoint.canUse = true;
                    Save.model.setdecreaseonepoint();
                } else if (model.getGridAt(location).getBoxNum() == 5) {
                    Function.decreaseTwoPoint.canUse = true;
                    Save.model.setdecreasetwopoint();
                } else if (model.getGridAt(location).getBoxNum() == 6) {
                    Function.addTwoPoint.canUse = true;
                    Save.model.setaddtwopoint();
                }

//                PlayerColor(currentPlayer, location);
            }

            model.getGridAt(location).setFlag(true);
            model.getGridAt(location).setOpened(true);

            if (DifficultyDecision.model != 1)
                mindShow(location);
            view1.setItemAt(location, ItemUtil.flagNum);
            MusicPlayer.aau2.play();
            if (!model.getGridAt(location).hasLandMine()) {
                view2.Lose(currentPlayer - 1);
                if (DifficultyDecision.model == 1) {
                    if (model.getNumberOfMind() < 20)
                        view1.getGridAt(location).setBorder(BorderFactory.createLineBorder(new Color(232, 206, 71, 225), 6));
                    else if (model.getNumberOfMind() < 40)
                        view1.getGridAt(location).setBorder(BorderFactory.createLineBorder(new Color(232, 206, 71, 225), 4));
                    else
                        view1.getGridAt(location).setBorder(BorderFactory.createLineBorder(new Color(232, 206, 71, 225), 3));

                    Object[] options = {"OK "};
                    JOptionPane.showOptionDialog(null, "Number of seconds plus one! ", "Prompt", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                    addTime += 1000;
                }
            }

            if (model.getGridAt(location).hasLandMine()) {
                view2.Goal(currentPlayer - 1);
                MusicPlayer.aau3.play();
                view2.findMine();
            }
            if (DifficultyDecision.model == 2) {
                PlayerColor(currentPlayer, location);
            }

            nextPlayer();
        }

        result = Winner();
        if (!view3.isTurnUp) {
            win();
        }

        if (BeginFrame.mode == 3 && currentPlayer == 2) {
            AiTurn(DifficultyDecision.AItype);
        }

        File file1 = new File(String.format("src\\xyz\\view\\playback\\play%d.txt",cnt));
        Save.output(file1);
        playback.add(cnt);
        cnt++;
        view1.repaint();
        view2.repaint();
    }

    @Override
    public void onPlayerMidClick(BoardLocation location) throws IOException {
        if (location.getRow() < 0 || location.getColumn() < 0 || location.getRow() >= model.getRow() || location.getColumn() >= model.getColumn())
            return;
        printMessage(location, "middle");
        int counter = 0;
        if (Save.model.getGrid()[location.getRow()][location.getColumn()].isOpened() && Save.model.getGrid()[location.getRow()][location.getColumn()].getNumberOfLandMine() > 0) {
            if (location.getRow() + 1 < Save.model.getRow() && Save.model.getGrid()[location.getRow() + 1][location.getColumn()].hasLandMine() && Save.model.getGrid()[location.getRow() + 1][location.getColumn()].isOpened())
                counter++;
            if (location.getColumn() + 1 < Save.model.getColumn() && Save.model.getGrid()[location.getRow()][location.getColumn() + 1].hasLandMine() && Save.model.getGrid()[location.getRow()][location.getColumn() + 1].isOpened())
                counter++;
            if (location.getRow() - 1 >= 0 && Save.model.getGrid()[location.getRow() - 1][location.getColumn()].hasLandMine() && Save.model.getGrid()[location.getRow() - 1][location.getColumn()].isOpened())
                counter++;
            if (location.getColumn() - 1 >= 0 && Save.model.getGrid()[location.getRow()][location.getColumn() - 1].hasLandMine() && Save.model.getGrid()[location.getRow()][location.getColumn() - 1].isOpened())
                counter++;
            if (location.getRow() + 1 < Save.model.getRow() && location.getColumn() + 1 < Save.model.getColumn() && Save.model.getGrid()[location.getRow() + 1][location.getColumn() + 1].hasLandMine() && Save.model.getGrid()[location.getRow() + 1][location.getColumn() + 1].isOpened())
                counter++;
            if (location.getColumn() + 1 < Save.model.getColumn() && location.getRow() - 1 >= 0 && Save.model.getGrid()[location.getRow() - 1][location.getColumn() + 1].hasLandMine() && Save.model.getGrid()[location.getRow() - 1][location.getColumn() + 1].isOpened())
                counter++;
            if (location.getRow() - 1 >= 0 && location.getColumn() - 1 >= 0 && Save.model.getGrid()[location.getRow() - 1][location.getColumn() - 1].hasLandMine() && Save.model.getGrid()[location.getRow() - 1][location.getColumn() - 1].isOpened())
                counter++;
            if (location.getColumn() - 1 >= 0 && location.getRow() + 1 < Save.model.getRow() && Save.model.getGrid()[location.getRow() + 1][location.getColumn() - 1].hasLandMine() && Save.model.getGrid()[location.getRow() + 1][location.getColumn() - 1].isOpened())
                counter++;
            System.out.println(counter);
            if (counter == Save.model.getGrid()[location.getRow()][location.getColumn()].getNumberOfLandMine()) {
                if (location.getRow() + 1 < Save.model.getRow() && !Save.model.getGrid()[location.getRow() + 1][location.getColumn()].isOpened()) {
                    Save.model.getGrid()[location.getRow() + 1][location.getColumn()].setOpened(true);
                    Save.model.getGrid()[location.getRow() + 1][location.getColumn()].setClick(false);
                    Save.model.calculateNum(location.getRow() + 1, location.getColumn());
                    Save.view1.setItemAt(Save.model.getGrid()[location.getRow() + 1][location.getColumn()].getLocation(), Save.model.getGrid()[location.getRow() + 1][location.getColumn()].getNumberOfLandMine());
                    if (Save.model.getGrid()[location.getRow() + 1][location.getColumn()].getNumberOfLandMine() == 0) {
                        int[][] visited = new int[model.getRow()][model.getColumn()];
                        fill(model, getModel().getGrid(), location.getRow() + 1, location.getColumn(), visited);
                    }
                }

                if (location.getColumn() + 1 < Save.model.getColumn() && !Save.model.getGrid()[location.getRow()][location.getColumn() + 1].isOpened()) {
                    Save.model.getGrid()[location.getRow()][location.getColumn() + 1].setOpened(true);
                    Save.model.getGrid()[location.getRow()][location.getColumn() + 1].setClick(false);
                    Save.model.calculateNum(location.getRow(), location.getColumn() + 1);
                    Save.view1.setItemAt(Save.model.getGrid()[location.getRow()][location.getColumn() + 1].getLocation(), Save.model.getGrid()[location.getRow()][location.getColumn() + 1].getNumberOfLandMine());
                    if (Save.model.getGrid()[location.getRow()][location.getColumn() + 1].getNumberOfLandMine() == 0) {
                        int[][] visited = new int[model.getRow()][model.getColumn()];
                        fill(model, getModel().getGrid(), location.getRow(), location.getColumn() + 1, visited);
                    }
                }
                if (location.getRow() - 1 >= 0 && !Save.model.getGrid()[location.getRow() - 1][location.getColumn()].isOpened()) {
                    Save.model.getGrid()[location.getRow() - 1][location.getColumn()].setOpened(true);
                    Save.model.getGrid()[location.getRow() - 1][location.getColumn()].setClick(false);
                    Save.model.calculateNum(location.getRow() - 1, location.getColumn());
                    Save.view1.setItemAt(Save.model.getGrid()[location.getRow() - 1][location.getColumn()].getLocation(), Save.model.getGrid()[location.getRow() - 1][location.getColumn()].getNumberOfLandMine());
                    if (Save.model.getGrid()[location.getRow() - 1][location.getColumn()].getNumberOfLandMine() == 0) {
                        int[][] visited = new int[model.getRow()][model.getColumn()];
                        fill(model, getModel().getGrid(), location.getRow() - 1, location.getColumn(), visited);
                    }
                }
                if (location.getColumn() - 1 >= 0 && !Save.model.getGrid()[location.getRow()][location.getColumn() - 1].isOpened()) {
                    Save.model.getGrid()[location.getRow()][location.getColumn() - 1].setOpened(true);
                    Save.model.getGrid()[location.getRow()][location.getColumn() - 1].setClick(false);
                    Save.model.calculateNum(location.getRow(), location.getColumn() - 1);
                    Save.view1.setItemAt(Save.model.getGrid()[location.getRow()][location.getColumn() - 1].getLocation(), Save.model.getGrid()[location.getRow()][location.getColumn() - 1].getNumberOfLandMine());
                    if (Save.model.getGrid()[location.getRow()][location.getColumn() - 1].getNumberOfLandMine() == 0) {
                        int[][] visited = new int[model.getRow()][model.getColumn()];
                        fill(model, getModel().getGrid(), location.getRow(), location.getColumn() - 1, visited);
                    }
                }
                if (location.getRow() + 1 < Save.model.getRow() && location.getColumn() + 1 < Save.model.getColumn() && !Save.model.getGrid()[location.getRow() + 1][location.getColumn() + 1].isOpened()) {
                    Save.model.getGrid()[location.getRow() + 1][location.getColumn() + 1].setOpened(true);
                    Save.model.getGrid()[location.getRow() + 1][location.getColumn() + 1].setClick(false);
                    Save.model.calculateNum(location.getRow() + 1, location.getColumn() + 1);
                    Save.view1.setItemAt(Save.model.getGrid()[location.getRow() + 1][location.getColumn() + 1].getLocation(), Save.model.getGrid()[location.getRow() + 1][location.getColumn() + 1].getNumberOfLandMine());
                    if (Save.model.getGrid()[location.getRow() + 1][location.getColumn() + 1].getNumberOfLandMine() == 0) {
                        int[][] visited = new int[model.getRow()][model.getColumn()];
                        fill(model, getModel().getGrid(), location.getRow() + 1, location.getColumn() + 1, visited);
                    }
                }
                if (location.getColumn() + 1 < Save.model.getColumn() && location.getRow() - 1 >= 0 && !Save.model.getGrid()[location.getRow() - 1][location.getColumn() + 1].isOpened()) {
                    Save.model.getGrid()[location.getRow() - 1][location.getColumn() + 1].setOpened(true);
                    Save.model.getGrid()[location.getRow() - 1][location.getColumn() + 1].setClick(false);
                    Save.model.calculateNum(location.getRow() - 1, location.getColumn() + 1);
                    Save.view1.setItemAt(Save.model.getGrid()[location.getRow() - 1][location.getColumn() + 1].getLocation(), Save.model.getGrid()[location.getRow() - 1][location.getColumn() + 1].getNumberOfLandMine());
                    if (Save.model.getGrid()[location.getRow() - 1][location.getColumn() + 1].getNumberOfLandMine() == 0) {
                        int[][] visited = new int[model.getRow()][model.getColumn()];
                        fill(model, getModel().getGrid(), location.getRow() - 1, location.getColumn() + 1, visited);
                    }
                }
                if (location.getRow() - 1 >= 0 && location.getColumn() - 1 >= 0 && !Save.model.getGrid()[location.getRow() - 1][location.getColumn() - 1].isOpened()) {
                    Save.model.getGrid()[location.getRow() - 1][location.getColumn() - 1].setOpened(true);
                    Save.model.getGrid()[location.getRow() - 1][location.getColumn() - 1].setClick(false);
                    Save.view1.setItemAt(Save.model.getGrid()[location.getRow() - 1][location.getColumn() - 1].getLocation(), Save.model.getGrid()[location.getRow() - 1][location.getColumn() - 1].getNumberOfLandMine());
                    if (Save.model.getGrid()[location.getRow() - 1][location.getColumn() - 1].getNumberOfLandMine() == 0) {
                        int[][] visited = new int[model.getRow()][model.getColumn()];
                        fill(model, getModel().getGrid(), location.getRow() - 1, location.getColumn() - 1, visited);
                    }
                }
                if (location.getColumn() - 1 >= 0 && location.getRow() + 1 < Save.model.getRow() && !Save.model.getGrid()[location.getRow() + 1][location.getColumn() - 1].isOpened()) {
                    Save.model.getGrid()[location.getRow() + 1][location.getColumn() - 1].setOpened(true);
                    Save.model.getGrid()[location.getRow() + 1][location.getColumn() - 1].setClick(false);
                    Save.model.calculateNum(location.getRow() + 1, location.getColumn() - 1);
                    Save.view1.setItemAt(Save.model.getGrid()[location.getRow() + 1][location.getColumn() - 1].getLocation(), Save.model.getGrid()[location.getRow() + 1][location.getColumn() - 1].getNumberOfLandMine());
                    if (Save.model.getGrid()[location.getRow() + 1][location.getColumn() - 1].getNumberOfLandMine() == 0) {
                        int[][] visited = new int[model.getRow()][model.getColumn()];
                        fill(model, getModel().getGrid(), location.getRow() + 1, location.getColumn() - 1, visited);
                    }
                }
            }
            File file1 = new File(String.format("src\\xyz\\view\\playback\\play%d.txt",cnt));
            Save.output(file1);
            playback.add(cnt);
            cnt++;
            view1.repaint();
        }
    }

    @Override
    public void onPlayerMove(BoardLocation location) {
        if (location.getRow() < 0 || location.getColumn() < 0 || location.getRow() >= model.getRow() || location.getColumn() >= model.getColumn())
            return;

        if (!model.getGridAt(location).isOpened() && model.getGridAt(location).isFlag()) {
            MusicPlayer.aau9.play();

            view1.setItemAt(location, 88);
        } else if (!model.getGridAt(location).isOpened()) {
            MusicPlayer.aau9.play();

            view1.setItemAt(location, 66);
        }
        view1.repaint();
    }

    public void onPlayerMoveNet(BoardLocation location) {
        if (getCurrentPlayer() == 1)
            return;
        if (!model.getGridAt(location).isOpened() && model.getGridAt(location).isFlag())
            view1.setItemAt(location, 88);
        else if (!model.getGridAt(location).isOpened())
            view1.setItemAt(location, 66);
        view1.repaint();
    }

    @Override
    public void onPlayerExit(BoardLocation location) {
        if (location.getRow() < 0 || location.getColumn() < 0 || location.getRow() >= model.getRow() || location.getColumn() >= model.getColumn())
            return;

        if (!model.getGridAt(location).isOpened() && model.getGridAt(location).isFlag())
            view1.setItemAt(location, -100);
        else if (!model.getGridAt(location).isOpened())
            view1.setItemAt(location, -2);
        view1.repaint();
    }

    public void onPlayerExitNet(BoardLocation location) {
        if (getCurrentPlayer() == 1)
            return;
        if (!model.getGridAt(location).isOpened() && model.getGridAt(location).isFlag())
            view1.setItemAt(location, -100);
        else if (!model.getGridAt(location).isOpened())
            view1.setItemAt(location, -2);
        view1.repaint();
    }

    private void printMessage(BoardLocation location, String str) {
        int row_in_message = location.getRow();
        int column_in_message = location.getColumn();
        String format;
        if (BeginFrame.mode == 3 && currentPlayer == 2)
            format = "\nComputer %d %s click at (%d, %d), ";
        else
            format = "\nOn Player %d %s click at (%d, %d), ";
        System.out.printf(format, currentPlayer, str, row_in_message + 1, column_in_message + 1);
    }


    public void fill(Board board, Square[][] grid, int x, int y, int[][] vistied) {
        if (x >= board.getRow() || y >= board.getColumn() || vistied[x][y] == 1)
            return;
        vistied[x][y] = 1;
        int num = model.calculateNum(x, y);
        if (grid[x][y].getNumberOfLandMine() < 0)
            return;
        else if (num > 0) {
            if (grid[x][y].isFlag())
                return;
            view1.setItemAt(grid[x][y].getLocation(), num);
            BoardLocation temp = new BoardLocation(x, y);
            model.getGridAt(temp).setClick(false);
            model.getGridAt(temp).setOpened(true);
            return;
        }
        if (grid[x][y].isFlag())
            return;
        view1.setItemAt(grid[x][y].getLocation(), num);
        BoardLocation temp = new BoardLocation(x, y);
        model.getGridAt(temp).setClick(false);
        model.getGridAt(temp).setOpened(true);
        if (y - 1 >= 0)
            fill(board, grid, x, y - 1, vistied);
        if (y - 1 >= 0 && x - 1 >= 0)
            fill(board, grid, x - 1, y - 1, vistied);
        if (x + 1 < model.getRow() && y - 1 >= 0)
            fill(board, grid, x + 1, y - 1, vistied);
        if (y + 1 < board.getColumn())
            fill(board, grid, x, y + 1, vistied);
        if (x - 1 >= 0 && y + 1 < model.getColumn())
            fill(board, grid, x - 1, y + 1, vistied);
        if (x + 1 < board.getRow() && y + 1 < model.getColumn())
            fill(board, grid, x + 1, y + 1, vistied);
        if (x - 1 >= 0)
            fill(board, grid, x - 1, y, vistied);
        if (x + 1 < board.getRow())
            fill(board, grid, x + 1, y, vistied);
    }

    public String Winner() {
        String result = null;
        int leftmine;
        leftmine = model.getNumberOfMind() - view2.getFounedmine();
        if (BeginFrame.mode == 1) {
            if (leftmine == 0)
                result = "You win!";
        } else if (ScoreBoard.getPerson() == 2) {
            if (Math.abs(view2.getScoreBoard()[2][0] - view2.getScoreBoard()[2][1]) > leftmine) {
                if (view2.getScoreBoard()[2][0] < view2.getScoreBoard()[2][1]) {
                    if (DifficultyDecision.model == 3)
                        result = "AI!";
                    else if (DifficultyDecision.model == 4)
                        result = "Opponent!";
                    else
                        result = "Player 2!";
                } else if (view2.getScoreBoard()[2][0] > view2.getScoreBoard()[2][1]) {
                    if (DifficultyDecision.model == 3 || DifficultyDecision.model == 4)
                        result = "You!";
                    else
                        result = "Player 1!";
                } else
                    result = "Dogfall!";
            } else if (leftmine == 0) {
                if (view2.getScoreBoard()[1][0] < view2.getScoreBoard()[1][1])
                    result = "Player 1!";
                else if (view2.getScoreBoard()[1][0] > view2.getScoreBoard()[1][1])
                    result = "Player 2!";
                else
                    result = "Dogfall!";
            }
        } else if (ScoreBoard.getPerson() == 3) {
            if ((Math.abs(view2.getScoreBoard()[2][0] - view2.getScoreBoard()[2][1]) > leftmine) ||
                    (Math.abs(view2.getScoreBoard()[2][1] - view2.getScoreBoard()[2][2]) > leftmine) ||
                    (Math.abs(view2.getScoreBoard()[2][2] - view2.getScoreBoard()[2][1]) > leftmine)) {
                if ((view2.getScoreBoard()[2][0] < view2.getScoreBoard()[2][1]) && (view2.getScoreBoard()[2][2] < view2.getScoreBoard()[2][1]))
                    result = "Player-2!";
                else if ((view2.getScoreBoard()[2][0] > view2.getScoreBoard()[2][1]) && (view2.getScoreBoard()[2][0] > view2.getScoreBoard()[2][2]))
                    result = "Player-1!";
                else if ((view2.getScoreBoard()[2][2] > view2.getScoreBoard()[2][1]) && (view2.getScoreBoard()[2][2] > view2.getScoreBoard()[2][0]))
                    result = "Player-3!";
                else
                    result = "Dogfall!";
            } else if (leftmine == 0) {
                if ((view2.getScoreBoard()[1][0] < view2.getScoreBoard()[1][1]) && (view2.getScoreBoard()[1][0] < view2.getScoreBoard()[1][2]))
                    result = "Player-1!";
                else if ((view2.getScoreBoard()[1][0] > view2.getScoreBoard()[1][1]) && (view2.getScoreBoard()[1][2] > view2.getScoreBoard()[1][1]))
                    result = "Player-2!";
                else if ((view2.getScoreBoard()[1][2] > view2.getScoreBoard()[1][1]) && (view2.getScoreBoard()[1][2] > view2.getScoreBoard()[1][0]))
                    result = "Player-3!";
                else
                    result = "Dogfall!";
            }
        } else if (ScoreBoard.getPerson() == 4) {
            if ((Math.abs(view2.getScoreBoard()[2][0] - view2.getScoreBoard()[2][1]) > leftmine) ||
                    (Math.abs(view2.getScoreBoard()[2][1] - view2.getScoreBoard()[2][2]) > leftmine) ||
                    (Math.abs(view2.getScoreBoard()[2][2] - view2.getScoreBoard()[2][0]) > leftmine) ||
                    (Math.abs(view2.getScoreBoard()[2][3] - view2.getScoreBoard()[2][0]) > leftmine) ||
                    (Math.abs(view2.getScoreBoard()[2][3] - view2.getScoreBoard()[2][1]) > leftmine) ||
                    (Math.abs(view2.getScoreBoard()[2][3] - view2.getScoreBoard()[2][2]) > leftmine)) {
                if ((view2.getScoreBoard()[2][0] < view2.getScoreBoard()[2][1]) && (view2.getScoreBoard()[2][2] < view2.getScoreBoard()[2][1]) && (view2.getScoreBoard()[2][3] < view2.getScoreBoard()[2][1]))
                    result = "Player-2!";
                else if ((view2.getScoreBoard()[2][0] > view2.getScoreBoard()[2][1]) && (view2.getScoreBoard()[2][0] > view2.getScoreBoard()[2][2]) && (view2.getScoreBoard()[2][0] > view2.getScoreBoard()[2][3]))
                    result = "Player-1!";
                else if ((view2.getScoreBoard()[2][2] > view2.getScoreBoard()[2][1]) && (view2.getScoreBoard()[2][2] > view2.getScoreBoard()[2][0]) && (view2.getScoreBoard()[2][2] > view2.getScoreBoard()[2][3]))
                    result = "Player-3!";
                else if ((view2.getScoreBoard()[2][3] > view2.getScoreBoard()[2][1]) && (view2.getScoreBoard()[2][3] > view2.getScoreBoard()[2][0]) && (view2.getScoreBoard()[2][2] > view2.getScoreBoard()[2][2]))
                    result = "Player-4!";
                else
                    result = "Dogfall!";
            } else if (leftmine == 0) {
                if ((view2.getScoreBoard()[1][0] < view2.getScoreBoard()[1][1]) && (view2.getScoreBoard()[1][0] < view2.getScoreBoard()[1][2]) && (view2.getScoreBoard()[1][0] < view2.getScoreBoard()[1][3]))
                    result = "Player-1!";
                else if ((view2.getScoreBoard()[1][0] > view2.getScoreBoard()[1][1]) && (view2.getScoreBoard()[1][2] > view2.getScoreBoard()[1][1]) && (view2.getScoreBoard()[1][3] > view2.getScoreBoard()[1][1]))
                    result = "Player-2!";
                else if ((view2.getScoreBoard()[1][2] > view2.getScoreBoard()[1][1]) && (view2.getScoreBoard()[1][2] > view2.getScoreBoard()[1][0]) && (view2.getScoreBoard()[1][2] > view2.getScoreBoard()[1][3]))
                    result = "Player-3!";
                else if ((view2.getScoreBoard()[1][2] > view2.getScoreBoard()[1][3]) && (view2.getScoreBoard()[1][1] > view2.getScoreBoard()[1][3]) && (view2.getScoreBoard()[1][0] > view2.getScoreBoard()[1][3]))
                    result = "Player-4!";
                else
                    result = "Dogfall!";
            }
        }
        return result;
    }

    public void AiTurn(int x) throws InterruptedException {
        Random ran = new Random();
        int suiji = ran.nextInt(3);

        for (int i = 0; i < model.getRow(); i++) {
            for (int i1 = 0; i1 < model.getColumn(); i1++) {
                if (getModel().getGrid()[i][i1].isAiMarked()) {
                    view1.getGridAt(getModel().getGrid()[i][i1].getLocation()).setBorder(BorderFactory.createEmptyBorder());
                    Save.model.getGrid()[i][i1].setAiMarked(false);
                }

            }
        }

        if (x == 1) {

            int abab = ran.nextInt(1000);
            int abab1 = ran.nextInt(1000);

            Timer temp = new Timer();
            temp.schedule(new TimerTask() {
                @Override
                public void run() {
                    Model mol = new Model();
                    BoardLocation rightChoice = mol.rightMachine();
                    BoardLocation leftChoice = mol.leftMachine();
                    BoardLocation mayLeft = mol.maybeLeftMachine();
                    BoardLocation mayRight = mol.maybeRightMachine();

                    if (abab <= 300 && rightChoice != null) {
                        Save.model.getGrid()[rightChoice.getRow()][rightChoice.getColumn()].setOpened(true);
                        Save.model.getGrid()[rightChoice.getRow()][rightChoice.getColumn()].setFlag(true);
                        Save.view2.findMine();
                        Save.view2.Goal(Save.gameController.currentPlayer - 1);
                        Save.model.calculateNum(rightChoice.getRow(), rightChoice.getColumn());
                        Save.view1.setItemAt(Save.model.getGrid()[rightChoice.getRow()][rightChoice.getColumn()].getLocation(), ItemUtil.flagNum);
                        Save.gameController.mindShow(Save.model.getGrid()[rightChoice.getRow()][rightChoice.getColumn()].getLocation());
                        if (model.getNumberOfMind() < 20)
                            view1.getGridAt(rightChoice).setBorder(BorderFactory.createLineBorder(new Color(19, 147, 107, 225), 6));
                        else if (model.getNumberOfMind() < 40)
                            view1.getGridAt(rightChoice).setBorder(BorderFactory.createLineBorder(new Color(19, 147, 107, 225), 4));
                        else
                            view1.getGridAt(rightChoice).setBorder(BorderFactory.createLineBorder(new Color(19, 147, 107, 225), 3));
                        nextPlayer();
                        model.getGrid()[rightChoice.getRow()][rightChoice.getColumn()].setAiMarked(true);
                        view1.repaint();
                        view2.repaint();

                    } else if (abab > 301 && abab < 500 && leftChoice != null) {
                        Save.model.getGrid()[leftChoice.getRow()][leftChoice.getColumn()].setOpened(true);
                        Save.model.getGrid()[leftChoice.getRow()][leftChoice.getColumn()].setClick(false);
                        Save.model.calculateNum(leftChoice.getRow(), leftChoice.getColumn());
                        Save.view1.setItemAt(Save.model.getGrid()[leftChoice.getRow()][leftChoice.getColumn()].getLocation(), Save.model.getGrid()[leftChoice.getRow()][leftChoice.getColumn()].getNumberOfLandMine());
                        if (Save.model.getGrid()[leftChoice.getRow()][leftChoice.getColumn()].getNumberOfLandMine() == 0) {
                            int[][] visited = new int[Save.model.getRow()][Save.model.getColumn()];
                            Save.gameController.fill(Save.model, Save.getModel().getGrid(), leftChoice.getRow(), leftChoice.getColumn(), visited);
                        }
                        if (model.getNumberOfMind() < 20)
                            view1.getGridAt(leftChoice).setBorder(BorderFactory.createLineBorder(new Color(19, 147, 107, 225), 6));
                        else if (model.getNumberOfMind() < 40)
                            view1.getGridAt(leftChoice).setBorder(BorderFactory.createLineBorder(new Color(19, 147, 107, 225), 4));
                        else
                            view1.getGridAt(leftChoice).setBorder(BorderFactory.createLineBorder(new Color(19, 147, 107, 225), 3));

                        model.getGrid()[leftChoice.getRow()][leftChoice.getColumn()].setAiMarked(true);
                        nextPlayer();
                        view1.repaint();
                    } else {
                        if (abab1 >= 600) {
                            Save.model.getGrid()[mayLeft.getRow()][mayLeft.getColumn()].setOpened(true);
                            Save.model.getGrid()[mayLeft.getRow()][mayLeft.getColumn()].setClick(false);
                            int num = Save.model.calculateNum(mayLeft.getRow(), mayLeft.getColumn());
                            Save.view1.setItemAt(Save.model.getGrid()[mayLeft.getRow()][mayLeft.getColumn()].getLocation(), Save.model.getGrid()[mayLeft.getRow()][mayLeft.getColumn()].getNumberOfLandMine());
                            if (num < 0)
                                view2.Lose(currentPlayer - 1);
                            if (Save.model.getGrid()[mayLeft.getRow()][mayLeft.getColumn()].getNumberOfLandMine() == 0) {
                                int[][] visited = new int[Save.model.getRow()][Save.model.getColumn()];
                                Save.gameController.fill(Save.model, Save.getModel().getGrid(), mayLeft.getRow(), mayLeft.getColumn(), visited);
                            }
                            if (model.getNumberOfMind() < 20)
                                view1.getGridAt(mayLeft).setBorder(BorderFactory.createLineBorder(new Color(19, 147, 107, 225), 6));
                            else if (model.getNumberOfMind() < 40)
                                view1.getGridAt(mayLeft).setBorder(BorderFactory.createLineBorder(new Color(19, 147, 107, 225), 4));
                            else
                                view1.getGridAt(mayLeft).setBorder(BorderFactory.createLineBorder(new Color(19, 147, 107, 225), 3));

                            model.getGrid()[mayLeft.getRow()][mayLeft.getColumn()].setAiMarked(true);
                            nextPlayer();
                            view2.repaint();
                            view1.repaint();
                        } else {
                            Save.model.getGrid()[mayRight.getRow()][mayRight.getColumn()].setOpened(true);
                            Save.model.getGrid()[mayRight.getRow()][mayRight.getColumn()].setFlag(true);
                            Save.view2.findMine();

                            int num = Save.model.calculateNum(mayRight.getRow(), mayRight.getColumn());
                            Save.view1.setItemAt(Save.model.getGrid()[mayRight.getRow()][mayRight.getColumn()].getLocation(), ItemUtil.flagNum);
                            if (num < 0)
                                Save.view2.Goal(Save.gameController.currentPlayer - 1);
                            else
                                Save.view2.Lose(Save.gameController.currentPlayer - 1);
                            Save.gameController.mindShow(Save.model.getGrid()[mayRight.getRow()][mayRight.getColumn()].getLocation());
                            if (model.getNumberOfMind() < 20)
                                view1.getGridAt(mayRight).setBorder(BorderFactory.createLineBorder(new Color(19, 147, 107, 225), 6));
                            else if (model.getNumberOfMind() < 40)
                                view1.getGridAt(mayRight).setBorder(BorderFactory.createLineBorder(new Color(19, 147, 107, 225), 4));
                            else
                                view1.getGridAt(mayRight).setBorder(BorderFactory.createLineBorder(new Color(19, 147, 107, 225), 3));

                            model.getGrid()[mayRight.getRow()][mayRight.getColumn()].setAiMarked(true);
                            nextPlayer();
                            view1.repaint();
                            view2.repaint();
                        }
                    }
                    DifficultyDecision.timer.interrupt();
                    DifficultyDecision.timer = new TimeThread(DifficultyDecision.timeTable);
                    DifficultyDecision.timer.start();
                }
            }, (suiji + 1) * 1000);

        } else if (x == 2) {
            int abab = ran.nextInt(1000);
            Timer temp = new Timer();
            temp.schedule(new TimerTask() {
                @Override
                public void run() {
                    Model mol = new Model();
                    BoardLocation rightChoice = mol.rightMachine();
                    BoardLocation leftChoice = mol.leftMachine();
                    BoardLocation mayLeft = mol.maybeLeftMachine();
                    BoardLocation mayRight = mol.maybeRightMachine();
                    if (rightChoice != null) {
                        Save.model.getGrid()[rightChoice.getRow()][rightChoice.getColumn()].setOpened(true);
                        Save.model.getGrid()[rightChoice.getRow()][rightChoice.getColumn()].setFlag(true);
                        Save.view2.findMine();
                        Save.view2.Goal(Save.gameController.currentPlayer - 1);
                        Save.model.calculateNum(rightChoice.getRow(), rightChoice.getColumn());
                        Save.view1.setItemAt(Save.model.getGrid()[rightChoice.getRow()][rightChoice.getColumn()].getLocation(), ItemUtil.flagNum);
                        Save.gameController.mindShow(Save.model.getGrid()[rightChoice.getRow()][rightChoice.getColumn()].getLocation());
                        if (model.getNumberOfMind() < 20)
                            view1.getGridAt(rightChoice).setBorder(BorderFactory.createLineBorder(new Color(19, 147, 107, 225), 6));
                        else if (model.getNumberOfMind() < 40)
                            view1.getGridAt(rightChoice).setBorder(BorderFactory.createLineBorder(new Color(19, 147, 107, 225), 4));
                        else
                            view1.getGridAt(rightChoice).setBorder(BorderFactory.createLineBorder(new Color(19, 147, 107, 225), 3));
                        model.getGrid()[rightChoice.getRow()][rightChoice.getColumn()].setAiMarked(true);
                        nextPlayer();
                        view1.repaint();
                        view2.repaint();

                    } else if (leftChoice != null) {
                        Save.model.getGrid()[leftChoice.getRow()][leftChoice.getColumn()].setOpened(true);
                        Save.model.getGrid()[leftChoice.getRow()][leftChoice.getColumn()].setClick(false);
                        Save.model.calculateNum(leftChoice.getRow(), leftChoice.getColumn());
                        Save.view1.setItemAt(Save.model.getGrid()[leftChoice.getRow()][leftChoice.getColumn()].getLocation(), Save.model.getGrid()[leftChoice.getRow()][leftChoice.getColumn()].getNumberOfLandMine());
                        if (Save.model.getGrid()[leftChoice.getRow()][leftChoice.getColumn()].getNumberOfLandMine() == 0) {
                            int[][] visited = new int[Save.model.getRow()][Save.model.getColumn()];
                            Save.gameController.fill(Save.model, Save.getModel().getGrid(), leftChoice.getRow(), leftChoice.getColumn(), visited);
                        }
                        if (model.getNumberOfMind() < 20)
                            view1.getGridAt(leftChoice).setBorder(BorderFactory.createLineBorder(new Color(19, 147, 107, 225), 6));
                        else if (model.getNumberOfMind() < 40)
                            view1.getGridAt(leftChoice).setBorder(BorderFactory.createLineBorder(new Color(19, 147, 107, 225), 4));
                        else
                            view1.getGridAt(leftChoice).setBorder(BorderFactory.createLineBorder(new Color(19, 147, 107, 225), 3));

                        model.getGrid()[leftChoice.getRow()][leftChoice.getColumn()].setAiMarked(true);
                        nextPlayer();
                        view1.repaint();
                    } else {
                        if (abab <= 500) {
                            Save.model.getGrid()[mayLeft.getRow()][mayLeft.getColumn()].setOpened(true);
                            Save.model.getGrid()[mayLeft.getRow()][mayLeft.getColumn()].setClick(false);
                            int num = Save.model.calculateNum(mayLeft.getRow(), mayLeft.getColumn());
                            Save.view1.setItemAt(Save.model.getGrid()[mayLeft.getRow()][mayLeft.getColumn()].getLocation(), Save.model.getGrid()[mayLeft.getRow()][mayLeft.getColumn()].getNumberOfLandMine());
                            if (num < 0)
                                view2.Lose(currentPlayer - 1);
                            if (Save.model.getGrid()[mayLeft.getRow()][mayLeft.getColumn()].getNumberOfLandMine() == 0) {
                                int[][] visited = new int[Save.model.getRow()][Save.model.getColumn()];
                                Save.gameController.fill(Save.model, Save.getModel().getGrid(), mayLeft.getRow(), mayLeft.getColumn(), visited);
                            }
                            if (model.getNumberOfMind() < 20)
                                view1.getGridAt(mayLeft).setBorder(BorderFactory.createLineBorder(new Color(19, 147, 107, 225), 6));
                            else if (model.getNumberOfMind() < 40)
                                view1.getGridAt(mayLeft).setBorder(BorderFactory.createLineBorder(new Color(19, 147, 107, 225), 4));
                            else
                                view1.getGridAt(mayLeft).setBorder(BorderFactory.createLineBorder(new Color(19, 147, 107, 225), 3));

                            model.getGrid()[mayLeft.getRow()][mayLeft.getColumn()].setAiMarked(true);
                            nextPlayer();
                            view2.repaint();
                            view1.repaint();
                        } else {
                            Save.model.getGrid()[mayRight.getRow()][mayRight.getColumn()].setOpened(true);
                            Save.model.getGrid()[mayRight.getRow()][mayRight.getColumn()].setFlag(true);
                            Save.view2.findMine();

                            int num = Save.model.calculateNum(mayRight.getRow(), mayRight.getColumn());
                            Save.view1.setItemAt(Save.model.getGrid()[mayRight.getRow()][mayRight.getColumn()].getLocation(), ItemUtil.flagNum);
                            if (num < 0)
                                Save.view2.Goal(Save.gameController.currentPlayer - 1);
                            else
                                Save.view2.Lose(Save.gameController.currentPlayer - 1);
                            Save.gameController.mindShow(Save.model.getGrid()[mayRight.getRow()][mayRight.getColumn()].getLocation());
                            if (model.getNumberOfMind() < 20)
                                view1.getGridAt(mayRight).setBorder(BorderFactory.createLineBorder(new Color(19, 147, 107, 225), 6));
                            else if (model.getNumberOfMind() < 40)
                                view1.getGridAt(mayRight).setBorder(BorderFactory.createLineBorder(new Color(19, 147, 107, 225), 4));
                            else
                                view1.getGridAt(mayRight).setBorder(BorderFactory.createLineBorder(new Color(19, 147, 107, 225), 3));

                            model.getGrid()[mayRight.getRow()][mayRight.getColumn()].setAiMarked(true);
                            nextPlayer();
                            view1.repaint();
                            view2.repaint();
                        }
                    }
                    DifficultyDecision.timer.interrupt();
                    DifficultyDecision.timer = new TimeThread(DifficultyDecision.timeTable);
                    DifficultyDecision.timer.start();
                }
            }, (suiji + 1) * 1000);

        } else if (x == 3) {
            int counter = 0;
            int a = ran.nextInt(1000);
            if (a >= 800)
                AiTurn(2);
            else {
                for (int i = 0; i < model.getRow(); i++) {
                    for (int i1 = 0; i1 < model.getColumn(); i1++) {
                        if (model.getGrid()[i][i1].hasLandMine() && !model.getGrid()[i][i1].isOpened() & ((i >= 1 && model.getGrid()[i - 1][i1].isOpened() && !model.getGrid()[i - 1][i1].isClick() && !model.getGrid()[i - 1][i1].hasLandMine()) || ((i + 1 < model.getRow()) && model.getGrid()[i + 1][i1].isOpened() && !model.getGrid()[i + 1][i1].isClick() && !model.getGrid()[i + 1][i1].hasLandMine()) || ((i1 + 1 < model.getColumn()) && model.getGrid()[i][i1 + 1].isOpened() && !model.getGrid()[i][i1 + 1].isClick() && !model.getGrid()[i][i1 + 1].hasLandMine()) || (i1 >= 1 && model.getGrid()[i][i1 - 1].isOpened() && !model.getGrid()[i][i1 - 1].isClick() && !model.getGrid()[i][i1 - 1].hasLandMine()) || (i + 1 < model.getRow() && i1 + 1 < model.getColumn() && model.getGrid()[i + 1][i1 + 1].isOpened() && !model.getGrid()[i + 1][i1 + 1].isClick() && !model.getGrid()[i + 1][i1 + 1].hasLandMine()) || (i > 0 && i1 > 0 && model.getGrid()[i - 1][i1 - 1].isOpened() && !model.getGrid()[i - 1][i1 - 1].isClick() && !model.getGrid()[i - 1][i1 - 1].hasLandMine()) || (i + 1 < model.getRow() && i1 > 0 && model.getGrid()[i + 1][i1 - 1].isOpened() && !model.getGrid()[i + 1][i1 - 1].isClick() && !model.getGrid()[i + 1][i1 - 1].hasLandMine()) || (i > 0 && i1 + 1 < model.getColumn() && model.getGrid()[i - 1][i1 + 1].isOpened() && !model.getGrid()[i - 1][i1 + 1].isClick() && !model.getGrid()[i - 1][i1 + 1].hasLandMine()))) {
                            Save.model.getGrid()[i][i1].setOpened(true);
                            Save.model.getGrid()[i][i1].setFlag(true);
                            Save.view2.findMine();
                            Save.view2.Goal(Save.gameController.currentPlayer - 1);
                            Save.model.calculateNum(i, i1);
                            Save.view1.setItemAt(Save.model.getGrid()[i][i1].getLocation(), ItemUtil.flagNum);
                            Save.gameController.mindShow(Save.model.getGrid()[i][i1].getLocation());
                            if (model.getNumberOfMind() < 20)
                                view1.getGridAt(model.getGrid()[i][i1].getLocation()).setBorder(BorderFactory.createLineBorder(new Color(19, 147, 107, 225), 6));
                            else if (model.getNumberOfMind() < 40)
                                view1.getGridAt(model.getGrid()[i][i1].getLocation()).setBorder(BorderFactory.createLineBorder(new Color(19, 147, 107, 225), 4));
                            else
                                view1.getGridAt(model.getGrid()[i][i1].getLocation()).setBorder(BorderFactory.createLineBorder(new Color(19, 147, 107, 225), 3));
                            model.getGrid()[i][i1].setAiMarked(true);
                            nextPlayer();
                            view1.repaint();
                            view2.repaint();
                            counter++;
                            break;
                        }
                    }
                    if (counter == 1)
                        break;
                }
                if (counter != 1)
                    AiTurn(2);
            }
        }
    }

    public void setClickable(int clickable) {
        this.clickable = clickable;
    }

    public void mindShow(BoardLocation location) {
        Timer temp = new Timer();
        temp.schedule(new TimerTask() {
            @Override
            public void run() {
                if (model.getGrid()[location.getRow()][location.getColumn()].hasLandMine()) {
                    model.getGridAt(location).setFlag(false);
                    view1.setItemAt(location, Save.model.getNumAt(location));
                    model.getGridAt(location).setFlag(true);
                    Save.view1.repaint();
                } else if (!model.getGrid()[location.getRow()][location.getColumn()].hasLandMine()) {
                    model.getGridAt(location).setFlag(false);
                    model.calculateNum(location.getRow(), location.getColumn());
                    view1.setItemAt(location, Save.model.getNumAt(location));
                    model.getGridAt(location).setFlag(true);
                    Save.view1.repaint();
                }
            }
        }, 500);
        Timer temp1 = new Timer();
        temp1.schedule(new TimerTask() {
            @Override
            public void run() {
                if (model.getGrid()[location.getRow()][location.getColumn()].hasLandMine()) {
                    model.getGridAt(location).setFlag(false);
                    view1.setItemAt(location, ItemUtil.flagNum);
                    model.getGridAt(location).setFlag(true);
                    Save.view1.repaint();
                }
            }
        }, 1000);
        Timer temp2 = new Timer();
        temp2.schedule(new TimerTask() {
            @Override
            public void run() {
                if (model.getGrid()[location.getRow()][location.getColumn()].hasLandMine()) {
                    model.getGridAt(location).setFlag(false);
                    view1.setItemAt(location, Save.model.getNumAt(location));
                    model.getGridAt(location).setFlag(true);
                    Save.view1.repaint();
                }
            }
        }, 1500);
        Timer temp3 = new Timer();
        temp3.schedule(new TimerTask() {
            @Override
            public void run() {
                if (model.getGrid()[location.getRow()][location.getColumn()].hasLandMine()) {
                    model.getGridAt(location).setFlag(false);
                    view1.setItemAt(location, ItemUtil.flagNum);
                    model.getGridAt(location).setFlag(true);
                    Save.view1.repaint();
                }
            }
        }, 2000);
    }

    public void win() {
        if (result != null) {
            if (DifficultyDecision.model == 1) {
                usertime = DifficultyDecision.timelabel.getText() + addTime;
                view3.setVisible(true);
                MusicPlayer.aau8.stop();
                MusicPlayer.aau7.play();
                view3.Result(result);
                view3.repaint();
                view3.isTurnUp = true;
                if (Board.getNUMBER_0F_MIND_LOW_LEVEL() == Save.model.getNumberOfMind()) {
                    DatabaseUtil.record(DatabaseUtil.player.id, usertime, "easy");
                    DatabaseUtil.updateInfo("person_easy", DatabaseUtil.player.id);
                    DatabaseUtil.updateInfo("person_count_easy", DatabaseUtil.player.id);
                }
                if (Board.getNUMBER_0F_MIND_MEDIUM_LEVEL() == Save.model.getNumberOfMind()) {
                    DatabaseUtil.record(DatabaseUtil.player.id, usertime, "middle");
                    DatabaseUtil.updateInfo("person_middle", DatabaseUtil.player.id);
                }
                if (Board.getNUMBER_0F_MIND_HIGH_LEVEL() == Save.model.getNumberOfMind()) {
                    DatabaseUtil.record(DatabaseUtil.player.id, usertime, "difficult");
                    DatabaseUtil.updateInfo("person_difficult", DatabaseUtil.player.id);
                }
                DifficultyDecision.time.stop();
                Function.alwaysUse = false;
                Function.Zhadan.open = false;
                DifficultyDecision.gameFrame.remove(DifficultyDecision.b1);
            } else {
                usertime = DifficultyDecision.timelabel.getText();
                Timer temp = new Timer();
                temp.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        view3.setVisible(true);
                        MusicPlayer.aau8.stop();
                        MusicPlayer.aau7.play();
                        view3.Result(result);
                        view3.repaint();
                        view3.isTurnUp = true;
                        if (Board.getNUMBER_0F_MIND_LOW_LEVEL() == Save.model.getNumberOfMind()) {
                            switch (DifficultyDecision.model) {
                                case 3:
                                    if (result.equals("You!")) {
                                        DatabaseUtil.updateInfo("person_easy", DatabaseUtil.player.id);
                                        DatabaseUtil.updateInfo("person_count_easy", DatabaseUtil.player.id);
                                        switch (DifficultyDecision.AItype) {
                                            case 1:
                                                DatabaseUtil.updateInfo("ai_easy", DatabaseUtil.player.id);
                                                DatabaseUtil.updateInfo("ai_count_easy", DatabaseUtil.player.id);
                                                break;
                                            case 2:
                                                DatabaseUtil.updateInfo("ai_middle", DatabaseUtil.player.id);
                                                DatabaseUtil.updateInfo("ai_count_middle", DatabaseUtil.player.id);
                                                break;
                                            case 3:
                                                DatabaseUtil.updateInfo("ai_difficult", DatabaseUtil.player.id);
                                                DatabaseUtil.updateInfo("ai_count_difficult", DatabaseUtil.player.id);
                                                break;
                                        }
                                    } else {
                                        DatabaseUtil.updateInfo("person_count_easy", DatabaseUtil.player.id);
                                        switch (DifficultyDecision.AItype) {
                                            case 1:
                                                DatabaseUtil.updateInfo("ai_easy", "ai_easy");
                                                DatabaseUtil.updateInfo("ai_count_easy", "ai_easy");
                                                break;
                                            case 2:
                                                DatabaseUtil.updateInfo("ai_middle", "ai_middle");
                                                DatabaseUtil.updateInfo("ai_count_middle", "ai_count_middle");
                                                break;
                                            case 3:
                                                DatabaseUtil.updateInfo("ai_difficult", "ai_difficult");
                                                DatabaseUtil.updateInfo("ai_count_difficult", "ai_difficult");
                                                break;
                                        }
                                    }
                                    break;
                                case 4:
                                    if (result.equals("You!")) {
                                        DatabaseUtil.updateInfo("player_easy", DatabaseUtil.player.id);
                                        DatabaseUtil.updateInfo("player_count", DatabaseUtil.player.id);
                                    } else {
                                        DatabaseUtil.updateInfo("player_easy", DatabaseUtil.opponent.id);
                                        DatabaseUtil.updateInfo("player_count", DatabaseUtil.opponent.id);
                                    }
                            }
                        } else if (Board.getNUMBER_0F_MIND_MEDIUM_LEVEL() == Save.model.getNumberOfMind()) {
                            switch (DifficultyDecision.model) {
                                case 3:
                                    if (result.equals("You!")) {
                                        DatabaseUtil.updateInfo("person_middle", DatabaseUtil.player.id);
                                        DatabaseUtil.updateInfo("person_count", DatabaseUtil.player.id);
                                        switch (DifficultyDecision.AItype) {
                                            case 1:
                                                DatabaseUtil.updateInfo("ai_easy", DatabaseUtil.player.id);
                                                DatabaseUtil.updateInfo("ai_count_easy", DatabaseUtil.player.id);
                                                DatabaseUtil.updateInfo("ai_count_easy", "ai_easy");
                                                break;
                                            case 2:
                                                DatabaseUtil.updateInfo("ai_middle", DatabaseUtil.player.id);
                                                DatabaseUtil.updateInfo("ai_count_middle", DatabaseUtil.player.id);
                                                DatabaseUtil.updateInfo("ai_count_middle", "ai_count_middle");
                                                break;
                                            case 3:
                                                DatabaseUtil.updateInfo("ai_difficult", DatabaseUtil.player.id);
                                                DatabaseUtil.updateInfo("ai_count_difficult", DatabaseUtil.player.id);
                                                DatabaseUtil.updateInfo("ai_count_difficult", "ai_difficult");
                                                break;
                                        }
                                    } else {
                                        DatabaseUtil.updateInfo("person_count_middle", DatabaseUtil.player.id);
                                        switch (DifficultyDecision.AItype) {
                                            case 1:
                                                DatabaseUtil.updateInfo("ai_easy", "ai_easy");
                                                DatabaseUtil.updateInfo("ai_count_easy", "ai_easy");
                                                break;
                                            case 2:
                                                DatabaseUtil.updateInfo("ai_middle", "ai_middle");
                                                DatabaseUtil.updateInfo("ai_count_middle", "ai_count_middle");
                                                break;
                                            case 3:
                                                DatabaseUtil.updateInfo("ai_difficult", "ai_difficult");
                                                DatabaseUtil.updateInfo("ai_count_difficult", "ai_difficult");
                                                break;
                                        }
                                    }
                                    break;
                                case 4:
                                    if (result.equals("You!")) {
                                        DatabaseUtil.updateInfo("player_middle", DatabaseUtil.player.id);
                                        DatabaseUtil.updateInfo("player_count", DatabaseUtil.player.id);
                                    } else {
                                        DatabaseUtil.updateInfo("player_middle", DatabaseUtil.opponent.id);
                                        DatabaseUtil.updateInfo("player_count", DatabaseUtil.opponent.id);
                                    }
                            }
                        } else if (Board.getNUMBER_0F_MIND_HIGH_LEVEL() == Save.model.getNumberOfMind()) {
                            switch (DifficultyDecision.model) {
                                case 3:
                                    if (result.equals("You!")) {
                                        DatabaseUtil.updateInfo("person_difficult", DatabaseUtil.player.id);
                                        DatabaseUtil.updateInfo("person_count_difficult", DatabaseUtil.player.id);
                                        switch (DifficultyDecision.AItype) {
                                            case 1:
                                                DatabaseUtil.updateInfo("ai_easy", DatabaseUtil.player.id);
                                                DatabaseUtil.updateInfo("ai_count_easy", DatabaseUtil.player.id);
                                                DatabaseUtil.updateInfo("ai_count_easy", "ai_easy");
                                                break;
                                            case 2:
                                                DatabaseUtil.updateInfo("ai_middle", DatabaseUtil.player.id);
                                                DatabaseUtil.updateInfo("ai_count_middle", DatabaseUtil.player.id);
                                                DatabaseUtil.updateInfo("ai_count_middle", "ai_count_middle");
                                                break;
                                            case 3:
                                                DatabaseUtil.updateInfo("ai_difficult", DatabaseUtil.player.id);
                                                DatabaseUtil.updateInfo("ai_count_difficult", DatabaseUtil.player.id);
                                                DatabaseUtil.updateInfo("ai_count_difficult", "ai_difficult");
                                                break;
                                        }
                                    } else {
                                        DatabaseUtil.updateInfo("person_count_difficult", DatabaseUtil.player.id);
                                        switch (DifficultyDecision.AItype) {
                                            case 1:
                                                DatabaseUtil.updateInfo("ai_easy", "ai_easy");
                                                DatabaseUtil.updateInfo("ai_count_easy", "ai_easy");
                                                break;
                                            case 2:
                                                DatabaseUtil.updateInfo("ai_middle", "ai_middle");
                                                DatabaseUtil.updateInfo("ai_count_middle", "ai_count_middle");
                                                break;
                                            case 3:
                                                DatabaseUtil.updateInfo("ai_difficult", "ai_difficult");
                                                DatabaseUtil.updateInfo("ai_count_difficult", "ai_difficult");
                                                break;
                                        }
                                    }
                                    break;
                                case 4:
                                    if (result.equals("You!")) {
                                        DatabaseUtil.updateInfo("player_difficult", DatabaseUtil.player.id);
                                        DatabaseUtil.updateInfo("player_count", DatabaseUtil.player.id);
                                    } else {
                                        DatabaseUtil.updateInfo("player_middle", DatabaseUtil.opponent.id);
                                        DatabaseUtil.updateInfo("player_count", DatabaseUtil.opponent.id);
                                    }
                            }
                        }
                        DifficultyDecision.time.stop();
                        if (DifficultyDecision.model != 4)
                            DifficultyDecision.timer.interrupt();
                    }
                }, 2000);
            }
        }
    }

    public static int coun1 = 0;
    public static int coun2 = 0;
    public static int coun3 = 0;
    public static int coun4 = 0;

    public static void PlayerColor(int current, BoardLocation x) {
        if (current == 1) {
            DifficultyDecision.nextRound.setVisible(false);
            if (Save.model.getNumberOfMind() < 20)
                Save.view1.getGridAt(x).setBorder(BorderFactory.createLineBorder(new Color(227, 26, 26, 225), 6));
            else if (Save.model.getNumberOfMind() < 40)
                Save.view1.getGridAt(x).setBorder(BorderFactory.createLineBorder(new Color(227, 26, 26, 225), 4));
            else
                Save.view1.getGridAt(x).setBorder(BorderFactory.createLineBorder(new Color(227, 26, 26, 225), 3));
            Save.model.getGrid()[x.getRow()][x.getColumn()].setPlayerMarked(true);

        } else if (current == 2) {
            if (Save.model.getNumberOfMind() < 20)
                Save.view1.getGridAt(x).setBorder(BorderFactory.createLineBorder(new Color(95, 60, 32, 225), 6));
            else if (Save.model.getNumberOfMind() < 40)
                Save.view1.getGridAt(x).setBorder(BorderFactory.createLineBorder(new Color(95, 60, 32, 225), 4));
            else
                Save.view1.getGridAt(x).setBorder(BorderFactory.createLineBorder(new Color(95, 60, 32, 225), 3));
            Save.model.getGrid()[x.getRow()][x.getColumn()].setPlayerMarked(true);
            if (coun2 < InputBox.rounds)
                coun2++;
            if (InputBox.players == 2 && coun2 == InputBox.rounds) {
                DifficultyDecision.nextRound.setVisible(true);
                coun2 = 0;
                for (int i = 0; i < Save.model.getRow(); i++) {
                    for (int i1 = 0; i1 < Save.model.getColumn(); i1++) {
                        if (Save.model.getGrid()[i][i1].isPlayerMarked()) {
                            Save.view1.getGridAt(Save.model.getGrid()[i][i1].getLocation()).setBorder(BorderFactory.createEmptyBorder());
                            Save.model.getGrid()[i][i1].setPlayerMarked(false);
                        }
                    }
                }
            }

        } else if (current == 3) {
            if (Save.model.getNumberOfMind() < 20)
                Save.view1.getGridAt(x).setBorder(BorderFactory.createLineBorder(new Color(115, 39, 238, 225), 6));
            else if (Save.model.getNumberOfMind() < 40)
                Save.view1.getGridAt(x).setBorder(BorderFactory.createLineBorder(new Color(115, 39, 238, 225), 4));
            else
                Save.view1.getGridAt(x).setBorder(BorderFactory.createLineBorder(new Color(115, 39, 238, 225), 3));
            Save.model.getGrid()[x.getRow()][x.getColumn()].setPlayerMarked(true);
            if (coun3 < InputBox.rounds)
                coun3++;
            if (InputBox.players == 3 && coun3 == InputBox.rounds) {
                DifficultyDecision.nextRound.setVisible(true);
                coun3 = 0;
                for (int i = 0; i < Save.model.getRow(); i++) {
                    for (int i1 = 0; i1 < Save.model.getColumn(); i1++) {
                        if (Save.model.getGrid()[i][i1].isPlayerMarked()) {
                            Save.view1.getGridAt(Save.model.getGrid()[i][i1].getLocation()).setBorder(BorderFactory.createEmptyBorder());
                            Save.model.getGrid()[i][i1].setPlayerMarked(false);
                        }
                    }
                }
            }

        } else {
            if (Save.model.getNumberOfMind() < 20)
                Save.view1.getGridAt(x).setBorder(BorderFactory.createLineBorder(new Color(255, 122, 0, 225), 6));
            else if (Save.model.getNumberOfMind() < 40)
                Save.view1.getGridAt(x).setBorder(BorderFactory.createLineBorder(new Color(255, 122, 0, 225), 4));
            else
                Save.view1.getGridAt(x).setBorder(BorderFactory.createLineBorder(new Color(255, 122, 0, 225), 3));

            Save.model.getGrid()[x.getRow()][x.getColumn()].setPlayerMarked(true);

            if (coun4 < InputBox.rounds)
                coun4++;
            if (InputBox.players == 4 && coun4 == InputBox.rounds) {
                DifficultyDecision.nextRound.setVisible(true);
                coun4 = 0;
                for (int i = 0; i < Save.model.getRow(); i++) {
                    for (int i1 = 0; i1 < Save.model.getColumn(); i1++) {
                        if (Save.model.getGrid()[i][i1].isPlayerMarked()) {
                            Save.view1.getGridAt(Save.model.getGrid()[i][i1].getLocation()).setBorder(BorderFactory.createEmptyBorder());
                            Save.model.getGrid()[i][i1].setPlayerMarked(false);
                        }
                    }
                }
            }

        }


    }

    public static void ShowAllMinds() {
//        System.out.println("????????????????");
        for (int i = 0; i < Save.model.getRow(); i++) {
            for (int i1 = 0; i1 < Save.model.getColumn(); i1++) {
                if (!Save.model.getGrid()[i][i1].isOpened() && Save.model.getGrid()[i][i1].hasLandMine()) {
                    Save.model.getGrid()[i][i1].setOpened(true);
                    Save.model.calculateNum(i, i1);
                    Save.view1.setItemAt(Save.model.getGrid()[i][i1].getLocation(), Save.model.getGrid()[i][i1].getNum());
                }
            }
        }
        Save.view1.repaint();
    }
}

