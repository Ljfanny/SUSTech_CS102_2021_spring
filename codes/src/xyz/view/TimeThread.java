package xyz.view;

import javax.swing.*;
import java.awt.*;

/**
 * 计时线程
 *
 * @author admin
 */
public class TimeThread extends Thread {
    JLabel label; //计时标签
    public static boolean changepla = false;

    public TimeThread(JLabel label) {
        this.label = label;
    }

    public void run() {
        //获取开始时间（毫秒），即当前时间
        long startTime = System.currentTimeMillis();
        while (true) {
            long currentTime = System.currentTimeMillis();
            long time = currentTime - startTime;
            //30秒倒计时
            label.setText(String.valueOf(30 - time / 1000));
            //如果30秒结束，督促
            if (label.getText().equals("0")) {
                this.interrupt();
                label.setText("对手等到花儿都谢了，赶紧的！");
                label.setFont(new Font("微软雅黑", Font.BOLD, 22));
                label.setForeground(Color.white);
                changepla = true;

                Save.gameController.nextPlayer();

            }
            //中断线程退出
            if (this.isInterrupted()) {
                break;
            }
        }
    }
}
