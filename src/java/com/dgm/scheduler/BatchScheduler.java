/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.scheduler;

import com.joy.Joy;
import com.joy.listener.ListenerBasic;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContextEvent;

/**
 *
 * @author Benoit CAYLA
 */
public class BatchScheduler extends ListenerBasic  {

    private ScheduledExecutorService scheduler;

    @Override
    public void init(ServletContextEvent event) {
        String schedulerActive = this.getContextParamFromWebXml(event.getServletContext(), "scheduler");
        
        if (schedulerActive != null) {
            if (schedulerActive.equalsIgnoreCase("true")) {
                int timer = 10;
                try {
                    Joy.log().debug( "Get timeout information");
                    timer = 30; //paramProvider.getParamValue("scheduletimeout").getIntValue();
                    Joy.log().debug("Timeout is defined to " + timer + " minutes");

                    scheduler = Executors.newSingleThreadScheduledExecutor();
                    scheduler.scheduleAtFixedRate(new BatchRefresh(), 0, timer, TimeUnit.MINUTES);
                    Joy.log().info("Scheduler Initialized");
                    
                } catch (Exception e) {
                    Joy.log().fatal(e);
                }
            }else
                Joy.log().info( "scheduler entry in not set to true, scheduler is not actived.");
        } else
            Joy.log().error("scheduler entry in web.xml was not found, scheduler is not actived.");
    }

    @Override
    public void end(ServletContextEvent event) {
        if (scheduler != null)
            scheduler.shutdownNow();
    }

}
