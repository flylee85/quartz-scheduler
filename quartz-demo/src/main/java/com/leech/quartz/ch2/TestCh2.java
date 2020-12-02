package com.leech.quartz.ch2;

import java.util.HashMap;
import java.util.Map;

/**
 * https://my.oschina.net/chengxiaoyuan/blog/664833
 */
public class TestCh2 {
    public static void main(String[] args) throws InterruptedException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", "1");
        QuartzManager.addJob(TestJob.class, "aaa", 1, map);

        map.clear();
        map.put("id", "2");
        QuartzManager.addJob(TestJob.class, "bbb", 1, map);

        Thread.sleep(3000);
        QuartzManager.removeJob("aaa");
        System.out.println("main end----------");
        Thread.sleep(3000);
        System.exit(1);
    }
}
