package xyz;

import xyz.controller.GameController;
import xyz.database.DatabaseUtil;
import xyz.model.*;
import xyz.view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;


public class Main {
    public static BeginFrame background;
    public static Thread v;

    public static void main(String[] args) {
        new Thread(new VideoPlayer()).start();
        java.util.Timer temp = new Timer();
        temp.schedule(new TimerTask() {
            @Override
            public void run() {
        background = new BeginFrame();
        background.setVisible(true);
            }
        }, 10000);


    }

    public static JFrame getBeginFrame() {
        return background;
    }
}

