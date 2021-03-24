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

import java.io.IOException;
import uk.ac.leeds.ccg.data.Data_Record;
import uk.ac.leeds.ccg.projects.fire.id.F_RecordID;

/**
 * Most variables are stored as Strings. The variables are coded from the Fire
 * Incident Reporting System: https://www.irs.fire.gov.uk/irshelp/default.htm
 *
 * @author Andy Turner
 */
public abstract class F_Dwellings_String_Record0 extends Data_Record {

    private static final long serialVersionUID = 1L;

    protected Integer tID_number;
    protected String tFRS_NAME;
    protected String tE_CODE;
    protected String tFINANCIAL_YEAR;
    protected String tMONTH_NAME;
    protected String tWEEKDAY_WEEKEND;
    protected String tDAY_NIGHT;
    protected String tBUILDING_OR_PROPERTY_TYPE;
    protected String tLATE_CALL;
    protected String tMULTI_SEATED_FLAG;
    protected String tIGNITION_TO_DISCOVERY;
    protected String tDISCOVERY_TO_CALL;
    protected String tHOW_DISCOVERED_DESCRIPTION;
    protected String tBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION;
    protected String tBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION;
    protected String tBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT;
    protected String tOCCUPIED_NORMAL;
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

    public F_Dwellings_String_Record0(F_RecordID i) {
        super(i);
    }

    protected final int init0(String[] s) throws IOException {
        int n = 0;
        check(s[n]);
        tID_number = Integer.parseInt(s[n]);
        n++;
        check(s[n]);
        tFRS_NAME = s[n];
        n++;
        check(s[n]);
        tE_CODE = s[n];
        n++;
        check(s[n]);
        tFINANCIAL_YEAR = s[n];
        n++;
        check(s[n]);
        tMONTH_NAME = s[n];
        n++;
        check(s[n]);
        tWEEKDAY_WEEKEND = s[n];
        n++;
        check(s[n]);
        tDAY_NIGHT = s[n];
        n++;
        check(s[n]);
        tBUILDING_OR_PROPERTY_TYPE = s[n];
        n++;
        check(s[n]);
        tLATE_CALL = s[n];
        n++;
        check(s[n]);
        tMULTI_SEATED_FLAG = s[n];
        n++;
        check(s[n]);
        tIGNITION_TO_DISCOVERY = s[n];
        n++;
        check(s[n]);
        tDISCOVERY_TO_CALL = s[n];
        n++;
        check(s[n]);
        tHOW_DISCOVERED_DESCRIPTION = s[n];
        return n;
    }

    protected final int init1(String[] s, int n) throws IOException {
        n++;
        check(s[n]);
        tBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION = s[n];
        n++;
        check(s[n]);
        tBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION = s[n];
        return n;
    }

    protected final void init2(String[] s, int n) throws IOException {
        n++;
        check(s[n]);
        tACCIDENTAL_OR_DELIBERATE = s[n];
        n++;
        check(s[n]);
        tVEHICLES = s[n];
        n++;
        check(s[n]);
        tVEHICLES_CODE = s[n];
        n++;
        check(s[n]);
        tPERSONNEL = s[n];
        n++;
        check(s[n]);
        tPERSONNEL_CODE = s[n];
        n++;
        check(s[n]);
        tSTARTING_DELAY_DESCRIPTION = s[n];
        n++;
        check(s[n]);
        tACTION_NON_FRS_DESCRIPTION = s[n];
        n++;
        check(s[n]);
        tACTION_FRS_DESCRIPTION = s[n];
        n++;
        check(s[n]);
        tCAUSE_OF_FIRE = s[n];
        n++;
        check(s[n]);
        tIGNITION_POWER = s[n];
        n++;
        check(s[n]);
        tSOURCE_OF_IGNITION = s[n];
        n++;
        check(s[n]);
        tITEM_IGNITED = s[n];
        n++;
        check(s[n]);
        tITEM_CAUSING_SPREAD = s[n];
        n++;
        check(s[n]);
        tRAPID_FIRE_GROWTH = s[n];
        n++;
        check(s[n]);
        tCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION = s[n];
        n++;
        check(s[n]);
        tCAUSE_EXPLOSION_INVOLVED = s[n];
        n++;
        check(s[n]);
        tCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION = s[n];
        n++;
        check(s[n]);
        tCAUSE_EXPLOSION_STAGE_DESCRIPTION = s[n];
        n++;
        check(s[n]);
        tCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION = s[n];
        n++;
        check(s[n]);
        tBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION = s[n];
        n++;
        check(s[n]);
        tBUILDING_FLOORS_ABOVE_GROUND = s[n];
        n++;
        check(s[n]);
        tBUILDING_FLOORS_BELOW_GROUND = s[n];
        n++;
        check(s[n]);
        tBUILDING_FLOOR_ORIGIN = s[n];
        n++;
        check(s[n]);
        tBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION = s[n];
        n++;
        check(s[n]);
        tBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION = s[n];
        n++;
        check(s[n]);
        tFIRE_START_LOCATION = s[n];
        n++;
        check(s[n]);
        tFIRE_SIZE_ON_ARRIVAL = s[n];
        n++;
        check(s[n]);
        tOTHER_PROPERTY_AFFECTED_ON_ARRIVAL = s[n];
        n++;
        check(s[n]);
        tBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION = s[n];
        n++;
        check(s[n]);
        tBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION = s[n];
        n++;
        check(s[n]);
        tFIRE_SIZE_ON_ARRIVAL_DESCRIPTION = s[n];
        n++;
        check(s[n]);
        tother_property_affected_close_d = s[n];
        n++;
        check(s[n]);
        tspread_of_fire_d = s[n];
        n++;
        check(s[n]);
        tRESPONSE_TIME = s[n];
        n++;
        check(s[n]);
        tRESPONSE_TIME_CODE = s[n];
        n++;
        check(s[n]);
        tTIME_AT_SCENE = s[n];
        n++;
        check(s[n]);
        tTIME_AT_SCENE_CODE = s[n];
        n++;
        check(s[n]);
        tFATALITY_CASUALTY = s[n];
        n++;
        check(s[n]);
        tRESCUES = s[n];
        n++;
        check(s[n]);
        tEVACUATIONS = s[n];
        n++;
        check(s[n]);
        tEVACUATIONS_CODE = s[n];
        n++;
        check(s[n]);
        tBUILDING_EVACUATION_DELAY_DESCRIPTION = s[n];
        n++;
        check(s[n]);
        tBUILDING_EVACUATION_TIME_DESCRIPTION = s[n];
    }

