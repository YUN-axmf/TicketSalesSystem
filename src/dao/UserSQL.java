package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSQL
{
    /**
     * 查询用户信息
     *
     * @return 返回用户信息二位数组表
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
     * 查询用户的身份证号
     * @return 用户身份证号数组
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
     * 查询用户姓名
     * @param userId 用户身份证号
     * @return 用户姓名
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
     * 查询用户密码
     * @param userId 用户身份证号
     * @return 用户密码
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
     * 更改用户密码
     * @param userId 用户身份证号
     * @param password 密码
     * @return 数据库中受影响的记录
     */
    public static int updateUserPwd(String userId, String password)
    {
        String sql = "UPDATE T_USER SET PWD ='" + password + "' WHERE ID_NO='" + userId + "'";
        int r = DBUtil.executeUpdate(sql);
        return r;
    }

    /**
     * 查询乘车人的身份证号
     * @return 乘车人身份证号数组
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
     * 查询用户乘车人数量
     * @param user 用户身份证号
     * @return 乘车人数量
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
     * 查询指定用户添加的乘车人信息
     * @param user 乘车人身份证号
     * @return 返回乘车人信息二位数组表
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
     * 查询指定用户添加的乘车人信息和该乘车人拥有车票数
     * @param user 乘车人身份证号
     * @return 返回乘车人信息二位数组表
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
     * 查询指定用户购买的车票
     * @param user 用户身份证号
     * @return 返回指定用户购买的车票二维数组表
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
     * 查询指定用户乘坐的车票
     * @param user 用户身份证号
     * @return 返回指定用户乘坐的车票二维数组表
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
     * 退票，即删除T_PURCHASE表中对应车票编号的数据
     * @param ticNo 要退票的车票编号
     * @return 数据库中受影响的行数
     */
    public static int refundTicket(String ticNo)
    {
        String sql = "DELETE FROM T_PURCHASE WHERE TIC_NO='" + ticNo + "'";
        int r = DBUtil.executeUpdate(sql);
        return r;
    }

    /**
     * 删除用户的乘车人，即删除T_ADD_PASSENGER中数据
     * @param userID 用户身份证号
     * @param passID 乘客身份证号
     * @return 数据库中受影响的记录数
     */
    public static int deletePassenger(String userID, String passID)
    {
        String sql = "DELETE FROM T_ADD_PASSENGER WHERE PASS_ID_NO='" + passID + "' AND USER_ID_NO= '" + userID + "'";
        int r = DBUtil.executeUpdate(sql);
        return r;
    }

    /**
     * 添加乘客，即向T_PASSENGER表中添加数据
     * @param passID 乘客身份证号
     * @param name 乘客姓名
     * @return 数据库中受影响的记录数
     */
    public static int insertPassenger(String passID, String name)
    {
        String sql = "INSERT INTO T_PASSENGER(ID_NO, NAME) VALUES('" + passID + "', '" + name + "')";
        int r = DBUtil.executeUpdate(sql);
        return r;
    }

    /**
     * 添加用户的乘车人，即添加T_ADD_PASSENGER中数据
     * @param user 用户身份证号
     * @param passID 乘客身份证号
     * @return 数据库中受影响的记录数
     */
    public static int insertAddPassenger(String user, String passID)
    {
        String sql = "INSERT INTO T_ADD_PASSENGER(USER_ID_NO, PASS_ID_NO) VALUES('" + user + "', '" + passID + "')";
        int r = DBUtil.executeUpdate(sql);
        return r;
    }

    /**
     * 判断乘客是否在T_PASSENGER表中
     * @param id 乘客身份证号
     * @return 若存在返回真，否则为假
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
     * 判断用户是否在T_USER表中
     * @param id 用户身份证号
     * @return 若存在返回真，否则为假
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
     * 判断乘客是否在对应用户的T_ADD_PASSENGER表中
     * @param userID 用户身份证号
     * @param passID 乘客身份证号
     * @return 若存在返回真，否则为假
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
     * 用户注册，即向T_USER表中添加用户信息
     * @param id 用户身份证号
     * @param name 用户姓名
     * @param password 用户密码
     * @return 数据库中受影响的记录数
     */
    public static int userRegister(String id, String name, String password)
    {
        String sql = "INSERT INTO T_USER(ID_NO, NAME, PWD) VALUES('" + id + "', '" + name + "', '" + password + "')";
        int r = DBUtil.executeUpdate(sql);
        return r;
    }

    /**
     * 删除用户信息
     * @param userId 用户身份证号
     * @return 数据库中受影响的记录数
     */
    public static int deleteUser(String userId)
    {
        String sql = "DELETE FROM T_USER WHERE ID_NO='" + userId + "'";
        int r = DBUtil.executeUpdate(sql);
        return r;
    }
}
