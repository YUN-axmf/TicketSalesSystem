package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSQL
{
    /**
     * ��ѯ�û���Ϣ
     *
     * @return �����û���Ϣ��λ�����
     */
    public static Object[][] queryUser()
    {
        Object[][] temp = new Object[100][5];
        int index = 0;
        String sql = "SELECT * FROM T_USER";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            while (rs.next())
            {
                temp[index][0] = rs.getString(1);
                temp[index][1] = rs.getString(2);
                temp[index][2] = rs.getString(3);
                temp[index][3] = TicketSQL.userTicNum(rs.getString(1));
                temp[index][4] = userPassNum(rs.getString(1));
                index++;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        Object[][] userInfo = new Object[index][5];
        for (int i = 0; i < index; i++)
            for (int j = 0; j < 5; j++)
                userInfo[i][j] = temp[i][j];
        return userInfo;
    }

    /**
     * ��ѯ�û������֤��
     * @return �û����֤������
     */
    public static String[] queryUserId()
    {
        String[] temp = new String[100];
        int index = 0;
        String sql = "SELECT ID_NO FROM T_USER";
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
        String[] passIdInfo = new String[index];
        for (int i = 0; i < index; i++)
            passIdInfo[i] = temp[i];
        return passIdInfo;
    }

    /**
     * ��ѯ�û�����
     * @param userId �û����֤��
     * @return �û�����
     */
    public static String queryUserName(String userId)
    {
        String name = null;
        String sql = "SELECT NAME FROM T_USER WHERE ID_NO='" + userId + "'";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            if (rs.next())
                name = rs.getString(1);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * ��ѯ�û�����
     * @param userId �û����֤��
     * @return �û�����
     */
    public static String queryUserPwd(String userId)
    {
        String password = null;
        String sql = "SELECT PWD FROM T_USER WHERE ID_NO='" + userId + "'";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            if (rs.next())
                password = rs.getString(1);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return password;
    }

    /**
     * �����û�����
     * @param userId �û����֤��
     * @param password ����
     * @return ���ݿ�����Ӱ��ļ�¼
     */
    public static int updateUserPwd(String userId, String password)
    {
        String sql = "UPDATE T_USER SET PWD ='" + password + "' WHERE ID_NO='" + userId + "'";
        int r = DBUtil.executeUpdate(sql);
        return r;
    }

    /**
     * ��ѯ�˳��˵����֤��
     * @return �˳������֤������
     */
    public static String[] queryPassengerId()
    {
        String[] temp = new String[100];
        int index = 0;
        String sql = "SELECT ID_NO FROM T_PASSENGER";
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
        String[] passIdInfo = new String[index];
        for (int i = 0; i < index; i++)
            passIdInfo[i] = temp[i];
        return passIdInfo;
    }

    /**
     * ��ѯ�û��˳�������
     * @param user �û����֤��
     * @return �˳�������
     */
    public static int userPassNum(String user)
    {
        int index = 0;
        String sql = "SELECT * FROM VW_INFO WHERE USER_ID = '" + user + "'";
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
     * ��ѯָ���û���ӵĳ˳�����Ϣ
     * @param user �˳������֤��
     * @return ���س˳�����Ϣ��λ�����
     */
    public static Object[][] queryPassenger(String user)
    {
        Object[][] temp = new Object[100][2];
        int index = 0;
        String sql = "SELECT * FROM VW_INFO WHERE USER_ID = '" + user + "'";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            while (rs.next())
            {
                temp[index][0] = rs.getString(3);
                temp[index][1] = rs.getString(4);
                index++;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        Object[][] passengerInfo = new Object[index][2];
        for (int i = 0; i < index; i++)
            for (int j = 0; j < 2; j++)
                passengerInfo[i][j] = temp[i][j];
        return passengerInfo;
    }

    /**
     * ��ѯָ���û���ӵĳ˳�����Ϣ�͸ó˳���ӵ�г�Ʊ��
     * @param user �˳������֤��
     * @return ���س˳�����Ϣ��λ�����
     */
    public static Object[][] queryPassInfo(String user)
    {
        Object[][] temp = new Object[100][3];
        int index = 0;
        String sql = "SELECT * FROM VW_INFO WHERE USER_ID = '" + user + "'";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            while (rs.next())
            {
                temp[index][0] = rs.getString(3);
                temp[index][1] = rs.getString(4);
                temp[index][2] = TicketSQL.passTicNum(rs.getString(3));
                index++;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        Object[][] passengerInfo = new Object[index][3];
        for (int i = 0; i < index; i++)
            for (int j = 0; j < 3; j++)
                passengerInfo[i][j] = temp[i][j];
        return passengerInfo;
    }

    /**
     * ��ѯָ���û�����ĳ�Ʊ
     * @param user �û����֤��
     * @return ����ָ���û�����ĳ�Ʊ��ά�����
     */
    public static Object[][] queryPurchaserTic(String user)
    {
        Object[][] temp = new Object[100][13];
        int index = 0;
        String sql = "SELECT * FROM VW_TICKET WHERE PURCHASER_ID = '" + user + "'";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            while (rs.next())
            {
                temp[index][0] = rs.getString(1);
                temp[index][1] = rs.getString(2).substring(0, 19);
                temp[index][2] = rs.getString(3);
                temp[index][3] = rs.getString(6);
                temp[index][4] = rs.getString(7);
                temp[index][5] = rs.getDouble(8);
                temp[index][6] = rs.getString(9);
                temp[index][7] = rs.getString(10);
                temp[index][8] = rs.getString(11);
                temp[index][9] = rs.getString(12);
                temp[index][10] = rs.getString(13);
                temp[index][11] = rs.getString(14);
                temp[index][12] = rs.getString(15);
                index++;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        Object[][] purchaserTicInfo = new Object[index][13];
        for (int i = 0; i < index; i++)
            for (int j = 0; j < 13; j++)
                purchaserTicInfo[i][j] = temp[i][j];
        return purchaserTicInfo;
    }

    /**
     * ��ѯָ���û������ĳ�Ʊ
     * @param user �û����֤��
     * @return ����ָ���û������ĳ�Ʊ��ά�����
     */
    public static Object[][] querySelfTic(String user)
    {
        Object[][] temp = new Object[100][13];
        int index = 0;
        String sql = "SELECT * FROM VW_TICKET WHERE PASSENGER_ID = '" + user + "'";
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
                temp[index][7] = rs.getString(10);
                temp[index][8] = rs.getString(11);
                temp[index][9] = rs.getString(12);
                temp[index][10] = rs.getString(13);
                temp[index][11] = rs.getString(14);
                temp[index][12] = rs.getString(15);
                index++;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        Object[][] selfTicInfo = new Object[index][13];
        for (int i = 0; i < index; i++)
            for (int j = 0; j < 13; j++)
                selfTicInfo[i][j] = temp[i][j];
        return selfTicInfo;
    }

    /**
     * ��Ʊ����ɾ��T_PURCHASE���ж�Ӧ��Ʊ��ŵ�����
     * @param ticNo Ҫ��Ʊ�ĳ�Ʊ���
     * @return ���ݿ�����Ӱ�������
     */
    public static int refundTicket(String ticNo)
    {
        String sql = "DELETE FROM T_PURCHASE WHERE TIC_NO='" + ticNo + "'";
        int r = DBUtil.executeUpdate(sql);
        return r;
    }

    /**
     * ɾ���û��ĳ˳��ˣ���ɾ��T_ADD_PASSENGER������
     * @param userID �û����֤��
     * @param passID �˿����֤��
     * @return ���ݿ�����Ӱ��ļ�¼��
     */
    public static int deletePassenger(String userID, String passID)
    {
        String sql = "DELETE FROM T_ADD_PASSENGER WHERE PASS_ID_NO='" + passID + "' AND USER_ID_NO= '" + userID + "'";
        int r = DBUtil.executeUpdate(sql);
        return r;
    }

    /**
     * ��ӳ˿ͣ�����T_PASSENGER�����������
     * @param passID �˿����֤��
     * @param name �˿�����
     * @return ���ݿ�����Ӱ��ļ�¼��
     */
    public static int insertPassenger(String passID, String name)
    {
        String sql = "INSERT INTO T_PASSENGER(ID_NO, NAME) VALUES('" + passID + "', '" + name + "')";
        int r = DBUtil.executeUpdate(sql);
        return r;
    }

    /**
     * ����û��ĳ˳��ˣ������T_ADD_PASSENGER������
     * @param user �û����֤��
     * @param passID �˿����֤��
     * @return ���ݿ�����Ӱ��ļ�¼��
     */
    public static int insertAddPassenger(String user, String passID)
    {
        String sql = "INSERT INTO T_ADD_PASSENGER(USER_ID_NO, PASS_ID_NO) VALUES('" + user + "', '" + passID + "')";
        int r = DBUtil.executeUpdate(sql);
        return r;
    }

    /**
     * �жϳ˿��Ƿ���T_PASSENGER����
     * @param id �˿����֤��
     * @return �����ڷ����棬����Ϊ��
     */
    public static boolean isPassExisting(String id)
    {
        boolean isExisting = false;
        String sql = "SELECT * FROM T_PASSENGER WHERE ID_NO='" + id + "'";
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
     * �ж��û��Ƿ���T_USER����
     * @param id �û����֤��
     * @return �����ڷ����棬����Ϊ��
     */
    public static boolean isUserExisting(String id)
    {
        boolean isExisting = false;
        String sql = "SELECT * FROM T_USER WHERE ID_NO='" + id + "'";
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
     * �жϳ˿��Ƿ��ڶ�Ӧ�û���T_ADD_PASSENGER����
     * @param userID �û����֤��
     * @param passID �˿����֤��
     * @return �����ڷ����棬����Ϊ��
     */
    public static boolean isAddPassExisting(String userID, String passID)
    {
        boolean isExisting = false;
        String sql = "SELECT * FROM T_ADD_PASSENGER WHERE USER_ID_NO='" + userID + "' AND PASS_ID_NO='" + passID + "'";
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
     * �û�ע�ᣬ����T_USER��������û���Ϣ
     * @param id �û����֤��
     * @param name �û�����
     * @param password �û�����
     * @return ���ݿ�����Ӱ��ļ�¼��
     */
    public static int userRegister(String id, String name, String password)
    {
        String sql = "INSERT INTO T_USER(ID_NO, NAME, PWD) VALUES('" + id + "', '" + name + "', '" + password + "')";
        int r = DBUtil.executeUpdate(sql);
        return r;
    }

    /**
     * ɾ���û���Ϣ
     * @param userId �û����֤��
     * @return ���ݿ�����Ӱ��ļ�¼��
     */
    public static int deleteUser(String userId)
    {
        String sql = "DELETE FROM T_USER WHERE ID_NO='" + userId + "'";
        int r = DBUtil.executeUpdate(sql);
        return r;
    }
}
