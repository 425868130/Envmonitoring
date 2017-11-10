package component;

import collectionClient.Client;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by XJW on 2017/5/18.
 * 同于获取并显示系统当前时间的类,传入要显示时间的JLabel引用即可
 */
public class SystemTimeThread extends Thread {
    JLabel jLabel;
    Boolean ThreadFlag;

    public SystemTimeThread(JLabel jLabel, Boolean ThreadFlag) {
        this.jLabel = jLabel;
        this.ThreadFlag = ThreadFlag;
    }

    @Override
    public void run() {
        super.run();
        while (!ThreadFlag) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            jLabel.setText(simpleDateFormat.format(new Date()));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
