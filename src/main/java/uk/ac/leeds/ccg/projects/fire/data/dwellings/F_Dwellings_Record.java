/*
 * Copyright 2021 Andy Turner, CCG, University of Leeds.
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

import uk.ac.leeds.ccg.data.Data_Record;
import uk.ac.leeds.ccg.projects.fire.id.F_RecordID;

/**
 * Old
 * @author Andy Turner
 */
public class F_Dwellings_Record extends Data_Record {

    private static final long serialVersionUID = 1L;

    protected Integer tID_number;
    protected String tFRS_NAME;
    protected String tE_CODE;
    protected String tFINANCIAL_YEAR;
    protected String tMONTH_NAME;
    protected String tWEEKDAY_WEEKEND;
    protected String tDAY_NIGHT;
    protected String tproperty_type_detailed_d;
    protected String tLATE_CALL;
    protected String tMULTI_SEATED_FLAG;
    protected String tIGNITION_TO_DISCOVERY;
    protected String tDISCOVERY_TO_CALL;
    protected String tHOW_DISCOVERED_DESCRIPTION;
    protected String tBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION;
    protected String tBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION;
    protected String tBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT;
    protected String tOCCUPANCY_TYPE;
    protected String tOCCUPIED_NORMAL;
    protected String tWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT;
    protected String tALARM_SYSTEM;
    protected String tALARM_SYSTEM_TYPE;
    protected String tALARM_REASON_FOR_POOR_OUTCOME;
    protected String tACCIDENTAL_OR_DELIBERATE;
    protected String tVEHICLES;
    protected String tVEHICLES_CODE;
    protected String tPERSONNEL;
    protected String tPERSONNEL_CODE;
    protected String tSTARTING_DELAY_DESCRIPTION;
    protected String tACTION_NON_FRS_DESCRIPTION;
    protected String tACTION_FRS_DESCRIPTION;
    protected String tCAUSE_OF_FIRE;
    protected String tIGNITION_POWER;
    protected String tSOURCE_OF_IGNITION;
    protected String tITEM_IGNITED;
    protected String tITEM_CAUSING_SPREAD;
    protected String tRAPID_FIRE_GROWTH;
    protected String tCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION;
    protected String tCAUSE_EXPLOSION_INVOLVED;
    protected String tCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION;
    protected String tCAUSE_EXPLOSION_STAGE_DESCRIPTION;
    protected String tCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION;
    protected String tBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION;
    protected String tBUILDING_FLOORS_ABOVE_GROUND;
    protected String tBUILDING_FLOORS_BELOW_GROUND;
    protected String tBUILDING_FLOOR_ORIGIN;
    protected String tBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION;
    protected String tBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION;
    protected String tFIRE_START_LOCATION;
    protected String tFIRE_SIZE_ON_ARRIVAL;
    protected String tOTHER_PROPERTY_AFFECTED_ON_ARRIVAL;
    protected String tBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION;
    protected String tBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION;
    protected String tFIRE_SIZE_ON_ARRIVAL_DESCRIPTION;
    protected String tother_property_affected_close_d;
    protected String tspread_of_fire_d;
    protected String tRESPONSE_TIME;
    protected String tRESPONSE_TIME_CODE;
    protected String tTIME_AT_SCENE;
    protected String tTIME_AT_SCENE_CODE;
    protected String tFATALITY_CASUALTY;
    protected String tRESCUES;
    protected String tEVACUATIONS;
    protected String tEVACUATIONS_CODE;
    protected String tBUILDING_EVACUATION_DELAY_DESCRIPTION;
    protected String tBUILDING_EVACUATION_TIME_DESCRIPTION;

