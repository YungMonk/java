package com.ifchange.rpc.position.controller;

import com.ifchange.rpc.position.model.User;
import com.ifchange.rpc.position.model.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/index")
public class Index {
    @Autowired
    private UserBean userBean;

    @RequestMapping(value ="/index", method = RequestMethod.GET)
    @ResponseBody
    public Map<?, ?> index() {
        List<User> list = new ArrayList<User>() {{
            add(new User("Jon", 1300));
            add(new User("Tom", 1200));
        }};

        HashMap<String, int[]> res = new HashMap<>();
        res.put("result", userBean.addUser(list));

        return res;
    }
}
