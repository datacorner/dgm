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
package com.dgm.rest;

import com.joy.mvc.actionTypes.ActionTypeREST;
import java.util.ArrayList;
import java.util.List;

/**
 * This class manages the arguments pairs into a rest call
 * @author Benoit CAYLA <benoit@famillecayla.fr>
 */
public class RESTPairMgtCommon extends ActionTypeREST {

    public RESTPairMgtCommon() {
        Pairs = new ArrayList();
    }
    
    protected class restParameterPair {
        private String Name;
        private String Value;

        public restParameterPair(String Name, String Value) {
            this.Name = Name;
            this.Value = Value;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getValue() {
            return Value;
        }

        public void setValue(String Value) {
            this.Value = Value;
        }
    }
    
    protected List<restParameterPair> Pairs;
    
    public int getPairsCount(int Index) {
        return Pairs.size();
    }
    
    public restParameterPair getPair(int Index) {
        try {
            return Pairs.get(Index);
        } catch (Exception e) { return null; }
    }

    public boolean CollectPairs(int firstParameterIndex) {
        int i = firstParameterIndex; // begins from the 3rd parameter
        boolean hasPair = true;
        
        try {
            while (hasPair) {
                String argName = this.getStrArgumentValue("P" + i++);
                String argValue = this.getStrArgumentValue("P" + i++);
                hasPair = (!argName.isEmpty() &&  !argValue.isEmpty());
                if (hasPair) {
                    Pairs.add(new restParameterPair(argName, argValue));
                } 
            }
            return true;
            
        } catch (Exception e) {
            return false;
        }
    }
}