    public F_Dwellings_Record(F_RecordID i, String[] s) throws Exception {
        super(i);
        inittID_number(s[0]);
        inittFRS_NAME(s[1]);
        inittE_CODE(s[2]);
        inittFINANCIAL_YEAR(s[3]);
        inittMONTH_NAME(s[4]);
        inittWEEKDAY_WEEKEND(s[5]);
        inittDAY_NIGHT(s[6]);
        inittproperty_type_detailed_d(s[7]);
        inittLATE_CALL(s[8]);
        inittMULTI_SEATED_FLAG(s[9]);
        inittIGNITION_TO_DISCOVERY(s[10]);
        inittDISCOVERY_TO_CALL(s[11]);
        inittHOW_DISCOVERED_DESCRIPTION(s[12]);
        inittBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION(s[13]);
        inittBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION(s[14]);
        inittBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT(s[15]);
        inittOCCUPANCY_TYPE(s[16]);
        inittOCCUPIED_NORMAL(s[17]);
        inittWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT(s[18]);
        inittALARM_SYSTEM(s[19]);
        inittALARM_SYSTEM_TYPE(s[20]);
        inittALARM_REASON_FOR_POOR_OUTCOME(s[21]);
        inittACCIDENTAL_OR_DELIBERATE(s[22]);
        inittVEHICLES(s[23]);
        inittVEHICLES_CODE(s[24]);
        inittPERSONNEL(s[25]);
        inittPERSONNEL_CODE(s[26]);
        inittSTARTING_DELAY_DESCRIPTION(s[27]);
        inittACTION_NON_FRS_DESCRIPTION(s[28]);
        inittACTION_FRS_DESCRIPTION(s[29]);
        inittCAUSE_OF_FIRE(s[30]);
        inittIGNITION_POWER(s[31]);
        inittSOURCE_OF_IGNITION(s[32]);
        inittITEM_IGNITED(s[33]);
        inittITEM_CAUSING_SPREAD(s[34]);
        inittRAPID_FIRE_GROWTH(s[35]);
        inittCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION(s[36]);
        inittCAUSE_EXPLOSION_INVOLVED(s[37]);
        inittCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION(s[38]);
        inittCAUSE_EXPLOSION_STAGE_DESCRIPTION(s[39]);
        inittCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION(s[40]);
        inittBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION(s[41]);
        inittBUILDING_FLOORS_ABOVE_GROUND(s[42]);
        inittBUILDING_FLOORS_BELOW_GROUND(s[43]);
        inittBUILDING_FLOOR_ORIGIN(s[44]);
        inittBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION(s[45]);
        inittBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION(s[46]);
        inittFIRE_START_LOCATION(s[47]);
        inittFIRE_SIZE_ON_ARRIVAL(s[48]);
        inittOTHER_PROPERTY_AFFECTED_ON_ARRIVAL(s[49]);
        inittBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION(s[50]);
        inittBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION(s[51]);
        inittFIRE_SIZE_ON_ARRIVAL_DESCRIPTION(s[52]);
        inittother_property_affected_close_d(s[53]);
        inittspread_of_fire_d(s[54]);
        inittRESPONSE_TIME(s[55]);
        inittRESPONSE_TIME_CODE(s[56]);
        inittTIME_AT_SCENE(s[57]);
        inittTIME_AT_SCENE_CODE(s[58]);
        inittFATALITY_CASUALTY(s[59]);
        inittRESCUES(s[60]);
        inittEVACUATIONS(s[61]);
        inittEVACUATIONS_CODE(s[62]);
        inittBUILDING_EVACUATION_DELAY_DESCRIPTION(s[63]);
        inittBUILDING_EVACUATION_TIME_DESCRIPTION(s[64]);
    }

