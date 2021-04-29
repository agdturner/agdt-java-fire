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

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import uk.ac.leeds.ccg.data.Data_Record;
import uk.ac.leeds.ccg.projects.fire.core.F_Strings;
import uk.ac.leeds.ccg.projects.fire.data.F_Data;
import uk.ac.leeds.ccg.projects.fire.id.F_RecordID;

/**
 * All variables store the IDs of values. The String version of the values can
 * be looked up using these IDs from the maps stored with the data
 * ({@link F_Data#id2names} and {@link F_Data#vname2id}).
 *
 * The variables are coded from the Fire Incident Reporting System:
 * https://www.irs.fire.gov.uk/irshelp/default.htm
 *
 * @author Andy Turner
 */
public abstract class F_Dwellings_Integer_Record0 extends Data_Record {

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

    /**
     * Type 1: Failure Flags - these are flags to indicate where something went
     * wrong at any point in this incident but without attributing blame / cause
     * / connection. These are like an early warning system for us. So they will
     * incorporate all the existing failure flags plus any more we think of.
     * These flags simply alert us to every field where something happened that
     * should not happen. We could equally call them Exceptional Event Flags.
     * They would include rescues, evacuations, casualties and fatalities; plus
     * when the fire fighters are attacked by residents!
     */
    /**
     * Type 2: Building System Failure Flags - these are flags to indicate where
     * existing alarms, compartmentation, means of escape, special types of
     * construction and delays to fire fighting caused by building issues were
     * present. These are indicative of mis-management, poor construction, and
     * regulatory failures, but further exploration would be needed to pass
     * judgement if possible. So these are just clear failures of the building.
     * They would include fire spread flags.
     */
    /**
     * Type 3: Fire Fighting Failure Flags - these are flags to indicate where
     * actions taken by the fire service impeded the fire and rescue. That would
     * include slow response times, delays to fire fighting caused by the FRS,
     * issues to do with evacuation (if we can see these).
     */
    /**
     * Type 4: Enhanced Risks Flags - these are similar to Andy's Danger Flags
     * and Phil's idea of the swiss cheese. But also a bit different. I think we
     * need to identify all types of data we have that suggest enhanced risks of
     * something going wrong, additional danger, that is really about connecting
     * type of building, type of occupant and type of fire together. Some of
     * these risks are already identified in previous flags but there may be
     * more across different fields or within those we previously looked at and
     * did not include as Failures. For example, here I am thinking of:
     * <pre>
     * i) time of fire
     * ii) lone occupiers or those over pension age
     * iii) fire that starts through faulty equipment / services or while people
     * are asleep or deliberately - limiting capacity of occupiers to detect and
     * put out fire themselves or call 999
     * iv) fires in buildings with cladding or timber frames
     * v) buildings with clear evidence of poor management
     * vi) buildings where not active suppression systems are present or no
     * alarms fitted
     * vii) buildings above floors and the higher up the greater problems for
     * escaping and fire fighting etc.
     * </pre> The point of the Enhanced Risk Flags is to identify all of the
     * possible things that could turn a fire - however small at the outset -
     * into a raging inferno and killing machine. Another Grenfell These risks
     * will definitely overlap other types of flags. When we combine these
     * additional risks with failures of building / fire fighting and
     * exceptional / unexpected events, we get more than swiss cheese. We get
     * fondue.
     */
    /**
     * Connectivity Flags
     */
    /**
     * Variable added for project analysis to classify fires by fire danger.
     */
    public Integer fDScore;

    /**
     * Variable added for project analysis to classify fires in terms of
     * potential failures as a result of delays to fire fighting.
     */
    public Integer combinedScore;

    /**
     * Variable added for project analysis to classify fires in terms of
     * potential failures as a result of delays to fire fighting.
     */
    public Integer delaysToFireFightingScore;

    /**
     * Variable added for project analysis to classify fires in terms of
     * potential failures as a result of cause of fire.
     */
    public Integer causeOfFireScore;

    /**
     * Variable added for project analysis to classify fires in terms of
     * potential failures as a result of fire spread issues.
     */
    public Integer fireSpreadScore;

    /**
     * Variable added for project analysis to classify fires in terms of
     * potential failures as a result of evacuation issues.
     */
    public Integer evacuationScore;

    /**
     * Variable added for project analysis to classify fires in terms of
     * potential failures as a result of delays to fire fighting, cause of fire,
     * fire spread issues and evacuation issues.
     */
    public Integer holeyCheese;

    /**
     * Variable added for project analysis to classify fires in terms of
     * potential failures as a result of delays to fire fighting and not fire
     * spread issues.
     */
    public Integer delaysToFireFightingAndNotFirespreadHoleyCheese;

    /**
     * Variable added for project analysis to classify fires in terms of
     * potential failures as a result of fire spread issues and not delays to
     * fire fighting.
     */
    public Integer firespreadAndNotDelaysToFireFightingHoleyCheese;

    /**
     * Variable added for project analysis to classify fires by fire safety
     * failures for Construction.
     */
    public Integer fSFScoreConstruction;

    /**
     * Variable added for project analysis to classify fires by fire safety
     * failures for Management.
     */
    public Integer fSFScoreManagement;

