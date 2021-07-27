package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StationSQL
{
    /**
     * 获取车站站名
     * @return 站名字符串数组
     */
    public static String[] queryStaName()
    {
        String[] temp = new String[100];
        int index = 0;
        String sql = "SELECT STA_NAME FROM T_STATION";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            while (rs.next())
            {
                temp[index] = rs.getString(1);
                index++;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        String[] staNameInfo = new String[index];
        for (int i = 0; i < index; i++)
            staNameInfo[i] = temp[i];
        return staNameInfo;
    }

    /**
     * 查询车站信息
     * @return 车站信息表的二维数组
     */
    public static Object[][] queryStation()
    {
        Object[][] temp = new Object[100][3];
        int index = 0;
        String sql = "SELECT * FROM T_STATION";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            while (rs.next())
            {
                temp[index][0] = rs.getString(1);
                temp[index][1] = rs.getDouble(2);
                temp[index][2] = rs.getDouble(3);
                index++;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        Object[][] stationInfo = new Object[index][3];
        for (int i = 0; i < index; i++)
            for (int j = 0; j < 3; j++)
                stationInfo[i][j] = temp[i][j];

        return stationInfo;
    }

    /**
     * 判断车站是否存在
     * @param staName 车站名
     * @return 若存在返回true，否则返回false
     */
    public static boolean isStaExisting(String staName)
    {
        boolean isExisting = false;
        String sql = "SELECT * FROM T_STATION WHERE STA_NAME='" + staName + "'";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            if (rs.next())
                isExisting = true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return isExisting;
    }

    /**
     * 查询车站的经度信息
     * @param name 车站名称
     * @return 车站经度
     */
    public static double queryLon(String name)
    {
        double lon = 0;
        String sql = "SELECT LONGITUDE FROM T_STATION WHERE STA_NAME='" + name + "'";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            if (rs.next())
                lon = rs.getDouble(1);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return lon;
    }

    /**
     * 查询车站的纬度信息
     * @param name 车站名称
     * @return 车站纬度
     */
    public static double queryLat(String name)
    {
        double lat = 0;
        String sql = "SELECT LATITUDE FROM T_STATION WHERE STA_NAME='" + name + "'";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            if (rs.next())
                lat = rs.getDouble(1);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return lat;
    }

    /**
     * 更改车站信息
     * @param formerSta 更改前的车站名
     * @param latterSta 更改后的车站名
     * @return 数据库中受影响的行数
     */
    public static int updateSta(String formerSta, String latterSta)
    {
        String sql = "UPDATE T_STATION SET STA_NAME ='" + latterSta + "' WHERE STA_NAME = '" + formerSta + "'";
        int r = DBUtil.executeUpdate(sql);
        return r;
    }

    /**
     * 增加车站信息
     * @param staName 车站名
     * @param lon 车站经度
     * @param lat 车站纬度
     * @return 数据库中受影响的行数
     */
    public static int addStation(String staName, double lon, double lat)
    {
        String sql = "INSERT INTO T_STATION(STA_NAME, LONGITUDE, LATITUDE) VALUES('" + staName +"', " + lon + ", " + lat + ");";
        int r = DBUtil.executeUpdate(sql);
        return r;
    }
}
