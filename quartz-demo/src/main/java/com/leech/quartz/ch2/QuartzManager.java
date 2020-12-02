package com.leech.quartz.ch2;

import org.quartz.*;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Map;

public class QuartzManager {

    private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();

    private static String HM_JOB = "HM_JOB";
    private static String HM_TRIGGER = "HM_TRIGGER";

    /**
     * 增加任务
     * @param jobClass 任务实现类
     * @param jobName 任务名称
     * @param interval 间隔时间
     * @param data 数据
     */
    public static void addJob(Class<? extends Job> jobClass, String jobName, int interval, Map<String, Object> data) {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            
            JobDetail jobDetail = JobBuilder.newJob(jobClass)
                            .withIdentity(jobName, HM_JOB)//任务名称和组构成任务key
                            .build();
            jobDetail.getJobDataMap().putAll(data);
            // 触发器
            SimpleTrigger trigger = TriggerBuilder.newTrigger()
                           .withIdentity(jobName, HM_TRIGGER)//触发器key
                           .startAt(DateBuilder.futureDate(1, IntervalUnit.SECOND))
                           .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                           .withIntervalInSeconds(interval)
                           .repeatForever())
                           .build();

            sched.scheduleJob(jobDetail, trigger);
            // 启动
            if (!sched.isShutdown()) {
                sched.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除任务 
     * 
     * @param jobName 任务名称
     */
    public static void removeJob(String jobName) {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            sched.deleteJob(new JobKey(jobName, HM_JOB));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}