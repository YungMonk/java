package dao;

import lib.MysqlManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    public User(String account, float balance){
        this.account = account;
        this.balance = balance;
    }

    public static int addUser(List<User> users) throws SQLException {
        int result = 0;

        PreparedStatement stt = MysqlManager.getConnection().prepareStatement(
                "INSERT INTO `mytrain`.`t_account`(`account`, `balance`) VALUES (?,?)"
        );

        for (User user : users) {
            stt.setString(1,user.account);
            stt.setFloat(2, user.balance);
            result = stt.executeUpdate();
        }

        return result;
    }
}
