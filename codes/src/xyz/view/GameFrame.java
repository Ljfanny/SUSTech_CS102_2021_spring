package xyz.view;
import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    public GameFrame() {



        setTitle("Mine clearance");
        setSize(1050, 900);
        setResizable(false);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);


    }
    public GameFrame(String x){

        setTitle("Mine clearance");
        setSize(1450,750);
        setResizable(false);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);


    }
}