    /**
     * @return A String representation of this.
     */
    @Override
    public String toString() {
        //return getId().getClass().getSimpleName() + id.toString();
        //return id.toString();
        String r = "FRS_NAME=" + tFRS_NAME;
        r += ", E_CODE=" + tE_CODE;
        r += ", FINANCIAL_YEAR=" + tFINANCIAL_YEAR;
        r += ", MONTH_NAME=" + tMONTH_NAME;
        r += ", WEEKDAY_WEEKEND=" + tWEEKDAY_WEEKEND;
        r += ", DAY_NIGHT=" + tDAY_NIGHT;
        r += ", property_type_detailed_d=" + tproperty_type_detailed_d;
        r += ", LATE_CALL=" + tLATE_CALL;
        r += ", MULTI_SEATED_FLAG=" + tMULTI_SEATED_FLAG;
        r += ", IGNITION_TO_DISCOVERY=" + tIGNITION_TO_DISCOVERY;
        r += ", DISCOVERY_TO_CALL=" + tDISCOVERY_TO_CALL;
        r += ", HOW_DISCOVERED_DESCRIPTION=" + tHOW_DISCOVERED_DESCRIPTION;
        r += ", BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION=" + tBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION;
        r += ", BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION=" + tBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION;
        r += ", BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT=" + tBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT;
        r += ", OCCUPANCY_TYPE=" + tOCCUPANCY_TYPE;
        r += ", OCCUPIED_NORMAL=" + tOCCUPIED_NORMAL;
        r += ", WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT=" + tWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT;
        r += ", ALARM_SYSTEM=" + tALARM_SYSTEM;
        r += ", ALARM_SYSTEM_TYPE=" + tALARM_SYSTEM_TYPE;
        r += ", ALARM_REASON_FOR_POOR_OUTCOME=" + tALARM_REASON_FOR_POOR_OUTCOME;
        r += ", ACCIDENTAL_OR_DELIBERATE=" + tACCIDENTAL_OR_DELIBERATE;
        r += ", VEHICLES=" + tVEHICLES;
        r += ", VEHICLES_CODE=" + tVEHICLES_CODE;
        r += ", PERSONNEL=" + tPERSONNEL;
        r += ", PERSONNEL_CODE=" + tPERSONNEL_CODE;
        r += ", STARTING_DELAY_DESCRIPTION=" + tSTARTING_DELAY_DESCRIPTION;
        r += ", ACTION_NON_FRS_DESCRIPTION=" + tACTION_NON_FRS_DESCRIPTION;
        r += ", ACTION_FRS_DESCRIPTION=" + tACTION_FRS_DESCRIPTION;
        r += ", CAUSE_OF_FIRE=" + tCAUSE_OF_FIRE;
        r += ", IGNITION_POWER=" + tIGNITION_POWER;
        r += ", SOURCE_OF_IGNITION=" + tSOURCE_OF_IGNITION;
        r += ", ITEM_IGNITED=" + tITEM_IGNITED;
        r += ", ITEM_CAUSING_SPREAD=" + tITEM_CAUSING_SPREAD;
        r += ", RAPID_FIRE_GROWTH=" + tRAPID_FIRE_GROWTH;
        r += ", CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION=" + tCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION;
        r += ", CAUSE_EXPLOSION_INVOLVED=" + tCAUSE_EXPLOSION_INVOLVED;
        r += ", CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION=" + tCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION;
        r += ", CAUSE_EXPLOSION_STAGE_DESCRIPTION=" + tCAUSE_EXPLOSION_STAGE_DESCRIPTION;
        r += ", CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION=" + tCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION;
        r += ", BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION=" + tBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION;
        r += ", BUILDING_FLOORS_ABOVE_GROUND=" + tBUILDING_FLOORS_ABOVE_GROUND;
        r += ", BUILDING_FLOORS_BELOW_GROUND=" + tBUILDING_FLOORS_BELOW_GROUND;
        r += ", BUILDING_FLOOR_ORIGIN=" + tBUILDING_FLOOR_ORIGIN;
        r += ", BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION=" + tBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION;
        r += ", BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION=" + tBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION;
        r += ", FIRE_START_LOCATION=" + tFIRE_START_LOCATION;
        r += ", FIRE_SIZE_ON_ARRIVAL=" + tFIRE_SIZE_ON_ARRIVAL;
        r += ", OTHER_PROPERTY_AFFECTED_ON_ARRIVAL=" + tOTHER_PROPERTY_AFFECTED_ON_ARRIVAL;
        r += ", BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION=" + tBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION;
        r += ", BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION=" + tBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION;
        r += ", FIRE_SIZE_ON_ARRIVAL_DESCRIPTION=" + tFIRE_SIZE_ON_ARRIVAL_DESCRIPTION;
        r += ", other_property_affected_close_d=" + tother_property_affected_close_d;
        r += ", spread_of_fire_d=" + tspread_of_fire_d;
        r += ", RESPONSE_TIME=" + tRESPONSE_TIME;
        r += ", RESPONSE_TIME_CODE=" + tRESPONSE_TIME_CODE;
        r += ", TIME_AT_SCENE=" + tTIME_AT_SCENE;
        r += ", TIME_AT_SCENE_CODE=" + tTIME_AT_SCENE_CODE;
        r += ", FATALITY_CASUALTY=" + tFATALITY_CASUALTY;
        r += ", RESCUES=" + tRESCUES;
        r += ", EVACUATIONS=" + tEVACUATIONS;
        r += ", EVACUATIONS_CODE=" + tEVACUATIONS_CODE;
        r += ", BUILDING_EVACUATION_DELAY_DESCRIPTION=" + tBUILDING_EVACUATION_DELAY_DESCRIPTION;
        r += ", BUILDING_EVACUATION_TIME_DESCRIPTION=" + tBUILDING_EVACUATION_TIME_DESCRIPTION;
        return r;
    }

