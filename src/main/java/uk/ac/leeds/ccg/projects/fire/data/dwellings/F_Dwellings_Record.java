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
import uk.ac.leeds.ccg.projects.fire.core.F_Strings;
import uk.ac.leeds.ccg.projects.fire.data.F_Data;
import uk.ac.leeds.ccg.projects.fire.id.F_RecordID;

/**
 * All variables are IDs of values.
 *
 * @author Andy Turner
 */
public class F_Dwellings_Record extends Data_Record {

    private static final long serialVersionUID = 1L;

    public Integer tID_number;
    public Integer tFRS_NAME;
    public Integer tE_CODE;
    public Integer tFINANCIAL_YEAR;
    public Integer tMONTH_NAME;
    public Integer tWEEKDAY_WEEKEND;
    public Integer tDAY_NIGHT;
    public Integer tBUILDING_OR_PROPERTY_TYPE;
    public Integer tLATE_CALL;
    public Integer tMULTI_SEATED_FLAG;
    public Integer tIGNITION_TO_DISCOVERY;
    public Integer tDISCOVERY_TO_CALL;
    public Integer tHOW_DISCOVERED_DESCRIPTION;
    public Integer tBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION;
    public Integer tBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION;
    public Integer tBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT;
    //public Integer tOCCUPANCY_TYPE;
    public Integer tOCCUPIED_NORMAL;
    //public Integer tWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT;
    //public Integer tALARM_SYSTEM;
    //public Integer tALARM_SYSTEM_TYPE;
    //public Integer tALARM_REASON_FOR_POOR_OUTCOME;
    public Integer tACCIDENTAL_OR_DELIBERATE;
    public Integer tVEHICLES;
    public Integer tVEHICLES_CODE;
    public Integer tPERSONNEL;
    public Integer tPERSONNEL_CODE;
    public Integer tSTARTING_DELAY_DESCRIPTION;
    public Integer tACTION_NON_FRS_DESCRIPTION;
    public Integer tACTION_FRS_DESCRIPTION;
    public Integer tCAUSE_OF_FIRE;
    public Integer tIGNITION_POWER;
    public Integer tSOURCE_OF_IGNITION;
    public Integer tITEM_IGNITED;
    public Integer tITEM_CAUSING_SPREAD;
    public Integer tRAPID_FIRE_GROWTH;
    public Integer tCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION;
    public Integer tCAUSE_EXPLOSION_INVOLVED;
    public Integer tCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION;
    public Integer tCAUSE_EXPLOSION_STAGE_DESCRIPTION;
    public Integer tCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION;
    public Integer tBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION;
    public Integer tBUILDING_FLOORS_ABOVE_GROUND;
    public Integer tBUILDING_FLOORS_BELOW_GROUND;
    public Integer tBUILDING_FLOOR_ORIGIN;
    public Integer tBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION;
    public Integer tBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION;
    public Integer tFIRE_START_LOCATION;
    public Integer tFIRE_SIZE_ON_ARRIVAL;
    public Integer tOTHER_PROPERTY_AFFECTED_ON_ARRIVAL;
    public Integer tBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION;
    public Integer tBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION;
    public Integer tFIRE_SIZE_ON_ARRIVAL_DESCRIPTION;
    public Integer tother_property_affected_close_d;
    public Integer tspread_of_fire_d;
    public Integer tRESPONSE_TIME;
    public Integer tRESPONSE_TIME_CODE;
    public Integer tTIME_AT_SCENE;
    public Integer tTIME_AT_SCENE_CODE;
    public Integer tFATALITY_CASUALTY;
    public Integer tRESCUES;
    public Integer tEVACUATIONS;
    public Integer tEVACUATIONS_CODE;
    public Integer tBUILDING_EVACUATION_DELAY_DESCRIPTION;
    public Integer tBUILDING_EVACUATION_TIME_DESCRIPTION;

