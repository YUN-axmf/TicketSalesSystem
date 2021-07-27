package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginSQL
{
    /**
     * 验证登录是否正确
     * @param id 用户名
     * @param password 密码
     * @param identity 身份
     * @return
     */
    public static boolean verify(String id, String password, String identity)
    {
        boolean success = false;
        String sql = null;
        if (identity == "user")
            sql = "SELECT PWD FROM T_USER WHERE ID_NO='" + id + "'";
        else if (identity == "admin")
            sql = "SELECT PWD FROM T_ADMIN WHERE ACCOUNT='" + id + "'";
        ResultSet rs = DBUtil.executeQuery(sql);
        try
        {
            if(rs != null && rs.next())
            {
                String databasePwd = rs.getString(1);
                if (databasePwd.equals(password))
                    success = true;
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return success;
    }
}
