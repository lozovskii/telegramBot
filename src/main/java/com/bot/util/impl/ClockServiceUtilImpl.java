package com.bot.util.impl;

import com.bot.util.ClockServiceUtil;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClockServiceUtilImpl implements ClockServiceUtil {
    @Override
    public void scheduler() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        long delay  = 1;
        long period = 5;
        LocalDateTime setted = LocalDateTime.now().plusSeconds(15);

        executor.scheduleAtFixedRate(() -> {
            LocalDateTime now = LocalDateTime.now();
            if(now.compareTo(setted) > 0){
                System.out.println(now);
                System.out.println(setted);
                executor.shutdown();
            }else{
                System.out.println("now: " + now);
                System.out.println("setted: " + setted);
                System.out.println("Хилимся, живём");
            }
        }, delay, period, TimeUnit.SECONDS);
    }
}
