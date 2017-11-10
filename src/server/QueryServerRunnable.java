package server;

import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by XJW on 2017/5/21.
 * 查询端处理线程
 */
public class QueryServerRunnable implements Runnable {
    private ServerSocket serverSocket;
    /*线程池*/
    private Executor executor;

    public QueryServerRunnable(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        /*创建线程池*/
        executor = Executors.newCachedThreadPool();
    }

    @Override
    public void run() {
        Socket tempSocket = null;
        try {
            while (!Server.isServerShutdown) {
                tempSocket = serverSocket.accept();
                ServerControl.showTrayMsg(null, "查询端" + tempSocket.toString() + "已连接", TrayIcon.MessageType.INFO);
                /*接到连接时保存Socket*/
                Server.querySocketList.add(tempSocket);
                /*将新连接加入到线程池用于处理请求*/
                executor.execute(new QueryHandler(tempSocket));
            }
        } catch (IOException e) {
            try {
                tempSocket.getInputStream().close();
                tempSocket.getOutputStream().close();
                tempSocket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            System.out.println("TCP监听关闭");
        }
    }
}
