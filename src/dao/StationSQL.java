package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StationSQL
{
    /**
     * ��ȡ��վվ��
     * @return վ���ַ�������
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
     * ��ѯ��վ��Ϣ
     * @return ��վ��Ϣ��Ķ�ά����
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
     * �жϳ�վ�Ƿ����
     * @param staName ��վ��
     * @return �����ڷ���true�����򷵻�false
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
     * ��ѯ��վ�ľ�����Ϣ
     * @param name ��վ����
     * @return ��վ����
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
     * ��ѯ��վ��γ����Ϣ
     * @param name ��վ����
     * @return ��վγ��
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
     * ���ĳ�վ��Ϣ
     * @param formerSta ����ǰ�ĳ�վ��
     * @param latterSta ���ĺ�ĳ�վ��
     * @return ���ݿ�����Ӱ�������
     */
    public static int updateSta(String formerSta, String latterSta)
    {
        String sql = "UPDATE T_STATION SET STA_NAME ='" + latterSta + "' WHERE STA_NAME = '" + formerSta + "'";
        int r = DBUtil.executeUpdate(sql);
        return r;
    }

    /**
     * ���ӳ�վ��Ϣ
     * @param staName ��վ��
     * @param lon ��վ����
     * @param lat ��վγ��
     * @return ���ݿ�����Ӱ�������
     */
    public static int addStation(String staName, double lon, double lat)
    {
        String sql = "INSERT INTO T_STATION(STA_NAME, LONGITUDE, LATITUDE) VALUES('" + staName +"', " + lon + ", " + lat + ");";
        int r = DBUtil.executeUpdate(sql);
        return r;
    }
}
