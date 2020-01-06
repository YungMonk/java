import dao.User;
import lib.MysqlManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public static void main(String[] args) throws SQLException {
        List<User> list = new ArrayList<>();
        list.add(new User("王五", 1300));
        list.add(new User("朱六", 1200));
        System.out.println(User.addUser(list));

        MysqlManager.close();
    }
}