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
package uk.ac.leeds.ccg.projects.fire.data.dwellings;

import java.util.HashSet;
import java.util.Set;
import uk.ac.leeds.ccg.projects.fire.core.F_Strings;
import uk.ac.leeds.ccg.projects.fire.data.F_Data;

public class F_Dwellings_Integer_Record1 extends F_Dwellings_Integer_Record0 {

    private static final long serialVersionUID = 1L;

    public Integer tOCCUPANCY_TYPE;
    public Integer tWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT;
    public Integer tALARM_SYSTEM;
    public Integer tALARM_SYSTEM_TYPE;
    public Integer tALARM_REASON_FOR_POOR_OUTCOME;

    public F_Dwellings_Integer_Record1(F_Dwellings_String_Record1 r, F_Data d) throws Exception {
        super(r, d);
        tOCCUPANCY_TYPE = d.name2ids.get(d.vname2id.get(F_Strings.OCCUPANCY_TYPE)).get(r.tOCCUPANCY_TYPE);
        tWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT = d.name2ids.get(d.vname2id.get(F_Strings.WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT)).get(r.tWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT);
        tALARM_SYSTEM = d.name2ids.get(d.vname2id.get(F_Strings.ALARM_SYSTEM)).get(r.tALARM_SYSTEM);
        tALARM_SYSTEM_TYPE = d.name2ids.get(d.vname2id.get(F_Strings.ALARM_SYSTEM_TYPE)).get(r.tALARM_SYSTEM_TYPE);
        tALARM_REASON_FOR_POOR_OUTCOME = d.name2ids.get(d.vname2id.get(F_Strings.ALARM_REASON_FOR_POOR_OUTCOME)).get(r.tALARM_REASON_FOR_POOR_OUTCOME);
    }

    /**
     * @return A String representation of this.
     */
    @Override
    public String toString(F_Data data) {
        String r = toString0(data);
        r += toString1(data);
        r += ", BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT=" + getBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT(data);
        r += ", OCCUPANCY_TYPE=" + getOCCUPANCY_TYPE(data);
        r += ", OCCUPIED_NORMAL=" + getOCCUPIED_NORMAL(data);
        r += ", WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT=" + getWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT(data);
        r += ", ALARM_SYSTEM=" + getALARM_SYSTEM(data);
        r += ", ALARM_SYSTEM_TYPE=" + getALARM_SYSTEM_TYPE(data);
        r += ", ALARM_REASON_FOR_POOR_OUTCOME=" + getALARM_REASON_FOR_POOR_OUTCOME(data);
        r += toString2(data);
        return r;
    }

    @Override
    public String getCSVHeader() {
        return getCSVHeader0()
                + getCSVHeader1()
                + "BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT,"
                + "OCCUPANCY_TYPE,"
                + "OCCUPIED_NORMAL,"
                + "WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT,"
                + "ALARM_SYSTEM,"
                + "ALARM_SYSTEM_TYPE,"
                + "ALARM_REASON_FOR_POOR_OUTCOME,"
                + getCSVHeader2();
    }

    @Override
    public String toCSVString(F_Data data) {
        String r = toCSVString0(data);
        r += toCSVString1(data);
        r += "\",\"" + getBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT(data);
        r += "\",\"" + getOCCUPANCY_TYPE(data);
        r += "\",\"" + getOCCUPIED_NORMAL(data);
        r += "\",\"" + getWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT(data);
        r += "\",\"" + getALARM_SYSTEM(data);
        r += "\",\"" + getALARM_SYSTEM_TYPE(data);
        r += "\",\"" + getALARM_REASON_FOR_POOR_OUTCOME(data);
        r += toCSVString2(data);
        return r;
    }

    public String getOCCUPANCY_TYPE(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.OCCUPANCY_TYPE)).get(tOCCUPANCY_TYPE);
    }

    public String getWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT)).get(tWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT);
    }

    public String getALARM_SYSTEM(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.ALARM_SYSTEM)).get(tALARM_SYSTEM);
    }

    public String getALARM_SYSTEM_TYPE(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.ALARM_SYSTEM_TYPE)).get(tALARM_SYSTEM_TYPE);
    }

    public String getALARM_REASON_FOR_POOR_OUTCOME(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.ALARM_REASON_FOR_POOR_OUTCOME)).get(tALARM_REASON_FOR_POOR_OUTCOME);
    }

    public void initScores(
            // MULTI_SEATED_FLAG
            int varMSF, int valMSF_Yes,
            // IGNITION_TO_DISCOVERY
            int varITD, Set<Integer> svalITD,
            // DISCOVERY_TO_CALL
            int varDTC, Set<Integer> svalDTC,
            // BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION
            int varBSSCD, int valBSSCD_SOCS, Set<Integer> svalBSSCD,
            // BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION
            int varBSSMOED, int valBSSMOED_SOCS, Set<Integer> svalBSSMOED,
            // ALARM_REASON_FOR_POOR_OUTCOME
            int varARFPO, Set<Integer> svalARFPO,
            // STARTING_DELAY_DESCRIPTION
            int varSDD, int valSDD_SOCS, Set<Integer> svalSDD,
            // CAUSE_OF_FIRE
            int varCOF, Set<Integer> svalCOF,
            // ITEM_CAUSING_SPREAD
            int varICS, Set<Integer> svalICS,
            // RAPID_FIRE_GROWTH
            int varRFG, int valRFG,
            // BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION
            int varBSCD, Set<Integer> svalBSCD,
            // OTHER_PROPERTY_AFFECTED_ON_ARRIVAL
            int varOPAOA, int valOPAOA,
            // other_property_affected_close_d
            int varOPAC, int valOPAC,
            // FIRE_SIZE_ON_ARRIVAL
            int varFSOA, Set<Integer> svalFSOASuccess,
            // spread_of_fire_d
            int varSOF, Set<Integer> svalSOFSuccess,
            // RESPONSE_TIME
            int varRT, Set<Integer> svalRTSuccess, Set<Integer> svalRTFailure,
            // FATALITY_CASUALTY
            int varFC, int valFC,
            // RESCUES
            int varR, int valR,
            // EVACUATIONS
            int varE, int valE,
            // BUILDING_EVACUATION_DELAY_DESCRIPTION
            int varBEDD, int valBEDD, Set<Integer> varBEDDFailure) {
        super.initScores0(varMSF, valMSF_Yes, varITD, svalITD, varDTC,
                svalDTC, varBSSCD, valBSSCD_SOCS, svalBSSCD, varBSSMOED,
                valBSSMOED_SOCS, svalBSSMOED, varSDD, valSDD_SOCS, svalSDD,
                varCOF, svalCOF, varICS, svalICS, varRFG, valRFG, varBSCD,
                svalBSCD, varOPAOA, valOPAOA, varOPAC, valOPAC, varFSOA,
                svalFSOASuccess, varSOF, svalSOFSuccess, varRT, svalRTSuccess,
                svalRTFailure, varFC, valFC, varR, valR, varE, valE, varBEDD,
                valBEDD, varBEDDFailure);
        // ALARM_REASON_FOR_POOR_OUTCOME
        if (svalARFPO.contains(tALARM_REASON_FOR_POOR_OUTCOME)) {
            FIRE_SAFETY_FAILURE_SCORE++;
        }
    }
}
