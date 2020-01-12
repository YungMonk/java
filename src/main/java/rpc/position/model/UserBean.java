package rpc.position.model;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @ClassName UserBean
 * @Description: TODO
 * @Author Yung
 * @Date 2020/1/6
 * @Version V1.0
 **/
public class UserBean {
    public static void jdbcTem() {
        // 启动IoC容器
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        // 获取IoC容器中JdbcTemplate实例
        JdbcTemplate jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");
        String sql = "INSERT INTO `mytrain`.`t_account`(`account`, `balance`) VALUES (?,?)";
        int count = jdbcTemplate.update(sql, "caoyc", 3000);
        System.out.println(count);
    }
}