    @Override
    public F_RecordID getId() {
        return (F_RecordID) id;
    }

    protected final void inittID_number(String s) {
        if (!s.trim().isEmpty()) {
            tID_number = Integer.parseInt(s);
        } else {
            tID_number = null;
        }
    }

    protected final void inittFRS_NAME(String s) {
        if (!s.trim().isEmpty()) {
            tFRS_NAME = s;
        } else {
            tFRS_NAME = null;
        }
    }

    protected final void inittE_CODE(String s) {
        if (!s.trim().isEmpty()) {
            tE_CODE = s;
        } else {
            tE_CODE = null;
        }
    }

    protected final void inittFINANCIAL_YEAR(String s) {
        if (!s.trim().isEmpty()) {
            //tFINANCIAL_YEAR = Integer.parseInt(s);
            tFINANCIAL_YEAR = s;
        } else {
            tFINANCIAL_YEAR = null;
        }
    }

    protected final void inittMONTH_NAME(String s) {
        if (!s.trim().isEmpty()) {
            tMONTH_NAME = s;
        } else {
            tMONTH_NAME = null;
        }
    }

    protected final void inittWEEKDAY_WEEKEND(String s) {
        if (!s.trim().isEmpty()) {
            tWEEKDAY_WEEKEND = s;
        } else {
            tWEEKDAY_WEEKEND = null;
        }
    }

    protected final void inittDAY_NIGHT(String s) {
        if (!s.trim().isEmpty()) {
            tDAY_NIGHT = s;
        } else {
            tDAY_NIGHT = null;
        }
    }

    protected final void inittproperty_type_detailed_d(String s) {
        if (!s.trim().isEmpty()) {
            tproperty_type_detailed_d = s;
        } else {
            tproperty_type_detailed_d = null;
        }
    }

    protected final void inittLATE_CALL(String s) {
        if (!s.trim().isEmpty()) {
            tLATE_CALL = s;
        } else {
            tLATE_CALL = null;
        }
    }

    protected final void inittMULTI_SEATED_FLAG(String s) {
        if (!s.trim().isEmpty()) {
            tMULTI_SEATED_FLAG = s;
        } else {
            tMULTI_SEATED_FLAG = null;
        }
    }

    protected final void inittIGNITION_TO_DISCOVERY(String s) {
        if (!s.trim().isEmpty()) {
            tIGNITION_TO_DISCOVERY = s;
        } else {
            tIGNITION_TO_DISCOVERY = null;
        }
    }

    protected final void inittDISCOVERY_TO_CALL(String s) {
        if (!s.trim().isEmpty()) {
            tDISCOVERY_TO_CALL = s;
        } else {
            tDISCOVERY_TO_CALL = null;
        }
    }

    protected final void inittHOW_DISCOVERED_DESCRIPTION(String s) {
        if (!s.trim().isEmpty()) {
            tHOW_DISCOVERED_DESCRIPTION = s;
        } else {
            tHOW_DISCOVERED_DESCRIPTION = null;
        }
    }

    protected final void inittBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION(String s) {
        if (!s.trim().isEmpty()) {
            tBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION = s;
        } else {
            tBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION = null;
        }
    }

    protected final void inittBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION(String s) {
        if (!s.trim().isEmpty()) {
            tBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION = s;
        } else {
            tBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION = null;
        }
    }

    protected final void inittBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT(String s) {
        if (!s.trim().isEmpty()) {
            tBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT = s;
        } else {
            tBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT = null;
        }
    }

    protected final void inittOCCUPANCY_TYPE(String s) {
        if (!s.trim().isEmpty()) {
            tOCCUPANCY_TYPE = s;
        } else {
            tOCCUPANCY_TYPE = null;
        }
    }

    protected final void inittOCCUPIED_NORMAL(String s) {
        if (!s.trim().isEmpty()) {
            tOCCUPIED_NORMAL = s;
        } else {
            tOCCUPIED_NORMAL = null;
        }
    }

    protected final void inittWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT(String s) {
        if (!s.trim().isEmpty()) {
            tWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT = s;
        } else {
            tWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT = null;
        }
    }

