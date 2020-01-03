package Test;

import driver.lib.MysqlManager;
import java.sql.Connection;

/**
 * @ClassName Main
 * @Description: TODO
 * @Author Yung
 * @Date 2020/1/3
 * @Version V1.0
 **/
public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        Connection conn = MysqlManager.getConnection();
        System.out.println(conn);
        MysqlManager.close();
    }
}