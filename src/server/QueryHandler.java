package server;

import com.google.gson.Gson;
import model.AvgAtmosphere;
import model.AvgClimate;
import model.AvgWaterQuality;
import utils.JDBCUtils;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by XJW on 2017/5/21.
 * 查询端请求处理器
 */
public class QueryHandler implements Runnable {
    Socket socket;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;
    String request;
    boolean socketAlive = true;
    static StringBuilder response = new StringBuilder();
    static Gson gson = new Gson();

    public QueryHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            /*监听查询端的请求数据*/
            while (!Server.isServerShutdown && socketAlive) {
                try {
                    /*获取查询端请求并执行查询操作,出现异常则关闭所有连接*/
                    if ((request = bufferedReader.readLine()) != null) {
                        bufferedWriter.write(queryDataByTime(request));
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                    }
                } catch (Exception e) {
                    closeAll();
                }
            }
            closeAll();
        } catch (IOException e) {
            closeAll();
        }
    }

    /*关闭所有连接*/
    private void closeAll() {
        try {
            bufferedReader.close();
            bufferedWriter.close();
            socket.close();
            socketAlive = false;
            ServerControl.showTrayMsg(null, "查询端" + socket.toString() + "已断开", TrayIcon.MessageType.INFO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*通过时间查找数据*/
    private String queryDataByTime(String arg) {
        /*查询数据前清空StringBuilder*/
        response.delete(0, response.length());
        getAVG_atmosphere(arg);
        getAVG_WaterQuality(arg);
        getAVG_Climate(arg);
        return response.toString();
    }

    /*获取空气质量平均值*/
    private void getAVG_atmosphere(String arg) {
        String sql_PGetAVG_atmosphere = "{call PGetAVG_atmosphere(?)}";
        String sql_PGetAVG_contaminants = "{call PGetAVG_contaminants(?)}";
        String sql_PGetAVG_windDirection = "{call PGetAVG_windDirection(?)}";
        AvgAtmosphere atmosphere = new AvgAtmosphere();
        List<String> argList = new ArrayList<>();
        argList.add(arg);
        JDBCUtils.queryData(sql_PGetAVG_atmosphere, argList);
        ResultSet res = JDBCUtils.rs;
        try {
            while (res.next()) {
                atmosphere.setHumidity(res.getInt("avg_humidity"));
                atmosphere.setPm(res.getInt("avg_pm"));
                atmosphere.setWindPower(res.getInt("avg_windPower"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JDBCUtils.queryData(sql_PGetAVG_contaminants, argList);
        res = JDBCUtils.rs;
        try {
            while (res.next()) {
                atmosphere.setContaminants(res.getString("contaminants"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JDBCUtils.queryData(sql_PGetAVG_windDirection, argList);
        res = JDBCUtils.rs;
        try {
            while (res.next()) {
                atmosphere.setWindDirection(res.getString("wind"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.append(gson.toJson(atmosphere) + ";");
    }

    /*获取水质平均值*/
    private void getAVG_WaterQuality(String arg) {
        String sql_PGetAVG_waterquality = "{call PGetAVG_waterquality(?)}";
        List<String> argsList = new ArrayList();
        argsList.add(arg);
        JDBCUtils.queryData(sql_PGetAVG_waterquality, argsList);
        ResultSet res = JDBCUtils.rs;
        AvgWaterQuality avgWaterQuality = new AvgWaterQuality();
        try {
            while (res.next()) {
                avgWaterQuality.setPh(res.getFloat("avg_ph"));
                avgWaterQuality.setDissolvedOxygen(res.getFloat("avg_dissolvedOxygen"));
                avgWaterQuality.setConductivity(res.getFloat("avg_conductivity"));
                avgWaterQuality.setTurbidity(res.getFloat("avg_turbidity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.append(gson.toJson(avgWaterQuality) + ";");
    }

    /*获取气候平均值*/
    private void getAVG_Climate(String arg) {
        String sql_PGetAVG_climate = "{call PGetAVG_climate(?)}";
        String sql_PGetAVG_weather = "{call PGetAVG_weather(?)}";
        String sql_PGetAVG_temperature = "{call PGetAVG_temperature(?)}";
        List<String> argList = new ArrayList<>();
        argList.add(arg);
        JDBCUtils.queryData(sql_PGetAVG_climate, argList);
        ResultSet res = JDBCUtils.rs;
        AvgClimate avgClimate = new AvgClimate();
        try {
            while (res.next()) {
                /*获取降水量、紫外线强度*/
                avgClimate.setPrecipitation(res.getFloat("avg_precipitation"));
                avgClimate.setUltravioletRays(res.getFloat("avg_ultravioletRays"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JDBCUtils.queryData(sql_PGetAVG_weather, argList);
        res = JDBCUtils.rs;
        try {
            while (res.next()) {
                /*获取当日出现次数最多的天气*/
                avgClimate.setWeather(res.getString("weather"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        /*获取每小时温度值*/
        JDBCUtils.queryData(sql_PGetAVG_temperature, argList);
        res = JDBCUtils.rs;
        try {
            List<Point> tempList = new ArrayList<>();
            while (res.next()) {
                tempList.add(new Point(res.getInt("hour") + 1, res.getInt("avg_temperature")));
            }
            avgClimate.setTemperature(tempList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
     /*   avgClimate.setTemperature(fakeTemp());*/
        response.append(gson.toJson(avgClimate));
    }

    /*生成随机的温度值用于测试*/
    private List<Point> fakeTemp() {
        List<Point> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 24; i++) {
            list.add(new Point(i + 1, random.nextInt(75) - 30));
        }
        return list;
    }
}
