package com.ifchange.rpc.position.task;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName Crontab
 * @Description: 动态定时任务：动态修改定时任务 cron 参数
 * @Author Yung
 * @Date 2020/1/17
 * @Version V1.0
 **/
@Component
public class DynamicCrontabTask implements SchedulingConfigurer {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private static final String DEFAULT_CRON = "0/5 * * * * ?";

    private static String cron;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(doTask(), getTrigger());
    }

    /**
     * @MethodName: doTask
     * @Description: 添加任务内容(Runnable)
     * @Param: []
     * @Return: java.lang.Runnable
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    private Runnable doTask() {
        return new Runnable() {
            @Override
            public void run() {
                // 业务逻辑
                System.out.println("执行了MyDynamicTask,时间为:" + new Date(System.currentTimeMillis()));
            }
        };
    }

    /**
     * @MethodName: getTrigger
     * @Description: 设置执行周期(Trigger)
     * @Param: []
     * @Return: org.springframework.scheduling.Trigger
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    private Trigger getTrigger() {
        return new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                // 定时任务触发，可修改定时任务的执行周期
                CronTrigger trigger = new CronTrigger(getCron());
                // 返回执行周期(Date)
                return trigger.nextExecutionTime(triggerContext);
            }
        };
    }

    /**
     * @MethodName: getCron
     * @Description: 读取执行周期(Trigger)
     * @Param: []
     * @Return: java.lang.String
     * @Author: Yung
     * @Date: 2020/1/19
     **/
    public String getCron() {
        return DEFAULT_CRON;
    }
}
