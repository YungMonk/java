package com.ifchange.rpc.position.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @ClassName UserBean
 * @Description: 用户操作
 * @Author Yung
 * @Date 2020/1/6
 * @Version V1.0
 **/
@Service
public class UserBean {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int[] addUser (List<User> users) {
        String $sql = "INSERT INTO `mytrain`.`t_account`(`account`, `balance`) VALUES (?,?)";

        return jdbcTemplate.batchUpdate($sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                User user = users.get(i);
                ps.setString(1, user.account);
                ps.setFloat(2,user.balance);
            }
            @Override
            public int getBatchSize() {
                return users.size();
            }
        });
    }
}
