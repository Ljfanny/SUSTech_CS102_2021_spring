package xyz.view;

import xyz.controller.GameController;
import xyz.database.DatabaseUtil;
import xyz.database.UserInformation;
import xyz.model.Board;
import xyz.model.BoardLocation;
import xyz.server.NetTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Logger;

public class PVPGameFrame extends GameFrame implements MouseListener, ActionListener {
    public JLabel label_timeCount;//计时
    public JLabel timecount;
    public TimeThread timer;
    public int result = 1;

    public TimeThread getTimer() {
        return timer;
    }

    public JLabel getLabel() {
        return label_timeCount;
    }


    private GameController gc = Save.gameController;
    private JButton startGame;
    private JButton exitGame;
    private JButton back;//悔棋按钮
    private JButton send; //聊天发送按钮
    //    private JLabel timecount;//计时器标签
    //双方状态
    private JLabel people1;//自己标签
    private JLabel people2;//对手标签
    private JLabel p1lv;//自己等级标签
    private JLabel p2lv;//对手等级标签
    private JLabel situation1;//自己状态标签
    private JLabel situation2;//对手状态标签
    private JLabel jLabel1;
    private JLabel jLabel2;//
    private JTextArea talkArea;
    private JTextField tf_ip; //输入IP框
    private JTextField talkField; //聊天文本框
    private static String ip;
    private DatagramSocket socket;
    private String gameState;
    private String enemyGameState;//敌人状态
    private Logger logger = Logger.getLogger("游戏");
    private boolean judge=false;

    public JButton getstart() {
        return startGame;
    }

    public static String getIp() {
        return ip;
    }

    public JTextField getTf() {
        return tf_ip;
    }

    public DatagramSocket getSocket() {
        return socket;
    }

    public JLabel getLabel1() {
        return jLabel1;
    }

    public JLabel getLabel2() {
        return jLabel2;
    }

    public JLabel getSituation1() {
        return situation1;
    }

    public JLabel getSituation2() {
        return situation2;
    }

    public JLabel current;
    public static final int CAN_NOT_CLICK_INFO = 0;
    public static final int CAN_CLICK_INFO = 1;

    public PVPGameFrame() {
//        setTitle("Mine clearance");
//        setSize(1050, 900);
//        setResizable(false);
//        setLocationRelativeTo(null); // Center the window.
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        setLayout(null);
        init();
    }


