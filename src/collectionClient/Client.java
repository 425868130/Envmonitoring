package collectionClient;

import com.google.gson.Gson;
import component.ImagePanel;
import component.SystemTimeThread;
import component.WarningDialog;
import model.Atmosphere;
import model.Climate;
import model.WaterQuality;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.List;

/**
 * Created by XJW on 2017/5/15.
 */
public class Client {
    private static JFrame frame;
    private InetAddress ip;
    private DatagramSocket ds;
    public static boolean isClientClosed = false;
    private static Gson gson = new Gson();
    private Atmosphere atmosphere;
    private Climate climate;
    private WaterQuality waterQuality;
    private JPanel root;
    private JComboBox cbxContaminants;
    private JTextField txfPM;
    private JTextField txfhumidity;
    private JComboBox cbxWindDirection;
    private JComboBox cbxWindPower;
    private JButton buttonSumbit;
    private JPanel first;
    private JTextField txfConductivity;
    private JLabel systemTime;
    private JTextField txfTemperature;
    private JTextField txfPrecipitation;
    private JTextField txfUltravioletRays;
    private JTextField txfPH;
    private JTextField txfDissolvedOxygen;
    private JTextField txfTurbidity;
    private JComboBox cbxWeather;
    private List<JTextField> textFieldList;

    public Client() {
        /*开启时钟线程*/
        new SystemTimeThread(systemTime, isClientClosed).start();
        addToList();
        InitSocket();
        buttonSumbit.addActionListener((event) -> {
            /*取值前先清除上次的值*/
            atmosphere = null;
            climate = null;
            waterQuality = null;
            getTxfValue();
            if (atmosphere != null && climate != null && waterQuality != null) {
                /*转换为json字符串并发送*/
                SubmitData(gson.toJson(atmosphere) + ";" + gson.toJson(climate) + ";" + gson.toJson(waterQuality));
            }

        });
    }

    public static void main(String[] args) {
        frame = new JFrame("环境监测-采集终端");
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                /*窗口关闭时将线程标志设置为FALSE以停止后台线程*/
                super.windowClosing(e);
                isClientClosed = true;
                System.exit(0);
            }
        });
        frame.setContentPane(new Client().root);
        frame.setLocation(300, 100);
        frame.pack();
        frame.setVisible(true);
    }

    /*自定义组件创建过程*/
    private void createUIComponents() {
        // TODO: place custom component creation code here
        root = new ImagePanel("/images/back.jpg");

    }

    /*将输入框加入列表方便遍历检测输入值*/
    private void addToList() {
        textFieldList = new ArrayList<>();
        textFieldList.add(txfPM);
        textFieldList.add(txfhumidity);
        textFieldList.add(txfConductivity);
        textFieldList.add(txfTemperature);
        textFieldList.add(txfPrecipitation);
        textFieldList.add(txfUltravioletRays);
        textFieldList.add(txfPH);
        textFieldList.add(txfDissolvedOxygen);
        textFieldList.add(txfTurbidity);
    }

    /*获取界面的输入框的值*/
    public void getTxfValue() {
        for (JTextField jTextField : textFieldList) {
            if (jTextField.getText().length() == 0) {
                WarningDialog.getInstace().setContent("数据项不能为空", "请输入完整的数据").setVisible(true);
                jTextField.requestFocus();
                jTextField.setBorder(BorderFactory.createLineBorder(Color.RED));
                return;
            } else if (jTextField.getText().length() > 6) {
                WarningDialog.getInstace().setContent("数据过长", "数据过长,请输入正确数据！").setVisible(true);
                jTextField.requestFocus();
                jTextField.setBorder(BorderFactory.createLineBorder(Color.RED));
                return;
            }
        }
        if (!RangeCheck()) {
            return;
        }
        try {
            /*获取界面控件的值，构造对象*/
            atmosphere = new Atmosphere(cbxContaminants.getSelectedItem().toString(), Integer.parseInt(txfPM.getText()), Integer.parseInt(txfhumidity.getText()), cbxWindDirection.getSelectedItem().toString(), Integer.parseInt(cbxWindPower.getSelectedItem().toString()));
            climate = new Climate(Float.parseFloat(txfTemperature.getText()), cbxWeather.getSelectedItem().toString(), Integer.parseInt(txfPrecipitation.getText()), Integer.parseInt(txfUltravioletRays.getText()));
            waterQuality = new WaterQuality(Float.parseFloat(txfDissolvedOxygen.getText()), Float.parseFloat(txfPH.getText()), Float.parseFloat(txfTurbidity.getText()), Float.parseFloat(txfConductivity.getText()));
        } catch (Exception e) {
            WarningDialog.getInstace().setContent("数据格式错误！", "请输入正确的数据类型!").setVisible(true);
        }
    }

    /*范围检查*/
    private boolean RangeCheck() {
        try {
            if (Float.parseFloat(txfTemperature.getText()) > 45 || Float.parseFloat(txfTemperature.getText()) < -30) {
                txfTemperature.requestFocus();
                txfTemperature.setBorder(BorderFactory.createLineBorder(Color.RED));
                WarningDialog.getInstace().setContent("温度范围错误", "请输入正确的温度值\n(-30~45)").setVisible(true);
                return false;
            }
            if (Integer.parseInt(txfPM.getText()) < 0 || Integer.parseInt(txfPM.getText()) > 400) {
                txfPM.requestFocus();
                txfPM.setBorder(BorderFactory.createLineBorder(Color.RED));
                WarningDialog.getInstace().setContent("PM2.5范围错误", "请输入正确的PM2.5值\n(0~400)").setVisible(true);
                return false;
            }
            if (Integer.parseInt(txfhumidity.getText()) < 0 || Integer.parseInt(txfhumidity.getText()) > 100) {
                txfhumidity.requestFocus();
                txfhumidity.setBorder(BorderFactory.createLineBorder(Color.RED));
                WarningDialog.getInstace().setContent("湿度范围错误", "请输入正确的湿度范围\n(0~100)").setVisible(true);
                return false;
            }
            if (Integer.parseInt(txfPH.getText()) < 0 || Integer.parseInt(txfPH.getText()) > 14) {
                txfPH.requestFocus();
                txfPH.setBorder(BorderFactory.createLineBorder(Color.RED));
                WarningDialog.getInstace().setContent("PH范围错误", "请输入正确的PH范围\n(0~14)").setVisible(true);
                return false;
            }
        } catch (Exception e) {
            WarningDialog.getInstace().setContent("数据格式错误！", "请输入正确的数据类型!").setVisible(true);
            return false;
        }
        return true;
    }

    /*初始化连接*/
    private void InitSocket() {
        try {
            ip = InetAddress.getByName("127.0.0.1");
            ds = new DatagramSocket();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*向服务器发送数据*/
    private void SubmitData(String data) {
        DatagramPacket dp = new DatagramPacket(data.getBytes(), data.getBytes().length, ip, 9999);
        try {
            ds.send(dp);
            WarningDialog.getInstace().setContent("发送成功", "数据提交成功！").setVisible(true);
            clearInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearInput() {
        /*取值前先清除上次的值*/
        atmosphere = null;
        climate = null;
        waterQuality = null;
        for (JTextField jTextField : textFieldList) {
            jTextField.setBorder(BorderFactory.createLineBorder(Color.gray));
            jTextField.setText("");
        }
    }
}