/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgm.rest;

import com.joy.mvc.actionTypes.ActionTypeREST;
import java.util.ArrayList;
import java.util.List;

/**
 * This class manages the arguments pairs into a rest call
 * @author B. Cayla
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
