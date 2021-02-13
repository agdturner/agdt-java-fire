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
            HashMap<String, Integer> m = new HashMap<>();
            HashMap<String, Integer> mIGNITION_TO_DISCOVERY = new HashMap<>();
            HashMap<String, Integer> mDISCOVERY_TO_CALL = new HashMap<>();
            HashMap<String, Integer> mHOW_DISCOVERED_DESCRIPTION = new HashMap<>();
            HashMap<String, Integer> mBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION = new HashMap<>();
            HashMap<String, Integer> mBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION = new HashMap<>();
            HashMap<String, Integer> mBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT = new HashMap<>();
            //HashMap<String, Integer> mOCCUPANCY_TYPE = new HashMap<>();
            HashMap<String, Integer> mOCCUPIED_NORMAL = new HashMap<>();
            HashMap<String, Integer> mWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT = new HashMap<>();
            HashMap<String, Integer> mALARM_SYSTEM = new HashMap<>();
            HashMap<String, Integer> mALARM_SYSTEM_TYPE = new HashMap<>();
            HashMap<String, Integer> mALARM_REASON_FOR_POOR_OUTCOME = new HashMap<>();
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
            // 2 Variables
            HashMap<String, Integer> mFATALITY_CASUALTY__NoCompartmentationInBuilding = new HashMap<>();

            HashMap<String, Integer> mFATALITY_CASUALTY__BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
            HashMap<String, Integer> mFATALITY_CASUALTY__BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION = new HashMap<>();

            HashMap<String, Integer> mAffectingMoreThan2FloorsBy__BUILDING_OR_PROPERTY_TYPE = new HashMap<>();

            HashMap<String, Integer> mFATALITY_CASUALTY__AffectingMoreThan2FloorsByType = new HashMap<>();
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
                    //Generic_Collections.addToCount(mOCCUPANCY_TYPE, r.gettOCCUPANCY_TYPE(), 1);
                    Generic_Collections.addToCount(mOCCUPIED_NORMAL, r.gettOCCUPIED_NORMAL(), 1);
                    //Generic_Collections.addToCount(mWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT, r.gettWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT(), 1);
                    //Generic_Collections.addToCount(mALARM_SYSTEM, r.gettALARM_SYSTEM(), 1);
                    //Generic_Collections.addToCount(mALARM_SYSTEM_TYPE, r.gettALARM_SYSTEM_TYPE(), 1);
                    //Generic_Collections.addToCount(mALARM_REASON_FOR_POOR_OUTCOME, r.gettALARM_REASON_FOR_POOR_OUTCOME(), 1);
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
                    Generic_Collections.addToCount(mFATALITY_CASUALTY, r.gettFATALITY_CASUALTY(), 1);
                    Generic_Collections.addToCount(mRESCUES, r.gettRESCUES(), 1);
                    Generic_Collections.addToCount(mEVACUATIONS, r.gettEVACUATIONS(), 1);
                    Generic_Collections.addToCount(mEVACUATIONS_CODE, r.gettEVACUATIONS_CODE(), 1);
                    Generic_Collections.addToCount(mBUILDING_EVACUATION_DELAY_DESCRIPTION, r.gettBUILDING_EVACUATION_DELAY_DESCRIPTION(), 1);
                    Generic_Collections.addToCount(mBUILDING_EVACUATION_TIME_DESCRIPTION, r.gettBUILDING_EVACUATION_TIME_DESCRIPTION(), 1);
                    
                    
                    String tBUILDING_OR_PROPERTY_TYPE = r.gettBUILDING_OR_PROPERTY_TYPE();
                    String tspread_of_fire_d = r.gettspread_of_fire_d();
                    String tFATALITY_CASUALTY = r.gettFATALITY_CASUALTY();
                    String tBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION = r.gettBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION();
                    if (tFATALITY_CASUALTY.equalsIgnoreCase("Fatality/Casualty")) {
                        Generic_Collections.addToCount(mFATALITY_CASUALTY__BUILDING_OR_PROPERTY_TYPE, tBUILDING_OR_PROPERTY_TYPE, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY__BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION, tBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION, 1);
                        if (tBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION.equalsIgnoreCase(F_Strings.NoCompartmentationInBuilding)) {
                            Generic_Collections.addToCount(mFATALITY_CASUALTY__NoCompartmentationInBuilding, tBUILDING_OR_PROPERTY_TYPE, 1);
                        }
                    }

                    if (tspread_of_fire_d.equalsIgnoreCase("Whole Building/ Affecting more than 2 floors")) {
                        Generic_Collections.addToCount(mAffectingMoreThan2FloorsBy__BUILDING_OR_PROPERTY_TYPE, tBUILDING_OR_PROPERTY_TYPE, 1);
                        if (tFATALITY_CASUALTY.equalsIgnoreCase("Fatality/Casualty")) {
                            Generic_Collections.addToCount(mFATALITY_CASUALTY__AffectingMoreThan2FloorsByType, tBUILDING_OR_PROPERTY_TYPE, 1);
                        }
                    }
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
            //print(F_Strings.OCCUPANCY_TYPE, mOCCUPANCY_TYPE);
            print(F_Strings.OCCUPIED_NORMAL, mOCCUPIED_NORMAL);
            //print(F_Strings.WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT, mWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT);
            //print(F_Strings.ALARM_SYSTEM, mALARM_SYSTEM);
            //print(F_Strings.ALARM_SYSTEM_TYPE, mALARM_SYSTEM_TYPE);
            //print(F_Strings.ALARM_REASON_FOR_POOR_OUTCOME, mALARM_REASON_FOR_POOR_OUTCOME);
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
            
            createLookups(mFRS_NAME.keySet(), F_Strings.FRS_NAME);
            createLookups(mE_CODE.keySet(), F_Strings.E_CODE);
            createLookups(mFINANCIAL_YEAR.keySet(), F_Strings.FINANCIAL_YEAR);
            createLookups(mMONTH_NAME.keySet(), F_Strings.MONTH_NAME);
            createLookups(mDAY_NIGHT.keySet(), F_Strings.DAY_NIGHT);
            createLookups(mLATE_CALL.keySet(), F_Strings.LATE_CALL);
            createLookups(mBUILDING_OR_PROPERTY_TYPE.keySet(), F_Strings.BUILDING_OR_PROPERTY_TYPE);
            createLookups(mMULTI_SEATED_FLAG.keySet(), F_Strings.MULTI_SEATED_FLAG);
            createLookups(mIGNITION_TO_DISCOVERY.keySet(), F_Strings.IGNITION_TO_DISCOVERY);
            createLookups(mHOW_DISCOVERED_DESCRIPTION.keySet(), F_Strings.HOW_DISCOVERED_DESCRIPTION);
            createLookups(mBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION.keySet(), F_Strings.BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION);
            createLookups(mBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION.keySet(), F_Strings.BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION);
            createLookups(mBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT.keySet(), F_Strings.BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT);
            //createLookups(mOCCUPANCY_TYPE.keySet(), F_Strings.OCCUPANCY_TYPE);
            createLookups(mOCCUPIED_NORMAL.keySet(), F_Strings.OCCUPIED_NORMAL);
            //createLookups(mWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT.keySet(), F_Strings.WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT);
            //createLookups(mALARM_SYSTEM.keySet(), F_Strings.ALARM_SYSTEM);
            createLookups(mALARM_REASON_FOR_POOR_OUTCOME.keySet(), F_Strings.ALARM_REASON_FOR_POOR_OUTCOME);
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
            
            print("FATALITY_CASUALTY IN BUILDING_OR_PROPERTY_TYPE", mFATALITY_CASUALTY__BUILDING_OR_PROPERTY_TYPE);
            print("FATALITY_CASUALTY IN BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION", mFATALITY_CASUALTY__BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION);

            print("FATALITY_CASUALTY IN " + F_Strings.NoCompartmentationInBuilding, mFATALITY_CASUALTY__NoCompartmentationInBuilding);

            print("Whole Building/ Affecting more than 2 floors BY property_type_detailed_d", mAffectingMoreThan2FloorsBy__BUILDING_OR_PROPERTY_TYPE);
            print("FATALITY_CASUALTY IN Whole Building/ Affecting more than 2 floors BY property_type_detailed_d", mFATALITY_CASUALTY__AffectingMoreThan2FloorsByType);
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
