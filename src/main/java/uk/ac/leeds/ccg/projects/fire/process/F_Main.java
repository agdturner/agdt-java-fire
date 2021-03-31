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
package uk.ac.leeds.ccg.projects.fire.process;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import uk.ac.leeds.ccg.data.core.Data_Environment;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.projects.fire.core.F_Environment;
import uk.ac.leeds.ccg.projects.fire.io.F_Files;
import uk.ac.leeds.ccg.projects.fire.core.F_Object;
import uk.ac.leeds.ccg.projects.fire.core.F_Strings;
import uk.ac.leeds.ccg.projects.fire.data.F_Collection0;
import uk.ac.leeds.ccg.projects.fire.data.F_Data;
import uk.ac.leeds.ccg.projects.fire.data.dwellings.F_Dwellings_String_Record1;
import uk.ac.leeds.ccg.projects.fire.data.dwellings.F_Dwellings_String_Record2;
import uk.ac.leeds.ccg.generic.io.Generic_Defaults;
import uk.ac.leeds.ccg.generic.io.Generic_IO;
import uk.ac.leeds.ccg.generic.util.Generic_Collections;
import uk.ac.leeds.ccg.math.Math_BigDecimal;
import uk.ac.leeds.ccg.projects.fire.data.F_Collection;
import uk.ac.leeds.ccg.projects.fire.data.F_Data0;
import uk.ac.leeds.ccg.projects.fire.data.dwellings.F_Dwellings_Integer_Record0;
import uk.ac.leeds.ccg.projects.fire.data.dwellings.F_Dwellings_Integer_Record1;
import uk.ac.leeds.ccg.projects.fire.data.dwellings.F_Dwellings_Integer_Record2;
import uk.ac.leeds.ccg.projects.fire.data.dwellings.F_Dwellings_String_Record0;
import uk.ac.leeds.ccg.projects.fire.data.ehs.F_ARecord;
import uk.ac.leeds.ccg.projects.fire.id.F_CollectionID;
import uk.ac.leeds.ccg.projects.fire.id.F_RecordID;
import static uk.ac.leeds.ccg.projects.fire.process.F_XLSXParser.getWorkbook;
import static uk.ac.leeds.ccg.projects.fire.process.F_XLSXParser.getHSSFWorkbook;

/**
 * F_Main
 *
 * This is that main processor for the fire incidence data.
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class F_Main extends F_Object {

    private static final long serialVersionUID = 1L;

    BigDecimal THOUSAND = BigDecimal.valueOf(1000);

    // Single variables
    // ----------------
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFRS_NAME = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_FRS_NAME = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllE_CODE = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_E_CODE = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllMONTH_NAME = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_MONTH_NAME = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllWEEKDAY_WEEKEND = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_WEEKDAY_WEEKEND = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllDAY_NIGHT = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_DAY_NIGHT = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllBUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllLATE_CALL = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_LATE_CALL = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllMULTI_SEATED_FLAG = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_MULTI_SEATED_FLAG = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllIGNITION_TO_DISCOVERY = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_IGNITION_TO_DISCOVERY = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllDISCOVERY_TO_CALL = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_DISCOVERY_TO_CALL = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllHOW_DISCOVERED_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_HOW_DISCOVERED_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllOCCUPIED_NORMAL = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_OCCUPIED_NORMAL = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllACCIDENTAL_OR_DELIBERATE = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_ACCIDENTAL_OR_DELIBERATE = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllVEHICLES = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_VEHICLES = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllVEHICLES_CODE = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_VEHICLES_CODE = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllPERSONNEL = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_PERSONNEL = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllPERSONNEL_CODE = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_PERSONNEL_CODE = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllSTARTING_DELAY_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_STARTING_DELAY_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllACTION_NON_FRS_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_ACTION_NON_FRS_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllACTION_FRS_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_ACTION_FRS_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllCAUSE_OF_FIRE = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_CAUSE_OF_FIRE = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllIGNITION_POWER = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_IGNITION_POWER = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllSOURCE_OF_IGNITION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_SOURCE_OF_IGNITION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllITEM_IGNITED = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_ITEM_IGNITED = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllITEM_CAUSING_SPREAD = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_ITEM_CAUSING_SPREAD = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllRAPID_FIRE_GROWTH = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_RAPID_FIRE_GROWTH = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllCAUSE_EXPLOSION_INVOLVED = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_CAUSE_EXPLOSION_INVOLVED = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllCAUSE_EXPLOSION_STAGE_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_CAUSE_EXPLOSION_STAGE_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllBUILDING_FLOORS_ABOVE_GROUND = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_BUILDING_FLOORS_ABOVE_GROUND = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllBUILDING_FLOORS_BELOW_GROUND = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_BUILDING_FLOORS_BELOW_GROUND = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllBUILDING_FLOOR_ORIGIN = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_BUILDING_FLOOR_ORIGIN = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFIRE_START_LOCATION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_FIRE_START_LOCATION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFIRE_SIZE_ON_ARRIVAL = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_FIRE_SIZE_ON_ARRIVAL = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllOTHER_PROPERTY_AFFECTED_ON_ARRIVAL = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_OTHER_PROPERTY_AFFECTED_ON_ARRIVAL = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFIRE_SIZE_ON_ARRIVAL_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_FIRE_SIZE_ON_ARRIVAL_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllother_property_affected_close_d = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_other_property_affected_close_d = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllspread_of_fire_d = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_spread_of_fire_d = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllRESPONSE_TIME = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_RESPONSE_TIME = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllRESPONSE_TIME_CODE = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_RESPONSE_TIME_CODE = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllTIME_AT_SCENE = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_TIME_AT_SCENE = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllTIME_AT_SCENE_CODE = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_TIME_AT_SCENE_CODE = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllRESCUES = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_RESCUES = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllEVACUATIONS = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_EVACUATIONS = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllEVACUATIONS_CODE = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_EVACUATIONS_CODE = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllBUILDING_EVACUATION_DELAY_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_BUILDING_EVACUATION_DELAY_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllBUILDING_EVACUATION_TIME_DESCRIPTION = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_BUILDING_EVACUATION_TIME_DESCRIPTION = new HashMap<>();
    // Normal Dwelling Types
    Map<Integer, Map<Integer, Integer>> mAllOCCUPANCY_TYPE = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_OCCUPANCY_TYPE = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllALARM_SYSTEM = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_ALARM_SYSTEM = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllALARM_SYSTEM_TYPE = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_ALARM_SYSTEM_TYPE = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllALARM_REASON_FOR_POOR_OUTCOME = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_ALARM_REASON_FOR_POOR_OUTCOME = new HashMap<>();
    // Other Dwelling Types
    Map<Integer, Map<Integer, Integer>> mAllFSO_APPLY = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_FSO_APPLY = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllSAFETY_SYSTEM = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_SAFETY_SYSTEM = new HashMap<>();
    // For floors 4 and 5 of Purpose Built Medium Rise
    Map<Integer, Integer> mMediumRise45 = new HashMap<>();
    Map<Integer, Integer> mMediumRise45_FATALITY_CASUALTY = new HashMap<>();
    Map<Integer, Integer> mMediumRise45_NoCompartmentation = new HashMap<>();
    Map<Integer, Integer> mMediumRise45_NoCompartmentation_FATALITY_CASUALTY = new HashMap<>();
    Map<Integer, Integer> mMediumRise45_WholeBuildingOrAffectingMoreThan2Floors = new HashMap<>();
    Map<Integer, Integer> mMediumRise45_WholeBuildingOrAffectingMoreThan2Floors_FATALITY_CASUALTY = new HashMap<>();
    Map<Integer, Integer> mMediumRise45_Cladding_WholeBuildingOrAffectingMoreThan2Floors = new HashMap<>();
    Map<Integer, Integer> mMediumRise45_Cladding_WholeBuildingOrAffectingMoreThan2Floors_FATALITY_CASUALTY = new HashMap<>();
    // For floors 6, 7, 8 and 9 of Purpose Built Medium Rise
    Map<Integer, Integer> mMediumRise6789 = new HashMap<>();
    Map<Integer, Integer> mMediumRise6789_FATALITY_CASUALTY = new HashMap<>();
    Map<Integer, Integer> mMediumRise6789_NoCompartmentation = new HashMap<>();
    Map<Integer, Integer> mMediumRise6789_NoCompartmentation_FATALITY_CASUALTY = new HashMap<>();
    Map<Integer, Integer> mMediumRise6789_WholeBuildingOrAffectingMoreThan2Floors = new HashMap<>();
    Map<Integer, Integer> mMediumRise6789_WholeBuildingOrAffectingMoreThan2Floors_FATALITY_CASUALTY = new HashMap<>();
    Map<Integer, Integer> mMediumRise6789_Cladding_WholeBuildingOrAffectingMoreThan2Floors = new HashMap<>();
    Map<Integer, Integer> mMediumRise6789_Cladding_WholeBuildingOrAffectingMoreThan2Floors_FATALITY_CASUALTY = new HashMap<>();
    // FIRE_SAFETY scores
    Map<Integer, Map<Integer, Integer>> mAllFDScore = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_FDScore = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFSFScoreManagement = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_FSFScoreManagement = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFSFScoreConstruction = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_FSFScoreConstruction = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFSFScoreFireFighting = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_FSFScoreFireFighting = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllCombinedScore = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_CombinedScore = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllHoleyCheese = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_HoleyCheese = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllDelaysToFireFightingAndNotFirespreadHoleyCheese = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_DelaysToFireFightingAndNotFirespreadHoleyCheese = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFirespreadAndNotDelaysToFireFightingHoleyCheese = new HashMap<>();
    Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_FirespreadAndNotDelaysToFireFightingHoleyCheese = new HashMap<>();

    // Selected variable counts By FINANCIAL YEAR
    // ------------------------------------------
    // 
    // Single variable selections
    // --------------------------
    // DAY_NIGHT
    HashMap<Integer, HashMap<Integer, Integer>> mAllNight_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_Night_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION
    HashMap<Integer, HashMap<Integer, Integer>> mAllNoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // spread_of_fire_d
    HashMap<Integer, HashMap<Integer, Integer>> mAllWholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // STARTING_DELAY_DESCRIPTION
    // BuildingType
    // DelayDueToAccessingFireBuildingType = "Delay due to: Accessing fire - due to building type e.g. high rise building"
    HashMap<Integer, HashMap<Integer, Integer>> mAllSDD_BuildingType_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_SDD_BuildingType_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // LargeSite
    // DelayDueToAccessingFireLargeSite = "Delay due to: Accessing fire - large site"
    HashMap<Integer, HashMap<Integer, Integer>> mAllSDD_LargeSite_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_SDD_LargeSite_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // Security
    // DelayDueToAccessingFireSecurity = "Delay due to: Accessing fire - security doors/other security measures"
    HashMap<Integer, HashMap<Integer, Integer>> mAllSDD_Security_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_SDD_Security_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // Assault
    // DelayDueToAssaultOnFirefighters = "Delay due to: Assault on Firefighters"
    HashMap<Integer, HashMap<Integer, Integer>> mAllSDD_Assault_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_SDD_Assault_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // Civil
    // DelayDueToCivilDisturbance = "Delay due to: Civil disturbance"
    HashMap<Integer, HashMap<Integer, Integer>> mAllSDD_Civil_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_SDD_Civil_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // Location
    // DelayDueToLocationOfFireNotImmediatelyEvident = "Delay due to: Location of fire not immediately evident"
    HashMap<Integer, HashMap<Integer, Integer>> mAllSDD_Location_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_SDD_Location_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // Wrong
    // DelayDueToSentToWrongLocation = "Delay due to: Sent to wrong location"
    HashMap<Integer, HashMap<Integer, Integer>> mAllSDD_Wrong_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_SDD_Wrong_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // Vehicle
    // DelayDueToVehicleAccessProblems = "Delay due to: Vehicle access problems"
    HashMap<Integer, HashMap<Integer, Integer>> mAllSDD_Vehicle_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_SDD_Vehicle_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // Delay
    HashMap<Integer, HashMap<Integer, Integer>> mAllSDD_Delay_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_SDD_Delay_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // NoDelay = "No delay"
    HashMap<Integer, HashMap<Integer, Integer>> mAllSDD_NoDelay_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_SDD_NoDelay_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // RESCUES
    HashMap<Integer, HashMap<Integer, Integer>> mAllRescues_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_Rescues_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // BUILDING_EVACUATION_DELAY_DESCRIPTION
    HashMap<Integer, HashMap<Integer, Integer>> mAllDelayEvac_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_DelayEvac_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllDelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // BUILDING_EVACUATION_TIME_DESCRIPTION
    HashMap<Integer, HashMap<Integer, Integer>> mAllEvacGT5_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_EvacGT5_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // OCCUPANCY_TYPE
    HashMap<Integer, HashMap<Integer, Integer>> mAllThreeOrMoreAdultsUnderPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_ThreeOrMoreAdultsUnderPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_ThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllCoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_CoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllCoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_CoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllCoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_CoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllLoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_LoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllLonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_LonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllLonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_LonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // CAUSE_OF_FIRE
    HashMap<Integer, HashMap<Integer, Integer>> mAllAccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // "Fault in equipment or appliance" AND ANY "Faulty fuel supply..." AND "Faulty leads to equipment or appliance" AND "Overheating, unknown cause" (basically things more likely to happen without people being aware at all).
    HashMap<Integer, HashMap<Integer, Integer>> mAllFaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION
    HashMap<Integer, HashMap<Integer, Integer>> mAllBSSMOEDNotApplicable_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_BSSMOEDNotApplicable_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // FIRE_SAFETY scores
    HashMap<Integer, HashMap<Integer, Integer>> mAllFIRE_SAFETY_SUCCESS_SCORE_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_FIRE_SAFETY_SUCCESS_SCORE_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFIRE_SAFETY_FAILURE_SCORE_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_FIRE_SAFETY_FAILURE_SCORE_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllHoleyCheese2_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_HoleyCheese2_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllHoleyCheese3_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_HoleyCheese3_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllHoleyCheese4_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_HoleyCheese4_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllDelaysToFireFightingAndNotFirespreadHoleyCheese_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_DelaysToFireFightingAndNotFirespreadHoleyCheese_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();

    // Multiple variable selections
    // ----------------------------
    HashMap<Integer, HashMap<Integer, Integer>> mAllNight_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_Night_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllNight_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_Night_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllCladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_Cladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllEvacGT5_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_EvacGT5_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllWholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsUnderPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsUnderPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllWholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllWholeBuildingOrAffectingMoreThan2Floors_CoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_CoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllWholeBuildingOrAffectingMoreThan2Floors_CoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_CoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllWholeBuildingOrAffectingMoreThan2Floors_CoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_CoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllWholeBuildingOrAffectingMoreThan2Floors_LoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_LoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllWholeBuildingOrAffectingMoreThan2Floors_LonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_LonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllWholeBuildingOrAffectingMoreThan2Floors_LonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_LonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllWholeBuildingOrAffectingMoreThan2Floors_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();

    HashMap<Integer, HashMap<Integer, Integer>> mAllWholeBuildingOrAffectingMoreThan2Floors_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, HashMap<Integer, Integer>> mAllFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();

    // Total for all FINANCIAL_YEARS
    // -----------------------------
    // Single variable selections
    // --------------------------
    // DAY_NIGHT
    HashMap<Integer, Integer> mTotalNight_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_Night_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION
    HashMap<Integer, Integer> mTotalNoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // spread_of_fire_d
    HashMap<Integer, Integer> mTotalWholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // STARTING_DELAY_DESCRIPTION
    // BuildingType
    HashMap<Integer, Integer> mTotalSDD_BuildingType_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_SDD_BuildingType_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // LargeSite
    HashMap<Integer, Integer> mTotalSDD_LargeSite_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_SDD_LargeSite_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // Security
    HashMap<Integer, Integer> mTotalSDD_Security_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_SDD_Security_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // Assault
    HashMap<Integer, Integer> mTotalSDD_Assault_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_SDD_Assault_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // Civil
    HashMap<Integer, Integer> mTotalSDD_Civil_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_SDD_Civil_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // Location
    HashMap<Integer, Integer> mTotalSDD_Location_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_SDD_Location_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // Wrong
    HashMap<Integer, Integer> mTotalSDD_Wrong_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_SDD_Wrong_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // Vehicle
    HashMap<Integer, Integer> mTotalSDD_Vehicle_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_SDD_Vehicle_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // Delay
    HashMap<Integer, Integer> mTotalSDD_Delay_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_SDD_Delay_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // NoDelay
    HashMap<Integer, Integer> mTotalSDD_NoDelay_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_SDD_NoDelay_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // RESCUES
    HashMap<Integer, Integer> mTotalRescues_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_Rescues_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // BUILDING_EVACUATION_DELAY_DESCRIPTION
    HashMap<Integer, Integer> mTotalDelayEvac_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_DelayEvac_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalDelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // BUILDING_EVACUATION_TIME_DESCRIPTION
    HashMap<Integer, Integer> mTotalEvacGT5_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_EvacGT5_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // OCCUPANCY_TYPE
    HashMap<Integer, Integer> mTotalThreeOrMoreAdultsUnderPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_ThreeOrMoreAdultsUnderPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_ThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalCoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_CoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalCoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_CoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalCoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_CoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalLoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_LoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalLonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_LonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalLonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_LonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // CAUSE_OF_FIRE
    HashMap<Integer, Integer> mTotalAccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION
    HashMap<Integer, Integer> mTotalBSSMOEDNotApplicable_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_BSSMOEDNotApplicable_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // FIRE_SAFETY scores
    HashMap<Integer, Integer> mTotalFIRE_SAFETY_SUCCESS_SCORE_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_FIRE_SAFETY_SUCCESS_SCORE_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFIRE_SAFETY_FAILURE_SCORE_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_FIRE_SAFETY_FAILURE_SCORE_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalHoleyCheese2_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_HoleyCheese2_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalHoleyCheese3_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_HoleyCheese3_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalHoleyCheese4_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_HoleyCheese4_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalDelaysToFireFightingAndNotFirespreadHoleyCheese_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_DelaysToFireFightingAndNotFirespreadHoleyCheese_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    // Multiple variable selections
    HashMap<Integer, Integer> mTotalNight_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_Night_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalNight_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_Night_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalCladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_Cladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalEvacGT5_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_EvacGT5_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalWholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsUnderPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsUnderPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalWholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalWholeBuildingOrAffectingMoreThan2Floors_CoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_CoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalWholeBuildingOrAffectingMoreThan2Floors_CoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_CoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalWholeBuildingOrAffectingMoreThan2Floors_CoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_CoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalWholeBuildingOrAffectingMoreThan2Floors_LoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_LoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalWholeBuildingOrAffectingMoreThan2Floors_LonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_LonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalWholeBuildingOrAffectingMoreThan2Floors_LonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_LonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalWholeBuildingOrAffectingMoreThan2Floors_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalWholeBuildingOrAffectingMoreThan2Floors_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
    HashMap<Integer, Integer> mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();

    // For convenience
    protected final F_Files files;

    public F_Main(F_Environment env) {
        super(env);
        files = env.files;
    }

    /**
     * Main method.
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            Path dataDir = Paths.get(System.getProperty("user.home"),
                    F_Strings.s_data, "projects", "Fire");
            F_Environment e = new F_Environment(new Data_Environment(
                    new Generic_Environment(new Generic_Defaults(dataDir))));
            F_Main p = new F_Main(e);
            p.run();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    /**
     * Run once an instance ({@code this}) is set up.
     *
     * @throws ClassNotFoundException
     */
    public void run() throws ClassNotFoundException {
        try {
            //boolean doLoadData = true;
            boolean doLoadData = false;
            if (doLoadData) {
                env.data0 = new F_Data0(env);
                env.data = new F_Data(env, 2010, 2019);
                loadHomeOfficeRestrictedData();
            } else {
                env.data.env = env;
                process();
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    /**
     * Loads source csv data files into collections. Each collections is a
     * collection for a month.
     *
     * @throws IOException
     */
    public void loadHomeOfficeRestrictedData() throws IOException, Exception {
        String m = "loadHomeOfficeRestrictedData";
        env.logStartTag(m);

        String type = ".xlsx";
        Path indir = Paths.get(files.getInputDir().toString(), "HomeOffice",
                "Restricted");

        int varFINANCIAL_YEAR = env.data.vname2id.get(F_Strings.FINANCIAL_YEAR);
        Map<String, Integer> tFINANCIAL_YEARVM = env.data.name2ids.get(varFINANCIAL_YEAR);

        // Load "other building - residential to send.xlsx"        
        Path f = Paths.get(indir.toString(),
                "other building - residential to send" + type);
        F_CollectionID cID = new F_CollectionID(env.data0.cID2recIDs.size());
        HashSet<F_RecordID> recIDs = new HashSet<>();
        env.data0.cID2recIDs.put(cID, recIDs);
        F_Collection0 c0 = new F_Collection0(cID);
        env.data0.data.put(cID, c0);
        env.log("Reading " + f.toString());
        Workbook wb = getWorkbook(f);
        if (wb == null) {
            env.log(f.toString() + " Errrrrr");
        } else {
            DataFormatter df = new DataFormatter();
            int i = 1;
            int numberOfSheets = wb.getNumberOfSheets();
            for (Sheet sheet : wb) {
                // Write out to text file
                String s = "Sheet " + i + " of " + numberOfSheets + ": " + sheet.getSheetName();
                System.out.println(s);
                if (i == 2) {
                    int ri = 0;
                    int n = 0;
                    for (Row row : sheet) {
                        if (n == 0) {
                            n = row.getLastCellNum();
                            for (Cell cell : row) {
                                System.out.print(df.formatCellValue(cell) + " ");
                            }
                            System.out.println();
                        } else {
                            String[] line = new String[n];
                            //int r = row.getRowNum();
                            //s = "\tRow " + r;
                            //System.out.println(s);
                            //pw.println(s);
                            for (Cell cell : row) {
                                line[cell.getColumnIndex()] = getFormatted(df.formatCellValue(cell));
                            }
                            try {
                                if (!line[0].isBlank()) {
                                    F_RecordID recID = new F_RecordID(Long.valueOf(line[0]));
                                    F_Dwellings_String_Record2 rec = new F_Dwellings_String_Record2(recID, line);
                                    recIDs.add(recID);
                                    c0.data2.put(recID, rec);
                                    Generic_Collections.addToMap(env.data0.cID2recIDs, cID, recID);
                                    env.data0.recID2cID.put(recID, cID);
                                }
                            } catch (Exception e) {
                                System.err.println("Error loading record " + ri + ", recID=" + line[0]);
                            }
                            if (ri % 1000 == 0) {
                                env.log("loaded record " + ri);
                            }
                        }
                        ri++;
                    }
                }
                i++;
            }
        }
        env.data0.cacheCollection(cID, c0);
        env.data0.clearCollection(cID);

        // Load other dwellings data
        Path[] vfs = new Path[6];
        vfs[0] = Paths.get(indir.toString(), "dwellings data to send 10-11" + type);
        vfs[1] = Paths.get(indir.toString(), "dwellings data to send 11-12" + type);
        vfs[2] = Paths.get(indir.toString(), "dwellings data to send 12-14" + type);
        vfs[3] = Paths.get(indir.toString(), "dwellings data to send 14-16" + type);
        vfs[4] = Paths.get(indir.toString(), "dwellings data to send 16-18" + type);
        vfs[5] = Paths.get(indir.toString(), "dwellings data to send 18-20" + type);

        for (Path vf : vfs) {
            cID = new F_CollectionID(env.data0.cID2recIDs.size());
            recIDs = new HashSet<>();
            env.data0.cID2recIDs.put(cID, recIDs);
            c0 = new F_Collection0(cID);
            env.data0.data.put(cID, c0);
            env.log("Reading " + vf.toString());
            wb = getWorkbook(vf);
            if (wb == null) {
                env.log(vf.toString() + " Errrrrr");
            } else {
                DataFormatter df = new DataFormatter();
                int i = 1;
                int numberOfSheets = wb.getNumberOfSheets();
                for (Sheet sheet : wb) {
                    // Write out to text file
                    String s = "Sheet " + i + " of " + numberOfSheets + ": " + sheet.getSheetName();
                    System.out.println(s);
                    if (i == 2) {
                        int ri = 0;
                        int n = 0;
                        for (Row row : sheet) {
                            if (n == 0) {
                                n = row.getLastCellNum();
                                for (Cell cell : row) {
                                    System.out.print(df.formatCellValue(cell) + " ");
                                }
                                System.out.println();
                            } else {
                                String[] line = new String[n];
                                //int r = row.getRowNum();
                                //s = "\tRow " + r;
                                //System.out.println(s);
                                //pw.println(s);
                                for (Cell cell : row) {
                                    line[cell.getColumnIndex()] = getFormatted(df.formatCellValue(cell));
                                }
                                try {
                                    if (!line[0].isBlank()) {
                                        F_RecordID recID = new F_RecordID(Long.valueOf(line[0]));
                                        recIDs.add(recID);
                                        c0.data1.put(recID, new F_Dwellings_String_Record1(recID, line));
                                        Generic_Collections.addToMap(env.data0.cID2recIDs, cID, recID);
                                        env.data0.recID2cID.put(recID, cID);
                                    }
                                } catch (Exception e) {
                                    System.err.println("Error loading record " + ri + ", recID=" + line[0]);
                                }
                                if (ri % 1000 == 0) {
                                    env.log("loaded record " + ri);
                                }
                            }
                            ri++;
                        }
                    }
                    i++;
                }
            }
            env.data0.cacheCollection(cID, c0);
            env.data0.clearCollection(cID);
        }
        /**
         * Summarise the data and organise it into collections of records for
         * each calendar year.
         * <ul>
         * <li>number of fires by type of building over the period of the
         * data</li>
         * <li>number of fires by type of building where fire spreads beyond 2
         * floors over period of data</li>
         * <li>number of fires by type of building by starting height and ending
         * height of fire over period of data</li>
         * <li>number of fires by type of building with compartmentation missing
         * over period of data</li>
         * <li>each of the above with injuries / fatalities</li>
         * </ul>
         */
        try {
            // 1 Variable
            HashMap<String, Integer> mFRS_NAME = new HashMap<>();
            HashMap<String, Integer> mE_CODE = new HashMap<>();
            HashMap<String, Integer> mFINANCIAL_YEAR = new HashMap<>();
            HashMap<String, Integer> mMONTH_NAME = new HashMap<>();
            HashMap<String, Integer> mWEEKDAY_WEEKEND = new HashMap<>();
            HashMap<String, Integer> mDAY_NIGHT = new HashMap<>();
            HashMap<String, Integer> mBUILDING_OR_PROPERTY_TYPE = new HashMap<>();
            HashMap<String, Integer> mLATE_CALL = new HashMap<>();
            HashMap<String, Integer> mMULTI_SEATED_FLAG = new HashMap<>();
            HashMap<String, Integer> mIGNITION_TO_DISCOVERY = new HashMap<>();
            HashMap<String, Integer> mDISCOVERY_TO_CALL = new HashMap<>();
            HashMap<String, Integer> mHOW_DISCOVERED_DESCRIPTION = new HashMap<>();
            HashMap<String, Integer> mBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION = new HashMap<>();
            HashMap<String, Integer> mBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION = new HashMap<>();
            HashMap<String, Integer> mBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT = new HashMap<>();
            HashMap<String, Integer> mOCCUPIED_NORMAL = new HashMap<>();
            HashMap<String, Integer> mACCIDENTAL_OR_DELIBERATE = new HashMap<>();
            HashMap<String, Integer> mVEHICLES = new HashMap<>();
            HashMap<String, Integer> mVEHICLES_CODE = new HashMap<>();
            HashMap<String, Integer> mPERSONNEL = new HashMap<>();
            HashMap<String, Integer> mPERSONNEL_CODE = new HashMap<>();
            HashMap<String, Integer> mSTARTING_DELAY_DESCRIPTION = new HashMap<>();
            HashMap<String, Integer> mACTION_NON_FRS_DESCRIPTION = new HashMap<>();
            HashMap<String, Integer> mACTION_FRS_DESCRIPTION = new HashMap<>();
            HashMap<String, Integer> mCAUSE_OF_FIRE = new HashMap<>();
            HashMap<String, Integer> mIGNITION_POWER = new HashMap<>();
            HashMap<String, Integer> mSOURCE_OF_IGNITION = new HashMap<>();
            HashMap<String, Integer> mITEM_IGNITED = new HashMap<>();
            HashMap<String, Integer> mITEM_CAUSING_SPREAD = new HashMap<>();
            HashMap<String, Integer> mRAPID_FIRE_GROWTH = new HashMap<>();
            HashMap<String, Integer> mCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION = new HashMap<>();
            HashMap<String, Integer> mCAUSE_EXPLOSION_INVOLVED = new HashMap<>();
            HashMap<String, Integer> mCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION = new HashMap<>();
            HashMap<String, Integer> mCAUSE_EXPLOSION_STAGE_DESCRIPTION = new HashMap<>();
            HashMap<String, Integer> mCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION = new HashMap<>();
            HashMap<String, Integer> mBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION = new HashMap<>();
            HashMap<String, Integer> mBUILDING_FLOORS_ABOVE_GROUND = new HashMap<>();
            HashMap<String, Integer> mBUILDING_FLOORS_BELOW_GROUND = new HashMap<>();
            HashMap<String, Integer> mBUILDING_FLOOR_ORIGIN = new HashMap<>();
            HashMap<String, Integer> mBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION = new HashMap<>();
            HashMap<String, Integer> mBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION = new HashMap<>();
            HashMap<String, Integer> mFIRE_START_LOCATION = new HashMap<>();
            HashMap<String, Integer> mFIRE_SIZE_ON_ARRIVAL = new HashMap<>();
            HashMap<String, Integer> mOTHER_PROPERTY_AFFECTED_ON_ARRIVAL = new HashMap<>();
            HashMap<String, Integer> mBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION = new HashMap<>();
            HashMap<String, Integer> mBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION = new HashMap<>();
            HashMap<String, Integer> mFIRE_SIZE_ON_ARRIVAL_DESCRIPTION = new HashMap<>();
            HashMap<String, Integer> mother_property_affected_close_d = new HashMap<>();
            HashMap<String, Integer> mspread_of_fire_d = new HashMap<>();
            HashMap<String, Integer> mRESPONSE_TIME = new HashMap<>();
            HashMap<String, Integer> mRESPONSE_TIME_CODE = new HashMap<>();
            HashMap<String, Integer> mTIME_AT_SCENE = new HashMap<>();
            HashMap<String, Integer> mTIME_AT_SCENE_CODE = new HashMap<>();
            HashMap<String, Integer> mFATALITY_CASUALTY = new HashMap<>();
            HashMap<String, Integer> mRESCUES = new HashMap<>();
            HashMap<String, Integer> mEVACUATIONS = new HashMap<>();
            HashMap<String, Integer> mEVACUATIONS_CODE = new HashMap<>();
            HashMap<String, Integer> mBUILDING_EVACUATION_DELAY_DESCRIPTION = new HashMap<>();
            HashMap<String, Integer> mBUILDING_EVACUATION_TIME_DESCRIPTION = new HashMap<>();
            // Normal dwelling types 
            HashMap<String, Integer> mOCCUPANCY_TYPE = new HashMap<>();
            HashMap<String, Integer> mWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT = new HashMap<>();
            HashMap<String, Integer> mALARM_SYSTEM = new HashMap<>();
            HashMap<String, Integer> mALARM_SYSTEM_TYPE = new HashMap<>();
            HashMap<String, Integer> mALARM_REASON_FOR_POOR_OUTCOME = new HashMap<>();
            // Other dwelling types 
            HashMap<String, Integer> mFSO_APPLY = new HashMap<>();
            HashMap<String, Integer> mSAFETY_SYSTEM = new HashMap<>();

            System.out.println("env.data0.cID2recIDs.size()=" + env.data0.cID2recIDs.size());
            System.out.println("env.data0.recID2cID.size()=" + env.data0.recID2cID.size());
            System.out.println("env.data0.data.size()=" + env.data0.data.size());
            Iterator<F_CollectionID> ite = env.data0.data.keySet().iterator();
            while (ite.hasNext()) {
                F_CollectionID cid = ite.next();
                System.out.println("cid=" + cid.toString());
                //env.data2.env = env;
                c0 = env.data0.getCollection(cid);
                Iterator<F_RecordID> ite2 = c0.data1.keySet().iterator();
                while (ite2.hasNext()) {
                    F_RecordID id = ite2.next();
                    F_Dwellings_String_Record1 r = c0.data1.get(id);
                    Generic_Collections.addToCount(mFATALITY_CASUALTY, r.gettFATALITY_CASUALTY(), 1);
                    addToCounts0(r,
                            mFRS_NAME,
                            mE_CODE,
                            mFINANCIAL_YEAR,
                            mMONTH_NAME,
                            mWEEKDAY_WEEKEND,
                            mDAY_NIGHT,
                            mBUILDING_OR_PROPERTY_TYPE,
                            mLATE_CALL,
                            mMULTI_SEATED_FLAG,
                            mIGNITION_TO_DISCOVERY,
                            mDISCOVERY_TO_CALL,
                            mHOW_DISCOVERED_DESCRIPTION,
                            mBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION,
                            mBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION,
                            mBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT,
                            mOCCUPIED_NORMAL,
                            mACCIDENTAL_OR_DELIBERATE,
                            mVEHICLES,
                            mVEHICLES_CODE,
                            mPERSONNEL,
                            mPERSONNEL_CODE,
                            mSTARTING_DELAY_DESCRIPTION,
                            mACTION_NON_FRS_DESCRIPTION,
                            mACTION_FRS_DESCRIPTION,
                            mCAUSE_OF_FIRE,
                            mIGNITION_POWER,
                            mSOURCE_OF_IGNITION,
                            mITEM_IGNITED,
                            mITEM_CAUSING_SPREAD,
                            mRAPID_FIRE_GROWTH,
                            mCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION,
                            mCAUSE_EXPLOSION_INVOLVED,
                            mCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION,
                            mCAUSE_EXPLOSION_STAGE_DESCRIPTION,
                            mCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION,
                            mBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION,
                            mBUILDING_FLOORS_ABOVE_GROUND,
                            mBUILDING_FLOORS_BELOW_GROUND,
                            mBUILDING_FLOOR_ORIGIN,
                            mBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION,
                            mBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION,
                            mFIRE_START_LOCATION,
                            mFIRE_SIZE_ON_ARRIVAL,
                            mOTHER_PROPERTY_AFFECTED_ON_ARRIVAL,
                            mBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION,
                            mBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION,
                            mFIRE_SIZE_ON_ARRIVAL_DESCRIPTION,
                            mother_property_affected_close_d,
                            mspread_of_fire_d,
                            mRESPONSE_TIME,
                            mRESPONSE_TIME_CODE,
                            mTIME_AT_SCENE,
                            mTIME_AT_SCENE_CODE,
                            mRESCUES,
                            mEVACUATIONS,
                            mEVACUATIONS_CODE,
                            mBUILDING_EVACUATION_DELAY_DESCRIPTION,
                            mBUILDING_EVACUATION_TIME_DESCRIPTION);
                    // Normal Dwelling Types
                    Generic_Collections.addToCount(mOCCUPANCY_TYPE, r.gettOCCUPANCY_TYPE(), 1);
                    Generic_Collections.addToCount(mWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT, r.gettWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT(), 1);
                    Generic_Collections.addToCount(mALARM_SYSTEM, r.gettALARM_SYSTEM(), 1);
                    Generic_Collections.addToCount(mALARM_SYSTEM_TYPE, r.gettALARM_SYSTEM_TYPE(), 1);
                    Generic_Collections.addToCount(mALARM_REASON_FOR_POOR_OUTCOME, r.gettALARM_REASON_FOR_POOR_OUTCOME(), 1);
                }
            }
            ite = env.data0.data.keySet().iterator();
            while (ite.hasNext()) {
                F_CollectionID cid = ite.next();
                System.out.println("cid=" + cid.toString());
                //env.data2.env = env;
                c0 = env.data0.getCollection(cid);
                Iterator<F_RecordID> ite2 = c0.data2.keySet().iterator();
                while (ite2.hasNext()) {
                    F_RecordID id = ite2.next();
                    F_Dwellings_String_Record2 r = c0.data2.get(id);
                    Generic_Collections.addToCount(mFATALITY_CASUALTY, r.gettFATALITY_CASUALTY(), 1);
                    addToCounts0(r,
                            mFRS_NAME,
                            mE_CODE,
                            mFINANCIAL_YEAR,
                            mMONTH_NAME,
                            mWEEKDAY_WEEKEND,
                            mDAY_NIGHT,
                            mBUILDING_OR_PROPERTY_TYPE,
                            mLATE_CALL,
                            mMULTI_SEATED_FLAG,
                            mIGNITION_TO_DISCOVERY,
                            mDISCOVERY_TO_CALL,
                            mHOW_DISCOVERED_DESCRIPTION,
                            mBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION,
                            mBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION,
                            mBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT,
                            mOCCUPIED_NORMAL,
                            mACCIDENTAL_OR_DELIBERATE,
                            mVEHICLES,
                            mVEHICLES_CODE,
                            mPERSONNEL,
                            mPERSONNEL_CODE,
                            mSTARTING_DELAY_DESCRIPTION,
                            mACTION_NON_FRS_DESCRIPTION,
                            mACTION_FRS_DESCRIPTION,
                            mCAUSE_OF_FIRE,
                            mIGNITION_POWER,
                            mSOURCE_OF_IGNITION,
                            mITEM_IGNITED,
                            mITEM_CAUSING_SPREAD,
                            mRAPID_FIRE_GROWTH,
                            mCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION,
                            mCAUSE_EXPLOSION_INVOLVED,
                            mCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION,
                            mCAUSE_EXPLOSION_STAGE_DESCRIPTION,
                            mCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION,
                            mBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION,
                            mBUILDING_FLOORS_ABOVE_GROUND,
                            mBUILDING_FLOORS_BELOW_GROUND,
                            mBUILDING_FLOOR_ORIGIN,
                            mBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION,
                            mBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION,
                            mFIRE_START_LOCATION,
                            mFIRE_SIZE_ON_ARRIVAL,
                            mOTHER_PROPERTY_AFFECTED_ON_ARRIVAL,
                            mBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION,
                            mBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION,
                            mFIRE_SIZE_ON_ARRIVAL_DESCRIPTION,
                            mother_property_affected_close_d,
                            mspread_of_fire_d,
                            mRESPONSE_TIME,
                            mRESPONSE_TIME_CODE,
                            mTIME_AT_SCENE,
                            mTIME_AT_SCENE_CODE,
                            mRESCUES,
                            mEVACUATIONS,
                            mEVACUATIONS_CODE,
                            mBUILDING_EVACUATION_DELAY_DESCRIPTION,
                            mBUILDING_EVACUATION_TIME_DESCRIPTION);
                    // Other Dwelling Types
                    Generic_Collections.addToCount(mFSO_APPLY, r.gettFSO_APPLY(), 1);
                    Generic_Collections.addToCount(mSAFETY_SYSTEM, r.gettSAFETY_SYSTEM(), 1);
                }
            }
            print(F_Strings.FRS_NAME, mFRS_NAME);
            print(F_Strings.E_CODE, mE_CODE);
            print(F_Strings.FINANCIAL_YEAR, mFINANCIAL_YEAR);
            print(F_Strings.MONTH_NAME, mMONTH_NAME);
            print(F_Strings.WEEKDAY_WEEKEND, mWEEKDAY_WEEKEND);
            print(F_Strings.DAY_NIGHT, mDAY_NIGHT);
            print(F_Strings.LATE_CALL, mLATE_CALL);
            print(F_Strings.BUILDING_OR_PROPERTY_TYPE, mBUILDING_OR_PROPERTY_TYPE);
            print(F_Strings.MULTI_SEATED_FLAG, mMULTI_SEATED_FLAG);
            print(F_Strings.IGNITION_TO_DISCOVERY, mIGNITION_TO_DISCOVERY);
            print(F_Strings.DISCOVERY_TO_CALL, mDISCOVERY_TO_CALL);
            print(F_Strings.HOW_DISCOVERED_DESCRIPTION, mHOW_DISCOVERED_DESCRIPTION);
            print(F_Strings.BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION, mBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION);
            print(F_Strings.BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION, mBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION);
            print(F_Strings.BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT, mBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT);
            print(F_Strings.OCCUPIED_NORMAL, mOCCUPIED_NORMAL);
            print(F_Strings.ACCIDENTAL_OR_DELIBERATE, mACCIDENTAL_OR_DELIBERATE);
            print(F_Strings.VEHICLES, mVEHICLES);
            print(F_Strings.VEHICLES_CODE, mVEHICLES_CODE);
            print(F_Strings.PERSONNEL, mPERSONNEL);
            print(F_Strings.PERSONNEL_CODE, mPERSONNEL_CODE);
            print(F_Strings.STARTING_DELAY_DESCRIPTION, mSTARTING_DELAY_DESCRIPTION);
            print(F_Strings.ACTION_NON_FRS_DESCRIPTION, mACTION_NON_FRS_DESCRIPTION);
            print(F_Strings.ACTION_FRS_DESCRIPTION, mACTION_FRS_DESCRIPTION);
            print(F_Strings.CAUSE_OF_FIRE, mCAUSE_OF_FIRE);
            print(F_Strings.IGNITION_POWER, mIGNITION_POWER);
            print(F_Strings.SOURCE_OF_IGNITION, mSOURCE_OF_IGNITION);
            print(F_Strings.ITEM_IGNITED, mITEM_IGNITED);
            print(F_Strings.ITEM_CAUSING_SPREAD, mITEM_CAUSING_SPREAD);
            print(F_Strings.RAPID_FIRE_GROWTH, mRAPID_FIRE_GROWTH);
            print(F_Strings.CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION, mCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION);
            print(F_Strings.CAUSE_EXPLOSION_INVOLVED, mCAUSE_EXPLOSION_INVOLVED);
            print(F_Strings.CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION, mCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION);
            print(F_Strings.CAUSE_EXPLOSION_STAGE_DESCRIPTION, mCAUSE_EXPLOSION_STAGE_DESCRIPTION);
            print(F_Strings.CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION, mCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION);
            print(F_Strings.BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION, mBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION);
            print(F_Strings.BUILDING_FLOORS_ABOVE_GROUND, mBUILDING_FLOORS_ABOVE_GROUND);
            print(F_Strings.BUILDING_FLOORS_BELOW_GROUND, mBUILDING_FLOORS_BELOW_GROUND);
            print(F_Strings.BUILDING_FLOOR_ORIGIN, mBUILDING_FLOOR_ORIGIN);
            print(F_Strings.BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION, mBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION);
            print(F_Strings.BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION, mBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION);
            print(F_Strings.FIRE_START_LOCATION, mFIRE_START_LOCATION);
            print(F_Strings.FIRE_SIZE_ON_ARRIVAL, mFIRE_SIZE_ON_ARRIVAL);
            print(F_Strings.OTHER_PROPERTY_AFFECTED_ON_ARRIVAL, mOTHER_PROPERTY_AFFECTED_ON_ARRIVAL);
            print(F_Strings.BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION, mBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION);
            print(F_Strings.BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION, mBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION);
            print(F_Strings.FIRE_SIZE_ON_ARRIVAL_DESCRIPTION, mFIRE_SIZE_ON_ARRIVAL_DESCRIPTION);
            print(F_Strings.other_property_affected_close_d, mother_property_affected_close_d);
            print(F_Strings.spread_of_fire_d, mspread_of_fire_d);
            print(F_Strings.RESPONSE_TIME, mRESPONSE_TIME);
            print(F_Strings.RESPONSE_TIME_CODE, mRESPONSE_TIME_CODE);
            print(F_Strings.TIME_AT_SCENE, mTIME_AT_SCENE);
            print(F_Strings.TIME_AT_SCENE_CODE, mTIME_AT_SCENE_CODE);
            print(F_Strings.FATALITY_CASUALTY, mFATALITY_CASUALTY);
            print(F_Strings.RESCUES, mRESCUES);
            print(F_Strings.EVACUATIONS, mEVACUATIONS);
            print(F_Strings.EVACUATIONS_CODE, mEVACUATIONS_CODE);
            print(F_Strings.BUILDING_EVACUATION_DELAY_DESCRIPTION, mBUILDING_EVACUATION_DELAY_DESCRIPTION);
            print(F_Strings.BUILDING_EVACUATION_TIME_DESCRIPTION, mBUILDING_EVACUATION_TIME_DESCRIPTION);
            // Normal Dwelling Types
            print(F_Strings.OCCUPANCY_TYPE, mOCCUPANCY_TYPE);
            print(F_Strings.WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT, mWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT);
            print(F_Strings.ALARM_SYSTEM, mALARM_SYSTEM);
            print(F_Strings.ALARM_SYSTEM_TYPE, mALARM_SYSTEM_TYPE);
            print(F_Strings.ALARM_REASON_FOR_POOR_OUTCOME, mALARM_REASON_FOR_POOR_OUTCOME);
            // Other Dwelling Types
            print(F_Strings.FSO_APPLY, mFSO_APPLY);
            print(F_Strings.SAFETY_SYSTEM, mSAFETY_SYSTEM);

            // ---------------
            // Create env.data
            // ---------------
            // Create Lookups
            // --------------
            createLookups(mFRS_NAME.keySet(), F_Strings.FRS_NAME);
            createLookups(mE_CODE.keySet(), F_Strings.E_CODE);
            //createLookups(mFINANCIAL_YEAR.keySet(), F_Strings.FINANCIAL_YEAR);
            createLookups(mMONTH_NAME.keySet(), F_Strings.MONTH_NAME);
            createLookups(mWEEKDAY_WEEKEND.keySet(), F_Strings.WEEKDAY_WEEKEND);
            createLookups(mDAY_NIGHT.keySet(), F_Strings.DAY_NIGHT);
            createLookups(mLATE_CALL.keySet(), F_Strings.LATE_CALL);
            createLookups(mBUILDING_OR_PROPERTY_TYPE.keySet(), F_Strings.BUILDING_OR_PROPERTY_TYPE);
            createLookups(mMULTI_SEATED_FLAG.keySet(), F_Strings.MULTI_SEATED_FLAG);
            createLookups(mIGNITION_TO_DISCOVERY.keySet(), F_Strings.IGNITION_TO_DISCOVERY);
            createLookups(mDISCOVERY_TO_CALL.keySet(), F_Strings.DISCOVERY_TO_CALL);
            createLookups(mHOW_DISCOVERED_DESCRIPTION.keySet(), F_Strings.HOW_DISCOVERED_DESCRIPTION);
            createLookups(mBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION.keySet(), F_Strings.BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION);
            createLookups(mBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION.keySet(), F_Strings.BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION);
            createLookups(mBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT.keySet(), F_Strings.BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT);
            createLookups(mOCCUPIED_NORMAL.keySet(), F_Strings.OCCUPIED_NORMAL);
            createLookups(mACCIDENTAL_OR_DELIBERATE.keySet(), F_Strings.ACCIDENTAL_OR_DELIBERATE);
            createLookups(mVEHICLES.keySet(), F_Strings.VEHICLES);
            createLookups(mVEHICLES_CODE.keySet(), F_Strings.VEHICLES_CODE);
            createLookups(mPERSONNEL.keySet(), F_Strings.PERSONNEL);
            createLookups(mPERSONNEL_CODE.keySet(), F_Strings.PERSONNEL_CODE);
            createLookups(mSTARTING_DELAY_DESCRIPTION.keySet(), F_Strings.STARTING_DELAY_DESCRIPTION);
            createLookups(mACTION_NON_FRS_DESCRIPTION.keySet(), F_Strings.ACTION_NON_FRS_DESCRIPTION);
            createLookups(mACTION_FRS_DESCRIPTION.keySet(), F_Strings.ACTION_FRS_DESCRIPTION);
            createLookups(mCAUSE_OF_FIRE.keySet(), F_Strings.CAUSE_OF_FIRE);
            createLookups(mIGNITION_POWER.keySet(), F_Strings.IGNITION_POWER);
            createLookups(mSOURCE_OF_IGNITION.keySet(), F_Strings.SOURCE_OF_IGNITION);
            createLookups(mITEM_IGNITED.keySet(), F_Strings.ITEM_IGNITED);
            createLookups(mITEM_CAUSING_SPREAD.keySet(), F_Strings.ITEM_CAUSING_SPREAD);
            createLookups(mRAPID_FIRE_GROWTH.keySet(), F_Strings.RAPID_FIRE_GROWTH);
            createLookups(mCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION.keySet(), F_Strings.CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION);
            createLookups(mCAUSE_EXPLOSION_INVOLVED.keySet(), F_Strings.CAUSE_EXPLOSION_INVOLVED);
            createLookups(mCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION.keySet(), F_Strings.CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION);
            createLookups(mCAUSE_EXPLOSION_STAGE_DESCRIPTION.keySet(), F_Strings.CAUSE_EXPLOSION_STAGE_DESCRIPTION);
            createLookups(mCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION.keySet(), F_Strings.CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION);
            createLookups(mBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION.keySet(), F_Strings.BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION);
            createLookups(mBUILDING_FLOORS_ABOVE_GROUND.keySet(), F_Strings.BUILDING_FLOORS_ABOVE_GROUND);
            createLookups(mBUILDING_FLOORS_BELOW_GROUND.keySet(), F_Strings.BUILDING_FLOORS_BELOW_GROUND);
            createLookups(mBUILDING_FLOOR_ORIGIN.keySet(), F_Strings.BUILDING_FLOOR_ORIGIN);
            createLookups(mBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION.keySet(), F_Strings.BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION);
            createLookups(mBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION.keySet(), F_Strings.BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION);
            createLookups(mFIRE_START_LOCATION.keySet(), F_Strings.FIRE_START_LOCATION);
            createLookups(mFIRE_SIZE_ON_ARRIVAL.keySet(), F_Strings.FIRE_SIZE_ON_ARRIVAL);
            createLookups(mOTHER_PROPERTY_AFFECTED_ON_ARRIVAL.keySet(), F_Strings.OTHER_PROPERTY_AFFECTED_ON_ARRIVAL);
            createLookups(mBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION.keySet(), F_Strings.BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION);
            createLookups(mBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION.keySet(), F_Strings.BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION);
            createLookups(mFIRE_SIZE_ON_ARRIVAL_DESCRIPTION.keySet(), F_Strings.FIRE_SIZE_ON_ARRIVAL_DESCRIPTION);
            createLookups(mother_property_affected_close_d.keySet(), F_Strings.other_property_affected_close_d);
            createLookups(mspread_of_fire_d.keySet(), F_Strings.spread_of_fire_d);
            createLookups(mRESPONSE_TIME.keySet(), F_Strings.RESPONSE_TIME);
            createLookups(mRESPONSE_TIME_CODE.keySet(), F_Strings.RESPONSE_TIME_CODE);
            createLookups(mTIME_AT_SCENE.keySet(), F_Strings.TIME_AT_SCENE);
            createLookups(mTIME_AT_SCENE_CODE.keySet(), F_Strings.TIME_AT_SCENE_CODE);
            createLookups(mFATALITY_CASUALTY.keySet(), F_Strings.FATALITY_CASUALTY);
            createLookups(mRESCUES.keySet(), F_Strings.RESCUES);
            createLookups(mEVACUATIONS.keySet(), F_Strings.EVACUATIONS);
            createLookups(mEVACUATIONS_CODE.keySet(), F_Strings.EVACUATIONS_CODE);
            createLookups(mBUILDING_EVACUATION_DELAY_DESCRIPTION.keySet(), F_Strings.BUILDING_EVACUATION_DELAY_DESCRIPTION);
            createLookups(mBUILDING_EVACUATION_TIME_DESCRIPTION.keySet(), F_Strings.BUILDING_EVACUATION_TIME_DESCRIPTION);
            // Normal Dwelling Types
            createLookups(mOCCUPANCY_TYPE.keySet(), F_Strings.OCCUPANCY_TYPE);
            createLookups(mWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT.keySet(), F_Strings.WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT);
            createLookups(mALARM_SYSTEM.keySet(), F_Strings.ALARM_SYSTEM);
            createLookups(mALARM_SYSTEM_TYPE.keySet(), F_Strings.ALARM_SYSTEM_TYPE);
            createLookups(mALARM_REASON_FOR_POOR_OUTCOME.keySet(), F_Strings.ALARM_REASON_FOR_POOR_OUTCOME);
            // Other Dwelling Types
            createLookups(mFSO_APPLY.keySet(), F_Strings.FSO_APPLY);
            createLookups(mSAFETY_SYSTEM.keySet(), F_Strings.SAFETY_SYSTEM);

            // Organise data into collections by financial year and add FIRE_SAFETY scores 
            // ---------------------------------------------------------------------------
            HashMap<Integer, Integer> mFDScore = new HashMap<>();
            HashMap<Integer, Integer> mFSFScoreManagement = new HashMap<>();
            HashMap<Integer, Integer> mFSFScoreConstruction = new HashMap<>();
            HashMap<Integer, Integer> mFSFScoreFireFighting = new HashMap<>();
            HashMap<Integer, Integer> mCombinedScore = new HashMap<>();
            HashMap<Integer, Integer> mDelaysToFireFightingScore = new HashMap<>();
            HashMap<Integer, Integer> mCauseOfFireScore = new HashMap<>();
            HashMap<Integer, Integer> mFireSpreadScore = new HashMap<>();
            HashMap<Integer, Integer> mEvacuationScore = new HashMap<>();
            HashMap<Integer, Integer> mHoleyCheeseScore = new HashMap<>();
            HashMap<Integer, Integer> mDelaysToFireFightingAndNotFirespreadHoleyCheese = new HashMap<>();

            // MULTI_SEATED_FLAG
            int varMSF = env.data.vname2id.get(F_Strings.MULTI_SEATED_FLAG);
            int valMSF_Yes = env.data.name2ids.get(varMSF).get(F_Strings.yes);
            // IGNITION_TO_DISCOVERY
            int varITD = env.data.vname2id.get(F_Strings.IGNITION_TO_DISCOVERY);
            Set<Integer> svalITD = new HashSet<>();
            svalITD.add(env.data.name2ids.get(varITD).get(F_Strings.FiveToThirtyMinutes));
            svalITD.add(env.data.name2ids.get(varITD).get(F_Strings.Over30MinutesAndUpto2Hours));
            svalITD.add(env.data.name2ids.get(varITD).get(F_Strings.Over2Hours));
            // DISCOVERY_TO_CALL
            int varDTC = env.data.vname2id.get(F_Strings.DISCOVERY_TO_CALL);
            Set<Integer> svalDTC = new HashSet<>();
            svalDTC.add(env.data.name2ids.get(varDTC).get(F_Strings.FiveToThirtyMinutes));
            svalDTC.add(env.data.name2ids.get(varDTC).get(F_Strings.Over30MinutesAndUpto2Hours));
            svalDTC.add(env.data.name2ids.get(varDTC).get(F_Strings.Over2Hours));
            // BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION
            int varBSSCD = env.data.vname2id.get(F_Strings.BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION);
            int valBSSCD_SOCS = env.data.name2ids.get(varBSSCD).get(F_Strings.StoppedOrCheckedSpread);
            Set<Integer> svalBSSCD = new HashSet<>();
            svalBSSCD.add(env.data.name2ids.get(varBSSCD).get(F_Strings.BreachedCurrentBuildingWork));
            svalBSSCD.add(env.data.name2ids.get(varBSSCD).get(F_Strings.BreachedFireDoorsLeftOpenOrIncorrectlyFitted));
            svalBSSCD.add(env.data.name2ids.get(varBSSCD).get(F_Strings.BreachedPreviousBuildingWork));
            svalBSSCD.add(env.data.name2ids.get(varBSSCD).get(F_Strings.DamageToCompartmentation));
            svalBSSCD.add(env.data.name2ids.get(varBSSCD).get(F_Strings.FireSpreadThroughGapsOrVoidsInConstruction));
            svalBSSCD.add(env.data.name2ids.get(varBSSCD).get(F_Strings.Other));
            // BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION
            int varBSSMOED = env.data.vname2id.get(F_Strings.BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION);
            int valBSSMOED_SOCS = env.data.name2ids.get(varBSSMOED).get(F_Strings.OKNoVisibleConcerns);
            Set<Integer> svalBSSMOEDDanger = new HashSet<>();
            svalBSSMOEDDanger.add(env.data.name2ids.get(varBSSMOED).get(F_Strings.ExitRouteBlockedBySmokeOrFlames));
            svalBSSMOEDDanger.add(env.data.name2ids.get(varBSSMOED).get(F_Strings.ContentsContributingToAbnormalFireSpreadOrSmokeProduction));
            svalBSSMOEDDanger.add(env.data.name2ids.get(varBSSMOED).get(F_Strings.ExitsLocked));
            svalBSSMOEDDanger.add(env.data.name2ids.get(varBSSMOED).get(F_Strings.ExitsBlocked));
            svalBSSMOEDDanger.add(env.data.name2ids.get(varBSSMOED).get(F_Strings.PoorImplementation));
            svalBSSMOEDDanger.add(env.data.name2ids.get(varBSSMOED).get(F_Strings.Other));
            Set<Integer> svalBSSMOEDManagement = new HashSet<>();
            svalBSSMOEDManagement.add(env.data.name2ids.get(varBSSMOED).get(F_Strings.ExitsLocked));
            svalBSSMOEDManagement.add(env.data.name2ids.get(varBSSMOED).get(F_Strings.ExitsBlocked));
            svalBSSMOEDManagement.add(env.data.name2ids.get(varBSSMOED).get(F_Strings.PoorImplementation));
            svalBSSMOEDManagement.add(env.data.name2ids.get(varBSSMOED).get(F_Strings.Other));
            Set<Integer> svalBSSMOEDConstruction = new HashSet<>();
            svalBSSMOEDConstruction.add(env.data.name2ids.get(varBSSMOED).get(F_Strings.PoorImplementation));
            svalBSSMOEDConstruction.add(env.data.name2ids.get(varBSSMOED).get(F_Strings.Other));
            // ALARM_REASON_FOR_POOR_OUTCOME
            int varARFPO = env.data.vname2id.get(F_Strings.ALARM_REASON_FOR_POOR_OUTCOME);
            Set<Integer> svalARFPO = new HashSet<>();
            svalARFPO.add(env.data.name2ids.get(varARFPO).get(F_Strings.DefectiveBattery));
            svalARFPO.add(env.data.name2ids.get(varARFPO).get(F_Strings.MissingBattery));
            svalARFPO.add(env.data.name2ids.get(varARFPO).get(F_Strings.FaultySystemOrIncorrectlyInstalled));
            svalARFPO.add(env.data.name2ids.get(varARFPO).get(F_Strings.FireInAreaNotCoveredBySystem));
            svalARFPO.add(env.data.name2ids.get(varARFPO).get(F_Strings.FireProductsDidNotReachDetectors));
            svalARFPO.add(env.data.name2ids.get(varARFPO).get(F_Strings.NoAlarmPresent));
            svalARFPO.add(env.data.name2ids.get(varARFPO).get(F_Strings.AlarmRaisedBeforeSystemOperated));
            svalARFPO.add(env.data.name2ids.get(varARFPO).get(F_Strings.NoOtherPersonResponded));
            svalARFPO.add(env.data.name2ids.get(varARFPO).get(F_Strings.NoPersonInEarshot));
            svalARFPO.add(env.data.name2ids.get(varARFPO).get(F_Strings.OccupantsDidNotRespond));
            svalARFPO.add(env.data.name2ids.get(varARFPO).get(F_Strings.OtherActPreventingAlarmFromOperating));
            //svalARFPO.add(env.data.name2ids.get(varARFPO).get(F_Strings.OtherOrUnspecified));
            // STARTING_DELAY_DESCRIPTION
            int varSDD = env.data.vname2id.get(F_Strings.STARTING_DELAY_DESCRIPTION);
            int valSDD_SOCS = env.data.name2ids.get(varSDD).get(F_Strings.NoDelay);
            Set<Integer> svalSDD = new HashSet<>();
            svalSDD.add(env.data.name2ids.get(varSDD).get(F_Strings.DelayDueToSentToWrongLocation));
            svalSDD.add(env.data.name2ids.get(varSDD).get(F_Strings.DelayDueToVehicleAccessProblems));
            svalSDD.add(env.data.name2ids.get(varSDD).get(F_Strings.DelayDueToAccessingFireLargeSite));
            svalSDD.add(env.data.name2ids.get(varSDD).get(F_Strings.DelayDueToAccessingFireBuildingType));
            svalSDD.add(env.data.name2ids.get(varSDD).get(F_Strings.DelayDueToAccessingFireSecurity));
            svalSDD.add(env.data.name2ids.get(varSDD).get(F_Strings.DelayDueToLocationOfFireNotImmediatelyEvident));
            Set<Integer> svalSDD2 = new HashSet<>();
            svalSDD2.add(env.data.name2ids.get(varSDD).get(F_Strings.DelayDueToCivilDisturbance));
            svalSDD2.add(env.data.name2ids.get(varSDD).get(F_Strings.DelayDueToAssaultOnFirefighters));
            // CAUSE_OF_FIRE
            int varCOF = env.data.vname2id.get(F_Strings.CAUSE_OF_FIRE);
            Set<Integer> svalCOF = new HashSet<>();
            svalCOF.add(env.data.name2ids.get(varCOF).get(F_Strings.AccumulationOfFlammableMaterial));
            Set<Integer> svalCOF2 = new HashSet<>();
            svalCOF2.add(env.data.name2ids.get(varCOF).get(F_Strings.FaultInEquipmentOrAppliance));
            svalCOF2.add(env.data.name2ids.get(varCOF).get(F_Strings.FaultyFuelSupplyElectricity));
            svalCOF2.add(env.data.name2ids.get(varCOF).get(F_Strings.FaultyFuelSupplyGas));
            svalCOF2.add(env.data.name2ids.get(varCOF).get(F_Strings.FaultyFuelSupplyPetrolProduct));
            svalCOF2.add(env.data.name2ids.get(varCOF).get(F_Strings.FaultyLeadsToEquipmentOrAppliance));
            //svalCOF2.add(env.data.name2ids.get(varCOF).get(F_Strings.OverheatingUnknownCause));
            // ITEM_CAUSING_SPREAD
            int varICS = env.data.vname2id.get(F_Strings.ITEM_CAUSING_SPREAD);
            Set<Integer> svalICS = new HashSet<>();
            svalICS.add(env.data.name2ids.get(varICS).get(F_Strings.RubbishOrWasteOrRecycling));
            Set<Integer> svalICS2 = new HashSet<>();
            svalICS2.add(env.data.name2ids.get(varICS).get(F_Strings.StructuralOrFixturesOrFittings));
            // RAPID_FIRE_GROWTH
            int varRFG = env.data.vname2id.get(F_Strings.RAPID_FIRE_GROWTH);
            int valRFG = env.data.name2ids.get(varRFG).get(F_Strings.Yes);
            // BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION
            int varBSCD = env.data.vname2id.get(F_Strings.BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION);
            Set<Integer> svalBSCD = new HashSet<>();
            svalBSCD.add(env.data.name2ids.get(varBSCD).get(F_Strings.Atria));
            svalBSCD.add(env.data.name2ids.get(varBSCD).get(F_Strings.Cladding));
            svalBSCD.add(env.data.name2ids.get(varBSCD).get(F_Strings.Other));
            svalBSCD.add(env.data.name2ids.get(varBSCD).get(F_Strings.SandwichPanels));
            svalBSCD.add(env.data.name2ids.get(varBSCD).get(F_Strings.TimberFramed));
            // OTHER_PROPERTY_AFFECTED_ON_ARRIVAL
            int varOPAOA = env.data.vname2id.get(F_Strings.OTHER_PROPERTY_AFFECTED_ON_ARRIVAL);
            int valOPAOA = env.data.name2ids.get(varOPAOA).get(F_Strings.AnotherPropertyAffected);
            // other_property_affected_close_d
            int varOPAC = env.data.vname2id.get(F_Strings.other_property_affected_close_d);
            int valOPAC = env.data.name2ids.get(varOPAC).get(F_Strings.AnotherPropertyAffected);
            // FIRE_SIZE_ON_ARRIVAL
            int varFSOA = env.data.vname2id.get(F_Strings.FIRE_SIZE_ON_ARRIVAL);
            Set<Integer> svalFSOAFailure = new HashSet<>();
            svalFSOAFailure.add(env.data.name2ids.get(varFSOA).get(F_Strings.LimitedTo2Floors));
            svalFSOAFailure.add(env.data.name2ids.get(varFSOA).get(F_Strings.WholeBuildingOrAffectingMoreThan2Floors));
            svalFSOAFailure.add(env.data.name2ids.get(varFSOA).get(F_Strings.RoofsOrRoofSpaces));
            Set<Integer> svalFSOASuccess = new HashSet<>();
            svalFSOASuccess.add(env.data.name2ids.get(varFSOA).get(F_Strings.LimitedToFloorOfOrigin));
            svalFSOASuccess.add(env.data.name2ids.get(varFSOA).get(F_Strings.LimitedToItem1stIgnited));
            svalFSOASuccess.add(env.data.name2ids.get(varFSOA).get(F_Strings.LimitedToRoomOfOrigin));
            svalFSOASuccess.add(env.data.name2ids.get(varFSOA).get(F_Strings.NoFireDamage));
            // spread_of_fire_d
            int varSOF = env.data.vname2id.get(F_Strings.spread_of_fire_d);
            Set<Integer> svalSOFFailure = new HashSet<>();
            svalSOFFailure.add(env.data.name2ids.get(varFSOA).get(F_Strings.LimitedTo2Floors));
            svalSOFFailure.add(env.data.name2ids.get(varFSOA).get(F_Strings.WholeBuildingOrAffectingMoreThan2Floors));
            svalSOFFailure.add(env.data.name2ids.get(varFSOA).get(F_Strings.RoofsOrRoofSpaces));
            Set<Integer> svalSOFSuccess = new HashSet<>();
            svalSOFSuccess.add(env.data.name2ids.get(varSOF).get(F_Strings.LimitedToFloorOfOrigin));
            svalSOFSuccess.add(env.data.name2ids.get(varSOF).get(F_Strings.LimitedToItem1stIgnited));
            svalSOFSuccess.add(env.data.name2ids.get(varSOF).get(F_Strings.LimitedToRoomOfOrigin));
            svalSOFSuccess.add(env.data.name2ids.get(varSOF).get(F_Strings.NoFireDamage));
            // RESPONSE_TIME
            int varRT = env.data.vname2id.get(F_Strings.RESPONSE_TIME);
            Set<Integer> svalRTSuccess = new HashSet<>();
            svalRTSuccess.add(env.data.name2ids.get(varRT).get(F_Strings.T1To2mins));
            svalRTSuccess.add(env.data.name2ids.get(varRT).get(F_Strings.T2To3mins));
            svalRTSuccess.add(env.data.name2ids.get(varRT).get(F_Strings.T3To4mins));
            svalRTSuccess.add(env.data.name2ids.get(varRT).get(F_Strings.T4To5mins));
            svalRTSuccess.add(env.data.name2ids.get(varRT).get(F_Strings.T5To6mins));
            svalRTSuccess.add(env.data.name2ids.get(varRT).get(F_Strings.T6To7mins));
            svalRTSuccess.add(env.data.name2ids.get(varRT).get(F_Strings.T7To8mins));
            svalRTSuccess.add(env.data.name2ids.get(varRT).get(F_Strings.T8To9mins));
            svalRTSuccess.add(env.data.name2ids.get(varRT).get(F_Strings.T9To10mins));
            svalRTSuccess.add(env.data.name2ids.get(varRT).get(F_Strings.T10To15mins));
            Set<Integer> svalRTFailure = new HashSet<>();
            svalRTFailure.add(env.data.name2ids.get(varRT).get(F_Strings.T15To20mins));
            svalRTFailure.add(env.data.name2ids.get(varRT).get(F_Strings.T20To60mins));
            // FATALITY_CASUALTY
            int varFC = env.data.vname2id.get(F_Strings.FATALITY_CASUALTY);
            int valFC = env.data.name2ids.get(varFC).get(F_Strings.FatalityOrCasualty);
            // RESCUES
            int varR = env.data.vname2id.get(F_Strings.RESCUES);
            int valR = env.data.name2ids.get(varR).get(F_Strings.P0);
            // EVACUATIONS
            int varE = env.data.vname2id.get(F_Strings.EVACUATIONS);
            int valE = env.data.name2ids.get(varE).get(F_Strings.P0);
            // BUILDING_EVACUATION_DELAY_DESCRIPTION
            int varBEDD = env.data.vname2id.get(F_Strings.BUILDING_EVACUATION_DELAY_DESCRIPTION);
            int valBEDD = env.data.name2ids.get(varBEDD).get(F_Strings.NotApplicable);
            Set<Integer> svalBEDDDanger = new HashSet<>();
            svalBEDDDanger.add(env.data.name2ids.get(varBEDD).get(F_Strings.DelayDueToFirefightingActionsPublic));
            svalBEDDDanger.add(env.data.name2ids.get(varBEDD).get(F_Strings.DelayDueToOccupantsDidNotRespondToAutomaticAlarm));
            svalBEDDDanger.add(env.data.name2ids.get(varBEDD).get(F_Strings.DelayElderlyOrDisabled));
            svalBEDDDanger.add(env.data.name2ids.get(varBEDD).get(F_Strings.DelayDueToReenteredBuilding));
            svalBEDDDanger.add(env.data.name2ids.get(varBEDD).get(F_Strings.DelayDueToFirefightingActionsByFireServiceContraflowOnStairs));
            svalBEDDDanger.add(env.data.name2ids.get(varBEDD).get(F_Strings.DelayDueToMeansOfEscapeOther));
            Set<Integer> svalBEDDFailureManagement = new HashSet<>();
            svalBEDDFailureManagement.add(env.data.name2ids.get(varBEDD).get(F_Strings.DelayDueToBuildingManagementPoor));
            svalBEDDFailureManagement.add(env.data.name2ids.get(varBEDD).get(F_Strings.DelayDueToMeansOfEscapeExitsLocked));
            svalBEDDFailureManagement.add(env.data.name2ids.get(varBEDD).get(F_Strings.DelayDueToMeansOfEscapeItemsStored));
            Set<Integer> svalBEDDFailureConstructionAndManagement = new HashSet<>();
            svalBEDDFailureConstructionAndManagement.add(env.data.name2ids.get(varBEDD).get(F_Strings.DelayDueToBuildingLayoutOrSignagePoor));
            svalBEDDFailureConstructionAndManagement.add(env.data.name2ids.get(varBEDD).get(F_Strings.DelayDueToMeansOfEscapeNotSuitable));

            //Map<String, Integer> tFINANCIAL_YEARVM = env.data.name2ids.get(0);
            int vnameid = env.data.vname2id.size();
            env.data.vname2id.put(F_Strings.FIRE_SAFETY_SUCCESS_SCORE, vnameid);
            env.data.id2vname.put(vnameid, F_Strings.FIRE_SAFETY_SUCCESS_SCORE);
            vnameid = env.data.vname2id.size();
            env.data.vname2id.put(F_Strings.FIRE_SAFETY_FAILURE_SCORE, vnameid);
            env.data.id2vname.put(vnameid, F_Strings.FIRE_SAFETY_FAILURE_SCORE);
            ite = env.data0.data.keySet().iterator();
            while (ite.hasNext()) {
                F_CollectionID cid = ite.next();
                System.out.println("cid=" + cid.toString());
                c0 = env.data0.getCollection(cid);
                if (cid.id == 0) {
                    // Other dwelling types
                    Iterator<F_RecordID> ite2 = c0.data2.keySet().iterator();
                    while (ite2.hasNext()) {
                        F_RecordID recID = ite2.next();
                        F_Dwellings_String_Record2 r = c0.data2.get(recID);
                        Integer year = tFINANCIAL_YEARVM.get(r.gettFINANCIAL_YEAR());
                        cID = new F_CollectionID(year);
                        env.data.cID2recIDs.get(cID).add(recID);
                        F_Dwellings_Integer_Record2 rr = new F_Dwellings_Integer_Record2(r, env.data);
                        rr.initScores0(varMSF, valMSF_Yes, varITD,
                                svalITD, varDTC, svalDTC, varBSSCD,
                                valBSSCD_SOCS, svalBSSCD, varBSSMOED,
                                valBSSMOED_SOCS, svalBSSMOEDDanger, svalBSSMOEDManagement, svalBSSMOEDConstruction,
                                varSDD,
                                valSDD_SOCS, svalSDD, svalSDD2,
                                varCOF, svalCOF, svalCOF2,
                                varICS, svalICS, svalICS2,
                                varRFG, valRFG, varBSCD, svalBSCD,
                                varOPAOA, valOPAOA, varOPAC, valOPAC,
                                varFSOA, svalFSOASuccess, svalFSOAFailure,
                                varSOF, svalSOFSuccess, svalSOFFailure,
                                varRT, svalRTSuccess, svalRTFailure,
                                varFC, valFC,
                                varR, valR, varE, valE, varBEDD, valBEDD,
                                svalBEDDDanger, svalBEDDFailureManagement,
                                svalBEDDFailureConstructionAndManagement);
                        addToCounts0(rr, mFDScore, mFSFScoreManagement,
                                mFSFScoreConstruction, mFSFScoreFireFighting,
                                mCombinedScore, mDelaysToFireFightingScore,
                                mCauseOfFireScore, mFireSpreadScore,
                                mEvacuationScore, mHoleyCheeseScore,
                                mDelaysToFireFightingAndNotFirespreadHoleyCheese);
                        env.data.data.get(cID).data2.put(recID, rr);
                        env.data.recID2cID.put(recID, cID);
                    }
                } else {
                    // Main dwelling types
                    Iterator<F_RecordID> ite2 = c0.data1.keySet().iterator();
                    while (ite2.hasNext()) {
                        F_RecordID recID = ite2.next();
                        F_Dwellings_String_Record1 r = c0.data1.get(recID);
                        Integer year = tFINANCIAL_YEARVM.get(r.gettFINANCIAL_YEAR());
                        cID = new F_CollectionID(year);
                        env.data.cID2recIDs.get(cID).add(recID);
                        F_Dwellings_Integer_Record1 rr = new F_Dwellings_Integer_Record1(r, env.data);
                        rr.initScores(varMSF, valMSF_Yes, varITD,
                                svalITD, varDTC, svalDTC, varBSSCD,
                                valBSSCD_SOCS, svalBSSCD, varBSSMOED,
                                valBSSMOED_SOCS, svalBSSMOEDDanger,
                                svalBSSMOEDManagement, svalBSSMOEDConstruction,
                                varARFPO, svalARFPO,
                                varSDD, valSDD_SOCS, svalSDD, svalSDD2,
                                varCOF, svalCOF, svalCOF2,
                                varICS, svalICS, svalICS2, varRFG, valRFG,
                                varBSCD, svalBSCD, varOPAOA, valOPAOA,
                                varOPAC, valOPAC,
                                varFSOA, svalFSOASuccess, svalFSOAFailure,
                                varSOF, svalSOFSuccess, svalSOFFailure,
                                varRT, svalRTSuccess, svalRTFailure,
                                varFC, valFC, varR, valR, varE,
                                valE, varBEDD, valBEDD, svalBEDDDanger,
                                svalBEDDFailureManagement,
                                svalBEDDFailureConstructionAndManagement);
                        addToCounts0(rr, mFDScore, mFSFScoreManagement,
                                mFSFScoreConstruction, mFSFScoreFireFighting,
                                mCombinedScore, mDelaysToFireFightingScore,
                                mCauseOfFireScore, mFireSpreadScore,
                                mEvacuationScore, mHoleyCheeseScore,
                                mDelaysToFireFightingAndNotFirespreadHoleyCheese);
                        env.data.data.get(cID).data1.put(recID, rr);
                        env.data.recID2cID.put(recID, cID);
                    }
                }
                //env.data2.clearCollection(cid);
                //env.data.cacheCollection(cid, c);
                //env.data.clearCollection(cid);
            }

            env.data0 = null;
            env.cacheData();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(F_Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(F_Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void addToCounts0(F_Dwellings_String_Record0 r,
            HashMap<String, Integer> mFRS_NAME,
            HashMap<String, Integer> mE_CODE,
            HashMap<String, Integer> mFINANCIAL_YEAR,
            HashMap<String, Integer> mMONTH_NAME,
            HashMap<String, Integer> mWEEKDAY_WEEKEND,
            HashMap<String, Integer> mDAY_NIGHT,
            HashMap<String, Integer> mBUILDING_OR_PROPERTY_TYPE,
            HashMap<String, Integer> mLATE_CALL,
            HashMap<String, Integer> mMULTI_SEATED_FLAG,
            HashMap<String, Integer> mIGNITION_TO_DISCOVERY,
            HashMap<String, Integer> mDISCOVERY_TO_CALL,
            HashMap<String, Integer> mHOW_DISCOVERED_DESCRIPTION,
            HashMap<String, Integer> mBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION,
            HashMap<String, Integer> mBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION,
            HashMap<String, Integer> mBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT,
            HashMap<String, Integer> mOCCUPIED_NORMAL,
            HashMap<String, Integer> mACCIDENTAL_OR_DELIBERATE,
            HashMap<String, Integer> mVEHICLES,
            HashMap<String, Integer> mVEHICLES_CODE,
            HashMap<String, Integer> mPERSONNEL,
            HashMap<String, Integer> mPERSONNEL_CODE,
            HashMap<String, Integer> mSTARTING_DELAY_DESCRIPTION,
            HashMap<String, Integer> mACTION_NON_FRS_DESCRIPTION,
            HashMap<String, Integer> mACTION_FRS_DESCRIPTION,
            HashMap<String, Integer> mCAUSE_OF_FIRE,
            HashMap<String, Integer> mIGNITION_POWER,
            HashMap<String, Integer> mSOURCE_OF_IGNITION,
            HashMap<String, Integer> mITEM_IGNITED,
            HashMap<String, Integer> mITEM_CAUSING_SPREAD,
            HashMap<String, Integer> mRAPID_FIRE_GROWTH,
            HashMap<String, Integer> mCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION,
            HashMap<String, Integer> mCAUSE_EXPLOSION_INVOLVED,
            HashMap<String, Integer> mCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION,
            HashMap<String, Integer> mCAUSE_EXPLOSION_STAGE_DESCRIPTION,
            HashMap<String, Integer> mCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION,
            HashMap<String, Integer> mBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION,
            HashMap<String, Integer> mBUILDING_FLOORS_ABOVE_GROUND,
            HashMap<String, Integer> mBUILDING_FLOORS_BELOW_GROUND,
            HashMap<String, Integer> mBUILDING_FLOOR_ORIGIN,
            HashMap<String, Integer> mBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION,
            HashMap<String, Integer> mBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION,
            HashMap<String, Integer> mFIRE_START_LOCATION,
            HashMap<String, Integer> mFIRE_SIZE_ON_ARRIVAL,
            HashMap<String, Integer> mOTHER_PROPERTY_AFFECTED_ON_ARRIVAL,
            HashMap<String, Integer> mBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION,
            HashMap<String, Integer> mBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION,
            HashMap<String, Integer> mFIRE_SIZE_ON_ARRIVAL_DESCRIPTION,
            HashMap<String, Integer> mother_property_affected_close_d,
            HashMap<String, Integer> mspread_of_fire_d,
            HashMap<String, Integer> mRESPONSE_TIME,
            HashMap<String, Integer> mRESPONSE_TIME_CODE,
            HashMap<String, Integer> mTIME_AT_SCENE,
            HashMap<String, Integer> mTIME_AT_SCENE_CODE,
            HashMap<String, Integer> mRESCUES,
            HashMap<String, Integer> mEVACUATIONS,
            HashMap<String, Integer> mEVACUATIONS_CODE,
            HashMap<String, Integer> mBUILDING_EVACUATION_DELAY_DESCRIPTION,
            HashMap<String, Integer> mBUILDING_EVACUATION_TIME_DESCRIPTION
    ) {
        Generic_Collections.addToCount(mFRS_NAME, r.gettFRS_NAME(), 1);
        Generic_Collections.addToCount(mE_CODE, r.gettE_CODE(), 1);
        Generic_Collections.addToCount(mFINANCIAL_YEAR, r.gettFINANCIAL_YEAR(), 1);
        Generic_Collections.addToCount(mMONTH_NAME, r.gettMONTH_NAME(), 1);
        Generic_Collections.addToCount(mWEEKDAY_WEEKEND, r.gettWEEKDAY_WEEKEND(), 1);
        Generic_Collections.addToCount(mDAY_NIGHT, r.gettDAY_NIGHT(), 1);
        Generic_Collections.addToCount(mLATE_CALL, r.gettLATE_CALL(), 1);
        Generic_Collections.addToCount(mBUILDING_OR_PROPERTY_TYPE, r.gettBUILDING_OR_PROPERTY_TYPE(), 1);
        Generic_Collections.addToCount(mMULTI_SEATED_FLAG, r.gettMULTI_SEATED_FLAG(), 1);
        Generic_Collections.addToCount(mIGNITION_TO_DISCOVERY, r.gettIGNITION_TO_DISCOVERY(), 1);
        Generic_Collections.addToCount(mDISCOVERY_TO_CALL, r.gettDISCOVERY_TO_CALL(), 1);
        Generic_Collections.addToCount(mHOW_DISCOVERED_DESCRIPTION, r.gettHOW_DISCOVERED_DESCRIPTION(), 1);
        Generic_Collections.addToCount(mBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION, r.gettBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION(), 1);
        Generic_Collections.addToCount(mBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION, r.gettBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION(), 1);
        Generic_Collections.addToCount(mBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT, r.gettBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT(), 1);
        Generic_Collections.addToCount(mOCCUPIED_NORMAL, r.gettOCCUPIED_NORMAL(), 1);
        Generic_Collections.addToCount(mACCIDENTAL_OR_DELIBERATE, r.gettACCIDENTAL_OR_DELIBERATE(), 1);
        Generic_Collections.addToCount(mVEHICLES, r.gettVEHICLES(), 1);
        Generic_Collections.addToCount(mVEHICLES_CODE, r.gettVEHICLES_CODE(), 1);
        Generic_Collections.addToCount(mPERSONNEL, r.gettPERSONNEL(), 1);
        Generic_Collections.addToCount(mPERSONNEL_CODE, r.gettPERSONNEL_CODE(), 1);
        Generic_Collections.addToCount(mSTARTING_DELAY_DESCRIPTION, r.gettSTARTING_DELAY_DESCRIPTION(), 1);
        Generic_Collections.addToCount(mACTION_NON_FRS_DESCRIPTION, r.gettACTION_NON_FRS_DESCRIPTION(), 1);
        Generic_Collections.addToCount(mACTION_FRS_DESCRIPTION, r.gettACTION_FRS_DESCRIPTION(), 1);
        Generic_Collections.addToCount(mCAUSE_OF_FIRE, r.gettCAUSE_OF_FIRE(), 1);
        Generic_Collections.addToCount(mIGNITION_POWER, r.gettIGNITION_POWER(), 1);
        Generic_Collections.addToCount(mSOURCE_OF_IGNITION, r.gettSOURCE_OF_IGNITION(), 1);
        Generic_Collections.addToCount(mITEM_IGNITED, r.gettITEM_IGNITED(), 1);
        Generic_Collections.addToCount(mITEM_CAUSING_SPREAD, r.gettITEM_CAUSING_SPREAD(), 1);
        Generic_Collections.addToCount(mRAPID_FIRE_GROWTH, r.gettRAPID_FIRE_GROWTH(), 1);
        Generic_Collections.addToCount(mCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION, r.gettCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION(), 1);
        Generic_Collections.addToCount(mCAUSE_EXPLOSION_INVOLVED, r.gettCAUSE_EXPLOSION_INVOLVED(), 1);
        Generic_Collections.addToCount(mCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION, r.gettCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION(), 1);
        Generic_Collections.addToCount(mCAUSE_EXPLOSION_STAGE_DESCRIPTION, r.gettCAUSE_EXPLOSION_STAGE_DESCRIPTION(), 1);
        Generic_Collections.addToCount(mCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION, r.gettCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION(), 1);
        Generic_Collections.addToCount(mBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION, r.gettBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION(), 1);
        Generic_Collections.addToCount(mBUILDING_FLOORS_ABOVE_GROUND, r.gettBUILDING_FLOORS_ABOVE_GROUND(), 1);
        Generic_Collections.addToCount(mBUILDING_FLOORS_BELOW_GROUND, r.gettBUILDING_FLOORS_BELOW_GROUND(), 1);
        Generic_Collections.addToCount(mBUILDING_FLOOR_ORIGIN, r.gettBUILDING_FLOOR_ORIGIN(), 1);
        Generic_Collections.addToCount(mBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION, r.gettBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION(), 1);
        Generic_Collections.addToCount(mBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION, r.gettBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION(), 1);
        Generic_Collections.addToCount(mFIRE_START_LOCATION, r.gettFIRE_START_LOCATION(), 1);
        Generic_Collections.addToCount(mFIRE_SIZE_ON_ARRIVAL, r.gettFIRE_SIZE_ON_ARRIVAL(), 1);
        Generic_Collections.addToCount(mOTHER_PROPERTY_AFFECTED_ON_ARRIVAL, r.gettOTHER_PROPERTY_AFFECTED_ON_ARRIVAL(), 1);
        Generic_Collections.addToCount(mBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION, r.gettBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION(), 1);
        Generic_Collections.addToCount(mBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION, r.gettBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION(), 1);
        Generic_Collections.addToCount(mFIRE_SIZE_ON_ARRIVAL_DESCRIPTION, r.gettFIRE_SIZE_ON_ARRIVAL_DESCRIPTION(), 1);
        Generic_Collections.addToCount(mother_property_affected_close_d, r.gettother_property_affected_close_d(), 1);
        Generic_Collections.addToCount(mspread_of_fire_d, r.gettspread_of_fire_d(), 1);
        Generic_Collections.addToCount(mRESPONSE_TIME, r.gettRESPONSE_TIME(), 1);
        Generic_Collections.addToCount(mRESPONSE_TIME_CODE, r.gettRESPONSE_TIME_CODE(), 1);
        Generic_Collections.addToCount(mTIME_AT_SCENE, r.gettTIME_AT_SCENE(), 1);
        Generic_Collections.addToCount(mTIME_AT_SCENE_CODE, r.gettTIME_AT_SCENE_CODE(), 1);
        Generic_Collections.addToCount(mRESCUES, r.gettRESCUES(), 1);
        Generic_Collections.addToCount(mEVACUATIONS, r.gettEVACUATIONS(), 1);
        Generic_Collections.addToCount(mEVACUATIONS_CODE, r.gettEVACUATIONS_CODE(), 1);
        Generic_Collections.addToCount(mBUILDING_EVACUATION_DELAY_DESCRIPTION, r.gettBUILDING_EVACUATION_DELAY_DESCRIPTION(), 1);
        Generic_Collections.addToCount(mBUILDING_EVACUATION_TIME_DESCRIPTION, r.gettBUILDING_EVACUATION_TIME_DESCRIPTION(), 1);
    }

    protected void addToCounts0(F_Dwellings_Integer_Record0 r,
            HashMap<Integer, Integer> mFDScore,
            HashMap<Integer, Integer> mFSFScoreManagement,
            HashMap<Integer, Integer> mFSFScoreConstruction,
            HashMap<Integer, Integer> mFSFScoreFireFighting,
            HashMap<Integer, Integer> mCombinedScore,
            HashMap<Integer, Integer> mDelaysToFireFightingScore,
            HashMap<Integer, Integer> mCauseOfFireScore,
            HashMap<Integer, Integer> mFireSpreadScore,
            HashMap<Integer, Integer> mEvacuationScore,
            HashMap<Integer, Integer> mHoleyCheeseScore,
            HashMap<Integer, Integer> mDelaysToFireFightingAndNotFirespreadHoleyCheese) {
        Generic_Collections.addToCount(mFDScore, r.fDScore, 1);
        Generic_Collections.addToCount(mFSFScoreManagement, r.fSFScoreManagement, 1);
        Generic_Collections.addToCount(mFSFScoreConstruction, r.fSFScoreConstruction, 1);
        Generic_Collections.addToCount(mFSFScoreFireFighting, r.fSFScoreFireFighting, 1);
        Generic_Collections.addToCount(mCombinedScore, r.combinedScore, 1);
        Generic_Collections.addToCount(mDelaysToFireFightingScore, r.delaysToFireFightingScore, 1);
        Generic_Collections.addToCount(mCauseOfFireScore, r.causeOfFireScore, 1);
        Generic_Collections.addToCount(mFireSpreadScore, r.fireSpreadScore, 1);
        Generic_Collections.addToCount(mEvacuationScore, r.evacuationScore, 1);
        Generic_Collections.addToCount(mHoleyCheeseScore, r.holeyCheese, 1);
        Generic_Collections.addToCount(mDelaysToFireFightingAndNotFirespreadHoleyCheese, r.delaysToFireFightingAndNotFirespreadHoleyCheese, 1);
    }

    protected void addToCounts0(F_Dwellings_Integer_Record0 r,
            HashMap<Integer, Integer> mFRS_NAME,
            HashMap<Integer, Integer> mE_CODE,
            HashMap<Integer, Integer> mMONTH_NAME,
            HashMap<Integer, Integer> mWEEKDAY_WEEKEND,
            HashMap<Integer, Integer> mDAY_NIGHT,
            HashMap<Integer, Integer> mBUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mLATE_CALL,
            HashMap<Integer, Integer> mMULTI_SEATED_FLAG,
            HashMap<Integer, Integer> mIGNITION_TO_DISCOVERY,
            HashMap<Integer, Integer> mDISCOVERY_TO_CALL,
            HashMap<Integer, Integer> mHOW_DISCOVERED_DESCRIPTION,
            HashMap<Integer, Integer> mBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION,
            HashMap<Integer, Integer> mBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION,
            HashMap<Integer, Integer> mBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT,
            HashMap<Integer, Integer> mOCCUPIED_NORMAL,
            HashMap<Integer, Integer> mACCIDENTAL_OR_DELIBERATE,
            HashMap<Integer, Integer> mVEHICLES,
            HashMap<Integer, Integer> mVEHICLES_CODE,
            HashMap<Integer, Integer> mPERSONNEL,
            HashMap<Integer, Integer> mPERSONNEL_CODE,
            HashMap<Integer, Integer> mSTARTING_DELAY_DESCRIPTION,
            HashMap<Integer, Integer> mACTION_NON_FRS_DESCRIPTION,
            HashMap<Integer, Integer> mACTION_FRS_DESCRIPTION,
            HashMap<Integer, Integer> mCAUSE_OF_FIRE,
            HashMap<Integer, Integer> mIGNITION_POWER,
            HashMap<Integer, Integer> mSOURCE_OF_IGNITION,
            HashMap<Integer, Integer> mITEM_IGNITED,
            HashMap<Integer, Integer> mITEM_CAUSING_SPREAD,
            HashMap<Integer, Integer> mRAPID_FIRE_GROWTH,
            HashMap<Integer, Integer> mCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION,
            HashMap<Integer, Integer> mCAUSE_EXPLOSION_INVOLVED,
            HashMap<Integer, Integer> mCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION,
            HashMap<Integer, Integer> mCAUSE_EXPLOSION_STAGE_DESCRIPTION,
            HashMap<Integer, Integer> mCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION,
            HashMap<Integer, Integer> mBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION,
            HashMap<Integer, Integer> mBUILDING_FLOORS_ABOVE_GROUND,
            HashMap<Integer, Integer> mBUILDING_FLOORS_BELOW_GROUND,
            HashMap<Integer, Integer> mBUILDING_FLOOR_ORIGIN,
            HashMap<Integer, Integer> mBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION,
            HashMap<Integer, Integer> mBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION,
            HashMap<Integer, Integer> mFIRE_START_LOCATION,
            HashMap<Integer, Integer> mFIRE_SIZE_ON_ARRIVAL,
            HashMap<Integer, Integer> mOTHER_PROPERTY_AFFECTED_ON_ARRIVAL,
            HashMap<Integer, Integer> mBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION,
            HashMap<Integer, Integer> mBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION,
            HashMap<Integer, Integer> mFIRE_SIZE_ON_ARRIVAL_DESCRIPTION,
            HashMap<Integer, Integer> mother_property_affected_close_d,
            HashMap<Integer, Integer> mspread_of_fire_d,
            HashMap<Integer, Integer> mRESPONSE_TIME,
            HashMap<Integer, Integer> mRESPONSE_TIME_CODE,
            HashMap<Integer, Integer> mTIME_AT_SCENE,
            HashMap<Integer, Integer> mTIME_AT_SCENE_CODE,
            HashMap<Integer, Integer> mRESCUES,
            HashMap<Integer, Integer> mEVACUATIONS,
            HashMap<Integer, Integer> mEVACUATIONS_CODE,
            HashMap<Integer, Integer> mBUILDING_EVACUATION_DELAY_DESCRIPTION,
            HashMap<Integer, Integer> mBUILDING_EVACUATION_TIME_DESCRIPTION) {
        Generic_Collections.addToCount(mFRS_NAME, r.tFRS_NAME, 1);
        Generic_Collections.addToCount(mE_CODE, r.tE_CODE, 1);
        Generic_Collections.addToCount(mMONTH_NAME, r.tMONTH_NAME, 1);
        Generic_Collections.addToCount(mWEEKDAY_WEEKEND, r.tWEEKDAY_WEEKEND, 1);
        Generic_Collections.addToCount(mDAY_NIGHT, r.tDAY_NIGHT, 1);
        Generic_Collections.addToCount(mLATE_CALL, r.tLATE_CALL, 1);
        Generic_Collections.addToCount(mBUILDING_OR_PROPERTY_TYPE, r.tBUILDING_OR_PROPERTY_TYPE, 1);
        Generic_Collections.addToCount(mMULTI_SEATED_FLAG, r.tMULTI_SEATED_FLAG, 1);
        Generic_Collections.addToCount(mIGNITION_TO_DISCOVERY, r.tIGNITION_TO_DISCOVERY, 1);
        Generic_Collections.addToCount(mDISCOVERY_TO_CALL, r.tDISCOVERY_TO_CALL, 1);
        Generic_Collections.addToCount(mHOW_DISCOVERED_DESCRIPTION, r.tHOW_DISCOVERED_DESCRIPTION, 1);
        Generic_Collections.addToCount(mBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION, r.tBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION, 1);
        Generic_Collections.addToCount(mBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION, r.tBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION, 1);
        Generic_Collections.addToCount(mBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT, r.tBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT, 1);
        Generic_Collections.addToCount(mOCCUPIED_NORMAL, r.tOCCUPIED_NORMAL, 1);
        Generic_Collections.addToCount(mACCIDENTAL_OR_DELIBERATE, r.tACCIDENTAL_OR_DELIBERATE, 1);
        Generic_Collections.addToCount(mVEHICLES, r.tVEHICLES, 1);
        Generic_Collections.addToCount(mVEHICLES_CODE, r.tVEHICLES_CODE, 1);
        Generic_Collections.addToCount(mPERSONNEL, r.tPERSONNEL, 1);
        Generic_Collections.addToCount(mPERSONNEL_CODE, r.tPERSONNEL_CODE, 1);
        Generic_Collections.addToCount(mSTARTING_DELAY_DESCRIPTION, r.tSTARTING_DELAY_DESCRIPTION, 1);
        Generic_Collections.addToCount(mACTION_NON_FRS_DESCRIPTION, r.tACTION_NON_FRS_DESCRIPTION, 1);
        Generic_Collections.addToCount(mACTION_FRS_DESCRIPTION, r.tACTION_FRS_DESCRIPTION, 1);
        Generic_Collections.addToCount(mCAUSE_OF_FIRE, r.tCAUSE_OF_FIRE, 1);
        Generic_Collections.addToCount(mIGNITION_POWER, r.tIGNITION_POWER, 1);
        Generic_Collections.addToCount(mSOURCE_OF_IGNITION, r.tSOURCE_OF_IGNITION, 1);
        Generic_Collections.addToCount(mITEM_IGNITED, r.tITEM_IGNITED, 1);
        Generic_Collections.addToCount(mITEM_CAUSING_SPREAD, r.tITEM_CAUSING_SPREAD, 1);
        Generic_Collections.addToCount(mRAPID_FIRE_GROWTH, r.tRAPID_FIRE_GROWTH, 1);
        Generic_Collections.addToCount(mCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION, r.tCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION, 1);
        Generic_Collections.addToCount(mCAUSE_EXPLOSION_INVOLVED, r.tCAUSE_EXPLOSION_INVOLVED, 1);
        Generic_Collections.addToCount(mCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION, r.tCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION, 1);
        Generic_Collections.addToCount(mCAUSE_EXPLOSION_STAGE_DESCRIPTION, r.tCAUSE_EXPLOSION_STAGE_DESCRIPTION, 1);
        Generic_Collections.addToCount(mCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION, r.tCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION, 1);
        Generic_Collections.addToCount(mBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION, r.tBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION, 1);
        Generic_Collections.addToCount(mBUILDING_FLOORS_ABOVE_GROUND, r.tBUILDING_FLOORS_ABOVE_GROUND, 1);
        Generic_Collections.addToCount(mBUILDING_FLOORS_BELOW_GROUND, r.tBUILDING_FLOORS_BELOW_GROUND, 1);
        Generic_Collections.addToCount(mBUILDING_FLOOR_ORIGIN, r.tBUILDING_FLOOR_ORIGIN, 1);
        Generic_Collections.addToCount(mBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION, r.tBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION, 1);
        Generic_Collections.addToCount(mBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION, r.tBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION, 1);
        Generic_Collections.addToCount(mFIRE_START_LOCATION, r.tFIRE_START_LOCATION, 1);
        Generic_Collections.addToCount(mFIRE_SIZE_ON_ARRIVAL, r.tFIRE_SIZE_ON_ARRIVAL, 1);
        Generic_Collections.addToCount(mOTHER_PROPERTY_AFFECTED_ON_ARRIVAL, r.tOTHER_PROPERTY_AFFECTED_ON_ARRIVAL, 1);
        Generic_Collections.addToCount(mBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION, r.tBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION, 1);
        Generic_Collections.addToCount(mBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION, r.tBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION, 1);
        Generic_Collections.addToCount(mFIRE_SIZE_ON_ARRIVAL_DESCRIPTION, r.tFIRE_SIZE_ON_ARRIVAL_DESCRIPTION, 1);
        Generic_Collections.addToCount(mother_property_affected_close_d, r.tother_property_affected_close_d, 1);
        Generic_Collections.addToCount(mspread_of_fire_d, r.tspread_of_fire_d, 1);
        Generic_Collections.addToCount(mRESPONSE_TIME, r.tRESPONSE_TIME, 1);
        Generic_Collections.addToCount(mRESPONSE_TIME_CODE, r.tRESPONSE_TIME_CODE, 1);
        Generic_Collections.addToCount(mTIME_AT_SCENE, r.tTIME_AT_SCENE, 1);
        Generic_Collections.addToCount(mTIME_AT_SCENE_CODE, r.tTIME_AT_SCENE_CODE, 1);
        Generic_Collections.addToCount(mRESCUES, r.tRESCUES, 1);
        Generic_Collections.addToCount(mEVACUATIONS, r.tEVACUATIONS, 1);
        Generic_Collections.addToCount(mEVACUATIONS_CODE, r.tEVACUATIONS_CODE, 1);
        Generic_Collections.addToCount(mBUILDING_EVACUATION_DELAY_DESCRIPTION, r.tBUILDING_EVACUATION_DELAY_DESCRIPTION, 1);
        Generic_Collections.addToCount(mBUILDING_EVACUATION_TIME_DESCRIPTION, r.tBUILDING_EVACUATION_TIME_DESCRIPTION, 1);
    }

    public String getFormatted(String s) {
        return s;
//        return s.trim().replaceAll("\\s+", "_").replaceAll("/", "_OR_")
//                .replaceAll("[^A-Za-z0-9 ]", "_");
    }

    /**
     * Prints out all things in {@code m} to std.out.
     *
     * @param <T> The type.
     * @param s The set containing values to output out.
     */
    public <K> void print(String name, Map<K, Integer> m) {
        System.out.println(name + " Table length=" + m.size());
        System.out.println("count,value");
        m.keySet().stream().forEach(i -> System.out.println("" + m.get(i) + "," + i));
        System.out.println("");
    }

    /**
     * Prints out all things in {@code m} to std.out.
     *
     * @param dirname The directory name for the output.
     * @param name The name of the thing to output out - this will be the start
     * of the filename.
     * @param <K> The type.
     * @param s The set containing values to output out.
     */
    public <K, V> void output(Path dir, String name0, String name1, int total, Map<K, V> m) {
        try {
            Path p = Paths.get(dir.toString(), F_Files.getFormattedFilenameString(name0 + " " + name1) + ".csv");
            System.out.println("output " + name1);
            try (PrintWriter pw = new PrintWriter(p.toFile())) {
                //pw.println(name + " Table length=" + m.size());
                pw.println("\"" + name1 + "\",count");
                //m.keySet().stream().forEach(i -> pw.println("\"" + i + "\",\"" + m.get(i) + "\""));
                m.keySet().stream().forEach(i -> pw.println("\"" + i + "\"," + m.get(i)));
                pw.println("\"total\"," + total);
            }
        } catch (IOException ex) {
            Logger.getLogger(F_Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Prints out all things in {@code s} to std.out.
     *
     * @param dirname The directory name for the output.
     * @param name The name of the thing to output out - this will be the start
     * of the filename.
     * @param s The set containing values to output out.
     */
    public <T extends F_Dwellings_Integer_Record0> void output(Path dir,
            String name0, String name1, Set<T> s) {
        try {
            Path p = Paths.get(dir.toString(),
                    F_Files.getFormattedFilenameString(name0 + " " + name1) + ".csv");
            System.out.println("output " + name1);
            try (PrintWriter pw = new PrintWriter(p.toFile())) {
                Iterator<T> ite = s.iterator();
                if (ite.hasNext()) {
                    pw.println(getHeader(s.iterator().next()));
                }
                s.stream().forEach(i -> pw.println(i.toCSVString(env.data)));
            }
        } catch (IOException ex) {
            Logger.getLogger(F_Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected String getHeader(F_Dwellings_Integer_Record0 i) {
        String r;
        if (i instanceof F_Dwellings_Integer_Record1) {
            r = ((F_Dwellings_Integer_Record1) i).getCSVHeader();
        } else {
            r = ((F_Dwellings_Integer_Record2) i).getCSVHeader();
        }
        return r;
    }

    /**
     * Prints out all things in {@code m} to std.out.
     *
     * @param dirname The directory name for the output.
     * @param name0 Prepend to filename.
     * @param name1 The name of the thing to output out - this will be the start
     * of the filename.
     * @param <K> The type.
     * @param s The set containing values to output out.
     */
    public void output(Path dir, String name0, String name1, Integer vid,
            Map<Integer, Integer> m, Map<Integer, Integer> mfc) {
        output(dir, name0, name1, name1, vid, m, mfc);
    }

    /**
     * Collects all the values
     */
    public <K0, K1, V extends Number> TreeMap<K1, V> collectCounts(
            Map<K0, Map<K1, V>> m) {
        if (m.isEmpty()) {
            return null;
        } else {
            TreeMap<K1, V> r = new TreeMap<>();
            Iterator<K0> ite = m.keySet().iterator();
            K0 k0 = ite.next();
            r.putAll(m.get(k0));
            while (ite.hasNext()) {
                k0 = ite.next();
                Generic_Collections.addToCount(r, m.get(k0));
            }
            return r;
        }
    }

    /**
     * Prints out all things in {@code m} to std.out.
     *
     * @param dirname The directory name for the output.
     * @param name0 Prepend to filename.
     * @param name1 The name of the thing to output out - this will be the start
     * of the filename.
     * @param <K> The type.
     * @param s The set containing values to output out.
     */
    public void output(Path dir, String name0, String name1, String vname,
            Integer vid, Map<Integer, Integer> m, Map<Integer, Integer> mfc) {
        try {
            Map<Integer, String> id2name = env.data.id2names.get(vid);
            HashMap<String, Integer> name2id = env.data.name2ids.get(vid);
            TreeMap<String, Integer> m2 = new TreeMap<>();
            Iterator<Integer> ite = m.keySet().iterator();
            while (ite.hasNext()) {
                Integer id = ite.next();
                if (id == null) {
                    System.out.println("id == null in output " + name1);
                } else {
                    m2.put(id2name.get(id), m.get(id));
                }
            }
            Path p = Paths.get(dir.toString(), F_Files.getFormattedFilenameString(name0 + " " + name1) + ".csv");
            System.out.println("output " + name1);
            try (PrintWriter pw = new PrintWriter(p.toFile())) {
                //pw.println(name + " Table length=" + m.size());
                pw.println("\"" + vname + "\",\"" + vname + "_ID\",Count,FATAL_CASUALTY_Count,%FATAL_CASUALTY");
                //m.keySet().stream().forEach(i -> pw.println("\"" + i + "\",\"" + m.get(i) + "\""));
                Iterator<String> ites = m2.keySet().iterator();
                while (ites.hasNext()) {
                    String s = ites.next();
                    Integer k = name2id.get(s);
                    Integer v = m.get(k);
                    Integer vfc = mfc.get(k);
                    double percentagefc;
                    if (vfc == null) {
                        vfc = 0;
                        percentagefc = 0;
                    } else {
                        percentagefc = (100.0d * vfc) / ((double) v);
                    }
                    pw.println("\"" + s + "\",\"" + k.toString() + "\"," + v
                            + "," + vfc + "," + Double.toString(percentagefc));
                }
                //m.keySet().stream().forEach(i -> pw.println("\"" + i + "\"," + m.get(i)));
            }
        } catch (IOException ex) {
            Logger.getLogger(F_Main.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Prints out all things in {@code m} to std.out.
     *
     * @param dirname The directory name for the output.
     * @param name0 Prepend to filename.
     * @param name1 The name of the thing to output out - this will be the start
     * of the filename.
     * @param <K> The type.
     * @param s The set containing values to output out.
     */
    public void outputScores(Path dir, String name0, String vname,
            Integer vid, Map<Integer, Integer> m, Map<Integer, Integer> mfc) {
        try {
            Path p = Paths.get(dir.toString(), F_Files.getFormattedFilenameString(name0 + " " + vname) + ".csv");
            System.out.println("output " + vname);
            try (PrintWriter pw = new PrintWriter(p.toFile())) {
                //pw.println(name + " Table length=" + m.size());
                pw.println("\"" + vname + "\",Count,FATAL_CASUALTY_Count,%FATAL_CASUALTY");
                //m.keySet().stream().forEach(i -> pw.println("\"" + i + "\",\"" + m.get(i) + "\""));
                Iterator<Integer> ites = m.keySet().iterator();
                while (ites.hasNext()) {
                    Integer k = ites.next();
                    Integer v = m.get(k);
                    Integer vfc = mfc.get(k);
                    double percentagefc;
                    if (vfc == null) {
                        vfc = 0;
                        percentagefc = 0;
                    } else {
                        percentagefc = (100.0d * vfc) / ((double) v);
                    }
                    pw.println("\"" + k.toString() + "\"," + v
                            + "," + vfc + "," + Double.toString(percentagefc));
                }
                //m.keySet().stream().forEach(i -> pw.println("\"" + i + "\"," + m.get(i)));
            }
        } catch (IOException ex) {
            Logger.getLogger(F_Main.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Prints out all things in {@code m} to std.out.
     *
     * @param dirname The directory name for the output.
     * @param name0 First part of the name of the thing to output out - this
     * will be the start of the filename. the values in {@code m}.
     * @param name1 Second part of the name of the thing to output out - this
     * will be the start of the filename. the values in {@code m}.
     * @param vid The id of the variable.
     * @param m Keys are variable value IDs, values are counts.
     */
    public void output(Path dir, String name0, String name1, int vid, int total,
            Map<Integer, Integer> m) {
        Map<Integer, String> id2name = env.data.id2names.get(vid);
        TreeMap<String, Integer> r = new TreeMap<>();
        Iterator<Integer> ite = m.keySet().iterator();
        while (ite.hasNext()) {
            Integer id = ite.next();
            if (id == null) {
                System.out.println("id == null in output " + name1);
            } else {
                r.put(id2name.get(id), m.get(id));
            }
        }
        output(dir, name0, name1, total, r);
    }

    /**
     * This creates some look ups or indexes for the keys of m.
     *
     * @param s The map that's keys are saved in look ups.
     * @param vname The name given to the lookups.
     */
    public void createLookups(Set<String> s, String vname) {
        int vnameid = env.data.vname2id.size();
        env.data.vname2id.put(vname, vnameid);
        env.data.id2vname.put(vnameid, vname);
        HashMap<String, Integer> name2id = new HashMap<>();
        HashMap<Integer, String> id2name = new HashMap<>();
        Iterator<String> ite = s.iterator();
        while (ite.hasNext()) {
            String k = ite.next();
            int id = name2id.size();
            name2id.put(k, id);
            id2name.put(id, k);
        }
        env.data.name2ids.put(vnameid, name2id);
        env.data.id2names.put(vnameid, id2name);
    }

    /**
     * This is the main processor for generating results once the source data
     * get loaded.
     */
    public void process() {
        try {
            String yearsAll = "2010/20";
            System.out.println("env.data.cID2recIDs.size()=" + env.data.cID2recIDs.size());
            System.out.println("env.data.recID2cID.size()=" + env.data.recID2cID.size());
            System.out.println("env.data.data.size()=" + env.data.data.size());

            // ---------------
            // Single variable
            // ---------------
            String name = "SingleVariableSummaries";
//            int varFIRE_SAFETY_SUCCESS_SCORE = env.data.vname2id.size();
//            env.data.vname2id.put(F_Strings.fSSScore, varFIRE_SAFETY_SUCCESS_SCORE);
//            env.data.id2vname.put(varFIRE_SAFETY_SUCCESS_SCORE, F_Strings.fSSScore);
//            env.data.id2names.put(varFIRE_SAFETY_SUCCESS_SCORE, mFIRE_SAFETY_SUCCESS_SCORE);
//            env.data.name2ids.put(varFIRE_SAFETY_SUCCESS_SCORE, mFIRE_SAFETY_SUCCESS_SCORE);
//            int varFIRE_SAFETY_FAILURE_SCORE = env.data.vname2id.size();
//            env.data.vname2id.put(F_Strings.fSFScore, varFIRE_SAFETY_FAILURE_SCORE);
//            env.data.id2vname.put(varFIRE_SAFETY_FAILURE_SCORE, F_Strings.fSFScore);
//            env.data.id2names.put(varFIRE_SAFETY_FAILURE_SCORE, mFIRE_SAFETY_FAILURE_SCORE);
//            env.data.name2ids.put(varFIRE_SAFETY_FAILURE_SCORE, mFIRE_SAFETY_FAILURE_SCORE);
            outputSingleVariableCountSummaries(name);

            // --------------
            // More variables
            // --------------
            // SelectedCountSummaries
            // ----------------------
            name = "SelectedCountSummaries";
            // BUILDING_OR_PROPERTY_TYPE
            int varBUILDING_OR_PROPERTY_TYPE = env.data.vname2id.get(F_Strings.BUILDING_OR_PROPERTY_TYPE);
            HashSet<Integer> sPurposeBuiltTypes = new HashSet<>();
            sPurposeBuiltTypes.add(env.data.name2ids.get(varBUILDING_OR_PROPERTY_TYPE).get(F_Strings.PurposeBuiltHighRiseFlats));
            sPurposeBuiltTypes.add(env.data.name2ids.get(varBUILDING_OR_PROPERTY_TYPE).get(F_Strings.PurposeBuiltMediumRiseFlats));
            sPurposeBuiltTypes.add(env.data.name2ids.get(varBUILDING_OR_PROPERTY_TYPE).get(F_Strings.PurposeBuiltLowRiseFlats));

            // FATALITY_CASUALTY
            int varFATALITY_CASUALTY = env.data.vname2id.get(F_Strings.FATALITY_CASUALTY);
            int valFATALITY_CASUALTY = env.data.name2ids.get(varFATALITY_CASUALTY).get(F_Strings.FatalityOrCasualty);
            // DAY_NIGHT
            int varDAY_NIGHT = env.data.vname2id.get(F_Strings.DAY_NIGHT);
            int valNight = env.data.name2ids.get(varDAY_NIGHT).get(
                    F_Strings.Night);
            // BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION
            int varBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION = env.data.vname2id.get(
                    F_Strings.BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION);
            int valNoCompartmentationInBuilding = env.data.name2ids.get(
                    varBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION).get(
                            F_Strings.NoCompartmentationInBuilding);
            // BUILDING_FLOOR_ORIGIN
            //int varBUILDING_FLOOR_ORIGIN = env.data.vname2id.get(F_Strings.BUILDING_FLOOR_ORIGIN);
            // spread_of_fire_d
            int varspread_of_fire_d = env.data.vname2id.get(F_Strings.spread_of_fire_d);
            int valWholeBuildingOrAffectingMoreThan2Floors = env.data.name2ids.get(
                    varspread_of_fire_d).get(
                            F_Strings.WholeBuildingOrAffectingMoreThan2Floors);
            // BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION
            int varBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION = env.data.vname2id.get(
                    F_Strings.BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION);
            int valCladding = env.data.name2ids.get(varBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION).get(F_Strings.Cladding);
            // STARTING_DELAY_DESCRIPTION
            int varSTARTING_DELAY_DESCRIPTION = env.data.vname2id.get(
                    F_Strings.STARTING_DELAY_DESCRIPTION);
            // BuildingType
            int valSDD_BuildingType = env.data.name2ids.get(
                    varSTARTING_DELAY_DESCRIPTION).get(F_Strings.DelayDueToAccessingFireBuildingType);
            // LargeSite
            int valSDD_LargeSite = env.data.name2ids.get(
                    varSTARTING_DELAY_DESCRIPTION).get(F_Strings.DelayDueToAccessingFireLargeSite);
            // Security
            int valSDD_Security = env.data.name2ids.get(
                    varSTARTING_DELAY_DESCRIPTION).get(F_Strings.DelayDueToAccessingFireSecurity);
            // Assault
            int valSDD_Assault = env.data.name2ids.get(
                    varSTARTING_DELAY_DESCRIPTION).get(F_Strings.DelayDueToAssaultOnFirefighters);
            // Civil
            int valSDD_Civil = env.data.name2ids.get(
                    varSTARTING_DELAY_DESCRIPTION).get(F_Strings.DelayDueToCivilDisturbance);
            // Location
            int valSDD_Location = env.data.name2ids.get(
                    varSTARTING_DELAY_DESCRIPTION).get(F_Strings.DelayDueToLocationOfFireNotImmediatelyEvident);
            // Wrong
            int valSDD_Wrong = env.data.name2ids.get(
                    varSTARTING_DELAY_DESCRIPTION).get(F_Strings.DelayDueToSentToWrongLocation);
            // Vehicle
            int valSDD_Vehicle = env.data.name2ids.get(
                    varSTARTING_DELAY_DESCRIPTION).get(F_Strings.DelayDueToVehicleAccessProblems);
            // NoDelay
            int valSDD_NoDelay = env.data.name2ids.get(
                    varSTARTING_DELAY_DESCRIPTION).get(F_Strings.NoDelay);
            // RESCUES
            int varRESCUES = env.data.vname2id.get(F_Strings.RESCUES);
            int valRESCUES_0 = env.data.name2ids.get(varRESCUES).get("0");
            // BUILDING_EVACUATION_DELAY_DESCRIPTION
            int varBUILDING_EVACUATION_DELAY_DESCRIPTION = env.data.vname2id.get(F_Strings.BUILDING_EVACUATION_DELAY_DESCRIPTION);
            HashSet<Integer> svalBUILDING_EVACUATION_DELAY_DESCRIPTION_NoDelay = new HashSet<>();
            svalBUILDING_EVACUATION_DELAY_DESCRIPTION_NoDelay.add(0);
            svalBUILDING_EVACUATION_DELAY_DESCRIPTION_NoDelay.add(4);
            svalBUILDING_EVACUATION_DELAY_DESCRIPTION_NoDelay.add(3);
            int valDelayElderlyOrDisabled = env.data.name2ids.get(
                    varBUILDING_EVACUATION_DELAY_DESCRIPTION).get(F_Strings.DelayElderlyOrDisabled);
            // BUILDING_EVACUATION_TIME_DESCRIPTION
            //int varBUILDING_EVACUATION_TIME_DESCRIPTION = env.data.vname2id.get(F_Strings.BUILDING_EVACUATION_TIME_DESCRIPTION);
            HashSet<Integer> svalBUILDING_EVACUATION_TIME_DESCRIPTION_GT5 = new HashSet<>();
            svalBUILDING_EVACUATION_TIME_DESCRIPTION_GT5.add(2);
            svalBUILDING_EVACUATION_TIME_DESCRIPTION_GT5.add(0);
            svalBUILDING_EVACUATION_TIME_DESCRIPTION_GT5.add(7);
            // OCCUPANCY_TYPE
            int varOCCUPANCY_TYPE = env.data.vname2id.get(F_Strings.OCCUPANCY_TYPE);
            //HashSet<Integer> svalLonePerson = new HashSet<>();
            //svalLonePerson.add(0);
            //svalLonePerson.add(1);
            int valThreeOrMoreAdultsUnderPensionableAgeNoChildren = env.data.name2ids.get(varOCCUPANCY_TYPE).get(F_Strings.ThreeOrMoreAdultsUnderPensionableAgeNoChildren);
            int valThreeOrMoreAdultsWithDependantChildren = env.data.name2ids.get(varOCCUPANCY_TYPE).get(F_Strings.ThreeOrMoreAdultsWithDependantChildren);
            int valCoupleBothUnderPensionableAgeWithNoChildren = env.data.name2ids.get(varOCCUPANCY_TYPE).get(F_Strings.CoupleBothUnderPensionableAgeWithNoChildren);
            int valCoupleOneOrMoreOverPensionableAgeNoChildren = env.data.name2ids.get(varOCCUPANCY_TYPE).get(F_Strings.CoupleOneOrMoreOverPensionableAgeNoChildren);
            int valCoupleWithDependantChildren = env.data.name2ids.get(varOCCUPANCY_TYPE).get(F_Strings.CoupleWithDependantChildren);
            int valLoneParentWithDependantChildren = env.data.name2ids.get(varOCCUPANCY_TYPE).get(F_Strings.LoneParentWithDependantChildren);
            int valLonePersonOverPensionableAge = env.data.name2ids.get(varOCCUPANCY_TYPE).get(F_Strings.LonePersonOverPensionableAge);
            int valLonePersonUnderPensionableAge = env.data.name2ids.get(varOCCUPANCY_TYPE).get(F_Strings.LonePersonUnderPensionableAge);
            // CAUSE_OF_FIRE
            int varCAUSE_OF_FIRE = env.data.vname2id.get(F_Strings.CAUSE_OF_FIRE);
            int valAccumulationOfFlammableMaterial = env.data.name2ids.get(varCAUSE_OF_FIRE).get(F_Strings.AccumulationOfFlammableMaterial);
            HashSet<Integer> svalFaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause = new HashSet<>();
            svalFaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause.add(env.data.name2ids.get(varCAUSE_OF_FIRE).get(F_Strings.FaultInEquipmentOrAppliance));
            svalFaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause.add(env.data.name2ids.get(varCAUSE_OF_FIRE).get(F_Strings.FaultyFuelSupplyElectricity));
            svalFaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause.add(env.data.name2ids.get(varCAUSE_OF_FIRE).get(F_Strings.FaultyFuelSupplyGas));
            svalFaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause.add(env.data.name2ids.get(varCAUSE_OF_FIRE).get(F_Strings.FaultyFuelSupplyPetrolProduct));
            svalFaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause.add(env.data.name2ids.get(varCAUSE_OF_FIRE).get(F_Strings.FaultyLeadsToEquipmentOrAppliance));
            svalFaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause.add(env.data.name2ids.get(varCAUSE_OF_FIRE).get(F_Strings.OverheatingUnknownCause));
            // BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION
            int varBSSMOED = env.data.vname2id.get(F_Strings.BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION);
            int valBSSMOEDNotApplicable = env.data.name2ids.get(varBSSMOED).get(F_Strings.NotApplicable);

            // Stuarts Table
            // -------------
            // BUILDING_FLOORS_ABOVE_GROUND
            //int varBUILDING_FLOORS_ABOVE_GROUND = env.data.vname2id.get(F_Strings.BUILDING_FLOORS_ABOVE_GROUND);
            HashSet<Integer> svalBUILDING_FLOORS_ABOVE_GROUND45 = new HashSet<>();
            svalBUILDING_FLOORS_ABOVE_GROUND45.add(25); // 4
            svalBUILDING_FLOORS_ABOVE_GROUND45.add(26); // 5
            HashSet<Integer> svalBUILDING_FLOORS_ABOVE_GROUND6789 = new HashSet<>();
            svalBUILDING_FLOORS_ABOVE_GROUND6789.add(27); // 6
            svalBUILDING_FLOORS_ABOVE_GROUND6789.add(29); // 7
            svalBUILDING_FLOORS_ABOVE_GROUND6789.add(30); // 8
            svalBUILDING_FLOORS_ABOVE_GROUND6789.add(31); // 10
            // tspread_of_fire_d WholeBuildingOrAffectingMoreThan2Floors
            Map<Integer, Set<F_Dwellings_Integer_Record0>> mWholeBuildingOrAffectingMoreThan2FloorsFATALITY_CASUALTY = new HashMap<>();
            Map<Integer, Set<F_Dwellings_Integer_Record0>> mWholeBuildingOrAffectingMoreThan2FloorsNonFATALITY_CASUALTY = new HashMap<>();
//            Iterator<Integer> itei = mAllBUILDING_OR_PROPERTY_TYPE.keySet().iterator();
//            while (itei.hasNext()) {
//                mWholeBuildingOrAffectingMoreThan2FloorsFATALITY_CASUALTY.put(itei.next(), new HashSet<>());
//            }

            // Subsets
            // -------
            // BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION valCladding 
            Set<F_Dwellings_Integer_Record1> highriseCladdingWholeBuildingOrAffectingMoreThan2FloorsFATALITY_CASUALTY = new HashSet<>();
            Set<F_Dwellings_Integer_Record1> highriseCladdingWholeBuildingOrAffectingMoreThan2FloorsNonFATALITY_CASUALTY = new HashSet<>();
            int valPurposeBuiltHighRiseFlats = env.data.name2ids.get(varBUILDING_OR_PROPERTY_TYPE).get(F_Strings.PurposeBuiltHighRiseFlats);
            Set<F_Dwellings_Integer_Record1> mediumriseCladdingWholeBuildingOrAffectingMoreThan2FloorsFATALITY_CASUALTY = new HashSet<>();
            Set<F_Dwellings_Integer_Record1> mediumriseCladdingWholeBuildingOrAffectingMoreThan2FloorsNonFATALITY_CASUALTY = new HashSet<>();
            int valPurposeBuiltMediumRiseFlats = env.data.name2ids.get(varBUILDING_OR_PROPERTY_TYPE).get(F_Strings.PurposeBuiltMediumRiseFlats);
            Set<F_Dwellings_Integer_Record1> lowriseCladdingWholeBuildingOrAffectingMoreThan2FloorsFATALITY_CASUALTY = new HashSet<>();
            Set<F_Dwellings_Integer_Record1> lowriseCladdingWholeBuildingOrAffectingMoreThan2FloorsNonFATALITY_CASUALTY = new HashSet<>();
            int valPurposeBuiltLowRiseFlats = env.data.name2ids.get(varBUILDING_OR_PROPERTY_TYPE).get(F_Strings.PurposeBuiltLowRiseFlats);
            Set<F_Dwellings_Integer_Record1> purposeBuilt_BSSMOED_NotApplicable = new HashSet<>();
            Set<F_Dwellings_Integer_Record1> FDScore_GE5 = new HashSet<>();
            Set<F_Dwellings_Integer_Record1> purposeBuilt = new HashSet<>();
            Iterator<F_CollectionID> ite = env.data.data.keySet().iterator();
            while (ite.hasNext()) {
                try {
                    F_CollectionID cid = ite.next();
                    F_Collection c = env.data.getCollection(cid);
                    String year = env.data.id2names.get(0).get(cid.id);
                    System.out.println("cid=" + cid.toString() + ", size=" + c.data1.keySet().size());
                    // Single variable counts
                    // ----------------------
                    // DAY_NIGHT
                    HashMap<Integer, Integer> mNight_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllNight_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mNight_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_Night_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_Night_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_Night_By_BUILDING_OR_PROPERTY_TYPE);
                    // BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION
                    HashMap<Integer, Integer> mNoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllNoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mNoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE);
                    // spread_of_fire_d
                    HashMap<Integer, Integer> mWholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllWholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mWholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE);
                    // STARTING_DELAY_DESCRIPTION
                    // BuildingType
                    HashMap<Integer, Integer> mSDD_BuildingType_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllSDD_BuildingType_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mSDD_BuildingType_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_SDD_BuildingType_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_SDD_BuildingType_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_SDD_BuildingType_By_BUILDING_OR_PROPERTY_TYPE);
                    // LargeSite
                    HashMap<Integer, Integer> mSDD_LargeSite_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllSDD_LargeSite_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mSDD_LargeSite_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_SDD_LargeSite_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_SDD_LargeSite_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_SDD_LargeSite_By_BUILDING_OR_PROPERTY_TYPE);
                    // Security
                    HashMap<Integer, Integer> mSDD_Security_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllSDD_Security_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mSDD_Security_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_SDD_Security_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_SDD_Security_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_SDD_Security_By_BUILDING_OR_PROPERTY_TYPE);
                    // Assault
                    HashMap<Integer, Integer> mSDD_Assault_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllSDD_Assault_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mSDD_Assault_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_SDD_Assault_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_SDD_Assault_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_SDD_Assault_By_BUILDING_OR_PROPERTY_TYPE);
                    // Civil
                    HashMap<Integer, Integer> mSDD_Civil_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllSDD_Civil_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mSDD_Civil_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_SDD_Civil_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_SDD_Civil_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_SDD_Civil_By_BUILDING_OR_PROPERTY_TYPE);
                    // Location
                    HashMap<Integer, Integer> mSDD_Location_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllSDD_Location_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mSDD_Location_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_SDD_Location_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_SDD_Location_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_SDD_Location_By_BUILDING_OR_PROPERTY_TYPE);
                    // Wrong
                    HashMap<Integer, Integer> mSDD_Wrong_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllSDD_Wrong_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mSDD_Wrong_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_SDD_Wrong_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_SDD_Wrong_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_SDD_Wrong_By_BUILDING_OR_PROPERTY_TYPE);
                    // Vehicle
                    HashMap<Integer, Integer> mSDD_Vehicle_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllSDD_Vehicle_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mSDD_Vehicle_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_SDD_Vehicle_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_SDD_Vehicle_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_SDD_Vehicle_By_BUILDING_OR_PROPERTY_TYPE);
                    // Delay
                    HashMap<Integer, Integer> mSDD_Delay_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllSDD_Delay_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mSDD_Delay_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_SDD_Delay_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_SDD_Delay_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_SDD_Delay_By_BUILDING_OR_PROPERTY_TYPE);
                    // NoDelay
                    HashMap<Integer, Integer> mSDD_NoDelay_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllSDD_NoDelay_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mSDD_NoDelay_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_SDD_NoDelay_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_SDD_NoDelay_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_SDD_NoDelay_By_BUILDING_OR_PROPERTY_TYPE);
                    // RESCUES
                    HashMap<Integer, Integer> mRescues_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllRescues_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mRescues_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_Rescues_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_Rescues_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_Rescues_By_BUILDING_OR_PROPERTY_TYPE);
                    // BUILDING_EVACUATION_DELAY_DESCRIPTION
                    HashMap<Integer, Integer> mDelayEvac_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllDelayEvac_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mDelayEvac_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_DelayEvac_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_DelayEvac_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_DelayEvac_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mDelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllDelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mDelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE);
                    // BUILDING_EVACUATION_TIME_DESCRIPTION
                    HashMap<Integer, Integer> mEvacGT5_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllEvacGT5_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mEvacGT5_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_EvacGT5_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_EvacGT5_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_EvacGT5_By_BUILDING_OR_PROPERTY_TYPE);
                    // OCCUPANCY_TYPE
                    HashMap<Integer, Integer> mThreeOrMoreAdultsUnderPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllThreeOrMoreAdultsUnderPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mThreeOrMoreAdultsUnderPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_ThreeOrMoreAdultsUnderPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_ThreeOrMoreAdultsUnderPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_ThreeOrMoreAdultsUnderPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_ThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_ThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_ThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mCoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllCoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mCoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_CoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_CoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_CoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mCoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllCoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mCoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_CoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_CoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_CoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mCoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllCoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mCoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_CoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_CoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_CoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mLoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllLoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mLoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_LoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_LoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_LoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mLonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllLonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mLonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_LonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_LonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_LonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mLonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllLonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mLonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_LonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_LonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_LonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE);
                    // CAUSE_OF_FIRE
                    HashMap<Integer, Integer> mAccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllLonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mAccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE);
                    // BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION
                    HashMap<Integer, Integer> mBSSMOEDNotApplicable_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllBSSMOEDNotApplicable_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mBSSMOEDNotApplicable_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_BSSMOEDNotApplicable_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_BSSMOEDNotApplicable_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_BSSMOEDNotApplicable_By_BUILDING_OR_PROPERTY_TYPE);
                    // FIRE_SAFETY scores
                    HashMap<Integer, Integer> mFIRE_SAFETY_SUCCESS_SCORE_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFIRE_SAFETY_SUCCESS_SCORE_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFIRE_SAFETY_SUCCESS_SCORE_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_FIRE_SAFETY_SUCCESS_SCORE_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_FIRE_SAFETY_SUCCESS_SCORE_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_FIRE_SAFETY_SUCCESS_SCORE_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFIRE_SAFETY_FAILURE_SCORE_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFIRE_SAFETY_FAILURE_SCORE_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFIRE_SAFETY_FAILURE_SCORE_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_FIRE_SAFETY_FAILURE_SCORE_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_FIRE_SAFETY_FAILURE_SCORE_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_FIRE_SAFETY_FAILURE_SCORE_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mHoleyCheese2_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllHoleyCheese2_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mHoleyCheese2_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_HoleyCheese2_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_HoleyCheese2_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_HoleyCheese2_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mHoleyCheese3_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllHoleyCheese3_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mHoleyCheese3_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_HoleyCheese3_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_HoleyCheese3_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_HoleyCheese3_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mHoleyCheese4_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllHoleyCheese4_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mHoleyCheese4_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_HoleyCheese4_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_HoleyCheese4_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_HoleyCheese4_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mDelaysToFireFightingAndNotFirespreadHoleyCheese_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllDelaysToFireFightingAndNotFirespreadHoleyCheese_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mHoleyCheese4_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_DelaysToFireFightingAndNotFirespreadHoleyCheese_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_DelaysToFireFightingAndNotFirespreadHoleyCheese_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_HoleyCheese4_By_BUILDING_OR_PROPERTY_TYPE);
                    // Multiple variable counts
                    // ------------------------
                    // DAY_NIGHT AND BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION
                    HashMap<Integer, Integer> mNight_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllNight_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mNight_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_Night_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_Night_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_Night_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE);
                    // DAY_NIGHT AND spread_of_fire_d
                    HashMap<Integer, Integer> mNight_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllNight_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mNight_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_Night_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_Night_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_Night_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE);
                    // BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION AND spread_of_fire_d
                    HashMap<Integer, Integer> mCladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllCladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mCladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_Cladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_Cladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_Cladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE);
                    // BUILDING_EVACUATION_TIME_DESCRIPTION AND BUILDING_EVACUATION_DELAY_DESCRIPTION
                    HashMap<Integer, Integer> mEvacGT5_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllEvacGT5_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mEvacGT5_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_EvacGT5_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_EvacGT5_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_EvacGT5_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE);
                    // spread_of_fire_d AND OCCUPANCY_TYPE
                    HashMap<Integer, Integer> mWholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsUnderPensionableAgeNoChildrenAge_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllWholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsUnderPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mWholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsUnderPensionableAgeNoChildrenAge_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsUnderPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsUnderPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsUnderPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mWholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllWholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mWholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mWholeBuildingOrAffectingMoreThan2Floors_CoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllWholeBuildingOrAffectingMoreThan2Floors_CoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mWholeBuildingOrAffectingMoreThan2Floors_CoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_CoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_CoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_CoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mWholeBuildingOrAffectingMoreThan2Floors_CoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllWholeBuildingOrAffectingMoreThan2Floors_CoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mWholeBuildingOrAffectingMoreThan2Floors_CoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_CoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_CoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_CoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mWholeBuildingOrAffectingMoreThan2Floors_CoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllWholeBuildingOrAffectingMoreThan2Floors_CoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mWholeBuildingOrAffectingMoreThan2Floors_CoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_CoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_CoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_CoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mWholeBuildingOrAffectingMoreThan2Floors_LoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllWholeBuildingOrAffectingMoreThan2Floors_LoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mWholeBuildingOrAffectingMoreThan2Floors_LoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_LoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_LoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_LoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mWholeBuildingOrAffectingMoreThan2Floors_LonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllWholeBuildingOrAffectingMoreThan2Floors_LonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mWholeBuildingOrAffectingMoreThan2Floors_LonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_LonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_LonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_LonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mWholeBuildingOrAffectingMoreThan2Floors_LonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllWholeBuildingOrAffectingMoreThan2Floors_LonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mWholeBuildingOrAffectingMoreThan2Floors_LonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_LonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_LonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_LonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE);
                    // spread_of_fire_d AND CAUSE_OF_FIRE
                    HashMap<Integer, Integer> mWholeBuildingOrAffectingMoreThan2Floors_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllWholeBuildingOrAffectingMoreThan2Floors_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mWholeBuildingOrAffectingMoreThan2Floors_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mWholeBuildingOrAffectingMoreThan2Floors_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllWholeBuildingOrAffectingMoreThan2Floors_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mWholeBuildingOrAffectingMoreThan2Floors_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE);
                    HashMap<Integer, Integer> mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                    mAllFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE);

                    int mediumRise45 = 0;
                    int mediumRise45_FATALITY_CASUALTY = 0;
                    int mediumRise45_NoCompartmentation = 0;
                    int mediumRise45_NoCompartmentation_FATALITY_CASUALTY = 0;
                    int mediumRise45_WholeBuildingOrAffectingMoreThan2Floors = 0;
                    int mediumRise45_WholeBuildingOrAffectingMoreThan2Floors_FATALITY_CASUALTY = 0;
                    int mediumRise45_Cladding_WholeBuildingOrAffectingMoreThan2Floors = 0;
                    int mediumRise45_Cladding_WholeBuildingOrAffectingMoreThan2Floors_FATALITY_CASUALTY = 0;
                    int mediumRise6789 = 0;
                    int mediumRise6789_FATALITY_CASUALTY = 0;
                    int mediumRise6789_NoCompartmentation = 0;
                    int mediumRise6789_NoCompartmentation_FATALITY_CASUALTY = 0;
                    int mediumRise6789_WholeBuildingOrAffectingMoreThan2Floors = 0;
                    int mediumRise6789_WholeBuildingOrAffectingMoreThan2Floors_FATALITY_CASUALTY = 0;
                    int mediumRise6789_Cladding_WholeBuildingOrAffectingMoreThan2Floors = 0;
                    int mediumRise6789_Cladding_WholeBuildingOrAffectingMoreThan2Floors_FATALITY_CASUALTY = 0;
                    Iterator<F_RecordID> ite2 = c.data1.keySet().iterator();
                    while (ite2.hasNext()) {
                        F_RecordID id = ite2.next();
                        F_Dwellings_Integer_Record1 r = c.data1.get(id);
//                    String sACCIDENTAL_OR_DELIBERATE = env.data.id2names.get(
//                            env.data.vname2id.get(F_Strings.ACCIDENTAL_OR_DELIBERATE)).get(r.tACCIDENTAL_OR_DELIBERATE);
//                    System.out.println("F_Strings.ACCIDENTAL_OR_DELIBERATE " 
//                            + sACCIDENTAL_OR_DELIBERATE);
                        // 
                        addToCountsAndSets(r, valFATALITY_CASUALTY, valNight,
                                mNight_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_Night_By_BUILDING_OR_PROPERTY_TYPE,
                                valNoCompartmentationInBuilding,
                                mNoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE,
                                mNight_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_Night_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE,
                                valWholeBuildingOrAffectingMoreThan2Floors,
                                mWholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
                                mWholeBuildingOrAffectingMoreThan2FloorsFATALITY_CASUALTY,
                                mWholeBuildingOrAffectingMoreThan2FloorsNonFATALITY_CASUALTY,
                                mNight_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_Night_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
                                valCladding,
                                mCladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_Cladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
                                valPurposeBuiltHighRiseFlats,
                                highriseCladdingWholeBuildingOrAffectingMoreThan2FloorsFATALITY_CASUALTY,
                                valPurposeBuiltMediumRiseFlats,
                                mediumriseCladdingWholeBuildingOrAffectingMoreThan2FloorsFATALITY_CASUALTY,
                                valPurposeBuiltLowRiseFlats,
                                lowriseCladdingWholeBuildingOrAffectingMoreThan2FloorsFATALITY_CASUALTY,
                                highriseCladdingWholeBuildingOrAffectingMoreThan2FloorsNonFATALITY_CASUALTY,
                                mediumriseCladdingWholeBuildingOrAffectingMoreThan2FloorsNonFATALITY_CASUALTY,
                                lowriseCladdingWholeBuildingOrAffectingMoreThan2FloorsNonFATALITY_CASUALTY,
                                // START_DELAY_DESCRIPTION
                                // BuildingType
                                valSDD_BuildingType,
                                mSDD_BuildingType_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_SDD_BuildingType_By_BUILDING_OR_PROPERTY_TYPE,
                                // LargeSite
                                valSDD_LargeSite,
                                mSDD_LargeSite_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_SDD_LargeSite_By_BUILDING_OR_PROPERTY_TYPE,
                                // Security
                                valSDD_Security,
                                mSDD_Security_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_SDD_Security_By_BUILDING_OR_PROPERTY_TYPE,
                                // Assault
                                valSDD_Assault,
                                mSDD_Assault_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_SDD_Assault_By_BUILDING_OR_PROPERTY_TYPE,
                                // Civil
                                valSDD_Civil,
                                mSDD_Civil_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_SDD_Civil_By_BUILDING_OR_PROPERTY_TYPE,
                                // Location
                                valSDD_Location,
                                mSDD_Location_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_SDD_Location_By_BUILDING_OR_PROPERTY_TYPE,
                                // Wrong
                                valSDD_Wrong,
                                mSDD_Wrong_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_SDD_Wrong_By_BUILDING_OR_PROPERTY_TYPE,
                                // Vehicle
                                valSDD_Vehicle,
                                mSDD_Vehicle_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_SDD_Vehicle_By_BUILDING_OR_PROPERTY_TYPE,
                                // NoDelay
                                valSDD_NoDelay,
                                mSDD_Delay_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_SDD_Delay_By_BUILDING_OR_PROPERTY_TYPE,
                                mSDD_NoDelay_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_SDD_NoDelay_By_BUILDING_OR_PROPERTY_TYPE,
                                // RESCUES
                                valRESCUES_0,
                                mRescues_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_Rescues_By_BUILDING_OR_PROPERTY_TYPE,
                                // BUILDING_EVACUATION_DELAY_DESCRIPTION
                                varBUILDING_EVACUATION_DELAY_DESCRIPTION,
                                svalBUILDING_EVACUATION_DELAY_DESCRIPTION_NoDelay,
                                mDelayEvac_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_DelayEvac_By_BUILDING_OR_PROPERTY_TYPE,
                                varBUILDING_EVACUATION_DELAY_DESCRIPTION,
                                svalBUILDING_EVACUATION_TIME_DESCRIPTION_GT5,
                                mEvacGT5_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_EvacGT5_By_BUILDING_OR_PROPERTY_TYPE,
                                valDelayElderlyOrDisabled,
                                mDelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE,
                                mEvacGT5_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_EvacGT5_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE,
                                valAccumulationOfFlammableMaterial,
                                mAccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE,
                                mWholeBuildingOrAffectingMoreThan2Floors_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE,
                                svalFaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause,
                                mFaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE,
                                mWholeBuildingOrAffectingMoreThan2Floors_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE,
                                valBSSMOEDNotApplicable,
                                mBSSMOEDNotApplicable_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_BSSMOEDNotApplicable_By_BUILDING_OR_PROPERTY_TYPE,
                                //                                mFIRE_SAFETY_SUCCESS_SCORE_By_BUILDING_OR_PROPERTY_TYPE,
                                //                                mFATALITY_CASUALTY_FIRE_SAFETY_SUCCESS_SCORE_By_BUILDING_OR_PROPERTY_TYPE,
                                //                                mFIRE_SAFETY_FAILURE_SCORE_By_BUILDING_OR_PROPERTY_TYPE,
                                //                                mFATALITY_CASUALTY_FIRE_SAFETY_FAILURE_SCORE_By_BUILDING_OR_PROPERTY_TYPE);
                                mHoleyCheese2_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_HoleyCheese2_By_BUILDING_OR_PROPERTY_TYPE,
                                mHoleyCheese3_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_HoleyCheese3_By_BUILDING_OR_PROPERTY_TYPE,
                                mHoleyCheese4_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_HoleyCheese4_By_BUILDING_OR_PROPERTY_TYPE,
                                mDelaysToFireFightingAndNotFirespreadHoleyCheese_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_DelaysToFireFightingAndNotFirespreadHoleyCheese_By_BUILDING_OR_PROPERTY_TYPE);
                        if (r.tBUILDING_OR_PROPERTY_TYPE == valPurposeBuiltMediumRiseFlats) {
                            if (svalBUILDING_FLOORS_ABOVE_GROUND45.contains(r.tBUILDING_FLOORS_ABOVE_GROUND)) {
                                mediumRise45++;
                                if (r.tFATALITY_CASUALTY == valFATALITY_CASUALTY) {
                                    mediumRise45_FATALITY_CASUALTY++;
                                }
                                if (r.tBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION == valNoCompartmentationInBuilding) {
                                    mediumRise45_NoCompartmentation++;
                                    if (r.tFATALITY_CASUALTY == valFATALITY_CASUALTY) {
                                        mediumRise45_NoCompartmentation_FATALITY_CASUALTY++;
                                    }
                                }
                                if (r.tspread_of_fire_d == valWholeBuildingOrAffectingMoreThan2Floors) {
                                    mediumRise45_WholeBuildingOrAffectingMoreThan2Floors++;
                                    if (r.tFATALITY_CASUALTY == valFATALITY_CASUALTY) {
                                        mediumRise45_WholeBuildingOrAffectingMoreThan2Floors_FATALITY_CASUALTY++;
                                    }
                                }
                                if (r.tBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION == valCladding) {
                                    if (r.tspread_of_fire_d == valWholeBuildingOrAffectingMoreThan2Floors) {
                                        mediumRise45_Cladding_WholeBuildingOrAffectingMoreThan2Floors++;
                                        if (r.tFATALITY_CASUALTY == valFATALITY_CASUALTY) {
                                            mediumRise45_Cladding_WholeBuildingOrAffectingMoreThan2Floors_FATALITY_CASUALTY++;
                                        }
                                    }
                                }
                            }
                            if (svalBUILDING_FLOORS_ABOVE_GROUND6789.contains(r.tBUILDING_FLOORS_ABOVE_GROUND)) {
                                mediumRise6789++;
                                if (r.tFATALITY_CASUALTY == valFATALITY_CASUALTY) {
                                    mediumRise6789_FATALITY_CASUALTY++;
                                }
                                if (r.tBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION == valNoCompartmentationInBuilding) {
                                    mediumRise6789_NoCompartmentation++;
                                    if (r.tFATALITY_CASUALTY == valFATALITY_CASUALTY) {
                                        mediumRise6789_NoCompartmentation_FATALITY_CASUALTY++;
                                    }
                                }
                            }
                        }
                        if (r.tOCCUPANCY_TYPE == valThreeOrMoreAdultsUnderPensionableAgeNoChildren) {
                            addToCount(r, valFATALITY_CASUALTY,
                                    mThreeOrMoreAdultsUnderPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE,
                                    mFATALITY_CASUALTY_ThreeOrMoreAdultsUnderPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
                            if (r.tspread_of_fire_d == valWholeBuildingOrAffectingMoreThan2Floors) {
                                addToCount(r, valFATALITY_CASUALTY,
                                        mWholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsUnderPensionableAgeNoChildrenAge_By_BUILDING_OR_PROPERTY_TYPE,
                                        mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsUnderPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
                            }
                        } else if (r.tOCCUPANCY_TYPE == valThreeOrMoreAdultsWithDependantChildren) {
                            addToCount(r, valFATALITY_CASUALTY,
                                    mThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE,
                                    mFATALITY_CASUALTY_ThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
                            if (r.tspread_of_fire_d == valWholeBuildingOrAffectingMoreThan2Floors) {
                                addToCount(r, valFATALITY_CASUALTY,
                                        mWholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE,
                                        mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
                            }
                        } else if (r.tOCCUPANCY_TYPE == valCoupleBothUnderPensionableAgeWithNoChildren) {
                            addToCount(r, valFATALITY_CASUALTY,
                                    mCoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE,
                                    mFATALITY_CASUALTY_CoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
                            if (r.tspread_of_fire_d == valWholeBuildingOrAffectingMoreThan2Floors) {
                                addToCount(r, valFATALITY_CASUALTY,
                                        mWholeBuildingOrAffectingMoreThan2Floors_CoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE,
                                        mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_CoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
                            }
                        } else if (r.tOCCUPANCY_TYPE == valCoupleOneOrMoreOverPensionableAgeNoChildren) {
                            addToCount(r, valFATALITY_CASUALTY,
                                    mCoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE,
                                    mFATALITY_CASUALTY_CoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
                            if (r.tspread_of_fire_d == valWholeBuildingOrAffectingMoreThan2Floors) {
                                addToCount(r, valFATALITY_CASUALTY,
                                        mWholeBuildingOrAffectingMoreThan2Floors_CoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE,
                                        mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_CoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
                            }
                        } else if (r.tOCCUPANCY_TYPE == valCoupleWithDependantChildren) {
                            addToCount(r, valFATALITY_CASUALTY,
                                    mCoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE,
                                    mFATALITY_CASUALTY_CoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
                            if (r.tspread_of_fire_d == valWholeBuildingOrAffectingMoreThan2Floors) {
                                addToCount(r, valFATALITY_CASUALTY,
                                        mWholeBuildingOrAffectingMoreThan2Floors_CoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE,
                                        mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_CoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
                            }
                        } else if (r.tOCCUPANCY_TYPE == valLoneParentWithDependantChildren) {
                            addToCount(r, valFATALITY_CASUALTY,
                                    mLoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE,
                                    mFATALITY_CASUALTY_LoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
                            if (r.tspread_of_fire_d == valWholeBuildingOrAffectingMoreThan2Floors) {
                                addToCount(r, valFATALITY_CASUALTY,
                                        mWholeBuildingOrAffectingMoreThan2Floors_LoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE,
                                        mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_LoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
                            }
                        } else if (r.tOCCUPANCY_TYPE == valLonePersonOverPensionableAge) {
                            addToCount(r, valFATALITY_CASUALTY,
                                    mLonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE,
                                    mFATALITY_CASUALTY_LonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE);
                            if (r.tspread_of_fire_d == valWholeBuildingOrAffectingMoreThan2Floors) {
                                addToCount(r, valFATALITY_CASUALTY,
                                        mWholeBuildingOrAffectingMoreThan2Floors_LonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE,
                                        mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_LonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE);
                            }
                        } else if (r.tOCCUPANCY_TYPE == valLonePersonUnderPensionableAge) {
                            addToCount(r, valFATALITY_CASUALTY,
                                    mLonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE,
                                    mFATALITY_CASUALTY_LonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE);
                            if (r.tspread_of_fire_d == valWholeBuildingOrAffectingMoreThan2Floors) {
                                addToCount(r, valFATALITY_CASUALTY,
                                        mWholeBuildingOrAffectingMoreThan2Floors_LonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE,
                                        mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_LonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE);
                            }
                        }
                        if (sPurposeBuiltTypes.contains(r.tBUILDING_OR_PROPERTY_TYPE)) {
                            purposeBuilt.add(r);
                            if (r.tBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION == valBSSMOEDNotApplicable) {
                                purposeBuilt_BSSMOED_NotApplicable.add(r);
                            }
                        }
                        if (r.fDScore >= 5) {
                            FDScore_GE5.add(r);
                        }
                    }
                    ite2 = c.data2.keySet().iterator();
                    while (ite2.hasNext()) {
                        F_RecordID id = ite2.next();
                        F_Dwellings_Integer_Record2 r = c.data2.get(id);
                        addToCountsAndSets(r, valFATALITY_CASUALTY, valNight,
                                mNight_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_Night_By_BUILDING_OR_PROPERTY_TYPE,
                                valNoCompartmentationInBuilding,
                                mNoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE,
                                mNight_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_Night_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE,
                                valWholeBuildingOrAffectingMoreThan2Floors,
                                mWholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
                                mWholeBuildingOrAffectingMoreThan2FloorsFATALITY_CASUALTY,
                                mWholeBuildingOrAffectingMoreThan2FloorsNonFATALITY_CASUALTY,
                                mNight_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_Night_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
                                valCladding,
                                mCladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_Cladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
                                valPurposeBuiltHighRiseFlats,
                                highriseCladdingWholeBuildingOrAffectingMoreThan2FloorsFATALITY_CASUALTY,
                                valPurposeBuiltMediumRiseFlats,
                                mediumriseCladdingWholeBuildingOrAffectingMoreThan2FloorsFATALITY_CASUALTY,
                                valPurposeBuiltLowRiseFlats,
                                lowriseCladdingWholeBuildingOrAffectingMoreThan2FloorsFATALITY_CASUALTY,
                                highriseCladdingWholeBuildingOrAffectingMoreThan2FloorsNonFATALITY_CASUALTY,
                                mediumriseCladdingWholeBuildingOrAffectingMoreThan2FloorsNonFATALITY_CASUALTY,
                                lowriseCladdingWholeBuildingOrAffectingMoreThan2FloorsNonFATALITY_CASUALTY,
                                // START_DELAY_DESCRIPTION
                                // BuildingType
                                valSDD_BuildingType,
                                mSDD_BuildingType_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_SDD_BuildingType_By_BUILDING_OR_PROPERTY_TYPE,
                                // LargeSite
                                valSDD_LargeSite,
                                mSDD_LargeSite_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_SDD_LargeSite_By_BUILDING_OR_PROPERTY_TYPE,
                                // Security
                                valSDD_Security,
                                mSDD_Security_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_SDD_Security_By_BUILDING_OR_PROPERTY_TYPE,
                                // Assault
                                valSDD_Assault,
                                mSDD_Assault_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_SDD_Assault_By_BUILDING_OR_PROPERTY_TYPE,
                                // Civil
                                valSDD_Civil,
                                mSDD_Civil_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_SDD_Civil_By_BUILDING_OR_PROPERTY_TYPE,
                                // Location
                                valSDD_Location,
                                mSDD_Location_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_SDD_Location_By_BUILDING_OR_PROPERTY_TYPE,
                                // Wrong
                                valSDD_Wrong,
                                mSDD_Wrong_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_SDD_Wrong_By_BUILDING_OR_PROPERTY_TYPE,
                                // Vehicle
                                valSDD_Vehicle,
                                mSDD_Vehicle_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_SDD_Vehicle_By_BUILDING_OR_PROPERTY_TYPE,
                                // NoDelay
                                valSDD_NoDelay,
                                mSDD_Delay_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_SDD_Delay_By_BUILDING_OR_PROPERTY_TYPE,
                                mSDD_NoDelay_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_SDD_NoDelay_By_BUILDING_OR_PROPERTY_TYPE,
                                // RESCUES
                                valRESCUES_0,
                                mRescues_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_Rescues_By_BUILDING_OR_PROPERTY_TYPE,
                                varBUILDING_EVACUATION_DELAY_DESCRIPTION,
                                svalBUILDING_EVACUATION_DELAY_DESCRIPTION_NoDelay,
                                mDelayEvac_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_DelayEvac_By_BUILDING_OR_PROPERTY_TYPE,
                                varBUILDING_EVACUATION_DELAY_DESCRIPTION,
                                svalBUILDING_EVACUATION_TIME_DESCRIPTION_GT5,
                                mEvacGT5_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_EvacGT5_By_BUILDING_OR_PROPERTY_TYPE,
                                valDelayElderlyOrDisabled,
                                mDelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE,
                                mEvacGT5_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_EvacGT5_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE,
                                valAccumulationOfFlammableMaterial,
                                mAccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE,
                                mWholeBuildingOrAffectingMoreThan2Floors_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE,
                                svalFaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause,
                                mFaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE,
                                mWholeBuildingOrAffectingMoreThan2Floors_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE,
                                valBSSMOEDNotApplicable,
                                mBSSMOEDNotApplicable_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_BSSMOEDNotApplicable_By_BUILDING_OR_PROPERTY_TYPE,
                                mHoleyCheese2_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_HoleyCheese2_By_BUILDING_OR_PROPERTY_TYPE,
                                mHoleyCheese3_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_HoleyCheese3_By_BUILDING_OR_PROPERTY_TYPE,
                                mHoleyCheese4_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_HoleyCheese4_By_BUILDING_OR_PROPERTY_TYPE,
                                mDelaysToFireFightingAndNotFirespreadHoleyCheese_By_BUILDING_OR_PROPERTY_TYPE,
                                mFATALITY_CASUALTY_DelaysToFireFightingAndNotFirespreadHoleyCheese_By_BUILDING_OR_PROPERTY_TYPE);
                    }
                    mMediumRise45.put(cid.id, mediumRise45);
                    mMediumRise45_FATALITY_CASUALTY.put(cid.id, mediumRise45_FATALITY_CASUALTY);
                    mMediumRise45_NoCompartmentation.put(cid.id, mediumRise45_NoCompartmentation);
                    mMediumRise45_NoCompartmentation_FATALITY_CASUALTY.put(cid.id, mediumRise45_NoCompartmentation_FATALITY_CASUALTY);
                    mMediumRise45_WholeBuildingOrAffectingMoreThan2Floors.put(cid.id, mediumRise45_WholeBuildingOrAffectingMoreThan2Floors);
                    mMediumRise45_WholeBuildingOrAffectingMoreThan2Floors_FATALITY_CASUALTY.put(cid.id, mediumRise45_WholeBuildingOrAffectingMoreThan2Floors_FATALITY_CASUALTY);
                    mMediumRise45_Cladding_WholeBuildingOrAffectingMoreThan2Floors.put(cid.id, mediumRise45_Cladding_WholeBuildingOrAffectingMoreThan2Floors);
                    mMediumRise45_Cladding_WholeBuildingOrAffectingMoreThan2Floors_FATALITY_CASUALTY.put(cid.id, mediumRise45_Cladding_WholeBuildingOrAffectingMoreThan2Floors_FATALITY_CASUALTY);
                    mMediumRise6789.put(cid.id, mediumRise6789);
                    mMediumRise6789_FATALITY_CASUALTY.put(cid.id, mediumRise6789_FATALITY_CASUALTY);
                    mMediumRise6789_NoCompartmentation.put(cid.id, mediumRise6789_NoCompartmentation);
                    mMediumRise6789_NoCompartmentation_FATALITY_CASUALTY.put(cid.id, mediumRise6789_NoCompartmentation_FATALITY_CASUALTY);
                    mMediumRise6789_WholeBuildingOrAffectingMoreThan2Floors.put(cid.id, mediumRise6789_WholeBuildingOrAffectingMoreThan2Floors);
                    mMediumRise6789_WholeBuildingOrAffectingMoreThan2Floors_FATALITY_CASUALTY.put(cid.id, mediumRise6789_WholeBuildingOrAffectingMoreThan2Floors_FATALITY_CASUALTY);
                    mMediumRise6789_Cladding_WholeBuildingOrAffectingMoreThan2Floors.put(cid.id, mediumRise6789_Cladding_WholeBuildingOrAffectingMoreThan2Floors);
                    mMediumRise6789_Cladding_WholeBuildingOrAffectingMoreThan2Floors_FATALITY_CASUALTY.put(cid.id, mediumRise6789_Cladding_WholeBuildingOrAffectingMoreThan2Floors_FATALITY_CASUALTY);

                    Path dir = getDir(name, year);
                    // Single variable
                    output(dir, year, F_Strings.DAY_NIGHT + " " + F_Strings.Night
                            + " BY "
                            + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            varBUILDING_OR_PROPERTY_TYPE,
                            mNight_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_Night_By_BUILDING_OR_PROPERTY_TYPE);
                    output(dir, year, F_Strings.BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION
                            + " " + F_Strings.NoCompartmentationInBuilding
                            + " BY "
                            + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            varBUILDING_OR_PROPERTY_TYPE,
                            mNoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE);
                    output(dir, year, F_Strings.spread_of_fire_d
                            + " " + F_Strings.WholeBuildingOrAffectingMoreThan2Floors
                            + " BY "
                            + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            varBUILDING_OR_PROPERTY_TYPE,
                            mWholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE);
                    // START_DELAY_DESCRIPTION
                    // BuildingType
                    output(dir, year, F_Strings.STARTING_DELAY_DESCRIPTION + " "
                            + F_Strings.sDelayDueToAccessingFireBuildingType
                            + " BY "
                            + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            varBUILDING_OR_PROPERTY_TYPE,
                            mSDD_BuildingType_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_SDD_BuildingType_By_BUILDING_OR_PROPERTY_TYPE);
                    // LargeSite
                    output(dir, year, F_Strings.STARTING_DELAY_DESCRIPTION + " "
                            + F_Strings.sDelayDueToAccessingFireLargeSite
                            + " BY "
                            + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            varBUILDING_OR_PROPERTY_TYPE,
                            mSDD_LargeSite_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_SDD_LargeSite_By_BUILDING_OR_PROPERTY_TYPE);
                    // Security
                    output(dir, year, F_Strings.STARTING_DELAY_DESCRIPTION + " "
                            + F_Strings.sDelayDueToAccessingFireSecurity
                            + " BY "
                            + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            varBUILDING_OR_PROPERTY_TYPE,
                            mSDD_Security_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_SDD_Security_By_BUILDING_OR_PROPERTY_TYPE);
                    // Assault
                    output(dir, year, F_Strings.STARTING_DELAY_DESCRIPTION + " "
                            + F_Strings.sDelayDueToAssaultOnFirefighters
                            + " BY "
                            + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            varBUILDING_OR_PROPERTY_TYPE,
                            mSDD_Assault_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_SDD_Assault_By_BUILDING_OR_PROPERTY_TYPE);
                    // Civil
                    output(dir, year, F_Strings.STARTING_DELAY_DESCRIPTION + " "
                            + F_Strings.sDelayDueToCivilDisturbance
                            + " BY "
                            + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            varBUILDING_OR_PROPERTY_TYPE,
                            mSDD_Civil_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_SDD_Civil_By_BUILDING_OR_PROPERTY_TYPE);
                    // Location
                    output(dir, year, F_Strings.STARTING_DELAY_DESCRIPTION + " "
                            + F_Strings.sDelayDueToLocationOfFireNotImmediatelyEvident
                            + " BY "
                            + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            varBUILDING_OR_PROPERTY_TYPE,
                            mSDD_Location_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_SDD_Location_By_BUILDING_OR_PROPERTY_TYPE);
                    // Wrong
                    output(dir, year, F_Strings.STARTING_DELAY_DESCRIPTION + " "
                            + F_Strings.sDelayDueToSentToWrongLocation
                            + " BY "
                            + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            varBUILDING_OR_PROPERTY_TYPE,
                            mSDD_Wrong_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_SDD_Wrong_By_BUILDING_OR_PROPERTY_TYPE);
                    // Vehicle
                    output(dir, year, F_Strings.STARTING_DELAY_DESCRIPTION + " "
                            + F_Strings.sDelayDueToVehicleAccessProblems
                            + " BY "
                            + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            varBUILDING_OR_PROPERTY_TYPE,
                            mSDD_Vehicle_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_SDD_Vehicle_By_BUILDING_OR_PROPERTY_TYPE);
                    // Delay
                    output(dir, year, F_Strings.STARTING_DELAY_DESCRIPTION + " "
                            + F_Strings.Delay
                            + " BY "
                            + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            varBUILDING_OR_PROPERTY_TYPE,
                            mSDD_Delay_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_SDD_Delay_By_BUILDING_OR_PROPERTY_TYPE);
                    // NoDelay
                    output(dir, year, F_Strings.STARTING_DELAY_DESCRIPTION + " "
                            + F_Strings.sNoDelay
                            + " BY "
                            + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            varBUILDING_OR_PROPERTY_TYPE,
                            mSDD_NoDelay_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_SDD_NoDelay_By_BUILDING_OR_PROPERTY_TYPE);
                    // RESCUES
                    output(dir, year, F_Strings.RESCUES + " NonZero"
                            + " BY "
                            + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            varBUILDING_OR_PROPERTY_TYPE,
                            mRescues_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_Rescues_By_BUILDING_OR_PROPERTY_TYPE);
                    output(dir, year, F_Strings.BUILDING_EVACUATION_DELAY_DESCRIPTION
                            + " DelayEvac"
                            + " BY "
                            + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            varBUILDING_OR_PROPERTY_TYPE,
                            mDelayEvac_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_DelayEvac_By_BUILDING_OR_PROPERTY_TYPE);
                    output(dir, year, F_Strings.BUILDING_EVACUATION_TIME_DESCRIPTION
                            + " GT5"
                            + " BY "
                            + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            varBUILDING_OR_PROPERTY_TYPE,
                            mEvacGT5_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_EvacGT5_By_BUILDING_OR_PROPERTY_TYPE);
                    output(dir, year, F_Strings.BUILDING_EVACUATION_DELAY_DESCRIPTION + " "
                            + F_Strings.DelayElderlyOrDisabled
                            + " BY "
                            + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            varBUILDING_OR_PROPERTY_TYPE,
                            mDelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE);
                    output(dir, year, F_Strings.OCCUPANCY_TYPE
                            + " " + F_Strings.LonePersonOverPensionableAge
                            + " BY "
                            + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            varBUILDING_OR_PROPERTY_TYPE,
                            mLonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_LonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE);
                    output(dir, year, F_Strings.OCCUPANCY_TYPE
                            + " " + F_Strings.LonePersonUnderPensionableAge
                            + " BY "
                            + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            varBUILDING_OR_PROPERTY_TYPE,
                            mLonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_LonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE);
                    // CAUSE_OF_FIRE
                    output(dir, year, F_Strings.CAUSE_OF_FIRE
                            + " " + F_Strings.AccumulationOfFlammableMaterial
                            + " BY "
                            + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            varBUILDING_OR_PROPERTY_TYPE,
                            mAccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE);
                    output(dir, year, F_Strings.CAUSE_OF_FIRE
                            + " " + F_Strings.FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause
                            + " BY "
                            + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            varBUILDING_OR_PROPERTY_TYPE,
                            mFaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE);
                    // BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION
                    output(dir, year, F_Strings.BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION
                            + " " + F_Strings.NotApplicable
                            + " BY "
                            + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            varBUILDING_OR_PROPERTY_TYPE,
                            mBSSMOEDNotApplicable_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_BSSMOEDNotApplicable_By_BUILDING_OR_PROPERTY_TYPE);
                    // HoleyCheese
                    output(dir, year, "HoleyCheese_2"
                            + " BY "
                            + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            varBUILDING_OR_PROPERTY_TYPE,
                            mHoleyCheese2_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_HoleyCheese2_By_BUILDING_OR_PROPERTY_TYPE);
                    output(dir, year, "HoleyCheese_3"
                            + " BY "
                            + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            varBUILDING_OR_PROPERTY_TYPE,
                            mHoleyCheese3_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_HoleyCheese3_By_BUILDING_OR_PROPERTY_TYPE);
                    output(dir, year, "HoleyCheese_4"
                            + " BY "
                            + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            varBUILDING_OR_PROPERTY_TYPE,
                            mHoleyCheese4_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_HoleyCheese4_By_BUILDING_OR_PROPERTY_TYPE);
                    output(dir, year, F_Strings.DelaysToFireFightingAndNotFirespreadHoleyCheese
                            + " BY "
                            + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            varBUILDING_OR_PROPERTY_TYPE,
                            mDelaysToFireFightingAndNotFirespreadHoleyCheese_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_DelaysToFireFightingAndNotFirespreadHoleyCheese_By_BUILDING_OR_PROPERTY_TYPE);
                    // Multiple Variables
                    // ------------------
                    output(dir, year, F_Strings.DAY_NIGHT + " " + F_Strings.Night + " AND "
                            + F_Strings.BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION
                            + " " + F_Strings.NoCompartmentationInBuilding + " BY "
                            + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            varBUILDING_OR_PROPERTY_TYPE,
                            mNight_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_Night_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE);
                    output(dir, year, F_Strings.BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION + " " + F_Strings.Cladding
                            + " AND "
                            + F_Strings.spread_of_fire_d + " " + F_Strings.WholeBuildingOrAffectingMoreThan2Floors
                            + " BY "
                            + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            varBUILDING_OR_PROPERTY_TYPE,
                            mCladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_Cladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE);
                    output(dir, year, F_Strings.BUILDING_EVACUATION_TIME_DESCRIPTION
                            + " GT5"
                            + " AND "
                            + F_Strings.BUILDING_EVACUATION_DELAY_DESCRIPTION + " " + F_Strings.DelayElderlyOrDisabled
                            + " BY "
                            + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            varBUILDING_OR_PROPERTY_TYPE,
                            mEvacGT5_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_EvacGT5_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE);
                    output(dir, year, F_Strings.OCCUPANCY_TYPE
                            + " " + F_Strings.LonePersonOverPensionableAge
                            + " AND "
                            + F_Strings.spread_of_fire_d + " " + F_Strings.WholeBuildingOrAffectingMoreThan2Floors
                            + " BY "
                            + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            varBUILDING_OR_PROPERTY_TYPE,
                            mWholeBuildingOrAffectingMoreThan2Floors_LonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_LonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE);
                    output(dir, year, F_Strings.OCCUPANCY_TYPE
                            + " " + F_Strings.LonePersonUnderPensionableAge
                            + " AND "
                            + F_Strings.spread_of_fire_d + " " + F_Strings.WholeBuildingOrAffectingMoreThan2Floors
                            + " BY "
                            + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            varBUILDING_OR_PROPERTY_TYPE,
                            mWholeBuildingOrAffectingMoreThan2Floors_LonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_LonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE);
                    output(dir, year, F_Strings.CAUSE_OF_FIRE
                            + " " + F_Strings.AccumulationOfFlammableMaterial
                            + " AND "
                            + F_Strings.spread_of_fire_d + " " + F_Strings.WholeBuildingOrAffectingMoreThan2Floors
                            + " BY "
                            + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            varBUILDING_OR_PROPERTY_TYPE,
                            mWholeBuildingOrAffectingMoreThan2Floors_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE);
                    output(dir, year, F_Strings.CAUSE_OF_FIRE
                            + " " + F_Strings.FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause
                            + " AND "
                            + F_Strings.spread_of_fire_d + " " + F_Strings.WholeBuildingOrAffectingMoreThan2Floors
                            + " BY "
                            + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            F_Strings.BUILDING_OR_PROPERTY_TYPE,
                            varBUILDING_OR_PROPERTY_TYPE,
                            mWholeBuildingOrAffectingMoreThan2Floors_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE);
                    // Single Variables
                    Generic_Collections.addToCount(mTotalNight_By_BUILDING_OR_PROPERTY_TYPE,
                            mNight_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_Night_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_Night_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalNoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE,
                            mNoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalWholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
                            mWholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE);
                    // STARTING_DELAY_DESCRIPTION
                    // BuildingType
                    Generic_Collections.addToCount(mTotalSDD_BuildingType_By_BUILDING_OR_PROPERTY_TYPE,
                            mSDD_BuildingType_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_SDD_BuildingType_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_SDD_BuildingType_By_BUILDING_OR_PROPERTY_TYPE);
                    // LargeSite
                    Generic_Collections.addToCount(mTotalSDD_LargeSite_By_BUILDING_OR_PROPERTY_TYPE,
                            mSDD_LargeSite_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_SDD_LargeSite_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_SDD_LargeSite_By_BUILDING_OR_PROPERTY_TYPE);
                    // Security
                    Generic_Collections.addToCount(mTotalSDD_Security_By_BUILDING_OR_PROPERTY_TYPE,
                            mSDD_Security_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_SDD_Security_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_SDD_Security_By_BUILDING_OR_PROPERTY_TYPE);
                    // Assault
                    Generic_Collections.addToCount(mTotalSDD_Assault_By_BUILDING_OR_PROPERTY_TYPE,
                            mSDD_Assault_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_SDD_Assault_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_SDD_Assault_By_BUILDING_OR_PROPERTY_TYPE);
                    // Civil
                    Generic_Collections.addToCount(mTotalSDD_Civil_By_BUILDING_OR_PROPERTY_TYPE,
                            mSDD_Civil_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_SDD_Civil_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_SDD_Civil_By_BUILDING_OR_PROPERTY_TYPE);
                    // Location
                    Generic_Collections.addToCount(mTotalSDD_Location_By_BUILDING_OR_PROPERTY_TYPE,
                            mSDD_Location_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_SDD_Location_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_SDD_Location_By_BUILDING_OR_PROPERTY_TYPE);
                    // Wrong
                    Generic_Collections.addToCount(mTotalSDD_Wrong_By_BUILDING_OR_PROPERTY_TYPE,
                            mSDD_Wrong_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_SDD_Wrong_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_SDD_Wrong_By_BUILDING_OR_PROPERTY_TYPE);
                    // Vehicle
                    Generic_Collections.addToCount(mTotalSDD_Vehicle_By_BUILDING_OR_PROPERTY_TYPE,
                            mSDD_Vehicle_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_SDD_Vehicle_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_SDD_Vehicle_By_BUILDING_OR_PROPERTY_TYPE);
                    // Delay
                    Generic_Collections.addToCount(mTotalSDD_Delay_By_BUILDING_OR_PROPERTY_TYPE,
                            mSDD_Delay_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_SDD_Delay_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_SDD_Delay_By_BUILDING_OR_PROPERTY_TYPE);
                    // NoDelay
                    Generic_Collections.addToCount(mTotalSDD_NoDelay_By_BUILDING_OR_PROPERTY_TYPE,
                            mSDD_NoDelay_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_SDD_NoDelay_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_SDD_NoDelay_By_BUILDING_OR_PROPERTY_TYPE);
                    // RESCUES
                    Generic_Collections.addToCount(mTotalRescues_By_BUILDING_OR_PROPERTY_TYPE,
                            mRescues_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_Rescues_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_Rescues_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalDelayEvac_By_BUILDING_OR_PROPERTY_TYPE,
                            mDelayEvac_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_DelayEvac_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_DelayEvac_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalEvacGT5_By_BUILDING_OR_PROPERTY_TYPE,
                            mEvacGT5_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_EvacGT5_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_EvacGT5_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalDelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE,
                            mDelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalThreeOrMoreAdultsUnderPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE,
                            mThreeOrMoreAdultsUnderPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_ThreeOrMoreAdultsUnderPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_ThreeOrMoreAdultsUnderPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE,
                            mThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_ThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_ThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalCoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE,
                            mCoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_CoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_CoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalCoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE,
                            mCoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_CoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_CoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalCoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE,
                            mCoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_CoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_CoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalLoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE,
                            mLoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_LoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_LoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalLonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE,
                            mLonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_LonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_LonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalLonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE,
                            mLonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_LonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_LonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE);
                    // CAUSE_OF_FIRE
                    Generic_Collections.addToCount(mTotalAccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE,
                            mAccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE,
                            mFaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE);
                    // BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION
                    Generic_Collections.addToCount(mTotalBSSMOEDNotApplicable_By_BUILDING_OR_PROPERTY_TYPE,
                            mBSSMOEDNotApplicable_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_BSSMOEDNotApplicable_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_BSSMOEDNotApplicable_By_BUILDING_OR_PROPERTY_TYPE);
                    // FIRE_SAFETY Scores
                    Generic_Collections.addToCount(mTotalFIRE_SAFETY_SUCCESS_SCORE_By_BUILDING_OR_PROPERTY_TYPE,
                            mFIRE_SAFETY_SUCCESS_SCORE_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_FIRE_SAFETY_SUCCESS_SCORE_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_FIRE_SAFETY_SUCCESS_SCORE_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFIRE_SAFETY_FAILURE_SCORE_By_BUILDING_OR_PROPERTY_TYPE,
                            mFIRE_SAFETY_FAILURE_SCORE_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_FIRE_SAFETY_FAILURE_SCORE_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_FIRE_SAFETY_FAILURE_SCORE_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalHoleyCheese2_By_BUILDING_OR_PROPERTY_TYPE,
                            mHoleyCheese2_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_HoleyCheese2_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_HoleyCheese2_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalHoleyCheese3_By_BUILDING_OR_PROPERTY_TYPE,
                            mHoleyCheese3_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_HoleyCheese3_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_HoleyCheese3_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalHoleyCheese4_By_BUILDING_OR_PROPERTY_TYPE,
                            mHoleyCheese4_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_HoleyCheese4_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_HoleyCheese4_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalDelaysToFireFightingAndNotFirespreadHoleyCheese_By_BUILDING_OR_PROPERTY_TYPE,
                            mDelaysToFireFightingAndNotFirespreadHoleyCheese_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_DelaysToFireFightingAndNotFirespreadHoleyCheese_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_DelaysToFireFightingAndNotFirespreadHoleyCheese_By_BUILDING_OR_PROPERTY_TYPE);
                    // Multiple variables
                    Generic_Collections.addToCount(mTotalNight_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE,
                            mNight_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_Night_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_Night_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalNight_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
                            mNight_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_Night_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_Night_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalEvacGT5_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE,
                            mEvacGT5_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_EvacGT5_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_EvacGT5_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalCladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
                            mCladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_Cladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_Cladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalWholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsUnderPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE,
                            mWholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsUnderPensionableAgeNoChildrenAge_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsUnderPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsUnderPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalWholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE,
                            mWholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalWholeBuildingOrAffectingMoreThan2Floors_CoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE,
                            mWholeBuildingOrAffectingMoreThan2Floors_CoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_CoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_CoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalWholeBuildingOrAffectingMoreThan2Floors_CoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE,
                            mWholeBuildingOrAffectingMoreThan2Floors_CoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_CoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_CoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalWholeBuildingOrAffectingMoreThan2Floors_CoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE,
                            mWholeBuildingOrAffectingMoreThan2Floors_CoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_CoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_CoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalWholeBuildingOrAffectingMoreThan2Floors_LoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE,
                            mWholeBuildingOrAffectingMoreThan2Floors_LoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_LoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_LoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalWholeBuildingOrAffectingMoreThan2Floors_LonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE,
                            mWholeBuildingOrAffectingMoreThan2Floors_LonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_LonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_LonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalWholeBuildingOrAffectingMoreThan2Floors_LonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE,
                            mWholeBuildingOrAffectingMoreThan2Floors_LonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_LonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_LonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE);
                    // CAUSE_OF_FIRE
                    Generic_Collections.addToCount(mTotalWholeBuildingOrAffectingMoreThan2Floors_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE,
                            mWholeBuildingOrAffectingMoreThan2Floors_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalWholeBuildingOrAffectingMoreThan2Floors_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE,
                            mWholeBuildingOrAffectingMoreThan2Floors_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE);
                    Generic_Collections.addToCount(mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE);

                } catch (IOException | ClassNotFoundException ex) {
                    Logger.getLogger(F_Main.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
            Path dir = getDir(name, yearsAll);

            // --------------------
            // Output for all Years
            // --------------------
            // Single variables
            // ----------------
            // DAY_NIGHT
            output(dir, F_Strings.s2010_20, F_Strings.DAY_NIGHT + " " + F_Strings.Night
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalNight_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_Night_By_BUILDING_OR_PROPERTY_TYPE);
            // BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION
            output(dir, F_Strings.s2010_20, F_Strings.BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION
                    + " " + F_Strings.NoCompartmentationInBuilding
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalNoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE);
            // spread_of_fire_d
            output(dir, F_Strings.s2010_20, F_Strings.spread_of_fire_d + " " + F_Strings.WholeBuildingOrAffectingMoreThan2Floors
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalWholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE);
            // START_DELAY_DESCRIPTION
            // BuildingType
            output(dir, F_Strings.s2010_20, F_Strings.STARTING_DELAY_DESCRIPTION + " "
                    + F_Strings.sDelayDueToAccessingFireBuildingType
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalSDD_BuildingType_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_SDD_BuildingType_By_BUILDING_OR_PROPERTY_TYPE);
            // LargeSite
            output(dir, F_Strings.s2010_20, F_Strings.STARTING_DELAY_DESCRIPTION + " "
                    + F_Strings.sDelayDueToAccessingFireLargeSite
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalSDD_LargeSite_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_SDD_LargeSite_By_BUILDING_OR_PROPERTY_TYPE);
            // Security
            output(dir, F_Strings.s2010_20, F_Strings.STARTING_DELAY_DESCRIPTION + " "
                    + F_Strings.sDelayDueToAccessingFireSecurity
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalSDD_Security_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_SDD_Security_By_BUILDING_OR_PROPERTY_TYPE);
            // Assault
            output(dir, F_Strings.s2010_20, F_Strings.STARTING_DELAY_DESCRIPTION + " "
                    + F_Strings.sDelayDueToAssaultOnFirefighters
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalSDD_Assault_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_SDD_Assault_By_BUILDING_OR_PROPERTY_TYPE);
            // Civil
            output(dir, F_Strings.s2010_20, F_Strings.STARTING_DELAY_DESCRIPTION + " "
                    + F_Strings.sDelayDueToCivilDisturbance
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalSDD_Civil_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_SDD_Civil_By_BUILDING_OR_PROPERTY_TYPE);
            // Location
            output(dir, F_Strings.s2010_20, F_Strings.STARTING_DELAY_DESCRIPTION + " "
                    + F_Strings.sDelayDueToLocationOfFireNotImmediatelyEvident
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalSDD_Location_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_SDD_Location_By_BUILDING_OR_PROPERTY_TYPE);
            // Wrong
            output(dir, F_Strings.s2010_20, F_Strings.STARTING_DELAY_DESCRIPTION + " "
                    + F_Strings.sDelayDueToSentToWrongLocation
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalSDD_Wrong_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_SDD_Wrong_By_BUILDING_OR_PROPERTY_TYPE);
            // Vehicle
            output(dir, F_Strings.s2010_20, F_Strings.STARTING_DELAY_DESCRIPTION + " "
                    + F_Strings.sDelayDueToVehicleAccessProblems
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalSDD_Vehicle_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_SDD_Vehicle_By_BUILDING_OR_PROPERTY_TYPE);
            // Delay
            output(dir, F_Strings.s2010_20, F_Strings.STARTING_DELAY_DESCRIPTION + " " + F_Strings.Delay
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalSDD_Delay_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_SDD_Delay_By_BUILDING_OR_PROPERTY_TYPE);
            // NoDelay
            output(dir, F_Strings.s2010_20, F_Strings.STARTING_DELAY_DESCRIPTION + " "
                    + F_Strings.sNoDelay
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalSDD_NoDelay_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_SDD_NoDelay_By_BUILDING_OR_PROPERTY_TYPE);
            // RESCUES
            output(dir, F_Strings.s2010_20, F_Strings.RESCUES + " " + F_Strings.OneOrMore
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalRescues_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_Rescues_By_BUILDING_OR_PROPERTY_TYPE);
            // BUILDING_EVACUATION_DELAY_DESCRIPTION
            output(dir, F_Strings.s2010_20, F_Strings.BUILDING_EVACUATION_DELAY_DESCRIPTION + " "
                    + F_Strings.DelayElderlyOrDisabled
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalDelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE);
            output(dir, F_Strings.s2010_20, F_Strings.BUILDING_EVACUATION_DELAY_DESCRIPTION
                    + " DelayEvac"
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalDelayEvac_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_DelayEvac_By_BUILDING_OR_PROPERTY_TYPE);
            // BUILDING_EVACUATION_TIME_DESCRIPTION
            output(dir, F_Strings.s2010_20, F_Strings.BUILDING_EVACUATION_TIME_DESCRIPTION
                    + " GT5"
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalEvacGT5_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_EvacGT5_By_BUILDING_OR_PROPERTY_TYPE);
            // OCCUPANCY_TYPE
            output(dir, F_Strings.s2010_20, F_Strings.OCCUPANCY_TYPE
                    + " " + F_Strings.ThreeOrMoreAdultsUnderPensionableAgeNoChildren
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalThreeOrMoreAdultsUnderPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_ThreeOrMoreAdultsUnderPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
            output(dir, F_Strings.s2010_20, F_Strings.OCCUPANCY_TYPE
                    + " " + F_Strings.ThreeOrMoreAdultsWithDependantChildren
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_ThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
            output(dir, F_Strings.s2010_20, F_Strings.OCCUPANCY_TYPE
                    + " " + F_Strings.CoupleBothUnderPensionableAgeWithNoChildren
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalCoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_CoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
            output(dir, F_Strings.s2010_20, F_Strings.OCCUPANCY_TYPE
                    + " " + F_Strings.CoupleOneOrMoreOverPensionableAgeNoChildren
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalCoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_CoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
            output(dir, F_Strings.s2010_20, F_Strings.OCCUPANCY_TYPE
                    + " " + F_Strings.CoupleWithDependantChildren
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalCoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_CoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
            output(dir, F_Strings.s2010_20, F_Strings.OCCUPANCY_TYPE
                    + " " + F_Strings.LoneParentWithDependantChildren
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalLoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_LoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
            output(dir, F_Strings.s2010_20, F_Strings.OCCUPANCY_TYPE
                    + " " + F_Strings.LonePersonOverPensionableAge
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalLonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_LonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE);
            output(dir, F_Strings.s2010_20, F_Strings.OCCUPANCY_TYPE
                    + " " + F_Strings.LonePersonUnderPensionableAge
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalLonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_LonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE);
            // CAUSE_OF_FIRE
            output(dir, F_Strings.s2010_20, F_Strings.CAUSE_OF_FIRE
                    + " " + F_Strings.AccumulationOfFlammableMaterial
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalAccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE);
            output(dir, F_Strings.s2010_20, F_Strings.CAUSE_OF_FIRE
                    + " " + F_Strings.FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalFaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE);
            // BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION
            output(dir, F_Strings.s2010_20, F_Strings.BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION
                    + " " + F_Strings.NotApplicable
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalBSSMOEDNotApplicable_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_BSSMOEDNotApplicable_By_BUILDING_OR_PROPERTY_TYPE);
            // FIRE_SAFETY
            output(dir, F_Strings.s2010_20, "HoleyCheese2"
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalHoleyCheese2_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_HoleyCheese2_By_BUILDING_OR_PROPERTY_TYPE);
            output(dir, F_Strings.s2010_20, "HoleyCheese3"
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalHoleyCheese3_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_HoleyCheese3_By_BUILDING_OR_PROPERTY_TYPE);
            output(dir, F_Strings.s2010_20, "HoleyCheese4"
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalHoleyCheese4_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_HoleyCheese4_By_BUILDING_OR_PROPERTY_TYPE);
            output(dir, F_Strings.s2010_20, F_Strings.DelaysToFireFightingAndNotFirespreadHoleyCheese
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalDelaysToFireFightingAndNotFirespreadHoleyCheese_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_DelaysToFireFightingAndNotFirespreadHoleyCheese_By_BUILDING_OR_PROPERTY_TYPE);
            // Multiple variables
            // ------------------
            // DAY_NIGHT AND BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION
            output(dir, F_Strings.s2010_20, F_Strings.DAY_NIGHT + " " + F_Strings.Night + " AND "
                    + F_Strings.BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION
                    + " " + F_Strings.NoCompartmentationInBuilding
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalNight_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_Night_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE);
            // DAY_NIGHT AND spread_of_fire_d
            output(dir, F_Strings.s2010_20, F_Strings.DAY_NIGHT + " " + F_Strings.Night
                    + " AND "
                    + F_Strings.spread_of_fire_d + " " + F_Strings.WholeBuildingOrAffectingMoreThan2Floors
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalNight_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_Night_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE);
            // BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION AND spread_of_fire_d
            output(dir, F_Strings.s2010_20, F_Strings.BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION + " " + F_Strings.Cladding
                    + " AND "
                    + F_Strings.spread_of_fire_d + " " + F_Strings.WholeBuildingOrAffectingMoreThan2Floors
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalCladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_Cladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE);
            // BUILDING_EVACUATION_TIME_DESCRIPTION AND BUILDING_EVACUATION_DELAY_DESCRIPTION
            output(dir, F_Strings.s2010_20, F_Strings.BUILDING_EVACUATION_TIME_DESCRIPTION
                    + " GT5"
                    + " AND "
                    + F_Strings.BUILDING_EVACUATION_DELAY_DESCRIPTION + " " + F_Strings.DelayElderlyOrDisabled
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalEvacGT5_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_EvacGT5_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE);
            // OCCUPANCY_TYPE AND spread_of_fire_d
            output(dir, F_Strings.s2010_20, F_Strings.OCCUPANCY_TYPE
                    + " " + F_Strings.ThreeOrMoreAdultsUnderPensionableAgeNoChildren
                    + " AND "
                    + F_Strings.spread_of_fire_d + " " + F_Strings.WholeBuildingOrAffectingMoreThan2Floors
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalWholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsUnderPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsUnderPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
            output(dir, F_Strings.s2010_20, F_Strings.OCCUPANCY_TYPE
                    + " " + F_Strings.ThreeOrMoreAdultsWithDependantChildren
                    + " AND "
                    + F_Strings.spread_of_fire_d + " " + F_Strings.WholeBuildingOrAffectingMoreThan2Floors
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalWholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_ThreeOrMoreAdultsWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
            output(dir, F_Strings.s2010_20, F_Strings.OCCUPANCY_TYPE
                    + " " + F_Strings.CoupleBothUnderPensionableAgeWithNoChildren
                    + " AND "
                    + F_Strings.spread_of_fire_d + " " + F_Strings.WholeBuildingOrAffectingMoreThan2Floors
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalWholeBuildingOrAffectingMoreThan2Floors_CoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_CoupleBothUnderPensionableAgeWithNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
            output(dir, F_Strings.s2010_20, F_Strings.OCCUPANCY_TYPE
                    + " " + F_Strings.CoupleOneOrMoreOverPensionableAgeNoChildren
                    + " AND "
                    + F_Strings.spread_of_fire_d + " " + F_Strings.WholeBuildingOrAffectingMoreThan2Floors
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalWholeBuildingOrAffectingMoreThan2Floors_CoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_CoupleOneOrMoreOverPensionableAgeNoChildren_By_BUILDING_OR_PROPERTY_TYPE);
            output(dir, F_Strings.s2010_20, F_Strings.OCCUPANCY_TYPE
                    + " " + F_Strings.CoupleWithDependantChildren
                    + " AND "
                    + F_Strings.spread_of_fire_d + " " + F_Strings.WholeBuildingOrAffectingMoreThan2Floors
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalWholeBuildingOrAffectingMoreThan2Floors_CoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_CoupleWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
            output(dir, F_Strings.s2010_20, F_Strings.OCCUPANCY_TYPE
                    + " " + F_Strings.LoneParentWithDependantChildren
                    + " AND "
                    + F_Strings.spread_of_fire_d + " " + F_Strings.WholeBuildingOrAffectingMoreThan2Floors
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalWholeBuildingOrAffectingMoreThan2Floors_LoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_LoneParentWithDependantChildren_By_BUILDING_OR_PROPERTY_TYPE);
            output(dir, F_Strings.s2010_20, F_Strings.OCCUPANCY_TYPE
                    + " " + F_Strings.LonePersonOverPensionableAge
                    + " AND "
                    + F_Strings.spread_of_fire_d + " " + F_Strings.WholeBuildingOrAffectingMoreThan2Floors
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalWholeBuildingOrAffectingMoreThan2Floors_LonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_LonePersonOverPensionableAge_By_BUILDING_OR_PROPERTY_TYPE);
            output(dir, F_Strings.s2010_20, F_Strings.OCCUPANCY_TYPE
                    + " " + F_Strings.LonePersonUnderPensionableAge
                    + " AND "
                    + F_Strings.spread_of_fire_d + " " + F_Strings.WholeBuildingOrAffectingMoreThan2Floors
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalWholeBuildingOrAffectingMoreThan2Floors_LonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_LonePersonUnderPensionableAge_By_BUILDING_OR_PROPERTY_TYPE);
            // spread_of_fire_d AND CAUSE_OF_FIRE
            output(dir, F_Strings.s2010_20, F_Strings.CAUSE_OF_FIRE
                    + " " + F_Strings.AccumulationOfFlammableMaterial
                    + " AND "
                    + F_Strings.spread_of_fire_d + " " + F_Strings.WholeBuildingOrAffectingMoreThan2Floors
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalWholeBuildingOrAffectingMoreThan2Floors_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE);
            output(dir, F_Strings.s2010_20, F_Strings.CAUSE_OF_FIRE
                    + " " + F_Strings.FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause
                    + " AND "
                    + F_Strings.spread_of_fire_d + " " + F_Strings.WholeBuildingOrAffectingMoreThan2Floors
                    + " BY "
                    + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    F_Strings.BUILDING_OR_PROPERTY_TYPE,
                    varBUILDING_OR_PROPERTY_TYPE,
                    mTotalWholeBuildingOrAffectingMoreThan2Floors_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE,
                    mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE);
            Iterator<Integer> itei = mTotalFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.keySet().iterator();
            while (itei.hasNext()) {
                Integer i = itei.next();
                String tBuildingOrPropertyType = env.data.id2names.get(varBUILDING_OR_PROPERTY_TYPE).get(i);
                dir = getDir(F_Strings.s_Subsets,
                        F_Strings.FATALITY_CASUALTY + "_" + F_Strings.FatalityOrCasualty
                        + "_" + F_Strings.spread_of_fire_d + "_" + F_Strings.WholeBuildingOrAffectingMoreThan2Floors);
                output(dir, F_Strings.s2010_20, tBuildingOrPropertyType,
                        mWholeBuildingOrAffectingMoreThan2FloorsFATALITY_CASUALTY.get(i));
                dir = getDir(F_Strings.s_Subsets,
                        F_Strings.FATALITY_CASUALTY + "_" + F_Strings.None
                        + "_" + F_Strings.spread_of_fire_d + "_" + F_Strings.WholeBuildingOrAffectingMoreThan2Floors);
                output(dir, F_Strings.s2010_20, tBuildingOrPropertyType,
                        mWholeBuildingOrAffectingMoreThan2FloorsNonFATALITY_CASUALTY.get(i));
            }
            dir = getDir(F_Strings.s_Subsets,
                    F_Strings.BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION + " " + F_Strings.Cladding
                    + "_AND_" + F_Strings.spread_of_fire_d + "_" + F_Strings.WholeBuildingOrAffectingMoreThan2Floors);
            output(dir, F_Strings.s2010_20, "highriseFATALITY_CASUALTY", highriseCladdingWholeBuildingOrAffectingMoreThan2FloorsFATALITY_CASUALTY);
            output(dir, F_Strings.s2010_20, "highriseNonFATALITY_CASUALTY", highriseCladdingWholeBuildingOrAffectingMoreThan2FloorsNonFATALITY_CASUALTY);
            output(dir, F_Strings.s2010_20, "mediumriseFATALITY_CASUALTY", mediumriseCladdingWholeBuildingOrAffectingMoreThan2FloorsFATALITY_CASUALTY);
            output(dir, F_Strings.s2010_20, "mediumriseNonFATALITY_CASUALTY", mediumriseCladdingWholeBuildingOrAffectingMoreThan2FloorsNonFATALITY_CASUALTY);
            output(dir, F_Strings.s2010_20, "lowriseFATALITY_CASUALTY", lowriseCladdingWholeBuildingOrAffectingMoreThan2FloorsFATALITY_CASUALTY);
            output(dir, F_Strings.s2010_20, "lowriseNonFATALITY_CASUALTY", lowriseCladdingWholeBuildingOrAffectingMoreThan2FloorsNonFATALITY_CASUALTY);
            output(dir.getParent(), F_Strings.s2010_20, "PurposeBuilt_"
                    + F_Strings.BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION
                    + "_" + F_Strings.NotApplicable, purposeBuilt_BSSMOED_NotApplicable);
            output(dir.getParent(), F_Strings.s2010_20, "FIRE_DANGER_SCORE"
                    + "_GE5", FDScore_GE5);
            output(dir.getParent(), F_Strings.s2010_20, "PurposeBuilt", purposeBuilt);

            // Load EnglishHousingSurvey
            Path indir = Paths.get(System.getProperty("user.home"),
                    F_Strings.s_data, "projects", "Fire", F_Strings.s_data, "input",
                    "EnglishHousingSurvey", "Headline");
            //Path indir = Paths.get(files.getInputDir().toString(),
            //        "EnglishHousingSurvey", "Headline");
            String type = ".xlsx"; // "csv";
            // Load "other building - residential to send.xlsx"
            String sn = "AT2.1";
            int br = 26;
            int ar = 58;
            int col = 9;
            int pbd = 2;
            Path f;
            f = Paths.get(indir.toString(),
                    "2019-20_EHS_Headline_Report_Section_2_Stock_Annex_Tables" + type);
            F_ARecord r1920 = getARecord("2019/20", f, sn, col, br, pbd, ar);
            f = Paths.get(indir.toString(),
                    "2018-19_Section_2_Housing_Stock_Annex_Tables" + type);
            F_ARecord r1819 = getARecord("2018/19", f, sn, col, br, pbd, ar);
            f = Paths.get(indir.toString(),
                    "2017-18_Section_2_Housing_Stock_Annex_Tables" + type);
            F_ARecord r1718 = getARecord("2017/18", f, sn, col, br, pbd, ar);
            f = Paths.get(indir.toString(),
                    "2016-17_Section_2_Housing_Stock_annex_tables" + type);
            F_ARecord r1617 = getARecord("2016/17", f, sn, col, br, pbd, ar);
            f = Paths.get(indir.toString(),
                    "2015-16_Section_2_Housing_Stock_annex_tables" + type);
            F_ARecord r1516 = getARecord("2015/16", f, sn, col, br, pbd, ar);
            f = Paths.get(indir.toString(),
                    "2014-15_Section_2_Housing_Stock_tables_and_figures_FINAL" + type);
            F_ARecord r1415 = getARecord("2014/15", f, sn, col, br, pbd, ar);
            f = Paths.get(indir.toString(),
                    "2013-14_Chapter_1_Tables_Figures_and_Annex_Tables" + type);
            sn = "AT1.1";
            br = 19;
            ar = 29;
            col = 6;
            F_ARecord r1314 = getARecord("2013/14", f, sn, col, br, pbd, ar);
            sn = "AT1.1";
            br = 19;
            ar = 24;
            f = Paths.get(indir.toString(),
                    "2012-13_Chapter_1_Stock_profile_tables_figures_and_annex_tables" + type);
            F_ARecord r1213 = getARecord("2012/13", f, sn, col, br, pbd, ar);
            sn = "AT1.4";
            br = 10;
            ar = 15;
            type = ".xls";
            f = Paths.get(indir.toString(),
                    "2011-12_Chapter_1_Tables__Figures_and_Annex_Tables" + type);
            F_ARecord r1112 = getARecordHSSF("2011/12", f, sn, col, br, pbd, ar);

            sn = "AT1.5";
            ar = 18;
            pbd = 4;
            type = ".xls";
            f = Paths.get(indir.toString(),
                    "2010-11_2173554" + type);
            F_ARecord r1011 = getARecordHSSF("2010/11", f, sn, col, br, pbd, ar);

            // Output
            try {
                dir = Paths.get(env.files.getOutputDir().toString(), "Stuart");
                Files.createDirectories(dir);
                Path p = Paths.get(dir.toString(), "test.csv");
                try (PrintWriter pw = Generic_IO.getPrintWriter(p, false)) {
                    pw.println("FINANCIAL_YEAR"
                            + ",Total dwellings"
                            + ",Total fires"
                            + ",Total fires * 10000 / Total dwellings"
                            + ",Total FATALITY_CASUALTY fires"
                            + ",Total FATALITY_CASUALTY fires as % of Total fires"
                            + ",Total NoCompartmentationInBuilding fires"
                            + ",Total FATALITY_CASUALTY_NoCompartmentationInBuilding fires"
                            + ",Total AffectingWholeBuildingOrMoreThan2Floors fires"
                            + ",Total FATALITY_CASUALTY_AffectingWholeBuildingOrMoreThan2Floors fires"
                            + ",Total Cladding_AffectingWholeBuildingOrMoreThan2Floors fires"
                            + ",Total FATALITY_CASUALTY_Cladding_AffectingWholeBuildingOrMoreThan2Floors fires"
                            + ",Bungalow dwellings"
                            + ",Bungalow % of Total dwellings"
                            + ",Bungalow fires"
                            + ",Bungalow fires as % of Total fires"
                            + ",Bungalow fires * 10000 / Bungalow dwellings"
                            + ",Bungalow FATALITY_CASUALTY fires"
                            + ",Bungalow FATALITY_CASUALTY fires as % of Total fires"
                            + ",Bungalow FATALITY_CASUALTY fires as % of FATALITY_CASUALTY fires"
                            + ",Bungalow FATALITY_CASUALTY fires as % of Bungalow fires"
                            + ",Bungalow NoCompartmentationInBuilding fires"
                            + ",Bungalow FATALITY_CASUALTY_NoCompartmentationInBuilding fires"
                            + ",Bungalow AffectingWholeBuildingOrMoreThan2Floors fires"
                            + ",Bungalow FATALITY_CASUALTY_AffectingWholeBuildingOrMoreThan2Floors fires"
                            + ",Bungalow Cladding_AffectingWholeBuildingOrMoreThan2Floors fires"
                            + ",Bungalow FATALITY_CASUALTY_Cladding_AffectingWholeBuildingOrMoreThan2Floors fires"
                            + ",PB dwellings"
                            + ",PB % of Total dwellings"
                            + ",PB fires"
                            + ",PB fires as % of Total fires"
                            + ",PB fires * 10000 / PB dwellings"
                            + ",PB FATALITY_CASUALTY fires"
                            + ",PB FATALITY_CASUALTY fires as % of Total fires"
                            + ",PB FATALITY_CASUALTY fires as % of FATALITY_CASUALTY fires"
                            + ",PB FATALITY_CASUALTY fires as % of PB fires"
                            + ",PB NoCompartmentationInBuilding fires"
                            + ",PB FATALITY_CASUALTY_NoCompartmentationInBuilding fires"
                            + ",PB AffectingWholeBuildingOrMoreThan2Floors fires"
                            + ",PB FATALITY_CASUALTY_AffectingWholeBuildingOrMoreThan2Floors fires"
                            + ",PB Cladding_AffectingWholeBuildingOrMoreThan2Floors fires"
                            + ",PB FATALITY_CASUALTY_Cladding_AffectingWholeBuildingOrMoreThan2Floors fires"
                            // PBHR
                            + ",PBHR dwellings"
                            + ",PBHR % of Total dwellings"
                            + ",PBHR fires"
                            + ",PBHR fires as % of Total fires"
                            + ",PBHR fires * 10000 / PB dwellings"
                            + ",PBHR FATALITY_CASUALTY fires"
                            + ",PBHR FATALITY_CASUALTY fires as % of Total fires"
                            + ",PBHR FATALITY_CASUALTY fires as % of FATALITY_CASUALTY fires"
                            + ",PBHR FATALITY_CASUALTY fires as % of PBHR fires"
                            + ",PBHR NoCompartmentationInBuilding fires"
                            + ",PBHR FATALITY_CASUALTY_NoCompartmentationInBuilding fires"
                            + ",PBHR AffectingWholeBuildingOrMoreThan2Floors fires"
                            + ",PBHR FATALITY_CASUALTY_AffectingWholeBuildingOrMoreThan2Floors fires"
                            + ",PBHR Cladding_AffectingWholeBuildingOrMoreThan2Floors fires"
                            + ",PBHR FATALITY_CASUALTY_Cladding_AffectingWholeBuildingOrMoreThan2Floors fires"
                            // PBLR
                            + ",PBLR dwellings"
                            + ",PBLR % of Total dwellings"
                            + ",PBLR fires"
                            + ",PBLR fires as % of Total fires"
                            + ",PBLR fires * 10000 / PB dwellings"
                            + ",PBLR FATALITY_CASUALTY fires"
                            + ",PBLR FATALITY_CASUALTY fires as % of Total fires"
                            + ",PBLR FATALITY_CASUALTY fires as % of FATALITY_CASUALTY fires"
                            + ",PBLR FATALITY_CASUALTY fires as % of PBLR fires"
                            + ",PBLR NoCompartmentationInBuilding fires"
                            + ",PBLR FATALITY_CASUALTY_NoCompartmentationInBuilding fires"
                            + ",PBLR AffectingWholeBuildingOrMoreThan2Floors fires"
                            + ",PBLR FATALITY_CASUALTY_AffectingWholeBuildingOrMoreThan2Floors fires"
                            + ",PBLR Cladding_AffectingWholeBuildingOrMoreThan2Floors fires"
                            + ",PBLR FATALITY_CASUALTY_Cladding_AffectingWholeBuildingOrMoreThan2Floors fires"
                    );
                    int yearID;
                    yearID = env.data.name2ids.get(0).get(F_Strings.S2010_11);
                    print(r1011, pw, yearID);
                    yearID = env.data.name2ids.get(0).get(F_Strings.S2011_12);
                    print(r1112, pw, yearID);
                    yearID = env.data.name2ids.get(0).get(F_Strings.S2012_13);
                    print(r1213, pw, yearID);
                    yearID = env.data.name2ids.get(0).get(F_Strings.S2013_14);
                    print(r1314, pw, yearID);
                    yearID = env.data.name2ids.get(0).get(F_Strings.S2014_15);
                    print(r1415, pw, yearID);
                    yearID = env.data.name2ids.get(0).get(F_Strings.S2015_16);
                    print(r1516, pw, yearID);
                    yearID = env.data.name2ids.get(0).get(F_Strings.S2016_17);
                    print(r1617, pw, yearID);
                    yearID = env.data.name2ids.get(0).get(F_Strings.S2017_18);
                    print(r1718, pw, yearID);
                    yearID = env.data.name2ids.get(0).get(F_Strings.S2018_19);
                    print(r1819, pw, yearID);
                    yearID = env.data.name2ids.get(0).get(F_Strings.S2019_20);
                    print(r1920, pw, yearID);
                }
            } catch (IOException ex) {
                Logger.getLogger(F_Main.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        } catch (IOException ex) {
            Logger.getLogger(F_Main.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Adds to counts in m and mFATALITY_CASUALTY
     *
     * @param r
     * @param valFATALITY_CASUALTY
     * @param m
     * @param mFATALITY_CASUALTY
     */
    protected void addToCount(F_Dwellings_Integer_Record0 r,
            int valFATALITY_CASUALTY, HashMap<Integer, Integer> m,
            HashMap<Integer, Integer> mFATALITY_CASUALTY) {
        Generic_Collections.addToCount(m, r.tBUILDING_OR_PROPERTY_TYPE, 1);
        if (r.tFATALITY_CASUALTY != null) { //
            if (r.tFATALITY_CASUALTY == valFATALITY_CASUALTY) {
                Generic_Collections.addToCount(mFATALITY_CASUALTY,
                        r.tBUILDING_OR_PROPERTY_TYPE, 1);
            }
        } else {
            System.out.println(r.toString());
        }
    }

    protected <T extends F_Dwellings_Integer_Record0> void addToCountsAndSets(T r,
            int valFATALITY_CASUALTY,
            int valNight,
            HashMap<Integer, Integer> mNight_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mFATALITY_CASUALTY_Night_By_BUILDING_OR_PROPERTY_TYPE,
            int valNoCompartmentationInBuilding,
            HashMap<Integer, Integer> mNoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mFATALITY_CASUALTY_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mNight_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mFATALITY_CASUALTY_Night_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE,
            int valWholeBuildingOrAffectingMoreThan2Floors,
            HashMap<Integer, Integer> mWholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
            Map<Integer, Set<T>> mWholeBuildingOrAffectingMoreThan2FloorsFATALITY_CASUALTY,
            Map<Integer, Set<T>> mWholeBuildingOrAffectingMoreThan2FloorsNonFATALITY_CASUALTY,
            HashMap<Integer, Integer> mNight_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mFATALITY_CASUALTY_Night_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
            int valCladding,
            HashMap<Integer, Integer> mCladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mFATALITY_CASUALTY_Cladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
            int valPurposeBuiltHighRiseFlats,
            Set<F_Dwellings_Integer_Record1> highriseCladdingWholeBuildingOrAffectingMoreThan2FloorsFATALITY_CASUALTY,
            int valPurposeBuiltMediumRiseFlats,
            Set<F_Dwellings_Integer_Record1> mediumriseCladdingWholeBuildingOrAffectingMoreThan2FloorsFATALITY_CASUALTY,
            int valPurposeBuiltLowRiseFlats,
            Set<F_Dwellings_Integer_Record1> lowriseCladdingWholeBuildingOrAffectingMoreThan2FloorsFATALITY_CASUALTY,
            Set<F_Dwellings_Integer_Record1> highriseCladdingWholeBuildingOrAffectingMoreThan2FloorsNonFATALITY_CASUALTY,
            Set<F_Dwellings_Integer_Record1> mediumriseCladdingWholeBuildingOrAffectingMoreThan2FloorsNonFATALITY_CASUALTY,
            Set<F_Dwellings_Integer_Record1> lowriseCladdingWholeBuildingOrAffectingMoreThan2FloorsNonFATALITY_CASUALTY,
            // START_DELAY_DESCRIPTION
            // BuildingType
            int valSDD_BuildingType,
            HashMap<Integer, Integer> mSDD_BuildingType_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mFATALITY_CASUALTY_SDD_BuildingType_By_BUILDING_OR_PROPERTY_TYPE,
            // LargeSite
            int valSDD_LargeSite,
            HashMap<Integer, Integer> mSDD_LargeSite_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mFATALITY_CASUALTY_SDD_LargeSite_By_BUILDING_OR_PROPERTY_TYPE,
            // Security
            int valSDD_Security,
            HashMap<Integer, Integer> mSDD_Security_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mFATALITY_CASUALTY_SDD_Security_By_BUILDING_OR_PROPERTY_TYPE,
            // Assault
            int valSDD_Assault,
            HashMap<Integer, Integer> mSDD_Assault_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mFATALITY_CASUALTY_SDD_Assault_By_BUILDING_OR_PROPERTY_TYPE,
            // Civil
            int valSDD_Civil,
            HashMap<Integer, Integer> mSDD_Civil_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mFATALITY_CASUALTY_SDD_Civil_By_BUILDING_OR_PROPERTY_TYPE,
            // Location
            int valSDD_Location,
            HashMap<Integer, Integer> mSDD_Location_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mFATALITY_CASUALTY_SDD_Location_By_BUILDING_OR_PROPERTY_TYPE,
            // Wrong
            int valSDD_Wrong,
            HashMap<Integer, Integer> mSDD_Wrong_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mFATALITY_CASUALTY_SDD_Wrong_By_BUILDING_OR_PROPERTY_TYPE,
            // Vehicle
            int valSDD_Vehicle,
            HashMap<Integer, Integer> mSDD_Vehicle_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mFATALITY_CASUALTY_SDD_Vehicle_By_BUILDING_OR_PROPERTY_TYPE,
            // NoDelay
            int valSDD_NoDelay,
            HashMap<Integer, Integer> mSDD_Delay_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mFATALITY_CASUALTY_SDD_Delay_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mSDD_NoDelay_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mFATALITY_CASUALTY_SDD_NoDelay_By_BUILDING_OR_PROPERTY_TYPE,
            // RESCUES
            int valRESCUES_0,
            HashMap<Integer, Integer> mRescues_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mFATALITY_CASUALTY_Rescues_By_BUILDING_OR_PROPERTY_TYPE,
            int tBUILDING_EVACUATION_DELAY_DESCRIPTION,
            HashSet<Integer> svalBUILDING_EVACUATION_DELAY_DESCRIPTION_NoDelay,
            HashMap<Integer, Integer> mDelayEvac_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mFATALITY_CASUALTY_DelayEvac_By_BUILDING_OR_PROPERTY_TYPE,
            int tBUILDING_EVACUATION_TIME_DESCRIPTION,
            HashSet<Integer> svalBUILDING_EVACUATION_TIME_DESCRIPTION_GT5,
            HashMap<Integer, Integer> mEvacGT5_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mFATALITY_CASUALTY_EvacGT5_By_BUILDING_OR_PROPERTY_TYPE,
            int valDelayElderlyOrDisabled,
            HashMap<Integer, Integer> mDelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mFATALITY_CASUALTY_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mEvacGT5_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mFATALITY_CASUALTY_EvacGT5_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE,
            int valAccumulationOfFlammableMaterial,
            HashMap<Integer, Integer> mAccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mFATALITY_CASUALTY_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mWholeBuildingOrAffectingMoreThan2Floors_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE,
            HashSet<Integer> svalFaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause,
            HashMap<Integer, Integer> mFaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mFATALITY_CASUALTY_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mWholeBuildingOrAffectingMoreThan2Floors_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE,
            int valBSSMOEDNotApplicable,
            HashMap<Integer, Integer> mBSSMOEDNotApplicable_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mFATALITY_CASUALTY_BSSMOEDNotApplicable_By_BUILDING_OR_PROPERTY_TYPE,
            //           HashMap<Integer, Integer>  mFIRE_SAFETY_SUCCESS_SCORE_By_BUILDING_OR_PROPERTY_TYPE,
            //                              HashMap<Integer, Integer>   mFATALITY_CASUALTY_FIRE_SAFETY_SUCCESS_SCORE_By_BUILDING_OR_PROPERTY_TYPE,
            //                              HashMap<Integer, Integer>   mFIRE_SAFETY_FAILURE_SCORE_By_BUILDING_OR_PROPERTY_TYPE,
            //                              HashMap<Integer, Integer>   mFATALITY_CASUALTY_FIRE_SAFETY_FAILURE_SCORE_By_BUILDING_OR_PROPERTY_TYPE

            HashMap<Integer, Integer> mHoleyCheese2_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mFATALITY_CASUALTY_HoleyCheese2_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mHoleyCheese3_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mFATALITY_CASUALTY_HoleyCheese3_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mHoleyCheese4_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mFATALITY_CASUALTY_HoleyCheese4_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mDelaysToFireFightingAndNotFirespreadHoleyCheese_By_BUILDING_OR_PROPERTY_TYPE,
            HashMap<Integer, Integer> mFATALITY_CASUALTY_DelaysToFireFightingAndNotFirespreadHoleyCheese_By_BUILDING_OR_PROPERTY_TYPE) {
        if (r.tDAY_NIGHT == valNight) {
            addToCount(r, valFATALITY_CASUALTY,
                    mNight_By_BUILDING_OR_PROPERTY_TYPE,
                    mFATALITY_CASUALTY_Night_By_BUILDING_OR_PROPERTY_TYPE);
        }
        if (r.tBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION == valNoCompartmentationInBuilding) {
            addToCount(r, valFATALITY_CASUALTY,
                    mNoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE,
                    mFATALITY_CASUALTY_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE);
            if (r.tDAY_NIGHT == valNight) {
                addToCount(r, valFATALITY_CASUALTY,
                        mNight_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE,
                        mFATALITY_CASUALTY_Night_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE);
            }
        }
        if (r.tspread_of_fire_d == valWholeBuildingOrAffectingMoreThan2Floors) {
            addToCount(r, valFATALITY_CASUALTY,
                    mWholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
                    mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE);
            if (r.tFATALITY_CASUALTY == valFATALITY_CASUALTY) {
                Generic_Collections.addToMap(mWholeBuildingOrAffectingMoreThan2FloorsFATALITY_CASUALTY,
                        r.tBUILDING_OR_PROPERTY_TYPE, r);
            } else {
                Generic_Collections.addToMap(mWholeBuildingOrAffectingMoreThan2FloorsNonFATALITY_CASUALTY,
                        r.tBUILDING_OR_PROPERTY_TYPE, r);
            }
            if (r.tDAY_NIGHT == valNight) {
                addToCount(r, valFATALITY_CASUALTY,
                        mNight_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
                        mFATALITY_CASUALTY_Night_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE);
            }
            if (r.tBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION == valCladding) {
                addToCount(r, valFATALITY_CASUALTY,
                        mCladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
                        mFATALITY_CASUALTY_Cladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE);
                if (r.tFATALITY_CASUALTY == valFATALITY_CASUALTY) {
                    if (r.tBUILDING_OR_PROPERTY_TYPE == valPurposeBuiltHighRiseFlats) {
                        highriseCladdingWholeBuildingOrAffectingMoreThan2FloorsFATALITY_CASUALTY.add((F_Dwellings_Integer_Record1) r);
                    }
                    if (r.tBUILDING_OR_PROPERTY_TYPE == valPurposeBuiltMediumRiseFlats) {
                        mediumriseCladdingWholeBuildingOrAffectingMoreThan2FloorsFATALITY_CASUALTY.add((F_Dwellings_Integer_Record1) r);
                    }
                    if (r.tBUILDING_OR_PROPERTY_TYPE == valPurposeBuiltLowRiseFlats) {
                        lowriseCladdingWholeBuildingOrAffectingMoreThan2FloorsFATALITY_CASUALTY.add((F_Dwellings_Integer_Record1) r);
                    }
                } else {
                    if (r.tBUILDING_OR_PROPERTY_TYPE == valPurposeBuiltHighRiseFlats) {
                        highriseCladdingWholeBuildingOrAffectingMoreThan2FloorsNonFATALITY_CASUALTY.add((F_Dwellings_Integer_Record1) r);
                    }
                    if (r.tBUILDING_OR_PROPERTY_TYPE == valPurposeBuiltMediumRiseFlats) {
                        mediumriseCladdingWholeBuildingOrAffectingMoreThan2FloorsNonFATALITY_CASUALTY.add((F_Dwellings_Integer_Record1) r);
                    }
                    if (r.tBUILDING_OR_PROPERTY_TYPE == valPurposeBuiltLowRiseFlats) {
                        lowriseCladdingWholeBuildingOrAffectingMoreThan2FloorsNonFATALITY_CASUALTY.add((F_Dwellings_Integer_Record1) r);
                    }
                }
            }
        }
        if (r.tSTARTING_DELAY_DESCRIPTION == valSDD_BuildingType) {
            addToCount(r, valFATALITY_CASUALTY,
                    mSDD_BuildingType_By_BUILDING_OR_PROPERTY_TYPE,
                    mFATALITY_CASUALTY_SDD_BuildingType_By_BUILDING_OR_PROPERTY_TYPE);
        } else if (r.tSTARTING_DELAY_DESCRIPTION == valSDD_LargeSite) {
            addToCount(r, valFATALITY_CASUALTY,
                    mSDD_LargeSite_By_BUILDING_OR_PROPERTY_TYPE,
                    mFATALITY_CASUALTY_SDD_LargeSite_By_BUILDING_OR_PROPERTY_TYPE);
        } else if (r.tSTARTING_DELAY_DESCRIPTION == valSDD_Security) {
            addToCount(r, valFATALITY_CASUALTY,
                    mSDD_Security_By_BUILDING_OR_PROPERTY_TYPE,
                    mFATALITY_CASUALTY_SDD_Security_By_BUILDING_OR_PROPERTY_TYPE);
        } else if (r.tSTARTING_DELAY_DESCRIPTION == valSDD_Civil) {
            addToCount(r, valFATALITY_CASUALTY,
                    mSDD_Civil_By_BUILDING_OR_PROPERTY_TYPE,
                    mFATALITY_CASUALTY_SDD_Civil_By_BUILDING_OR_PROPERTY_TYPE);
        } else if (r.tSTARTING_DELAY_DESCRIPTION == valSDD_Assault) {
            addToCount(r, valFATALITY_CASUALTY,
                    mSDD_Assault_By_BUILDING_OR_PROPERTY_TYPE,
                    mFATALITY_CASUALTY_SDD_Assault_By_BUILDING_OR_PROPERTY_TYPE);
        } else if (r.tSTARTING_DELAY_DESCRIPTION == valSDD_Location) {
            addToCount(r, valFATALITY_CASUALTY,
                    mSDD_Location_By_BUILDING_OR_PROPERTY_TYPE,
                    mFATALITY_CASUALTY_SDD_Location_By_BUILDING_OR_PROPERTY_TYPE);
        } else if (r.tSTARTING_DELAY_DESCRIPTION == valSDD_Wrong) {
            addToCount(r, valFATALITY_CASUALTY,
                    mSDD_Wrong_By_BUILDING_OR_PROPERTY_TYPE,
                    mFATALITY_CASUALTY_SDD_Wrong_By_BUILDING_OR_PROPERTY_TYPE);
        } else if (r.tSTARTING_DELAY_DESCRIPTION == valSDD_Vehicle) {
            addToCount(r, valFATALITY_CASUALTY,
                    mSDD_Vehicle_By_BUILDING_OR_PROPERTY_TYPE,
                    mFATALITY_CASUALTY_SDD_Vehicle_By_BUILDING_OR_PROPERTY_TYPE);
        } else if (r.tSTARTING_DELAY_DESCRIPTION == valSDD_NoDelay) {
            addToCount(r, valFATALITY_CASUALTY,
                    mSDD_NoDelay_By_BUILDING_OR_PROPERTY_TYPE,
                    mFATALITY_CASUALTY_SDD_NoDelay_By_BUILDING_OR_PROPERTY_TYPE);
        } else {
            addToCount(r, valFATALITY_CASUALTY,
                    mSDD_Delay_By_BUILDING_OR_PROPERTY_TYPE,
                    mFATALITY_CASUALTY_SDD_Delay_By_BUILDING_OR_PROPERTY_TYPE);
        }
        if (r.tRESCUES != valRESCUES_0) {
            addToCount(r, valFATALITY_CASUALTY,
                    mRescues_By_BUILDING_OR_PROPERTY_TYPE,
                    mFATALITY_CASUALTY_Rescues_By_BUILDING_OR_PROPERTY_TYPE);
        }
        if (!svalBUILDING_EVACUATION_DELAY_DESCRIPTION_NoDelay.contains(r.tBUILDING_EVACUATION_DELAY_DESCRIPTION)) {
            addToCount(r, valFATALITY_CASUALTY,
                    mDelayEvac_By_BUILDING_OR_PROPERTY_TYPE,
                    mFATALITY_CASUALTY_DelayEvac_By_BUILDING_OR_PROPERTY_TYPE);
        }
        if (svalBUILDING_EVACUATION_TIME_DESCRIPTION_GT5.contains(r.tBUILDING_EVACUATION_TIME_DESCRIPTION)) {
            addToCount(r, valFATALITY_CASUALTY,
                    mEvacGT5_By_BUILDING_OR_PROPERTY_TYPE,
                    mFATALITY_CASUALTY_EvacGT5_By_BUILDING_OR_PROPERTY_TYPE);
        }
        if (r.tBUILDING_EVACUATION_DELAY_DESCRIPTION == valDelayElderlyOrDisabled) {
            addToCount(r, valFATALITY_CASUALTY,
                    mDelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE,
                    mFATALITY_CASUALTY_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE);
            if (svalBUILDING_EVACUATION_TIME_DESCRIPTION_GT5.contains(r.tBUILDING_EVACUATION_TIME_DESCRIPTION)) {
                addToCount(r, valFATALITY_CASUALTY,
                        mEvacGT5_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE,
                        mFATALITY_CASUALTY_EvacGT5_DelayElderlyOrDisabled_By_BUILDING_OR_PROPERTY_TYPE);
            }
        }
        if (r.tCAUSE_OF_FIRE == valAccumulationOfFlammableMaterial) {
            addToCount(r, valFATALITY_CASUALTY,
                    mAccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE,
                    mFATALITY_CASUALTY_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE);
            if (r.tspread_of_fire_d == valWholeBuildingOrAffectingMoreThan2Floors) {
                addToCount(r, valFATALITY_CASUALTY,
                        mWholeBuildingOrAffectingMoreThan2Floors_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE,
                        mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_AccumulationOfFlammableMaterial_By_BUILDING_OR_PROPERTY_TYPE);
            }
        }
        if (svalFaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause.contains(r.tCAUSE_OF_FIRE)) {
            addToCount(r, valFATALITY_CASUALTY,
                    mFaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE,
                    mFATALITY_CASUALTY_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE);
            if (r.tspread_of_fire_d == valWholeBuildingOrAffectingMoreThan2Floors) {
                addToCount(r, valFATALITY_CASUALTY,
                        mWholeBuildingOrAffectingMoreThan2Floors_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE,
                        mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause_By_BUILDING_OR_PROPERTY_TYPE);
            }
        }
        if (r.tBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION == valBSSMOEDNotApplicable) {
            addToCount(r, valFATALITY_CASUALTY,
                    mBSSMOEDNotApplicable_By_BUILDING_OR_PROPERTY_TYPE,
                    mFATALITY_CASUALTY_BSSMOEDNotApplicable_By_BUILDING_OR_PROPERTY_TYPE);
        }
        if (null != r.holeyCheese) {
            switch (r.holeyCheese) {
                case 2:
                    addToCount(r, valFATALITY_CASUALTY,
                            mHoleyCheese2_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_HoleyCheese2_By_BUILDING_OR_PROPERTY_TYPE);
                    break;
                case 3:
                    addToCount(r, valFATALITY_CASUALTY,
                            mHoleyCheese3_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_HoleyCheese3_By_BUILDING_OR_PROPERTY_TYPE);
                    break;
                case 4:
                    addToCount(r, valFATALITY_CASUALTY,
                            mHoleyCheese4_By_BUILDING_OR_PROPERTY_TYPE,
                            mFATALITY_CASUALTY_HoleyCheese4_By_BUILDING_OR_PROPERTY_TYPE);
                    break;
                default:
                    break;
            }
        }
        if (r.delaysToFireFightingAndNotFirespreadHoleyCheese == 1) {
            addToCount(r, valFATALITY_CASUALTY,
                    mDelaysToFireFightingAndNotFirespreadHoleyCheese_By_BUILDING_OR_PROPERTY_TYPE,
                    mFATALITY_CASUALTY_DelaysToFireFightingAndNotFirespreadHoleyCheese_By_BUILDING_OR_PROPERTY_TYPE);
        }
    }

    public Path getDir(String dirName0, String dirName1) throws IOException {
        Path dir = Paths.get(env.files.getOutputDir().toString(),
                F_Files.getFormattedFilenameString(dirName0),
                F_Files.getFormattedFilenameString(dirName1));
        try {
            Files.createDirectories(dir);
        } catch (IOException ex) {
            Logger.getLogger(F_Main.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return dir;
    }

    public void print(F_ARecord r, PrintWriter pw, int yearID) {
        Map<Integer, Integer> mFATALITY_CASUALTY = mAllFATALITY_CASUALTY.get(yearID);
        Map<Integer, Integer> mBUILDING_OR_PROPERTY_TYPE = mAllBUILDING_OR_PROPERTY_TYPE.get(yearID);
        Map<Integer, Integer> mFATALITY_CASUALTY_BUILDING_OR_PROPERTY_TYPE = mAllFATALITY_CASUALTY_BUILDING_OR_PROPERTY_TYPE.get(yearID);
        Map<Integer, Integer> mNoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE = mAllNoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE.get(yearID);
        Map<Integer, Integer> mFATALITY_CASUALTY_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE = mAllFATALITY_CASUALTY_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE.get(yearID);
        Map<Integer, Integer> mWholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE = mAllWholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.get(yearID);
        Map<Integer, Integer> mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE = mAllFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.get(yearID);
        Map<Integer, Integer> mCladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE = mAllCladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.get(yearID);
        Map<Integer, Integer> mFATALITY_CASUALTY_Cladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE = mAllFATALITY_CASUALTY_Cladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.get(yearID);
        int mediumRise45 = mMediumRise45.get(yearID);
        int mediumRise45FC = mMediumRise45_FATALITY_CASUALTY.get(yearID);
        int mediumRise45NC = mMediumRise45_NoCompartmentation.get(yearID);
        int mediumRise45NCFC = mMediumRise45_NoCompartmentation_FATALITY_CASUALTY.get(yearID);
        int mediumRise45AM2 = mMediumRise45_WholeBuildingOrAffectingMoreThan2Floors.get(yearID);
        int mediumRise45AM2FC = mMediumRise45_WholeBuildingOrAffectingMoreThan2Floors_FATALITY_CASUALTY.get(yearID);
        int mediumRise45CAM2 = mMediumRise45_Cladding_WholeBuildingOrAffectingMoreThan2Floors.get(yearID);
        int mediumRise45CAM2FC = mMediumRise45_Cladding_WholeBuildingOrAffectingMoreThan2Floors_FATALITY_CASUALTY.get(yearID);
        int mediumRise6789 = mMediumRise6789.get(yearID);
        int mediumRise6789FC = mMediumRise6789_FATALITY_CASUALTY.get(yearID);
        int mediumRise6789NC = mMediumRise6789_NoCompartmentation.get(yearID);
        int mediumRise6789NCFC = mMediumRise6789_NoCompartmentation_FATALITY_CASUALTY.get(yearID);
        int mediumRise6789AM2 = mMediumRise6789_WholeBuildingOrAffectingMoreThan2Floors.get(yearID);
        int mediumRise6789AM2FC = mMediumRise6789_WholeBuildingOrAffectingMoreThan2Floors_FATALITY_CASUALTY.get(yearID);
        int mediumRise6789CAM2 = mMediumRise6789_Cladding_WholeBuildingOrAffectingMoreThan2Floors.get(yearID);
        int mediumRise6789CAM2FC = mMediumRise6789_Cladding_WholeBuildingOrAffectingMoreThan2Floors_FATALITY_CASUALTY.get(yearID);

        int total = 0;
        Iterator<Integer> ite;
        ite = mFATALITY_CASUALTY.keySet().iterator();
        while (ite.hasNext()) {
            Integer k = ite.next();
            Integer v;
            v = mFATALITY_CASUALTY.get(k);
            if (v != null) {
                total += v;
            }
        }
        int totalNC = 0;
        int totalFCNC = 0;
        int totalAM2 = 0;
        int totalFCAM2 = 0;
        int totalCAM2 = 0;
        int totalFCCAM2 = 0;
        Integer v;
        ite = mBUILDING_OR_PROPERTY_TYPE.keySet().iterator();
        while (ite.hasNext()) {
            Integer k = ite.next();
            v = mNoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE.get(k);
            if (v != null) {
                totalNC += v;
            }
            v = mFATALITY_CASUALTY_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE.get(k);
            if (v != null) {
                totalFCNC += v;
            }
            v = mWholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.get(k);
            if (v != null) {
                totalAM2 += v;
            }
            v = mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.get(k);
            if (v != null) {
                totalFCAM2 += v;
            }
            v = mCladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.get(k);
            if (v != null) {
                totalCAM2 += v;
            }
            v = mFATALITY_CASUALTY_Cladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.get(k);
            if (v != null) {
                totalFCCAM2 += v;
            }
        }
        int varFATALITY_CASUALTY = env.data.vname2id.get(F_Strings.FATALITY_CASUALTY);
        int valFATALITY_CASUALTY = env.data.name2ids.get(varFATALITY_CASUALTY).get(F_Strings.FatalityOrCasualty);
        int totalFC = mFATALITY_CASUALTY.get(valFATALITY_CASUALTY);
        int varBUILDING_OR_PROPERTY_TYPE = env.data.vname2id.get(F_Strings.BUILDING_OR_PROPERTY_TYPE);
        int valBungalow = env.data.name2ids.get(varBUILDING_OR_PROPERTY_TYPE).get(F_Strings.BungalowSingleOccupancy);
        int valPurposeBuiltHighRiseFlats = env.data.name2ids.get(varBUILDING_OR_PROPERTY_TYPE).get(F_Strings.PurposeBuiltHighRiseFlats);
        int valPurposeBuiltMediumRiseFlats = env.data.name2ids.get(varBUILDING_OR_PROPERTY_TYPE).get(F_Strings.PurposeBuiltMediumRiseFlats);
        int valPurposeBuiltLowRiseFlats = env.data.name2ids.get(varBUILDING_OR_PROPERTY_TYPE).get(F_Strings.PurposeBuiltLowRiseFlats);
        int b = mBUILDING_OR_PROPERTY_TYPE.get(valBungalow);
        int bFC = 0;
        v = mFATALITY_CASUALTY_BUILDING_OR_PROPERTY_TYPE.get(valBungalow);
        if (v != null) {
            bFC += v;
        }
        int bNC = 0;
        v = mNoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE.get(valBungalow);
        if (v != null) {
            bNC += v;
        }
        int bFCNC = 0;
        v = mFATALITY_CASUALTY_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE.get(valBungalow);
        if (v != null) {
            bFCNC += v;
        }
        int bAM2 = 0;
        v = mWholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.get(valBungalow);
        if (v != null) {
            bAM2 += v;
        }
        int bFCAM2 = 0;
        v = mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.get(valBungalow);
        if (v != null) {
            bFCAM2 += v;
        }
        int bCAM2 = 0;
        v = mCladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.get(valBungalow);
        if (v != null) {
            bCAM2 += v;
        }
        int bFCCAM2 = 0;
        v = mFATALITY_CASUALTY_Cladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.get(valBungalow);
        if (v != null) {
            bFCCAM2 += v;
        }
        // PB
        int pb = mBUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltHighRiseFlats)
                + mBUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltMediumRiseFlats)
                + mBUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltLowRiseFlats);
        int pbFC = mFATALITY_CASUALTY_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltHighRiseFlats)
                + mFATALITY_CASUALTY_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltMediumRiseFlats)
                + mFATALITY_CASUALTY_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltLowRiseFlats);
        int rpb = r.purposeBuiltFlatHighRise + r.purposeBuiltFlatLowRise;
        int pbNC = 0;
        v = mNoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltHighRiseFlats);
        if (v != null) {
            pbNC += v;
        }
        v = mNoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltMediumRiseFlats);
        if (v != null) {
            pbNC += v;
        }
        v = mNoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltLowRiseFlats);
        if (v != null) {
            pbNC += v;
        }
        int pbFCNC = 0;
        v = mFATALITY_CASUALTY_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltHighRiseFlats);
        if (v != null) {
            pbFCNC += v;
        }
        v = mFATALITY_CASUALTY_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltMediumRiseFlats);
        if (v != null) {
            pbFCNC += v;
        }
        v = mFATALITY_CASUALTY_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltLowRiseFlats);
        if (v != null) {
            pbFCNC += v;
        }
        int pbAM2 = 0;
        v = mWholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltHighRiseFlats);
        if (v != null) {
            pbAM2 += v;
        }
        v = mWholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltMediumRiseFlats);
        if (v != null) {
            pbAM2 += v;
        }
        v = mWholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltLowRiseFlats);
        if (v != null) {
            pbAM2 += v;
        }
        int pbFCAM2 = 0;
        v = mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltHighRiseFlats);
        if (v != null) {
            pbFCAM2 += v;
        }
        v = mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltMediumRiseFlats);
        if (v != null) {
            pbFCAM2 += v;
        }
        v = mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltLowRiseFlats);
        if (v != null) {
            pbFCAM2 += v;
        }
        int pbCAM2 = 0;
        v = mCladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltHighRiseFlats);
        if (v != null) {
            pbCAM2 += v;
        }
        v = mCladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltMediumRiseFlats);
        if (v != null) {
            pbCAM2 += v;
        }
        v = mCladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltLowRiseFlats);
        if (v != null) {
            pbCAM2 += v;
        }
        int pbFCCAM2 = 0;
        v = mFATALITY_CASUALTY_Cladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltHighRiseFlats);
        if (v != null) {
            pbFCCAM2 += v;
        }
        v = mFATALITY_CASUALTY_Cladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltMediumRiseFlats);
        if (v != null) {
            pbFCCAM2 += v;
        }
        v = mFATALITY_CASUALTY_Cladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltLowRiseFlats);
        if (v != null) {
            pbFCCAM2 += v;
        }
        // PBHR
        int pbhr = mBUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltHighRiseFlats) + mediumRise6789;
        int pbhrFC = mFATALITY_CASUALTY_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltHighRiseFlats)
                + mediumRise6789FC;
        int rpbhr = r.purposeBuiltFlatHighRise;
        int pbhrNC = mediumRise6789NC;
        v = mNoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltHighRiseFlats);
        if (v != null) {
            pbhrNC += v;
        }
        int pbhrFCNC = mediumRise6789NCFC;
        v = mFATALITY_CASUALTY_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltHighRiseFlats);
        if (v != null) {
            pbhrFCNC += v;
        }
        int pbhrAM2 = mediumRise6789AM2;
        v = mWholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltHighRiseFlats);
        if (v != null) {
            pbhrAM2 += v;
        }
        int pbhrFCAM2 = mediumRise6789AM2FC;
        v = mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltHighRiseFlats);
        if (v != null) {
            pbhrFCAM2 += v;
        }
        int pbhrCAM2 = mediumRise6789CAM2;
        v = mCladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltHighRiseFlats);
        if (v != null) {
            pbhrCAM2 += v;
        }
        int pbhrFCCAM2 = mediumRise6789CAM2FC;
        v = mFATALITY_CASUALTY_Cladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltHighRiseFlats);
        if (v != null) {
            pbhrFCCAM2 += v;
        }
        // PBLR 
        int pblr = mBUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltLowRiseFlats) + mediumRise45;
        int pblrFC = mFATALITY_CASUALTY_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltLowRiseFlats)
                + mediumRise45FC;
        int rpblr = r.purposeBuiltFlatLowRise;
        int pblrNC = mediumRise45NC;
        v = mNoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltLowRiseFlats);
        if (v != null) {
            pblrNC += v;
        }
        int pblrFCNC = mediumRise45NCFC;
        v = mFATALITY_CASUALTY_NoCompartmentationInBuilding_By_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltLowRiseFlats);
        if (v != null) {
            pblrFCNC += v;
        }
        int pblrAM2 = mediumRise45AM2;
        v = mWholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltLowRiseFlats);
        if (v != null) {
            pblrAM2 += v;
        }
        int pblrFCAM2 = mediumRise45AM2FC;
        v = mFATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltLowRiseFlats);
        if (v != null) {
            pblrFCAM2 += v;
        }
        int pblrCAM2 = mediumRise45CAM2;
        v = mCladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltLowRiseFlats);
        if (v != null) {
            pblrCAM2 += v;
        }
        int pblrFCCAM2 = mediumRise45CAM2FC;
        v = mFATALITY_CASUALTY_Cladding_WholeBuildingOrAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE.get(valPurposeBuiltLowRiseFlats);
        if (v != null) {
            pblrFCCAM2 += v;
        }
        pw.println(r.year
                + "," + r.allDwellings
                + "," + total // Total fires
                + "," + (total * 10000 / (double) r.allDwellings)
                + "," + totalFC // FATALITY_CASUALTY fires
                + "," + (totalFC * 100) / (double) total // FATALITY_CASUALTY fires as % of Total fires
                + "," + totalNC // NoCompartmentationInBuilding
                + "," + totalFCNC // FATALITY_CASUALTY_NoCompartmentationInBuilding
                + "," + totalAM2 // WholeBuildingOrAffectingMoreThan2Floors
                + "," + totalFCAM2 // FATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors
                + "," + totalCAM2 // Cladding_WholeBuildingOrAffectingMoreThan2Floors
                + "," + totalFCCAM2 // FATALITY_CASUALTY_Cladding_WholeBuildingOrAffectingMoreThan2Floors
                + "," + r.totalBungalows // Bungalow dwellings
                + "," + (r.totalBungalows * 100) / (double) r.allDwellings // Bungalow % of Total dwellings
                + "," + b // Bungalow fires
                + "," + (b * 100) / (double) total // Bungalow fires as % of Total fires
                + "," + (b * 10000) / (double) r.totalBungalows // Bungalow fires * 10000 / Bungalow dwellings
                + "," + bFC // Bungalow FATALITY_CASUALTY fires
                + "," + (bFC * 100) / (double) total // Bungalow FATALITY_CASUALTY fires as % of Total fires
                + "," + (bFC * 100) / (double) totalFC // Bungalow FATALITY_CASUALTY fires as % of FATALITY_CASUALTY fires
                + "," + (bFC * 100) / (double) b // Bungalow FATALITY_CASUALTY fires as % of Bungalow fires
                + "," + bNC // Bungalow NoCompartmentationInBuilding
                + "," + bFCNC // Bungalow FATALITY_CASUALTY_NoCompartmentationInBuilding
                + "," + bAM2 // Bungalow WholeBuildingOrAffectingMoreThan2Floors
                + "," + bFCAM2 // Bungalow FATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors
                + "," + bCAM2 // Bungalow Cladding_WholeBuildingOrAffectingMoreThan2Floors
                + "," + bFCCAM2 // Bungalow FATALITY_CASUALTY_Cladding_WholeBuildingOrAffectingMoreThan2Floors
                + "," + rpb // PB dwellings
                + "," + (rpb * 100) / (double) r.allDwellings // PB % of Total dwellings
                + "," + pb // PB fires
                + "," + (pb * 100) / (double) total // PB fires as % of Total fires
                + "," + (pb * 10000) / (double) rpb
                + "," + pbFC // PB FATALITY_CASUALTY fires
                + "," + (pbFC * 100) / (double) total // PB FATALITY_CASUALTY fires as % of Total fires
                + "," + (pbFC * 100) / (double) totalFC // PB FATALITY_CASUALTY fires as % of FATALITY_CASUALTY fires
                + "," + (pbFC * 100) / (double) pb // PB FATALITY_CASUALTY fires as % of PB fires
                + "," + pbNC // PB NoCompartmentationInBuilding
                + "," + pbFCNC // PB FATALITY_CASUALTY_NoCompartmentationInBuilding
                + "," + pbAM2 // PB WholeBuildingOrAffectingMoreThan2Floors
                + "," + pbFCAM2 // PB FATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors
                + "," + pbCAM2 // PB Cladding_WholeBuildingOrAffectingMoreThan2Floors
                + "," + pbFCCAM2 // PB FATALITY_CASUALTY_Cladding_WholeBuildingOrAffectingMoreThan2Floors
                // PBHR
                + "," + rpbhr // PBHR dwellings
                + "," + (rpbhr * 100) / (double) r.allDwellings // PBHR % of Total dwellings
                + "," + pbhr // PBHR fires
                + "," + (pbhr * 100) / (double) total // PBHR fires as % of Total fires
                + "," + (pbhr * 10000) / (double) rpbhr
                + "," + pbhrFC // PBHR FATALITY_CASUALTY fires
                + "," + (pbhrFC * 100) / (double) total // PBHR FATALITY_CASUALTY fires as % of Total fires
                + "," + (pbhrFC * 100) / (double) totalFC // PBHR FATALITY_CASUALTY fires as % of FATALITY_CASUALTY fires
                + "," + (pbhrFC * 100) / (double) pbhr // PBHR FATALITY_CASUALTY fires as % of PBHR fires
                + "," + pbhrNC // PBHR NoCompartmentationInBuilding
                + "," + pbhrFCNC // PBHR FATALITY_CASUALTY_NoCompartmentationInBuilding
                + "," + pbhrAM2 // PBHR WholeBuildingOrAffectingMoreThan2Floors
                + "," + pbhrFCAM2 // PBHR FATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors
                + "," + pbhrCAM2 // PBHR Cladding_WholeBuildingOrAffectingMoreThan2Floors
                + "," + pbhrFCCAM2 // PBHR FATALITY_CASUALTY_Cladding_WholeBuildingOrAffectingMoreThan2Floors
                // PBLR
                + "," + rpblr // PBLR dwellings
                + "," + (rpblr * 100) / (double) r.allDwellings // PBLR % of Total dwellings
                + "," + pblr // PBLR fires
                + "," + (pblr * 100) / (double) total // PBLR fires as % of Total fires
                + "," + (pblr * 10000) / (double) rpblr
                + "," + pblrFC // PBLR FATALITY_CASUALTY fires
                + "," + (pblrFC * 100) / (double) total // PBLR FATALITY_CASUALTY fires as % of Total fires
                + "," + (pblrFC * 100) / (double) totalFC // PBLR FATALITY_CASUALTY fires as % of FATALITY_CASUALTY fires
                + "," + (pblrFC * 100) / (double) pblr // PBLR FATALITY_CASUALTY fires as % of PBLR fires
                + "," + pblrNC // PBLR NoCompartmentationInBuilding
                + "," + pblrFCNC // PBLR FATALITY_CASUALTY_NoCompartmentationInBuilding
                + "," + pblrAM2 // PBLR WholeBuildingOrAffectingMoreThan2Floors
                + "," + pblrFCAM2 // PBLR FATALITY_CASUALTY_WholeBuildingOrAffectingMoreThan2Floors
                + "," + pblrCAM2 // PBLR Cladding_WholeBuildingOrAffectingMoreThan2Floors
                + "," + pblrFCCAM2 // PBLR FATALITY_CASUALTY_Cladding_WholeBuildingOrAffectingMoreThan2Floors
        );
    }

    public void outputSingleVariableCountSummaries(String name) {
        try {
            int varFATALITY_CASUALTY = env.data.vname2id.get(F_Strings.FATALITY_CASUALTY);
            int valFATALITY_CASUALTY = env.data.name2ids.get(varFATALITY_CASUALTY).get(F_Strings.FatalityOrCasualty);
            // 1 Variable
            int allTotal = 0;
            Iterator<F_CollectionID> ite = env.data.data.keySet().iterator();
            while (ite.hasNext()) {
                F_CollectionID cid = ite.next();
                F_Collection c = env.data.getCollection(cid);
                String tFINANCIAL_YEAR = env.data.id2names.get(0).get(cid.id);
                // TOTAL
                int total = 0;
                // FATALITY_CASUALTY
                HashMap<Integer, Integer> mFATALITY_CASUALTY = new HashMap<>();
                mAllFATALITY_CASUALTY.put(cid.id, mFATALITY_CASUALTY);
                // FRS_NAME
                HashMap<Integer, Integer> mFRS_NAME = new HashMap<>();
                mAllFRS_NAME.put(cid.id, mFRS_NAME);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_FRS_NAME = new HashMap<>();
                mAllFATALITY_CASUALTY_FRS_NAME.put(cid.id, mFATALITY_CASUALTY_FRS_NAME);
                // E_CODE
                HashMap<Integer, Integer> mE_CODE = new HashMap<>();
                mAllE_CODE.put(cid.id, mE_CODE);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_E_CODE = new HashMap<>();
                mAllFATALITY_CASUALTY_E_CODE.put(cid.id, mFATALITY_CASUALTY_E_CODE);
                // MONTH_NAME
                HashMap<Integer, Integer> mMONTH_NAME = new HashMap<>();
                mAllMONTH_NAME.put(cid.id, mMONTH_NAME);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_MONTH_NAME = new HashMap<>();
                mAllFATALITY_CASUALTY_MONTH_NAME.put(cid.id, mFATALITY_CASUALTY_MONTH_NAME);
                // WEEKDAY_WEEKEND
                HashMap<Integer, Integer> mWEEKDAY_WEEKEND = new HashMap<>();
                mAllWEEKDAY_WEEKEND.put(cid.id, mWEEKDAY_WEEKEND);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_WEEKDAY_WEEKEND = new HashMap<>();
                mAllFATALITY_CASUALTY_WEEKDAY_WEEKEND.put(cid.id, mFATALITY_CASUALTY_WEEKDAY_WEEKEND);
                // DAY_NIGHT
                HashMap<Integer, Integer> mDAY_NIGHT = new HashMap<>();
                mAllDAY_NIGHT.put(cid.id, mDAY_NIGHT);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_DAY_NIGHT = new HashMap<>();
                mAllFATALITY_CASUALTY_DAY_NIGHT.put(cid.id, mFATALITY_CASUALTY_DAY_NIGHT);
                // BUILDING_OR_PROPERTY_TYPE
                HashMap<Integer, Integer> mBUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                mAllBUILDING_OR_PROPERTY_TYPE.put(cid.id, mBUILDING_OR_PROPERTY_TYPE);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
                mAllFATALITY_CASUALTY_BUILDING_OR_PROPERTY_TYPE.put(cid.id, mFATALITY_CASUALTY_BUILDING_OR_PROPERTY_TYPE);
                // LATE_CALL
                HashMap<Integer, Integer> mLATE_CALL = new HashMap<>();
                mAllLATE_CALL.put(cid.id, mLATE_CALL);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_LATE_CALL = new HashMap<>();
                mAllFATALITY_CASUALTY_LATE_CALL.put(cid.id, mFATALITY_CASUALTY_LATE_CALL);
                // MULTI_SEATED_FLAG
                HashMap<Integer, Integer> mMULTI_SEATED_FLAG = new HashMap<>();
                mAllMULTI_SEATED_FLAG.put(cid.id, mMULTI_SEATED_FLAG);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_MULTI_SEATED_FLAG = new HashMap<>();
                mAllFATALITY_CASUALTY_MULTI_SEATED_FLAG.put(cid.id, mFATALITY_CASUALTY_MULTI_SEATED_FLAG);
                // IGNITION_TO_DISCOVERY
                HashMap<Integer, Integer> mIGNITION_TO_DISCOVERY = new HashMap<>();
                mAllIGNITION_TO_DISCOVERY.put(cid.id, mIGNITION_TO_DISCOVERY);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_IGNITION_TO_DISCOVERY = new HashMap<>();
                mAllFATALITY_CASUALTY_IGNITION_TO_DISCOVERY.put(cid.id, mFATALITY_CASUALTY_IGNITION_TO_DISCOVERY);
                // DISCOVERY_TO_CALL
                HashMap<Integer, Integer> mDISCOVERY_TO_CALL = new HashMap<>();
                mAllDISCOVERY_TO_CALL.put(cid.id, mDISCOVERY_TO_CALL);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_DISCOVERY_TO_CALL = new HashMap<>();
                mAllFATALITY_CASUALTY_DISCOVERY_TO_CALL.put(cid.id, mFATALITY_CASUALTY_DISCOVERY_TO_CALL);
                // HOW_DISCOVERED_DESCRIPTION
                HashMap<Integer, Integer> mHOW_DISCOVERED_DESCRIPTION = new HashMap<>();
                mAllHOW_DISCOVERED_DESCRIPTION.put(cid.id, mHOW_DISCOVERED_DESCRIPTION);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_HOW_DISCOVERED_DESCRIPTION = new HashMap<>();
                mAllFATALITY_CASUALTY_HOW_DISCOVERED_DESCRIPTION.put(cid.id, mFATALITY_CASUALTY_HOW_DISCOVERED_DESCRIPTION);
                // BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION
                HashMap<Integer, Integer> mBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION = new HashMap<>();
                mAllBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION.put(cid.id, mBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION = new HashMap<>();
                mAllFATALITY_CASUALTY_BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION.put(cid.id, mFATALITY_CASUALTY_BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION);
                // BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION
                HashMap<Integer, Integer> mBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION = new HashMap<>();
                mAllBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION.put(cid.id, mBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION = new HashMap<>();
                mAllFATALITY_CASUALTY_BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION.put(cid.id, mFATALITY_CASUALTY_BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION);
                // BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT
                HashMap<Integer, Integer> mBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT = new HashMap<>();
                mAllBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT.put(cid.id, mBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT = new HashMap<>();
                mAllFATALITY_CASUALTY_BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT.put(cid.id, mFATALITY_CASUALTY_BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT);
                // OCCUPIED_NORMAL
                HashMap<Integer, Integer> mOCCUPIED_NORMAL = new HashMap<>();
                mAllOCCUPIED_NORMAL.put(cid.id, mOCCUPIED_NORMAL);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_OCCUPIED_NORMAL = new HashMap<>();
                mAllFATALITY_CASUALTY_OCCUPIED_NORMAL.put(cid.id, mFATALITY_CASUALTY_OCCUPIED_NORMAL);
                // ACCIDENTAL_OR_DELIBERATE
                HashMap<Integer, Integer> mACCIDENTAL_OR_DELIBERATE = new HashMap<>();
                mAllACCIDENTAL_OR_DELIBERATE.put(cid.id, mACCIDENTAL_OR_DELIBERATE);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_ACCIDENTAL_OR_DELIBERATE = new HashMap<>();
                mAllFATALITY_CASUALTY_ACCIDENTAL_OR_DELIBERATE.put(cid.id, mFATALITY_CASUALTY_ACCIDENTAL_OR_DELIBERATE);
                // VEHICLES
                HashMap<Integer, Integer> mVEHICLES = new HashMap<>();
                mAllVEHICLES.put(cid.id, mVEHICLES);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_VEHICLES = new HashMap<>();
                mAllFATALITY_CASUALTY_VEHICLES.put(cid.id, mFATALITY_CASUALTY_VEHICLES);
                // VEHICLES_CODE
                HashMap<Integer, Integer> mVEHICLES_CODE = new HashMap<>();
                mAllVEHICLES_CODE.put(cid.id, mVEHICLES_CODE);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_VEHICLES_CODE = new HashMap<>();
                mAllFATALITY_CASUALTY_VEHICLES_CODE.put(cid.id, mFATALITY_CASUALTY_VEHICLES_CODE);
                // PERSONNEL
                HashMap<Integer, Integer> mPERSONNEL = new HashMap<>();
                mAllPERSONNEL.put(cid.id, mPERSONNEL);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_PERSONNEL = new HashMap<>();
                mAllFATALITY_CASUALTY_PERSONNEL.put(cid.id, mFATALITY_CASUALTY_PERSONNEL);
                // PERSONNEL_CODE
                HashMap<Integer, Integer> mPERSONNEL_CODE = new HashMap<>();
                mAllPERSONNEL_CODE.put(cid.id, mPERSONNEL_CODE);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_PERSONNEL_CODE = new HashMap<>();
                mAllFATALITY_CASUALTY_PERSONNEL_CODE.put(cid.id, mFATALITY_CASUALTY_PERSONNEL_CODE);
                // STARTING_DELAY_DESCRIPTION
                HashMap<Integer, Integer> mSTARTING_DELAY_DESCRIPTION = new HashMap<>();
                mAllSTARTING_DELAY_DESCRIPTION.put(cid.id, mSTARTING_DELAY_DESCRIPTION);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_STARTING_DELAY_DESCRIPTION = new HashMap<>();
                mAllFATALITY_CASUALTY_STARTING_DELAY_DESCRIPTION.put(cid.id, mFATALITY_CASUALTY_STARTING_DELAY_DESCRIPTION);
                // ACTION_NON_FRS_DESCRIPTION
                HashMap<Integer, Integer> mACTION_NON_FRS_DESCRIPTION = new HashMap<>();
                mAllACTION_NON_FRS_DESCRIPTION.put(cid.id, mACTION_NON_FRS_DESCRIPTION);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_ACTION_NON_FRS_DESCRIPTION = new HashMap<>();
                mAllFATALITY_CASUALTY_ACTION_NON_FRS_DESCRIPTION.put(cid.id, mFATALITY_CASUALTY_ACTION_NON_FRS_DESCRIPTION);
                // ACTION_FRS_DESCRIPTION
                HashMap<Integer, Integer> mACTION_FRS_DESCRIPTION = new HashMap<>();
                mAllACTION_FRS_DESCRIPTION.put(cid.id, mACTION_FRS_DESCRIPTION);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_ACTION_FRS_DESCRIPTION = new HashMap<>();
                mAllFATALITY_CASUALTY_ACTION_FRS_DESCRIPTION.put(cid.id, mFATALITY_CASUALTY_ACTION_FRS_DESCRIPTION);
                // CAUSE_OF_FIRE
                HashMap<Integer, Integer> mCAUSE_OF_FIRE = new HashMap<>();
                mAllCAUSE_OF_FIRE.put(cid.id, mCAUSE_OF_FIRE);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_CAUSE_OF_FIRE = new HashMap<>();
                mAllFATALITY_CASUALTY_CAUSE_OF_FIRE.put(cid.id, mFATALITY_CASUALTY_CAUSE_OF_FIRE);
                // IGNITION_POWER
                HashMap<Integer, Integer> mIGNITION_POWER = new HashMap<>();
                mAllIGNITION_POWER.put(cid.id, mIGNITION_POWER);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_IGNITION_POWER = new HashMap<>();
                mAllFATALITY_CASUALTY_IGNITION_POWER.put(cid.id, mFATALITY_CASUALTY_IGNITION_POWER);
                // SOURCE_OF_IGNITION
                HashMap<Integer, Integer> mSOURCE_OF_IGNITION = new HashMap<>();
                mAllSOURCE_OF_IGNITION.put(cid.id, mSOURCE_OF_IGNITION);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_SOURCE_OF_IGNITION = new HashMap<>();
                mAllFATALITY_CASUALTY_SOURCE_OF_IGNITION.put(cid.id, mFATALITY_CASUALTY_SOURCE_OF_IGNITION);
                // ITEM_IGNITED
                HashMap<Integer, Integer> mITEM_IGNITED = new HashMap<>();
                mAllITEM_IGNITED.put(cid.id, mITEM_IGNITED);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_ITEM_IGNITED = new HashMap<>();
                mAllFATALITY_CASUALTY_ITEM_IGNITED.put(cid.id, mFATALITY_CASUALTY_ITEM_IGNITED);
                // ITEM_CAUSING_SPREAD
                HashMap<Integer, Integer> mITEM_CAUSING_SPREAD = new HashMap<>();
                mAllITEM_CAUSING_SPREAD.put(cid.id, mITEM_CAUSING_SPREAD);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_ITEM_CAUSING_SPREAD = new HashMap<>();
                mAllFATALITY_CASUALTY_ITEM_CAUSING_SPREAD.put(cid.id, mFATALITY_CASUALTY_ITEM_CAUSING_SPREAD);
                // RAPID_FIRE_GROWTH
                HashMap<Integer, Integer> mRAPID_FIRE_GROWTH = new HashMap<>();
                mAllRAPID_FIRE_GROWTH.put(cid.id, mRAPID_FIRE_GROWTH);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_RAPID_FIRE_GROWTH = new HashMap<>();
                mAllFATALITY_CASUALTY_RAPID_FIRE_GROWTH.put(cid.id, mFATALITY_CASUALTY_RAPID_FIRE_GROWTH);
                // CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION
                HashMap<Integer, Integer> mCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION = new HashMap<>();
                mAllCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION.put(cid.id, mCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION = new HashMap<>();
                mAllFATALITY_CASUALTY_CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION.put(cid.id, mFATALITY_CASUALTY_CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION);
                // CAUSE_EXPLOSION_INVOLVED
                HashMap<Integer, Integer> mCAUSE_EXPLOSION_INVOLVED = new HashMap<>();
                mAllCAUSE_EXPLOSION_INVOLVED.put(cid.id, mCAUSE_EXPLOSION_INVOLVED);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_CAUSE_EXPLOSION_INVOLVED = new HashMap<>();
                mAllFATALITY_CASUALTY_CAUSE_EXPLOSION_INVOLVED.put(cid.id, mFATALITY_CASUALTY_CAUSE_EXPLOSION_INVOLVED);
                // CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION
                HashMap<Integer, Integer> mCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION = new HashMap<>();
                mAllCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION.put(cid.id, mCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION = new HashMap<>();
                mAllFATALITY_CASUALTY_CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION.put(cid.id, mFATALITY_CASUALTY_CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION);
                // CAUSE_EXPLOSION_STAGE_DESCRIPTION
                HashMap<Integer, Integer> mCAUSE_EXPLOSION_STAGE_DESCRIPTION = new HashMap<>();
                mAllCAUSE_EXPLOSION_STAGE_DESCRIPTION.put(cid.id, mCAUSE_EXPLOSION_STAGE_DESCRIPTION);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_CAUSE_EXPLOSION_STAGE_DESCRIPTION = new HashMap<>();
                mAllFATALITY_CASUALTY_CAUSE_EXPLOSION_STAGE_DESCRIPTION.put(cid.id, mFATALITY_CASUALTY_CAUSE_EXPLOSION_STAGE_DESCRIPTION);
                // CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION
                HashMap<Integer, Integer> mCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION = new HashMap<>();
                mAllCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION.put(cid.id, mCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION = new HashMap<>();
                mAllFATALITY_CASUALTY_CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION.put(cid.id, mFATALITY_CASUALTY_CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION);
                // BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION
                HashMap<Integer, Integer> mBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION = new HashMap<>();
                mAllBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION.put(cid.id, mBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION = new HashMap<>();
                mAllFATALITY_CASUALTY_BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION.put(cid.id, mFATALITY_CASUALTY_BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION);
                // BUILDING_FLOORS_ABOVE_GROUND
                HashMap<Integer, Integer> mBUILDING_FLOORS_ABOVE_GROUND = new HashMap<>();
                mAllBUILDING_FLOORS_ABOVE_GROUND.put(cid.id, mBUILDING_FLOORS_ABOVE_GROUND);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_BUILDING_FLOORS_ABOVE_GROUND = new HashMap<>();
                mAllFATALITY_CASUALTY_BUILDING_FLOORS_ABOVE_GROUND.put(cid.id, mFATALITY_CASUALTY_BUILDING_FLOORS_ABOVE_GROUND);
                // BUILDING_FLOORS_BELOW_GROUND
                HashMap<Integer, Integer> mBUILDING_FLOORS_BELOW_GROUND = new HashMap<>();
                mAllBUILDING_FLOORS_BELOW_GROUND.put(cid.id, mBUILDING_FLOORS_BELOW_GROUND);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_BUILDING_FLOORS_BELOW_GROUND = new HashMap<>();
                mAllFATALITY_CASUALTY_BUILDING_FLOORS_BELOW_GROUND.put(cid.id, mFATALITY_CASUALTY_BUILDING_FLOORS_BELOW_GROUND);
                // BUILDING_FLOOR_ORIGIN
                HashMap<Integer, Integer> mBUILDING_FLOOR_ORIGIN = new HashMap<>();
                mAllBUILDING_FLOOR_ORIGIN.put(cid.id, mBUILDING_FLOOR_ORIGIN);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_BUILDING_FLOOR_ORIGIN = new HashMap<>();
                mAllFATALITY_CASUALTY_BUILDING_FLOOR_ORIGIN.put(cid.id, mFATALITY_CASUALTY_BUILDING_FLOOR_ORIGIN);
                // BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION
                HashMap<Integer, Integer> mBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION = new HashMap<>();
                mAllBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION.put(cid.id, mBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION = new HashMap<>();
                mAllFATALITY_CASUALTY_BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION.put(cid.id, mFATALITY_CASUALTY_BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION);
                // BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION
                HashMap<Integer, Integer> mBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION = new HashMap<>();
                mAllBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION.put(cid.id, mBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION = new HashMap<>();
                mAllFATALITY_CASUALTY_BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION.put(cid.id, mFATALITY_CASUALTY_BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION);
                // FIRE_START_LOCATION
                HashMap<Integer, Integer> mFIRE_START_LOCATION = new HashMap<>();
                mAllFIRE_START_LOCATION.put(cid.id, mFIRE_START_LOCATION);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_FIRE_START_LOCATION = new HashMap<>();
                mAllFATALITY_CASUALTY_FIRE_START_LOCATION.put(cid.id, mFATALITY_CASUALTY_FIRE_START_LOCATION);
                // FIRE_SIZE_ON_ARRIVAL
                HashMap<Integer, Integer> mFIRE_SIZE_ON_ARRIVAL = new HashMap<>();
                mAllFIRE_SIZE_ON_ARRIVAL.put(cid.id, mFIRE_SIZE_ON_ARRIVAL);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_FIRE_SIZE_ON_ARRIVAL = new HashMap<>();
                mAllFATALITY_CASUALTY_FIRE_SIZE_ON_ARRIVAL.put(cid.id, mFATALITY_CASUALTY_FIRE_SIZE_ON_ARRIVAL);
                // OTHER_PROPERTY_AFFECTED_ON_ARRIVAL
                HashMap<Integer, Integer> mOTHER_PROPERTY_AFFECTED_ON_ARRIVAL = new HashMap<>();
                mAllOTHER_PROPERTY_AFFECTED_ON_ARRIVAL.put(cid.id, mOTHER_PROPERTY_AFFECTED_ON_ARRIVAL);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_OTHER_PROPERTY_AFFECTED_ON_ARRIVAL = new HashMap<>();
                mAllFATALITY_CASUALTY_OTHER_PROPERTY_AFFECTED_ON_ARRIVAL.put(cid.id, mFATALITY_CASUALTY_OTHER_PROPERTY_AFFECTED_ON_ARRIVAL);
                // BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION
                HashMap<Integer, Integer> mBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION = new HashMap<>();
                mAllBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION.put(cid.id, mBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION = new HashMap<>();
                mAllFATALITY_CASUALTY_BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION.put(cid.id, mFATALITY_CASUALTY_BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION);
                // BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION
                HashMap<Integer, Integer> mBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION = new HashMap<>();
                mAllBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION.put(cid.id, mBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION = new HashMap<>();
                mAllFATALITY_CASUALTY_BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION.put(cid.id, mFATALITY_CASUALTY_BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION);
                // FIRE_SIZE_ON_ARRIVAL_DESCRIPTION
                HashMap<Integer, Integer> mFIRE_SIZE_ON_ARRIVAL_DESCRIPTION = new HashMap<>();
                mAllFIRE_SIZE_ON_ARRIVAL_DESCRIPTION.put(cid.id, mFIRE_SIZE_ON_ARRIVAL_DESCRIPTION);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_FIRE_SIZE_ON_ARRIVAL_DESCRIPTION = new HashMap<>();
                mAllFATALITY_CASUALTY_FIRE_SIZE_ON_ARRIVAL_DESCRIPTION.put(cid.id, mFATALITY_CASUALTY_FIRE_SIZE_ON_ARRIVAL_DESCRIPTION);
                // other_property_affected_close_d
                HashMap<Integer, Integer> mother_property_affected_close_d = new HashMap<>();
                mAllother_property_affected_close_d.put(cid.id, mother_property_affected_close_d);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_other_property_affected_close_d = new HashMap<>();
                mAllFATALITY_CASUALTY_other_property_affected_close_d.put(cid.id, mFATALITY_CASUALTY_other_property_affected_close_d);
                // spread_of_fire_d
                HashMap<Integer, Integer> mspread_of_fire_d = new HashMap<>();
                mAllspread_of_fire_d.put(cid.id, mspread_of_fire_d);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_spread_of_fire_d = new HashMap<>();
                mAllFATALITY_CASUALTY_spread_of_fire_d.put(cid.id, mFATALITY_CASUALTY_spread_of_fire_d);
                // RESPONSE_TIME
                HashMap<Integer, Integer> mRESPONSE_TIME = new HashMap<>();
                mAllRESPONSE_TIME.put(cid.id, mRESPONSE_TIME);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_RESPONSE_TIME = new HashMap<>();
                mAllFATALITY_CASUALTY_RESPONSE_TIME.put(cid.id, mFATALITY_CASUALTY_RESPONSE_TIME);
                // RESPONSE_TIME_CODE
                HashMap<Integer, Integer> mRESPONSE_TIME_CODE = new HashMap<>();
                mAllRESPONSE_TIME_CODE.put(cid.id, mRESPONSE_TIME_CODE);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_RESPONSE_TIME_CODE = new HashMap<>();
                mAllFATALITY_CASUALTY_RESPONSE_TIME_CODE.put(cid.id, mFATALITY_CASUALTY_RESPONSE_TIME_CODE);
                // TIME_AT_SCENE
                HashMap<Integer, Integer> mTIME_AT_SCENE = new HashMap<>();
                mAllTIME_AT_SCENE.put(cid.id, mTIME_AT_SCENE);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_TIME_AT_SCENE = new HashMap<>();
                mAllFATALITY_CASUALTY_TIME_AT_SCENE.put(cid.id, mFATALITY_CASUALTY_TIME_AT_SCENE);
                // TIME_AT_SCENE_CODE
                HashMap<Integer, Integer> mTIME_AT_SCENE_CODE = new HashMap<>();
                mAllTIME_AT_SCENE_CODE.put(cid.id, mTIME_AT_SCENE_CODE);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_TIME_AT_SCENE_CODE = new HashMap<>();
                mAllFATALITY_CASUALTY_TIME_AT_SCENE_CODE.put(cid.id, mFATALITY_CASUALTY_TIME_AT_SCENE_CODE);
                // RESCUES
                HashMap<Integer, Integer> mRESCUES = new HashMap<>();
                mAllRESCUES.put(cid.id, mRESCUES);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_RESCUES = new HashMap<>();
                mAllFATALITY_CASUALTY_RESCUES.put(cid.id, mFATALITY_CASUALTY_RESCUES);
                // EVACUATIONS
                HashMap<Integer, Integer> mEVACUATIONS = new HashMap<>();
                mAllEVACUATIONS.put(cid.id, mEVACUATIONS);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_EVACUATIONS = new HashMap<>();
                mAllFATALITY_CASUALTY_EVACUATIONS.put(cid.id, mFATALITY_CASUALTY_EVACUATIONS);
                // EVACUATIONS_CODE
                HashMap<Integer, Integer> mEVACUATIONS_CODE = new HashMap<>();
                mAllEVACUATIONS_CODE.put(cid.id, mEVACUATIONS_CODE);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_EVACUATIONS_CODE = new HashMap<>();
                mAllFATALITY_CASUALTY_EVACUATIONS_CODE.put(cid.id, mFATALITY_CASUALTY_EVACUATIONS_CODE);
                // BUILDING_EVACUATION_DELAY_DESCRIPTION
                HashMap<Integer, Integer> mBUILDING_EVACUATION_DELAY_DESCRIPTION = new HashMap<>();
                mAllBUILDING_EVACUATION_DELAY_DESCRIPTION.put(cid.id, mBUILDING_EVACUATION_DELAY_DESCRIPTION);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_BUILDING_EVACUATION_DELAY_DESCRIPTION = new HashMap<>();
                mAllFATALITY_CASUALTY_BUILDING_EVACUATION_DELAY_DESCRIPTION.put(cid.id, mFATALITY_CASUALTY_BUILDING_EVACUATION_DELAY_DESCRIPTION);
                // BUILDING_EVACUATION_TIME_DESCRIPTION
                HashMap<Integer, Integer> mBUILDING_EVACUATION_TIME_DESCRIPTION = new HashMap<>();
                mAllBUILDING_EVACUATION_TIME_DESCRIPTION.put(cid.id, mBUILDING_EVACUATION_TIME_DESCRIPTION);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_BUILDING_EVACUATION_TIME_DESCRIPTION = new HashMap<>();
                mAllFATALITY_CASUALTY_BUILDING_EVACUATION_TIME_DESCRIPTION.put(cid.id, mFATALITY_CASUALTY_BUILDING_EVACUATION_TIME_DESCRIPTION);
                // Normal Dwelling Types
                // OCCUPANCY_TYPE
                HashMap<Integer, Integer> mOCCUPANCY_TYPE = new HashMap<>();
                mAllOCCUPANCY_TYPE.put(cid.id, mOCCUPANCY_TYPE);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_OCCUPANCY_TYPE = new HashMap<>();
                mAllFATALITY_CASUALTY_OCCUPANCY_TYPE.put(cid.id, mFATALITY_CASUALTY_OCCUPANCY_TYPE);
                // WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT
                HashMap<Integer, Integer> mWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT = new HashMap<>();
                mAllWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT.put(cid.id, mWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT = new HashMap<>();
                mAllFATALITY_CASUALTY_WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT.put(cid.id, mFATALITY_CASUALTY_WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT);
                // ALARM_SYSTEM
                HashMap<Integer, Integer> mALARM_SYSTEM = new HashMap<>();
                mAllALARM_SYSTEM.put(cid.id, mALARM_SYSTEM);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_ALARM_SYSTEM = new HashMap<>();
                mAllFATALITY_CASUALTY_ALARM_SYSTEM.put(cid.id, mFATALITY_CASUALTY_ALARM_SYSTEM);
                // ALARM_SYSTEM_TYPE
                HashMap<Integer, Integer> mALARM_SYSTEM_TYPE = new HashMap<>();
                mAllALARM_SYSTEM_TYPE.put(cid.id, mALARM_SYSTEM_TYPE);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_ALARM_SYSTEM_TYPE = new HashMap<>();
                mAllFATALITY_CASUALTY_ALARM_SYSTEM_TYPE.put(cid.id, mFATALITY_CASUALTY_ALARM_SYSTEM_TYPE);
                // ALARM_REASON_FOR_POOR_OUTCOME
                HashMap<Integer, Integer> mALARM_REASON_FOR_POOR_OUTCOME = new HashMap<>();
                mAllALARM_REASON_FOR_POOR_OUTCOME.put(cid.id, mALARM_REASON_FOR_POOR_OUTCOME);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_ALARM_REASON_FOR_POOR_OUTCOME = new HashMap<>();
                mAllFATALITY_CASUALTY_ALARM_REASON_FOR_POOR_OUTCOME.put(cid.id, mFATALITY_CASUALTY_ALARM_REASON_FOR_POOR_OUTCOME);
                // Other Dwelling Types 
                HashMap<Integer, Integer> mFSO_APPLY = new HashMap<>();
                mAllFSO_APPLY.put(cid.id, mFSO_APPLY);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_FSO_APPLY = new HashMap<>();
                mAllFATALITY_CASUALTY_FSO_APPLY.put(cid.id, mFATALITY_CASUALTY_FSO_APPLY);
                HashMap<Integer, Integer> mSAFETY_SYSTEM = new HashMap<>();
                mAllSAFETY_SYSTEM.put(cid.id, mSAFETY_SYSTEM);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_SAFETY_SYSTEM = new HashMap<>();
                mAllFATALITY_CASUALTY_SAFETY_SYSTEM.put(cid.id, mFATALITY_CASUALTY_SAFETY_SYSTEM);
                // FDScore
                HashMap<Integer, Integer> mFDScore = new HashMap<>();
                mAllFDScore.put(cid.id, mFDScore);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_FDScore = new HashMap<>();
                mAllFATALITY_CASUALTY_FDScore.put(cid.id, mFATALITY_CASUALTY_FDScore);
                // FSFScoreManagement
                HashMap<Integer, Integer> mFSFScoreManagement = new HashMap<>();
                mAllFSFScoreManagement.put(cid.id, mFSFScoreManagement);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_FSFScoreManagement = new HashMap<>();
                mAllFATALITY_CASUALTY_FSFScoreManagement.put(cid.id, mFATALITY_CASUALTY_FSFScoreManagement);
                // FSFScoreConstruction
                HashMap<Integer, Integer> mFSFScoreConstruction = new HashMap<>();
                mAllFSFScoreConstruction.put(cid.id, mFSFScoreConstruction);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_FSFScoreConstruction = new HashMap<>();
                mAllFATALITY_CASUALTY_FSFScoreConstruction.put(cid.id, mFATALITY_CASUALTY_FSFScoreConstruction);
                // FSFScoreFireFighting
                HashMap<Integer, Integer> mFSFScoreFireFighting = new HashMap<>();
                mAllFSFScoreFireFighting.put(cid.id, mFSFScoreFireFighting);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_FSFScoreFireFighting = new HashMap<>();
                mAllFATALITY_CASUALTY_FSFScoreFireFighting.put(cid.id, mFATALITY_CASUALTY_FSFScoreFireFighting);
                // CombineScore
                HashMap<Integer, Integer> mCombinedScore = new HashMap<>();
                mAllCombinedScore.put(cid.id, mCombinedScore);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_CombinedScore = new HashMap<>();
                mAllFATALITY_CASUALTY_CombinedScore.put(cid.id, mFATALITY_CASUALTY_CombinedScore);
                // HoleyCheese
                HashMap<Integer, Integer> mHoleyCheese = new HashMap<>();
                mAllHoleyCheese.put(cid.id, mHoleyCheese);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_HoleyCheese = new HashMap<>();
                mAllFATALITY_CASUALTY_HoleyCheese.put(cid.id, mFATALITY_CASUALTY_HoleyCheese);
                // DelaysToFireFightingAndNotFirespreadHoleyCheese
                HashMap<Integer, Integer> mDelaysToFireFightingAndNotFirespreadHoleyCheese = new HashMap<>();
                mAllDelaysToFireFightingAndNotFirespreadHoleyCheese.put(cid.id, mDelaysToFireFightingAndNotFirespreadHoleyCheese);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_DelaysToFireFightingAndNotFirespreadHoleyCheese = new HashMap<>();
                mAllFATALITY_CASUALTY_DelaysToFireFightingAndNotFirespreadHoleyCheese.put(cid.id, mFATALITY_CASUALTY_DelaysToFireFightingAndNotFirespreadHoleyCheese);
                HashMap<Integer, Integer> mFirespreadAndNotDelaysToFireFightingHoleyCheese = new HashMap<>();
                mAllFirespreadAndNotDelaysToFireFightingHoleyCheese.put(cid.id, mFirespreadAndNotDelaysToFireFightingHoleyCheese);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_FirespreadAndNotDelaysToFireFightingHoleyCheese = new HashMap<>();
                mAllFATALITY_CASUALTY_FirespreadAndNotDelaysToFireFightingHoleyCheese.put(cid.id, mFATALITY_CASUALTY_FirespreadAndNotDelaysToFireFightingHoleyCheese);

                Iterator<F_RecordID> ite2;
                // Normal Dwelling Types
                ite2 = c.data1.keySet().iterator();
                while (ite2.hasNext()) {
                    F_RecordID id = ite2.next();
                    F_Dwellings_Integer_Record1 r = c.data1.get(id);
                    total++;
                    addToCounts0(r, mFRS_NAME, mE_CODE, mMONTH_NAME,
                            mWEEKDAY_WEEKEND, mDAY_NIGHT,
                            mBUILDING_OR_PROPERTY_TYPE, mLATE_CALL,
                            mMULTI_SEATED_FLAG, mIGNITION_TO_DISCOVERY,
                            mDISCOVERY_TO_CALL, mHOW_DISCOVERED_DESCRIPTION,
                            mBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION,
                            mBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION,
                            mBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT,
                            mOCCUPIED_NORMAL, mACCIDENTAL_OR_DELIBERATE,
                            mVEHICLES, mVEHICLES_CODE, mPERSONNEL,
                            mPERSONNEL_CODE, mSTARTING_DELAY_DESCRIPTION,
                            mACTION_NON_FRS_DESCRIPTION,
                            mACTION_FRS_DESCRIPTION, mCAUSE_OF_FIRE,
                            mIGNITION_POWER, mSOURCE_OF_IGNITION,
                            mITEM_IGNITED, mITEM_CAUSING_SPREAD,
                            mRAPID_FIRE_GROWTH,
                            mCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION,
                            mCAUSE_EXPLOSION_INVOLVED,
                            mCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION,
                            mCAUSE_EXPLOSION_STAGE_DESCRIPTION,
                            mCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION,
                            mBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION,
                            mBUILDING_FLOORS_ABOVE_GROUND,
                            mBUILDING_FLOORS_BELOW_GROUND,
                            mBUILDING_FLOOR_ORIGIN,
                            mBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION,
                            mBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION,
                            mFIRE_START_LOCATION, mFIRE_SIZE_ON_ARRIVAL,
                            mOTHER_PROPERTY_AFFECTED_ON_ARRIVAL,
                            mBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION,
                            mBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION,
                            mFIRE_SIZE_ON_ARRIVAL_DESCRIPTION,
                            mother_property_affected_close_d,
                            mspread_of_fire_d, mRESPONSE_TIME,
                            mRESPONSE_TIME_CODE, mTIME_AT_SCENE,
                            mTIME_AT_SCENE_CODE,
                            mRESCUES, mEVACUATIONS, mEVACUATIONS_CODE,
                            mBUILDING_EVACUATION_DELAY_DESCRIPTION,
                            mBUILDING_EVACUATION_TIME_DESCRIPTION);
                    Generic_Collections.addToCount(mFATALITY_CASUALTY, r.tFATALITY_CASUALTY, 1);
                    Generic_Collections.addToCount(mOCCUPANCY_TYPE, r.tOCCUPANCY_TYPE, 1);
                    Generic_Collections.addToCount(mWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT, r.tWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT, 1);
                    Generic_Collections.addToCount(mALARM_SYSTEM, r.tALARM_SYSTEM, 1);
                    Generic_Collections.addToCount(mALARM_SYSTEM_TYPE, r.tALARM_SYSTEM_TYPE, 1);
                    Generic_Collections.addToCount(mALARM_REASON_FOR_POOR_OUTCOME, r.tALARM_REASON_FOR_POOR_OUTCOME, 1);
                    // FIRE_SAFETY scores
                    Generic_Collections.addToCount(mFDScore, r.fDScore, 1);
                    Generic_Collections.addToCount(mFSFScoreManagement, r.fSFScoreManagement, 1);
                    Generic_Collections.addToCount(mFSFScoreConstruction, r.fSFScoreConstruction, 1);
                    Generic_Collections.addToCount(mFSFScoreFireFighting, r.fSFScoreFireFighting, 1);
                    Generic_Collections.addToCount(mCombinedScore, r.combinedScore, 1);
                    Generic_Collections.addToCount(mHoleyCheese, r.holeyCheese, 1);
                    Generic_Collections.addToCount(mDelaysToFireFightingAndNotFirespreadHoleyCheese, r.delaysToFireFightingAndNotFirespreadHoleyCheese, 1);
                    Generic_Collections.addToCount(mFirespreadAndNotDelaysToFireFightingHoleyCheese, r.firespreadAndNotDelaysToFireFightingHoleyCheese, 1);
                    if (r.tFATALITY_CASUALTY == valFATALITY_CASUALTY) {
                        addToCounts0(r, mFATALITY_CASUALTY_FRS_NAME, mFATALITY_CASUALTY_E_CODE, mFATALITY_CASUALTY_MONTH_NAME,
                                mFATALITY_CASUALTY_WEEKDAY_WEEKEND, mFATALITY_CASUALTY_DAY_NIGHT,
                                mFATALITY_CASUALTY_BUILDING_OR_PROPERTY_TYPE, mFATALITY_CASUALTY_LATE_CALL,
                                mFATALITY_CASUALTY_MULTI_SEATED_FLAG, mFATALITY_CASUALTY_IGNITION_TO_DISCOVERY,
                                mFATALITY_CASUALTY_DISCOVERY_TO_CALL, mFATALITY_CASUALTY_HOW_DISCOVERED_DESCRIPTION,
                                mFATALITY_CASUALTY_BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION,
                                mFATALITY_CASUALTY_BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION,
                                mFATALITY_CASUALTY_BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT,
                                mFATALITY_CASUALTY_OCCUPIED_NORMAL, mFATALITY_CASUALTY_ACCIDENTAL_OR_DELIBERATE,
                                mFATALITY_CASUALTY_VEHICLES, mFATALITY_CASUALTY_VEHICLES_CODE, mFATALITY_CASUALTY_PERSONNEL,
                                mFATALITY_CASUALTY_PERSONNEL_CODE, mFATALITY_CASUALTY_STARTING_DELAY_DESCRIPTION,
                                mFATALITY_CASUALTY_ACTION_NON_FRS_DESCRIPTION,
                                mFATALITY_CASUALTY_ACTION_FRS_DESCRIPTION, mFATALITY_CASUALTY_CAUSE_OF_FIRE,
                                mFATALITY_CASUALTY_IGNITION_POWER, mFATALITY_CASUALTY_SOURCE_OF_IGNITION,
                                mFATALITY_CASUALTY_ITEM_IGNITED, mFATALITY_CASUALTY_ITEM_CAUSING_SPREAD,
                                mFATALITY_CASUALTY_RAPID_FIRE_GROWTH,
                                mFATALITY_CASUALTY_CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION,
                                mFATALITY_CASUALTY_CAUSE_EXPLOSION_INVOLVED,
                                mFATALITY_CASUALTY_CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION,
                                mFATALITY_CASUALTY_CAUSE_EXPLOSION_STAGE_DESCRIPTION,
                                mFATALITY_CASUALTY_CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION,
                                mFATALITY_CASUALTY_BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION,
                                mFATALITY_CASUALTY_BUILDING_FLOORS_ABOVE_GROUND,
                                mFATALITY_CASUALTY_BUILDING_FLOORS_BELOW_GROUND,
                                mFATALITY_CASUALTY_BUILDING_FLOOR_ORIGIN,
                                mFATALITY_CASUALTY_BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION,
                                mFATALITY_CASUALTY_BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION,
                                mFATALITY_CASUALTY_FIRE_START_LOCATION, mFATALITY_CASUALTY_FIRE_SIZE_ON_ARRIVAL,
                                mFATALITY_CASUALTY_OTHER_PROPERTY_AFFECTED_ON_ARRIVAL,
                                mFATALITY_CASUALTY_BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION,
                                mFATALITY_CASUALTY_BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION,
                                mFATALITY_CASUALTY_FIRE_SIZE_ON_ARRIVAL_DESCRIPTION,
                                mFATALITY_CASUALTY_other_property_affected_close_d,
                                mFATALITY_CASUALTY_spread_of_fire_d, mFATALITY_CASUALTY_RESPONSE_TIME,
                                mFATALITY_CASUALTY_RESPONSE_TIME_CODE, mFATALITY_CASUALTY_TIME_AT_SCENE,
                                mFATALITY_CASUALTY_TIME_AT_SCENE_CODE,
                                mFATALITY_CASUALTY_RESCUES, mFATALITY_CASUALTY_EVACUATIONS, mFATALITY_CASUALTY_EVACUATIONS_CODE,
                                mFATALITY_CASUALTY_BUILDING_EVACUATION_DELAY_DESCRIPTION,
                                mFATALITY_CASUALTY_BUILDING_EVACUATION_TIME_DESCRIPTION);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_OCCUPANCY_TYPE, r.tOCCUPANCY_TYPE, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT, r.tWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_ALARM_SYSTEM, r.tALARM_SYSTEM, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_ALARM_SYSTEM_TYPE, r.tALARM_SYSTEM_TYPE, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_ALARM_REASON_FOR_POOR_OUTCOME, r.tALARM_REASON_FOR_POOR_OUTCOME, 1);
                        // FIRE_SAFETY scores
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_FDScore, r.fDScore, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_FSFScoreManagement, r.fSFScoreManagement, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_FSFScoreConstruction, r.fSFScoreConstruction, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_FSFScoreFireFighting, r.fSFScoreFireFighting, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_CombinedScore, r.combinedScore, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_HoleyCheese, r.holeyCheese, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_DelaysToFireFightingAndNotFirespreadHoleyCheese, r.delaysToFireFightingAndNotFirespreadHoleyCheese, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_FirespreadAndNotDelaysToFireFightingHoleyCheese, r.firespreadAndNotDelaysToFireFightingHoleyCheese, 1);
                    }
                }
                // Other Dwelling Types
                ite2 = c.data2.keySet().iterator();
                while (ite2.hasNext()) {
                    F_RecordID id = ite2.next();
                    F_Dwellings_Integer_Record2 r = c.data2.get(id);
                    total++;
                    addToCounts0(r, mFRS_NAME, mE_CODE, mMONTH_NAME,
                            mWEEKDAY_WEEKEND, mDAY_NIGHT,
                            mBUILDING_OR_PROPERTY_TYPE, mLATE_CALL,
                            mMULTI_SEATED_FLAG, mIGNITION_TO_DISCOVERY,
                            mDISCOVERY_TO_CALL, mHOW_DISCOVERED_DESCRIPTION,
                            mBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION,
                            mBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION,
                            mBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT,
                            mOCCUPIED_NORMAL, mACCIDENTAL_OR_DELIBERATE,
                            mVEHICLES, mVEHICLES_CODE, mPERSONNEL,
                            mPERSONNEL_CODE, mSTARTING_DELAY_DESCRIPTION,
                            mACTION_NON_FRS_DESCRIPTION,
                            mACTION_FRS_DESCRIPTION, mCAUSE_OF_FIRE,
                            mIGNITION_POWER, mSOURCE_OF_IGNITION,
                            mITEM_IGNITED, mITEM_CAUSING_SPREAD,
                            mRAPID_FIRE_GROWTH,
                            mCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION,
                            mCAUSE_EXPLOSION_INVOLVED,
                            mCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION,
                            mCAUSE_EXPLOSION_STAGE_DESCRIPTION,
                            mCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION,
                            mBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION,
                            mBUILDING_FLOORS_ABOVE_GROUND,
                            mBUILDING_FLOORS_BELOW_GROUND,
                            mBUILDING_FLOOR_ORIGIN,
                            mBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION,
                            mBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION,
                            mFIRE_START_LOCATION, mFIRE_SIZE_ON_ARRIVAL,
                            mOTHER_PROPERTY_AFFECTED_ON_ARRIVAL,
                            mBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION,
                            mBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION,
                            mFIRE_SIZE_ON_ARRIVAL_DESCRIPTION,
                            mother_property_affected_close_d,
                            mspread_of_fire_d, mRESPONSE_TIME,
                            mRESPONSE_TIME_CODE, mTIME_AT_SCENE,
                            mTIME_AT_SCENE_CODE,
                            mRESCUES, mEVACUATIONS, mEVACUATIONS_CODE,
                            mBUILDING_EVACUATION_DELAY_DESCRIPTION,
                            mBUILDING_EVACUATION_TIME_DESCRIPTION);
                    Generic_Collections.addToCount(mFATALITY_CASUALTY, r.tFATALITY_CASUALTY, 1);
                    Generic_Collections.addToCount(mFSO_APPLY, r.tFSO_APPLY, 1);
                    Generic_Collections.addToCount(mSAFETY_SYSTEM, r.tSAFETY_SYSTEM, 1);
                    // FIRE_SAFETY scores
                    Generic_Collections.addToCount(mFDScore, r.fDScore, 1);
                    Generic_Collections.addToCount(mFSFScoreManagement, r.fSFScoreManagement, 1);
                    Generic_Collections.addToCount(mFSFScoreConstruction, r.fSFScoreConstruction, 1);
                    Generic_Collections.addToCount(mFSFScoreFireFighting, r.fSFScoreFireFighting, 1);
                    Generic_Collections.addToCount(mCombinedScore, r.combinedScore, 1);
                    Generic_Collections.addToCount(mHoleyCheese, r.holeyCheese, 1);
                    Generic_Collections.addToCount(mDelaysToFireFightingAndNotFirespreadHoleyCheese, r.delaysToFireFightingAndNotFirespreadHoleyCheese, 1);
                    Generic_Collections.addToCount(mFirespreadAndNotDelaysToFireFightingHoleyCheese, r.firespreadAndNotDelaysToFireFightingHoleyCheese, 1);
                    if (r.tFATALITY_CASUALTY == valFATALITY_CASUALTY) {
                        addToCounts0(r, mFATALITY_CASUALTY_FRS_NAME, mFATALITY_CASUALTY_E_CODE, mFATALITY_CASUALTY_MONTH_NAME,
                                mFATALITY_CASUALTY_WEEKDAY_WEEKEND, mFATALITY_CASUALTY_DAY_NIGHT,
                                mFATALITY_CASUALTY_BUILDING_OR_PROPERTY_TYPE, mFATALITY_CASUALTY_LATE_CALL,
                                mFATALITY_CASUALTY_MULTI_SEATED_FLAG, mFATALITY_CASUALTY_IGNITION_TO_DISCOVERY,
                                mFATALITY_CASUALTY_DISCOVERY_TO_CALL, mFATALITY_CASUALTY_HOW_DISCOVERED_DESCRIPTION,
                                mFATALITY_CASUALTY_BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION,
                                mFATALITY_CASUALTY_BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION,
                                mFATALITY_CASUALTY_BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT,
                                mFATALITY_CASUALTY_OCCUPIED_NORMAL, mFATALITY_CASUALTY_ACCIDENTAL_OR_DELIBERATE,
                                mFATALITY_CASUALTY_VEHICLES, mFATALITY_CASUALTY_VEHICLES_CODE, mFATALITY_CASUALTY_PERSONNEL,
                                mFATALITY_CASUALTY_PERSONNEL_CODE, mFATALITY_CASUALTY_STARTING_DELAY_DESCRIPTION,
                                mFATALITY_CASUALTY_ACTION_NON_FRS_DESCRIPTION,
                                mFATALITY_CASUALTY_ACTION_FRS_DESCRIPTION, mFATALITY_CASUALTY_CAUSE_OF_FIRE,
                                mFATALITY_CASUALTY_IGNITION_POWER, mFATALITY_CASUALTY_SOURCE_OF_IGNITION,
                                mFATALITY_CASUALTY_ITEM_IGNITED, mFATALITY_CASUALTY_ITEM_CAUSING_SPREAD,
                                mFATALITY_CASUALTY_RAPID_FIRE_GROWTH,
                                mFATALITY_CASUALTY_CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION,
                                mFATALITY_CASUALTY_CAUSE_EXPLOSION_INVOLVED,
                                mFATALITY_CASUALTY_CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION,
                                mFATALITY_CASUALTY_CAUSE_EXPLOSION_STAGE_DESCRIPTION,
                                mFATALITY_CASUALTY_CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION,
                                mFATALITY_CASUALTY_BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION,
                                mFATALITY_CASUALTY_BUILDING_FLOORS_ABOVE_GROUND,
                                mFATALITY_CASUALTY_BUILDING_FLOORS_BELOW_GROUND,
                                mFATALITY_CASUALTY_BUILDING_FLOOR_ORIGIN,
                                mFATALITY_CASUALTY_BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION,
                                mFATALITY_CASUALTY_BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION,
                                mFATALITY_CASUALTY_FIRE_START_LOCATION, mFATALITY_CASUALTY_FIRE_SIZE_ON_ARRIVAL,
                                mFATALITY_CASUALTY_OTHER_PROPERTY_AFFECTED_ON_ARRIVAL,
                                mFATALITY_CASUALTY_BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION,
                                mFATALITY_CASUALTY_BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION,
                                mFATALITY_CASUALTY_FIRE_SIZE_ON_ARRIVAL_DESCRIPTION,
                                mFATALITY_CASUALTY_other_property_affected_close_d,
                                mFATALITY_CASUALTY_spread_of_fire_d, mFATALITY_CASUALTY_RESPONSE_TIME,
                                mFATALITY_CASUALTY_RESPONSE_TIME_CODE, mFATALITY_CASUALTY_TIME_AT_SCENE,
                                mFATALITY_CASUALTY_TIME_AT_SCENE_CODE,
                                mFATALITY_CASUALTY_RESCUES, mFATALITY_CASUALTY_EVACUATIONS, mFATALITY_CASUALTY_EVACUATIONS_CODE,
                                mFATALITY_CASUALTY_BUILDING_EVACUATION_DELAY_DESCRIPTION,
                                mFATALITY_CASUALTY_BUILDING_EVACUATION_TIME_DESCRIPTION);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_FSO_APPLY, r.tFSO_APPLY, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_SAFETY_SYSTEM, r.tSAFETY_SYSTEM, 1);
                        // FIRE_SAFETY scores
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_FDScore, r.fDScore, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_FSFScoreManagement, r.fSFScoreManagement, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_FSFScoreConstruction, r.fSFScoreConstruction, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_FSFScoreFireFighting, r.fSFScoreFireFighting, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_CombinedScore, r.combinedScore, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_HoleyCheese, r.holeyCheese, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_DelaysToFireFightingAndNotFirespreadHoleyCheese, r.delaysToFireFightingAndNotFirespreadHoleyCheese, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_FirespreadAndNotDelaysToFireFightingHoleyCheese, r.firespreadAndNotDelaysToFireFightingHoleyCheese, 1);
                    }
                }
                allTotal += total;
                Path dir = getDir(name, tFINANCIAL_YEAR);
                output(dir, tFINANCIAL_YEAR, F_Strings.FATALITY_CASUALTY, varFATALITY_CASUALTY, total, mFATALITY_CASUALTY);
                output(dir, tFINANCIAL_YEAR, F_Strings.FRS_NAME, env.data.vname2id.get(F_Strings.FRS_NAME), mFRS_NAME, mFATALITY_CASUALTY_FRS_NAME);
                output(dir, tFINANCIAL_YEAR, F_Strings.E_CODE, env.data.vname2id.get(F_Strings.E_CODE), mE_CODE, mFATALITY_CASUALTY_E_CODE);
                output(dir, tFINANCIAL_YEAR, F_Strings.MONTH_NAME, env.data.vname2id.get(F_Strings.MONTH_NAME), mMONTH_NAME, mFATALITY_CASUALTY_MONTH_NAME);
                output(dir, tFINANCIAL_YEAR, F_Strings.WEEKDAY_WEEKEND, env.data.vname2id.get(F_Strings.WEEKDAY_WEEKEND), mWEEKDAY_WEEKEND, mFATALITY_CASUALTY_WEEKDAY_WEEKEND);
                output(dir, tFINANCIAL_YEAR, F_Strings.DAY_NIGHT, env.data.vname2id.get(F_Strings.DAY_NIGHT), mDAY_NIGHT, mFATALITY_CASUALTY_DAY_NIGHT);
                output(dir, tFINANCIAL_YEAR, F_Strings.LATE_CALL, env.data.vname2id.get(F_Strings.LATE_CALL), mLATE_CALL, mFATALITY_CASUALTY_LATE_CALL);
                output(dir, tFINANCIAL_YEAR, F_Strings.BUILDING_OR_PROPERTY_TYPE, env.data.vname2id.get(F_Strings.BUILDING_OR_PROPERTY_TYPE), mBUILDING_OR_PROPERTY_TYPE, mFATALITY_CASUALTY_BUILDING_OR_PROPERTY_TYPE);
                output(dir, tFINANCIAL_YEAR, F_Strings.MULTI_SEATED_FLAG, env.data.vname2id.get(F_Strings.MULTI_SEATED_FLAG), mMULTI_SEATED_FLAG, mFATALITY_CASUALTY_MULTI_SEATED_FLAG);
                output(dir, tFINANCIAL_YEAR, F_Strings.IGNITION_TO_DISCOVERY, env.data.vname2id.get(F_Strings.IGNITION_TO_DISCOVERY), mIGNITION_TO_DISCOVERY, mFATALITY_CASUALTY_IGNITION_TO_DISCOVERY);
                output(dir, tFINANCIAL_YEAR, F_Strings.DISCOVERY_TO_CALL, env.data.vname2id.get(F_Strings.DISCOVERY_TO_CALL), mDISCOVERY_TO_CALL, mFATALITY_CASUALTY_DISCOVERY_TO_CALL);
                output(dir, tFINANCIAL_YEAR, F_Strings.HOW_DISCOVERED_DESCRIPTION, env.data.vname2id.get(F_Strings.HOW_DISCOVERED_DESCRIPTION), mHOW_DISCOVERED_DESCRIPTION, mFATALITY_CASUALTY_HOW_DISCOVERED_DESCRIPTION);
                output(dir, tFINANCIAL_YEAR, F_Strings.BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION), mBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION, mFATALITY_CASUALTY_BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION);
                output(dir, tFINANCIAL_YEAR, F_Strings.BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION), mBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION, mFATALITY_CASUALTY_BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION);
                output(dir, tFINANCIAL_YEAR, F_Strings.BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT, env.data.vname2id.get(F_Strings.BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT), mBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT, mFATALITY_CASUALTY_BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT);
                output(dir, tFINANCIAL_YEAR, F_Strings.OCCUPIED_NORMAL, env.data.vname2id.get(F_Strings.OCCUPIED_NORMAL), mOCCUPIED_NORMAL, mFATALITY_CASUALTY_OCCUPIED_NORMAL);
                output(dir, tFINANCIAL_YEAR, F_Strings.ACCIDENTAL_OR_DELIBERATE, env.data.vname2id.get(F_Strings.ACCIDENTAL_OR_DELIBERATE), mACCIDENTAL_OR_DELIBERATE, mFATALITY_CASUALTY_ACCIDENTAL_OR_DELIBERATE);
                output(dir, tFINANCIAL_YEAR, F_Strings.VEHICLES, env.data.vname2id.get(F_Strings.VEHICLES), mVEHICLES, mFATALITY_CASUALTY_VEHICLES);
                output(dir, tFINANCIAL_YEAR, F_Strings.VEHICLES_CODE, env.data.vname2id.get(F_Strings.VEHICLES_CODE), mVEHICLES_CODE, mFATALITY_CASUALTY_VEHICLES_CODE);
                output(dir, tFINANCIAL_YEAR, F_Strings.PERSONNEL, env.data.vname2id.get(F_Strings.PERSONNEL), mPERSONNEL, mFATALITY_CASUALTY_PERSONNEL);
                output(dir, tFINANCIAL_YEAR, F_Strings.PERSONNEL_CODE, env.data.vname2id.get(F_Strings.PERSONNEL_CODE), mPERSONNEL_CODE, mFATALITY_CASUALTY_PERSONNEL_CODE);
                output(dir, tFINANCIAL_YEAR, F_Strings.STARTING_DELAY_DESCRIPTION, env.data.vname2id.get(F_Strings.STARTING_DELAY_DESCRIPTION), mSTARTING_DELAY_DESCRIPTION, mFATALITY_CASUALTY_STARTING_DELAY_DESCRIPTION);
                output(dir, tFINANCIAL_YEAR, F_Strings.ACTION_NON_FRS_DESCRIPTION, env.data.vname2id.get(F_Strings.ACTION_NON_FRS_DESCRIPTION), mACTION_NON_FRS_DESCRIPTION, mFATALITY_CASUALTY_ACTION_NON_FRS_DESCRIPTION);
                output(dir, tFINANCIAL_YEAR, F_Strings.ACTION_FRS_DESCRIPTION, env.data.vname2id.get(F_Strings.ACTION_FRS_DESCRIPTION), mACTION_FRS_DESCRIPTION, mFATALITY_CASUALTY_ACTION_FRS_DESCRIPTION);
                output(dir, tFINANCIAL_YEAR, F_Strings.CAUSE_OF_FIRE, env.data.vname2id.get(F_Strings.CAUSE_OF_FIRE), mCAUSE_OF_FIRE, mFATALITY_CASUALTY_CAUSE_OF_FIRE);
                output(dir, tFINANCIAL_YEAR, F_Strings.IGNITION_POWER, env.data.vname2id.get(F_Strings.IGNITION_POWER), mIGNITION_POWER, mFATALITY_CASUALTY_IGNITION_POWER);
                output(dir, tFINANCIAL_YEAR, F_Strings.SOURCE_OF_IGNITION, env.data.vname2id.get(F_Strings.SOURCE_OF_IGNITION), mSOURCE_OF_IGNITION, mFATALITY_CASUALTY_SOURCE_OF_IGNITION);
                output(dir, tFINANCIAL_YEAR, F_Strings.ITEM_IGNITED, env.data.vname2id.get(F_Strings.ITEM_IGNITED), mITEM_IGNITED, mFATALITY_CASUALTY_ITEM_IGNITED);
                output(dir, tFINANCIAL_YEAR, F_Strings.ITEM_CAUSING_SPREAD, env.data.vname2id.get(F_Strings.ITEM_CAUSING_SPREAD), mITEM_CAUSING_SPREAD, mFATALITY_CASUALTY_ITEM_CAUSING_SPREAD);
                output(dir, tFINANCIAL_YEAR, F_Strings.RAPID_FIRE_GROWTH, env.data.vname2id.get(F_Strings.RAPID_FIRE_GROWTH), mRAPID_FIRE_GROWTH, mFATALITY_CASUALTY_RAPID_FIRE_GROWTH);
                output(dir, tFINANCIAL_YEAR, F_Strings.CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION, env.data.vname2id.get(F_Strings.CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION), mCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION, mFATALITY_CASUALTY_CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION);
                output(dir, tFINANCIAL_YEAR, F_Strings.CAUSE_EXPLOSION_INVOLVED, env.data.vname2id.get(F_Strings.CAUSE_EXPLOSION_INVOLVED), mCAUSE_EXPLOSION_INVOLVED, mFATALITY_CASUALTY_CAUSE_EXPLOSION_INVOLVED);
                output(dir, tFINANCIAL_YEAR, F_Strings.CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION, env.data.vname2id.get(F_Strings.CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION), mCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION, mFATALITY_CASUALTY_CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION);
                output(dir, tFINANCIAL_YEAR, F_Strings.CAUSE_EXPLOSION_STAGE_DESCRIPTION, env.data.vname2id.get(F_Strings.CAUSE_EXPLOSION_STAGE_DESCRIPTION), mCAUSE_EXPLOSION_STAGE_DESCRIPTION, mFATALITY_CASUALTY_CAUSE_EXPLOSION_STAGE_DESCRIPTION);
                output(dir, tFINANCIAL_YEAR, F_Strings.CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION, env.data.vname2id.get(F_Strings.CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION), mCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION, mFATALITY_CASUALTY_CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION);
                output(dir, tFINANCIAL_YEAR, F_Strings.BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION), mBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION, mFATALITY_CASUALTY_BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION);
                output(dir, tFINANCIAL_YEAR, F_Strings.BUILDING_FLOORS_ABOVE_GROUND, env.data.vname2id.get(F_Strings.BUILDING_FLOORS_ABOVE_GROUND), mBUILDING_FLOORS_ABOVE_GROUND, mFATALITY_CASUALTY_BUILDING_FLOORS_ABOVE_GROUND);
                output(dir, tFINANCIAL_YEAR, F_Strings.BUILDING_FLOORS_BELOW_GROUND, env.data.vname2id.get(F_Strings.BUILDING_FLOORS_BELOW_GROUND), mBUILDING_FLOORS_BELOW_GROUND, mFATALITY_CASUALTY_BUILDING_FLOORS_BELOW_GROUND);
                output(dir, tFINANCIAL_YEAR, F_Strings.BUILDING_FLOOR_ORIGIN, env.data.vname2id.get(F_Strings.BUILDING_FLOOR_ORIGIN), mBUILDING_FLOOR_ORIGIN, mFATALITY_CASUALTY_BUILDING_FLOOR_ORIGIN);
                output(dir, tFINANCIAL_YEAR, F_Strings.BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION), mBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION, mFATALITY_CASUALTY_BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION);
                output(dir, tFINANCIAL_YEAR, F_Strings.BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION), mBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION, mFATALITY_CASUALTY_BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION);
                output(dir, tFINANCIAL_YEAR, F_Strings.FIRE_START_LOCATION, env.data.vname2id.get(F_Strings.FIRE_START_LOCATION), mFIRE_START_LOCATION, mFATALITY_CASUALTY_FIRE_START_LOCATION);
                output(dir, tFINANCIAL_YEAR, F_Strings.FIRE_SIZE_ON_ARRIVAL, env.data.vname2id.get(F_Strings.FIRE_SIZE_ON_ARRIVAL), mFIRE_SIZE_ON_ARRIVAL, mFATALITY_CASUALTY_FIRE_SIZE_ON_ARRIVAL);
                output(dir, tFINANCIAL_YEAR, F_Strings.OTHER_PROPERTY_AFFECTED_ON_ARRIVAL, env.data.vname2id.get(F_Strings.OTHER_PROPERTY_AFFECTED_ON_ARRIVAL), mOTHER_PROPERTY_AFFECTED_ON_ARRIVAL, mFATALITY_CASUALTY_OTHER_PROPERTY_AFFECTED_ON_ARRIVAL);
                output(dir, tFINANCIAL_YEAR, F_Strings.BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION), mBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION, mFATALITY_CASUALTY_BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION);
                output(dir, tFINANCIAL_YEAR, F_Strings.BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION), mBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION, mFATALITY_CASUALTY_BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION);
                output(dir, tFINANCIAL_YEAR, F_Strings.FIRE_SIZE_ON_ARRIVAL_DESCRIPTION, env.data.vname2id.get(F_Strings.FIRE_SIZE_ON_ARRIVAL_DESCRIPTION), mFIRE_SIZE_ON_ARRIVAL_DESCRIPTION, mFATALITY_CASUALTY_FIRE_SIZE_ON_ARRIVAL_DESCRIPTION);
                output(dir, tFINANCIAL_YEAR, F_Strings.other_property_affected_close_d, env.data.vname2id.get(F_Strings.other_property_affected_close_d), mother_property_affected_close_d, mFATALITY_CASUALTY_other_property_affected_close_d);
                output(dir, tFINANCIAL_YEAR, F_Strings.spread_of_fire_d, env.data.vname2id.get(F_Strings.spread_of_fire_d), mspread_of_fire_d, mFATALITY_CASUALTY_spread_of_fire_d);
                output(dir, tFINANCIAL_YEAR, F_Strings.RESPONSE_TIME, env.data.vname2id.get(F_Strings.RESPONSE_TIME), mRESPONSE_TIME, mFATALITY_CASUALTY_RESPONSE_TIME);
                output(dir, tFINANCIAL_YEAR, F_Strings.RESPONSE_TIME_CODE, env.data.vname2id.get(F_Strings.RESPONSE_TIME_CODE), mRESPONSE_TIME_CODE, mFATALITY_CASUALTY_RESPONSE_TIME_CODE);
                output(dir, tFINANCIAL_YEAR, F_Strings.TIME_AT_SCENE, env.data.vname2id.get(F_Strings.TIME_AT_SCENE), mTIME_AT_SCENE, mFATALITY_CASUALTY_TIME_AT_SCENE);
                output(dir, tFINANCIAL_YEAR, F_Strings.TIME_AT_SCENE_CODE, env.data.vname2id.get(F_Strings.TIME_AT_SCENE_CODE), mTIME_AT_SCENE_CODE, mFATALITY_CASUALTY_TIME_AT_SCENE_CODE);
                output(dir, tFINANCIAL_YEAR, F_Strings.RESCUES, env.data.vname2id.get(F_Strings.RESCUES), mRESCUES, mFATALITY_CASUALTY_RESCUES);
                output(dir, tFINANCIAL_YEAR, F_Strings.EVACUATIONS, env.data.vname2id.get(F_Strings.EVACUATIONS), mEVACUATIONS, mFATALITY_CASUALTY_EVACUATIONS);
                output(dir, tFINANCIAL_YEAR, F_Strings.EVACUATIONS_CODE, env.data.vname2id.get(F_Strings.EVACUATIONS_CODE), mEVACUATIONS_CODE, mFATALITY_CASUALTY_EVACUATIONS_CODE);
                output(dir, tFINANCIAL_YEAR, F_Strings.BUILDING_EVACUATION_DELAY_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_EVACUATION_DELAY_DESCRIPTION), mBUILDING_EVACUATION_DELAY_DESCRIPTION, mFATALITY_CASUALTY_BUILDING_EVACUATION_DELAY_DESCRIPTION);
                output(dir, tFINANCIAL_YEAR, F_Strings.BUILDING_EVACUATION_TIME_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_EVACUATION_TIME_DESCRIPTION), mBUILDING_EVACUATION_TIME_DESCRIPTION, mFATALITY_CASUALTY_BUILDING_EVACUATION_TIME_DESCRIPTION);
                // Normal Dwelling Types
                output(dir, tFINANCIAL_YEAR, F_Strings.OCCUPANCY_TYPE, env.data.vname2id.get(F_Strings.OCCUPANCY_TYPE), mOCCUPANCY_TYPE, mFATALITY_CASUALTY_OCCUPANCY_TYPE);
                output(dir, tFINANCIAL_YEAR, F_Strings.WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT, env.data.vname2id.get(F_Strings.WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT), mWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT, mFATALITY_CASUALTY_WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT);
                output(dir, tFINANCIAL_YEAR, F_Strings.ALARM_SYSTEM, env.data.vname2id.get(F_Strings.ALARM_SYSTEM), mALARM_SYSTEM, mFATALITY_CASUALTY_ALARM_SYSTEM);
                output(dir, tFINANCIAL_YEAR, F_Strings.ALARM_SYSTEM_TYPE, env.data.vname2id.get(F_Strings.ALARM_SYSTEM_TYPE), mALARM_SYSTEM_TYPE, mFATALITY_CASUALTY_ALARM_SYSTEM_TYPE);
                output(dir, tFINANCIAL_YEAR, F_Strings.ALARM_REASON_FOR_POOR_OUTCOME, env.data.vname2id.get(F_Strings.ALARM_REASON_FOR_POOR_OUTCOME), mALARM_REASON_FOR_POOR_OUTCOME, mFATALITY_CASUALTY_ALARM_REASON_FOR_POOR_OUTCOME);
                // Other Dwelling Types
                output(dir, tFINANCIAL_YEAR, F_Strings.FSO_APPLY, env.data.vname2id.get(F_Strings.FSO_APPLY), mFSO_APPLY, mFATALITY_CASUALTY_FSO_APPLY);
                output(dir, tFINANCIAL_YEAR, F_Strings.SAFETY_SYSTEM, env.data.vname2id.get(F_Strings.SAFETY_SYSTEM), mSAFETY_SYSTEM, mFATALITY_CASUALTY_SAFETY_SYSTEM);
                // FIRE_SAFETY scores
                outputScores(dir, tFINANCIAL_YEAR, F_Strings.FIRE_DANGER_SCORE, env.data.vname2id.get(F_Strings.FIRE_DANGER_SCORE), mFDScore, mFATALITY_CASUALTY_FDScore);
                outputScores(dir, tFINANCIAL_YEAR, F_Strings.FIRE_SAFETY_FAILURE_SCORE_CONSTRUCTION, env.data.vname2id.get(F_Strings.FIRE_SAFETY_FAILURE_SCORE_CONSTRUCTION), mFSFScoreConstruction, mFATALITY_CASUALTY_FSFScoreConstruction);
                outputScores(dir, tFINANCIAL_YEAR, F_Strings.FIRE_SAFETY_FAILURE_SCORE_MANAGEMENT, env.data.vname2id.get(F_Strings.FIRE_SAFETY_FAILURE_SCORE_MANAGEMENT), mFSFScoreManagement, mFATALITY_CASUALTY_FSFScoreManagement);
                outputScores(dir, tFINANCIAL_YEAR, F_Strings.FIRE_SAFETY_FAILURE_SCORE_FIRE_FIGHTING, env.data.vname2id.get(F_Strings.FIRE_SAFETY_FAILURE_SCORE_FIRE_FIGHTING), mFSFScoreFireFighting, mFATALITY_CASUALTY_FSFScoreFireFighting);
                outputScores(dir, tFINANCIAL_YEAR, F_Strings.CombinedScore, env.data.vname2id.get(F_Strings.CombinedScore), mCombinedScore, mFATALITY_CASUALTY_CombinedScore);
                outputScores(dir, tFINANCIAL_YEAR, F_Strings.HoleyCheese, env.data.vname2id.get(F_Strings.HoleyCheese), mHoleyCheese, mFATALITY_CASUALTY_HoleyCheese);
                outputScores(dir, tFINANCIAL_YEAR, F_Strings.DelaysToFireFightingAndNotFirespreadHoleyCheese, env.data.vname2id.get(F_Strings.DelaysToFireFightingAndNotFirespreadHoleyCheese), mDelaysToFireFightingAndNotFirespreadHoleyCheese, mFATALITY_CASUALTY_DelaysToFireFightingAndNotFirespreadHoleyCheese);
                outputScores(dir, tFINANCIAL_YEAR, F_Strings.FirespreadAndNotDelaysToFireFightingHoleyCheese, env.data.vname2id.get(F_Strings.FirespreadAndNotDelaysToFireFightingHoleyCheese), mFirespreadAndNotDelaysToFireFightingHoleyCheese, mFATALITY_CASUALTY_FirespreadAndNotDelaysToFireFightingHoleyCheese);

            }
            Path dir = getDir(name, F_Strings.s2010_20);
            output(dir, F_Strings.s2010_20, F_Strings.FATALITY_CASUALTY, varFATALITY_CASUALTY, allTotal, collectCounts(mAllFATALITY_CASUALTY));
            output(dir, F_Strings.s2010_20, F_Strings.FRS_NAME, env.data.vname2id.get(F_Strings.FRS_NAME), collectCounts(mAllFRS_NAME), collectCounts(mAllFATALITY_CASUALTY_FRS_NAME));
            output(dir, F_Strings.s2010_20, F_Strings.E_CODE, env.data.vname2id.get(F_Strings.E_CODE), collectCounts(mAllE_CODE), collectCounts(mAllFATALITY_CASUALTY_E_CODE));
            output(dir, F_Strings.s2010_20, F_Strings.MONTH_NAME, env.data.vname2id.get(F_Strings.MONTH_NAME), collectCounts(mAllMONTH_NAME), collectCounts(mAllFATALITY_CASUALTY_MONTH_NAME));
            output(dir, F_Strings.s2010_20, F_Strings.WEEKDAY_WEEKEND, env.data.vname2id.get(F_Strings.WEEKDAY_WEEKEND), collectCounts(mAllWEEKDAY_WEEKEND), collectCounts(mAllFATALITY_CASUALTY_WEEKDAY_WEEKEND));
            output(dir, F_Strings.s2010_20, F_Strings.DAY_NIGHT, env.data.vname2id.get(F_Strings.DAY_NIGHT), collectCounts(mAllDAY_NIGHT), collectCounts(mAllFATALITY_CASUALTY_DAY_NIGHT));
            output(dir, F_Strings.s2010_20, F_Strings.LATE_CALL, env.data.vname2id.get(F_Strings.LATE_CALL), collectCounts(mAllLATE_CALL), collectCounts(mAllFATALITY_CASUALTY_LATE_CALL));
            output(dir, F_Strings.s2010_20, F_Strings.BUILDING_OR_PROPERTY_TYPE, env.data.vname2id.get(F_Strings.BUILDING_OR_PROPERTY_TYPE), collectCounts(mAllBUILDING_OR_PROPERTY_TYPE), collectCounts(mAllFATALITY_CASUALTY_BUILDING_OR_PROPERTY_TYPE));
            output(dir, F_Strings.s2010_20, F_Strings.MULTI_SEATED_FLAG, env.data.vname2id.get(F_Strings.MULTI_SEATED_FLAG), collectCounts(mAllMULTI_SEATED_FLAG), collectCounts(mAllFATALITY_CASUALTY_MULTI_SEATED_FLAG));
            output(dir, F_Strings.s2010_20, F_Strings.IGNITION_TO_DISCOVERY, env.data.vname2id.get(F_Strings.IGNITION_TO_DISCOVERY), collectCounts(mAllIGNITION_TO_DISCOVERY), collectCounts(mAllFATALITY_CASUALTY_IGNITION_TO_DISCOVERY));
            output(dir, F_Strings.s2010_20, F_Strings.DISCOVERY_TO_CALL, env.data.vname2id.get(F_Strings.DISCOVERY_TO_CALL), collectCounts(mAllDISCOVERY_TO_CALL), collectCounts(mAllFATALITY_CASUALTY_DISCOVERY_TO_CALL));
            output(dir, F_Strings.s2010_20, F_Strings.HOW_DISCOVERED_DESCRIPTION, env.data.vname2id.get(F_Strings.HOW_DISCOVERED_DESCRIPTION), collectCounts(mAllHOW_DISCOVERED_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_HOW_DISCOVERED_DESCRIPTION));
            output(dir, F_Strings.s2010_20, F_Strings.BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION), collectCounts(mAllBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION));
            output(dir, F_Strings.s2010_20, F_Strings.BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION), collectCounts(mAllBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION));
            output(dir, F_Strings.s2010_20, F_Strings.BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT, env.data.vname2id.get(F_Strings.BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT), collectCounts(mAllBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT), collectCounts(mAllFATALITY_CASUALTY_BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT));
            output(dir, F_Strings.s2010_20, F_Strings.OCCUPIED_NORMAL, env.data.vname2id.get(F_Strings.OCCUPIED_NORMAL), collectCounts(mAllOCCUPIED_NORMAL), collectCounts(mAllFATALITY_CASUALTY_OCCUPIED_NORMAL));
            output(dir, F_Strings.s2010_20, F_Strings.ACCIDENTAL_OR_DELIBERATE, env.data.vname2id.get(F_Strings.ACCIDENTAL_OR_DELIBERATE), collectCounts(mAllACCIDENTAL_OR_DELIBERATE), collectCounts(mAllFATALITY_CASUALTY_ACCIDENTAL_OR_DELIBERATE));
            output(dir, F_Strings.s2010_20, F_Strings.VEHICLES, env.data.vname2id.get(F_Strings.VEHICLES), collectCounts(mAllVEHICLES), collectCounts(mAllFATALITY_CASUALTY_VEHICLES));
            output(dir, F_Strings.s2010_20, F_Strings.VEHICLES_CODE, env.data.vname2id.get(F_Strings.VEHICLES_CODE), collectCounts(mAllVEHICLES_CODE), collectCounts(mAllFATALITY_CASUALTY_VEHICLES_CODE));
            output(dir, F_Strings.s2010_20, F_Strings.PERSONNEL, env.data.vname2id.get(F_Strings.PERSONNEL), collectCounts(mAllPERSONNEL), collectCounts(mAllFATALITY_CASUALTY_PERSONNEL));
            output(dir, F_Strings.s2010_20, F_Strings.PERSONNEL_CODE, env.data.vname2id.get(F_Strings.PERSONNEL_CODE), collectCounts(mAllPERSONNEL_CODE), collectCounts(mAllFATALITY_CASUALTY_PERSONNEL_CODE));
            output(dir, F_Strings.s2010_20, F_Strings.STARTING_DELAY_DESCRIPTION, env.data.vname2id.get(F_Strings.STARTING_DELAY_DESCRIPTION), collectCounts(mAllSTARTING_DELAY_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_STARTING_DELAY_DESCRIPTION));
            output(dir, F_Strings.s2010_20, F_Strings.ACTION_NON_FRS_DESCRIPTION, env.data.vname2id.get(F_Strings.ACTION_NON_FRS_DESCRIPTION), collectCounts(mAllACTION_NON_FRS_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_ACTION_NON_FRS_DESCRIPTION));
            output(dir, F_Strings.s2010_20, F_Strings.ACTION_FRS_DESCRIPTION, env.data.vname2id.get(F_Strings.ACTION_FRS_DESCRIPTION), collectCounts(mAllACTION_FRS_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_ACTION_FRS_DESCRIPTION));
            output(dir, F_Strings.s2010_20, F_Strings.CAUSE_OF_FIRE, env.data.vname2id.get(F_Strings.CAUSE_OF_FIRE), collectCounts(mAllCAUSE_OF_FIRE), collectCounts(mAllFATALITY_CASUALTY_CAUSE_OF_FIRE));
            output(dir, F_Strings.s2010_20, F_Strings.IGNITION_POWER, env.data.vname2id.get(F_Strings.IGNITION_POWER), collectCounts(mAllIGNITION_POWER), collectCounts(mAllFATALITY_CASUALTY_IGNITION_POWER));
            output(dir, F_Strings.s2010_20, F_Strings.SOURCE_OF_IGNITION, env.data.vname2id.get(F_Strings.SOURCE_OF_IGNITION), collectCounts(mAllSOURCE_OF_IGNITION), collectCounts(mAllFATALITY_CASUALTY_SOURCE_OF_IGNITION));
            output(dir, F_Strings.s2010_20, F_Strings.ITEM_IGNITED, env.data.vname2id.get(F_Strings.ITEM_IGNITED), collectCounts(mAllITEM_IGNITED), collectCounts(mAllFATALITY_CASUALTY_ITEM_IGNITED));
            output(dir, F_Strings.s2010_20, F_Strings.ITEM_CAUSING_SPREAD, env.data.vname2id.get(F_Strings.ITEM_CAUSING_SPREAD), collectCounts(mAllITEM_CAUSING_SPREAD), collectCounts(mAllFATALITY_CASUALTY_ITEM_CAUSING_SPREAD));
            output(dir, F_Strings.s2010_20, F_Strings.RAPID_FIRE_GROWTH, env.data.vname2id.get(F_Strings.RAPID_FIRE_GROWTH), collectCounts(mAllRAPID_FIRE_GROWTH), collectCounts(mAllFATALITY_CASUALTY_RAPID_FIRE_GROWTH));
            output(dir, F_Strings.s2010_20, F_Strings.CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION, env.data.vname2id.get(F_Strings.CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION), collectCounts(mAllCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION));
            output(dir, F_Strings.s2010_20, F_Strings.CAUSE_EXPLOSION_INVOLVED, env.data.vname2id.get(F_Strings.CAUSE_EXPLOSION_INVOLVED), collectCounts(mAllCAUSE_EXPLOSION_INVOLVED), collectCounts(mAllFATALITY_CASUALTY_CAUSE_EXPLOSION_INVOLVED));
            output(dir, F_Strings.s2010_20, F_Strings.CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION, env.data.vname2id.get(F_Strings.CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION), collectCounts(mAllCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION));
            output(dir, F_Strings.s2010_20, F_Strings.CAUSE_EXPLOSION_STAGE_DESCRIPTION, env.data.vname2id.get(F_Strings.CAUSE_EXPLOSION_STAGE_DESCRIPTION), collectCounts(mAllCAUSE_EXPLOSION_STAGE_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_CAUSE_EXPLOSION_STAGE_DESCRIPTION));
            output(dir, F_Strings.s2010_20, F_Strings.CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION, env.data.vname2id.get(F_Strings.CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION), collectCounts(mAllCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION));
            output(dir, F_Strings.s2010_20, F_Strings.BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION), collectCounts(mAllBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION));
            output(dir, F_Strings.s2010_20, F_Strings.BUILDING_FLOORS_ABOVE_GROUND, env.data.vname2id.get(F_Strings.BUILDING_FLOORS_ABOVE_GROUND), collectCounts(mAllBUILDING_FLOORS_ABOVE_GROUND), collectCounts(mAllFATALITY_CASUALTY_BUILDING_FLOORS_ABOVE_GROUND));
            output(dir, F_Strings.s2010_20, F_Strings.BUILDING_FLOORS_BELOW_GROUND, env.data.vname2id.get(F_Strings.BUILDING_FLOORS_BELOW_GROUND), collectCounts(mAllBUILDING_FLOORS_BELOW_GROUND), collectCounts(mAllFATALITY_CASUALTY_BUILDING_FLOORS_BELOW_GROUND));
            output(dir, F_Strings.s2010_20, F_Strings.BUILDING_FLOOR_ORIGIN, env.data.vname2id.get(F_Strings.BUILDING_FLOOR_ORIGIN), collectCounts(mAllBUILDING_FLOOR_ORIGIN), collectCounts(mAllFATALITY_CASUALTY_BUILDING_FLOOR_ORIGIN));
            output(dir, F_Strings.s2010_20, F_Strings.BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION), collectCounts(mAllBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION));
            output(dir, F_Strings.s2010_20, F_Strings.BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION), collectCounts(mAllBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION));
            output(dir, F_Strings.s2010_20, F_Strings.FIRE_START_LOCATION, env.data.vname2id.get(F_Strings.FIRE_START_LOCATION), collectCounts(mAllFIRE_START_LOCATION), collectCounts(mAllFATALITY_CASUALTY_FIRE_START_LOCATION));
            output(dir, F_Strings.s2010_20, F_Strings.FIRE_SIZE_ON_ARRIVAL, env.data.vname2id.get(F_Strings.FIRE_SIZE_ON_ARRIVAL), collectCounts(mAllFIRE_SIZE_ON_ARRIVAL), collectCounts(mAllFATALITY_CASUALTY_FIRE_SIZE_ON_ARRIVAL));
            output(dir, F_Strings.s2010_20, F_Strings.OTHER_PROPERTY_AFFECTED_ON_ARRIVAL, env.data.vname2id.get(F_Strings.OTHER_PROPERTY_AFFECTED_ON_ARRIVAL), collectCounts(mAllOTHER_PROPERTY_AFFECTED_ON_ARRIVAL), collectCounts(mAllFATALITY_CASUALTY_OTHER_PROPERTY_AFFECTED_ON_ARRIVAL));
            output(dir, F_Strings.s2010_20, F_Strings.BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION), collectCounts(mAllBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION));
            output(dir, F_Strings.s2010_20, F_Strings.BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION), collectCounts(mAllBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION));
            output(dir, F_Strings.s2010_20, F_Strings.FIRE_SIZE_ON_ARRIVAL_DESCRIPTION, env.data.vname2id.get(F_Strings.FIRE_SIZE_ON_ARRIVAL_DESCRIPTION), collectCounts(mAllFIRE_SIZE_ON_ARRIVAL_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_FIRE_SIZE_ON_ARRIVAL_DESCRIPTION));
            output(dir, F_Strings.s2010_20, F_Strings.other_property_affected_close_d, env.data.vname2id.get(F_Strings.other_property_affected_close_d), collectCounts(mAllother_property_affected_close_d), collectCounts(mAllFATALITY_CASUALTY_other_property_affected_close_d));
            output(dir, F_Strings.s2010_20, F_Strings.spread_of_fire_d, env.data.vname2id.get(F_Strings.spread_of_fire_d), collectCounts(mAllspread_of_fire_d), collectCounts(mAllFATALITY_CASUALTY_spread_of_fire_d));
            output(dir, F_Strings.s2010_20, F_Strings.RESPONSE_TIME, env.data.vname2id.get(F_Strings.RESPONSE_TIME), collectCounts(mAllRESPONSE_TIME), collectCounts(mAllFATALITY_CASUALTY_RESPONSE_TIME));
            output(dir, F_Strings.s2010_20, F_Strings.RESPONSE_TIME_CODE, env.data.vname2id.get(F_Strings.RESPONSE_TIME_CODE), collectCounts(mAllRESPONSE_TIME_CODE), collectCounts(mAllFATALITY_CASUALTY_RESPONSE_TIME_CODE));
            output(dir, F_Strings.s2010_20, F_Strings.TIME_AT_SCENE, env.data.vname2id.get(F_Strings.TIME_AT_SCENE), collectCounts(mAllTIME_AT_SCENE), collectCounts(mAllFATALITY_CASUALTY_TIME_AT_SCENE));
            output(dir, F_Strings.s2010_20, F_Strings.TIME_AT_SCENE_CODE, env.data.vname2id.get(F_Strings.TIME_AT_SCENE_CODE), collectCounts(mAllTIME_AT_SCENE_CODE), collectCounts(mAllFATALITY_CASUALTY_TIME_AT_SCENE_CODE));
            output(dir, F_Strings.s2010_20, F_Strings.RESCUES, env.data.vname2id.get(F_Strings.RESCUES), collectCounts(mAllRESCUES), collectCounts(mAllFATALITY_CASUALTY_RESCUES));
            output(dir, F_Strings.s2010_20, F_Strings.EVACUATIONS, env.data.vname2id.get(F_Strings.EVACUATIONS), collectCounts(mAllEVACUATIONS), collectCounts(mAllFATALITY_CASUALTY_EVACUATIONS));
            output(dir, F_Strings.s2010_20, F_Strings.EVACUATIONS_CODE, env.data.vname2id.get(F_Strings.EVACUATIONS_CODE), collectCounts(mAllEVACUATIONS_CODE), collectCounts(mAllFATALITY_CASUALTY_EVACUATIONS_CODE));
            output(dir, F_Strings.s2010_20, F_Strings.BUILDING_EVACUATION_DELAY_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_EVACUATION_DELAY_DESCRIPTION), collectCounts(mAllBUILDING_EVACUATION_DELAY_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_BUILDING_EVACUATION_DELAY_DESCRIPTION));
            output(dir, F_Strings.s2010_20, F_Strings.BUILDING_EVACUATION_TIME_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_EVACUATION_TIME_DESCRIPTION), collectCounts(mAllBUILDING_EVACUATION_TIME_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_BUILDING_EVACUATION_TIME_DESCRIPTION));
            // Normal Dwelling Types
            output(dir, F_Strings.s2010_20, F_Strings.OCCUPANCY_TYPE, env.data.vname2id.get(F_Strings.OCCUPANCY_TYPE), collectCounts(mAllOCCUPANCY_TYPE), collectCounts(mAllFATALITY_CASUALTY_OCCUPANCY_TYPE));
            output(dir, F_Strings.s2010_20, F_Strings.WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT, env.data.vname2id.get(F_Strings.WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT), collectCounts(mAllWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT), collectCounts(mAllFATALITY_CASUALTY_WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT));
            output(dir, F_Strings.s2010_20, F_Strings.ALARM_SYSTEM, env.data.vname2id.get(F_Strings.ALARM_SYSTEM), collectCounts(mAllALARM_SYSTEM), collectCounts(mAllFATALITY_CASUALTY_ALARM_SYSTEM));
            output(dir, F_Strings.s2010_20, F_Strings.ALARM_SYSTEM_TYPE, env.data.vname2id.get(F_Strings.ALARM_SYSTEM_TYPE), collectCounts(mAllALARM_SYSTEM_TYPE), collectCounts(mAllFATALITY_CASUALTY_ALARM_SYSTEM_TYPE));
            output(dir, F_Strings.s2010_20, F_Strings.ALARM_REASON_FOR_POOR_OUTCOME, env.data.vname2id.get(F_Strings.ALARM_REASON_FOR_POOR_OUTCOME), collectCounts(mAllALARM_REASON_FOR_POOR_OUTCOME), collectCounts(mAllFATALITY_CASUALTY_ALARM_REASON_FOR_POOR_OUTCOME));
            // Other Dwelling Types
            output(dir, F_Strings.s2010_20, F_Strings.FSO_APPLY, env.data.vname2id.get(F_Strings.FSO_APPLY), collectCounts(mAllFSO_APPLY), collectCounts(mAllFATALITY_CASUALTY_FSO_APPLY));
            output(dir, F_Strings.s2010_20, F_Strings.SAFETY_SYSTEM, env.data.vname2id.get(F_Strings.SAFETY_SYSTEM), collectCounts(mAllSAFETY_SYSTEM), collectCounts(mAllFATALITY_CASUALTY_SAFETY_SYSTEM));
//            // FIRE_SAFETY scores
            outputScores(dir, F_Strings.s2010_20, F_Strings.FIRE_DANGER_SCORE, env.data.vname2id.get(F_Strings.FIRE_DANGER_SCORE), collectCounts(mAllFDScore), collectCounts(mAllFATALITY_CASUALTY_FDScore));
            outputScores(dir, F_Strings.s2010_20, F_Strings.FIRE_SAFETY_FAILURE_SCORE_MANAGEMENT, env.data.vname2id.get(F_Strings.FIRE_SAFETY_FAILURE_SCORE_MANAGEMENT), collectCounts(mAllFSFScoreManagement), collectCounts(mAllFATALITY_CASUALTY_FSFScoreManagement));
            outputScores(dir, F_Strings.s2010_20, F_Strings.FIRE_SAFETY_FAILURE_SCORE_CONSTRUCTION, env.data.vname2id.get(F_Strings.FIRE_SAFETY_FAILURE_SCORE_CONSTRUCTION), collectCounts(mAllFSFScoreConstruction), collectCounts(mAllFATALITY_CASUALTY_FSFScoreConstruction));
            outputScores(dir, F_Strings.s2010_20, F_Strings.FIRE_SAFETY_FAILURE_SCORE_FIRE_FIGHTING, env.data.vname2id.get(F_Strings.FIRE_SAFETY_FAILURE_SCORE_FIRE_FIGHTING), collectCounts(mAllFSFScoreFireFighting), collectCounts(mAllFATALITY_CASUALTY_FSFScoreFireFighting));
            outputScores(dir, F_Strings.s2010_20, F_Strings.CombinedScore, env.data.vname2id.get(F_Strings.CombinedScore), collectCounts(mAllCombinedScore), collectCounts(mAllFATALITY_CASUALTY_CombinedScore));
            outputScores(dir, F_Strings.s2010_20, F_Strings.HoleyCheese, env.data.vname2id.get(F_Strings.HoleyCheese), collectCounts(mAllHoleyCheese), collectCounts(mAllFATALITY_CASUALTY_HoleyCheese));
            outputScores(dir, F_Strings.s2010_20, F_Strings.DelaysToFireFightingAndNotFirespreadHoleyCheese, env.data.vname2id.get(F_Strings.DelaysToFireFightingAndNotFirespreadHoleyCheese), collectCounts(mAllDelaysToFireFightingAndNotFirespreadHoleyCheese), collectCounts(mAllFATALITY_CASUALTY_DelaysToFireFightingAndNotFirespreadHoleyCheese));
            outputScores(dir, F_Strings.s2010_20, F_Strings.FirespreadAndNotDelaysToFireFightingHoleyCheese, env.data.vname2id.get(F_Strings.FirespreadAndNotDelaysToFireFightingHoleyCheese), collectCounts(mAllFirespreadAndNotDelaysToFireFightingHoleyCheese), collectCounts(mAllFATALITY_CASUALTY_FirespreadAndNotDelaysToFireFightingHoleyCheese));
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(F_Main.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param year Year
     * @param f File path
     * @param sn SheetName
     * @param col All tenures column
     * @param br Bungalow row
     * @param ar All Dwellings row
     * @return
     */
    public F_ARecord getARecord(String year, Path f, String sn, int col, int br, int pbd, int ar) {
        F_ARecord r = new F_ARecord();
        r.year = year;
        Workbook wb = getWorkbook(f);
        if (wb != null) {
            DataFormatter df = new DataFormatter();
            int i = 1;
            int numberOfSheets = wb.getNumberOfSheets();
            for (Sheet sheet : wb) {
                // Write out to text file
                String sheetName = sheet.getSheetName();
                //String s = "Sheet " + i + " of " + numberOfSheets + ": " + sheetName;
                //System.out.println(s);
                if (sheetName.equalsIgnoreCase(sn)) {
                    String s = "Sheet " + i + " of " + numberOfSheets + ": " + sheetName;
                    System.out.println(s);
                    Row row;
                    Cell cell;
                    row = sheet.getRow(5);
                    cell = row.getCell(col);
                    if (df.formatCellValue(cell).equalsIgnoreCase(F_Strings.allTenures)) {
                        System.out.println("Found \"" + F_Strings.allTenures + "\".");
                    }
                    row = sheet.getRow(br);
                    cell = row.getCell(1);
                    if (df.formatCellValue(cell).equalsIgnoreCase(F_Strings.bungalow)) {
                        System.out.println("Found \"" + F_Strings.bungalow + "\".");
                        r.totalBungalows = formatCell(df, row.getCell(col));
                    }
                    row = sheet.getRow(ar);
                    cell = row.getCell(1);
                    if (df.formatCellValue(cell).equalsIgnoreCase(F_Strings.allDwellings)) {
                        System.out.println("Found \"" + F_Strings.allDwellings + "\".");
                        r.allDwellings = formatCell(df, row.getCell(col));
                    }
                    row = sheet.getRow(br + pbd);
                    cell = row.getCell(1);
                    if (df.formatCellValue(cell).equalsIgnoreCase(F_Strings.purposeBuiltFlatLowRise)) {
                        System.out.println("Found \"" + F_Strings.purposeBuiltFlatLowRise + "\".");
                        r.purposeBuiltFlatLowRise = formatCell(df, row.getCell(col));
                    }
                    row = sheet.getRow(br + pbd + 1);
                    cell = row.getCell(1);
                    if (df.formatCellValue(cell).equalsIgnoreCase(F_Strings.purposeBuiltFlatHighRise)) {
                        System.out.println("Found \"" + F_Strings.purposeBuiltFlatHighRise + "\".");
                        r.purposeBuiltFlatHighRise = formatCell(df, row.getCell(col));
                    }
                    System.out.println(r.toString());

//                    for (Row row1 : sheet) {
//                        int rowNum = row1.getRowNum();
//                        for (Cell cell1 : row1) {
//                            int columnIndex = cell1.getColumnIndex();
//                            String cellValue = df.formatCellValue(cell1);
//                            if (cellValue.equalsIgnoreCase("bungalow")) {
//                                System.out.println("row=" + rowNum
//                                    + ", col=" + columnIndex
//                                    + ", value=" + df.formatCellValue(cell1));
//                            }
//                            if (cellValue.equalsIgnoreCase("all dwellings")) {
//                                
//                            }
//                        }
//                    }
                }
                i++;
            }
        }
        return r;
    }

    /**
     *
     * @param year Year
     * @param f File path
     * @param sn SheetName
     * @param col All tenures column
     * @param br Bungalow row
     * @param ar All Dwellings row
     * @return
     */
    public F_ARecord getARecordHSSF(String year, Path f, String sn, int col, int br, int pbd, int ar) {
        F_ARecord r = new F_ARecord();
        r.year = year;
        HSSFWorkbook wb = getHSSFWorkbook(f);
        if (wb != null) {
            DataFormatter df = new DataFormatter();
            int i = 1;
            int numberOfSheets = wb.getNumberOfSheets();
            for (Sheet sheet : wb) {
                // Write out to text file
                String sheetName = sheet.getSheetName().trim();
                //String s = "Sheet " + i + " of " + numberOfSheets + ": " + sheetName;
                //System.out.println(s);
                if (sheetName.equalsIgnoreCase(sn)) {
                    String s = "Sheet " + i + " of " + numberOfSheets + ": " + sheetName;
                    System.out.println(s);
                    Row row;
                    Cell cell;
                    row = sheet.getRow(5);
                    cell = row.getCell(col);
                    if (df.formatCellValue(cell).equalsIgnoreCase(F_Strings.allTenures)) {
                        System.out.println("Found \"" + F_Strings.allTenures + "\".");
                    }
                    row = sheet.getRow(br);
                    cell = row.getCell(1);
                    if (df.formatCellValue(cell).equalsIgnoreCase(F_Strings.bungalow)) {
                        System.out.println("Found \"" + F_Strings.bungalow + "\".");
                        r.totalBungalows = formatCell(df, row.getCell(col));
                    }
                    row = sheet.getRow(ar);
                    cell = row.getCell(1);
                    if (df.formatCellValue(cell).equalsIgnoreCase(F_Strings.all_dwelling_types)) {
                        System.out.println("Found \"" + F_Strings.all_dwelling_types + "\".");
                        r.allDwellings = formatCell(df, row.getCell(col));
                    }
                    row = sheet.getRow(br + pbd);
                    cell = row.getCell(1);
                    if (df.formatCellValue(cell).equalsIgnoreCase(F_Strings.purposeBuiltFlatLowRise)) {
                        System.out.println("Found \"" + F_Strings.purposeBuiltFlatLowRise + "\".");
                        r.purposeBuiltFlatLowRise = formatCell(df, row.getCell(col));
                    }
                    row = sheet.getRow(br + pbd + 1);
                    cell = row.getCell(1);
                    if (df.formatCellValue(cell).equalsIgnoreCase(F_Strings.purposeBuiltFlatHighRise)) {
                        System.out.println("Found \"" + F_Strings.purposeBuiltFlatHighRise + "\".");
                        r.purposeBuiltFlatHighRise = formatCell(df, row.getCell(col));
                    }
                    System.out.println(r.toString());

//                    for (Row row1 : sheet) {
//                        int rowNum = row1.getRowNum();
//                        for (Cell cell1 : row1) {
//                            int columnIndex = cell1.getColumnIndex();
//                            String cellValue = df.formatCellValue(cell1);
//                            if (cellValue.equalsIgnoreCase("bungalow")) {
//                                System.out.println("row=" + rowNum
//                                    + ", col=" + columnIndex
//                                    + ", value=" + df.formatCellValue(cell1));
//                            }
//                            if (cellValue.equalsIgnoreCase("all dwellings")) {
//                                
//                            }
//                        }
//                    }
                }
                i++;
            }
        }
        return r;
    }

    public int formatCell(DataFormatter df, Cell cell) {
        //return new BigDecimal(df.formatCellValue(cell).replaceAll(",", "")).multiply(THOUSAND).intValueExact();
        return Math_BigDecimal.round(new BigDecimal(cell.getNumericCellValue()).multiply(THOUSAND), 0).intValueExact();
    }
}
