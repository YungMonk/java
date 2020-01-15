import com.ifchange.rpc.position.Application;
import com.ifchange.rpc.position.lib.MysqlManager;
import com.ifchange.rpc.position.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SpringBootJdbcApplicationTests
 * @Description: 数据库配置测试
 * @Author Yung
 * @Date 2020/1/14
 * @Version V1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UnitTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void testDataSource() throws SQLException {

        System.out.println("dataSource类型：" + dataSource.getClass());

        Connection connection = dataSource.getConnection();
        System.out.println("connection连接：" + connection);
        connection.close();
    }

    @Test
    public void testDataGeneral() throws SQLException {
        List<User> list = new ArrayList<User>(){{
            add(new User("王五", 1300));
            add(new User("朱六", 1200));
        }};
        System.out.println(User.addUser(list));
        MysqlManager.close();
    }
}
