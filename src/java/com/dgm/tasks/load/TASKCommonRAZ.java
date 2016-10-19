/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.tasks.load;

import com.dgm.common.Constants;
import com.joy.bo.BOEntityReadWrite;
import com.joy.mvc.actionTypes.ActionTypeTASK;
import com.joy.tasks.JoyTaskStatus;

/**
 *
 * @author Benoit CAYLA
 */
public class TASKCommonRAZ extends ActionTypeTASK {

    protected void purgeTable(String Table) {
        BOEntityReadWrite entity = (BOEntityReadWrite)this.getEntities().getEntity(Table);
        if (!entity.deleteAll())
            this.addTrace(Table + " not purged successfully");
    }
    
    protected void landingpurge() {
        purgeTable("LND_TERM");
        purgeTable("LND_GLOSSARY");
        purgeTable("LND_CATEGORY");
        purgeTable("LND_TERM_RELATIONSHIPS");
        purgeTable("LND_METRIC");
        purgeTable("LND_SCORECARD");
        purgeTable("LND_SCORECARD_GROUP");
        this.addTrace("Landing Tables purged");
    }
    
    protected void dtmpurge() {
        purgeTable("All Facts");
        this.addTrace("Facts purged");
        
        purgeTable("DIM_METRIC");
        purgeTable("DIM_ORIGINE");
        purgeTable("DIM_SCORECARD_GROUP");
        purgeTable("Scorecard Dimension");
        purgeTable("DIM_DQAXIS");
        purgeTable("DIM_CONTEXT");
        purgeTable("DIM_JOB");
        purgeTable("DIM_TERM");
        purgeTable("DIM_DATASOURCE");
        purgeTable("DIM_CATEGORY");
        purgeTable("DIM_GLOSSARY");
        purgeTable("DIM_TERM_RELLINKS");
        purgeTable("DIM_TERM_RELATIONSHIP");
        purgeTable("DIM_TERM_TYPE");
        purgeTable("Scorecard Dimension");
        this.addTrace("Dimensions purged");
        
        this.addTrace("Working area purged");
    }

    protected void insertUnknownLine(BOEntityReadWrite entity) {
        if (entity.insert()<0)
            this.addTrace(entity.getName() + " not initialized successfully !");
    }
    
