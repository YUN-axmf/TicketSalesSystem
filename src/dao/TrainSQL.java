package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TrainSQL
{
    /**
     * 根据列车号获取列车类型
     * @param triNo 列车号
     * @return 列车类型
     */
    public static String queryTriType(String triNo)
    {
        String triType = "";
        String sql = "SELECT TRI_TYPE FROM T_TRAIN WHERE TRI_NO='" + triNo + "'";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            if (rs.next())
            {
                triType = rs.getString(1);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return triType;
    }

    /**
     * 根据列车号获取始发站
     * @param triNo 列车号
     * @return 始发站
     */
    public static String queryOriSta(String triNo)
    {
        String oriSta = "";
        String sql = "SELECT DEP_STA FROM T_TRAIN WHERE TRI_NO='" + triNo + "'";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            if (rs.next())
            {
                oriSta = rs.getString(1);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return oriSta;
    }

    /**
     * 根据列车号获取终点站
     * @param triNo 列车号
     * @return 终点站
     */
    public static String queryDestination(String triNo)
    {
        String destination = "";
        String sql = "SELECT TERMINUS FROM T_TRAIN WHERE TRI_NO='" + triNo + "'";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            if (rs.next())
            {
                destination = rs.getString(1);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return destination;
    }


    /**
     * 根据列车号获取经停站名
     * @param triNo 列车号
     * @return 经停站名数组
     */
    public static String[] queryTransitSta(String triNo)
    {
        String[] temp = new String[100];
        int index = 0;
        String sql = "SELECT DISTINCT STA_NAME FROM T_TRANSIT WHERE TRI_NO='" + triNo + "'";
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
        String[] transitStaInfo = new String[index];
        for (int i = 0; i < index; i++)
            transitStaInfo[i] = temp[i];
        return transitStaInfo;
    }

    /**
     * 根据列车号获取出发站后的经停站名
     * @param triNo 列车号
     * @param oriSta 出发站
     * @return 出发站后的经停站名数组
     */
    public static String[] queryTransitDesSta(String triNo, String oriSta)
    {
        String[] temp = new String[100];
        int index = 0;
        String sql = "EXECUTE SP_QUERY_TRANSIT_DES_STA @V_TRI_NO ='" + triNo + "', @V_ORI_STA='" + oriSta + "'";
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
        String[] transitStaInfo = new String[index];
        for (int i = 0; i < index; i++)
            transitStaInfo[i] = temp[i];
        return transitStaInfo;
    }

    /**
     * 查找列车车次号
     * @return 列车车次号数组
     */
    public static String[] queryTrainNo()
    {
        String[] temp = new String[100];
        int index = 0;
        String sql = "SELECT TRI_NO FROM T_TRAIN";
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
        String[] trainNoInfo = new String[index];
        for (int i = 0; i < index; i++)
            trainNoInfo[i] = temp[i];
        return trainNoInfo;
    }

    /**
     * 查找座位的车厢号
     * @return 车厢号信息二维数组
     */
    public static String[] queryCoach()
    {
        String[] temp = new String[100];
        int index = 0;
        String sql = "SELECT DISTINCT COACH_NO FROM T_SEAT";
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
        String[] coachNoInfo = new String[index];
        for (int i = 0; i < index; i++)
            coachNoInfo[i] = temp[i];
        return coachNoInfo;
    }

    /**
     * 查找座位的座位号
     * @return 座位号信息二维数组
     */
    public static String[] querySeat()
    {
        String[] temp = new String[100];
        int index = 0;
        String sql = "SELECT DISTINCT SEAT_NO FROM T_SEAT";
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
        String[] seatNoInfo = new String[index];
        for (int i = 0; i < index; i++)
            seatNoInfo[i] = temp[i];
        return seatNoInfo;
    }

    /**
     * 查找列车的经停信息
     * @param triNo 车次号
     * @return 列车经停信息二维数组表
     */
    public static Object[][] queryTransit(String triNo)
    {
        Object[][] temp = new Object[100][6];
        int index = 0;
        String sql = "SELECT * FROM T_TRANSIT WHERE TRI_NO = '" + triNo + "' ORDER BY STA_NO";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            while (rs.next())
            {
                temp[index][0] = rs.getString(2);
                temp[index][1] = rs.getInt(3);
                temp[index][2] = rs.getString(4).substring(0, 5);
                temp[index][3] = rs.getInt(5);
                temp[index][4] = rs.getString(6).substring(0, 5);
                temp[index][5] = rs.getDouble(7);
                index++;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        Object[][] transitInfo = new Object[index][6];
        for (int i = 0; i < index; i++)
            for (int j = 0; j < 6; j++)
                transitInfo[i][j] = temp[i][j];
        return transitInfo;
    }

    /**
     * 获取列车信息
     * @return 列车信息二维数组
     */
    public static Object[][] queryTrain()
    {
        Object[][] temp = new Object[100][9];
        int index = 0;
        String sql = "SELECT * FROM T_TRAIN";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            while (rs.next())
            {
                temp[index][0] = rs.getString(1);
                temp[index][1] = rs.getString(2);
                temp[index][2] = rs.getString(3).substring(0, 5);
                temp[index][3] = rs.getString(4).substring(0, 5);
                temp[index][4] = timeDayDiff(rs.getString(3).substring(0, 5), rs.getString(4).substring(0, 5), rs.getInt(6));
                temp[index][5] = rs.getInt(6);
                temp[index][6] = rs.getString(7);
                temp[index][7] = rs.getString(8);
                temp[index][8] = rs.getInt(9);
                index++;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        Object[][] trainInfo = new Object[index][9];
        for (int i = 0; i < index; i++)
            for (int j = 0; j < 9; j++)
                trainInfo[i][j] = temp[i][j];
        return trainInfo;
    }

    /**
     * 增加列车信息
     * @param triNo
     * @param type
     * @param depTime
     * @param arrTime
     * @param days
     * @param depSta
     * @param terminus
     * @return 数据库中受影响的记录数
     */
    public static int addTrain(String triNo, String type, String depTime, String arrTime, int days, String depSta, String terminus)
    {
        String sql = "INSERT INTO T_TRAIN(TRI_NO, TRI_TYPE, DEP_TIME, ARR_TIME, DAYS, DEP_STA, TERMINUS) VALUES('" + triNo + "', '" + type + "', '" + depTime + "', '" + arrTime + "', " + days + ", '" + depSta + "', '" + terminus +"')";
        int r = DBUtil.executeUpdate(sql);
        return r;
    }

    /**
     * 判断列车是否存在
     * @param triNo 列车号
     * @return 若存在返回true，否则返回false
     */
    public static boolean isExistingTrain(String triNo)
    {
        boolean isExisting = false;
        String sql = "SELECT * FROM T_TRAIN WHERE TRI_NO='" + triNo + "'";
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

    public static String timeDayDiff(String time1, String time2, int days)
    {
        String time = "";
        int minute1 = Integer.parseInt(time1.substring(0,2)) * 60 + Integer.parseInt(time1.substring(3,5));
        int minute2 = Integer.parseInt(time2.substring(0,2)) * 60 + Integer.parseInt(time2.substring(3,5));
        int diff = minute2 - minute1 + 24 * 60 * days;
        int hour = diff / 60;
        int minute = diff % 60;
        if (hour < 10 && minute < 10)
            time = "0" + hour + ":0" + minute;
        else if (hour >= 10 && minute < 10)
            time = hour + ":0" + minute;
        else if (hour < 10 && minute >= 10)
            time = "0" + hour + ":" + minute;
        else
            time = hour + ":" + minute;
        return time;
    }
}
