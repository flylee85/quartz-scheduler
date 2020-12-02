package com.leech.quartz.ch1;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;
import java.util.List;

/**
 * https://segmentfault.com/a/1190000009128277
 */
@Slf4j
public class HelloJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 从context中获取instName,groupName以及dataMap
        String instName = context.getJobDetail().getKey().getName();
        String groupName = context.getJobDetail().getKey().getGroup();
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        // 从dataMap中获取myDescription,myValue以及myArray
        String myDescription = dataMap.getString("myDescription");
        int myValue = dataMap.getInt("myValue");
        List<String> myArray = (List<String>) dataMap.get("myArray");
        log.info("---> Instance = " + instName + ", group = " + groupName
                + ", description = " + myDescription + ", value =" + myValue
                + ", array item[0] = " + myArray.get(0));
        log.info("Runtime: " + new Date().toString() + " <---");
    }

}
