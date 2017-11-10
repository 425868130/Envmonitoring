package queryClient;

import com.google.gson.Gson;
import component.*;
import model.AvgAtmosphere;
import model.AvgClimate;
import model.AvgWaterQuality;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.*;

/**
 * Created by XJW on 2017/5/21.
 */
public class QueryClient {
    public static boolean isClientClosed = false;
    private static boolean getServerConnect = false;
    /*面板透明度常量*/
    final static float TRANSPARENCY = 0.5f;
    private JPanel rootPanel;
    private JPanel first;
    private JButton query;
    private JTextField dateChoice;
    private JPanel temperatureCurvePanel;
    private JPanel rightPanel;
    private JPanel leftTopPanel;
    private JPanel leftBottomPanel;
    private JLabel labContaminants;
    private JLabel labPM;
    private JLabel labWindPower;
    private JLabel labHumidity;
    private JLabel labWindDirection;
    private JLabel labPH;
    private JLabel labTurbidity;
    private JLabel labConductivity;
    private JLabel labDissolvedOxygen;
    private JLabel labWeather;
    private JLabel labPrecipitation;
    private JLabel labUltravioletRays;
    private JLabel SysTime;
    private DateChooser dateChooser;
    SocketAddress address;
    Socket socket;
    BufferedWriter bufferedWriter;
    BufferedReader bufferedReader;
    Gson gson;

    public QueryClient() {
        new SystemTimeThread(SysTime, isClientClosed).start();
        GetData();
        gson = new Gson();
        query.addActionListener(e -> SendData(dateChoice.getText()));
    }

    /*自定义组件创建*/
    private void createUIComponents() {
        /*创建带背景图的根面板*/
        rootPanel = new ImagePanel("/images/queryBack.jpg");
        /*创建日期选择器*/
        dateChoice = new JTextField();
        dateChooser = DateChooser.getInstance("yyyy-MM-dd");
        dateChooser.register(dateChoice);
        /*创建半透明面板*/
        leftTopPanel = new TranslucentPanel(TRANSPARENCY);
        leftBottomPanel = new TranslucentPanel(TRANSPARENCY);
        rightPanel = new TranslucentPanel(TRANSPARENCY);
        /*温度曲线面板*/
        temperatureCurvePanel = MyPanel.getInstance();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("查询终端");
        frame.setContentPane(new QueryClient().rootPanel);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                isClientClosed = true;
                System.exit(0);
            }
        });
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.pack();
        frame.setVisible(true);
    }

    /*初始化连接*/
    public void getConnection() {
        /*连接服务器*/
        System.out.println("连接服务器");
        try {
            if (address == null) {
                address = new InetSocketAddress(InetAddress.getLocalHost(), 9998);
            }
            if (socket != null) {
                socket.close();
            }
            socket = new Socket();
            socket.connect(address);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("服务器连接成功！");
            getServerConnect = true;
        } catch (UnknownHostException e) {
            WarningDialog.getInstace().setContent("未知的主机", "服务器连接失败，未知的主机").setVisible(true);
        } catch (IOException e) {
            //e.printStackTrace();
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            socket = null;
            WarningDialog.getInstace().setContent("连接失败", "服务器连接失败,正在尝试重连....").setVisible(true);
        }
    }


    private void SendData(String data) {
        try {
            bufferedWriter.write(data);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            getServerConnect = false;
            System.out.println("连接已断开");
            getConnection();
        }
    }

    private void GetData() {
        new Thread(() -> {
            while (!isClientClosed) {
                if (getServerConnect) {
                    try {
                        System.out.println("获取数据");
                        String str = bufferedReader.readLine();
                        String[] data = str.split(";");
                        AvgAtmosphere avgAtmosphere = gson.fromJson(data[0], AvgAtmosphere.class);
                        AvgWaterQuality avgWaterQuality = gson.fromJson(data[1], AvgWaterQuality.class);
                        labContaminants.setText(avgAtmosphere.getContaminants());
                        labPM.setText(String.valueOf(avgAtmosphere.getPm() + "   μg/m3"));
                        labWindDirection.setText(avgAtmosphere.getWindDirection());
                        labWindPower.setText(String.valueOf(avgAtmosphere.getWindPower() + "  级"));
                        labHumidity.setText(String.valueOf(avgAtmosphere.getHumidity() + "  %"));
                    /*=============*/
                        labPH.setText(String.valueOf(avgWaterQuality.getPh()));
                        labTurbidity.setText(String.valueOf(avgWaterQuality.getTurbidity() + "   NTU"));
                        labConductivity.setText(String.valueOf(avgWaterQuality.getConductivity() + "   µS/cm"));
                        labDissolvedOxygen.setText(String.valueOf(avgWaterQuality.getDissolvedOxygen() + "   mg/L"));
                    /*============*/
                        AvgClimate avgClimate = gson.fromJson(data[2], AvgClimate.class);
                        labWeather.setText(avgClimate.getWeather());
                        labPrecipitation.setText(String.valueOf(avgClimate.getPrecipitation() + "   mm"));
                        labUltravioletRays.setText(String.valueOf(avgClimate.getUltravioletRays() + "  mW/m2"));
                        MyPanel.getInstance().SetTemperatureList(avgClimate.getTemperature());
                    } catch (Exception e) {
                        getServerConnect = false;
                        System.out.println("连接已断开");
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        getConnection();
                    }
                }//if
                else {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    getConnection();
                }
            }
        }).start();
    }
}
