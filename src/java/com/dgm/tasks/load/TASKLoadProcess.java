/*
 * Load from LND_TERM to 
 * DIM_TERM : functionnal key for match OBJECT_ID
 * DIM_CATEGORY : functionnal key for match CAT_ID
 * DIM_GLOSSARY : functionnal key for match GLO_ID
 */
package com.dgm.tasks.load;

import com.dgm.tasks.load.TASKCommonLoad;
import com.joy.tasks.JoyTaskStatus;

/**
 * @author benoit CAYLA
 */
public class TASKLoadProcess extends TASKCommonLoad {

    @Override
    public JoyTaskStatus taskExecute() {
        return this.loadInternalLanding();
    }
    
}
