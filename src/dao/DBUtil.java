package dao;

import java.sql.*;

/**
 * JDBC工具类
 * 对常用JDBC操作进行封装
 */
public class DBUtil
{
    private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver",
            url = "jdbc:sqlserver://localhost:1433; DatabaseName=ticket_sales_system",
            databaseUser = "sa",
            databasePassword = "947499";

    private static Connection conn = null;

    // 静态代码块在类加载时执行并且只执行一次
    static
    {
        try
        {
            Class.forName(driver);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public static void closeConnection()
    {
        try
        {
            if(conn != null && !conn.isClosed())
                conn.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * 执行UPDATE INSERT DELETE语句
     * 返回影响数据库中的记录条数
     */
    public static int executeUpdate(String sql)
    {
        int r = 0;
        try
        {
            conn = DriverManager.getConnection(url, databaseUser, databasePassword);
            Statement cmd = conn.createStatement();
            r = cmd.executeUpdate(sql);
            conn.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return r;
    }

    /**
     * 执行SELECT语句
     */
    public static ResultSet executeQuery(String sql)
    {
        ResultSet rs = null;
        try
        {
            conn = DriverManager.getConnection(url, databaseUser, databasePassword);
            Statement cmd = conn.createStatement();
            rs = cmd.executeQuery(sql);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return rs;
    }
}
