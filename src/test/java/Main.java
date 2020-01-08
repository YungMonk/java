import model.User;
import model.UserBean;
import lib.MysqlManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName java.Main
 * @Description: 测试包
 * @Author Yung
 * @Date 2020/1/3
 * @Version V1.0
 **/
public class Main {
    /**
     * @MethodName: main
     * @Description: 测试入口
     * @Param: [args]
     * @Return: void
     * @Author: Yung
     * @Date: 2020/1/8
     **/
    public static void main(String[] args) throws SQLException {
        List<User> list = new ArrayList<User>(){{
            add(new User("王五", 1300));
            add(new User("朱六", 1200));
        }};
        System.out.println(User.addUser(list));

        UserBean.jdbcTem();

        MysqlManager.close();
    }
}