/*
 * Copyright 2021 Centre for Computational Geography, University of Leeds.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.leeds.ccg.projects.fire.data.ehs;

/**
 *
 * @author Andy Turner
 */
public class F_ARecord {
    
    public String year;
    public int allDwellings;
    public int bungalows;
    public int houses;
    public int convertedFlatsAndMaisonettes;
    public int purposeBuiltLowRiseFlats;
    public int purposeBuiltHighRiseFlats;
    
    public F_ARecord(){}
    
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(year=" + year 
                + ", allDwellings=" + allDwellings
                + ", bungalows=" + bungalows
                + ", houses=" + houses
                + ", convertedFlatsAndMaisonettes=" + convertedFlatsAndMaisonettes
                + ", purposeBuiltLowRiseFlats=" + purposeBuiltLowRiseFlats
                + ", purposeBuiltHighRiseFlats=" + purposeBuiltHighRiseFlats
                + ")";
    }
    
    public double getPercentageBungalow() {
        return getPercentageOfAllDwellings(bungalows);
    }

    public double getPercentageHouses() {
        return getPercentageOfAllDwellings(houses);
    }

    public double getPercentageBungalowConvertedFlatsAndMaisonettes() {
        return getPercentageOfAllDwellings(convertedFlatsAndMaisonettes);
    }

    public double getPercentagePurposeBuilt() {
        return getPercentageOfAllDwellings(purposeBuiltLowRiseFlats + purposeBuiltHighRiseFlats);
    }

    public double getPercentageOfAllDwellings(int count){
        return (count * 100.0d) / (double) allDwellings;
    }
}
