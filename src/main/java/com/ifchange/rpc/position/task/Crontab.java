package com.ifchange.rpc.position.task;

import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName Crontab
 * @Description: 动态修改定时任务cron参数
 * @Author Yung
 * @Date 2020/1/17
 * @Version V1.0
 **/
@Component
public class Crontab implements SchedulingConfigurer {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private static final String DEFAULT_CRON = "0/5 * * * * ?";

    private String cron = DEFAULT_CRON;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(
                // 1.添加任务内容(Runnable)
                () -> {
                    if (!cron.equals(DEFAULT_CRON)) {
                        System.out.println("");
                    }

                    // 定时任务的业务逻辑
                    System.out.println("动态修改定时任务cron参数，当前时间：" + dateFormat.format(new Date()));
                },

                // 2.设置执行周期(Trigger)
                triggerContext -> {
                    // 定时任务触发，可修改定时任务的执行周期
                    CronTrigger trigger = new CronTrigger(cron);

                    // 返回执行周期(Date)
                    return trigger.nextExecutionTime(triggerContext);
                }
        );
    }
}