    protected String toString0() {
        String r = "FRS_NAME=" + tFRS_NAME;
        r += ", E_CODE=" + tE_CODE;
        r += ", FINANCIAL_YEAR=" + tFINANCIAL_YEAR;
        r += ", MONTH_NAME=" + tMONTH_NAME;
        r += ", WEEKDAY_WEEKEND=" + tWEEKDAY_WEEKEND;
        r += ", DAY_NIGHT=" + tDAY_NIGHT;
        r += ", BUILDING_OR_PROPERTY_TYPE=" + tBUILDING_OR_PROPERTY_TYPE;
        r += ", LATE_CALL=" + tLATE_CALL;
        r += ", MULTI_SEATED_FLAG=" + tMULTI_SEATED_FLAG;
        r += ", IGNITION_TO_DISCOVERY=" + tIGNITION_TO_DISCOVERY;
        r += ", DISCOVERY_TO_CALL=" + tDISCOVERY_TO_CALL;
        r += ", HOW_DISCOVERED_DESCRIPTION=" + tHOW_DISCOVERED_DESCRIPTION;
        return r;
    }

    protected String toString1() {
        String r = ", BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION=" + tBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION;
        r += ", BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION=" + tBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION;
        return r;
    }

    protected String toString2() {
        String r = ", ACCIDENTAL_OR_DELIBERATE=" + tACCIDENTAL_OR_DELIBERATE;
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

    protected final void check(String s) throws IOException {
        if (s.isBlank()) {
            throw new IOException("Blank value!");
        }
    }

    public final Integer gettID_number() {
        return tID_number;
    }

    public final String gettFRS_NAME() {
        return tFRS_NAME;
    }

    public final String gettE_CODE() {
        return tE_CODE;
    }

    public final String gettFINANCIAL_YEAR() {
        return tFINANCIAL_YEAR;
    }

    public final String gettMONTH_NAME() {
        return tMONTH_NAME;
    }

    public final String gettWEEKDAY_WEEKEND() {
        return tWEEKDAY_WEEKEND;
    }

    public final String gettDAY_NIGHT() {
        return tDAY_NIGHT;
    }

    public final String gettBUILDING_OR_PROPERTY_TYPE() {
        return tBUILDING_OR_PROPERTY_TYPE;
    }

    public final String gettLATE_CALL() {
        return tLATE_CALL;
    }

    public final String gettMULTI_SEATED_FLAG() {
        return tMULTI_SEATED_FLAG;
    }

    public final String gettIGNITION_TO_DISCOVERY() {
        return tIGNITION_TO_DISCOVERY;
    }

    public final String gettDISCOVERY_TO_CALL() {
        return tDISCOVERY_TO_CALL;
    }

    public final String gettHOW_DISCOVERED_DESCRIPTION() {
        return tHOW_DISCOVERED_DESCRIPTION;
    }

    public final String gettBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION() {
        return tBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION;
    }

    public final String gettBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION() {
        return tBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION;
    }

    public final String gettBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT() {
        return tBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT;
    }

    public final String gettOCCUPIED_NORMAL() {
        return tOCCUPIED_NORMAL;
    }

    public final String gettACCIDENTAL_OR_DELIBERATE() {
        return tACCIDENTAL_OR_DELIBERATE;
    }

    public final String gettVEHICLES() {
        return tVEHICLES;
    }

    public final String gettVEHICLES_CODE() {
        return tVEHICLES_CODE;
    }

    public final String gettPERSONNEL() {
        return tPERSONNEL;
    }

    public final String gettPERSONNEL_CODE() {
        return tPERSONNEL_CODE;
    }

    public final String gettSTARTING_DELAY_DESCRIPTION() {
        return tSTARTING_DELAY_DESCRIPTION;
    }

    public final String gettACTION_NON_FRS_DESCRIPTION() {
        return tACTION_NON_FRS_DESCRIPTION;
    }

    public final String gettACTION_FRS_DESCRIPTION() {
        return tACTION_FRS_DESCRIPTION;
    }

    public final String gettCAUSE_OF_FIRE() {
        return tCAUSE_OF_FIRE;
    }

    public final String gettIGNITION_POWER() {
        return tIGNITION_POWER;
    }

    public final String gettSOURCE_OF_IGNITION() {
        return tSOURCE_OF_IGNITION;
    }

    public final String gettITEM_IGNITED() {
        return tITEM_IGNITED;
    }

    public final String gettITEM_CAUSING_SPREAD() {
        return tITEM_CAUSING_SPREAD;
    }

    public final String gettRAPID_FIRE_GROWTH() {
        return tRAPID_FIRE_GROWTH;
    }

    public final String gettCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION() {
        return tCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION;
    }

    public final String gettCAUSE_EXPLOSION_INVOLVED() {
        return tCAUSE_EXPLOSION_INVOLVED;
    }

    public final String gettCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION() {
        return tCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION;
    }

    public final String gettCAUSE_EXPLOSION_STAGE_DESCRIPTION() {
        return tCAUSE_EXPLOSION_STAGE_DESCRIPTION;
    }

    public final String gettCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION() {
        return tCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION;
    }

    public final String gettBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION() {
        return tBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION;
    }

    public final String gettBUILDING_FLOORS_ABOVE_GROUND() {
        return tBUILDING_FLOORS_ABOVE_GROUND;
    }

    public final String gettBUILDING_FLOORS_BELOW_GROUND() {
        return tBUILDING_FLOORS_BELOW_GROUND;
    }

    public final String gettBUILDING_FLOOR_ORIGIN() {
        return tBUILDING_FLOOR_ORIGIN;
    }

    public final String gettBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION() {
        return tBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION;
    }

    public final String gettBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION() {
        return tBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION;
    }

    public final String gettFIRE_START_LOCATION() {
        return tFIRE_START_LOCATION;
    }

    public final String gettFIRE_SIZE_ON_ARRIVAL() {
        return tFIRE_SIZE_ON_ARRIVAL;
    }

    public final String gettOTHER_PROPERTY_AFFECTED_ON_ARRIVAL() {
        return tOTHER_PROPERTY_AFFECTED_ON_ARRIVAL;
    }

    public final String gettBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION() {
        return tBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION;
    }

    public final String gettBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION() {
        return tBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION;
    }

    public final String gettFIRE_SIZE_ON_ARRIVAL_DESCRIPTION() {
        return tFIRE_SIZE_ON_ARRIVAL_DESCRIPTION;
    }

    public final String gettother_property_affected_close_d() {
        return tother_property_affected_close_d;
    }

    public final String gettspread_of_fire_d() {
        return tspread_of_fire_d;
    }

    public final String gettRESPONSE_TIME() {
        return tRESPONSE_TIME;
    }

    public final String gettRESPONSE_TIME_CODE() {
        return tRESPONSE_TIME_CODE;
    }

    public final String gettTIME_AT_SCENE() {
        return tTIME_AT_SCENE;
    }

    public final String gettTIME_AT_SCENE_CODE() {
        return tTIME_AT_SCENE_CODE;
    }

    public final String gettFATALITY_CASUALTY() {
        return tFATALITY_CASUALTY;
    }

    public final String gettRESCUES() {
        return tRESCUES;
    }

    public final String gettEVACUATIONS() {
        return tEVACUATIONS;
    }

    public final String gettEVACUATIONS_CODE() {
        return tEVACUATIONS_CODE;
    }

    public final String gettBUILDING_EVACUATION_DELAY_DESCRIPTION() {
        return tBUILDING_EVACUATION_DELAY_DESCRIPTION;
    }

    public final String gettBUILDING_EVACUATION_TIME_DESCRIPTION() {
        return tBUILDING_EVACUATION_TIME_DESCRIPTION;
    }
}
