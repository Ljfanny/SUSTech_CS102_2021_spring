package xyz.view;

import xyz.database.DatabaseUtil;
import xyz.database.UserInformation;

import javax.swing.*;
import java.awt.*;

public class DetailInformation extends JFrame {
    DetailInformation(){
        setSize(450,500);
        setLocationRelativeTo(null);
        JTextArea jLabel =new JTextArea();
        jLabel.setSize(450,500);
        jLabel.setLayout(new GridBagLayout());
        jLabel.setFont(new Font("微软雅黑",Font.PLAIN,20));
        jLabel.setText(DatabaseUtil.player.detail());
        add(jLabel);
        setVisible(true);
    }

    public void paint(Graphics g) {
        super.paint(g);
        String str = null;
        str =DatabaseUtil.player.detail();
        g.drawString(str, 10, 10);
    }
}