    public void init() {
//        label_timeCount = new JLabel();
//        label_timeCount.setFont(new Font("宋体", Font.BOLD, 30));
//        label_timeCount.setBounds(410, 770, 500, 50);
//        add(label_timeCount);

        gameState = "NOT_START";
        enemyGameState = "NOT_START";
//        cb=new PPChessBoard(this);
        gc.setClickable(CAN_NOT_CLICK_INFO);


//        cb.setBounds(210, 40, 570, 585);
//        cb.setVisible(true);
//

//        cb.setInfoBoard(talkArea);
        tf_ip = new JTextField("请输入对手IP地址");
        if (Save.model.getNumberOfMind() != 99)
            tf_ip.setBounds(610, 8, 230, 30);
        else
            tf_ip.setBounds(610, 15, 230, 30);


        tf_ip.addMouseListener(this);


        startGame = new JButton("准备游戏");//设置名称，下同
        if (Save.model.getNumberOfMind() != 99)
            startGame.setBounds(625, 43, 190, 45);//设置起始位置，宽度和高度，下同
        else
            startGame.setBounds(845, 10, 190, 45);
        startGame.setBackground(new Color(245, 212, 20));//设置颜色，下同
        startGame.setFont(new Font("宋体", Font.BOLD, 20));//设置字体，下同


        startGame.addActionListener(this);

//
//        back=new JButton("悔  棋");
//        back.setBounds(780, 185, 200, 50);
//        back.setBackground(new Color(85,107,47));
//        back.setFont(new Font("宋体", Font.BOLD, 20));
//        back.addActionListener(this);


        send = new JButton("发送");
        if (Save.model.getNumberOfMind() != 99)
            send.setBounds(840, 485, 60, 30);
        else
            send.setBounds(840, 750, 60, 30);
        send.setBackground(new Color(95, 63, 29));


        send.addActionListener(this);


        talkField = new JTextField("聊天");
        if (Save.model.getNumberOfMind() != 99)
            talkField.setBounds(800, 450, 200, 30);
        else
            talkField.setBounds(800, 700, 200, 30);


        talkField.addMouseListener(this);


//        exitGame=new JButton("返  回");
//        exitGame.setBackground(new Color(218,165,32));
//        exitGame.setBounds(780,240,200,50);
//        exitGame.setFont(new Font("宋体", Font.BOLD, 20));//设置字体，下同


        people1 = new JLabel("    我:");
        people1.setOpaque(true);
        people1.setBackground(new Color(82, 109, 165));
        if (Save.model.getNumberOfMind() != 99)
            people1.setBounds(820, 180, 200, 25);
        else
            people1.setBounds(20, 630, 200, 25);
        people1.setFont(new Font("宋体", Font.BOLD, 15));

        people2 = new JLabel("    对手:");
        people2.setOpaque(true);
        people2.setBackground(new Color(82, 109, 165));
        if (Save.model.getNumberOfMind() != 99)
            people2.setBounds(820, 687, 200, 25);
        else
            people2.setBounds(250, 630, 200, 25);
        people2.setFont(new Font("宋体", Font.BOLD, 15));

        p1lv = new JLabel("    等 级:LV.");
        p1lv.setOpaque(true);
        p1lv.setBackground(new Color(82, 109, 165));
        if (Save.model.getNumberOfMind() != 99)
            p1lv.setBounds(820, 205, 200, 25);
        else
            p1lv.setBounds(20, 675, 200, 25);
        p1lv.setFont(new Font("宋体", Font.BOLD, 15));

        p2lv = new JLabel("    等 级:LV.");
        p2lv.setOpaque(true);
        p2lv.setBackground(new Color(82, 109, 165));
        if (Save.model.getNumberOfMind() != 99)
            p2lv.setBounds(820, 712, 200, 25);
        else
            p2lv.setBounds(250, 675, 200, 25);
        p2lv.setFont(new Font("宋体", Font.BOLD, 15));

        situation1 = new JLabel("    状态:");
        situation1.setOpaque(true);
        situation1.setBackground(new Color(82, 109, 165));
        if (Save.model.getNumberOfMind() != 99)
            situation1.setBounds(820, 230, 200, 25);
        else
            situation1.setBounds(20, 720, 200, 25);
        situation1.setFont(new Font("宋体", Font.BOLD, 15));

        situation2 = new JLabel("    状态:");
        situation2.setOpaque(true);
        situation2.setBackground(new Color(82, 109, 165));
        if (Save.model.getNumberOfMind() != 99)
            situation2.setBounds(820, 737, 200, 25);
        else
            situation2.setBounds(250, 720, 200, 25);
        situation2.setFont(new Font("宋体", Font.BOLD, 15));
        jLabel1 = new JLabel();
        add(jLabel1);
        jLabel1.setBounds(130, 75, 200, 50);
        jLabel2 = new JLabel();
        add(jLabel2);
        jLabel2.setBounds(130, 410, 200, 50);

        timecount = new JLabel("    计时器:");
        timecount.setBounds(10, 770, 200, 50);
        timecount.setFont(new Font("宋体", Font.BOLD, 30));
        timecount.setForeground(new Color(243, 233, 172));


        current = new JLabel("1111");
        current.setBounds(600, 770, 200, 50);
        current.setFont(new Font("宋体", Font.BOLD, 30));
        current.setForeground(new Color(243, 233, 172));

        talkArea = new JTextArea();  //对弈信息
        talkArea.setEnabled(false);
        talkArea.setBackground(Color.BLACK);
        //滑动条
        JScrollPane p = new JScrollPane(talkArea);
        if (Save.model.getNumberOfMind() != 99)
            p.setBounds(800, 255, 200, 200);
        else
            p.setBounds(535, 625, 200, 200);

        add(tf_ip);
//        add(cb);
        add(startGame);
//        add(back);
//        add(exitGame);
        add(people1);
        add(people2);
        add(p1lv);
        add(p2lv);
        add(situation1);
        add(situation2);
        add(timecount);
        add(p);
        add(send);
        add(talkField);
//        add(current);
        //加载线程
        ReicThread();
        repaint();
    }


