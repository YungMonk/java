package driver.lib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @ClassName MysqlManager
 * @Description: TODO
 * @Author Yung
 * @Date 2020/1/3
 * @Version V1.0
 **/
public class MysqlManager {
    private static Connection mConnect;

    static {
        try {
            System.out.println("init---");
            Class.forName("com.mysql.cj.jdbc.Driver");
            mConnect = DriverManager.getConnection("jdbc:mysql://49.235.239.71:3306/mytrain", "admin", "j4xRG.mod53F");
        } catch (ClassNotFoundException | SQLException e) {
            // @TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return mConnect;
    }

    public static void close() {
        try {
            mConnect.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