    /**
     * Variable added for project analysis to classify fires by fire safety
     * failures for Construction.
     */
    public Integer fSFScoreFireFighting;
    
    /**
     * Fire spread by time FRS arrive.
     */
    public Integer AType;
    
    /**
     * Fire spread at fire stop
     */
    public Integer BType;

    public F_Dwellings_Integer_Record0(F_Dwellings_String_Record0 r, F_Data d) throws Exception {
        super(r.id);
        tID_number = r.tID_number;
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
        tOCCUPIED_NORMAL = d.name2ids.get(d.vname2id.get(F_Strings.OCCUPIED_NORMAL)).get(r.tOCCUPIED_NORMAL);
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
        // Assign AType and BType
        Integer vFSOAD = d.vname2id.get(F_Strings.FIRE_SIZE_ON_ARRIVAL_DESCRIPTION);
        HashMap<String, Integer> name2idFSOAD = d.name2ids.get(vFSOAD);
        Integer vOPAOA = d.vname2id.get(F_Strings.OTHER_PROPERTY_AFFECTED_ON_ARRIVAL);
        HashMap<String, Integer> name2idOPAOA = d.name2ids.get(vOPAOA);
        AType = 0;
        if (tFIRE_SIZE_ON_ARRIVAL_DESCRIPTION.equals(name2idFSOAD.get(F_Strings.LimitedToItem1stIgnited))
                || tFIRE_SIZE_ON_ARRIVAL_DESCRIPTION.equals(name2idFSOAD.get(F_Strings.LimitedToRoomOfOrigin))
                || tFIRE_SIZE_ON_ARRIVAL_DESCRIPTION.equals(name2idFSOAD.get(F_Strings.NotApplicable))) {
            if (tOTHER_PROPERTY_AFFECTED_ON_ARRIVAL.equals(name2idOPAOA.get(F_Strings.NoOtherPropertyAffected))) {
                AType = 1;
            } else if (tOTHER_PROPERTY_AFFECTED_ON_ARRIVAL.equals(name2idOPAOA.get(F_Strings.AnotherPropertyAffected))) {
                AType = 2;
            }
        } else {
            if (tOTHER_PROPERTY_AFFECTED_ON_ARRIVAL.equals(name2idOPAOA.get(F_Strings.NoOtherPropertyAffected))) {
                AType = 3;
            } else if (tOTHER_PROPERTY_AFFECTED_ON_ARRIVAL.equals(name2idOPAOA.get(F_Strings.AnotherPropertyAffected))) {
                AType = 4;
            }
        }
        Integer vSOF = d.vname2id.get(F_Strings.spread_of_fire_d);
        HashMap<String, Integer> name2idSOF = d.name2ids.get(vSOF);
        Integer vOPAC = d.vname2id.get(F_Strings.other_property_affected_close_d);
        HashMap<String, Integer> name2idOPAC = d.name2ids.get(vOPAC);        
        BType = 0;
        if (tspread_of_fire_d.equals(name2idSOF.get(F_Strings.LimitedToItem1stIgnited))
                || tspread_of_fire_d.equals(name2idSOF.get(F_Strings.LimitedToRoomOfOrigin))
                || tspread_of_fire_d.equals(name2idSOF.get(F_Strings.NoFireDamage))) {
            if (tother_property_affected_close_d.equals(name2idOPAC.get(F_Strings.NoOtherPropertyAffected))) {
                BType = 1;
            } else if (tother_property_affected_close_d.equals(name2idOPAC.get(F_Strings.AnotherPropertyAffected))) {
                BType = 2;
            }
        } else {
            if (tother_property_affected_close_d.equals(name2idOPAC.get(F_Strings.NoOtherPropertyAffected))) {
                BType = 3;
            } else if (tother_property_affected_close_d.equals(name2idOPAC.get(F_Strings.AnotherPropertyAffected))) {
                BType = 4;
            }
        }
    }

    @Override
    public F_RecordID getId() {
        return (F_RecordID) id;
    }

