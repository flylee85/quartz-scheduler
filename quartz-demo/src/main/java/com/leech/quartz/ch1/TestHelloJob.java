package com.leech.quartz.ch1;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.quartz.JobBuilder.newJob;

public class TestHelloJob {
    public static void main(String[] args) {
        try {
            // 通过 schedulerFactory 获取一个调度器
            // Grab the Scheduler instance from the Factory
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            // 创建 jobDetail 实例，绑定 Job 实现类
            // 指明 job 的名称，所在组的名称，以及绑定 job 类
            // define the job and tie it to our HelloJob class
            JobDetail job = newJob(HelloJob.class)
                    .withIdentity("job1", "group1")
                    .build();

            // Trigger the job to run now, and then repeat every 40 seconds
            /*Trigger trigger = newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(2)
                            .repeatForever())
                    .build();*/


            // 定义调度触发规则

            // SimpleTrigger，从当前时间的下 1 秒开始，每隔 1 秒执行 1 次，重复执行 2 次
            /*Trigger trigger = TriggerBuilder.newTrigger()
                // 指明 trigger 的 name 和 group
                .withIdentity("trigger1", "group1")
                // 从当前时间的下 1 秒开始执行，默认为立即开始执行（.startNow()）
                .startAt(DateBuilder.evenSecondDate(new Date()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(1) // 每隔 1 秒执行 1 次
                        .withRepeatCount(2)) // 重复执行 2 次，一共执行 3 次
                .build();*/


            // corn 表达式，先立即执行一次，然后每隔 5 秒执行 1 次
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("*/5 * * * * ?"))
                    .build();

            // 初始化参数传递到 job
            job.getJobDataMap().put("myDescription", "Hello Quartz");
            job.getJobDataMap().put("myValue", 1990);
            List<String> list = new ArrayList<>();
            list.add("firstItem");
            job.getJobDataMap().put("myArray", list);

            // 把作业和触发器注册到任务调度中
            scheduler.scheduleJob(job, trigger);

            // 启动计划程序（实际上直到调度器已经启动才会开始运行）
            scheduler.start();
            Thread.sleep(600000);
            scheduler.shutdown();
        } catch (Exception se) {
            se.printStackTrace();
        }
    }
}
