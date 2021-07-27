package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketSQL
{
    /**
     * 根据出发地、目的地、发车时间、是否查询全部列车查询有票的列车信息
     * @param origin 出发地
     * @param destination 目的地
     * @param dateInfo 发车时间
     * @param all 是否查询全部列车
     * @return 列车信息二维数组表
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
     * 查找指定发车日期的所有购票信息
     * @param date 发车日期
     * @return 购票信息二位数组
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
     * 查找指定发车日期指定列车的购票信息
     * @param date 发车日期
     * @param triNo 列车号
     * @return 购票信息二位数组
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
     * 查找指定发车日期的所有车票信息
     * @param date 发车日期
     * @return 车票信息二维数组
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
                    temp[index][7] = "未售";
                else
                    temp[index][7] = "已售";
                temp[index][8] = rs.getString(9);
                temp[index][9] = rs.getString(10);
                temp[index][10] = rs.getString(11);
                if (rs.getString(9) == null)
                {
                    temp[index][8] = "无座";
                    temp[index][9] = "无座";
                    temp[index][10] = "无座";
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
     * 查找指定发车日期指定列车车票信息
     * @param date 发车日期
     * @param triNo 车次号
     * @return 车票信息二维数组
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
                    temp[index][7] = "未售";
                else
                    temp[index][7] = "已售";
                temp[index][8] = rs.getString(9);
                temp[index][9] = rs.getString(10);
                temp[index][10] = rs.getString(11);
                if (rs.getString(9) == null)
                {
                    temp[index][8] = "无座";
                    temp[index][9] = "无座";
                    temp[index][10] = "无座";
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
     * 根据车次、发车日期、出发站、目的站为参数得到该车次空余无座车票总量
     * @param triNo 车次
     * @param dateInfo 发车日期
     * @param origin 出发站
     * @param destination 目的站
     * @return 无座车票数
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
     * 根据车次、发车日期、座位等级、出发站、目的站得到该车次空余该座位等级车票总量
     * @param triNo 车次号
     * @param dateInfo 发车日期
     * @param origin 出发站
     * @param destination 目的站
     * @param seatClass 座位等级
     * @return 车票总量
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
     * 根据车次、发车日期、出发站、目的站得到无座车票票价
     * @param triNo 车次
     * @param dateInfo 发车日期
     * @param origin 出发站
     * @param destination 目的站
     * @return 无座车票票价
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
     * 根据车次、发车日期、座位等级、出发站、目的站得到票价
     * @param triNo 车次号
     * @param dateInfo 发车日期
     * @param seatClass 座位等级
     * @param origin 出发站
     * @param destination 目的站
     * @return 票价
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
     * 根据车次号、发车时间、座位等级、出发站、目的站查询车票编号
     * @param triNo
     * @param dateInfo
     * @param seatClass
     * @param origin
     * @param destination
     * @return 车票编号
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
     * 根据车次号、发车时间、出发站、目的站查询无座车票编号
     * @param triNo
     * @param dateInfo
     * @param origin
     * @param destination
     * @return 车票编号
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
     * 根据车票编号用户身份证号、乘客身份证号购买车票
     * @param userId 用户身份证号
     * @param passID 乘客身份证号
     * @return 数据库中受影响的行数
     */
    public static int buyTicket(String ticketNo, String userId, String passID)
    {

        String sql = "INSERT INTO T_PURCHASE(TIC_NO, BUY_PLACE, PURCHASER_ID, PASSENGER_ID) VALUES('" + ticketNo + "', 'ONLINE', '" + userId + "', '" + passID + "')";
        int r = DBUtil.executeUpdate(sql);
        return r;
    }

    /**
     * 查询用户购买的有效车票数量
     * @param user 用户身份证号
     * @return 车票数量
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
     * 查询乘车人的有效车票数量
     * @param passId 乘车人身份证号
     * @return 车票数量
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
     * 根据车厢号座位号获取座位等级
     * @param coach 车厢号
     * @param seat 座位号
     * @return 座位等级
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
     * 更改车票为无座未售
     * @param ticNo 车票编号
     * @param fares 车费
     * @return 数据库中受影响的记录数
     */
    public static int updateTicket(String ticNo, double fares)
    {
        // 先退票
        String deleteSql = "DELETE FROM T_PURCHASE WHERE TIC_NO='" + ticNo + "'";
        DBUtil.executeUpdate(deleteSql);
        String updateSql = "UPDATE T_TICKET SET FARES=" + fares +", COACH_NO=NULL, SEAT_NO=NULL, CLASS=NULL WHERE TIC_NO='" + ticNo + "'";
        int r = DBUtil.executeUpdate(updateSql);
        return r;
    }

    /**
     * 更改有座车票为未售
     * @param ticNo 车票编号
     * @param fares 车费
     * @param coach 车厢号
     * @param seat 座位号
     * @param seatClass 座位等级
     * @return 数据库中受影响的记录数
     */
    public static int updateTicket(String ticNo, double fares, int coach, String seat, String seatClass)
    {
        // 先退票
        String deleteSql = "DELETE FROM T_PURCHASE WHERE TIC_NO='" + ticNo + "'";
        DBUtil.executeUpdate(deleteSql);
        String updateSql = "UPDATE T_TICKET SET FARES=" + fares +", COACH_NO=" + coach + ", SEAT_NO='" + seat + "', CLASS='" + seatClass + "' WHERE TIC_NO='" + ticNo + "'";
        int r = DBUtil.executeUpdate(updateSql);
        return r;
    }

    /**
     * 更改车票为无座已售
     * @param ticNo 车票编号
     * @param fares 车费
     * @param passId 乘车人身份证号
     * @param purchaserId 购买人身份证号
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
     * 更改车票为有座已售
     * @param ticNo 车票编号
     * @param fares 车费
     * @param coach 车厢号
     * @param seat 座位号
     * @param seatClass 座位等级
     * @param passId 乘车人身份证号
     * @param purchaserId 购买人身份证号
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
     * 增加无座车票信息
     * @param ticNo 车票编号
     * @param fares 票价
     * @param date 发车日期
     * @param triNo 列车号
     * @return 数据库中受影响的记录数
     */
    public static int addTicket(String ticNo, double fares, String oriSta, String destination, String date, String triNo)
    {
        String sql = "INSERT INTO T_TICKET(TIC_NO, FARES, ORI_STA, DESTINATION, DEP_DATE, TRI_NO, COACH_NO, SEAT_NO, CLASS) VALUES('" + ticNo + "', " + fares + ", '" + oriSta + "', '" + destination + "', '" + date + "', '" + triNo + "', NULL, NULL, NULL);";
        int r = DBUtil.executeUpdate(sql);
        return r;
    }

    /**
     * 增加有座车票信息
     * @param ticNo 车票编号
     * @param fares 票价
     * @param date 发车日期
     * @param triNo 列车号
     * @param coach 车厢号
     * @param seat 座位号
     * @param seatClass 座位等级
     * @return 数据库中受影响的记录数
     */
    public static int addTicket(String ticNo, double fares, String oriSta, String destination, String date, String triNo, int coach, String seat, String seatClass)
    {
        String sql = "INSERT INTO T_TICKET(TIC_NO, FARES, ORI_STA, DESTINATION, DEP_DATE, TRI_NO, COACH_NO, SEAT_NO, CLASS) VALUES('" + ticNo + "', " + fares + ", '" + oriSta + "', '" + destination + "', '" + date + "', '" + triNo + "', " + coach +", '" + seat + "', '" + seatClass +"');";
        int r = DBUtil.executeUpdate(sql);
        return r;
    }

    /**
     * 删除车票
     * @param ticNo 车票编号
     * @return 数据库中受影响的记录数
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
     * 增加购票信息
     * @param ticNo 车票编号
     * @param purchaserId 购票人身份证号
     * @param passId 乘客身份证号
     * @return 数据库受影响的记录数
     */
    public static int addPurchase(String ticNo, String purchaserId, String passId)
    {
        String sql = "INSERT INTO T_PURCHASE(TIC_NO, BUY_PLACE, PURCHASER_ID, PASSENGER_ID) VALUES('" + ticNo + "', 'ONLINE', '" + purchaserId + "', '" + passId + "');";
        int r = DBUtil.executeUpdate(sql);
        return r;
    }

    /**
     * 判断车票编号是否已经存在
     * @param ticNo 车票编号
     * @return 存在返回true，不存在返回false
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
     * 判断车票是否已经售出
     * @param ticNo 车票编号
     * @return 售出返回true，未售出返回false
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
     * 验证座位是否已经有票
     * @param triNo 车次号
     * @param date 发车日期
     * @param coach 车厢号
     * @param seat  座位号
     * @return 有票返回false，无票返回true
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
     * 根据车票编号获取车票信息
     * @param ticNo 车票编号
     * @return 车票信息数组
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
     * 根据车票获得购票人身份证号
     * @param ticNo 车票编号
     * @return 购票人身份证号
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
     * 根据车次号和发车日期获得未售车票数
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
     * 根据车次号和发车日期获得所有车票数
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
     * 根据车次号和发车日期获得所有车票数
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
     * 根据车次号和发车日期获得未售车票数
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
     * 根据发车日期获得销售额
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
     * 根据车次号和发车日期获得销售额
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
     * 得到两个时间类型字符串的时间差
     * @param time1 时间字符串1
     * @param time2 时间字符串2
     * @return 时间差字符串
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
