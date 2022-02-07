package xyz.view;

import javax.swing.*;
import java.awt.*;

public class VideoPlayer extends Thread {
    public static JFrame jFrame;
    public static Video video=new Video();
    int num = 0;

    public VideoPlayer() {
        jFrame = new JFrame();
        jFrame.setSize(1624, 1050);
        jFrame.getContentPane().setBackground(Color.BLACK);
//        if (x == 1) {
//            jFrame.add(video.getVideo("one.mp4"));
//            num = 1;
//        } else if (x == 2) {
//            jFrame.add(video.getVideo("many.mp4"));
//            num = 2;
//        } else if (x == 3) {
//            jFrame.add(video.getVideo("computer.mp4"));
//            num = 3;
//        } else if (x == 4) {
//            jFrame.add(video.getVideo("connect.mp4"));
//            num = 4;
//        } else
        jFrame.add(video.getVideo("start_1_2.mp4"));

        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
        jFrame.setResizable(false);
    }

    @Override
    public void run() {
        try {

            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        jFrame.dispose();
    }
}