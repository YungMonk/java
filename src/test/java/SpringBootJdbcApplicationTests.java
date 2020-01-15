import com.ifchange.rpc.position.Application;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @ClassName SpringBootJdbcApplicationTests
 * @Description: 数据库配置测试
 * @Author Yung
 * @Date 2020/1/14
 * @Version V1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SpringBootJdbcApplicationTests {

    @Autowired
    private DataSource dataSource;

    @org.junit.Test
    public void testDataSource() throws SQLException {

        System.out.println("dataSource类型：" + dataSource.getClass());

        Connection connection = dataSource.getConnection();
        System.out.println("connection连接：" + connection);
        connection.close();
    }
}
