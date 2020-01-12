package rpc.position.model;

import rpc.position.lib.MysqlManager;

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

    /***
     * @MethodName: User
     * @Description: 初始化构造
     * @Param: []
     * @Return:
     * @Author: Yung
     * @Date: 2020/1/6
     **/
    public User() {

    }

    /***
     * @MethodName: User
     * @Description: 初始化构造
     * @Param: [account, balance]
     * @Return:
     * @Author: Yung
     * @Date: 2020/1/6
     **/
    public User(String account, float balance) {
        this.account = account;
        this.balance = balance;
    }

    /**
     * @MethodName: addUser
     * @Description: 添加用户
     * @Param: [users]
     * @Return: int
     * @Author: Yung
     * @Date: 2020/1/6
     **/
    public static int addUser(List<User> users) throws SQLException {
        int result = 0;

        PreparedStatement stt = MysqlManager.getConnection().prepareStatement(
                "INSERT INTO `mytrain`.`t_account`(`account`, `balance`) VALUES (?,?)"
        );

        for (User user : users) {
            stt.setString(1, user.account);
            stt.setFloat(2, user.balance);
            result = stt.executeUpdate();
        }

        return result;
    }
}
