package com.ifchange.rpc.position.controller;

import com.ifchange.rpc.position.model.UserBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.ifchange.rpc.position.lib.MysqlManager;
import com.ifchange.rpc.position.model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/index")
public class Index {

    @RequestMapping("/index")
    @ResponseBody
    public Map<String, String> dbTestConn() throws SQLException {
        List<User> list = new ArrayList<User>() {{
            add(new User("王五", 1300));
            add(new User("朱六", 1200));
        }};
        System.out.println(User.addUser(list));

        UserBean.jdbcTem();

        MysqlManager.close();

        HashMap<String, String> res = new HashMap<String, String>();
        res.put("result", "add user success.");

        return res;
    }
}
