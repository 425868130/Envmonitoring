package utils;

import javax.swing.*;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;


public class JDBCUtils {

    // sql server的连接驱动
    public static final String SQLSERVER_DRIVER = "com.mysql.jdbc.Driver";

    // sql server的数据库的url
    public static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/javanet?characterEncoding=utf8&useSSL=false";
    // sql server的数据库的用户名
    public static final String DB_USER = "root";
    // sql server的数据库的密码
    public static final String DB_PASSWORD = "1320074071";

    public static Connection con = null;
    public static PreparedStatement pstm = null;
    public static ResultSet rs = null;
    public static CallableStatement cstm = null;

    /**
     * 获取JDBC的连接
     *
     * @return JDBC的连接
     */
    public static void getConnection() {
        try {
            Class.forName(SQLSERVER_DRIVER);
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "数据库驱动加载失败！");
            e.printStackTrace();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "数据库连接失败！");
            System.exit(0);
        }
    }

    /**
     * 数据库数据变更的方法
     *
     * @param sql       数据库数据变更的sql语句
     * @param paramList sql语句中的参数列表
     * @return result 执行sql语句后的结果
     */
    public static int updateData(String sql, List paramList) {
        int result = 0;

        try {
            // 获取连接
            getConnection();
            pstm = con.prepareStatement(sql);

            // 设置sql语句中的参数
            if ((null != paramList) && (!paramList.isEmpty())) {
                for (int i = 0; i < paramList.size(); i++) {
                    pstm.setObject(i + 1, paramList.get(i));
                }
            }

            // 获取执行sql语句的结果
            result = pstm.executeUpdate();

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        } catch (NullPointerException nullPointerE) {
            nullPointerE.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 数据库查询的方法
     *
     * @param sql       数据库查询的sql语句
     * @param paramList 数据库查询的参数列表
     */
    public static void queryData(String sql, List paramList) {

        try {
            if (con == null) {
                // 获取连接
                getConnection();
            }
            // 获取预编译指令
            pstm = con.prepareStatement(sql);

            //ֵ 设置sql语句中的参数
            if ((null != paramList) && (!paramList.isEmpty())) {
                for (int i = 0; i < paramList.size(); i++) {
                    pstm.setObject(i + 1, paramList.get(i));
                }
            }

            // 获取sql语句后的结果
            rs = pstm.executeQuery();

        } catch (SQLException sqlE) {
            sqlE.printStackTrace();
        } catch (NullPointerException nullPointerE) {
            nullPointerE.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 关闭数据库资源
     *
     * @throws SQLException sql语句异常
     */
    public static void closeAll() throws SQLException {

        // 关闭数据库结果集
        if (null != rs) {
            rs.close();
        }

        // 关闭数据库预编译指令
        if (null != pstm) {
            pstm.close();
        }

        // 关闭数据库连接
        if (null != con) {
            con.close();
        }
    }


}
