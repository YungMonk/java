package com.ifchange.rpc.position.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @ClassName StaticCrontabTask
 * @Description: 静态定时任务
 * @Author Yung
 * @Date 2020/1/17
 * @Version V1.0
 **/
@Component          // 相对与在持久层、业务层和控制层分别采用 @Repository、@Service 和 @Controller 对分层中的类进行注释，@Component注解用于对那些比较中立的类进行注释；
@EnableScheduling   // 1.开启定时任务
@EnableAsync        // 2.开启多线程
public class StaticCrontabTask {

    /**
     * @MethodName: first
     * @Description: 自动扫描，启动时间点之后5秒执行一次
     * @Param: []
     * @Return: void
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    @Async
    @Scheduled(fixedDelay = 1000*10 )  // 间隔1*10秒
    public void first() throws InterruptedException {
        System.out.println("第一个定时任务开始 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
        System.out.println("需要执行的定时任务");
        Thread.sleep(1000 * 10);
    }

    /**
     * @MethodName: second
     * @Description: 自动扫描，启动时间点之后10秒执行一次
     * @Param: []
     * @Return: void
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    @Async
    @Scheduled(cron = "0 0 * * * ?") // 每小时一次
    public void second() {
        System.out.println("第二个定时任务开始 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
        System.out.println("需要执行的定时任务");
    }

}
