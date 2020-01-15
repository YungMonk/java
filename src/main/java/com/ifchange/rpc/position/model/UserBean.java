package com.ifchange.rpc.position.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @ClassName UserBean
 * @Description: 用户操作
 * @Author Yung
 * @Date 2020/1/6
 * @Version V1.0
 **/
public class UserBean {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void jdbcTem() {
        System.out.println(this.jdbcTemplate.update(
                "INSERT INTO `mytrain`.`t_account`(`account`, `balance`) VALUES (?,?)",
                "caoyc", 3000
        ));
    }
}
