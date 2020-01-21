package com.ifchange.rpc.position.controller;

import com.ifchange.rpc.position.json.ApiResult;
import com.ifchange.rpc.position.model.User;
import com.ifchange.rpc.position.model.UserBean;
import com.ifchange.rpc.position.util.MRedis;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/index")
public class Index {
    @Autowired
    private UserBean userBean;

    @Autowired
    private MRedis myRedis;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
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

    @ApiOperation(value = "问候", notes = "入口访问")
    @ApiImplicitParam(name = "id", value = "用户id", required = false, dataType = "Long")
    @RequestMapping(value = "/hello")
    @ResponseBody
    public ApiResult hello(Long id) {
        myRedis.set("sys:test_set_func", "isOK");
        myRedis.set("sys:dynamic_task", "0 0 * * * ?");

        // 有序集合
        Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet<>();
        tuples.add(new DefaultTypedTuple<String>("c", 3d));
        tuples.add(new DefaultTypedTuple<String>("d", 4d));
        tuples.add(new DefaultTypedTuple<String>("e", 5d));

        myRedis.zAdd("sys:test_zset_add", tuples);

        Set<String> set = myRedis.zRevRange("sys:test_zset_add", 0, -1);
        System.out.println(set);

        Set<ZSetOperations.TypedTuple<String>> zset = myRedis.zRangeWithScore("sys:test_zset_add", 0, -1);

        for (ZSetOperations.TypedTuple<String> next : zset) {
            System.out.println("value:" + next.getValue() + " score:" + next.getScore());
        }

        return new ApiResult().success(new ArrayList<User>() {{
            add(new User("Jon", 1300));
            add(new User("Tom", 1200));
        }});
    }
}