    protected final void inittALARM_SYSTEM(String s) {
        if (!s.trim().isEmpty()) {
            tALARM_SYSTEM = s;
        } else {
            tALARM_SYSTEM = null;
        }
    }

    protected final void inittALARM_SYSTEM_TYPE(String s) {
        if (!s.trim().isEmpty()) {
            tALARM_SYSTEM_TYPE = s;
        } else {
            tALARM_SYSTEM_TYPE = null;
        }
    }

    protected final void inittALARM_REASON_FOR_POOR_OUTCOME(String s) {
        if (!s.trim().isEmpty()) {
            tALARM_REASON_FOR_POOR_OUTCOME = s;
        } else {
            tALARM_REASON_FOR_POOR_OUTCOME = null;
        }
    }

    protected final void inittACCIDENTAL_OR_DELIBERATE(String s) {
        if (!s.trim().isEmpty()) {
            tACCIDENTAL_OR_DELIBERATE = s;
        } else {
            tACCIDENTAL_OR_DELIBERATE = null;
        }
    }

    protected final void inittVEHICLES(String s) {
        if (!s.trim().isEmpty()) {
            tVEHICLES = s;
        } else {
            tVEHICLES = null;
        }
    }

    protected final void inittVEHICLES_CODE(String s) {
        if (!s.trim().isEmpty()) {
            tVEHICLES_CODE = s;
        } else {
            tVEHICLES_CODE = null;
        }
    }

    protected final void inittPERSONNEL(String s) {
        if (!s.trim().isEmpty()) {
            tPERSONNEL = s;
        } else {
            tPERSONNEL = null;
        }
    }

    protected final void inittPERSONNEL_CODE(String s) {
        if (!s.trim().isEmpty()) {
            tPERSONNEL_CODE = s;
        } else {
            tPERSONNEL_CODE = null;
        }
    }

    protected final void inittSTARTING_DELAY_DESCRIPTION(String s) {
        if (!s.trim().isEmpty()) {
            tSTARTING_DELAY_DESCRIPTION = s;
        } else {
            tSTARTING_DELAY_DESCRIPTION = null;
        }
    }

    protected final void inittACTION_NON_FRS_DESCRIPTION(String s) {
        if (!s.trim().isEmpty()) {
            tACTION_NON_FRS_DESCRIPTION = s;
        } else {
            tACTION_NON_FRS_DESCRIPTION = null;
        }
    }

    protected final void inittACTION_FRS_DESCRIPTION(String s) {
        if (!s.trim().isEmpty()) {
            tACTION_FRS_DESCRIPTION = s;
        } else {
            tACTION_FRS_DESCRIPTION = null;
        }
    }

    protected final void inittCAUSE_OF_FIRE(String s) {
        if (!s.trim().isEmpty()) {
            tCAUSE_OF_FIRE = s;
        } else {
            tCAUSE_OF_FIRE = null;
        }
    }

    protected final void inittIGNITION_POWER(String s) {
        if (!s.trim().isEmpty()) {
            tIGNITION_POWER = s;
        } else {
            tIGNITION_POWER = null;
        }
    }

    protected final void inittSOURCE_OF_IGNITION(String s) {
        if (!s.trim().isEmpty()) {
            tSOURCE_OF_IGNITION = s;
        } else {
            tSOURCE_OF_IGNITION = null;
        }
    }

    protected final void inittITEM_IGNITED(String s) {
        if (!s.trim().isEmpty()) {
            tITEM_IGNITED = s;
        } else {
            tITEM_IGNITED = null;
        }
    }

    protected final void inittITEM_CAUSING_SPREAD(String s) {
        if (!s.trim().isEmpty()) {
            tITEM_CAUSING_SPREAD = s;
        } else {
            tITEM_CAUSING_SPREAD = null;
        }
    }

    protected final void inittRAPID_FIRE_GROWTH(String s) {
        if (!s.trim().isEmpty()) {
            tRAPID_FIRE_GROWTH = s;
        } else {
            tRAPID_FIRE_GROWTH = null;
        }
    }

    protected final void inittCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION(String s) {
        if (!s.trim().isEmpty()) {
            tCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION = s;
        } else {
            tCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION = null;
        }
    }

    protected final void inittCAUSE_EXPLOSION_INVOLVED(String s) {
        if (!s.trim().isEmpty()) {
            tCAUSE_EXPLOSION_INVOLVED = s;
        } else {
            tCAUSE_EXPLOSION_INVOLVED = null;
        }
    }