    public String getFRS_NAME(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.FRS_NAME)).get(tFRS_NAME);
    }

    public String getE_CODE(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.E_CODE)).get(tE_CODE);
    }

    public String getFINANCIAL_YEAR(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.FINANCIAL_YEAR)).get(tFINANCIAL_YEAR);
    }

    public String getMONTH_NAME(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.MONTH_NAME)).get(tMONTH_NAME);
    }

    public String getWEEKDAY_WEEKEND(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.WEEKDAY_WEEKEND)).get(tWEEKDAY_WEEKEND);
    }

    public String getDAY_NIGHT(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.DAY_NIGHT)).get(tDAY_NIGHT);
    }

    public String getBUILDING_OR_PROPERTY_TYPE(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.BUILDING_OR_PROPERTY_TYPE)).get(tBUILDING_OR_PROPERTY_TYPE);
    }

    public String getLATE_CALL(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.LATE_CALL)).get(tLATE_CALL);
    }

    public String getMULTI_SEATED_FLAG(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.MULTI_SEATED_FLAG)).get(tMULTI_SEATED_FLAG);
    }

    public String getIGNITION_TO_DISCOVERY(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.IGNITION_TO_DISCOVERY)).get(tIGNITION_TO_DISCOVERY);
    }

    public String getDISCOVERY_TO_CALL(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.DISCOVERY_TO_CALL)).get(tDISCOVERY_TO_CALL);
    }

    public String getHOW_DISCOVERED_DESCRIPTION(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.HOW_DISCOVERED_DESCRIPTION)).get(tHOW_DISCOVERED_DESCRIPTION);
    }

    public String getBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION)).get(tBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION);
    }

    public String getBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION)).get(tBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION);
    }

    public String getBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT)).get(tBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT);
    }

    public String getOCCUPIED_NORMAL(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.OCCUPIED_NORMAL)).get(tOCCUPIED_NORMAL);
    }

    public String getACCIDENTAL_OR_DELIBERATE(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.ACCIDENTAL_OR_DELIBERATE)).get(tACCIDENTAL_OR_DELIBERATE);
    }

    public String getVEHICLES(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.VEHICLES)).get(tVEHICLES);
    }

    public String getVEHICLES_CODE(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.VEHICLES_CODE)).get(tVEHICLES_CODE);
    }

    public String getPERSONNEL(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.PERSONNEL)).get(tPERSONNEL);
    }

    public String getPERSONNEL_CODE(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.PERSONNEL_CODE)).get(tPERSONNEL_CODE);
    }

    public String getSTARTING_DELAY_DESCRIPTION(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.STARTING_DELAY_DESCRIPTION)).get(tSTARTING_DELAY_DESCRIPTION);
    }

    public String getACTION_NON_FRS_DESCRIPTION(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.ACTION_NON_FRS_DESCRIPTION)).get(tACTION_NON_FRS_DESCRIPTION);
    }

    public String getACTION_FRS_DESCRIPTION(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.ACTION_FRS_DESCRIPTION)).get(tACTION_FRS_DESCRIPTION);
    }

    public String getCAUSE_OF_FIRE(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.CAUSE_OF_FIRE)).get(tCAUSE_OF_FIRE);
    }

    public String getIGNITION_POWER(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.IGNITION_POWER)).get(tIGNITION_POWER);
    }

    public String getSOURCE_OF_IGNITION(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.SOURCE_OF_IGNITION)).get(tSOURCE_OF_IGNITION);
    }

    public String getITEM_IGNITED(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.ITEM_IGNITED)).get(tITEM_IGNITED);
    }

    public String getITEM_CAUSING_SPREAD(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.ITEM_CAUSING_SPREAD)).get(tITEM_CAUSING_SPREAD);
    }

    public String getRAPID_FIRE_GROWTH(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.RAPID_FIRE_GROWTH)).get(tRAPID_FIRE_GROWTH);
    }

    public String getCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION)).get(tCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION);
    }

    public String getCAUSE_EXPLOSION_INVOLVED(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.CAUSE_EXPLOSION_INVOLVED)).get(tCAUSE_EXPLOSION_INVOLVED);
    }

    public String getCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION)).get(tCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION);
    }

    public String getCAUSE_EXPLOSION_STAGE_DESCRIPTION(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.CAUSE_EXPLOSION_STAGE_DESCRIPTION)).get(tCAUSE_EXPLOSION_STAGE_DESCRIPTION);
    }

    public String getCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION)).get(tCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION);
    }

    public String getBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION)).get(tBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION);
    }

    public String getBUILDING_FLOORS_ABOVE_GROUND(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.BUILDING_FLOORS_ABOVE_GROUND)).get(tBUILDING_FLOORS_ABOVE_GROUND);
    }

    public String getBUILDING_FLOORS_BELOW_GROUND(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.BUILDING_FLOORS_BELOW_GROUND)).get(tBUILDING_FLOORS_BELOW_GROUND);
    }

    public String getBUILDING_FLOOR_ORIGIN(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.BUILDING_FLOOR_ORIGIN)).get(tBUILDING_FLOOR_ORIGIN);
    }

    public String getBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION)).get(tBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION);
    }

    public String getBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION)).get(tBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION);
    }

    public String getFIRE_START_LOCATION(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.FIRE_START_LOCATION)).get(tFIRE_START_LOCATION);
    }

    public String getFIRE_SIZE_ON_ARRIVAL(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.FIRE_SIZE_ON_ARRIVAL)).get(tFIRE_SIZE_ON_ARRIVAL);
    }

    public String getOTHER_PROPERTY_AFFECTED_ON_ARRIVAL(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.OTHER_PROPERTY_AFFECTED_ON_ARRIVAL)).get(tOTHER_PROPERTY_AFFECTED_ON_ARRIVAL);
    }

    public String getBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION)).get(tBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION);
    }

    public String getBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION)).get(tBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION);
    }

    public String getFIRE_SIZE_ON_ARRIVAL_DESCRIPTION(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.FIRE_SIZE_ON_ARRIVAL_DESCRIPTION)).get(tFIRE_SIZE_ON_ARRIVAL_DESCRIPTION);
    }

    public String getother_property_affected_close_d(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.other_property_affected_close_d)).get(tother_property_affected_close_d);
    }

    public String getspread_of_fire_d(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.spread_of_fire_d)).get(tspread_of_fire_d);
    }

    public String getRESPONSE_TIME(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.RESPONSE_TIME)).get(tRESPONSE_TIME);
    }

    public String getRESPONSE_TIME_CODE(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.RESPONSE_TIME_CODE)).get(tRESPONSE_TIME_CODE);
    }

    public String getTIME_AT_SCENE(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.TIME_AT_SCENE)).get(tTIME_AT_SCENE);
    }

    public String getTIME_AT_SCENE_CODE(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.TIME_AT_SCENE_CODE)).get(tTIME_AT_SCENE_CODE);
    }

    public String getFATALITY_CASUALTY(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.FATALITY_CASUALTY)).get(tFATALITY_CASUALTY);
    }

    public String getRESCUES(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.RESCUES)).get(tRESCUES);
    }

    public String getEVACUATIONS(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.EVACUATIONS)).get(tEVACUATIONS);
    }

    public String getEVACUATIONS_CODE(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.EVACUATIONS_CODE)).get(tEVACUATIONS_CODE);
    }

    public String getBUILDING_EVACUATION_DELAY_DESCRIPTION(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.BUILDING_EVACUATION_DELAY_DESCRIPTION)).get(tBUILDING_EVACUATION_DELAY_DESCRIPTION);
    }

    public String getBUILDING_EVACUATION_TIME_DESCRIPTION(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.BUILDING_EVACUATION_TIME_DESCRIPTION)).get(tBUILDING_EVACUATION_TIME_DESCRIPTION);
    }

    public String getCSVHeader() {
        return getCSVHeader0() + getCSVHeader1()
                + "BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT,"
                + "OCCUPIED_NORMAL,"
                + getCSVHeader2();
    }

    public static String getCSVHeader0() {
        return "ID,FRS_NAME,E_CODE,FINANCIAL_YEAR,MONTH_NAME,"
                + "WEEKDAY_WEEKEND,DAY_NIGHT,BUILDING_OR_PROPERTY_TYPE,"
                + "LATE_CALL,MULTI_SEATED_FLAG,IGNITION_TO_DISCOVERY,"
                + "DISCOVERY_TO_CALL,HOW_DISCOVERED_DESCRIPTION,";
    }

    public static String getCSVHeader1() {
        return "BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION,"
                + "BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION,";
    }

    public static String getCSVHeader2() {
        return "ACCIDENTAL_OR_DELIBERATE,VEHICLES,VEHICLES_CODE,PERSONNEL,"
                + "PERSONNEL_CODE,STARTING_DELAY_DESCRIPTION,"
                + "ACTION_NON_FRS_DESCRIPTION,ACTION_FRS_DESCRIPTION,"
                + "CAUSE_OF_FIRE,IGNITION_POWER,SOURCE_OF_IGNITION,"
                + "ITEM_IGNITED,ITEM_CAUSING_SPREAD,RAPID_FIRE_GROWTH,"
                + "CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION,"
                + "CAUSE_EXPLOSION_INVOLVED,"
                + "CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION,"
                + "CAUSE_EXPLOSION_STAGE_DESCRIPTION,"
                + "CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION,"
                + "BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION,"
                + "BUILDING_FLOORS_ABOVE_GROUND,"
                + "BUILDING_FLOORS_BELOW_GROUND,"
                + "BUILDING_FLOOR_ORIGIN,"
                + "BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION,"
                + "BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION,"
                + "FIRE_START_LOCATION,"
                + "FIRE_SIZE_ON_ARRIVAL,"
                + "OTHER_PROPERTY_AFFECTED_ON_ARRIVAL,"
                + "BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION,"
                + "BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION,"
                + "FIRE_SIZE_ON_ARRIVAL_DESCRIPTION,"
                + "other_property_affected_close_d,"
                + "spread_of_fire_d,"
                + "RESPONSE_TIME,"
                + "RESPONSE_TIME_CODE,"
                + "TIME_AT_SCENE,"
                + "TIME_AT_SCENE_CODE,"
                + "FATALITY_CASUALTY,"
                + "RESCUES,"
                + "EVACUATIONS,"
                + "EVACUATIONS_CODE,"
                + "BUILDING_EVACUATION_DELAY_DESCRIPTION,"
                + "BUILDING_EVACUATION_TIME_DESCRIPTION,"
                + "FIRE_SAFETY_DANGER_SCORE,"
                + "FIRE_SAFETY_FAILURE_SCORE_MANAGEMENT,"
                + "FIRE_SAFETY_FAILURE_SCORE_CONSTRUCTION,"
                + "FIRE_SAFETY_FAILURE_SCORE_FIRE_FIGHTING,"
                + "COMBINED_FAILURE_SCORE,"
                + "FAILURE_SCORE_DELAYS_TO_FIRE_FIGHTING,"
                + "FAILURE_SCORE_CAUSE_OF_FIRE,"
                + "FAILURE_SCORE_FIRE_SPREAD,"
                + "FAILURE_SCORE_EVACUATION,"
                + "HoleyCheese";
    }

    public abstract String toString(F_Data data);

    public String toString0(F_Data data) {
        String r = "ID=" + getId();
        r += ", FRS_NAME=" + getFRS_NAME(data);
        r += ", E_CODE=" + getE_CODE(data);
        r += ", FINANCIAL_YEAR=" + getFINANCIAL_YEAR(data);
        r += ", MONTH_NAME=" + getMONTH_NAME(data);
        r += ", WEEKDAY_WEEKEND=" + getWEEKDAY_WEEKEND(data);
        r += ", DAY_NIGHT=" + getDAY_NIGHT(data);
        r += ", BUILDING_OR_PROPERTY_TYPE=" + getBUILDING_OR_PROPERTY_TYPE(data);
        r += ", LATE_CALL=" + getLATE_CALL(data);
        r += ", MULTI_SEATED_FLAG=" + getMULTI_SEATED_FLAG(data);
        r += ", IGNITION_TO_DISCOVERY=" + getIGNITION_TO_DISCOVERY(data);
        r += ", DISCOVERY_TO_CALL=" + getDISCOVERY_TO_CALL(data);
        r += ", HOW_DISCOVERED_DESCRIPTION=" + getHOW_DISCOVERED_DESCRIPTION(data);
        return r;
    }

    protected String toString1(F_Data data) {
        String r = ", BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION=" + getBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION(data);
        r += ", BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION=" + getBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION(data);
        return r;
    }

    public String toString2(F_Data data) {
        String r = ", ACCIDENTAL_OR_DELIBERATE=" + getACCIDENTAL_OR_DELIBERATE(data);
        r += ", VEHICLES=" + getVEHICLES(data);
        r += ", VEHICLES_CODE=" + getVEHICLES_CODE(data);
        r += ", PERSONNEL=" + getPERSONNEL(data);
        r += ", PERSONNEL_CODE=" + getPERSONNEL_CODE(data);
        r += ", STARTING_DELAY_DESCRIPTION=" + getSTARTING_DELAY_DESCRIPTION(data);
        r += ", ACTION_NON_FRS_DESCRIPTION=" + getACTION_NON_FRS_DESCRIPTION(data);
        r += ", ACTION_FRS_DESCRIPTION=" + getACTION_FRS_DESCRIPTION(data);
        r += ", CAUSE_OF_FIRE=" + getCAUSE_OF_FIRE(data);
        r += ", IGNITION_POWER=" + getIGNITION_POWER(data);
        r += ", SOURCE_OF_IGNITION=" + getSOURCE_OF_IGNITION(data);
        r += ", ITEM_IGNITED=" + getITEM_IGNITED(data);
        r += ", ITEM_CAUSING_SPREAD=" + getITEM_CAUSING_SPREAD(data);
        r += ", RAPID_FIRE_GROWTH=" + getRAPID_FIRE_GROWTH(data);
        r += ", CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION=" + getCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION(data);
        r += ", CAUSE_EXPLOSION_INVOLVED=" + getCAUSE_EXPLOSION_INVOLVED(data);
        r += ", CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION=" + getCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION(data);
        r += ", CAUSE_EXPLOSION_STAGE_DESCRIPTION=" + getCAUSE_EXPLOSION_STAGE_DESCRIPTION(data);
        r += ", CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION=" + getCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION(data);
        r += ", BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION=" + getBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION(data);
        r += ", BUILDING_FLOORS_ABOVE_GROUND=" + getBUILDING_FLOORS_ABOVE_GROUND(data);
        r += ", BUILDING_FLOORS_BELOW_GROUND=" + getBUILDING_FLOORS_BELOW_GROUND(data);
        r += ", BUILDING_FLOOR_ORIGIN=" + getBUILDING_FLOOR_ORIGIN(data);
        r += ", BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION=" + getBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION(data);
        r += ", BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION=" + getBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION(data);
        r += ", FIRE_START_LOCATION=" + getFIRE_START_LOCATION(data);
        r += ", FIRE_SIZE_ON_ARRIVAL=" + getFIRE_SIZE_ON_ARRIVAL(data);
        r += ", OTHER_PROPERTY_AFFECTED_ON_ARRIVAL=" + getOTHER_PROPERTY_AFFECTED_ON_ARRIVAL(data);
        r += ", BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION=" + getBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION(data);
        r += ", BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION=" + getBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION(data);
        r += ", FIRE_SIZE_ON_ARRIVAL_DESCRIPTION=" + getFIRE_SIZE_ON_ARRIVAL_DESCRIPTION(data);
        r += ", other_property_affected_close_d=" + getother_property_affected_close_d(data);
        r += ", spread_of_fire_d=" + getspread_of_fire_d(data);
        r += ", RESPONSE_TIME=" + getRESPONSE_TIME(data);
        r += ", RESPONSE_TIME_CODE=" + getRESPONSE_TIME_CODE(data);
        r += ", TIME_AT_SCENE=" + getTIME_AT_SCENE(data);
        r += ", TIME_AT_SCENE_CODE=" + getTIME_AT_SCENE_CODE(data);
        r += ", FATALITY_CASUALTY=" + getFATALITY_CASUALTY(data);
        r += ", RESCUES=" + getRESCUES(data);
        r += ", EVACUATIONS=" + getEVACUATIONS(data);
        r += ", EVACUATIONS_CODE=" + getEVACUATIONS_CODE(data);
        r += ", BUILDING_EVACUATION_DELAY_DESCRIPTION=" + getBUILDING_EVACUATION_DELAY_DESCRIPTION(data);
        r += ", BUILDING_EVACUATION_TIME_DESCRIPTION=" + getBUILDING_EVACUATION_TIME_DESCRIPTION(data);
        //r += ", fSFScore=" + fSFScore;
        //r += ", fSSScore=" + fSSScore;
        r += ", fDScore=" + fDScore;
        r += ", fSFScoreConstruction=" + fSFScoreConstruction;
        r += ", fSFScoreManagement=" + fSFScoreManagement;
        r += ", fSFScoreFireFighting=" + fSFScoreFireFighting;
        r += ", combinedScore=" + combinedScore;
        r += ", delaysToFireFightingScore=" + delaysToFireFightingScore;
        r += ", causeOfFireScore=" + causeOfFireScore;
        r += ", fireSpreadScore=" + fireSpreadScore;
        r += ", evacuationScore=" + evacuationScore;
        r += ", holeyCheese=" + holeyCheese;
        r += ", delaysToFireFightingAndNotFirespreadHoleyCheese=" + delaysToFireFightingAndNotFirespreadHoleyCheese;
        r += ", AType=" + AType;
        r += ", BType=" + BType;        
        return r;
    }

    public abstract String toCSVString(F_Data data);

    public String toCSVString0(F_Data data) {
        String r = "" + getId().id;
        r += ",\"" + getFRS_NAME(data);
        r += "\",\"" + getE_CODE(data);
        r += "\",\"" + getFINANCIAL_YEAR(data);
        r += "\",\"" + getMONTH_NAME(data);
        r += "\",\"" + getWEEKDAY_WEEKEND(data);
        r += "\",\"" + getDAY_NIGHT(data);
        r += "\",\"" + getBUILDING_OR_PROPERTY_TYPE(data);
        r += "\",\"" + getLATE_CALL(data);
        r += "\",\"" + getMULTI_SEATED_FLAG(data);
        r += "\",\"" + getIGNITION_TO_DISCOVERY(data);
        r += "\",\"" + getDISCOVERY_TO_CALL(data);
        r += "\",\"" + getHOW_DISCOVERED_DESCRIPTION(data);
        return r;
    }

    protected String toCSVString1(F_Data data) {
        String r = "\",\"" + getBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION(data);
        r += "\",\"" + getBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION(data);
        return r;
    }

    public String toCSVString2(F_Data data) {
        String r = "\",\"" + getACCIDENTAL_OR_DELIBERATE(data);
        r += "\",\"" + getVEHICLES(data);
        r += "\",\"" + getVEHICLES_CODE(data);
        r += "\",\"" + getPERSONNEL(data);
        r += "\",\"" + getPERSONNEL_CODE(data);
        r += "\",\"" + getSTARTING_DELAY_DESCRIPTION(data);
        r += "\",\"" + getACTION_NON_FRS_DESCRIPTION(data);
        r += "\",\"" + getACTION_FRS_DESCRIPTION(data);
        r += "\",\"" + getCAUSE_OF_FIRE(data);
        r += "\",\"" + getIGNITION_POWER(data);
        r += "\",\"" + getSOURCE_OF_IGNITION(data);
        r += "\",\"" + getITEM_IGNITED(data);
        r += "\",\"" + getITEM_CAUSING_SPREAD(data);
        r += "\",\"" + getRAPID_FIRE_GROWTH(data);
        r += "\",\"" + getCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION(data);
        r += "\",\"" + getCAUSE_EXPLOSION_INVOLVED(data);
        r += "\",\"" + getCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION(data);
        r += "\",\"" + getCAUSE_EXPLOSION_STAGE_DESCRIPTION(data);
        r += "\",\"" + getCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION(data);
        r += "\",\"" + getBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION(data);
        r += "\",\"" + getBUILDING_FLOORS_ABOVE_GROUND(data);
        r += "\",\"" + getBUILDING_FLOORS_BELOW_GROUND(data);
        r += "\",\"" + getBUILDING_FLOOR_ORIGIN(data);
        r += "\",\"" + getBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION(data);
        r += "\",\"" + getBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION(data);
        r += "\",\"" + getFIRE_START_LOCATION(data);
        r += "\",\"" + getFIRE_SIZE_ON_ARRIVAL(data);
        r += "\",\"" + getOTHER_PROPERTY_AFFECTED_ON_ARRIVAL(data);
        r += "\",\"" + getBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION(data);
        r += "\",\"" + getBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION(data);
        r += "\",\"" + getFIRE_SIZE_ON_ARRIVAL_DESCRIPTION(data);
        r += "\",\"" + getother_property_affected_close_d(data);
        r += "\",\"" + getspread_of_fire_d(data);
        r += "\",\"" + getRESPONSE_TIME(data);
        r += "\",\"" + getRESPONSE_TIME_CODE(data);
        r += "\",\"" + getTIME_AT_SCENE(data);
        r += "\",\"" + getTIME_AT_SCENE_CODE(data);
        r += "\",\"" + getFATALITY_CASUALTY(data);
        r += "\",\"" + getRESCUES(data);
        r += "\",\"" + getEVACUATIONS(data);
        r += "\",\"" + getEVACUATIONS_CODE(data);
        r += "\",\"" + getBUILDING_EVACUATION_DELAY_DESCRIPTION(data);
        r += "\",\"" + getBUILDING_EVACUATION_TIME_DESCRIPTION(data);
        //r += "\"," + fSFScore;
        //r += "," + fSSScore;
        r += "\"," + fDScore;
        r += "," + fSFScoreConstruction;
        r += "," + fSFScoreManagement;
        r += "," + fSFScoreFireFighting;
        r += "," + combinedScore;
        r += "," + delaysToFireFightingScore;
        r += "," + causeOfFireScore;
        r += "," + fireSpreadScore;
        r += "," + evacuationScore;
        r += "," + holeyCheese;
        r += "," + delaysToFireFightingAndNotFirespreadHoleyCheese;
        r += "," + AType;
        r += "," + BType;
        return r;
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
    public void initScores0(
            // MULTI_SEATED_FLAG
            int varMSF, int valMSF_Yes,
            // IGNITION_TO_DISCOVERY
            int varITD, Set<Integer> svalITD,
            // DISCOVERY_TO_CALL
            int varDTC, Set<Integer> svalDTC,
            // BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION
            int varBSSCD, int valBSSCD_SOCS, Set<Integer> svalBSSCD,
            // BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION
            int varBSSMOED, int valBSSMOED_SOCS, Set<Integer> svalBSSMOEDDanger,
            Set<Integer> svalBSSMOEDManagement, Set<Integer> svalBSSMOEDConstruction,
            // STARTING_DELAY_DESCRIPTION
            int varSDD, int valSDD_SOCS, Set<Integer> svalSDD, Set<Integer> svalSDD2,
            // CAUSE_OF_FIRE
            int varCOF, Set<Integer> svalCOF, Set<Integer> svalCOF2,
            // ITEM_CAUSING_SPREAD
            int varICS, Set<Integer> svalICS, Set<Integer> svalICS2,
            // RAPID_FIRE_GROWTH
            int varRFG, int valRFG,
            // BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION
            int varBSCD, Set<Integer> svalBSCD,
            // OTHER_PROPERTY_AFFECTED_ON_ARRIVAL
            int varOPAOA, int valOPAOA,
            // other_property_affected_close_d
            int varOPAC, int valOPAC,
            // FIRE_SIZE_ON_ARRIVAL
            int varFSOA, Set<Integer> svalFSOASuccess, Set<Integer> svalFSOAFailure,
            // spread_of_fire_d
            int varSOF, Set<Integer> svalSOFSuccess, Set<Integer> svalSOFFailure,
            // RESPONSE_TIME
            int varRT, Set<Integer> svalRTSuccess, Set<Integer> svalRTFailure,
            // FATALITY_CASUALTY
            int varFC, int valFC,
            // RESCUES
            int varR, int valR,
            // EVACUATIONS
            int varE, int valE,
            // BUILDING_EVACUATION_DELAY_DESCRIPTION
            int varBEDD, int valBEDD, Set<Integer> svalBEDDDanger,
            Set<Integer> svalBEDDFailureManagement,
            Set<Integer> svalBEDDFailureConstructionAndManagement) {
        fDScore = 0;
        //fSFScore = 0;
        //fSSScore = 0;
        fSFScoreConstruction = 0;
        fSFScoreManagement = 0;
        fSFScoreFireFighting = 0;
        combinedScore = 0;
        delaysToFireFightingScore = 0;
        causeOfFireScore = 0;
        fireSpreadScore = 0;
        evacuationScore = 0;
        holeyCheese = 0;
        delaysToFireFightingAndNotFirespreadHoleyCheese = 0;
        firespreadAndNotDelaysToFireFightingHoleyCheese = 0;
        // MULTI_SEATED_FLAG
        if (tMULTI_SEATED_FLAG == valMSF_Yes) {
            fDScore++;
        }
        // IGNITION_TO_DISCOVERY
        if (svalITD.contains(tIGNITION_TO_DISCOVERY)) {
            fDScore++;
            delaysToFireFightingScore++;
            combinedScore++;
        }
        // DISCOVERY_TO_CALL
        if (svalDTC.contains(tDISCOVERY_TO_CALL)) {
            fSFScoreManagement++;
            delaysToFireFightingScore++;
            combinedScore++;
        }
//        // BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION
//        if (tBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION == valBSSCD_SOCS) {
//            fSSScoreConstruction++;
//        }
        if (svalBSSCD.contains(tBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION)) {
            fSFScoreConstruction++;
            fireSpreadScore++;
            combinedScore++;
        }
//        // BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION
//        if (tBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION == valBSSMOED_SOCS) {
//            fSSScoreManagement++;
//        }
        if (svalBSSMOEDDanger.contains(tBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION)) {
            fDScore++;
            evacuationScore++;
            combinedScore++;
        }
        if (svalBSSMOEDManagement.contains(tBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION)) {
            fSFScoreManagement++;
        }
        if (svalBSSMOEDConstruction.contains(tBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION)) {
            fSFScoreConstruction++;
        }
//        // STARTING_DELAY_DESCRIPTION
//        if (tSTARTING_DELAY_DESCRIPTION == valSDD_SOCS) {
//            fSSScoreFireFighting++;
//        }
        if (svalSDD.contains(tSTARTING_DELAY_DESCRIPTION)) {
            fSFScoreFireFighting++;
            delaysToFireFightingScore++;
            combinedScore++;
        }
        if (svalSDD2.contains(tSTARTING_DELAY_DESCRIPTION)) {
            fDScore++;
            delaysToFireFightingScore++;
            combinedScore++;
        }
        // CAUSE_OF_FIRE
        if (svalCOF.contains(tCAUSE_OF_FIRE)) {
            fSFScoreManagement++;
            causeOfFireScore++;
            combinedScore++;
        }
        if (svalCOF2.contains(tCAUSE_OF_FIRE)) {
            causeOfFireScore++;
            combinedScore++;
        }
        // ITEM_CAUSING_SPREAD
        if (svalICS.contains(tITEM_CAUSING_SPREAD)) {
            fSFScoreManagement++;
            fireSpreadScore++;
            combinedScore++;
        }
        if (svalICS2.contains(tITEM_CAUSING_SPREAD)) {
            fSFScoreConstruction++;
            fireSpreadScore++;
            combinedScore++;
        }
        // RAPID_FIRE_GROWTH
        if (tRAPID_FIRE_GROWTH == valRFG) {
            fDScore++;
            fireSpreadScore++;
            combinedScore++;
        }
        // BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION
        if (svalBSCD.contains(tBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION)) {
            fSFScoreConstruction++;
            fireSpreadScore++;
            combinedScore++;
        }
        // OTHER_PROPERTY_AFFECTED_ON_ARRIVAL
        if (tOTHER_PROPERTY_AFFECTED_ON_ARRIVAL == valOPAOA) {
            fDScore++;
            fireSpreadScore++;
            combinedScore++;
        }
        // other_property_affected_close_d
        if (tother_property_affected_close_d == valOPAC) {
            fDScore++;
            fireSpreadScore++;
            combinedScore++;
        }
        // FIRE_SIZE_ON_ARRIVAL
        if (!svalFSOASuccess.contains(tFIRE_SIZE_ON_ARRIVAL)) {
            fDScore++;
        }
        if (svalFSOAFailure.contains(tFIRE_SIZE_ON_ARRIVAL)) {
            fireSpreadScore++;
            combinedScore++;
        }
        // spread_of_fire_d
        if (!svalSOFSuccess.contains(tspread_of_fire_d)) {
            fDScore++;
        }
        if (svalSOFSuccess.contains(tFIRE_SIZE_ON_ARRIVAL)) {
            fireSpreadScore++;
            combinedScore++;
        }
//        // RESPONSE_TIME
//        if (svalRTSuccess.contains(tRESPONSE_TIME)) {
//            fSSScoreFireFighting++;
//        }
        if (svalRTFailure.contains(tRESPONSE_TIME)) {
            fSFScoreFireFighting++;
            delaysToFireFightingScore++;
            combinedScore++;
        }
//        // FATALITY_CASUALTY
//        if (tFATALITY_CASUALTY == valFC) {
//            fSFScore++;
//        } else {
//            fSSScore++;
//        }
//        // RESCUES
//        if (tRESCUES != valR) {
//            fDScore++;
//        }
//        // EVACUATIONS
//        if (tEVACUATIONS != valE) {
//            fDScore++;
//        }
//        // BUILDING_EVACUATION_DELAY_DESCRIPTION
//        if (tBUILDING_EVACUATION_DELAY_DESCRIPTION == valBEDD) {
//            fSSScore++;
//        }
        if (svalBEDDDanger.contains(tBUILDING_EVACUATION_DELAY_DESCRIPTION)) {
            fDScore++;
            evacuationScore++;
            combinedScore++;
        }
        if (svalBEDDFailureManagement.contains(tBUILDING_EVACUATION_DELAY_DESCRIPTION)) {
            fSFScoreManagement++;
            evacuationScore++;
            combinedScore++;
        }
        if (svalBEDDFailureConstructionAndManagement.contains(tBUILDING_EVACUATION_DELAY_DESCRIPTION)) {
            fSFScoreManagement++;
            fSFScoreConstruction++;
            evacuationScore++;
            combinedScore++;
        }
        if (delaysToFireFightingScore > 0) {
            holeyCheese++;
            if (fireSpreadScore == 0) {
                delaysToFireFightingAndNotFirespreadHoleyCheese++;
            }
        } else {
            if (fireSpreadScore > 0) {
                firespreadAndNotDelaysToFireFightingHoleyCheese++;
            }
        }
        if (causeOfFireScore > 0) {
            holeyCheese++;
        }
        if (fireSpreadScore > 0) {
            holeyCheese++;
        }
        if (evacuationScore > 0) {
            holeyCheese++;
        }
    }
}
