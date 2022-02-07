package xyz.view;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;

public class ScoreBoard extends JFrame {
    private final int[][] scoreBoard;
    private static Font font;
    private static int person ;

    int founedmine = 0;

    public int getFounedmine() {
        return founedmine;
    }

    public void setFounedmine(int founedmine) {
        this.founedmine = founedmine;
    }

    public int findMine() {
        founedmine++;
        return founedmine;
    }

    public static void setPerson(int person) {
        ScoreBoard.person = person;
    }

    static {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT,
                    new FileInputStream("src/xyz/view/Font/Denise Sans.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        font = font.deriveFont(Font.PLAIN, 30);
    }

    public ScoreBoard() {
        setTitle("Score Board");
        setPerson(InputBox.players);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~" + InputBox.players);
        setSize(400, 240 * person + 80);
//        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        this.getContentPane().setBackground(Color.WHITE);
        this.setFont(font);
        this.setForeground(new Color(250, 255, 200));
        scoreBoard = new int[3][person];
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        String str1, str2, str3;
        for (int i = 0; i < person; i++) {
            str1 = String.format("Player%d-score:", i + 1);
            g.drawString(str1, 40, 80 * (i + 1));
            str2 = String.format("Player%d-lose:", i + 1);
            g.drawString(str2, 40, 80 * (i + person + 1));
            str3 = String.format("Player%d-tatal:", i + 1);
            g.drawString(str3, 40, 80 * (i + 2 * person + 1));
        }

        for (int i = 0; i < person; i++) {
            Count(i);
        }

        for (int i = 0; i < person; i++) {
            g.drawString("" + scoreBoard[0][i], 300, 80 * (i + 1));
            g.drawString("" + scoreBoard[1][i], 300, 80 * (i + person + 1));
            g.drawString("" + scoreBoard[2][i], 300, 80 * (i + 2 * person + 1));
        }
    }

    public void Goal(int player) {
        scoreBoard[0][player]++;
        Count(player);
    }

    public void Delete(int player) {
        scoreBoard[0][player]--;
        Count(player);
    }

    public void Lose(int player) {
        scoreBoard[1][player]++;
        Count(player);
    }

    public void Count(int player) {
        scoreBoard[2][player] = scoreBoard[0][player] - scoreBoard[1][player];
    }

    public static int getPerson() {
        return person;
    }

    public int[][] getScoreBoard() {
        return scoreBoard;
    }

    public int getScoreBoard(int x, int y) {
        return scoreBoard[x][y];
    }

    public void setScoreBoard(int x, int y, int num) {
        this.scoreBoard[x][y] = num;
    }
}