    protected final void inittCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION(String s) {
        if (!s.trim().isEmpty()) {
            tCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION = s;
        } else {
            tCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION = null;
        }
    }

    protected final void inittCAUSE_EXPLOSION_STAGE_DESCRIPTION(String s) {
        if (!s.trim().isEmpty()) {
            tCAUSE_EXPLOSION_STAGE_DESCRIPTION = s;
        } else {
            tCAUSE_EXPLOSION_STAGE_DESCRIPTION = null;
        }
    }

    protected final void inittCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION(String s) {
        if (!s.trim().isEmpty()) {
            tCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION = s;
        } else {
            tCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION = null;
        }
    }

    protected final void inittBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION(String s) {
        if (!s.trim().isEmpty()) {
            tBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION = s;
        } else {
            tBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION = null;
        }
    }

    protected final void inittBUILDING_FLOORS_ABOVE_GROUND(String s) {
        if (!s.trim().isEmpty()) {
            tBUILDING_FLOORS_ABOVE_GROUND = s;
        } else {
            tBUILDING_FLOORS_ABOVE_GROUND = null;
        }
    }

    protected final void inittBUILDING_FLOORS_BELOW_GROUND(String s) {
        if (!s.trim().isEmpty()) {
            tBUILDING_FLOORS_BELOW_GROUND = s;
        } else {
            tBUILDING_FLOORS_BELOW_GROUND = null;
        }
    }

    protected final void inittBUILDING_FLOOR_ORIGIN(String s) {
        if (!s.trim().isEmpty()) {
            tBUILDING_FLOOR_ORIGIN = s;
        } else {
            tBUILDING_FLOOR_ORIGIN = null;
        }
    }

    protected final void inittBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION(String s) {
        if (!s.trim().isEmpty()) {
            tBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION = s;
        } else {
            tBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION = null;
        }
    }

    protected final void inittBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION(String s) {
        if (!s.trim().isEmpty()) {
            tBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION = s;
        } else {
            tBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION = null;
        }
    }

    protected final void inittFIRE_START_LOCATION(String s) {
        if (!s.trim().isEmpty()) {
            tFIRE_START_LOCATION = s;
        } else {
            tFIRE_START_LOCATION = null;
        }
    }

    protected final void inittFIRE_SIZE_ON_ARRIVAL(String s) {
        if (!s.trim().isEmpty()) {
            tFIRE_SIZE_ON_ARRIVAL = s;
        } else {
            tFIRE_SIZE_ON_ARRIVAL = null;
        }
    }

    protected final void inittOTHER_PROPERTY_AFFECTED_ON_ARRIVAL(String s) {
        if (!s.trim().isEmpty()) {
            tOTHER_PROPERTY_AFFECTED_ON_ARRIVAL = s;
        } else {
            tOTHER_PROPERTY_AFFECTED_ON_ARRIVAL = null;
        }
    }

    protected final void inittBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION(String s) {
        if (!s.trim().isEmpty()) {
            tBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION = s;
        } else {
            tBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION = null;
        }
    }

    protected final void inittBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION(String s) {
        if (!s.trim().isEmpty()) {
            tBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION = s;
        } else {
            tBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION = null;
        }
    }

    protected final void inittFIRE_SIZE_ON_ARRIVAL_DESCRIPTION(String s) {
        if (!s.trim().isEmpty()) {
            tFIRE_SIZE_ON_ARRIVAL_DESCRIPTION = s;
        } else {
            tFIRE_SIZE_ON_ARRIVAL_DESCRIPTION = null;
        }
    }

    protected final void inittother_property_affected_close_d(String s) {
        if (!s.trim().isEmpty()) {
            tother_property_affected_close_d = s;
        } else {
            tother_property_affected_close_d = null;
        }
    }

    protected final void inittspread_of_fire_d(String s) {
        if (!s.trim().isEmpty()) {
            tspread_of_fire_d = s;
        } else {
            tspread_of_fire_d = null;
        }
    }

    protected final void inittRESPONSE_TIME(String s) {
        if (!s.trim().isEmpty()) {
            tRESPONSE_TIME = s;
        } else {
            tRESPONSE_TIME = null;
        }
    }

