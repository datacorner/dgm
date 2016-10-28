/*
 * Copyright (C) 2016 Benoit CAYLA (benoit@famillecayla.fr)
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
package com.dgm.form.data;

import com.joy.Joy;
import com.joy.bo.IEntity;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Benoit CAYLA (benoit@famillecayla.fr)
 */
public class LNDMetricAction extends LNDCommonAction {

    public LNDMetricAction() {
        this.LandingKeyName = "JOYFUNCKEY";
        this.LandingTableName = "LND_METRIC";
    }
    
    @Override
    public void updateSpecific(IEntity Entity) {
        Entity.field("MET_NAME").setValue(getStrArgumentValue("MET_NAME"));
        Entity.field("SCORECARD_KEY").setValue(getStrArgumentValue("SCORECARD_KEY"));
        Entity.field("SCORECARDGRP_KEY").setValue(getStrArgumentValue("SCORECARDGRP_KEY"));
        Entity.field("MET_SCORE").setValue(getStrArgumentValue("MET_SCORE"));
    }

    @Override
    public void editSpecific (ResultSet rs) { 
        try {
            loadCBOScorecard(rs.getString("SCORECARD_KEY"));
            loadCBOScorecardGroup(rs.getString("SCORECARDGRP_KEY"));
        } catch (SQLException ex) {
            Joy.log().error(ex);
        }
    }
    
    @Override
    public void addSpecific () { 
        loadCBOScorecard("");
        loadCBOScorecardGroup("");
    }
    
    private void loadCBOScorecard(String PKSelected) {
        try {
            // into the DIM table
            IEntity entity = this.getBOFactory().getEntity("DIM_SCORECARD");
            entity.addSort("SCO_NAME");
            ResultSet rs = entity.select();
            this.loadVector(rs, "SCO_FUNCKEY",  "SCO_NAME", "SCORECARD_CBO", PKSelected);
            this.getBOFactory().closeResultSet(rs);

        } catch (Exception e) {
            Joy.log().error( e);
        }
    }
    
    private void loadCBOScorecardGroup(String PKSelected) {
        try {
            // into the DIM table
            IEntity entity = this.getBOFactory().getEntity("DIM_SCORECARD_GROUP");
            entity.addSort("SCG_NAME");
            ResultSet rs = entity.select();
            this.loadVector(rs, "SCG_FUNCKEY",  "SCG_NAME", "SCORECARD_GROUP_CBO", PKSelected);
            this.getBOFactory().closeResultSet(rs);

        } catch (Exception e) {
            Joy.log().error( e);
        }
    }
}
