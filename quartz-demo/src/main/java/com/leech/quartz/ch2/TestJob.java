package com.leech.quartz.ch2;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TestJob implements Job {

    @Override
    public void execute(JobExecutionContext jobexecutioncontext) throws JobExecutionException {
        String id = jobexecutioncontext.getJobDetail().getJobDataMap().getString("id");
        System.out.println("threadId: " + Thread.currentThread().getId() + ", id: " + id);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}