package controller;

import javafx.application.Application;
import javafx.stage.Stage;
import lib.MysqlManager;
import model.User;
import model.UserBean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Index extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws SQLException {
        List<User> list = new ArrayList<User>() {{
            add(new User("王五", 1300));
            add(new User("朱六", 1200));
        }};
        System.out.println(User.addUser(list));

        UserBean.jdbcTem();

        MysqlManager.close();
    }
}
