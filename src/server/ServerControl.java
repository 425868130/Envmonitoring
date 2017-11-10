package server;

import javax.swing.*;
import java.awt.*;

/**
 * Created by XJW on 2017/5/24.
 * 服务器控制器
 */
public class ServerControl {

    public static void main(String args[]) {
        Server server = Server.getServer();
        SwingUtilities.invokeLater(() -> constructSystemTray(server));
    }

    /*创建系统托盘*/
    private static void constructSystemTray(Server server) {
        if (!SystemTray.isSupported()) {
            JOptionPane.showMessageDialog(null, "系统不支持托盘！");
            return;
        }
        /*获取系统托盘*/
        SystemTray tray = SystemTray.getSystemTray();
        /*获取托盘图标*/
        Image image = new ImageIcon(Server.class.getResource("/images/server.jpg")).getImage();
        /*构造弹出菜单*/
        PopupMenu popupMenu = new PopupMenu();
        /*构造菜单项及点击事件*/
        MenuItem startItem = new MenuItem("Start Server");
        startItem.addActionListener(e -> {
            /*如果服务已经开启则不做任何操作*/
            if (Server.isServerShutdown == false) {
                JOptionPane.showMessageDialog(null, "服务已经在运行,请勿重复开启!");
                return;
            }
            Server.isServerShutdown = false;
            server.startServer();
            JOptionPane.showMessageDialog(null, "服务启动成功！");
        });
        /*将菜单项添加到弹出菜单*/
        popupMenu.add(startItem);

        MenuItem stopItem = new MenuItem("Stop Server");
        stopItem.addActionListener(e -> {
            Server.isServerShutdown = true;
            server.ShutDownServer();
            JOptionPane.showMessageDialog(null, "服务已停止！");
        });
        /*将菜单项添加到弹出菜单*/
        popupMenu.add(stopItem);

        MenuItem closeItem = new MenuItem("Exit Server");
        closeItem.addActionListener(e -> {
            Server.isServerShutdown = true;
            System.exit(0);
        });

        popupMenu.add(closeItem);
        /*构造托盘图标*/
        TrayIcon icon = new TrayIcon(image, "ServerControl", popupMenu);
        icon.setImageAutoSize(true);
        try {
            tray.add(icon);
            showTrayMsg(null, "服务已启动", TrayIcon.MessageType.INFO);
        } catch (AWTException e) {
            e.printStackTrace();
            System.out.println("Could not add tray icon to system tray");
        }
    }

    public static void showTrayMsg(String caption, String text, TrayIcon.MessageType messageType) {
        SystemTray.getSystemTray().getTrayIcons()[0].displayMessage(caption, text, messageType);
    }
}
