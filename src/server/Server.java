package server;

import utils.JDBCUtils;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by XJW on 2017/5/20.
 * 服务器端程序
 */
public class Server {
    public static List<Socket> querySocketList = new ArrayList<>();
    private ServerSocket querySocket;
    private DatagramSocket ds;
    public static boolean isServerShutdown = false;
    static Server server = new Server();

    private Server() {
        startServer();
    }

    public static Server getServer() {
        return server;
    }


    /*监听连接*/
    public void startServer() {
        /*初始化数据库连接*/
        JDBCUtils.getConnection();
        byte[] b = new byte[1024];
        DatagramPacket dp = new DatagramPacket(b, b.length);
        try {
            if (ds == null || ds.isClosed()) {
                ds = new DatagramSocket(9999);
            }
        /*监听客户端*/
            new Thread(new ClientServerRunnable(dp, ds)).start();
            /*监听查询端,使用tcp协议的9998端口*/
            if (querySocket == null) {
                querySocket = new ServerSocket(9998);
            }
        } catch (SocketException e) {
            JOptionPane.showMessageDialog(null, "端口被占用,服务启动失败！");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (querySocket != null) {
            new Thread(new QueryServerRunnable(querySocket)).start();
        }
    }

    public void ShutDownServer() {
        try {
            JDBCUtils.closeAll();
            if (querySocket != null) {
                querySocket.close();
                querySocket = null;
            }
            if (ds != null) {
                ds.close();
                ds = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