    protected final void inittRESPONSE_TIME_CODE(String s) {
        if (!s.trim().isEmpty()) {
            tRESPONSE_TIME_CODE = s;
        } else {
            tRESPONSE_TIME_CODE = null;
        }
    }

    protected final void inittTIME_AT_SCENE(String s) {
        if (!s.trim().isEmpty()) {
            tTIME_AT_SCENE = s;
        } else {
            tTIME_AT_SCENE = null;
        }
    }

    protected final void inittTIME_AT_SCENE_CODE(String s) {
        if (!s.trim().isEmpty()) {
            tTIME_AT_SCENE_CODE = s;
        } else {
            tTIME_AT_SCENE_CODE = null;
        }
    }

    protected final void inittFATALITY_CASUALTY(String s) {
        if (!s.trim().isEmpty()) {
            tFATALITY_CASUALTY = s;
        } else {
            tFATALITY_CASUALTY = null;
        }
    }

    protected final void inittRESCUES(String s) {
        if (!s.trim().isEmpty()) {
            tRESCUES = s;
        } else {
            tRESCUES = null;
        }
    }

    protected final void inittEVACUATIONS(String s) {
        if (!s.trim().isEmpty()) {
            tEVACUATIONS = s;
        } else {
            tEVACUATIONS = null;
        }
    }

    protected final void inittEVACUATIONS_CODE(String s) {
        if (!s.trim().isEmpty()) {
            tEVACUATIONS_CODE = s;
        } else {
            tEVACUATIONS_CODE = null;
        }
    }

    protected final void inittBUILDING_EVACUATION_DELAY_DESCRIPTION(String s) {
        if (!s.trim().isEmpty()) {
            tBUILDING_EVACUATION_DELAY_DESCRIPTION = s;
        } else {
            tBUILDING_EVACUATION_DELAY_DESCRIPTION = null;
        }
    }

    protected final void inittBUILDING_EVACUATION_TIME_DESCRIPTION(String s) {
        if (!s.trim().isEmpty()) {
            tBUILDING_EVACUATION_TIME_DESCRIPTION = s;
        } else {
            tBUILDING_EVACUATION_TIME_DESCRIPTION = null;
        }
    }

    public Integer gettID_number() {
        return tID_number;
    }

    public String gettFRS_NAME() {
        return tFRS_NAME;
    }

    public String gettE_CODE() {
        return tE_CODE;
    }

    public String gettFINANCIAL_YEAR() {
        return tFINANCIAL_YEAR;
    }

    public String gettMONTH_NAME() {
        return tMONTH_NAME;
    }

    public String gettWEEKDAY_WEEKEND() {
        return tWEEKDAY_WEEKEND;
    }

    public String gettDAY_NIGHT() {
        return tDAY_NIGHT;
    }

    public String gettproperty_type_detailed_d() {
        return tproperty_type_detailed_d;
    }

    public String gettLATE_CALL() {
        return tLATE_CALL;
    }

    public String gettMULTI_SEATED_FLAG() {
        return tMULTI_SEATED_FLAG;
    }

    public String gettIGNITION_TO_DISCOVERY() {
        return tIGNITION_TO_DISCOVERY;
    }

    public String gettDISCOVERY_TO_CALL() {
        return tDISCOVERY_TO_CALL;
    }

    public String gettHOW_DISCOVERED_DESCRIPTION() {
        return tHOW_DISCOVERED_DESCRIPTION;
    }

    public String gettBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION() {
        return tBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION;
    }

    public String gettBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION() {
        return tBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION;
    }

    public String gettBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT() {
        return tBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT;
    }

    public String gettOCCUPANCY_TYPE() {
        return tOCCUPANCY_TYPE;
    }

    public String gettOCCUPIED_NORMAL() {
        return tOCCUPIED_NORMAL;
    }

    public String gettWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT() {
        return tWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT;
    }

    public String gettALARM_SYSTEM() {
        return tALARM_SYSTEM;
    }

    public String gettALARM_SYSTEM_TYPE() {
        return tALARM_SYSTEM_TYPE;
    }

    public String gettALARM_REASON_FOR_POOR_OUTCOME() {
        return tALARM_REASON_FOR_POOR_OUTCOME;
    }

    public String gettACCIDENTAL_OR_DELIBERATE() {
        return tACCIDENTAL_OR_DELIBERATE;
    }

    public String gettVEHICLES() {
        return tVEHICLES;
    }