    protected void init() {
        BOEntityReadWrite entity;
        
        entity = (BOEntityReadWrite)this.getEntities().getEntity("DIM_CONTEXT");
        entity.field("CON_PK").setValue(0);
        entity.field("CON_DESCRIPTION").setValue(Constants.UNKNOWN);
        entity.field("CON_FUNCKEY").setValue(Constants.UNKNOWN_CODE);
        insertUnknownLine(entity);
        
        entity = (BOEntityReadWrite)this.getEntities().getEntity("DIM_ORIGINE");
        entity.field("ORI_PK").setValue(0);
        entity.field("ORI_NAME").setValue(Constants.UNKNOWN);
        entity.field("ORI_FUNCKEY").setValue(Constants.UNKNOWN_CODE);
        insertUnknownLine(entity);
        
        entity = (BOEntityReadWrite)this.getEntities().getEntity("DIM_JOB");
        entity.field("JOB_PK").setValue(0);
        entity.field("JOB_NAME").setValue(Constants.UNKNOWN);
        entity.field("JOB_FUNCKEY").setValue(Constants.UNKNOWN_CODE);
        insertUnknownLine(entity);
        
        entity = (BOEntityReadWrite)this.getEntities().getEntity("Scorecard Dimension");
        entity.field("SCO_PK").setValue(0);
        entity.field("SCO_NAME").setValue(Constants.UNKNOWN);
        entity.field("SCO_DESCRIPTION").setValue(Constants.UNKNOWN);
        entity.field("SCO_FUNCKEY").setValue(Constants.UNKNOWN_CODE);
        insertUnknownLine(entity);

        entity = (BOEntityReadWrite)this.getEntities().getEntity("DIM_SCORECARD_GROUP");
        entity.field("SCG_PK").setValue(0);
        entity.field("SCO_FK").setValue(0);
        entity.field("SCG_NAME").setValue(Constants.UNKNOWN);
        entity.field("SCG_FUNCKEY").setValue(Constants.UNKNOWN_CODE);
        insertUnknownLine(entity);
        
        entity = (BOEntityReadWrite)this.getEntities().getEntity("DIM_METRIC");
        entity.field("MET_PK").setValue(0);
        entity.field("MET_NAME").setValue(Constants.UNKNOWN);
        entity.field("MET_DESCRIPTION").setValue(Constants.UNKNOWN_CODE);
        insertUnknownLine(entity);

        entity = (BOEntityReadWrite)this.getEntities().getEntity("DIM_TERM");
        entity.field("TRM_PK").setValue(0);
        entity.field("TRM_NAME").setValue(Constants.UNKNOWN);
        entity.field("TRM_FUNCKEY").setValue(Constants.UNKNOWN_CODE);
        insertUnknownLine(entity);     

        entity = (BOEntityReadWrite)this.getEntities().getEntity("DIM_DATASOURCE");
        entity.field("DSO_PK").setValue(0);
        entity.field("DSO_SOURCENAME").setValue(Constants.UNKNOWN);
        entity.field("DSO_CONNECTION").setValue(Constants.UNKNOWN);
        entity.field("DSO_FUNCKEY").setValue(Constants.UNKNOWN_CODE);
        insertUnknownLine(entity); 

        entity = (BOEntityReadWrite)this.getEntities().getEntity("DIM_DQAXIS");
        entity.field("DQX_PK").setValue(0);
        entity.field("DQX_NAME").setValue(Constants.UNKNOWN);
        entity.field("DQX_CODE").setValue(Constants.UNKNOWN_CODE);
        insertUnknownLine(entity);
        
        entity = (BOEntityReadWrite)this.getEntities().getEntity("DIM_GLOSSARY");
        entity.field("GLO_PK").setValue(0);
        entity.field("GLO_NAME").setValue(Constants.UNKNOWN);
        entity.field("GLO_DESCRIPTION").setValue(Constants.UNKNOWN_CODE);
        insertUnknownLine(entity);
        
        entity = (BOEntityReadWrite)this.getEntities().getEntity("DIM_TERM_TYPE");
        entity.field("TRT_PK").setValue(0);
        entity.field("TRT_NAME").setValue(Constants.UNKNOWN);
        entity.field("TRT_DESCRIPTION").setValue(Constants.UNKNOWN_CODE);
        insertUnknownLine(entity);
        
        entity = (BOEntityReadWrite)this.getEntities().getEntity("DIM_CATEGORY");
        entity.field("CAT_PK").setValue(0);
        entity.field("CAT_NAME").setValue(Constants.UNKNOWN);
        entity.field("CAT_DESCRIPTION").setValue(Constants.UNKNOWN_CODE);
        insertUnknownLine(entity);
        
        entity = (BOEntityReadWrite)this.getEntities().getEntity("DIM_TERM_RELATIONSHIP");
        entity.field("REL_PK").setValue(0);
        entity.field("REL_NAME").setValue(Constants.UNKNOWN);
        entity.field("REL_NAME").setValue(Constants.UNKNOWN);
        insertUnknownLine(entity);
        
        entity = (BOEntityReadWrite)this.getEntities().getEntity("DIM_TERM_RELLINKS");
        entity.field("TRL_PK").setValue(0);
        entity.field("REL_FK").setValue(0);
        insertUnknownLine(entity);
        
        this.addTrace("Tables Initialized successfully");
    }

    @Override
    public JoyTaskStatus taskExecute() {
        return super.taskExecute(); //To change body of generated methods, choose Tools | Templates.
    }

}
