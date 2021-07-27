package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TrainSQL
{
    /**
     * �����г��Ż�ȡ�г�����
     * @param triNo �г���
     * @return �г�����
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
     * �����г��Ż�ȡʼ��վ
     * @param triNo �г���
     * @return ʼ��վ
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
     * �����г��Ż�ȡ�յ�վ
     * @param triNo �г���
     * @return �յ�վ
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
     * �����г��Ż�ȡ��ͣվ��
     * @param triNo �г���
     * @return ��ͣվ������
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
     * �����г��Ż�ȡ����վ��ľ�ͣվ��
     * @param triNo �г���
     * @param oriSta ����վ
     * @return ����վ��ľ�ͣվ������
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
     * �����г����κ�
     * @return �г����κ�����
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
     * ������λ�ĳ����
     * @return �������Ϣ��ά����
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
     * ������λ����λ��
     * @return ��λ����Ϣ��ά����
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
     * �����г��ľ�ͣ��Ϣ
     * @param triNo ���κ�
     * @return �г���ͣ��Ϣ��ά�����
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
     * ��ȡ�г���Ϣ
     * @return �г���Ϣ��ά����
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
     * �����г���Ϣ
     * @param triNo
     * @param type
     * @param depTime
     * @param arrTime
     * @param days
     * @param depSta
     * @param terminus
     * @return ���ݿ�����Ӱ��ļ�¼��
     */
    public static int addTrain(String triNo, String type, String depTime, String arrTime, int days, String depSta, String terminus)
    {
        String sql = "INSERT INTO T_TRAIN(TRI_NO, TRI_TYPE, DEP_TIME, ARR_TIME, DAYS, DEP_STA, TERMINUS) VALUES('" + triNo + "', '" + type + "', '" + depTime + "', '" + arrTime + "', " + days + ", '" + depSta + "', '" + terminus +"')";
        int r = DBUtil.executeUpdate(sql);
        return r;
    }

    /**
     * �ж��г��Ƿ����
     * @param triNo �г���
     * @return �����ڷ���true�����򷵻�false
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
