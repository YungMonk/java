package rpc.position.controller;

import rpc.position.lib.MysqlManager;
import rpc.position.model.User;
import rpc.position.model.UserBean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Index {

    public void dbTestConn() throws SQLException {
        List<User> list = new ArrayList<User>() {{
            add(new User("王五", 1300));
            add(new User("朱六", 1200));
        }};
        System.out.println(User.addUser(list));

        UserBean.jdbcTem();

        MysqlManager.close();
    }
}
