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
package uk.ac.leeds.ccg.projects.fire.core;

import uk.ac.leeds.ccg.generic.core.Generic_Strings;

/**
 * F_Strings
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class F_Strings extends Generic_Strings {

    private static final long serialVersionUID = 1L;

    public static final String s_Fire = "Fire";
    public static final String s_Subsets = "Subsets";
    // Variable names
    public static final String TOTAL = "TOTAL";
    public static final String FRS_NAME = "FRS_NAME";
    public static final String E_CODE = "E_CODE";
    public static final String FINANCIAL_YEAR = "FINANCIAL_YEAR";
    public static final String MONTH_NAME = "MONTH_NAME";
    public static final String WEEKDAY_WEEKEND = "WEEKDAY_WEEKEND";
    public static final String DAY_NIGHT = "DAY_NIGHT";
    public static final String LATE_CALL = "LATE_CALL";
    public static final String BUILDING_OR_PROPERTY_TYPE = "BUILDING_OR_PROPERTY_TYPE";
    public static final String MULTI_SEATED_FLAG = "MULTI_SEATED_FLAG";
    public static final String IGNITION_TO_DISCOVERY = "IGNITION_TO_DISCOVERY";
    public static final String DISCOVERY_TO_CALL = "DISCOVERY_TO_CALL";
    public static final String HOW_DISCOVERED_DESCRIPTION = "HOW_DISCOVERED_DESCRIPTION";
    public static final String BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION = "BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION";
    public static final String BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION = "BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION";
    public static final String BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT = "BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT";
    public static final String OCCUPANCY_TYPE = "OCCUPANCY_TYPE";
    public static final String OCCUPIED_NORMAL = "OCCUPIED_NORMAL";
    public static final String WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT = "WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT";
    public static final String ALARM_SYSTEM = "ALARM_SYSTEM";
    public static final String ALARM_SYSTEM_TYPE = "ALARM_SYSTEM_TYPE";
    public static final String ALARM_REASON_FOR_POOR_OUTCOME = "ALARM_REASON_FOR_POOR_OUTCOME";
    public static final String ACCIDENTAL_OR_DELIBERATE = "ACCIDENTAL_OR_DELIBERATE";
    public static final String VEHICLES = "VEHICLES";
    public static final String VEHICLES_CODE = "VEHICLES_CODE";
    public static final String PERSONNEL = "PERSONNEL";
    public static final String PERSONNEL_CODE = "PERSONNEL_CODE";
    public static final String STARTING_DELAY_DESCRIPTION = "STARTING_DELAY_DESCRIPTION";
    public static final String ACTION_NON_FRS_DESCRIPTION = "ACTION_NON_FRS_DESCRIPTION";
    public static final String ACTION_FRS_DESCRIPTION = "ACTION_FRS_DESCRIPTION";
    public static final String CAUSE_OF_FIRE = "CAUSE_OF_FIRE";
    public static final String IGNITION_POWER = "IGNITION_POWER";
    public static final String SOURCE_OF_IGNITION = "SOURCE_OF_IGNITION";
    public static final String ITEM_IGNITED = "ITEM_IGNITED";
    public static final String ITEM_CAUSING_SPREAD = "ITEM_CAUSING_SPREAD";
    public static final String RAPID_FIRE_GROWTH = "RAPID_FIRE_GROWTH";
    public static final String CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION = "CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION";
    public static final String CAUSE_EXPLOSION_INVOLVED = "CAUSE_EXPLOSION_INVOLVED";
    public static final String CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION = "CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION";
    public static final String CAUSE_EXPLOSION_STAGE_DESCRIPTION = "CAUSE_EXPLOSION_STAGE_DESCRIPTION";
    public static final String CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION = "CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION";
    public static final String BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION = "BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION";
    public static final String BUILDING_FLOORS_ABOVE_GROUND = "BUILDING_FLOORS_ABOVE_GROUND";
    public static final String BUILDING_FLOORS_BELOW_GROUND = "BUILDING_FLOORS_BELOW_GROUND";
    public static final String BUILDING_FLOOR_ORIGIN = "BUILDING_FLOOR_ORIGIN";
    public static final String BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION = "BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION";
    public static final String BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION = "BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION";
    public static final String FIRE_START_LOCATION = "FIRE_START_LOCATION";
    public static final String FIRE_SIZE_ON_ARRIVAL = "FIRE_SIZE_ON_ARRIVAL";
    public static final String OTHER_PROPERTY_AFFECTED_ON_ARRIVAL = "OTHER_PROPERTY_AFFECTED_ON_ARRIVAL";
    public static final String BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION = "BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION";
    public static final String BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION = "BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION";
    public static final String FIRE_SIZE_ON_ARRIVAL_DESCRIPTION = "FIRE_SIZE_ON_ARRIVAL_DESCRIPTION";
    public static final String other_property_affected_close_d = "other_property_affected_close_d";
    public static final String spread_of_fire_d = "spread_of_fire_d";
    public static final String RESPONSE_TIME = "RESPONSE_TIME";
    public static final String RESPONSE_TIME_CODE = "RESPONSE_TIME_CODE";
    public static final String TIME_AT_SCENE = "TIME_AT_SCENE";
    public static final String TIME_AT_SCENE_CODE = "TIME_AT_SCENE_CODE";
    public static final String FATALITY_CASUALTY = "FATALITY_CASUALTY";
    public static final String RESCUES = "RESCUES";
    public static final String EVACUATIONS = "EVACUATIONS";
    public static final String EVACUATIONS_CODE = "EVACUATIONS_CODE";
    public static final String BUILDING_EVACUATION_DELAY_DESCRIPTION = "BUILDING_EVACUATION_DELAY_DESCRIPTION";
    public static final String BUILDING_EVACUATION_TIME_DESCRIPTION = "BUILDING_EVACUATION_TIME_DESCRIPTION";
    // Variable values
    // FINNCIAL_YEAR
    public static final String S2014_15 = "2014/15";
    public static final String S2015_16 = "2015/16";
    public static final String S2016_17 = "2016/17";
    public static final String S2017_18 = "2017/18";
    public static final String S2018_19 = "2018/19";
    public static final String S2019_20 = "2019/20";
    public static final String Cladding = "Cladding";
    public static final String FatalityOrCasualty = "Fatality/Casualty";
    //public static final String FatalityOrCasualty = "Fatality_OR_Casualty";
    //public static final String NoCompartmentationInBuilding = "No_compartmentation_in_building";
    public static final String NoCompartmentationInBuilding = "No compartmentation in building";
    public static final String BungalowSingleOccupancy = "Bungalow - single occupancy";
    public static final String PurposeBuiltHighRiseFlats = "Purpose Built High Rise (10+) Flats";
    public static final String PurposeBuiltMediumRiseFlats = "Purpose Built Medium Rise (4-9) Flats";
    public static final String PurposeBuiltLowRiseFlats = "Purpose Built Low Rise (1-3) Flats/Maisonettes";
    public static final String WholeBuildingOrAffectingMoreThan2Floors = "Whole Building/ Affecting more than 2 floors";
    //public static final String WholeBuildingOrAffectingMoreThan2Floors = "Whole_Building_OR__Affecting_more_than_2_floors";

    // EHS data
    public static final String allDwellings = "all dwellings";
    public static final String allTenures = "all tenures";
    public static final String bungalow = "bungalow";
    public static final String purposeBuiltFlatLowRise = "purpose built flat, low rise";
    public static final String purposeBuiltFlatHighRise = "purpose built flat, high rise";
    
    public F_Strings() {
        super();
    }
}
