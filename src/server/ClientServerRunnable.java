package server;

import com.google.gson.Gson;
import model.Atmosphere;
import model.Climate;
import model.WaterQuality;
import utils.JDBCUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by XJW on 2017/5/21.
 * 处理客户端时间的线程任务
 */
public class ClientServerRunnable implements Runnable {
    Gson gson = new Gson();
    DatagramPacket dp;
    DatagramSocket ds;

    public ClientServerRunnable(DatagramPacket dp, DatagramSocket ds) {
        this.dp = dp;
        this.ds = ds;
    }

    @Override
    public void run() {
        try {
            /*获取数据*/
            while (!Server.isServerShutdown) {
                ds.receive(dp);
                /*解析json数据*/
                String[] data = new String(dp.getData(), 0, dp.getLength()).split(";");
                Atmosphere atmosphere = gson.fromJson(data[0], Atmosphere.class);
                Climate climate = gson.fromJson(data[1], Climate.class);
                WaterQuality waterQuality = gson.fromJson(data[2], WaterQuality.class);
                SaveAtmosphereToDB(atmosphere);
                SaveClimateToDB(climate);
                SaveWaterQualityToDB(waterQuality);
            }
            ds.close();
        } catch (IOException e) {
            ds.close();
            System.out.println("UDP监听关闭");
        }
    }

    /*检验数据的合法性*/
    private boolean checkData(Atmosphere atmosphere, Climate climate, WaterQuality waterQuality) {

        return true;
    }

    /*存储空气质量数据到数据库*/
    private void SaveAtmosphereToDB(Atmosphere atmosphere) {
        String sql = "insert into atmosphere values(null,null,?,?,?,?,?)";
        List args = new ArrayList();
        args.add(atmosphere.getContaminants());
        args.add(atmosphere.getPm());
        args.add(atmosphere.getHumidity());
        args.add(atmosphere.getWindDirection());
        args.add(atmosphere.getWindPower());
        JDBCUtils.updateData(sql, args);
    }

    /*存储气候数据到数据库*/
    private void SaveClimateToDB(Climate climate) {
        String sql = "insert into climate values(null,null,?,?,?,?)";
        List args = new ArrayList();
        args.add(climate.getTemperature());
        args.add(climate.getWeather());
        args.add(climate.getPrecipitation());
        args.add(climate.getUltravioletRays());
        JDBCUtils.updateData(sql, args);
    }

    /*存储水质数据到数据库*/
    private void SaveWaterQualityToDB(WaterQuality waterQuality) {
        String sql = "insert into waterquality values(null,null,?,?,?,?)";
        List args = new ArrayList();
        args.add(waterQuality.getDissolvedOxygen());
        args.add(waterQuality.getPh());
        args.add(waterQuality.getTurbidity());
        args.add(waterQuality.getConductivity());
        JDBCUtils.updateData(sql, args);
    }
}
