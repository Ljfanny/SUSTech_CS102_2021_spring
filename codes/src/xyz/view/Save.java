package xyz.view;

import xyz.controller.GameController;
import xyz.listener.GameListener;
import xyz.model.Board;
import xyz.model.BoardLocation;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class Save {
    public static BoardComponent view1;
    public static ScoreBoard view2;
    public static WinnerComponent view3;
    public static Board model;
    public static int currentPlayer;
    public static GameController gameController;

    public static void saver(GameController gameController, BoardComponent view1, ScoreBoard view2, Board model, int currentPlayer){
        Save.setModel(model);
        Save.setView1(view1);
        Save.setView2(view2);
        Save.setCurrentPlayer(currentPlayer);
        Save.gameController=gameController;
    }

    public static void output(File file) throws IOException {
        PrintWriter pw = new PrintWriter(file);
        pw.println(model.getRow());
        pw.println(model.getColumn());

        for (int i = 0; i < model.getRow(); i++) {
            for (int j = 0; j < model.getColumn(); j++) {
                BoardLocation location = new BoardLocation(i,j);
                pw.println(model.getGridAt(location).toString());
            }
        }

        for (int i = 0; i < ScoreBoard.getPerson(); i++) {
            pw.println(view2.getScoreBoard()[0][i]);
            pw.println(view2.getScoreBoard()[1][i]);
            pw.println(view2.getScoreBoard()[2][i]);
        }

        pw.println(currentPlayer);
        pw.println(view2.founedmine);
        if(BeginFrame.mode==2)
        pw.println(InputBox.rounds);
        pw.close();
    }

    public static void input(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader bf = new BufferedReader(fr);
        int row = Integer.parseInt(bf.readLine());
        int column = Integer.parseInt(bf.readLine());
        int totalNum = row*column;
        ArrayList<String> list = new ArrayList<>();
        String t = null;
        while ((t = bf.readLine())!=null){
            list.add(t);
        }
        int cnt1 = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                BoardLocation location = new BoardLocation(i,j);
                String []temp = list.get(cnt1).split(" ");
                if(temp[0].equals("true")){
                    view1.setItemAt(location,Integer.parseInt(temp[3]));
                }
                else
                    view1.setItemAt(location,-2);
                cnt1++;
                if(cnt1==totalNum)
                    break;
            }
        }
        int cnt2 = 0;
        for (int i = 0; i < model.getRow(); i++) {
            for (int j = 0; j < model.getColumn(); j++) {
                BoardLocation location = new BoardLocation(i,j);
                String []temp = list.get(cnt2).split(" ");
                if(temp[0].equals("true"))
                    model.getGridAt(location).setOpened(true);
                else
                    model.getGridAt(location).setOpened(false);
                if(temp[1].equals("true"))
                    model.getGridAt(location).setFlag(true);
                else
                    model.getGridAt(location).setFlag(false);
                if(temp[2].equals("true"))
                    model.getGridAt(location).setHasLandMine(true);
                else
                    model.getGridAt(location).setHasLandMine(false);
                model.getGridAt(location).setNumberOfLandMine(Byte.parseByte(temp[3]));
                model.getGridAt(location).setNumberOfLandMineRound(Byte.parseByte(temp[4]));
                if(temp[5].equals("true"))
                    model.getGridAt(location).setClick(true);
                else
                    model.getGridAt(location).setClick(false);
                model.getGridAt(location).setBoxNum(Integer.parseInt(temp[6]));
                if(temp[7].equals("true"))
                    model.getGridAt(location).setBox(true);
                else
                    model.getGridAt(location).setBox(false);
                cnt2++;
                if(cnt2==totalNum)
                    break;
            }
        }
        int cnt3 = 0;
        for (int i = 0; i < ScoreBoard.getPerson(); i++) {
            view2.setScoreBoard(0,i,Integer.parseInt(list.get(totalNum+cnt3)));
            cnt3++;
            view2.setScoreBoard(1,i,Integer.parseInt(list.get(totalNum+cnt3)));
            cnt3++;
            view2.setScoreBoard(2,i,Integer.parseInt(list.get(totalNum+cnt3)));
            cnt3++;
        }
        currentPlayer = Integer.parseInt(list.get(totalNum+ScoreBoard.getPerson()*3));
        view2.setFounedmine(Integer.parseInt(list.get(totalNum+ScoreBoard.getPerson()*3+1)));
        view1.repaint();
        view2.repaint();
        gameController.first=false;
        bf.close();
        fr.close();
    }

    public static BoardComponent getView1() {
        return view1;
    }

    public static void setView1(BoardComponent view1) {
        Save.view1 = view1;
    }

    public static ScoreBoard getView2() {
        return view2;
    }

    public static void setView2(ScoreBoard view2) {
        Save.view2 = view2;
    }

    public static Board getModel() {
        return model;
    }

    public static void setModel(Board model) {
        Save.model = model;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public static void setCurrentPlayer(int currentPlayer) {
        Save.currentPlayer = currentPlayer;
    }
}