    public F_Dwellings_Record(F_Dwellings_Record0 r, F_Data d) throws Exception {
        super(r.id);
        tFRS_NAME = d.name2ids.get(d.vname2id.get(F_Strings.FRS_NAME)).get(r.tFRS_NAME);
        tE_CODE = d.name2ids.get(d.vname2id.get(F_Strings.E_CODE)).get(r.tE_CODE);
        tFINANCIAL_YEAR = d.name2ids.get(d.vname2id.get(F_Strings.FINANCIAL_YEAR)).get(r.tFINANCIAL_YEAR);
        tMONTH_NAME = d.name2ids.get(d.vname2id.get(F_Strings.MONTH_NAME)).get(r.tMONTH_NAME);
        tWEEKDAY_WEEKEND = d.name2ids.get(d.vname2id.get(F_Strings.WEEKDAY_WEEKEND)).get(r.tWEEKDAY_WEEKEND);
        tDAY_NIGHT = d.name2ids.get(d.vname2id.get(F_Strings.DAY_NIGHT)).get(r.tDAY_NIGHT);
        tBUILDING_OR_PROPERTY_TYPE = d.name2ids.get(d.vname2id.get(F_Strings.BUILDING_OR_PROPERTY_TYPE)).get(r.tBUILDING_OR_PROPERTY_TYPE);
        tLATE_CALL = d.name2ids.get(d.vname2id.get(F_Strings.LATE_CALL)).get(r.tLATE_CALL);
        tMULTI_SEATED_FLAG = d.name2ids.get(d.vname2id.get(F_Strings.MULTI_SEATED_FLAG)).get(r.tMULTI_SEATED_FLAG);
        tIGNITION_TO_DISCOVERY = d.name2ids.get(d.vname2id.get(F_Strings.IGNITION_TO_DISCOVERY)).get(r.tIGNITION_TO_DISCOVERY);
        tDISCOVERY_TO_CALL = d.name2ids.get(d.vname2id.get(F_Strings.DISCOVERY_TO_CALL)).get(r.tDISCOVERY_TO_CALL);
        tHOW_DISCOVERED_DESCRIPTION = d.name2ids.get(d.vname2id.get(F_Strings.HOW_DISCOVERED_DESCRIPTION)).get(r.tHOW_DISCOVERED_DESCRIPTION);
        tBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION = d.name2ids.get(d.vname2id.get(F_Strings.BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION)).get(r.tBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION);
        tBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION = d.name2ids.get(d.vname2id.get(F_Strings.BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION)).get(r.tBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION);
        tBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT = d.name2ids.get(d.vname2id.get(F_Strings.BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT)).get(r.tBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT);
        //tOCCUPANCY_TYPE = d.name2ids.get(d.vname2id.get(F_Strings.OCCUPANCY_TYPE)).get(r.tOCCUPANCY_TYPE);
        tOCCUPIED_NORMAL = d.name2ids.get(d.vname2id.get(F_Strings.OCCUPIED_NORMAL)).get(r.tOCCUPIED_NORMAL);
        //tWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT = d.name2ids.get(d.vname2id.get(F_Strings.WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT)).get(r.tWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT);
        //tALARM_SYSTEM = d.name2ids.get(d.vname2id.get(F_Strings.ALARM_SYSTEM)).get(r.tALARM_SYSTEM);
        //tALARM_SYSTEM_TYPE = d.name2ids.get(d.vname2id.get(F_Strings.ALARM_SYSTEM_TYPE)).get(r.tALARM_SYSTEM_TYPE);
        //tALARM_REASON_FOR_POOR_OUTCOME = d.name2ids.get(d.vname2id.get(F_Strings.ALARM_REASON_FOR_POOR_OUTCOME)).get(r.tALARM_REASON_FOR_POOR_OUTCOME);
        tACCIDENTAL_OR_DELIBERATE = d.name2ids.get(d.vname2id.get(F_Strings.ACCIDENTAL_OR_DELIBERATE)).get(r.tACCIDENTAL_OR_DELIBERATE);
        tVEHICLES = d.name2ids.get(d.vname2id.get(F_Strings.VEHICLES)).get(r.tVEHICLES);
        tVEHICLES_CODE = d.name2ids.get(d.vname2id.get(F_Strings.VEHICLES_CODE)).get(r.tVEHICLES_CODE);
        tPERSONNEL = d.name2ids.get(d.vname2id.get(F_Strings.PERSONNEL)).get(r.tPERSONNEL);
        tPERSONNEL_CODE = d.name2ids.get(d.vname2id.get(F_Strings.PERSONNEL_CODE)).get(r.tPERSONNEL_CODE);
        tSTARTING_DELAY_DESCRIPTION = d.name2ids.get(d.vname2id.get(F_Strings.STARTING_DELAY_DESCRIPTION)).get(r.tSTARTING_DELAY_DESCRIPTION);
        tACTION_NON_FRS_DESCRIPTION = d.name2ids.get(d.vname2id.get(F_Strings.ACTION_NON_FRS_DESCRIPTION)).get(r.tACTION_NON_FRS_DESCRIPTION);
        tACTION_FRS_DESCRIPTION = d.name2ids.get(d.vname2id.get(F_Strings.ACTION_FRS_DESCRIPTION)).get(r.tACTION_FRS_DESCRIPTION);
        tCAUSE_OF_FIRE = d.name2ids.get(d.vname2id.get(F_Strings.CAUSE_OF_FIRE)).get(r.tCAUSE_OF_FIRE);
        tIGNITION_POWER = d.name2ids.get(d.vname2id.get(F_Strings.IGNITION_POWER)).get(r.tIGNITION_POWER);
        tSOURCE_OF_IGNITION = d.name2ids.get(d.vname2id.get(F_Strings.SOURCE_OF_IGNITION)).get(r.tSOURCE_OF_IGNITION);
        tITEM_IGNITED = d.name2ids.get(d.vname2id.get(F_Strings.ITEM_IGNITED)).get(r.tITEM_IGNITED);
        tITEM_CAUSING_SPREAD = d.name2ids.get(d.vname2id.get(F_Strings.ITEM_CAUSING_SPREAD)).get(r.tITEM_CAUSING_SPREAD);
        tRAPID_FIRE_GROWTH = d.name2ids.get(d.vname2id.get(F_Strings.RAPID_FIRE_GROWTH)).get(r.tRAPID_FIRE_GROWTH);
        tCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION = d.name2ids.get(d.vname2id.get(F_Strings.CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION)).get(r.tCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION);
        tCAUSE_EXPLOSION_INVOLVED = d.name2ids.get(d.vname2id.get(F_Strings.CAUSE_EXPLOSION_INVOLVED)).get(r.tCAUSE_EXPLOSION_INVOLVED);
        tCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION = d.name2ids.get(d.vname2id.get(F_Strings.CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION)).get(r.tCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION);
        tCAUSE_EXPLOSION_STAGE_DESCRIPTION = d.name2ids.get(d.vname2id.get(F_Strings.CAUSE_EXPLOSION_STAGE_DESCRIPTION)).get(r.tCAUSE_EXPLOSION_STAGE_DESCRIPTION);
        tCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION = d.name2ids.get(d.vname2id.get(F_Strings.CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION)).get(r.tCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION);
        tBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION = d.name2ids.get(d.vname2id.get(F_Strings.BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION)).get(r.tBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION);
        tBUILDING_FLOORS_ABOVE_GROUND = d.name2ids.get(d.vname2id.get(F_Strings.BUILDING_FLOORS_ABOVE_GROUND)).get(r.tBUILDING_FLOORS_ABOVE_GROUND);
        tBUILDING_FLOORS_BELOW_GROUND = d.name2ids.get(d.vname2id.get(F_Strings.BUILDING_FLOORS_BELOW_GROUND)).get(r.tBUILDING_FLOORS_BELOW_GROUND);
        tBUILDING_FLOOR_ORIGIN = d.name2ids.get(d.vname2id.get(F_Strings.BUILDING_FLOOR_ORIGIN)).get(r.tBUILDING_FLOOR_ORIGIN);
        tBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION = d.name2ids.get(d.vname2id.get(F_Strings.BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION)).get(r.tBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION);
        tBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION = d.name2ids.get(d.vname2id.get(F_Strings.BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION)).get(r.tBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION);
        tFIRE_START_LOCATION = d.name2ids.get(d.vname2id.get(F_Strings.FIRE_START_LOCATION)).get(r.tFIRE_START_LOCATION);
        tFIRE_SIZE_ON_ARRIVAL = d.name2ids.get(d.vname2id.get(F_Strings.FIRE_SIZE_ON_ARRIVAL)).get(r.tFIRE_SIZE_ON_ARRIVAL);
        tOTHER_PROPERTY_AFFECTED_ON_ARRIVAL = d.name2ids.get(d.vname2id.get(F_Strings.OTHER_PROPERTY_AFFECTED_ON_ARRIVAL)).get(r.tOTHER_PROPERTY_AFFECTED_ON_ARRIVAL);
        tBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION = d.name2ids.get(d.vname2id.get(F_Strings.BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION)).get(r.tBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION);
        tBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION = d.name2ids.get(d.vname2id.get(F_Strings.BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION)).get(r.tBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION);
        tFIRE_SIZE_ON_ARRIVAL_DESCRIPTION = d.name2ids.get(d.vname2id.get(F_Strings.FIRE_SIZE_ON_ARRIVAL_DESCRIPTION)).get(r.tFIRE_SIZE_ON_ARRIVAL_DESCRIPTION);
        tother_property_affected_close_d = d.name2ids.get(d.vname2id.get(F_Strings.other_property_affected_close_d)).get(r.tother_property_affected_close_d);
        tspread_of_fire_d = d.name2ids.get(d.vname2id.get(F_Strings.spread_of_fire_d)).get(r.tspread_of_fire_d);
        tRESPONSE_TIME = d.name2ids.get(d.vname2id.get(F_Strings.RESPONSE_TIME)).get(r.tRESPONSE_TIME);
        tRESPONSE_TIME_CODE = d.name2ids.get(d.vname2id.get(F_Strings.RESPONSE_TIME_CODE)).get(r.tRESPONSE_TIME_CODE);
        tTIME_AT_SCENE = d.name2ids.get(d.vname2id.get(F_Strings.TIME_AT_SCENE)).get(r.tTIME_AT_SCENE);
        tTIME_AT_SCENE_CODE = d.name2ids.get(d.vname2id.get(F_Strings.TIME_AT_SCENE_CODE)).get(r.tTIME_AT_SCENE_CODE);
        tFATALITY_CASUALTY = d.name2ids.get(d.vname2id.get(F_Strings.FATALITY_CASUALTY)).get(r.tFATALITY_CASUALTY);
        tRESCUES = d.name2ids.get(d.vname2id.get(F_Strings.RESCUES)).get(r.tRESCUES);
        tEVACUATIONS = d.name2ids.get(d.vname2id.get(F_Strings.EVACUATIONS)).get(r.tEVACUATIONS);
        tEVACUATIONS_CODE = d.name2ids.get(d.vname2id.get(F_Strings.EVACUATIONS_CODE)).get(r.tEVACUATIONS_CODE);
        tBUILDING_EVACUATION_DELAY_DESCRIPTION = d.name2ids.get(d.vname2id.get(F_Strings.BUILDING_EVACUATION_DELAY_DESCRIPTION)).get(r.tBUILDING_EVACUATION_DELAY_DESCRIPTION);
        tBUILDING_EVACUATION_TIME_DESCRIPTION = d.name2ids.get(d.vname2id.get(F_Strings.BUILDING_EVACUATION_TIME_DESCRIPTION)).get(r.tBUILDING_EVACUATION_TIME_DESCRIPTION);
    }

    @Override
    public F_RecordID getId() {
        return (F_RecordID) id;
    }

    public String getACCIDENTAL_OR_DELIBERATE(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.ACCIDENTAL_OR_DELIBERATE)).get(tACCIDENTAL_OR_DELIBERATE);
    }
}
