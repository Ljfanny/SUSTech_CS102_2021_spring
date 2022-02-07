package xyz.view;

import javax.swing.JFrame;
import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class InputBox {

    static myFrame frame;

    void createGUI(int model, JFrame father) {
        setModelNum(model);
        setFather(father);
        //创建一个窗口，创建一个窗口
        frame = new myFrame("Input");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        //设置窗口大小
       if (model==2 &&TypeFrame.firsttime)
           frame.setSize(250,170);
       else
           frame.setSize(250,270);
        frame.setLocationRelativeTo(null);
        //显示窗口
        frame.setVisible(true);

    }

    void createGUI(int model) {
        setModelNum(model);
        //创建一个窗口，创建一个窗口
        frame = new myFrame("Input");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        //设置窗口大小
        if (model==2 &&TypeFrame.firsttime)
            frame.setSize(250,170);
        else
            frame.setSize(250,270);
        frame.setLocationRelativeTo(null);
        //显示窗口
        frame.setVisible(true);

    }

    public void setModelNum(int x) {
        model = x;
    }

    public void setFather(JFrame father) {
        this.father = father;
    }

    public int ro;
    public int model;
    public int col;
    public int minds;
    public static int players=2;
    public static int rounds=1;
    public JFrame father;

    class myFrame extends JFrame {
        JLabel labelRow = new JLabel("Row: ");
        JLabel labelColumn = new JLabel("Column: ");
        JLabel labelMindNum = new JLabel("Mind Number: ");
        JLabel labelPlayerNum = new JLabel("Player Number: ");
        JLabel labelRoundNum = new JLabel("Round Number: ");

        //创建JTextField，16表示16列，用于JTextField的宽度显示而不是限制字符个数
        JTextField textField1 = new JTextField(20);
        JTextField textField2 = new JTextField(20);
        JTextField textField3 = new JTextField(20);
        JTextField textField4 = new JTextField(20);
        JTextField textField5 = new JTextField(20);
        JButton button = new JButton("OK");

        //构造函数
        public myFrame(String title) {
            //继承父类，
            super(title);

            //内容面板
            Container contentPane = getContentPane();
            contentPane.setLayout(new FlowLayout());
            setResizable(false);
            //添加控件

            if (model == 2 && TypeFrame.firsttime) {

                contentPane.add(labelPlayerNum);
                contentPane.add(textField4);
                contentPane.add(labelRoundNum);
                contentPane.add(textField5);
                contentPane.add(button);
                button.addActionListener((e) -> {
                    onButtonOk();
                });
            } else {

                contentPane.add(labelRow);
                contentPane.add(textField1);
                contentPane.add(labelColumn);
                contentPane.add(textField2);
                contentPane.add(labelMindNum);
                contentPane.add(textField3);
                contentPane.add(button);
                button.addActionListener((e) -> {
                    onButtonOk();
                });
            }
        }

        //事件处理
        private void onButtonOk() {
            int temp = 0;
            int temp1 = 0;
            String row = textField1.getText();
            String column = textField2.getText();
            String mindnum = textField3.getText();
            String playernum = textField4.getText();
            String roundnum = textField5.getText();
            if (model == 2 && TypeFrame.firsttime) {
                if (playernum.equals("")) {
                    Object[] options = {"OK "};
                    JOptionPane.showOptionDialog(null, "You haven't typed the number of players yet! ", "Prompt", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                } else if (Integer.parseInt(playernum) > 4) {
                    Object[] options = {"OK "};
                    JOptionPane.showOptionDialog(null, "Player Numbers are too big! ", "Prompt", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                } else if (Integer.parseInt(playernum) < 2) {
                    Object[] options = {"OK "};
                    JOptionPane.showOptionDialog(null, "Player Numbers are too small! ", "Prompt", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                } else temp1++;

                if (roundnum.equals("")) {
                    Object[] options = {"OK "};
                    JOptionPane.showOptionDialog(null, "You haven't typed the number of rounds yet! ", "Prompt", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                } else if (Integer.parseInt(roundnum) > 5) {
                    Object[] options = {"OK "};
                    JOptionPane.showOptionDialog(null, "Round Numbers are too big! ", "Prompt", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                } else if (Integer.parseInt(roundnum) <1) {
                    Object[] options = {"OK "};
                    JOptionPane.showOptionDialog(null, "The number of rounds is too small! ", "Prompt", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                } else temp1++;

            } else {
                if (row.equals("")) {
                    Object[] options = {"OK "};
                    JOptionPane.showOptionDialog(null, "You haven't typed row yet! ", "Prompt", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                } else if (Integer.parseInt(row) > 24) {
                    Object[] options = {"OK "};
                    JOptionPane.showOptionDialog(null, "The number of row is too big! ", "Prompt", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                }else if (Integer.parseInt(row) <4) {
                    Object[] options = {"OK "};
                    JOptionPane.showOptionDialog(null, "The number of row is too small! ", "Prompt", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE, null, options, options[0]);}
                else temp++;


                if (column.equals("")) {
                    Object[] options = {"OK "};
                    JOptionPane.showOptionDialog(null, "You haven't typed column yet! ", "Prompt", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                } else if (Integer.parseInt(column) > 30) {
                    Object[] options = {"OK "};
                    JOptionPane.showOptionDialog(null, "The number of column is too big! ", "Prompt", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                } else if (Integer.parseInt(column) <4) {
                    Object[] options = {"OK "};
                    JOptionPane.showOptionDialog(null, "The number of column is too small! ", "Prompt", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE, null, options, options[0]);}
                else temp++;


                if (mindnum.equals("")) {
                    Object[] options = {"OK "};
                    JOptionPane.showOptionDialog(null, "You haven't typed the number of minds yet! ", "Prompt", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                } else if (Integer.parseInt(mindnum) >= (0.5 * (Integer.parseInt(row) * Integer.parseInt(column)))) {
                    Object[] options = {"OK "};
                    JOptionPane.showOptionDialog(null, "That's a lot of minds! ", "Prompt", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                } else if (Integer.parseInt(column) <1) {
                    Object[] options = {"OK "};
                    JOptionPane.showOptionDialog(null, "The number of minds is too small! ", "Prompt", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE, null, options, options[0]);}
                else temp++;
            }


            int row2;
            int column2;
            int round2;
            int[] big = new int[24];
            big[0] = 43;
            big[1] = 43;
            big[2] = 43;
            big[3] = 43;
            big[4] = 43;
            int co = 43;
            for (int i = 5; i < big.length; i++) {
                big[i] = --co;
            }
            if (temp == 3) {
                frame.setResizable(false);
                frame.setVisible(false);
                row2 = Integer.parseInt(row);
                column2 = Integer.parseInt(column);
//                round2 = Integer.parseInt(roundnum);
                ro = row2;
                col = column2;
//                rounds = round2;
                minds = Integer.parseInt(mindnum);
//                ScoreBoard.setPerson(players);
                DifficultyDecision.setLevel(ro, col, minds, ro * big[ro - 1], col * big[ro - 1], model);
            }
            if (temp1 == 2) {
                frame.setResizable(false);
                frame.setVisible(false);
                TypeFrame.firsttime = false;
                TypeFrame.TypeChoice(father, model);
                round2 = Integer.parseInt(roundnum);
                rounds = round2;
                players = Integer.parseInt(playernum);
            }


        }
    }

    public int getRo() {
        return ro;
    }

    public int getCol() {
        return col;
    }

    public int getMinds() {
        return minds;
    }

    public int getPlayers() {
        return players;
    }

    public int getRounds() {
        return rounds;
    }

    public static void setRounds(int x){
        rounds = x;
    }
}
