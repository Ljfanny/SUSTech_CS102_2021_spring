package xyz.view;

import xyz.model.BoardLocation;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.swing.*;
import java.util.Date;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.Thread.sleep;

public class GIF extends Thread {//动画？
    public static double x;
    public static double y;
    public Tools tools;

    public static ArrayList<Integer> getUse() {
        return use;
    }

    public static void setUse(ArrayList<Integer> use) {
        GIF.use = use;
    }

    static ArrayList<Integer> use;


    public GIF() {

    }

    //    public void paint(Graphics g) {
//        super.paint(g);
//        Graphics2D graph = (Graphics2D) g;
//        if (image != null) {
//            graph.drawImage(image, 0, 0, getWidth(), getHeight(), 0, 0, image.getWidth(null), image.getHeight(null), null);
//        }
//    }
    public static BoxFound donttai = new BoxFound();

    public void run() {
        while (true) {
            if (BeginFrame.mode == 2 || BeginFrame.mode == 4){
                try {
                    sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (Save.gameController.boxNumber != 0) {
                    if (!donttai.isRunning) {
                        donttai = new BoxFound();
//                    donttai.run();
                        new Thread(donttai).start();
                        donttai.idx = 1;
                        donttai.setVisible(true);
                        donttai.isRunning = true;
                    }
                    donttai.setVisible(false);
                    donttai.idx = -1;

                    if (x == -400) {
                        x = 250;
                    }else
                        x =-400;
                    tools.repaint();

                    if (x == 250) {
                        try {
                            sleep(1600);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    if (x < -300) {
                        Save.gameController.boxNumber = 0;
                        donttai.isRunning = false;

                    }
                } else {
                    x = -400;
                    y = 260;
                }
            }
            if(BeginFrame.mode==1) {
                DifficultyDecision.player1b.setVisible(true);
            }
            else if(Save.gameController.currentPlayer==1&&BeginFrame.mode==3){
                DifficultyDecision.player1d.setVisible(false);
                DifficultyDecision.player1b.setVisible(true);
                DifficultyDecision.computerd.setVisible(true);
            }
            else if(Save.gameController.currentPlayer==1&&BeginFrame.mode!=3&&BeginFrame.mode!=4){
                DifficultyDecision.player1d.setVisible(false);
                DifficultyDecision.player2d.setVisible(false);
                DifficultyDecision.player3d.setVisible(false);
                DifficultyDecision.player4b.setVisible(false);
                DifficultyDecision.computerd.setVisible(false);
                DifficultyDecision.player1b.setVisible(true);
                DifficultyDecision.player2d.setVisible(true);
                DifficultyDecision.player3d.setVisible(true);
                DifficultyDecision.player4d.setVisible(true);
                DifficultyDecision.computerd.setVisible(true);
            }
            else if(Save.gameController.currentPlayer==1&&BeginFrame.mode==4){
                DifficultyDecision.player1d.setVisible(false);
                DifficultyDecision.player4b.setVisible(false);
                DifficultyDecision.player1b.setVisible(true);
                DifficultyDecision.player4d.setVisible(true);
            }
            else if(Save.gameController.currentPlayer==2&&BeginFrame.mode==4){
                DifficultyDecision.player1b.setVisible(false);
                DifficultyDecision.player4d.setVisible(false);
                DifficultyDecision.player1d.setVisible(true);
                DifficultyDecision.player4b.setVisible(true);
            }
            else if(Save.gameController.currentPlayer==2&&BeginFrame.mode==2){
                DifficultyDecision.player1b.setVisible(false);
                DifficultyDecision.player2d.setVisible(false);
                DifficultyDecision.player3d.setVisible(false);
                DifficultyDecision.player4d.setVisible(false);
                DifficultyDecision.computerd.setVisible(false);
                DifficultyDecision.player1d.setVisible(true);
                DifficultyDecision.player2b.setVisible(true);
                DifficultyDecision.player3d.setVisible(true);
                DifficultyDecision.player4d.setVisible(true);
                DifficultyDecision.computerb.setVisible(true);
            }
            else if(Save.gameController.currentPlayer==3){
                DifficultyDecision.player1d.setVisible(false);
                DifficultyDecision.player2b.setVisible(false);
                DifficultyDecision.player3d.setVisible(false);
                DifficultyDecision.player4d.setVisible(false);
                DifficultyDecision.computerb.setVisible(false);
                DifficultyDecision.player1d.setVisible(true);
                DifficultyDecision.player2d.setVisible(true);
                DifficultyDecision.player3b.setVisible(true);
                DifficultyDecision.player4d.setVisible(true);
                DifficultyDecision.computerd.setVisible(true);
            }
            else {
                DifficultyDecision.player1d.setVisible(false);
                DifficultyDecision.player2d.setVisible(false);
                DifficultyDecision.player3b.setVisible(false);
                DifficultyDecision.player4d.setVisible(false);
                DifficultyDecision.computerd.setVisible(false);
                DifficultyDecision.player1d.setVisible(true);
                DifficultyDecision.player2d.setVisible(true);
                DifficultyDecision.player3d.setVisible(true);
                DifficultyDecision.player4b.setVisible(true);
                DifficultyDecision.computerd.setVisible(true);
            }

        }

    }

}
