package dao;

import java.sql.*;

/**
 * JDBC������
 * �Գ���JDBC�������з�װ
 */
public class DBUtil
{
    private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver",
            url = "jdbc:sqlserver://localhost:1433; DatabaseName=ticket_sales_system",
            databaseUser = "sa",
            databasePassword = "947499";

    private static Connection conn = null;

    // ��̬������������ʱִ�в���ִֻ��һ��
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
     * ִ��UPDATE INSERT DELETE���
     * ����Ӱ�����ݿ��еļ�¼����
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
     * ִ��SELECT���
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
