/*
 * Copyright (C) 2016 Benoit CAYLA <benoit@famillecayla.fr>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
 * @author Benoit CAYLA <benoit@famillecayla.fr>
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
