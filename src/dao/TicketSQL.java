package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketSQL
{
    /**
     * ���ݳ����ء�Ŀ�ĵء�����ʱ�䡢�Ƿ��ѯȫ���г���ѯ��Ʊ���г���Ϣ
     * @param origin ������
     * @param destination Ŀ�ĵ�
     * @param dateInfo ����ʱ��
     * @param all �Ƿ��ѯȫ���г�
     * @return �г���Ϣ��ά�����
     */
    public static Object[][] queryTriNo(String origin, String destination, String dateInfo, boolean all)
    {
        Object[][] temp = new Object[100][6];
        int index = 0;
        String sql = null;
        if (all)
            sql = "EXECUTE SP_QUERY_TICKET @V_ORI_STA='" + origin + "', @V_DESTINATION='" + destination +"', @V_DEP_DATE='"+ dateInfo +"'";
        else
            sql = "EXECUTE SP_QUERY_TICKET_HIGH @V_ORI_STA='" + origin + "', @V_DESTINATION='" + destination +"', @V_DEP_DATE='"+ dateInfo +"'";

        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            while (rs.next())
            {
                temp[index][0] = rs.getString(1);
                temp[index][1] = rs.getString(2);
                temp[index][2] = rs.getString(3).substring(0, 5);
                temp[index][3] = rs.getString(4);
                temp[index][4] = rs.getString(5).substring(0, 5);
                temp[index][5] = timeDiff(rs.getString(3).substring(0, 5), rs.getString(5).substring(0, 5));
                index++;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        Object[][] triNoInfo = new Object[index][6];
        for (int i = 0; i < index; i++)
            for (int j = 0; j < 6; j++)
                triNoInfo[i][j] = temp[i][j];
        return triNoInfo;
    }

    /**
     * ����ָ���������ڵ����й�Ʊ��Ϣ
     * @param date ��������
     * @return ��Ʊ��Ϣ��λ����
     */
    public static Object[][] queryPurchase(String date)
    {
        Object[][] temp = new Object[100][10];
        int index = 0;
        String sql = "SELECT * FROM VW_TICKET WHERE DEP_DATE='" + date + "'";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            while (rs.next())
            {
                temp[index][0] = rs.getString(1);
                temp[index][1] = rs.getString(2).substring(0, 19);
                temp[index][2] = rs.getString(3);
                temp[index][3] = rs.getString(4);
                temp[index][4] = rs.getString(5);
                temp[index][5] = rs.getDouble(8);
                temp[index][6] = rs.getString(9);
                temp[index][7] = rs.getString(10);;
                temp[index][8] = rs.getString(11);
                temp[index][9] = rs.getString(12);
                index++;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        Object[][] ticInfo = new Object[index][10];
        for (int i = 0; i < index; i++)
            for (int j = 0; j < 10; j++)
                ticInfo[i][j] = temp[i][j];
        return ticInfo;
    }

    /**
     * ����ָ����������ָ���г��Ĺ�Ʊ��Ϣ
     * @param date ��������
     * @param triNo �г���
     * @return ��Ʊ��Ϣ��λ����
     */
    public static Object[][] queryPurchase(String date, String triNo)
    {
        Object[][] temp = new Object[100][10];
        int index = 0;
        String sql = "SELECT * FROM VW_TICKET WHERE DEP_DATE='" + date + "' AND TRI_NO='" + triNo + "'";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            while (rs.next())
            {
                temp[index][0] = rs.getString(1);
                temp[index][1] = rs.getString(2).substring(0, 19);
                temp[index][2] = rs.getString(3);
                temp[index][3] = rs.getString(4);
                temp[index][4] = rs.getString(5);
                temp[index][5] = rs.getString(6);
                temp[index][6] = rs.getString(7);
                temp[index][7] = rs.getDouble(8);
                temp[index][8] = rs.getString(9);
                temp[index][9] = rs.getString(10);
                index++;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        Object[][] ticInfo = new Object[index][10];
        for (int i = 0; i < index; i++)
            for (int j = 0; j < 10; j++)
                ticInfo[i][j] = temp[i][j];
        return ticInfo;
    }


    /**
     * ����ָ���������ڵ����г�Ʊ��Ϣ
     * @param date ��������
     * @return ��Ʊ��Ϣ��ά����
     */
    public static Object[][] queryTic(String date)
    {
        Object[][] temp = new Object[100][11];
        int index = 0;
        String sql = "SELECT * FROM T_TICKET WHERE DEP_DATE='" + date + "'";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            while (rs.next())
            {
                temp[index][0] = rs.getString(1);
                temp[index][1] = rs.getString(2);
                temp[index][2] = rs.getString(3);
                temp[index][3] = rs.getString(4);
                temp[index][4] = rs.getString(5);
                temp[index][5] = rs.getString(7);
                temp[index][6] = rs.getString(6);
                if (rs.getString(8).equals("0"))
                    temp[index][7] = "δ��";
                else
                    temp[index][7] = "����";
                temp[index][8] = rs.getString(9);
                temp[index][9] = rs.getString(10);
                temp[index][10] = rs.getString(11);
                if (rs.getString(9) == null)
                {
                    temp[index][8] = "����";
                    temp[index][9] = "����";
                    temp[index][10] = "����";
                }
                index++;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        Object[][] ticInfo = new Object[index][11];
        for (int i = 0; i < index; i++)
            for (int j = 0; j < 11; j++)
                ticInfo[i][j] = temp[i][j];
        return ticInfo;
    }

    /**
     * ����ָ����������ָ���г���Ʊ��Ϣ
     * @param date ��������
     * @param triNo ���κ�
     * @return ��Ʊ��Ϣ��ά����
     */
    public static Object[][] queryTic(String date, String triNo)
    {
        Object[][] temp = new Object[100][11];
        int index = 0;
        String sql = "SELECT * FROM T_TICKET WHERE DEP_DATE='" + date + "' AND TRI_NO='" + triNo + "'";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            while (rs.next())
            {
                temp[index][0] = rs.getString(1);
                temp[index][1] = rs.getString(2);
                temp[index][2] = rs.getString(3);
                temp[index][3] = rs.getString(4);
                temp[index][4] = rs.getString(5);
                temp[index][5] = rs.getString(7);
                temp[index][6] = rs.getString(6);
                if (rs.getString(8).equals("0"))
                    temp[index][7] = "δ��";
                else
                    temp[index][7] = "����";
                temp[index][8] = rs.getString(9);
                temp[index][9] = rs.getString(10);
                temp[index][10] = rs.getString(11);
                if (rs.getString(9) == null)
                {
                    temp[index][8] = "����";
                    temp[index][9] = "����";
                    temp[index][10] = "����";
                }
                index++;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        Object[][] ticInfo = new Object[index][11];
        for (int i = 0; i < index; i++)
            for (int j = 0; j < 11; j++)
                ticInfo[i][j] = temp[i][j];
        return ticInfo;
    }

    /**
     * ���ݳ��Ρ��������ڡ�����վ��Ŀ��վΪ�����õ��ó��ο���������Ʊ����
     * @param triNo ����
     * @param dateInfo ��������
     * @param origin ����վ
     * @param destination Ŀ��վ
     * @return ������Ʊ��
     */
    public static int queryTicClass(String triNo, String dateInfo, String origin, String destination)
    {
        int num = 0;
        String sql = "EXECUTE SP_NULL_NUM @V_TRI_NO = '" + triNo + "', @V_DEP_DATE = '" + dateInfo + "', @V_ORI_STA = '" + origin + "', @V_DESTINATION = '" + destination + "'";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            if (rs.next())
                num = rs.getInt(1);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return num;
    }

    /**
     * ���ݳ��Ρ��������ڡ���λ�ȼ�������վ��Ŀ��վ�õ��ó��ο������λ�ȼ���Ʊ����
     * @param triNo ���κ�
     * @param dateInfo ��������
     * @param origin ����վ
     * @param destination Ŀ��վ
     * @param seatClass ��λ�ȼ�
     * @return ��Ʊ����
     */
    public static int queryTicClass(String triNo, String dateInfo, String seatClass, String origin, String destination)
    {
        int num = 0;
        String sql = "EXECUTE SP_QUERY_CLASS @V_TRI_NO = '" + triNo + "', @V_DEP_DATE = '" + dateInfo + "', @V_CLASS = '" + seatClass + "', @V_ORI_STA = '" + origin + "', @V_DESTINATION = '" + destination + "'";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            if (rs.next())
                num = rs.getInt(1);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return num;
    }

    /**
     * ���ݳ��Ρ��������ڡ�����վ��Ŀ��վ�õ�������ƱƱ��
     * @param triNo ����
     * @param dateInfo ��������
     * @param origin ����վ
     * @param destination Ŀ��վ
     * @return ������ƱƱ��
     */
    public static double queryTicFare(String triNo, String dateInfo, String origin, String destination)
    {
        double fare = 0;
        String sql = "EXECUTE SP_NULL_FARE @V_TRI_NO = '" + triNo + "', @V_DEP_DATE = '" + dateInfo + "', @V_ORI_STA = '" + origin + "', @V_DESTINATION = '" + destination + "'";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            if (rs.next())
                fare = rs.getDouble(1);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return fare;
    }

    /**
     * ���ݳ��Ρ��������ڡ���λ�ȼ�������վ��Ŀ��վ�õ�Ʊ��
     * @param triNo ���κ�
     * @param dateInfo ��������
     * @param seatClass ��λ�ȼ�
     * @param origin ����վ
     * @param destination Ŀ��վ
     * @return Ʊ��
     */
    public static double queryTicFare(String triNo, String dateInfo, String seatClass, String origin, String destination)
    {
        double fare = 0;
        String sql = "EXECUTE SP_QUERY_FARE @V_TRI_NO = '" + triNo + "', @V_DEP_DATE = '" + dateInfo + "', @V_CLASS = '" + seatClass + "', @V_ORI_STA = '" + origin + "', @V_DESTINATION = '" + destination + "'";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            if (rs.next())
                fare = rs.getDouble(1);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return fare;
    }

    /**
     * ���ݳ��κš�����ʱ�䡢��λ�ȼ�������վ��Ŀ��վ��ѯ��Ʊ���
     * @param triNo
     * @param dateInfo
     * @param seatClass
     * @param origin
     * @param destination
     * @return ��Ʊ���
     */
    public static String queryTicNo(String triNo, String dateInfo, String seatClass, String origin, String destination)
    {
        String ticketNo = "";
        String sql = "SELECT TIC_NO FROM T_TICKET WHERE TRI_NO='" + triNo + "' AND DEP_DATE='" + dateInfo + "' AND CLASS='" + seatClass + "' AND ORI_STA='" + origin + "' AND DESTINATION= '" + destination + "' AND STATUS = 0";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            if (rs.next())
            {
                ticketNo = rs.getString(1);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return ticketNo;
    }

    /**
     * ���ݳ��κš�����ʱ�䡢����վ��Ŀ��վ��ѯ������Ʊ���
     * @param triNo
     * @param dateInfo
     * @param origin
     * @param destination
     * @return ��Ʊ���
     */
    public static String queryTicNo(String triNo, String dateInfo, String origin, String destination)
    {
        String ticketNo = "";
        String sql = "SELECT TIC_NO FROM T_TICKET WHERE TRI_NO='" + triNo + "' AND DEP_DATE='" + dateInfo + "' AND CLASS IS NULL AND ORI_STA='" + origin + "' AND DESTINATION= '" + destination + "' AND STATUS = 0";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            if (rs.next())
            {
                ticketNo = rs.getString(1);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return ticketNo;
    }

    /**
     * ���ݳ�Ʊ����û����֤�š��˿����֤�Ź���Ʊ
     * @param userId �û����֤��
     * @param passID �˿����֤��
     * @return ���ݿ�����Ӱ�������
     */
    public static int buyTicket(String ticketNo, String userId, String passID)
    {

        String sql = "INSERT INTO T_PURCHASE(TIC_NO, BUY_PLACE, PURCHASER_ID, PASSENGER_ID) VALUES('" + ticketNo + "', 'ONLINE', '" + userId + "', '" + passID + "')";
        int r = DBUtil.executeUpdate(sql);
        return r;
    }

    /**
     * ��ѯ�û��������Ч��Ʊ����
     * @param user �û����֤��
     * @return ��Ʊ����
     */
    public static int userTicNum(String user)
    {
        int index = 0;
        String sql = "SELECT * FROM T_PURCHASE WHERE PURCHASER_ID = '" + user + "'";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            while (rs.next())
                index++;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return index;
    }

    /**
     * ��ѯ�˳��˵���Ч��Ʊ����
     * @param passId �˳������֤��
     * @return ��Ʊ����
     */
    public static int passTicNum(String passId)
    {
        int index = 0;
        String sql = "SELECT * FROM T_PURCHASE WHERE PASSENGER_ID = '" + passId + "'";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            while (rs.next())
                index++;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return index;
    }

    /**
     * ���ݳ������λ�Ż�ȡ��λ�ȼ�
     * @param coach �����
     * @param seat ��λ��
     * @return ��λ�ȼ�
     */
    public static String querySeatClass(String coach, String seat)
    {
        String seatClass = "";
        String sql = "SELECT DISTINCT CLASS FROM T_SEAT WHERE COACH_NO='" + coach + "' AND SEAT_NO='" + seat + "'";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            if (rs.next())
            {
                seatClass = rs.getString(1);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return seatClass;
    }

    /**
     * ���ĳ�ƱΪ����δ��
     * @param ticNo ��Ʊ���
     * @param fares ����
     * @return ���ݿ�����Ӱ��ļ�¼��
     */
    public static int updateTicket(String ticNo, double fares)
    {
        // ����Ʊ
        String deleteSql = "DELETE FROM T_PURCHASE WHERE TIC_NO='" + ticNo + "'";
        DBUtil.executeUpdate(deleteSql);
        String updateSql = "UPDATE T_TICKET SET FARES=" + fares +", COACH_NO=NULL, SEAT_NO=NULL, CLASS=NULL WHERE TIC_NO='" + ticNo + "'";
        int r = DBUtil.executeUpdate(updateSql);
        return r;
    }

    /**
     * ����������ƱΪδ��
     * @param ticNo ��Ʊ���
     * @param fares ����
     * @param coach �����
     * @param seat ��λ��
     * @param seatClass ��λ�ȼ�
     * @return ���ݿ�����Ӱ��ļ�¼��
     */
    public static int updateTicket(String ticNo, double fares, int coach, String seat, String seatClass)
    {
        // ����Ʊ
        String deleteSql = "DELETE FROM T_PURCHASE WHERE TIC_NO='" + ticNo + "'";
        DBUtil.executeUpdate(deleteSql);
        String updateSql = "UPDATE T_TICKET SET FARES=" + fares +", COACH_NO=" + coach + ", SEAT_NO='" + seat + "', CLASS='" + seatClass + "' WHERE TIC_NO='" + ticNo + "'";
        int r = DBUtil.executeUpdate(updateSql);
        return r;
    }

    /**
     * ���ĳ�ƱΪ��������
     * @param ticNo ��Ʊ���
     * @param fares ����
     * @param passId �˳������֤��
     * @param purchaserId ���������֤��
     */
    public static void updateTicket(String ticNo, double fares, String passId, String purchaserId)
    {
        boolean isSold = TicketSQL.isSold(ticNo);
        if (isSold)
        {
            String updateSql1 = "UPDATE T_TICKET SET FARES=" + fares + ", COACH_NO=NULL, SEAT_NO=NULL, CLASS=NULL WHERE TIC_NO='" + ticNo + "'";
            DBUtil.executeUpdate(updateSql1);
            String updateSql2 = "UPDATE T_PURCHASE SET PASSENGER_ID='" + passId + "', PURCHASER_ID='" + purchaserId + "' WHERE TIC_NO='" + ticNo + "'";
            DBUtil.executeUpdate(updateSql2);
        }
        else
        {
            String insertSql = "INSERT INTO T_PURCHASE(TIC_NO, BUY_PLACE, PURCHASER_ID, PASSENGER_ID) VALUES('" + ticNo + "', 'ONLINE', '" + purchaserId + "', '" + passId + "')";
            DBUtil.executeUpdate(insertSql);
            String updateSql = "UPDATE T_TICKET SET FARES=" + fares + ", COACH_NO=NULL, SEAT_NO=NULL, CLASS=NULL WHERE TIC_NO='" + ticNo + "'";
            DBUtil.executeUpdate(updateSql);
        }
    }

    /**
     * ���ĳ�ƱΪ��������
     * @param ticNo ��Ʊ���
     * @param fares ����
     * @param coach �����
     * @param seat ��λ��
     * @param seatClass ��λ�ȼ�
     * @param passId �˳������֤��
     * @param purchaserId ���������֤��
     */
    public static void updateTicket(String ticNo, double fares, int coach, String seat, String seatClass, String passId, String purchaserId)
    {
        boolean isSold = TicketSQL.isSold(ticNo);
        if (isSold)
        {
            String updateSql1 = "UPDATE T_TICKET SET FARES=" + fares +", COACH_NO=" + coach + ", SEAT_NO='" + seat + "', CLASS='" + seatClass + "' WHERE TIC_NO='" + ticNo + "'";
            DBUtil.executeUpdate(updateSql1);
            String updateSql2 = "UPDATE T_PURCHASE SET PASSENGER_ID='" + passId + "', PURCHASER_ID='" + purchaserId + "' WHERE TIC_NO='" + ticNo + "'";
            DBUtil.executeUpdate(updateSql2);
        }
        else
        {
            String insertSql = "INSERT INTO T_PURCHASE(TIC_NO, BUY_PLACE, PURCHASER_ID, PASSENGER_ID) VALUES('" + ticNo + "', 'ONLINE', '" + purchaserId + "', '" + passId + "')";
            DBUtil.executeUpdate(insertSql);
            String updateSql = "UPDATE T_TICKET SET FARES=" + fares +", COACH_NO=" + coach + ", SEAT_NO='" + seat + "', CLASS='" + seatClass + "' WHERE TIC_NO='" + ticNo + "'";
            DBUtil.executeUpdate(updateSql);
        }
    }

    /**
     * ����������Ʊ��Ϣ
     * @param ticNo ��Ʊ���
     * @param fares Ʊ��
     * @param date ��������
     * @param triNo �г���
     * @return ���ݿ�����Ӱ��ļ�¼��
     */
    public static int addTicket(String ticNo, double fares, String oriSta, String destination, String date, String triNo)
    {
        String sql = "INSERT INTO T_TICKET(TIC_NO, FARES, ORI_STA, DESTINATION, DEP_DATE, TRI_NO, COACH_NO, SEAT_NO, CLASS) VALUES('" + ticNo + "', " + fares + ", '" + oriSta + "', '" + destination + "', '" + date + "', '" + triNo + "', NULL, NULL, NULL);";
        int r = DBUtil.executeUpdate(sql);
        return r;
    }

    /**
     * ����������Ʊ��Ϣ
     * @param ticNo ��Ʊ���
     * @param fares Ʊ��
     * @param date ��������
     * @param triNo �г���
     * @param coach �����
     * @param seat ��λ��
     * @param seatClass ��λ�ȼ�
     * @return ���ݿ�����Ӱ��ļ�¼��
     */
    public static int addTicket(String ticNo, double fares, String oriSta, String destination, String date, String triNo, int coach, String seat, String seatClass)
    {
        String sql = "INSERT INTO T_TICKET(TIC_NO, FARES, ORI_STA, DESTINATION, DEP_DATE, TRI_NO, COACH_NO, SEAT_NO, CLASS) VALUES('" + ticNo + "', " + fares + ", '" + oriSta + "', '" + destination + "', '" + date + "', '" + triNo + "', " + coach +", '" + seat + "', '" + seatClass +"');";
        int r = DBUtil.executeUpdate(sql);
        return r;
    }

    /**
     * ɾ����Ʊ
     * @param ticNo ��Ʊ���
     * @return ���ݿ�����Ӱ��ļ�¼��
     */
    public static int deleteTicket(String ticNo)
    {
        if (isSold(ticNo))
        {
            String sql = "DELETE FROM T_PURCHASE WHERE TIC_NO='" + ticNo + "'";
            DBUtil.executeUpdate(sql);
        }
        String sql = "DELETE FROM T_TICKET WHERE TIC_NO='" + ticNo + "'";
        int r = DBUtil.executeUpdate(sql);
        return r;
    }

    /**
     * ���ӹ�Ʊ��Ϣ
     * @param ticNo ��Ʊ���
     * @param purchaserId ��Ʊ�����֤��
     * @param passId �˿����֤��
     * @return ���ݿ���Ӱ��ļ�¼��
     */
    public static int addPurchase(String ticNo, String purchaserId, String passId)
    {
        String sql = "INSERT INTO T_PURCHASE(TIC_NO, BUY_PLACE, PURCHASER_ID, PASSENGER_ID) VALUES('" + ticNo + "', 'ONLINE', '" + purchaserId + "', '" + passId + "');";
        int r = DBUtil.executeUpdate(sql);
        return r;
    }

    /**
     * �жϳ�Ʊ����Ƿ��Ѿ�����
     * @param ticNo ��Ʊ���
     * @return ���ڷ���true�������ڷ���false
     */
    public static boolean isExistingTicket(String ticNo)
    {
        boolean isExisting = false;
        String sql = "SELECT * FROM T_TICKET WHERE TIC_NO='" + ticNo + "'";
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
     * �жϳ�Ʊ�Ƿ��Ѿ��۳�
     * @param ticNo ��Ʊ���
     * @return �۳�����true��δ�۳�����false
     */
    public static boolean isSold(String ticNo)
    {
        boolean isSold = false;
        String sql = "SELECT * FROM T_TICKET WHERE TIC_NO='" + ticNo + "' AND STATUS=1";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            if (rs.next())
                isSold = true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return isSold;
    }

    /**
     * ��֤��λ�Ƿ��Ѿ���Ʊ
     * @param triNo ���κ�
     * @param date ��������
     * @param coach �����
     * @param seat  ��λ��
     * @return ��Ʊ����false����Ʊ����true
     */
    public static boolean validSeat(String triNo, String date, int coach, String seat)
    {
        boolean valid = true;
        String sql = "SELECT * FROM T_TICKET WHERE TRI_NO='" + triNo + "' AND DEP_DATE='" + date + "'AND COACH_NO=" + coach + " AND SEAT_NO='" + seat + "'";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            if (rs.next())
                valid = false;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return valid;
    }

    /**
     * ���ݳ�Ʊ��Ż�ȡ��Ʊ��Ϣ
     * @param ticNo ��Ʊ���
     * @return ��Ʊ��Ϣ����
     */
    public static Object[] queryTicInfo(String ticNo)
    {
        Object[] ticInfo = new Object[11];
        String sql = "SELECT * FROM T_TICKET WHERE TIC_NO='" + ticNo + "'";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            if (rs.next())
            {
                ticInfo[0] = rs.getString(1);
                ticInfo[1] = rs.getDouble(2);
                ticInfo[2] = rs.getString(3);
                ticInfo[3] = rs.getString(4);
                ticInfo[4] = rs.getString(5);
                ticInfo[5] = rs.getString(6);
                ticInfo[6] = rs.getString(7);
                ticInfo[7] = rs.getDouble(8);
                ticInfo[8] = rs.getString(9);
                ticInfo[9] = rs.getString(10);
                ticInfo[10] = rs.getString(11);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return ticInfo;
    }

    /**
     * ���ݳ�Ʊ��ù�Ʊ�����֤��
     * @param ticNo ��Ʊ���
     * @return ��Ʊ�����֤��
     */
    public static String queryPurchaser(String ticNo)
    {
        String id = "";
        String sql = "SELECT * FROM VW_TICKET WHERE TIC_NO='" + ticNo + "'";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            if (rs.next())
            {
                id = rs.getString(4);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * ���ݳ��κźͷ������ڻ��δ�۳�Ʊ��
     * @param triNo
     * @param dateInfo
     * @return
     */
    public static int queryTicNum(String triNo, String dateInfo)
    {
        int num = 0;
        String sql = "EXECUTE SP_SUM_TICKET @V_TRI_NO = '" + triNo + "', @V_DEP_DATE = '" + dateInfo + "'";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            if (rs.next())
                num = rs.getInt(1);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return num;
    }

    /**
     * ���ݳ��κźͷ������ڻ�����г�Ʊ��
     * @param dateInfo
     * @return
     */
    public static int queryAllTicNum(String dateInfo)
    {
        int num = 0;
        String sql = "EXECUTE SP_SUM_TICKET_ALL_TICKET @V_DEP_DATE = '" + dateInfo + "'";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            if (rs.next())
                num = rs.getInt(1);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return num;
    }

    /**
     * ���ݳ��κźͷ������ڻ�����г�Ʊ��
     * @param dateInfo
     * @return
     */
    public static int queryAllTicNum(String triNo, String dateInfo)
    {
        int num = 0;
        String sql = "EXECUTE SP_SUM_TICKET_ALL @V_TRI_NO = '" + triNo + "', @V_DEP_DATE = '" + dateInfo + "'";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            if (rs.next())
                num = rs.getInt(1);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return num;
    }


    /**
     * ���ݳ��κźͷ������ڻ��δ�۳�Ʊ��
     * @param dateInfo
     * @return
     */
    public static int queryTicNum(String dateInfo)
    {
        int num = 0;
        String sql = "EXECUTE SP_SUM_TICKET_EMPTY @V_DEP_DATE = '" + dateInfo + "'";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            if (rs.next())
                num = rs.getInt(1);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return num;
    }

    /**
     * ���ݷ������ڻ�����۶�
     * @param dateInfo
     * @return
     */
    public static double queryTicFares(String dateInfo)
    {
        double sum = 0;
        String sql = "EXECUTE SP_SUM_ALL_FARES @V_DEP_DATE = '" + dateInfo + "'";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            if (rs.next())
                sum = rs.getDouble(1);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return sum;
    }

    /**
     * ���ݳ��κźͷ������ڻ�����۶�
     * @param triNo
     * @param dateInfo
     * @return
     */
    public static double queryTicFares(String triNo, String dateInfo)
    {
        double sum = 0;
        String sql = "EXECUTE SP_SUM_FARES @V_TRI_NO = '" + triNo + "', @V_DEP_DATE = '" + dateInfo + "'";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            if (rs.next())
                sum = rs.getDouble(1);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return sum;
    }

    /**
     * �õ�����ʱ�������ַ�����ʱ���
     * @param time1 ʱ���ַ���1
     * @param time2 ʱ���ַ���2
     * @return ʱ����ַ���
     */
    public static String timeDiff(String time1, String time2)
    {
        String time = "";
        int minute1 = Integer.parseInt(time1.substring(0,2)) * 60 + Integer.parseInt(time1.substring(3,5));
        int minute2 = Integer.parseInt(time2.substring(0,2)) * 60 + Integer.parseInt(time2.substring(3,5));
        int diff = 0;
        if (minute2 >= minute1)
            diff = minute2 - minute1;
        else
            diff = minute2 - minute1 + 24 * 60;
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
