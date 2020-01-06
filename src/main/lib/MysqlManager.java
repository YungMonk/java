package lib;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @ClassName MysqlManager
 * @Description: TODO
 * @Author Yung
 * @Date 2020/1/3
 * @Version V1.0
 **/
public class MysqlManager {
    /*
     * 单例变量
     */
    private static Connection mConnect;

    /*
     * 创建单例 DB 链接
     */
    static {
        try {
            System.out.println("Init database ...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            mConnect = DriverManager.getConnection("jdbc:mysql://49.235.239.71:3306/mytrain", "admin", "j4xRG.mod53F");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @MethodName: getConnection
     * @Description: 获取 DB 链接
     * @Param: []
     * @Return: java.sql.Connection
     * @Author: Yung
     * @Date: 2020/1/3
     **/
    public static Connection getConnection() {
        return mConnect;
    }

    /**
     * @MethodName: close
     * @Description: 关闭 DB 链接
     * @Param: []
     * @Return: void
     * @Author: Yung
     * @Date: 2020/1/3
     **/
    public static void close() {
        try {
            mConnect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @MethodName: createTable
     * @Description: 创建表
     * @Param: [sql]
     * @Return: boolean
     * @Author: Yung
     * @Date: 2020/1/3
     **/
    public boolean createTable(String sql) {
        boolean result = false;

        try {
            Statement statement = mConnect.createStatement();
            result = statement.execute(sql);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
