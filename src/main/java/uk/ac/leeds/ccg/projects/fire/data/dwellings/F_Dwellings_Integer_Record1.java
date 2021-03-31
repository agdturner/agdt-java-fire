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

    /**
     * @param varMSF MULTI_SEATED_FLAG variable ID.
     * @param valMSF_Yes MULTI_SEATED_FLAG variable value indicating
     * FIRE_SAFETY_FAILURE. The other value is regarded as a
     * FIRE_SAFETY_SUCCESS.
     * @param varITD IGNITION_TO_DISCOVERY variable ID.
     * @param svalITD Set of IGNITION_TO_DISCOVERY variable values indicating
     * FIRE_SAFETY_FAILURE.
     * @param varDTC DISCOVERY_TO_CALL variable ID.
     * @param svalDTC Set of DISCOVERY_TO_CALL variable values indicating
     * FIRE_SAFETY_FAILURE.
     * @param varBSSCD BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION
     * variable ID.
     * @param valBSSCD_SOCS BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION
     * variable value indicating FIRE_SAFETY_SUCCESS.
     * @param svalBSSCD Set of
     * BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION variable values
     * indicating FIRE_SAFETY_FAILURE.
     * @param varBSSMOED BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION
     * variable ID.
     * @param valBSSMOED_SOCS BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION
     * variable value indicating FIRE_SAFETY_SUCCESS.
     * @param svalBSSMOEDDanger Set of DISCOVERY_TO_CALL variable values
     * indicating FIRE_SAFETY_FAILURE.
     * @param varARFPO ALARM_REASON_FOR_POOR_OUTCOME variable ID.
     * @param svalARFPO Set of ALARM_REASON_FOR_POOR_OUTCOME variable values
     * indicating FIRE_SAFETY_FAILURE.
     * @param varARFPO ALARM_REASON_FOR_POOR_OUTCOME variable ID.
     * @param svalARFPO Set of ALARM_REASON_FOR_POOR_OUTCOME variable values
     * indicating FIRE_SAFETY_FAILURE.
     * @param varSDD STARTING_DELAY_DESCRIPTION variable ID.
     * @param valSDD_SOCS STARTING_DELAY_DESCRIPTION variable value indicating
     * FIRE_SAFETY_SUCCESS.
     * @param svalSDD Set of STARTING_DELAY_DESCRIPTION variable values
     * indicating FIRE_SAFETY_FAILURE fire fighting.
     * @param svalSDD2 Set of STARTING_DELAY_DESCRIPTION variable values
     * indicating increased fire danger.
     * @param varCOF CAUSE_OF_FIRE variable ID.
     * @param svalCOF Set of CAUSE_OF_FIRE variable values indicating
     * FIRE_SAFETY_FAILURE.
     * @param varICS ITEM_CAUSING_SPREAD variable ID.
     * @param svalICS Set of ITEM_CAUSING_SPREAD variable values indicating
     * FIRE_SAFETY_FAILURE.
     * @param varRFG RAPID_FIRE_GROWTH variable ID.
     * @param valRFG RAPID_FIRE_GROWTH variable value indicating
     * FIRE_SAFETY_FAILURE. The other value is regarded as a
     * FIRE_SAFETY_SUCCESS.
     * @param varBSCD BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION variable ID.
     * @param svalBSCD Set of BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION variable
     * values indicating FIRE_SAFETY_FAILURE.
     * @param varOPAOA OTHER_PROPERTY_AFFECTED_ON_ARRIVAL variable ID.
     * @param valOPAOA OTHER_PROPERTY_AFFECTED_ON_ARRIVAL variable value
     * indicating FIRE_SAFETY_FAILURE. The other value is regarded as a
     * FIRE_SAFETY_SUCCESS.
     * @param varOPAC other_property_affected_close_d variable ID.
     * @param valOPAC other_property_affected_close_d variable value indicating
     * FIRE_SAFETY_FAILURE. The other value is regarded as a
     * FIRE_SAFETY_SUCCESS.
     * @param varFSOA FIRE_SIZE_ON_ARRIVAL variable ID.
     * @param svalFSOASuccess Set of FIRE_SIZE_ON_ARRIVAL variable values
     * indicating FIRE_SAFETY_FAILURE. The other value is regarded as a
     * FIRE_SAFETY_SUCCESS.
     * @param varSOF spread_of_fire_d variable ID.
     * @param svalSOFSuccess Set of spread_of_fire_d variable values indicating
     * FIRE_SAFETY_SUCCESS.
     * @param varRT RESPONSE_TIME variable ID.
     * @param svalRTSuccess Set of RESPONSE_TIME variable values indicating
     * FIRE_SAFETY_SUCCESS.
     * @param svalRTFailure Set of RESPONSE_TIME variable values indicating
     * FIRE_SAFETY_FAILURE.
     * @param varFC FATALITY_CASUALTY variable ID.
     * @param valFC FATALITY_CASUALTY variable value indicating
     * FIRE_SAFETY_FAILURE. The other value is regarded as a
     * FIRE_SAFETY_SUCCESS.
     * @param varR RESCUES variable ID.
     * @param valR RESCUES variable value indicating FIRE_SAFETY_SUCCESS. The
     * other value is regarded as a FIRE_SAFETY_FAILURE.
     * @param varE EVACUATIONS variable ID.
     * @param valE EVACUATIONS variable value indicating FIRE_SAFETY_SUCCESS.
     * The other value is regarded as a FIRE_SAFETY_FAILURE.
     * @param varBEDD BUILDING_EVACUATION_DELAY_DESCRIPTION variable ID.
     * @param valBEDD BUILDING_EVACUATION_DELAY_DESCRIPTION variable value
     * indicating FIRE_SAFETY_SUCCESS.
     * @param svalBEDDFailure Set of BUILDING_EVACUATION_DELAY_DESCRIPTION
     * values indicating FIRE_SAFETY_FAILURE.
     */
    public void initScores(int varMSF, int valMSF_Yes, int varITD,
            Set<Integer> svalITD, int varDTC, Set<Integer> svalDTC,
            int varBSSCD, int valBSSCD_SOCS, Set<Integer> svalBSSCD,
            int varBSSMOED, int valBSSMOED_SOCS, Set<Integer> svalBSSMOEDDanger,
            Set<Integer> svalBSSMOEDManagement, Set<Integer> svalBSSMOEDConstruction,
            int varARFPO, Set<Integer> svalARFPO, int varSDD, int valSDD_SOCS,
            Set<Integer> svalSDD, Set<Integer> svalSDD2,
            int varCOF, Set<Integer> svalCOF, Set<Integer> svalCOF2,
            int varICS, Set<Integer> svalICS, Set<Integer> svalICS2,
            int varRFG, int valRFG,
            int varBSCD, Set<Integer> svalBSCD, int varOPAOA, int valOPAOA,
            int varOPAC, int valOPAC,
            int varFSOA, Set<Integer> svalFSOASuccess, Set<Integer> svalFSOAFailure,
            int varSOF, Set<Integer> svalSOFSuccess, Set<Integer> svalSOFFailure,
            int varRT, Set<Integer> svalRTSuccess, Set<Integer> svalRTFailure,
            int varFC, int valFC, int varR, int valR, int varE, int valE,
            int varBEDD, int valBEDD, Set<Integer> svalBEDDDanger, Set<Integer> svalBEDDFailureManagement,
            Set<Integer> svalBEDDFailureConstructionAndManagement) {
        super.initScores0(varMSF, valMSF_Yes, varITD, svalITD, varDTC,
                svalDTC, varBSSCD, valBSSCD_SOCS, svalBSSCD, varBSSMOED,
                valBSSMOED_SOCS, svalBSSMOEDDanger, svalBSSMOEDManagement, svalBSSMOEDConstruction,
                varSDD, valSDD_SOCS, svalSDD, svalSDD2,
                varCOF, svalCOF, svalCOF2,
                varICS, svalICS, svalICS2, varRFG, valRFG, varBSCD,
                svalBSCD, varOPAOA, valOPAOA, varOPAC, valOPAC,
                varFSOA, svalFSOASuccess, svalFSOAFailure,
                varSOF, svalSOFSuccess, svalSOFFailure,
                varRT, svalRTSuccess,
                svalRTFailure, varFC, valFC, varR, valR, varE, valE, varBEDD,
                valBEDD, svalBEDDDanger, svalBEDDFailureManagement,
                svalBEDDFailureConstructionAndManagement);
        // ALARM_REASON_FOR_POOR_OUTCOME
        if (svalARFPO.contains(tALARM_REASON_FOR_POOR_OUTCOME)) {
            //fSFScore++;
            delaysToFireFightingScore++;
            combinedScore++;
        }
    }
}
