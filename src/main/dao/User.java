package dao;

import lib.MysqlManager;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @ClassName User
 * @Description: 用户 DB 操作
 * @Author Yung
 * @Date 2020/1/3
 * @Version V1.0
 **/
public class User {

    public String account;
    public float balance;

    public int addUser(List<User> users) {
        int result = 0;

        try {
            Statement statement = MysqlManager.getConnection().createStatement();
            for (User user : users) {
                result = MysqlManager.JDBCTemplate().update("INSERT INTO `mytrain`.`t_account`(`account`, `balance`) VALUES (?, ?) ", account, balance);
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("PRIMARY")) {
                System.err.println("主键重复");
            }
        }

        return result;
    }
}
