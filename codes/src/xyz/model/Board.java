package xyz.model;

import xyz.server.NetTool;
import xyz.view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Board {
    private Square[][] grid;
    private int row;
    private int column;
    private static int NUMBER_0F_MIND_LOW_LEVEL = 10;
    private static int NUMBER_0F_MIND_MEDIUM_LEVEL = 40;
    private static int NUMBER_0F_MIND_HIGH_LEVEL = 99;
    private int numberOfMind;


    public void getBox() {
        Random temp = new Random();
        int x;
        int y;
        int co;
        int counter = 0;
        int max = ((Save.model.numberOfMind / 5) + 1);

        while (counter < max) {
            co =temp.nextInt(6);
//            if (DifficultyDecision.model == 4) {
//                while (co == 1)
//                    co = temp.nextInt(6);
//            }
            x = temp.nextInt(Save.model.getRow());
            y = temp.nextInt(Save.model.getColumn());
            if (!getGrid()[x][y].isBox() && getGrid()[x][y].hasLandMine()) {
                getGrid()[x][y].setBoxNum(co + 1);
                getGrid()[x][y].setBox(true);
                if (BeginFrame.mode == 4 && PVPGameFrame.getIp() != null) {
                    NetTool.sendUDPBroadCast(PVPGameFrame.getIp(), "Function" + "," + x + "," + y + "," + getGrid()[x][y].getBoxNum());
                }
                counter++;
            }
        }
    }


    public Board() {

    }

    public Board(int row, int col) {
        grid = new Square[row][col];
        this.column = col;
        this.row = row;
        iniGrid();
//        iniItem();
    }


    public void iniGrid() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                grid[i][j] = new Square(new BoardLocation(i, j));
            }
        }
    }

    public void iniNet(int[][] mins) {
        for (int i = 0; i < mins.length; i++) {
            for (int j = 0; j < mins[0].length; j++) {
                grid[i][j].setNumberOfLandMine((byte) mins[i][j]);
                if (mins[i][j] < 0)
                    grid[i][j].setHasLandMine(true);
            }
        }
    }

    public void inBox(String[] x) {
        grid[Integer.parseInt(x[1])][Integer.parseInt(x[2])].setBox(true);
        grid[Integer.parseInt(x[1])][Integer.parseInt(x[2])].setBoxNum(Integer.parseInt(x[3]));
    }


    public void iniItem(int a, int b) {
        iniGrid();
        Random row = new Random();
        Random column = new Random();
        int counter = 0;
        int cnt = 0;
        while (counter < getNumberOfMind()) {
            int x = 0;
            int y = 0;
            x = row.nextInt(getRow());
            y = column.nextInt(getColumn());
            if (!(x == a && y == b) && !grid[x][y].hasLandMine()) {
                if (!(x == a - 1 && y == b) && !(x == a + 1 && y == b) && !(x == a && y == b + 1) && !(x == a && y == b - 1) && !(x == a - 1 && y == b - 1) && !(x == a - 1 && y == b + 1) && !(x == a + 1 && y == b - 1) && !(x == a + 1 && y == b + 1)) {
                    grid[x][y].setHasLandMine(true);
                    int t = -3 - column.nextInt(15);
                    grid[x][y].setNumberOfLandMine((byte) t);
                    counter++;
                }
            }
        }

        for (int i = 0; i < getRow(); i++) {
            for (int j = 0; j < getColumn(); j++) {
                if (grid[i][j].getNumberOfLandMineRound() > 5) {
                    cnt++;
                }
            }
        }

        if (cnt > 0)
            iniItem(a, b);
    }

    public int calculateNum(int i, int j) {
        byte temp = 0;
        if (i - 1 >= 0 && grid[i - 1][j].hasLandMine())
            temp++;
        if (i + 1 < getRow() && grid[i + 1][j].hasLandMine())
            temp++;
        if (j + 1 < getColumn() && grid[i][j + 1].hasLandMine())
            temp++;
        if (i - 1 >= 0 && j + 1 < getColumn() && grid[i - 1][j + 1].hasLandMine())
            temp++;
        if (i + 1 < getRow() && j + 1 < getColumn() && grid[i + 1][j + 1].hasLandMine())
            temp++;
        if (j - 1 >= 0 && grid[i][j - 1].hasLandMine())
            temp++;
        if (i - 1 >= 0 && j - 1 >= 0 && grid[i - 1][j - 1].hasLandMine())
            temp++;
        if (i + 1 < getRow() && j - 1 >= 0 && grid[i + 1][j - 1].hasLandMine())
            temp++;
        grid[i][j].setNumberOfLandMineRound(temp);

        if (!grid[i][j].hasLandMine()) {
            grid[i][j].setNumberOfLandMine(temp);
            return temp;
        } else {
            return -1;
        }
    }

    public Square getGridAt(BoardLocation location) {
        return grid[location.getRow()][location.getColumn()];
    }

    public int getNumAt(BoardLocation location) {
        return getGridAt(location).getNum();
    }

    public void openGrid(BoardLocation location) {
        getGridAt(location).setOpened(true);
    }

    public void flagGrid(BoardLocation location) {
        getGridAt(location).setFlag(true);
    }

    // click type == 1 means that is left click
    // click type == 2 means that is middle click
    // click type == 3 means that is right click
    public boolean isValidClick(BoardLocation location, int clickType) {
        // TODO: You should implement a method here to check whether it is a valid action
        switch (clickType) {
            case 1:
            case 2:
                if (!getGridAt(location).isOpened() && !getGridAt(location).isFlag()) return true;
                else return false;
            default:
                return true;
        }
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Square[][] getGrid() {
        return grid;
    }

    public int getNumberOfMind() {
        return numberOfMind;
    }

    public void setNumberOfMind(int numberOfMind) {
        this.numberOfMind = numberOfMind;
    }

    public static int getNUMBER_0F_MIND_LOW_LEVEL() {
        return NUMBER_0F_MIND_LOW_LEVEL;
    }

    public static int getNUMBER_0F_MIND_MEDIUM_LEVEL() {
        return NUMBER_0F_MIND_MEDIUM_LEVEL;
    }

    public static int getNUMBER_0F_MIND_HIGH_LEVEL() {
        return NUMBER_0F_MIND_HIGH_LEVEL;
    }

    //function-set ↓
    public void setToushi() {
        DifficultyDecision.b1.requestFocus();
        if (Save.view2.getFounedmine() != Save.model.numberOfMind) {

            if (((DifficultyDecision.model == 2 || DifficultyDecision.model == 4) && Function.Toushi.canUse) || ((DifficultyDecision.model == 1 || DifficultyDecision.model == 3) && Function.alwaysUse)) {
                int[][] current = new int[row][column];
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < column; j++) {
                        if (grid[i][j].isOpened() && !grid[i][j].isFlag())
                            current[i][j] = 1;
                        else if (!grid[i][j].isOpened())
                            current[i][j] = 0;
                        else if (grid[i][j].isOpened() && grid[i][j].isFlag())
                            current[i][j] = 2;
                    }
                }
                for (int i = 0; i < row; i++) {
                    for (int j = 0; j < column; j++) {
                        if (current[i][j] == 0) {
                            grid[i][j].setOpened(true);
                            Save.model.calculateNum(i, j);
                            Save.view1.setItemAt(grid[i][j].getLocation(), grid[i][j].getNum());
                        }
                    }
                }
                Save.view1.repaint();
                java.util.Timer temp = new Timer();
                temp.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        for (int i = 0; i < row; i++) {
                            for (int j = 0; j < column; j++) {
                                if (current[i][j] == 0) {
                                    grid[i][j].setOpened(false);
                                    Save.view1.setItemAt(grid[i][j].getLocation(), grid[i][j].getNum());
                                }
                            }
                        }
                        Save.view1.repaint();
                    }
                }, 1000);
                Function.Toushi.canUse = false;
            } else if (DifficultyDecision.model == 3 || DifficultyDecision.model == 1) {
                Object[] options = {"OK "};
                JOptionPane.showOptionDialog(null, "You can't use it! ", "Prompt", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            }
        }
    }

    public void setZhadan() throws IOException, InterruptedException {
        DifficultyDecision.b1.requestFocus();
        if (((DifficultyDecision.model == 2 || DifficultyDecision.model == 4) && Function.Zhadan.canUse) || ((DifficultyDecision.model == 1 || DifficultyDecision.model == 3) && Function.alwaysUse)) {
            if (DifficultyDecision.model == 2 || DifficultyDecision.model == 4) {
                Random suiji = new Random();
                int counter = 0;
                while (counter < 1) {
                    int x = suiji.nextInt(Save.model.getRow());
                    int y = suiji.nextInt(Save.model.getColumn());


                    if (!grid[x][y].isOpened()) {
                        if (BeginFrame.mode == 4 && PVPGameFrame.getIp() != null) {
                            NetTool.sendUDPBroadCast(PVPGameFrame.getIp(), "Zha" + "," + x + "," + y + "," + "local");
                        }
                        SquareComponent.judgeZhadan(x, y);
                        SquareComponent.judgeZhadan(x - 1, y);
                        SquareComponent.judgeZhadan(x + 1, y);
                        SquareComponent.judgeZhadan(x, y - 1);
                        SquareComponent.judgeZhadan(x, y + 1);
                        counter++;
                        Function.Zhadan.open = false;
                        Function.Zhadan.canUse = false;
                    }
                }
            } else {
                Function.Zhadan.open = !Function.Zhadan.open;
                Function.Zhadan.canUse = false;
            }

        } else if (DifficultyDecision.model == 3 || DifficultyDecision.model == 1) {
            Object[] options = {"OK "};
            JOptionPane.showOptionDialog(null, "You can't use it! ", "Prompt", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, options, options[0]);
        }
    }

    public void setZhadan(int x, int y) throws IOException, InterruptedException {
//        DifficultyDecision.b1.requestFocus();
//        if (((DifficultyDecision.model == 2 || DifficultyDecision.model == 4) && Function.Zhadan.canUse) || ((DifficultyDecision.model == 1 || DifficultyDecision.model == 3) && Function.alwaysUse)) {
//            if (DifficultyDecision.model == 2 || DifficultyDecision.model == 4) {
//                int counter = 0;
//                while (counter < 1) {
//                    if (!grid[x][y].isOpened()) {
        SquareComponent.judgeZhadan(x, y);
        SquareComponent.judgeZhadan(x - 1, y);
        SquareComponent.judgeZhadan(x + 1, y);
        SquareComponent.judgeZhadan(x, y - 1);
        SquareComponent.judgeZhadan(x, y + 1);
        Function.Zhadan.open = false;
        Function.Zhadan.canUse = false;
//                    }
//                }
//            } else {
//                Function.Zhadan.open = !Function.Zhadan.open;
//                Function.Zhadan.canUse = false;
//            }
//
//        } else if (DifficultyDecision.model == 3 || DifficultyDecision.model == 1) {
//            Object[] options = {"OK "};
//            JOptionPane.showOptionDialog(null, "You can't use it! ", "Prompt", JOptionPane.DEFAULT_OPTION,
//                    JOptionPane.WARNING_MESSAGE, null, options, options[0]);
//        }
    }


    public void setaddonepoint() {
        if (DifficultyDecision.model == 2 || DifficultyDecision.model == 4 || (Function.alwaysUse && DifficultyDecision.model == 3)) {
            Save.view2.Goal(Save.gameController.currentPlayer - 1);
            Save.view2.repaint();
            Function.addOpenPoint.canUse = false;
        } else if (DifficultyDecision.model == 3 || DifficultyDecision.model == 1) {
            Object[] options = {"OK "};
            JOptionPane.showOptionDialog(null, "You can't use it! ", "Prompt", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, options, options[0]);
        }
    }

    public void setaddtwopoint() {
        if (DifficultyDecision.model == 2 || DifficultyDecision.model == 4 || (Function.alwaysUse && DifficultyDecision.model == 3)) {
            Save.view2.Goal(Save.gameController.currentPlayer - 1);
            Save.view2.Goal(Save.gameController.currentPlayer - 1);
            Save.view2.repaint();
            Function.addTwoPoint.canUse = false;
        } else if (DifficultyDecision.model == 3 || DifficultyDecision.model == 1) {
            Object[] options = {"OK "};
            JOptionPane.showOptionDialog(null, "You can't use it! ", "Prompt", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, options, options[0]);
        }
    }

    public void setdecreaseonepoint() {
        if (DifficultyDecision.model == 2 || DifficultyDecision.model == 4 || (Function.alwaysUse && DifficultyDecision.model == 3)) {
            Save.view2.Lose(Save.gameController.currentPlayer - 1);
            Save.view2.repaint();
            Function.decreaseOnePoint.canUse = false;
        } else if (DifficultyDecision.model == 3 || DifficultyDecision.model == 1) {
            Object[] options = {"OK "};
            JOptionPane.showOptionDialog(null, "You can't use it! ", "Prompt", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, options, options[0]);
        }
    }

    public void setdecreasetwopoint() {
        if (DifficultyDecision.model == 2 || DifficultyDecision.model == 4 || (Function.alwaysUse && DifficultyDecision.model == 3)) {

            Save.view2.Lose(Save.gameController.currentPlayer - 1);
            Save.view2.Lose(Save.gameController.currentPlayer - 1);
            Save.view2.repaint();
            Function.decreaseTwoPoint.canUse = false;
        } else if (DifficultyDecision.model == 3 || DifficultyDecision.model == 1) {
            Object[] options = {"OK "};
            JOptionPane.showOptionDialog(null, "You can't use it! ", "Prompt", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, options, options[0]);
        }
    }

}
