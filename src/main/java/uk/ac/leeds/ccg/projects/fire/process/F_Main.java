/*
 * Copyright 2018 Andy Turner, CCG, University of Leeds.
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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import uk.ac.leeds.ccg.data.core.Data_Environment;
import uk.ac.leeds.ccg.data.format.Data_ReadCSV;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.projects.fire.core.F_Environment;
import uk.ac.leeds.ccg.projects.fire.io.F_Files;
import uk.ac.leeds.ccg.projects.fire.core.F_Object;
import uk.ac.leeds.ccg.projects.fire.core.F_Strings;
import uk.ac.leeds.ccg.projects.fire.data.F_Collection;
import uk.ac.leeds.ccg.projects.fire.data.F_Data;
import uk.ac.leeds.ccg.projects.fire.data.dwellings.F_Dwellings_Record0;
import uk.ac.leeds.ccg.projects.fire.data.dwellings.F_Dwellings_Record1;
import uk.ac.leeds.ccg.projects.fire.data.dwellings.F_Dwellings_Record2;
import uk.ac.leeds.ccg.generic.io.Generic_Defaults;
import uk.ac.leeds.ccg.generic.io.Generic_IO;
import uk.ac.leeds.ccg.generic.util.Generic_Collections;
import uk.ac.leeds.ccg.projects.fire.id.F_CollectionID;
import uk.ac.leeds.ccg.projects.fire.id.F_RecordID;

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

    // For convenience
    protected F_Data data;
    protected final F_Files files;

    public F_Main(F_Environment env) {
        super(env);
        data = env.data;
        files = env.files;
    }

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

    public void run() throws ClassNotFoundException {
        try {
            //boolean doLoadData = true;
            boolean doLoadData = false;
            if (doLoadData) {
                env.data = new F_Data(env);
                data = env.data;
                loadData();
            } else {
                env.data.env = env;
                process2();
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    /**
     * Loads source csv data files into collections. Each collections is a
     * collection for a day.
     *
     * @throws IOException
     */
    public void loadData() throws IOException, Exception {
        String m = "loadData";
        env.logStartTag(m);
        Path indir = files.getInputDir();
        Path gendir = files.getGeneratedDir();
        Path outdir = Paths.get(gendir.toString(), F_Strings.s_Subsets);
        Files.createDirectories(outdir);
        String type = ".xlsx"; // "csv";
        // Load "other building - residential to send.xlsx"        
        Path f = Paths.get(indir.toString(), "other building - residential to send" + type);
        F_CollectionID cID = new F_CollectionID(data.cID2recIDs.size());
        HashSet<F_RecordID> recIDs = new HashSet<>();
        data.cID2recIDs.put(cID, recIDs);
        F_Collection c = new F_Collection(cID);
        data.data.put(cID, c);
        env.log("Reading " + f.toString());
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(new FileInputStream(f.toFile()));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(F_XLSXParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | EncryptedDocumentException ex) {
            Logger.getLogger(F_XLSXParser.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                            int r = row.getRowNum();
                            //s = "\tRow " + r;
                            //System.out.println(s);
                            //pw.println(s);
                            for (Cell cell : row) {
                                line[cell.getColumnIndex()] = df.formatCellValue(cell);
                            }
                            try {
                                F_RecordID recID = new F_RecordID(Long.valueOf(line[0]));
                                F_Dwellings_Record2 rec = new F_Dwellings_Record2(recID, line);
                                recIDs.add(recID);
                                c.data.put(recID, rec);
                                Generic_Collections.addToMap(data.cID2recIDs, cID, recID);
                                data.recID2cID.put(recID, cID);
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
        data.cacheCollection(cID, c);
        //data.clearCollection(cID);

        // Load other  dwellings data
        Path[] vfs = new Path[6];
        vfs[0] = Paths.get(indir.toString(), "dwellings data to send 10-11" + type);
        vfs[1] = Paths.get(indir.toString(), "dwellings data to send 11-12" + type);
        vfs[2] = Paths.get(indir.toString(), "dwellings data to send 12-14" + type);
        vfs[3] = Paths.get(indir.toString(), "dwellings data to send 14-16" + type);
        vfs[4] = Paths.get(indir.toString(), "dwellings data to send 16-18" + type);
        vfs[5] = Paths.get(indir.toString(), "dwellings data to send 18-20" + type);

        Path dataDirPath = Paths.get("C:", "Users", "agdtu", "work", "research",
                "fire", "data");
        String dirName = "MA065AZX5";
        Path inDataDirPath = Paths.get(dataDirPath.toString(), "source", dirName);
        int syntax = 1;
        for (Path vf : vfs) {
            cID = new F_CollectionID(data.cID2recIDs.size());
            recIDs = new HashSet<>();
            data.cID2recIDs.put(cID, recIDs);
            c = new F_Collection(cID);
            data.data.put(cID, c);
            env.log("Reading " + vf.toString());
            wb = null;
            try {
                wb = WorkbookFactory.create(new FileInputStream(vf.toFile()));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(F_XLSXParser.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | EncryptedDocumentException ex) {
                Logger.getLogger(F_XLSXParser.class.getName()).log(Level.SEVERE, null, ex);
            }
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
                                int r = row.getRowNum();
                                //s = "\tRow " + r;
                                //System.out.println(s);
                                //pw.println(s);
                                for (Cell cell : row) {
                                    line[cell.getColumnIndex()] = df.formatCellValue(cell);
                                }
                                try {
                                    F_RecordID recID = new F_RecordID(Long.valueOf(line[0]));
                                    F_Dwellings_Record1 rec = new F_Dwellings_Record1(recID, line);
                                    recIDs.add(recID);
                                    c.data.put(recID, rec);
                                    Generic_Collections.addToMap(data.cID2recIDs, cID, recID);
                                    data.recID2cID.put(recID, cID);
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
            data.cacheCollection(cID, c);
            //data.clearCollection(cID);
        }
        env.logEndTag(m);
        env.env.closeLog(env.logID);
        env.swapData();
    }

    /**
     * Summarise the data.
     * <ul>
     * <li>number of fires by type of building over the period of the data</li>
     * <li>number of fires by type of building where fire spreads beyond 2
     * floors over period of data</li>
     * <li>number of fires by type of building by starting height and ending
     * height of fire over period of data</li>
     * <li>number of fires by type of building with compartmentation missing
     * over period of data</li>
     * <li>each of the above with injuries / fatalities</li>
     * </ul>
     *
     * FSO_APPLY SAFETY_SYSTEM
     *
     * OCCUPANCY_TYPE
     * WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT,ALARM_SYSTEM,ALARM_SYSTEM_TYPE,ALARM_REASON_FOR_POOR_OUTCOME,
     *
     * ID
     * number,FRS_NAME,E_CODE,FINANCIAL_YEAR,MONTH_NAME,WEEKDAY_WEEKEND,DAY_NIGHT,property_type_detailed_d,LATE_CALL,MULTI_SEATED_FLAG,IGNITION_TO_DISCOVERY,DISCOVERY_TO_CALL,HOW_DISCOVERED_DESCRIPTION,BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION,BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION,BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT,OCCUPANCY_TYPE,OCCUPIED_NORMAL,WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT,ALARM_SYSTEM,ALARM_SYSTEM_TYPE,ALARM_REASON_FOR_POOR_OUTCOME,ACCIDENTAL_OR_DELIBERATE,VEHICLES,VEHICLES_CODE,PERSONNEL,PERSONNEL_CODE,STARTING_DELAY_DESCRIPTION,ACTION_NON_FRS_DESCRIPTION,ACTION_FRS_DESCRIPTION,CAUSE_OF_FIRE,IGNITION_POWER,SOURCE_OF_IGNITION,ITEM_IGNITED,ITEM_CAUSING_SPREAD,RAPID_FIRE_GROWTH,CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION,CAUSE_EXPLOSION_INVOLVED,CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION,CAUSE_EXPLOSION_STAGE_DESCRIPTION,CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION,BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION,BUILDING_FLOORS_ABOVE_GROUND,BUILDING_FLOORS_BELOW_GROUND,BUILDING_FLOOR_ORIGIN,BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION,BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION,FIRE_START_LOCATION,FIRE_SIZE_ON_ARRIVAL,OTHER_PROPERTY_AFFECTED_ON_ARRIVAL,BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION,BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION,FIRE_SIZE_ON_ARRIVAL_DESCRIPTION,other_property_affected_close_d,spread_of_fire_d,RESPONSE_TIME,RESPONSE_TIME_CODE,TIME_AT_SCENE,TIME_AT_SCENE_CODE,FATALITY_CASUALTY,RESCUES,EVACUATIONS,EVACUATIONS_CODE,BUILDING_EVACUATION_DELAY_DESCRIPTION,BUILDING_EVACUATION_TIME_DESCRIPTION
     * ID number FRS_NAME E_CODE FINANCIAL_YEAR MONTH_NAME WEEKDAY_WEEKEND
     * DAY_NIGHT BUILDING_TYPE LATE_CALL MULTI_SEATED_FLAG IGNITION_TO_DISCOVERY
     * DISCOVERY_TO_CALL HOW_DISCOVERED_DESCRIPTION
     * BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION
     * BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION
     * BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT OCCUPIED_NORMAL
     * ACCIDENTAL_OR_DELIBERATE VEHICLES VEHICLES_CODE PERSONNEL PERSONNEL_CODE
     * STARTING_DELAY_DESCRIPTION ACTION_NON_FRS_DESCRIPTION
     * ACTION_FRS_DESCRIPTION CAUSE_OF_FIRE IGNITION_POWER SOURCE_OF_IGNITION
     * ITEM_IGNITED ITEM_CAUSING_SPREAD RAPID_FIRE_GROWTH
     * CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION CAUSE_EXPLOSION_INVOLVED
     * CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION CAUSE_EXPLOSION_STAGE_DESCRIPTION
     * CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION
     * BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION BUILDING_FLOORS_ABOVE_GROUND
     * BUILDING_FLOORS_BELOW_GROUND BUILDING_FLOOR_ORIGIN
     * BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION
     * BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION FIRE_START_LOCATION
     * FIRE_SIZE_ON_ARRIVAL OTHER_PROPERTY_AFFECTED_ON_ARRIVAL
     * BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION
     * BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION FIRE_SIZE_ON_ARRIVAL_DESCRIPTION
     * other_property_affected_close_d spread_of_fire_d RESPONSE_TIME
     * RESPONSE_TIME_CODE TIME_AT_SCENE TIME_AT_SCENE_CODE FATALITY_CASUALTY
     * RESCUES EVACUATIONS EVACUATIONS_CODE
     * BUILDING_EVACUATION_DELAY_DESCRIPTION BUILDING_EVACUATION_TIME_DESCRIPTIO
     * ID number FRS_NAME E_CODE FINANCIAL_YEAR MONTH_NAME WEEKDAY_WEEKEND
     * DAY_NIGHT property_type_detailed_d LATE_CALL MULTI_SEATED_FLAG
     * IGNITION_TO_DISCOVERY DISCOVERY_TO_CALL HOW_DISCOVERED_DESCRIPTION
     * BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION
     * BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION
     * BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT OCCUPIED_NORMAL
     * ACCIDENTAL_OR_DELIBERATE VEHICLES VEHICLES_CODE PERSONNEL PERSONNEL_CODE
     * STARTING_DELAY_DESCRIPTION ACTION_NON_FRS_DESCRIPTION
     * ACTION_FRS_DESCRIPTION CAUSE_OF_FIRE IGNITION_POWER SOURCE_OF_IGNITION
     * ITEM_IGNITED ITEM_CAUSING_SPREAD RAPID_FIRE_GROWTH
     * CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION CAUSE_EXPLOSION_INVOLVED
     * CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION CAUSE_EXPLOSION_STAGE_DESCRIPTION
     * CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION
     * BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION BUILDING_FLOORS_ABOVE_GROUND
     * BUILDING_FLOORS_BELOW_GROUND BUILDING_FLOOR_ORIGIN
     * BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION
     * BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION FIRE_START_LOCATION
     * FIRE_SIZE_ON_ARRIVAL OTHER_PROPERTY_AFFECTED_ON_ARRIVAL
     * BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION
     * BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION FIRE_SIZE_ON_ARRIVAL_DESCRIPTION
     * other_property_affected_close_d spread_of_fire_d RESPONSE_TIME
     * RESPONSE_TIME_CODE TIME_AT_SCENE TIME_AT_SCENE_CODE FATALITY_CASUALTY
     * RESCUES EVACUATIONS EVACUATIONS_CODE
     * BUILDING_EVACUATION_DELAY_DESCRIPTION
     * BUILDING_EVACUATION_TIME_DESCRIPTION ID number FRS_NAME E_CODE
     * FINANCIAL_YEAR MONTH_NAME WEEKDAY_WEEKEND DAY_NIGHT BUILDING_TYPE
     * LATE_CALL MULTI_SEATED_FLAG IGNITION_TO_DISCOVERY DISCOVERY_TO_CALL
     * HOW_DISCOVERED_DESCRIPTION FSO_APPLY
     * BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION
     * BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION SAFETY_SYSTEM
     * BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT OCCUPIED_NORMAL
     * ACCIDENTAL_OR_DELIBERATE VEHICLES VEHICLES_CODE PERSONNEL PERSONNEL_CODE
     * STARTING_DELAY_DESCRIPTION ACTION_NON_FRS_DESCRIPTION
     * ACTION_FRS_DESCRIPTION CAUSE_OF_FIRE IGNITION_POWER SOURCE_OF_IGNITION
     * ITEM_IGNITED ITEM_CAUSING_SPREAD RAPID_FIRE_GROWTH
     * CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION CAUSE_EXPLOSION_INVOLVED
     * CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION CAUSE_EXPLOSION_STAGE_DESCRIPTION
     * CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION
     * BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION BUILDING_FLOORS_ABOVE_GROUND
     * BUILDING_FLOORS_BELOW_GROUND BUILDING_FLOOR_ORIGIN
     * BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION
     * BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION FIRE_START_LOCATION
     * FIRE_SIZE_ON_ARRIVAL OTHER_PROPERTY_AFFECTED_ON_ARRIVAL
     * BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION
     * BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION FIRE_SIZE_ON_ARRIVAL_DESCRIPTION
     * other_property_affected_close_d spread_of_fire_d RESPONSE_TIME
     * RESPONSE_TIME_CODE TIME_AT_SCENE TIME_AT_SCENE_CODE FATALITY_CASUALTY
     * RESCUES EVACUATIONS EVACUATIONS_CODE
     * BUILDING_EVACUATION_DELAY_DESCRIPTION
     * BUILDING_EVACUATION_TIME_DESCRIPTION
     */
    public void process2() {
        try {
            env.loadData();
            HashMap<String, Integer> sFRS_NAME = new HashMap<>();
            HashMap<String, Integer> sE_CODE = new HashMap<>();
            HashMap<String, Integer> mBUILDING_OR_PROPERTY_TYPE = new HashMap<>();
            HashMap<String, Integer> sBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION = new HashMap<>();
            HashMap<String, Integer> sACCIDENTAL_OR_DELIBERATE = new HashMap<>();
            HashMap<String, Integer> sFIRE_START_LOCATION = new HashMap<>();
            HashMap<String, Integer> sspread_of_fire_d = new HashMap<>();
            HashMap<String, Integer> sFATALITY_CASUALTY = new HashMap<>();

            HashMap<String, Integer> sFATALITY_CASUALTYNoCompartmentationInBuilding = new HashMap<>();

            HashMap<String, Integer> sFATALITY_CASUALTYproperty_type_detailed_d = new HashMap<>();
            HashMap<String, Integer> sFATALITY_CASUALTYBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION = new HashMap<>();

            HashMap<String, Integer> sAffectingMoreThan2FloorsByType = new HashMap<>();

            HashMap<String, Integer> sFATALITY_CASUALTYAffectingMoreThan2FloorsByType = new HashMap<>();

            /*
             * ID number,FRS_NAME,E_CODE,FINANCIAL_YEAR,MONTH_NAME,
             * WEEKDAY_WEEKEND,DAY_NIGHT,
             * property_type_detailed_d,
             * LATE_CALL,MULTI_SEATED_FLAG,IGNITION_TO_DISCOVERY,
             * DISCOVERY_TO_CALL,
             * HOW_DISCOVERED_DESCRIPTION,
             * BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION,
             * BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION,
             * BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT,
             * OCCUPANCY_TYPE,
             * OCCUPIED_NORMAL,
             * WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT,
             * ALARM_SYSTEM,ALARM_SYSTEM_TYPE,ALARM_REASON_FOR_POOR_OUTCOME,
             * ACCIDENTAL_OR_DELIBERATE,VEHICLES,VEHICLES_CODE,PERSONNEL,
             * PERSONNEL_CODE,
             * STARTING_DELAY_DESCRIPTION,
             * ACTION_NON_FRS_DESCRIPTION,
             * ACTION_FRS_DESCRIPTION,
             * CAUSE_OF_FIRE,IGNITION_POWER,
             * SOURCE_OF_IGNITION,
             * ITEM_IGNITED,ITEM_CAUSING_SPREAD,
             * RAPID_FIRE_GROWTH,
             * CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION,
             * CAUSE_EXPLOSION_INVOLVED,
             * CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION,
             * CAUSE_EXPLOSION_STAGE_DESCRIPTION,
             * CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION,
             * BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION,
             * BUILDING_FLOORS_ABOVE_GROUND,
             * BUILDING_FLOORS_BELOW_GROUND,
             * BUILDING_FLOOR_ORIGIN,
             * BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION,
             * BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION,
             * FIRE_START_LOCATION,
             * FIRE_SIZE_ON_ARRIVAL,
             * OTHER_PROPERTY_AFFECTED_ON_ARRIVAL,
             * BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION,
             * BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION,
             * FIRE_SIZE_ON_ARRIVAL_DESCRIPTION,
             * other_property_affected_close_d,
             * spread_of_fire_d,
             * RESPONSE_TIME,
             * RESPONSE_TIME_CODE,TIME_AT_SCENE,
             * TIME_AT_SCENE_CODE,FATALITY_CASUALTY,
             * RESCUES,EVACUATIONS,EVACUATIONS_CODE,
             * BUILDING_EVACUATION_DELAY_DESCRIPTION,
             * BUILDING_EVACUATION_TIME_DESCRIPTION
             */
            String NoCompartmentationInBuilding = "No compartmentation in building";

            /**
             * 36124,Suffolk,E31000034,2010/11,May,Weekend,Night, Dwelling -
             * Multiple occupancy,no,no, Over 30 minutes and up to 2
             * hours,Immediately, Automatic alarm system,No compartmentation in
             * building,OK - no visible concerns,yes,"3 or more adults under
             * pensionable age, no child/ren",Occupied,no,Alarm Present,Mains
             * powered,Other/ Unspecified,Recorded as Accidental,1,1,6,4,No
             * delay,None,Removal from/of heat source,Cooking - other
             * cooking,Electric,Cooking
             * appliances,Food,Food,None,None,no,NULL,NULL,NULL,None,0,0,0,None,None,Kitchen,No
             * fire damage,No other property was affected,Up to 5,Up to 5,Not
             * applicable,No other property was affected,No fire damage,10 to 15
             * mins,10,15 to 30 mins,2,None,0,Up to 5,1,"Evacuation, but no
             * delay",Immediately
             */
            System.out.println("env.data.cID2recIDs.size()=" + env.data.cID2recIDs.size());
            System.out.println("env.data.recID2cID.size()=" + env.data.recID2cID.size());
            System.out.println("env.data.data.size()=" + env.data.data.size());
            Iterator<F_CollectionID> ite = env.data.data.keySet().iterator();
            while (ite.hasNext()) {
                F_CollectionID cid = ite.next();
                System.out.println("cid=" + cid.toString());
                env.data.env = env;
                F_Collection c = env.data.getCollection(cid);
                Iterator<F_RecordID> ite2 = c.data.keySet().iterator();
                while (ite2.hasNext()) {
                    F_RecordID id = ite2.next();
                    F_Dwellings_Record0 r = c.data.get(id);
                    Generic_Collections.addToCount(sFRS_NAME, r.gettFRS_NAME(), 1);
                    Generic_Collections.addToCount(sE_CODE, r.gettE_CODE(), 1);
                    String BUILDING_OR_PROPERTY_TYPE = r.gettBUILDING_OR_PROPERTY_TYPE();
                    Generic_Collections.addToCount(mBUILDING_OR_PROPERTY_TYPE, BUILDING_OR_PROPERTY_TYPE, 1);
                    String BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION = r.gettBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION();
                    Generic_Collections.addToCount(sBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION, BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION, 1);
                    Generic_Collections.addToCount(sACCIDENTAL_OR_DELIBERATE, r.gettACCIDENTAL_OR_DELIBERATE(), 1);
                    Generic_Collections.addToCount(sFIRE_START_LOCATION, r.gettFIRE_START_LOCATION(), 1);
                    String spread_of_fire_d = r.gettspread_of_fire_d();
                    Generic_Collections.addToCount(sspread_of_fire_d, spread_of_fire_d, 1);
                    String FATALITY_CASUALTY = r.gettFATALITY_CASUALTY();
                    Generic_Collections.addToCount(sFATALITY_CASUALTY, r.gettFATALITY_CASUALTY(), 1);

                    if (FATALITY_CASUALTY.equalsIgnoreCase("Fatality/Casualty")) {
                        Generic_Collections.addToCount(sFATALITY_CASUALTYproperty_type_detailed_d, BUILDING_OR_PROPERTY_TYPE, 1);
                        Generic_Collections.addToCount(sFATALITY_CASUALTYBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION, BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION, 1);
                        if (BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION.equalsIgnoreCase(NoCompartmentationInBuilding)) {
                            Generic_Collections.addToCount(sFATALITY_CASUALTYNoCompartmentationInBuilding, BUILDING_OR_PROPERTY_TYPE, 1);
                        }

                    }

                    if (spread_of_fire_d.equalsIgnoreCase("Whole Building/ Affecting more than 2 floors")) {
                        Generic_Collections.addToCount(sAffectingMoreThan2FloorsByType, BUILDING_OR_PROPERTY_TYPE, 1);
                        if (FATALITY_CASUALTY.equalsIgnoreCase("Fatality/Casualty")) {
                            Generic_Collections.addToCount(sFATALITY_CASUALTYAffectingMoreThan2FloorsByType, BUILDING_OR_PROPERTY_TYPE, 1);
                        }
                    }
                }
            }
            print("FRS_NAME", sFRS_NAME);
            print("E_CODE", sE_CODE);
            
            print(F_Strings.BUILDING_OR_PROPERTY_TYPE, mBUILDING_OR_PROPERTY_TYPE);
            createLookups(mBUILDING_OR_PROPERTY_TYPE.keySet(), F_Strings.BUILDING_OR_PROPERTY_TYPE);
            
            print("BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION", sBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION);
            print("ACCIDENTAL_OR_DELIBERATE", sACCIDENTAL_OR_DELIBERATE);
            print("FIRE_START_LOCATION", sFIRE_START_LOCATION);
            print("spread_of_fire_d", sspread_of_fire_d);
            print("FATALITY_CASUALTY", sFATALITY_CASUALTY);

            print("FATALITY_CASUALTY IN property_type_detailed_d", sFATALITY_CASUALTYproperty_type_detailed_d);
            print("FATALITY_CASUALTY IN BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION", sFATALITY_CASUALTYBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION);

            print("FATALITY_CASUALTY IN " + NoCompartmentationInBuilding, sFATALITY_CASUALTYNoCompartmentationInBuilding);

            print("Whole Building/ Affecting more than 2 floors BY property_type_detailed_d", sAffectingMoreThan2FloorsByType);
            print("FATALITY_CASUALTY IN Whole Building/ Affecting more than 2 floors BY property_type_detailed_d", sFATALITY_CASUALTYAffectingMoreThan2FloorsByType);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(F_Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void print(String name, HashSet<String> s) {
        s.stream().forEach(i -> System.out.println(i));
    }

    public void print(String name, HashMap<String, Integer> m) {
        System.out.println(name + ".size()=" + m.size());
        System.out.println("count,value");
        m.keySet().stream().forEach(i -> System.out.println("" + m.get(i) + "," + i));
    }

    /**
     * This creates some look ups or indexes for the keys of m.
     * @param s The map that's keys are saved in look ups. 
     * @param name The name given to the lookups.
     */
    public void createLookups(Set<String> s, String name) {
        HashMap<String, Integer> name2id = new HashMap<>();
        HashMap<Integer, String> id2name = new HashMap<>();
        Iterator<String> ite = s.iterator();
        while (ite.hasNext()) {
            String k = ite.next();
            int id = name2id.size();
            name2id.put(k, id);
            id2name.put(id, k);
        }
        try {
            String p0 = env.files.getGeneratedDir().getPath().toString();
            Path p;
            p = Paths.get(p0, name + "2ID");
            Generic_IO.writeObject(name2id, p);
            p = Paths.get(p0, "ID2" + name);
            Generic_IO.writeObject(id2name, p);
        } catch (IOException ex) {
            Logger.getLogger(F_Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