    public void ReicThread() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    byte buf[] = new byte[1024];
                    socket = new DatagramSocket(10086);
                    DatagramPacket dp = new DatagramPacket(buf, buf.length);
                    while (true) {
                        current.setText(String.valueOf(gc.getCurrentPlayer()));

                        socket.receive(dp);
//                        System.out.println("---------------");
                        //0.接收到的发送端的主机名
                        InetAddress ia = dp.getAddress();
                        //enemyMsg.add(new String(ia.getHostName()));  //对方端口
//                        logger.info("对手IP："+ia.getHostName());
//                        System.out.println("对方已连接！"+"对手IP："+ia.getHostName());
                        enemyGameState = "ready";

                        //1.接收到的内容
                        String data = new String(dp.getData(), 0, dp.getLength());
//                        System.out.println(data);
                        if (enemyGameState.equals("ready") && (gameState.equals("FIGHTING") || gameState.equals("ready")))
                            if (data.isEmpty()) {
                                gc.setClickable(CAN_NOT_CLICK_INFO);
//                            System.out.println("you can move");
                            } else {

                                String[] msg = data.split(",");
                                if (!judge){
                                    NetTool.sendUDPBroadCast(PVPGameFrame.getIp(), "ID" + "," + DatabaseUtil.player.id);
                                    judge=true;
                                }

                                // 接收到对面准备信息并且自己点击了准备
                                if (msg[0].equals("FIRST")) {
                                    String board = msg[1];
                                    System.out.println(board);
                                    String[] squarss = board.split("b");
                                    String[][] squars = new String[squarss.length][];
                                    for (int i = 0; i < squars.length; i++) {
                                        squars[i] = squarss[i].split("a");
                                    }
                                    int[][] mins = new int[squars.length][squars[0].length];
                                    for (int i = 0; i < squars.length; i++) {
                                        for (int j = 0; j < squars[0].length; j++) {
                                            mins[i][j] = Integer.parseInt(squars[i][j]);
                                        }
                                    }
                                    gc.first = false;
                                    gc.getModel().iniNet(mins);

//                                gc.setCurrentPlayer(1);
                                }
                                if (msg[0].equals("Function"))
                                    gc.getModel().inBox(msg);

                                if (msg[0].equals("ready")) {
                                    enemyGameState = "ready";
                                    System.out.println("对方已准备");
                                    if (gameState.equals("ready")) {
                                        gameState = "FIGHTING";
                                        gc.setClickable(CAN_CLICK_INFO);
                                        startGame.setText("正在游戏");
                                        situation1.setText("    状态:等待...");
                                        situation2.setText("    状态:下棋...");
                                        logger.info("等待对方消息");
//                                        if (timer!=null)
//                                            timer.interrupt();
//                                        timer = new TimeThread(label_timeCount);
//                                        timer.start();
                                    }
                                }  else if (msg[0].equals("LEFT")) {
                                    System.out.println("发送坐标");
                                    //接受坐标以及角色
                                    situation1.setText("    状态:等待...");
                                    situation2.setText("    状态:探索...");
                                    //重新启动计时线程
//                                    if (timer!=null)
//                                        timer.interrupt();
//                                    timer = new TimeThread(label_timeCount);
//                                    timer.start();
                                    gc.onPlayerLeftClick(new BoardLocation(Integer.parseInt(msg[1]), Integer.parseInt(msg[2])));

//                                cb.setCoord(Integer.parseInt(msg[1]), Integer.parseInt(msg[2]), Integer.parseInt(msg[3]));

                                } else if (msg[0].equals("enemy")) {
                                    talkArea.append("对手：" + msg[1] + "\n");
                                    logger.info("对手发送的消息" + msg[1]);
                                } else if (msg[0].equals("RIGHT")) {
                                    System.out.println("发送坐标");
                                    //接受坐标以及角色
                                    situation1.setText("    状态:等待...");
                                    situation2.setText("    状态:插旗...");
                                    //重新启动计时线程
//                                    if (timer!=null)
//                                    timer.interrupt();
//                                    timer = new TimeThread(label_timeCount);
//                                    timer.start();
                                    gc.onPlayerRightClick(new BoardLocation(Integer.parseInt(msg[1]), Integer.parseInt(msg[2])));
                                } else if (msg[0].equals("MOVE")) {
                                    gc.onPlayerMoveNet(new BoardLocation(Integer.parseInt(msg[1]), Integer.parseInt(msg[2])));
                                } else if (msg[0].equals("Mid"))
                                    gc.onPlayerMidClick(new BoardLocation(Integer.parseInt(msg[1]), Integer.parseInt(msg[2])));
                                if (msg[0].equals("EXIT"))
                                    gc.onPlayerExitNet(new BoardLocation(Integer.parseInt(msg[1]), Integer.parseInt(msg[2])));

                                 if (msg[0].equals("Zha")) {
                                     System.out.println("zha!!!!!");
                                    Save.model.setZhadan(Integer.parseInt(msg[1]),Integer.parseInt(msg[2]));
                                }
                                if (msg[0].equals("ID")) {
                                    DatabaseUtil.getInformation(msg[1], 2);
                                    System.out.println(DatabaseUtil.opponent);
                                    people2.setText("对手：" + DatabaseUtil.opponent.name);
                                    people1.setText("我： " + DatabaseUtil.player.name);
                                    p2lv.setText("等级：LV" + DatabaseUtil.opponent.level);
                                    p1lv.setText("等级：LV" + DatabaseUtil.player.level);
                                }

                            }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startGame) {
            if (!tf_ip.getText().isEmpty() &&
                    !tf_ip.getText().equals("不能为空") &&
                    !tf_ip.getText().equals("请输入IP地址") &&
                    !tf_ip.getText().equals("不能连接到此IP")) {
                ip = tf_ip.getText();
                startGame.setEnabled(false);
                startGame.setText("等待对方准备");
                tf_ip.setEditable(false);
                //发送准备好信息
                NetTool.sendUDPBroadCast(ip, "ready, ");
//                NetTool.sendUDPBroadCast(ip, "ID" + "," + DatabaseUtil.player.id);
                gameState = "ready";
                if (enemyGameState.equals("ready")) {
                    if (PVPGameFrame.getIp() != null) {
                    }
                    gameState = "FIGHTING";
//                    cb.setClickable(CAN_CLICK_INFO);
                    startGame.setText("正在游戏");
                    situation1.setText("    状态:等待...");
                    situation2.setText("    状态:下棋...");
//                    if (timer!=null)
//                        timer.interrupt();
//                    timer = new TimeThread(label_timeCount);
//                    timer.start();
                    gc.setCurrentPlayer(1);
                } else
                    gc.setCurrentPlayer(2);
                NetTool.sendUDPBroadCast(PVPGameFrame.getIp(), "ID" + "," + DatabaseUtil.player.id);

            } else {
                tf_ip.setText("不能为空");
            }

        }
        //点击悔棋后的操作
        else if (e.getSource() == back) {
            //发送悔棋信息
            NetTool.sendUDPBroadCast(ip, "back" + ", ");
            logger.info("玩家选择悔棋");
        }
        // 聊天发送按钮
        else if (e.getSource() == send) {
            if (!talkField.getText().isEmpty() && !talkField.getText().equals("不能为空")) {
                //获得输入的内容
                String msg = talkField.getText();
                talkArea.append("我：" + msg + "\n");
                talkField.setText("");
                ip = tf_ip.getText();
                NetTool.sendUDPBroadCast(ip, "enemy" + "," + msg);
            } else {
                talkField.setText("不能为空");
            }

        }
        //退出游戏，加载主菜单
        else if (e.getSource() == exitGame) {
            dispose();
//            new SelectMenu();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == tf_ip) {
            tf_ip.setText("");
        } else if (e.getSource() == talkField) {
            talkField.setText("");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
