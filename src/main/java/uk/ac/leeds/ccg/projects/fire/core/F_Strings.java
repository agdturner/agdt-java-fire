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
    // --------------
    public static final String FSO_APPLY = "FSO_APPLY";
    public static final String SAFETY_SYSTEM = "SAFETY_SYSTEM";
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
    // ---------------
    // FINANCIAL_YEAR
    public static final String S2010_11 = "2010/11";
    public static final String S2011_12 = "2011/12";
    public static final String S2012_13 = "2012/13";
    public static final String S2013_14 = "2013/14";
    public static final String S2014_15 = "2014/15";
    public static final String S2015_16 = "2015/16";
    public static final String S2016_17 = "2016/17";
    public static final String S2017_18 = "2017/18";
    public static final String S2018_19 = "2018/19";
    public static final String S2019_20 = "2019/20";
    public static final String s2010_20 = "2010_20";

    // BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION
    public static final String Atria = "Atria";
    public static final String Cladding = "Cladding";
    public static final String None = "None";
    public static final String NotKnown = "Not known";
    public static final String Other = "Other";
    public static final String SandwichPanels = "Sandwich panels";
    public static final String Thatch = "Thatch";
    public static final String TimberFramed = "Timber framed";

    // FatalityOrCasualty
    public static final String FatalityOrCasualty = "Fatality/Casualty";
    //public static final String FatalityOrCasualty = "Fatality_OR_Casualty";
    //public static final String None = "None";

    // DAY_NIGHT
    public static final String Afternoon = "Afternoon";
    public static final String Evening = "Evening";
    public static final String Morning = "Morning";
    public static final String Night = "Night";

    // BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION
    public static final String BreachedCurrentBuildingWork = "Breached - current building work";
    public static final String BreachedFireDoorsLeftOpenOrIncorrectlyFitted = "Breached - fire doors left open or incorrectly fitted";
    public static final String BreachedPreviousBuildingWork = "Breached - previous building work";
    public static final String DamageToCompartmentation = "Damage to compartmentation";
    public static final String FireSpreadThroughGapsOrVoidsInConstruction = "Fire spread through gaps or voids in construction";
    public static final String NoCompartmentationInBuilding = "No compartmentation in building";
    public static final String NotApplicable = "Not applicable";
    //public static final String Other = "Other";
    public static final String StoppedOrCheckedSpread = "Stopped/Checked spread";

    // BUILDING_OR_PROPERTY_TYPE
    public static final String BungalowSingleOccupancy = "Bungalow - single occupancy";
    public static final String PurposeBuiltHighRiseFlats = "Purpose Built High Rise (10+) Flats";
    public static final String PurposeBuiltMediumRiseFlats = "Purpose Built Medium Rise (4-9) Flats";
    public static final String PurposeBuiltLowRiseFlats = "Purpose Built Low Rise (1-3) Flats/Maisonettes";

    // spread_of_fire_d
    public static final String LimitedTo2Floors = "Limited to 2 floors";
    public static final String LimitedToFloorOfOrigin = "Limited to floor of origin (not whole building)";
    public static final String LimitedToItem1stIgnited = "Limited to item 1st ignited";
    public static final String LimitedToRoomOfOrigin = "Limited to room of origin";
    public static final String NoFireDamage = "No fire damage";
    public static final String RoofsOrRoofSpaces = "Roofs/ Roof spaces";
    public static final String WholeBuildingOrAffectingMoreThan2Floors = "Whole Building/ Affecting more than 2 floors";

    // STARTING_DELAY_DESCRIPTION
    public static final String Delay = "Delay"; // This is an amalgam of any delay.
    public static final String DelayDueToAccessingFireBuildingType = "Delay due to: Accessing fire - due to building type e.g. high rise building";
    public static final String DelayDueToAccessingFireLargeSite = "Delay due to: Accessing fire - large site";
    public static final String DelayDueToAccessingFireSecurity = "Delay due to: Accessing fire - security doors/other security measures";
    public static final String DelayDueToAssaultOnFirefighters = "Delay due to: Assault on Firefighters";
    public static final String DelayDueToCivilDisturbance = "Delay due to: Civil disturbance";
    public static final String DelayDueToLocationOfFireNotImmediatelyEvident = "Delay due to: Location of fire not immediately evident";
    public static final String DelayDueToSentToWrongLocation = "Delay due to: Sent to wrong location";
    public static final String DelayDueToVehicleAccessProblems = "Delay due to: Vehicle access problems";
    public static final String No_delay = "No delay";

    // ITEM_CAUSING_SPREAD
    public static final String ClothingOrTextiles = "Clothing/ Textiles";
    public static final String ExplosivesOrGasOrChemicals = "Explosives/ Gas/ Chemicals";
    public static final String FoamOrRubberOrPlastic = "Foam/ Rubber/ Plastic";
    public static final String Food = "Food";
    public static final String FurnitureOrFurnishings = "Furniture/ Furnishings";
    public static final String OtherOrNotKnownOrAnimalOrDecorationOrCelebrationOrVegetationOrNone = "Other/ Not known/ Animal/ Decoration/ Celebration/ Vegetation/ None";
    public static final String PaperOrCardboard = "Paper/ Cardboard";
    public static final String RubbishOrWasteOrRecycling = "Rubbish/Waste/Recycling";
    public static final String StructuralOrFixturesOrFittings = "Structural/ Fixtures/ Fittings";
    public static final String Wood = "Wood";

    // RAPID_FIRE_GROWTH
    //public static final String None = "None";
    //public static final String NotKnown = "Not known";
    public static final String Yes = "Yes";

    // OTHER_PROPERTY_AFFECTED_ON_ARRIVAL
    public static final String AnotherPropertyAffected = "Another property was affected";
    public static final String NoOtherPropertyAffected = "No other property was affected";

    // other_property_affected_close_d
    //public static final String AnotherPropertyAffected = "Another property was affected";
    //public static final String NoOtherPropertyAffected = "No other property was affected";
    //
    // FIRE_SIZE_ON_ARRIVAL
    //public static final String LimitedTo2Floors = "Limited to 2 floors";
    //public static final String LimitedToFloorOfOrigin = "Limited to floor of origin (not whole building)";
    //public static final String LimitedToItem1stIgnited = "Limited to item 1st ignited";
    //public static final String LimitedToRoomOfOrigin = "Limited to room of origin";
    //public static final String NoFireDamage = "No fire damage";
    //public static final String RoofsOrRoofSpaces = "Roofs/ Roof spaces";
    //public static final String WholeBuildingOrAffectingMoreThan2Floors = "Whole Building/ Affecting more than 2 floors";
    //
    // RESCUES
    public static final String OneOrMore = "OneOrMore"; // This is an amalgam for 1+

    // BUILDING_EVACUATION_DELAY_DESCRIPTION
    public static final String DelayDueToBuildingLayoutOrSignagePoor = "Delay due to: Building layout/signage poor";
    public static final String DelayDueToBuildingManagementPoor = "Delay due to: Building management poor";
    public static final String DelayElderlyOrDisabled = "Delay due to: Elderly or disabled";
    public static final String DelayDueToFirefightingActionsPublic = "Delay due to: Firefighting actions - public";
    public static final String DelayDueToFirefightingActionsByFireServiceContraflowOnStairs = "Delay due to: Firefighting actions by fire service - contraflow on stairs";
    public static final String DelayDueToMeansOfEscapeExitsLocked = "Delay due to: Means of escape - exits locked";
    public static final String DelayDueToMeansOfEscapeItemsStored = "Delay due to: Means of escape - items stored";
    public static final String DelayDueToMeansOfEscapeNotSuitable = "Delay due to: Means of escape - not suitable";
    public static final String DelayDueToMeansOfEscapeOther = "Delay due to: Means of escape - other";
    public static final String DelayDueToOccupantsDidNotRespondToAutomaticAlarm = "Delay due to: Occupants did not respond to automatic alarm";
    public static final String DelayDueToOccupantsGatheringPossessions = "Delay due to: Occupants gathering possessions";
    public static final String DelayDueToReenteredBuilding = "Delay due to: Re-entered building";
    public static final String EvacuationButNoDelay = "Evacuation, but no delay";
    //public static final String NotApplicable = "Not applicable";

    // OCCUPANCY_TYPE
    public static final String ThreeOrMoreAdultsUnderPensionableAgeNoChildren = "3 or more adults under pensionable age, no child/ren";
    public static final String ThreeOrMoreAdultsWithDependantChildren = "3 or more adults with dependant child/ren";
    public static final String CoupleBothUnderPensionableAgeWithNoChildren = "Couple both under pensionable age with no children";
    public static final String CoupleOneOrMoreOverPensionableAgeNoChildren = "Couple one or more over pensionable age, no child/ren";
    public static final String CoupleWithDependantChildren = "Couple with dependant child/ren";
    public static final String LoneParentWithDependantChildren = "Lone parent with dependant child/ren";
    public static final String LonePersonOverPensionableAge = "Lone person over pensionable age";
    public static final String LonePersonUnderPensionableAge = "Lone person under pensionable age";
    //public static final String NotKnown = "Not known";
    //public static final String Other = "Other";

    // CAUSE_OF_FIRE
    public static final String AccumulationOfFlammableMaterial = "Accumulation of flammable material";
    public static final String FaultInEquipmentOrAppliance = "Fault in equipment or appliance";
    public static final String FaultyFuelSupplyElectricity = "Faulty fuel supply - electricity";
    public static final String FaultyFuelSupplyGas = "Faulty fuel supply - gas";
    public static final String FaultyFuelSupplyPetrolProduct = "Faulty fuel supply - petrol product";
    public static final String FaultyLeadsToEquipmentOrAppliance = "Faulty leads to equipment or appliance";
    public static final String OverheatingUnknownCause = "Overheating, unknown cause";
    public static final String FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause = "FaultyEquipmentOrApplianceOrFuelSupplyOrLeadsOrOverheatingUnknownCause";

    // BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION
    public static final String ContentsContributingToAbnormalFireSpreadOrSmokeProduction = "Contents contributing to abnormal fire spread /smoke production";
    public static final String ExitRouteBlockedBySmokeOrFlames = "Exit route blocked by smoke/flames";
    public static final String ExitsBlocked = "Exits blocked (e.g. Materials stored blocking exit)";
    public static final String ExitsLocked = "Exits locked";
    //public static final String NotApplicable = "Not applicable";
    public static final String OKNoVisibleConcerns = "OK - no visible concerns";
    //public static final String Other = "Other";
    public static final String PoorImplementation = "Poor implementation e.g. doors swing the wrong way";

    // ALARM_REASON_FOR_POOR_OUTCOME
    public static final String AlarmRaisedBeforeSystemOperated = "Alarm raised before system operated";
    public static final String DefectiveBattery = "Defective battery";
    public static final String FaultySystemOrIncorrectlyInstalled = "Faulty system/ Incorrectly installed";
    public static final String FireInAreaNotCoveredBySystem = "Fire in area not covered by system";
    public static final String FireProductsDidNotReachDetectors = "Fire products did not reach detector(s)";
    public static final String MissingBattery = "Missing battery";
    public static final String NoAlarmPresent = "No alarm present";
    public static final String NoOtherPersonResponded = "No other person responded";
    public static final String NoPersonInEarshot = "No person in earshot";
    public static final String OccupantsDidNotRespond = "Occupants did not respond";
    public static final String OtherActPreventingAlarmFromOperating = "Other act preventing alarm from operating";
    public static final String OtherOrUnspecified = "Other/ Unspecified";

    // WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT
    public static final String DontKnow = "don't know";
    public static final String No = "no";
    //public static final String Yes = "yes";

    // ALARM_SYSTEM_TYPE
    public static final String BatteryPowered = "Battery powered";
    public static final String MainsPowered = "Mains powered";
    //public static final String NoAlarmPresent = "No alarm present";
    //public static final String OtherOrUnspecified = "Other/ Unspecified";

    // ALARM_SYSTEM
    public static final String AlarmAbsent = "Alarm Absent";
    public static final String AlarmPresent = "Alarm Present";

    // MULTI_SEATED_FLAG
    public static final String no = "no";
    public static final String yes = "yes";
    
    // IGNITION_TO_DISCOVERY
    public static final String FiveToThirtyMinutes = "5 to 30 minutes";
    public static final String Immediately = "Immediately";
    //public static final String NotKnown = "Not known";
    public static final String Over2Hours = "Over 2 hours";
    public static final String Over30MinutesAndUpto2Hours = "Over 30 minutes and up to 2 hours";
    public static final String Under5Minutes = "Under 5 minutes";

    // DISCOVERY_TO_CALL
    //public static final String FiveToThirtyMinutes = "5 to 30 minutes";
    //public static final String Immediately = "Immediately";
    //public static final String NotKnown = "Not known";
    //public static final String Over2Hours = "Over 2 hours";
    //public static final String Over30MinutesAndUpto2Hours = "Over 30 minutes and up to 2 hours";
    //public static final String Under5Minutes = "Under 5 minutes";
    // RESPONSE_TIME
    public static final String T1To2mins = "1 to 2 mins";
    public static final String T2To3mins = "2 to 3 mins";
    public static final String T3To4mins = "3 to 4 mins";
    public static final String T4To5mins = "4 to 5 mins";
    public static final String T5To6mins = "5 to 6 mins";
    public static final String T6To7mins = "6 to 7 mins";
    public static final String T7To8mins = "7 to 8 mins";
    public static final String T8To9mins = "8 to 9 mins";
    public static final String T9To10mins = "9 to 10 mins";
    public static final String T10To15mins = "10 to 15 mins";
    public static final String T15To20mins = "15 to 20 mins";
    public static final String T20To60mins = "20 to 60 mins";
    //public static final String NotKnown = "Not known";

    // RESCUES
    public static final String P0 = "0";
    public static final String P1 = "1";
    public static final String P2 = "2";
    public static final String P3 = "3";
    public static final String P4 = "4";
    public static final String P5 = "5";
    public static final String P6 = "6";
    public static final String P7 = "7";
    public static final String P8 = "8";
    public static final String P9 = "9";
    public static final String P10 = "10";
    public static final String P11 = "11";
    public static final String P12 = "12";
    public static final String P13 = "13";
    public static final String P14 = "14";
    public static final String P15 = "15";
    public static final String P16 = "16";
    public static final String P22 = "22";

    // EVACUATIONS
    //public static final String P0 = "0";
    public static final String EUpTo5 = "Up to 5";
    public static final String E6To20 = "6 to 20";
    public static final String E21To50 = "21 to 50";
    public static final String E51To100 = "51 to 100";
    public static final String E101To250 = "101 to 250";
    public static final String E251To1000 = "251 to 1,000";

    //FIRE_SAFETY Scores
    public static final String FIRE_SAFETY_SUCCESS_SCORE = "FIRE_SAFETY_SUCCESS_SCORE";
    public static final String FIRE_SAFETY_FAILURE_SCORE = "FIRE_SAFETY_FAILURE_SCORE";
    
    //public static final String  = "";
    // EHS data
    // --------
    public static final String allDwellings = "all dwellings";
    public static final String all_dwelling_types = "all dwelling types";
    public static final String allTenures = "all tenures";
    public static final String bungalow = "bungalow";
    public static final String purposeBuiltFlatLowRise = "purpose built flat, low rise";
    public static final String purposeBuiltFlatHighRise = "purpose built flat, high rise";

    public F_Strings() {
        super();
    }
}
