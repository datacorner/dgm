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
package com.dgm.tasks.load;

import com.dgm.common.Utils;
import com.dgm.common.providers.ParamProvider;
import com.joy.Joy;
import com.joy.bo.BOEntityReadWrite;
import com.joy.etl.MappingSpecification;
import com.joy.etl.MappingFactory;
import com.joy.etl.StatMap;
import com.joy.mvc.actionTypes.ActionTypeTASK;
import com.joy.tasks.JoyTaskStatus;
import java.util.Date;

/**
 *
 * @author Benoit CAYLA <benoit@famillecayla.fr>
 */
public class TASKCommonLoad extends ActionTypeTASK {
    
    private void trace (String msg) {
        this.addTrace(msg);
        Joy.log().info(msg);
    }
    
    /**
     * Gather data from informatica
     * @return 
     */
    public JoyTaskStatus loadInformatica() {
        try {
            ParamProvider myParams = new ParamProvider(this.getEntities());
            String wfName = this.getTaskName();
            String result = "";
            trace("Informatica Workflow execution : " + wfName);

            String cmdLine = Utils.getInformaticaWorkflowCommandLine(myParams.getParamValue("infacmd").getStrValue(),
                                                                        myParams.getParamValue("infadomain").getStrValue(),
                                                                        myParams.getParamValue("infadis").getStrValue(),
                                                                        myParams.getParamValue("infauser").getStrValue(),
                                                                        myParams.getParamValue("infapwd").getStrValue(),
                                                                        myParams.getParamValue("infaapp").getStrValue(),
                                                                        wfName);
            trace("Launch command line: " + cmdLine);
            result = Joy.executeCommandLine(cmdLine);
            trace("Result : " + result);
            this.setMessage(result);
            
        } catch (Exception e) {
            this.addTrace("Exception while launching workflow : " + e);
            Joy.log().fatal(e);
            this.setMessage(e.toString());
            return JoyTaskStatus.Failed;
        }
        return JoyTaskStatus.Success;
    }
    
    /**
     * Load landing and SRC table into the datamart
     * @return 
     */
    public JoyTaskStatus loadInternalLanding() {
        try {
            // Add here the mappings to launch
            String[] mappings = {   "DQ Axis Source", 
                                    "Terms Type landing", 
                                    "Glossary Landing", 
                                    "Category Landing",
                                    "Term Landing",
                                    "Term RelationShips Names Landing",
                                    "Term RelationShips Landing",
                                    "Context",
                                    "Origin Landing",
                                    "Scorecard Landing",
                                    "Scorecard Group Landing", 
                                    "Metric Landing",
                                    "DataSource Landing"};

            // Add here the concetps/data corresponding to the mappings launched
            String[] concepts = {   "Dimensions", 
                                    "Term Types", 
                                    "Glossaries", 
                                    "Categories",
                                    "Terms",
                                    "Terms RelationShip Names",
                                    "Terms RelationShips",
                                    "Contexts",
                                    "Origins",
                                    "Scorecards",
                                    "Scorecard Groups", 
                                    "Metrics", 
                                    "DataSources"};

            StatMap[] stat = new StatMap[mappings.length];
            MappingSpecification mapping;

            MappingFactory maps = new MappingFactory();
            maps.init(Joy.parameters().getMapping("landing"));

            // Execute the mappings for the dimensions...
            this.resetMessages();
            int i=0;
            for (i=0; i < mappings.length; i++) {
                Joy.log().info("Load mapping: " + mappings[i]);
                mapping = maps.getMapping(mappings[i]);
                stat[i] = mapping.process(this.getEntities());
                Joy.log().info(concepts[i] + " loaded with " + stat[i].getRowsInserted() + " (inserts), " + stat[i].getRowsUpdated() + " (updates).");
                this.addMessage("<B>" + concepts[i] + ":</B> " + stat[i].getRowsInserted() + " (inserts), " + stat[i].getRowsUpdated() + " (updates).<BR>");
            }
            
            // Create a new JOB
            Joy.log().info("Create a new Job ");
            BOEntityReadWrite job = (BOEntityReadWrite)this.getEntities().getEntity("DIM_JOB");
            int newJobID = job.field("JOB_PK").getNextID();
            job.field("JOB_PK").setValue(newJobID);
            job.field("JOB_NAME").setValue("Job ID " + newJobID);
            job.field("JOB_DATETIME_LOAD").setValue(new Date());
            job.field("JOB_FUNCKEY").setValue("JOB" + String.valueOf(newJobID));
            job.insert();
            Joy.log().info("New Job created with ID = " + newJobID);
            
            // Load the Fact table
            mapping = maps.getMapping("Facts Landing");
            StatMap fctStat = mapping.process(this.getEntities(), newJobID);
            Joy.log().info("Facts loaded with " + fctStat.getRowsInserted() + " (inserts), " + fctStat.getRowsUpdated() + " (updates).");
            this.addMessage("<B>Facts:</B> " + fctStat.getRowsInserted() + " (inserts), " + fctStat.getRowsUpdated() + " (updates).<BR>");
            
        } catch (Exception e) {
            Joy.log().fatal("Fatal Error while loading datamart: " + e);
            return JoyTaskStatus.Failed;
        }
        return JoyTaskStatus.Success;
    }
    
    @Override
    public JoyTaskStatus taskExecute() {
        return JoyTaskStatus.Failed;
    }
}