    public String gettVEHICLES_CODE() {
        return tVEHICLES_CODE;
    }

    public String gettPERSONNEL() {
        return tPERSONNEL;
    }

    public String gettPERSONNEL_CODE() {
        return tPERSONNEL_CODE;
    }

    public String gettSTARTING_DELAY_DESCRIPTION() {
        return tSTARTING_DELAY_DESCRIPTION;
    }

    public String gettACTION_NON_FRS_DESCRIPTION() {
        return tACTION_NON_FRS_DESCRIPTION;
    }

    public String gettACTION_FRS_DESCRIPTION() {
        return tACTION_FRS_DESCRIPTION;
    }

    public String gettCAUSE_OF_FIRE() {
        return tCAUSE_OF_FIRE;
    }

    public String gettIGNITION_POWER() {
        return tIGNITION_POWER;
    }

    public String gettSOURCE_OF_IGNITION() {
        return tSOURCE_OF_IGNITION;
    }

    public String gettITEM_IGNITED() {
        return tITEM_IGNITED;
    }

    public String gettITEM_CAUSING_SPREAD() {
        return tITEM_CAUSING_SPREAD;
    }

    public String gettRAPID_FIRE_GROWTH() {
        return tRAPID_FIRE_GROWTH;
    }

    public String gettCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION() {
        return tCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION;
    }

    public String gettCAUSE_EXPLOSION_INVOLVED() {
        return tCAUSE_EXPLOSION_INVOLVED;
    }

    public String gettCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION() {
        return tCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION;
    }

    public String gettCAUSE_EXPLOSION_STAGE_DESCRIPTION() {
        return tCAUSE_EXPLOSION_STAGE_DESCRIPTION;
    }

    public String gettCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION() {
        return tCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION;
    }

    public String gettBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION() {
        return tBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION;
    }

    public String gettBUILDING_FLOORS_ABOVE_GROUND() {
        return tBUILDING_FLOORS_ABOVE_GROUND;
    }

    public String gettBUILDING_FLOORS_BELOW_GROUND() {
        return tBUILDING_FLOORS_BELOW_GROUND;
    }

    public String gettBUILDING_FLOOR_ORIGIN() {
        return tBUILDING_FLOOR_ORIGIN;
    }

    public String gettBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION() {
        return tBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION;
    }

    public String gettBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION() {
        return tBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION;
    }

    public String gettFIRE_START_LOCATION() {
        return tFIRE_START_LOCATION;
    }

    public String gettFIRE_SIZE_ON_ARRIVAL() {
        return tFIRE_SIZE_ON_ARRIVAL;
    }

    public String gettOTHER_PROPERTY_AFFECTED_ON_ARRIVAL() {
        return tOTHER_PROPERTY_AFFECTED_ON_ARRIVAL;
    }

    public String gettBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION() {
        return tBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION;
    }

    public String gettBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION() {
        return tBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION;
    }

    public String gettFIRE_SIZE_ON_ARRIVAL_DESCRIPTION() {
        return tFIRE_SIZE_ON_ARRIVAL_DESCRIPTION;
    }

    public String gettother_property_affected_close_d() {
        return tother_property_affected_close_d;
    }

    public String gettspread_of_fire_d() {
        return tspread_of_fire_d;
    }

    public String gettRESPONSE_TIME() {
        return tRESPONSE_TIME;
    }

    public String gettRESPONSE_TIME_CODE() {
        return tRESPONSE_TIME_CODE;
    }

    public String gettTIME_AT_SCENE() {
        return tTIME_AT_SCENE;
    }

    public String gettTIME_AT_SCENE_CODE() {
        return tTIME_AT_SCENE_CODE;
    }

    public String gettFATALITY_CASUALTY() {
        return tFATALITY_CASUALTY;
    }

    public String gettRESCUES() {
        return tRESCUES;
    }

    public String gettEVACUATIONS() {
        return tEVACUATIONS;
    }

    public String gettEVACUATIONS_CODE() {
        return tEVACUATIONS_CODE;
    }

    public String gettBUILDING_EVACUATION_DELAY_DESCRIPTION() {
        return tBUILDING_EVACUATION_DELAY_DESCRIPTION;
    }

    public String gettBUILDING_EVACUATION_TIME_DESCRIPTION() {
        return tBUILDING_EVACUATION_TIME_DESCRIPTION;
    }
}
