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

import java.io.IOException;
import java.io.PrintWriter;
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
import uk.ac.leeds.ccg.projects.fire.data.dwellings.F_Dwellings_Record0;
import uk.ac.leeds.ccg.projects.fire.data.dwellings.F_Dwellings_Record1;
import uk.ac.leeds.ccg.projects.fire.data.dwellings.F_Dwellings_Record2;
import uk.ac.leeds.ccg.generic.io.Generic_Defaults;
import uk.ac.leeds.ccg.generic.util.Generic_Collections;
import uk.ac.leeds.ccg.projects.fire.data.F_Collection;
import uk.ac.leeds.ccg.projects.fire.data.F_Data0;
import uk.ac.leeds.ccg.projects.fire.data.dwellings.F_Dwellings_Record;
import uk.ac.leeds.ccg.projects.fire.id.F_CollectionID;
import uk.ac.leeds.ccg.projects.fire.id.F_RecordID;
import static uk.ac.leeds.ccg.projects.fire.process.F_XLSXParser.getWorkbook;

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
                loadData();
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
    public void loadData() throws IOException, Exception {
        String m = "loadData";
        Map<String, Integer> tFINANCIAL_YEARVM = env.data.name2ids.get(0);
        env.logStartTag(m);
        Path indir = files.getInputDir();
        Path gendir = files.getGeneratedDir();
        Path outdir = Paths.get(gendir.toString(), F_Strings.s_Subsets);
        Files.createDirectories(outdir);
        String type = ".xlsx"; // "csv";
        // Load "other building - residential to send.xlsx"        
        Path f = Paths.get(indir.toString(), "other building - residential to send" + type);
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
                                F_RecordID recID = new F_RecordID(Long.valueOf(line[0]));
                                F_Dwellings_Record2 rec = new F_Dwellings_Record2(recID, line);
                                recIDs.add(recID);
                                c0.data.put(recID, rec);
                                Generic_Collections.addToMap(env.data0.cID2recIDs, cID, recID);
                                env.data0.recID2cID.put(recID, cID);
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
        //data.clearCollection(cID);

        // Load other  dwellings data
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
                                    F_RecordID recID = new F_RecordID(Long.valueOf(line[0]));
                                    F_Dwellings_Record1 rec = new F_Dwellings_Record1(recID, line);
                                    recIDs.add(recID);
                                    c0.data.put(recID, rec);
                                    Generic_Collections.addToMap(env.data0.cID2recIDs, cID, recID);
                                    env.data0.recID2cID.put(recID, cID);
                                } catch (Exception e) {
                                    //System.err.println("Error loading record " + ri + ", recID=" + line[0]);
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
            //data.clearCollection(cID);
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
            //HashMap<String, Integer> mOCCUPANCY_TYPE = new HashMap<>();
            HashMap<String, Integer> mOCCUPIED_NORMAL = new HashMap<>();
            //HashMap<String, Integer> mWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT = new HashMap<>();
            //HashMap<String, Integer> mALARM_SYSTEM = new HashMap<>();
            //HashMap<String, Integer> mALARM_SYSTEM_TYPE = new HashMap<>();
            //HashMap<String, Integer> mALARM_REASON_FOR_POOR_OUTCOME = new HashMap<>();
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
            HashMap<String, Integer> mFATALITY_CASUALTY_ = new HashMap<>();
            HashMap<String, Integer> mRESCUES = new HashMap<>();
            HashMap<String, Integer> mEVACUATIONS = new HashMap<>();
            HashMap<String, Integer> mEVACUATIONS_CODE = new HashMap<>();
            HashMap<String, Integer> mBUILDING_EVACUATION_DELAY_DESCRIPTION = new HashMap<>();
            HashMap<String, Integer> mBUILDING_EVACUATION_TIME_DESCRIPTION = new HashMap<>();
            System.out.println("env.data0.cID2recIDs.size()=" + env.data0.cID2recIDs.size());
            System.out.println("env.data0.recID2cID.size()=" + env.data0.recID2cID.size());
            System.out.println("env.data0.data.size()=" + env.data0.data.size());
            Iterator<F_CollectionID> ite = env.data0.data.keySet().iterator();
            while (ite.hasNext()) {
                F_CollectionID cid = ite.next();
                System.out.println("cid=" + cid.toString());
                //env.data0.env = env;
                c0 = env.data0.getCollection(cid);
                Iterator<F_RecordID> ite2 = c0.data.keySet().iterator();
                while (ite2.hasNext()) {
                    F_RecordID id = ite2.next();
                    F_Dwellings_Record0 r = c0.data.get(id);
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
                    Generic_Collections.addToCount(mFATALITY_CASUALTY_, r.gettFATALITY_CASUALTY(), 1);
                    Generic_Collections.addToCount(mRESCUES, r.gettRESCUES(), 1);
                    Generic_Collections.addToCount(mEVACUATIONS, r.gettEVACUATIONS(), 1);
                    Generic_Collections.addToCount(mEVACUATIONS_CODE, r.gettEVACUATIONS_CODE(), 1);
                    Generic_Collections.addToCount(mBUILDING_EVACUATION_DELAY_DESCRIPTION, r.gettBUILDING_EVACUATION_DELAY_DESCRIPTION(), 1);
                    Generic_Collections.addToCount(mBUILDING_EVACUATION_TIME_DESCRIPTION, r.gettBUILDING_EVACUATION_TIME_DESCRIPTION(), 1);
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
            print(F_Strings.FATALITY_CASUALTY, mFATALITY_CASUALTY_);
            print(F_Strings.RESCUES, mRESCUES);
            print(F_Strings.EVACUATIONS, mEVACUATIONS);
            print(F_Strings.EVACUATIONS_CODE, mEVACUATIONS_CODE);
            print(F_Strings.BUILDING_EVACUATION_DELAY_DESCRIPTION, mBUILDING_EVACUATION_DELAY_DESCRIPTION);
            print(F_Strings.BUILDING_EVACUATION_TIME_DESCRIPTION, mBUILDING_EVACUATION_TIME_DESCRIPTION);
            // Create env.data
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
            //createLookups(mOCCUPANCY_TYPE.keySet(), F_Strings.OCCUPANCY_TYPE);
            createLookups(mOCCUPIED_NORMAL.keySet(), F_Strings.OCCUPIED_NORMAL);
            //createLookups(mWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT.keySet(), F_Strings.WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT);
            //createLookups(mALARM_SYSTEM.keySet(), F_Strings.ALARM_SYSTEM);
            //createLookups(mALARM_REASON_FOR_POOR_OUTCOME.keySet(), F_Strings.ALARM_REASON_FOR_POOR_OUTCOME);
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
            createLookups(mFATALITY_CASUALTY_.keySet(), F_Strings.FATALITY_CASUALTY);
            createLookups(mRESCUES.keySet(), F_Strings.RESCUES);
            createLookups(mEVACUATIONS.keySet(), F_Strings.EVACUATIONS);
            createLookups(mEVACUATIONS_CODE.keySet(), F_Strings.EVACUATIONS_CODE);
            createLookups(mBUILDING_EVACUATION_DELAY_DESCRIPTION.keySet(), F_Strings.BUILDING_EVACUATION_DELAY_DESCRIPTION);
            createLookups(mBUILDING_EVACUATION_TIME_DESCRIPTION.keySet(), F_Strings.BUILDING_EVACUATION_TIME_DESCRIPTION);
            // Organise data into collections by financial year
            //Map<String, Integer> tFINANCIAL_YEARVM = env.data.name2ids.get(0);
            ite = env.data0.data.keySet().iterator();
            while (ite.hasNext()) {
                F_CollectionID cid0 = ite.next();
                System.out.println("cid=" + cid0.toString());
                c0 = env.data0.getCollection(cid0);
                Iterator<F_RecordID> ite2 = c0.data.keySet().iterator();
                while (ite2.hasNext()) {
                    F_RecordID recID = ite2.next();
                    F_Dwellings_Record0 r = c0.data.get(recID);
                    Integer year = tFINANCIAL_YEARVM.get(r.gettFINANCIAL_YEAR());
                    if (year == null) {
                        System.out.println("year == null, F_Dwellings_Record0=" + r.toString());
                        System.out.println("r.gettFINANCIAL_YEAR()=\"" + r.gettFINANCIAL_YEAR() + "\"");
                        print("tFINANCIAL_YEARVM", tFINANCIAL_YEARVM);
                        System.exit(0);
                    } else {
                        cID = new F_CollectionID(year);
                        F_Collection c = env.data.data.get(cID);
                        env.data.cID2recIDs.get(cID).add(recID);
                        F_Dwellings_Record rec = new F_Dwellings_Record(r, env.data);
                        c.data.put(recID, rec);
                        env.data.recID2cID.put(recID, cID);
                    }
                }
                //env.data0.clearCollection(cid);
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
    public <K, V> void output(String dirname0, String dirname1, String name, int total, Map<K, V> m) {
        try {
            Path dir = Paths.get(env.files.getOutputDir().toString(), 
                    F_Files.getFormattedFilenameString(dirname0),
                    F_Files.getFormattedFilenameString(dirname1));
            Files.createDirectories(dir);
            Path p = Paths.get(dir.toString(), F_Files.getFormattedFilenameString(name) + ".csv");
            System.out.println("output " + name);
            try ( PrintWriter pw = new PrintWriter(p.toFile())) {
                //pw.println(name + " Table length=" + m.size());
                pw.println("\"" + name + "\",count");
                //m.keySet().stream().forEach(i -> pw.println("\"" + i + "\",\"" + m.get(i) + "\""));
                m.keySet().stream().forEach(i -> pw.println("\"" + i + "\"," + m.get(i)));
                pw.println("\"total\"," + total);
            }
        } catch (IOException ex) {
            Logger.getLogger(F_Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Prints out all things in {@code m} to std.out.
     *
     * @param dirname The directory name for the output.
     * @param name The name of the thing to output out - this will be the start
     * of the filename.
     * @param s The set containing values to output out.
     */
    public void output(String dirname, String name, Set<F_Dwellings_Record> s) {
        try {
            Path dir = Paths.get(env.files.getOutputDir().toString(), dirname);
            Files.createDirectories(dir);
            Path p = Paths.get(dir.toString(), F_Files.getFormattedFilenameString(name) + ".csv");
            System.out.println("output " + name);
            try ( PrintWriter pw = new PrintWriter(p.toFile())) {
                pw.println(F_Dwellings_Record.getCSVHeader());
                s.stream().forEach(i -> pw.println(i.toCSVString(env.data)));
            }
        } catch (IOException ex) {
            Logger.getLogger(F_Main.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    public void output(String dirname0, String dirname1, String name, Integer vid,
            Map<Integer, Integer> m, Map<Integer, Integer> mfc) {
        output(dirname0, dirname1, name, name, vid, m, mfc);
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
     * @param name The name of the thing to output out - this will be the start
     * of the filename.
     * @param <K> The type.
     * @param s The set containing values to output out.
     */
    public void output(String dirname0, String dirname1, String name, String vname,
            Integer vid, Map<Integer, Integer> m, Map<Integer, Integer> mfc) {
        try {
            Map<Integer, String> id2name = env.data.id2names.get(vid);
            HashMap<String, Integer> name2id = env.data.name2ids.get(vid);
            TreeMap<String, Integer> m2 = new TreeMap<>();
            Iterator<Integer> ite = m.keySet().iterator();
            while (ite.hasNext()) {
                Integer id = ite.next();
                if (id == null) {
                    System.out.println("id == null in output " + name);
                } else {
                    m2.put(id2name.get(id), m.get(id));
                }
            }
            Path dir = Paths.get(env.files.getOutputDir().toString(), 
                    F_Files.getFormattedFilenameString(dirname0),
                    F_Files.getFormattedFilenameString(dirname1));
            Files.createDirectories(dir);
            Path p = Paths.get(dir.toString(), F_Files.getFormattedFilenameString(name) + ".csv");
            System.out.println("output " + name);
            try ( PrintWriter pw = new PrintWriter(p.toFile())) {
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
            Logger.getLogger(F_Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Prints out all things in {@code m} to std.out.
     *
     * @param dirname The directory name for the output.
     * @param name First part of the name of the thing to output out - this will
     * be the start of the filename. the values in {@code m}.
     * @param vid The id of the variable.
     * @param m Keys are variable value IDs, values are counts.
     */
    public void output(String dirname0, String dirname1, String name, int vid, int total,
            Map<Integer, Integer> m) {
        Map<Integer, String> id2name = env.data.id2names.get(vid);
        TreeMap<String, Integer> r = new TreeMap<>();
        Iterator<Integer> ite = m.keySet().iterator();
        while (ite.hasNext()) {
            Integer id = ite.next();
            if (id == null) {
                System.out.println("id == null in output " + name);
            } else {
                r.put(id2name.get(id), m.get(id));
            }
        }
        output(dirname0, dirname1, name, total, r);
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
        System.out.println("env.data.cID2recIDs.size()=" + env.data.cID2recIDs.size());
        System.out.println("env.data.recID2cID.size()=" + env.data.recID2cID.size());
        System.out.println("env.data.data.size()=" + env.data.data.size());
        // Single variable
        String name = "SingleVariableSummaries";
        outputSingleVariableCountSummaries(name);
        // 2 Variables
        name = "ComplexVariableCountSummaries";
        HashMap<Integer, Integer> mNoCompartmentationInBuilding_BY_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
        HashMap<Integer, Integer> mFATALITY_CASUALTY_NoCompartmentationInBuilding_BY_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
        HashMap<Integer, Integer> mAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
        HashMap<Integer, Integer> mFATALITY_CASUALTY_AffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();

        int varFATALITY_CASUALTY = env.data.vname2id.get(F_Strings.FATALITY_CASUALTY);
        int valFATALITY_CASUALTY = env.data.name2ids.get(varFATALITY_CASUALTY).get(F_Strings.FatalityOrCasualty);
        int varBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION = env.data.vname2id.get(
                F_Strings.BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION);
        int valNoCompartmentationInBuilding = env.data.name2ids.get(
                varBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION).get(
                        F_Strings.NoCompartmentationInBuilding);
        //int varBUILDING_FLOOR_ORIGIN = env.data.vname2id.get(F_Strings.BUILDING_FLOOR_ORIGIN);
        int varspread_of_fire_d = env.data.vname2id.get(F_Strings.spread_of_fire_d);
        int valWholeBuildingOrAffectingMoreThan2Floors = env.data.name2ids.get(
                varspread_of_fire_d).get(
                        F_Strings.WholeBuildingOrAffectingMoreThan2Floors);
        int varBUILDING_OR_PROPERTY_TYPE = env.data.vname2id.get(F_Strings.BUILDING_OR_PROPERTY_TYPE);

        HashMap<Integer, Integer> mCladding_AffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();
        HashMap<Integer, Integer> mFATALITY_CASUALTY_Cladding_AffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE = new HashMap<>();

        int varBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION = env.data.vname2id.get(
                F_Strings.BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION);
        int valCladding = env.data.name2ids.get(varBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION).get(F_Strings.Cladding);

        HashSet<F_Dwellings_Record> highriseCladdingAffectingMoreThan2FloorsFATALITY_CASUALTY = new HashSet<>();
        HashSet<F_Dwellings_Record> highriseCladdingAffectingMoreThan2FloorsNonFATALITY_CASUALTY = new HashSet<>();
        int valPurposeBuiltHighRiseFlats = env.data.name2ids.get(varBUILDING_OR_PROPERTY_TYPE).get(F_Strings.PurposeBuiltHighRiseFlats);
        HashSet<F_Dwellings_Record> mediumriseCladdingAffectingMoreThan2FloorsFATALITY_CASUALTY = new HashSet<>();
        HashSet<F_Dwellings_Record> mediumriseCladdingAffectingMoreThan2FloorsNonFATALITY_CASUALTY = new HashSet<>();
        int valPurposeBuiltMediumRiseFlats = env.data.name2ids.get(varBUILDING_OR_PROPERTY_TYPE).get(F_Strings.PurposeBuiltMediumRiseFlats);
        HashSet<F_Dwellings_Record> lowriseCladdingAffectingMoreThan2FloorsFATALITY_CASUALTY = new HashSet<>();
        HashSet<F_Dwellings_Record> lowriseCladdingAffectingMoreThan2FloorsNonFATALITY_CASUALTY = new HashSet<>();
        int valPurposeBuiltLowRiseFlats = env.data.name2ids.get(varBUILDING_OR_PROPERTY_TYPE).get(F_Strings.PurposeBuiltLowRiseFlats);

        Iterator<F_CollectionID> ite = env.data.data.keySet().iterator();
        while (ite.hasNext()) {
            try {
                F_CollectionID cid = ite.next();
                F_Collection c = env.data.getCollection(cid);
                System.out.println("cid=" + cid.toString() + ", size=" + c.data.keySet().size());
                Iterator<F_RecordID> ite2 = c.data.keySet().iterator();
                while (ite2.hasNext()) {
                    F_RecordID id = ite2.next();
                    F_Dwellings_Record r = c.data.get(id);
//                    String sACCIDENTAL_OR_DELIBERATE = env.data.id2names.get(
//                            env.data.vname2id.get(F_Strings.ACCIDENTAL_OR_DELIBERATE)).get(r.tACCIDENTAL_OR_DELIBERATE);
//                    System.out.println("F_Strings.ACCIDENTAL_OR_DELIBERATE " 
//                            + sACCIDENTAL_OR_DELIBERATE);
                    if (r.tBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION == valNoCompartmentationInBuilding) {
                        Generic_Collections.addToCount(
                                mNoCompartmentationInBuilding_BY_BUILDING_OR_PROPERTY_TYPE,
                                r.tBUILDING_OR_PROPERTY_TYPE, 1);
                        if (r.tFATALITY_CASUALTY == valFATALITY_CASUALTY) {
                            Generic_Collections.addToCount(
                                    mFATALITY_CASUALTY_NoCompartmentationInBuilding_BY_BUILDING_OR_PROPERTY_TYPE,
                                    r.tBUILDING_OR_PROPERTY_TYPE, 1);
                        }
                    }
                    if (r.tspread_of_fire_d == valWholeBuildingOrAffectingMoreThan2Floors) {
                        Generic_Collections.addToCount(
                                mAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
                                r.tBUILDING_OR_PROPERTY_TYPE, 1);
                        if (r.tFATALITY_CASUALTY == valFATALITY_CASUALTY) {
                            Generic_Collections.addToCount(
                                    mFATALITY_CASUALTY_AffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
                                    r.tBUILDING_OR_PROPERTY_TYPE, 1);
                        }
                        if (r.tBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION == valCladding) {
                            Generic_Collections.addToCount(
                                    mCladding_AffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
                                    r.tBUILDING_OR_PROPERTY_TYPE, 1);
                            if (r.tFATALITY_CASUALTY == valFATALITY_CASUALTY) {
                                Generic_Collections.addToCount(
                                        mFATALITY_CASUALTY_Cladding_AffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
                                        r.tBUILDING_OR_PROPERTY_TYPE, 1);
                                if (r.tBUILDING_OR_PROPERTY_TYPE == valPurposeBuiltHighRiseFlats) {
                                    highriseCladdingAffectingMoreThan2FloorsFATALITY_CASUALTY.add(r);
                                }
                                if (r.tBUILDING_OR_PROPERTY_TYPE == valPurposeBuiltMediumRiseFlats) {
                                    mediumriseCladdingAffectingMoreThan2FloorsFATALITY_CASUALTY.add(r);
                                }
                                if (r.tBUILDING_OR_PROPERTY_TYPE == valPurposeBuiltLowRiseFlats) {
                                    lowriseCladdingAffectingMoreThan2FloorsFATALITY_CASUALTY.add(r);
                                }
                            } else {
                                if (r.tBUILDING_OR_PROPERTY_TYPE == valPurposeBuiltHighRiseFlats) {
                                    highriseCladdingAffectingMoreThan2FloorsNonFATALITY_CASUALTY.add(r);
                                }
                                if (r.tBUILDING_OR_PROPERTY_TYPE == valPurposeBuiltMediumRiseFlats) {
                                    mediumriseCladdingAffectingMoreThan2FloorsNonFATALITY_CASUALTY.add(r);
                                }
                                if (r.tBUILDING_OR_PROPERTY_TYPE == valPurposeBuiltLowRiseFlats) {
                                    lowriseCladdingAffectingMoreThan2FloorsNonFATALITY_CASUALTY.add(r);
                                }
                            }
                        }
                    }
                }

            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(F_Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        output(name, F_Strings.NoCompartmentationInBuilding + " BY "
                + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                F_Strings.BUILDING_OR_PROPERTY_TYPE,
                varBUILDING_OR_PROPERTY_TYPE,
                mNoCompartmentationInBuilding_BY_BUILDING_OR_PROPERTY_TYPE,
                mFATALITY_CASUALTY_NoCompartmentationInBuilding_BY_BUILDING_OR_PROPERTY_TYPE);
        output(name, F_Strings.WholeBuildingOrAffectingMoreThan2Floors
                + " BY "
                + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                F_Strings.BUILDING_OR_PROPERTY_TYPE,
                varBUILDING_OR_PROPERTY_TYPE,
                mAffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
                mFATALITY_CASUALTY_AffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE);
        output(name, F_Strings.Cladding
                + " AND "
                + F_Strings.WholeBuildingOrAffectingMoreThan2Floors
                + " BY "
                + F_Strings.BUILDING_OR_PROPERTY_TYPE,
                F_Strings.BUILDING_OR_PROPERTY_TYPE,
                varBUILDING_OR_PROPERTY_TYPE,
                mCladding_AffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE,
                mFATALITY_CASUALTY_Cladding_AffectingMoreThan2Floors_By_BUILDING_OR_PROPERTY_TYPE);
        output(F_Strings.s_Subsets, "highriseCladdingAffectingMoreThan2FloorsFATALITY_CASUALTY",
                highriseCladdingAffectingMoreThan2FloorsFATALITY_CASUALTY);
        output(F_Strings.s_Subsets, "highriseCladdingAffectingMoreThan2FloorsNonFATALITY_CASUALTY",
                highriseCladdingAffectingMoreThan2FloorsNonFATALITY_CASUALTY);
        output(F_Strings.s_Subsets, "mediumriseCladdingAffectingMoreThan2FloorsFATALITY_CASUALTY",
                mediumriseCladdingAffectingMoreThan2FloorsFATALITY_CASUALTY);
        output(F_Strings.s_Subsets, "mediumriseCladdingAffectingMoreThan2FloorsNonFATALITY_CASUALTY",
                mediumriseCladdingAffectingMoreThan2FloorsNonFATALITY_CASUALTY);
        output(F_Strings.s_Subsets, "lowriseCladdingAffectingMoreThan2FloorsFATALITY_CASUALTY",
                lowriseCladdingAffectingMoreThan2FloorsFATALITY_CASUALTY);
        output(F_Strings.s_Subsets, "lowriseCladdingAffectingMoreThan2FloorsNonFATALITY_CASUALTY",
                lowriseCladdingAffectingMoreThan2FloorsNonFATALITY_CASUALTY);
    }

    public void outputSingleVariableCountSummaries(String name) {
        try {
            int varFATALITY_CASUALTY = env.data.vname2id.get(F_Strings.FATALITY_CASUALTY);
            int valFATALITY_CASUALTY = env.data.name2ids.get(varFATALITY_CASUALTY).get(F_Strings.FatalityOrCasualty);
            // 1 Variable
            int allTotal = 0;
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
            //HashMap<Integer, Integer> mAllOCCUPANCY_TYPE = new HashMap<>();
            Map<Integer, Map<Integer, Integer>> mAllOCCUPIED_NORMAL = new HashMap<>();
            Map<Integer, Map<Integer, Integer>> mAllFATALITY_CASUALTY_OCCUPIED_NORMAL = new HashMap<>();
            //HashMap<Integer, Integer> mAllWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT = new HashMap<>();
            //HashMap<Integer, Integer> mAllALARM_SYSTEM = new HashMap<>();
            //HashMap<Integer, Integer> mAllALARM_SYSTEM_TYPE = new HashMap<>();
            //HashMap<Integer, Integer> mAllALARM_REASON_FOR_POOR_OUTCOME = new HashMap<>();
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
                // 
                HashMap<Integer, Integer> mBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION = new HashMap<>();
                mAllBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION.put(cid.id, mBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION = new HashMap<>();
                mAllFATALITY_CASUALTY_BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION.put(cid.id, mFATALITY_CASUALTY_BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION);
                // BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT
                HashMap<Integer, Integer> mBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT = new HashMap<>();
                mAllBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT.put(cid.id, mBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT = new HashMap<>();
                mAllFATALITY_CASUALTY_BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT.put(cid.id, mFATALITY_CASUALTY_BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT);
                //HashMap<Integer, Integer> mOCCUPANCY_TYPE = new HashMap<>();
                // OCCUPIED_NORMAL
                HashMap<Integer, Integer> mOCCUPIED_NORMAL = new HashMap<>();
                mAllOCCUPIED_NORMAL.put(cid.id, mOCCUPIED_NORMAL);
                HashMap<Integer, Integer> mFATALITY_CASUALTY_OCCUPIED_NORMAL = new HashMap<>();
                mAllFATALITY_CASUALTY_OCCUPIED_NORMAL.put(cid.id, mFATALITY_CASUALTY_OCCUPIED_NORMAL);
                //HashMap<Integer, Integer> mWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT = new HashMap<>();
                //HashMap<Integer, Integer> mALARM_SYSTEM = new HashMap<>();
                //HashMap<Integer, Integer> mALARM_SYSTEM_TYPE = new HashMap<>();
                //HashMap<Integer, Integer> mALARM_REASON_FOR_POOR_OUTCOME = new HashMap<>();
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
                Iterator<F_RecordID> ite2 = c.data.keySet().iterator();
                while (ite2.hasNext()) {
                    F_RecordID id = ite2.next();
                    F_Dwellings_Record r = c.data.get(id);
                    total ++;
                    Generic_Collections.addToCount(mFATALITY_CASUALTY, r.tFATALITY_CASUALTY, 1);
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
                    //Generic_Collections.addToCount(mOCCUPANCY_TYPE, r.tOCCUPANCY_TYPE, 1);
                    Generic_Collections.addToCount(mOCCUPIED_NORMAL, r.tOCCUPIED_NORMAL, 1);
                    //Generic_Collections.addToCount(mWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT, r.tWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT, 1);
                    //Generic_Collections.addToCount(mALARM_SYSTEM, r.tALARM_SYSTEM, 1);
                    //Generic_Collections.addToCount(mALARM_SYSTEM_TYPE, r.tALARM_SYSTEM_TYPE, 1);
                    //Generic_Collections.addToCount(mALARM_REASON_FOR_POOR_OUTCOME, r.tALARM_REASON_FOR_POOR_OUTCOME, 1);
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
                    if (r.tFATALITY_CASUALTY == valFATALITY_CASUALTY) {
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_FRS_NAME, r.tFRS_NAME, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_E_CODE, r.tE_CODE, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_MONTH_NAME, r.tMONTH_NAME, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_WEEKDAY_WEEKEND, r.tWEEKDAY_WEEKEND, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_DAY_NIGHT, r.tDAY_NIGHT, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_LATE_CALL, r.tLATE_CALL, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_BUILDING_OR_PROPERTY_TYPE, r.tBUILDING_OR_PROPERTY_TYPE, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_MULTI_SEATED_FLAG, r.tMULTI_SEATED_FLAG, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_IGNITION_TO_DISCOVERY, r.tIGNITION_TO_DISCOVERY, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_DISCOVERY_TO_CALL, r.tDISCOVERY_TO_CALL, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_HOW_DISCOVERED_DESCRIPTION, r.tHOW_DISCOVERED_DESCRIPTION, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION, r.tBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION, r.tBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT, r.tBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT, 1);
                        //Generic_Collections.addToCount(mFATALITY_CASUALTY_OCCUPANCY_TYPE, r.tOCCUPANCY_TYPE, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_OCCUPIED_NORMAL, r.tOCCUPIED_NORMAL, 1);
                        //Generic_Collections.addToCount(mFATALITY_CASUALTY_WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT, r.tWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT, 1);
                        //Generic_Collections.addToCount(mFATALITY_CASUALTY_ALARM_SYSTEM, r.tALARM_SYSTEM, 1);
                        //Generic_Collections.addToCount(mFATALITY_CASUALTY_ALARM_SYSTEM_TYPE, r.tALARM_SYSTEM_TYPE, 1);
                        //Generic_Collections.addToCount(mFATALITY_CASUALTY_ALARM_REASON_FOR_POOR_OUTCOME, r.tALARM_REASON_FOR_POOR_OUTCOME, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_ACCIDENTAL_OR_DELIBERATE, r.tACCIDENTAL_OR_DELIBERATE, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_VEHICLES, r.tVEHICLES, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_VEHICLES_CODE, r.tVEHICLES_CODE, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_PERSONNEL, r.tPERSONNEL, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_PERSONNEL_CODE, r.tPERSONNEL_CODE, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_STARTING_DELAY_DESCRIPTION, r.tSTARTING_DELAY_DESCRIPTION, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_ACTION_NON_FRS_DESCRIPTION, r.tACTION_NON_FRS_DESCRIPTION, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_ACTION_FRS_DESCRIPTION, r.tACTION_FRS_DESCRIPTION, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_CAUSE_OF_FIRE, r.tCAUSE_OF_FIRE, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_IGNITION_POWER, r.tIGNITION_POWER, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_SOURCE_OF_IGNITION, r.tSOURCE_OF_IGNITION, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_ITEM_IGNITED, r.tITEM_IGNITED, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_ITEM_CAUSING_SPREAD, r.tITEM_CAUSING_SPREAD, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_RAPID_FIRE_GROWTH, r.tRAPID_FIRE_GROWTH, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION, r.tCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_CAUSE_EXPLOSION_INVOLVED, r.tCAUSE_EXPLOSION_INVOLVED, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION, r.tCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_CAUSE_EXPLOSION_STAGE_DESCRIPTION, r.tCAUSE_EXPLOSION_STAGE_DESCRIPTION, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION, r.tCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION, r.tBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_BUILDING_FLOORS_ABOVE_GROUND, r.tBUILDING_FLOORS_ABOVE_GROUND, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_BUILDING_FLOORS_BELOW_GROUND, r.tBUILDING_FLOORS_BELOW_GROUND, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_BUILDING_FLOOR_ORIGIN, r.tBUILDING_FLOOR_ORIGIN, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION, r.tBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION, r.tBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_FIRE_START_LOCATION, r.tFIRE_START_LOCATION, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_FIRE_SIZE_ON_ARRIVAL, r.tFIRE_SIZE_ON_ARRIVAL, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_OTHER_PROPERTY_AFFECTED_ON_ARRIVAL, r.tOTHER_PROPERTY_AFFECTED_ON_ARRIVAL, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION, r.tBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION, r.tBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_FIRE_SIZE_ON_ARRIVAL_DESCRIPTION, r.tFIRE_SIZE_ON_ARRIVAL_DESCRIPTION, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_other_property_affected_close_d, r.tother_property_affected_close_d, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_spread_of_fire_d, r.tspread_of_fire_d, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_RESPONSE_TIME, r.tRESPONSE_TIME, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_RESPONSE_TIME_CODE, r.tRESPONSE_TIME_CODE, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_TIME_AT_SCENE, r.tTIME_AT_SCENE, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_TIME_AT_SCENE_CODE, r.tTIME_AT_SCENE_CODE, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_RESCUES, r.tRESCUES, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_EVACUATIONS, r.tEVACUATIONS, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_EVACUATIONS_CODE, r.tEVACUATIONS_CODE, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_BUILDING_EVACUATION_DELAY_DESCRIPTION, r.tBUILDING_EVACUATION_DELAY_DESCRIPTION, 1);
                        Generic_Collections.addToCount(mFATALITY_CASUALTY_BUILDING_EVACUATION_TIME_DESCRIPTION, r.tBUILDING_EVACUATION_TIME_DESCRIPTION, 1);
                    }
                }
                allTotal += total;
                output(name, tFINANCIAL_YEAR, F_Strings.FATALITY_CASUALTY, varFATALITY_CASUALTY, total, mFATALITY_CASUALTY);
                output(name, tFINANCIAL_YEAR, F_Strings.FRS_NAME, env.data.vname2id.get(F_Strings.FRS_NAME), mFRS_NAME, mFATALITY_CASUALTY_FRS_NAME);
                output(name, tFINANCIAL_YEAR, F_Strings.E_CODE, env.data.vname2id.get(F_Strings.E_CODE), mE_CODE, mFATALITY_CASUALTY_E_CODE);
                output(name, tFINANCIAL_YEAR, F_Strings.MONTH_NAME, env.data.vname2id.get(F_Strings.MONTH_NAME), mMONTH_NAME, mFATALITY_CASUALTY_MONTH_NAME);
                output(name, tFINANCIAL_YEAR, F_Strings.WEEKDAY_WEEKEND, env.data.vname2id.get(F_Strings.WEEKDAY_WEEKEND), mWEEKDAY_WEEKEND, mFATALITY_CASUALTY_WEEKDAY_WEEKEND);
                output(name, tFINANCIAL_YEAR, F_Strings.DAY_NIGHT, env.data.vname2id.get(F_Strings.DAY_NIGHT), mDAY_NIGHT, mFATALITY_CASUALTY_DAY_NIGHT);
                output(name, tFINANCIAL_YEAR, F_Strings.LATE_CALL, env.data.vname2id.get(F_Strings.LATE_CALL), mLATE_CALL, mFATALITY_CASUALTY_LATE_CALL);
                output(name, tFINANCIAL_YEAR, F_Strings.BUILDING_OR_PROPERTY_TYPE, env.data.vname2id.get(F_Strings.BUILDING_OR_PROPERTY_TYPE), mBUILDING_OR_PROPERTY_TYPE, mFATALITY_CASUALTY_BUILDING_OR_PROPERTY_TYPE);
                output(name, tFINANCIAL_YEAR, F_Strings.MULTI_SEATED_FLAG, env.data.vname2id.get(F_Strings.MULTI_SEATED_FLAG), mMULTI_SEATED_FLAG, mFATALITY_CASUALTY_MULTI_SEATED_FLAG);
                output(name, tFINANCIAL_YEAR, F_Strings.IGNITION_TO_DISCOVERY, env.data.vname2id.get(F_Strings.IGNITION_TO_DISCOVERY), mIGNITION_TO_DISCOVERY, mFATALITY_CASUALTY_IGNITION_TO_DISCOVERY);
                output(name, tFINANCIAL_YEAR, F_Strings.DISCOVERY_TO_CALL, env.data.vname2id.get(F_Strings.DISCOVERY_TO_CALL), mDISCOVERY_TO_CALL, mFATALITY_CASUALTY_DISCOVERY_TO_CALL);
                output(name, tFINANCIAL_YEAR, F_Strings.HOW_DISCOVERED_DESCRIPTION, env.data.vname2id.get(F_Strings.HOW_DISCOVERED_DESCRIPTION), mHOW_DISCOVERED_DESCRIPTION, mFATALITY_CASUALTY_HOW_DISCOVERED_DESCRIPTION);
                output(name, tFINANCIAL_YEAR, F_Strings.BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION), mBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION, mFATALITY_CASUALTY_BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION);
                output(name, tFINANCIAL_YEAR, F_Strings.BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION), mBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION, mFATALITY_CASUALTY_BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION);
                output(name, tFINANCIAL_YEAR, F_Strings.BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT, env.data.vname2id.get(F_Strings.BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT), mBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT, mFATALITY_CASUALTY_BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT);
                //print(F_Strings.OCCUPANCY_TYPE, env.data.vname2id.get(F_Strings.OCCUPANCY_TYPE), mOCCUPANCY_TYPE, mFATALITY_CASUALTY_);
                output(name, tFINANCIAL_YEAR, F_Strings.OCCUPIED_NORMAL, env.data.vname2id.get(F_Strings.OCCUPIED_NORMAL), mOCCUPIED_NORMAL, mFATALITY_CASUALTY_OCCUPIED_NORMAL);
                //print(F_Strings.WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT, env.data.vname2id.get(F_Strings.WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT), mWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT, mFATALITY_CASUALTY_);
                //print(F_Strings.ALARM_SYSTEM, env.data.vname2id.get(F_Strings.ALARM_SYSTEM), mALARM_SYSTEM, mFATALITY_CASUALTY_);
                //print(F_Strings.ALARM_SYSTEM_TYPE, env.data.vname2id.get(F_Strings.ALARM_SYSTEM_TYPE), mALARM_SYSTEM_TYPE, mFATALITY_CASUALTY_);
                //print(F_Strings.ALARM_REASON_FOR_POOR_OUTCOME, env.data.vname2id.get(F_Strings.ALARM_REASON_FOR_POOR_OUTCOME), mALARM_REASON_FOR_POOR_OUTCOME, mFATALITY_CASUALTY_);
                output(name, tFINANCIAL_YEAR, F_Strings.ACCIDENTAL_OR_DELIBERATE, env.data.vname2id.get(F_Strings.ACCIDENTAL_OR_DELIBERATE), mACCIDENTAL_OR_DELIBERATE, mFATALITY_CASUALTY_ACCIDENTAL_OR_DELIBERATE);
                output(name, tFINANCIAL_YEAR, F_Strings.VEHICLES, env.data.vname2id.get(F_Strings.VEHICLES), mVEHICLES, mFATALITY_CASUALTY_VEHICLES);
                output(name, tFINANCIAL_YEAR, F_Strings.VEHICLES_CODE, env.data.vname2id.get(F_Strings.VEHICLES_CODE), mVEHICLES_CODE, mFATALITY_CASUALTY_VEHICLES_CODE);
                output(name, tFINANCIAL_YEAR, F_Strings.PERSONNEL, env.data.vname2id.get(F_Strings.PERSONNEL), mPERSONNEL, mFATALITY_CASUALTY_PERSONNEL);
                output(name, tFINANCIAL_YEAR, F_Strings.PERSONNEL_CODE, env.data.vname2id.get(F_Strings.PERSONNEL_CODE), mPERSONNEL_CODE, mFATALITY_CASUALTY_PERSONNEL_CODE);
                output(name, tFINANCIAL_YEAR, F_Strings.STARTING_DELAY_DESCRIPTION, env.data.vname2id.get(F_Strings.STARTING_DELAY_DESCRIPTION), mSTARTING_DELAY_DESCRIPTION, mFATALITY_CASUALTY_STARTING_DELAY_DESCRIPTION);
                output(name, tFINANCIAL_YEAR, F_Strings.ACTION_NON_FRS_DESCRIPTION, env.data.vname2id.get(F_Strings.ACTION_NON_FRS_DESCRIPTION), mACTION_NON_FRS_DESCRIPTION, mFATALITY_CASUALTY_ACTION_NON_FRS_DESCRIPTION);
                output(name, tFINANCIAL_YEAR, F_Strings.ACTION_FRS_DESCRIPTION, env.data.vname2id.get(F_Strings.ACTION_FRS_DESCRIPTION), mACTION_FRS_DESCRIPTION, mFATALITY_CASUALTY_ACTION_FRS_DESCRIPTION);
                output(name, tFINANCIAL_YEAR, F_Strings.CAUSE_OF_FIRE, env.data.vname2id.get(F_Strings.CAUSE_OF_FIRE), mCAUSE_OF_FIRE, mFATALITY_CASUALTY_CAUSE_OF_FIRE);
                output(name, tFINANCIAL_YEAR, F_Strings.IGNITION_POWER, env.data.vname2id.get(F_Strings.IGNITION_POWER), mIGNITION_POWER, mFATALITY_CASUALTY_IGNITION_POWER);
                output(name, tFINANCIAL_YEAR, F_Strings.SOURCE_OF_IGNITION, env.data.vname2id.get(F_Strings.SOURCE_OF_IGNITION), mSOURCE_OF_IGNITION, mFATALITY_CASUALTY_SOURCE_OF_IGNITION);
                output(name, tFINANCIAL_YEAR, F_Strings.ITEM_IGNITED, env.data.vname2id.get(F_Strings.ITEM_IGNITED), mITEM_IGNITED, mFATALITY_CASUALTY_ITEM_IGNITED);
                output(name, tFINANCIAL_YEAR, F_Strings.ITEM_CAUSING_SPREAD, env.data.vname2id.get(F_Strings.ITEM_CAUSING_SPREAD), mITEM_CAUSING_SPREAD, mFATALITY_CASUALTY_ITEM_CAUSING_SPREAD);
                output(name, tFINANCIAL_YEAR, F_Strings.RAPID_FIRE_GROWTH, env.data.vname2id.get(F_Strings.RAPID_FIRE_GROWTH), mRAPID_FIRE_GROWTH, mFATALITY_CASUALTY_RAPID_FIRE_GROWTH);
                output(name, tFINANCIAL_YEAR, F_Strings.CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION, env.data.vname2id.get(F_Strings.CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION), mCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION, mFATALITY_CASUALTY_CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION);
                output(name, tFINANCIAL_YEAR, F_Strings.CAUSE_EXPLOSION_INVOLVED, env.data.vname2id.get(F_Strings.CAUSE_EXPLOSION_INVOLVED), mCAUSE_EXPLOSION_INVOLVED, mFATALITY_CASUALTY_CAUSE_EXPLOSION_INVOLVED);
                output(name, tFINANCIAL_YEAR, F_Strings.CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION, env.data.vname2id.get(F_Strings.CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION), mCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION, mFATALITY_CASUALTY_CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION);
                output(name, tFINANCIAL_YEAR, F_Strings.CAUSE_EXPLOSION_STAGE_DESCRIPTION, env.data.vname2id.get(F_Strings.CAUSE_EXPLOSION_STAGE_DESCRIPTION), mCAUSE_EXPLOSION_STAGE_DESCRIPTION, mFATALITY_CASUALTY_CAUSE_EXPLOSION_STAGE_DESCRIPTION);
                output(name, tFINANCIAL_YEAR, F_Strings.CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION, env.data.vname2id.get(F_Strings.CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION), mCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION, mFATALITY_CASUALTY_CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION);
                output(name, tFINANCIAL_YEAR, F_Strings.BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION), mBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION, mFATALITY_CASUALTY_BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION);
                output(name, tFINANCIAL_YEAR, F_Strings.BUILDING_FLOORS_ABOVE_GROUND, env.data.vname2id.get(F_Strings.BUILDING_FLOORS_ABOVE_GROUND), mBUILDING_FLOORS_ABOVE_GROUND, mFATALITY_CASUALTY_BUILDING_FLOORS_ABOVE_GROUND);
                output(name, tFINANCIAL_YEAR, F_Strings.BUILDING_FLOORS_BELOW_GROUND, env.data.vname2id.get(F_Strings.BUILDING_FLOORS_BELOW_GROUND), mBUILDING_FLOORS_BELOW_GROUND, mFATALITY_CASUALTY_BUILDING_FLOORS_BELOW_GROUND);
                output(name, tFINANCIAL_YEAR, F_Strings.BUILDING_FLOOR_ORIGIN, env.data.vname2id.get(F_Strings.BUILDING_FLOOR_ORIGIN), mBUILDING_FLOOR_ORIGIN, mFATALITY_CASUALTY_BUILDING_FLOOR_ORIGIN);
                output(name, tFINANCIAL_YEAR, F_Strings.BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION), mBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION, mFATALITY_CASUALTY_BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION);
                output(name, tFINANCIAL_YEAR, F_Strings.BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION), mBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION, mFATALITY_CASUALTY_BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION);
                output(name, tFINANCIAL_YEAR, F_Strings.FIRE_START_LOCATION, env.data.vname2id.get(F_Strings.FIRE_START_LOCATION), mFIRE_START_LOCATION, mFATALITY_CASUALTY_FIRE_START_LOCATION);
                output(name, tFINANCIAL_YEAR, F_Strings.FIRE_SIZE_ON_ARRIVAL, env.data.vname2id.get(F_Strings.FIRE_SIZE_ON_ARRIVAL), mFIRE_SIZE_ON_ARRIVAL, mFATALITY_CASUALTY_FIRE_SIZE_ON_ARRIVAL);
                output(name, tFINANCIAL_YEAR, F_Strings.OTHER_PROPERTY_AFFECTED_ON_ARRIVAL, env.data.vname2id.get(F_Strings.OTHER_PROPERTY_AFFECTED_ON_ARRIVAL), mOTHER_PROPERTY_AFFECTED_ON_ARRIVAL, mFATALITY_CASUALTY_OTHER_PROPERTY_AFFECTED_ON_ARRIVAL);
                output(name, tFINANCIAL_YEAR, F_Strings.BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION), mBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION, mFATALITY_CASUALTY_BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION);
                output(name, tFINANCIAL_YEAR, F_Strings.BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION), mBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION, mFATALITY_CASUALTY_BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION);
                output(name, tFINANCIAL_YEAR, F_Strings.FIRE_SIZE_ON_ARRIVAL_DESCRIPTION, env.data.vname2id.get(F_Strings.FIRE_SIZE_ON_ARRIVAL_DESCRIPTION), mFIRE_SIZE_ON_ARRIVAL_DESCRIPTION, mFATALITY_CASUALTY_FIRE_SIZE_ON_ARRIVAL_DESCRIPTION);
                output(name, tFINANCIAL_YEAR, F_Strings.other_property_affected_close_d, env.data.vname2id.get(F_Strings.other_property_affected_close_d), mother_property_affected_close_d, mFATALITY_CASUALTY_other_property_affected_close_d);
                output(name, tFINANCIAL_YEAR, F_Strings.spread_of_fire_d, env.data.vname2id.get(F_Strings.spread_of_fire_d), mspread_of_fire_d, mFATALITY_CASUALTY_spread_of_fire_d);
                output(name, tFINANCIAL_YEAR, F_Strings.RESPONSE_TIME, env.data.vname2id.get(F_Strings.RESPONSE_TIME), mRESPONSE_TIME, mFATALITY_CASUALTY_RESPONSE_TIME);
                output(name, tFINANCIAL_YEAR, F_Strings.RESPONSE_TIME_CODE, env.data.vname2id.get(F_Strings.RESPONSE_TIME_CODE), mRESPONSE_TIME_CODE, mFATALITY_CASUALTY_RESPONSE_TIME_CODE);
                output(name, tFINANCIAL_YEAR, F_Strings.TIME_AT_SCENE, env.data.vname2id.get(F_Strings.TIME_AT_SCENE), mTIME_AT_SCENE, mFATALITY_CASUALTY_TIME_AT_SCENE);
                output(name, tFINANCIAL_YEAR, F_Strings.TIME_AT_SCENE_CODE, env.data.vname2id.get(F_Strings.TIME_AT_SCENE_CODE), mTIME_AT_SCENE_CODE, mFATALITY_CASUALTY_TIME_AT_SCENE_CODE);
                output(name, tFINANCIAL_YEAR, F_Strings.RESCUES, env.data.vname2id.get(F_Strings.RESCUES), mRESCUES, mFATALITY_CASUALTY_RESCUES);
                output(name, tFINANCIAL_YEAR, F_Strings.EVACUATIONS, env.data.vname2id.get(F_Strings.EVACUATIONS), mEVACUATIONS, mFATALITY_CASUALTY_EVACUATIONS);
                output(name, tFINANCIAL_YEAR, F_Strings.EVACUATIONS_CODE, env.data.vname2id.get(F_Strings.EVACUATIONS_CODE), mEVACUATIONS_CODE, mFATALITY_CASUALTY_EVACUATIONS_CODE);
                output(name, tFINANCIAL_YEAR, F_Strings.BUILDING_EVACUATION_DELAY_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_EVACUATION_DELAY_DESCRIPTION), mBUILDING_EVACUATION_DELAY_DESCRIPTION, mFATALITY_CASUALTY_BUILDING_EVACUATION_DELAY_DESCRIPTION);
                output(name, tFINANCIAL_YEAR, F_Strings.BUILDING_EVACUATION_TIME_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_EVACUATION_TIME_DESCRIPTION), mBUILDING_EVACUATION_TIME_DESCRIPTION, mFATALITY_CASUALTY_BUILDING_EVACUATION_TIME_DESCRIPTION);
            }
            output(name, "2010_20", F_Strings.FATALITY_CASUALTY, varFATALITY_CASUALTY, allTotal, collectCounts(mAllFATALITY_CASUALTY));
            output(name, "2010_20", F_Strings.FRS_NAME, env.data.vname2id.get(F_Strings.FRS_NAME), collectCounts(mAllFRS_NAME), collectCounts(mAllFATALITY_CASUALTY_FRS_NAME));
            output(name, "2010_20", F_Strings.E_CODE, env.data.vname2id.get(F_Strings.E_CODE), collectCounts(mAllE_CODE), collectCounts(mAllFATALITY_CASUALTY_E_CODE));
            output(name, "2010_20", F_Strings.MONTH_NAME, env.data.vname2id.get(F_Strings.MONTH_NAME), collectCounts(mAllMONTH_NAME), collectCounts(mAllFATALITY_CASUALTY_MONTH_NAME));
            output(name, "2010_20", F_Strings.WEEKDAY_WEEKEND, env.data.vname2id.get(F_Strings.WEEKDAY_WEEKEND), collectCounts(mAllWEEKDAY_WEEKEND), collectCounts(mAllFATALITY_CASUALTY_WEEKDAY_WEEKEND));
            output(name, "2010_20", F_Strings.DAY_NIGHT, env.data.vname2id.get(F_Strings.DAY_NIGHT), collectCounts(mAllDAY_NIGHT), collectCounts(mAllFATALITY_CASUALTY_DAY_NIGHT));
            output(name, "2010_20", F_Strings.LATE_CALL, env.data.vname2id.get(F_Strings.LATE_CALL), collectCounts(mAllLATE_CALL), collectCounts(mAllFATALITY_CASUALTY_LATE_CALL));
            output(name, "2010_20", F_Strings.BUILDING_OR_PROPERTY_TYPE, env.data.vname2id.get(F_Strings.BUILDING_OR_PROPERTY_TYPE), collectCounts(mAllBUILDING_OR_PROPERTY_TYPE), collectCounts(mAllFATALITY_CASUALTY_BUILDING_OR_PROPERTY_TYPE));
            output(name, "2010_20", F_Strings.MULTI_SEATED_FLAG, env.data.vname2id.get(F_Strings.MULTI_SEATED_FLAG), collectCounts(mAllMULTI_SEATED_FLAG), collectCounts(mAllFATALITY_CASUALTY_MULTI_SEATED_FLAG));
            output(name, "2010_20", F_Strings.IGNITION_TO_DISCOVERY, env.data.vname2id.get(F_Strings.IGNITION_TO_DISCOVERY), collectCounts(mAllIGNITION_TO_DISCOVERY), collectCounts(mAllFATALITY_CASUALTY_IGNITION_TO_DISCOVERY));
            output(name, "2010_20", F_Strings.DISCOVERY_TO_CALL, env.data.vname2id.get(F_Strings.DISCOVERY_TO_CALL), collectCounts(mAllDISCOVERY_TO_CALL), collectCounts(mAllFATALITY_CASUALTY_DISCOVERY_TO_CALL));
            output(name, "2010_20", F_Strings.HOW_DISCOVERED_DESCRIPTION, env.data.vname2id.get(F_Strings.HOW_DISCOVERED_DESCRIPTION), collectCounts(mAllHOW_DISCOVERED_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_HOW_DISCOVERED_DESCRIPTION));
            output(name, "2010_20", F_Strings.BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION), collectCounts(mAllBUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_BUILDING_SAFETY_SYSTEM_COMPARTMENTATION_DESCRIPTION));
            output(name, "2010_20", F_Strings.BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION), collectCounts(mAllBUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_BUILDING_SAFETY_SYSTEM_MEANS_OF_ESCAPE_DESCRIPTION));
            output(name, "2010_20", F_Strings.BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT, env.data.vname2id.get(F_Strings.BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT), collectCounts(mAllBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT), collectCounts(mAllFATALITY_CASUALTY_BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT));
            //print(F_Strings.OCCUPANCY_TYPE, env.data.vname2id.get(F_Strings.OCCUPANCY_TYPE), collectCounts(mAllOCCUPANCY_TYPE, collectCounts(mAllFATALITY_CASUALTY_);
            output(name, "2010_20", F_Strings.OCCUPIED_NORMAL, env.data.vname2id.get(F_Strings.OCCUPIED_NORMAL), collectCounts(mAllOCCUPIED_NORMAL), collectCounts(mAllFATALITY_CASUALTY_OCCUPIED_NORMAL));
            //print(F_Strings.WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT, env.data.vname2id.get(F_Strings.WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT), collectCounts(mAllWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT, collectCounts(mAllFATALITY_CASUALTY_);
            //print(F_Strings.ALARM_SYSTEM, env.data.vname2id.get(F_Strings.ALARM_SYSTEM), collectCounts(mAllALARM_SYSTEM, collectCounts(mAllFATALITY_CASUALTY_);
            //print(F_Strings.ALARM_SYSTEM_TYPE, env.data.vname2id.get(F_Strings.ALARM_SYSTEM_TYPE), collectCounts(mAllALARM_SYSTEM_TYPE, collectCounts(mAllFATALITY_CASUALTY_);
            //print(F_Strings.ALARM_REASON_FOR_POOR_OUTCOME, env.data.vname2id.get(F_Strings.ALARM_REASON_FOR_POOR_OUTCOME), collectCounts(mAllALARM_REASON_FOR_POOR_OUTCOME, collectCounts(mAllFATALITY_CASUALTY_);
            output(name, "2010_20", F_Strings.ACCIDENTAL_OR_DELIBERATE, env.data.vname2id.get(F_Strings.ACCIDENTAL_OR_DELIBERATE), collectCounts(mAllACCIDENTAL_OR_DELIBERATE), collectCounts(mAllFATALITY_CASUALTY_ACCIDENTAL_OR_DELIBERATE));
            output(name, "2010_20", F_Strings.VEHICLES, env.data.vname2id.get(F_Strings.VEHICLES), collectCounts(mAllVEHICLES), collectCounts(mAllFATALITY_CASUALTY_VEHICLES));
            output(name, "2010_20", F_Strings.VEHICLES_CODE, env.data.vname2id.get(F_Strings.VEHICLES_CODE), collectCounts(mAllVEHICLES_CODE), collectCounts(mAllFATALITY_CASUALTY_VEHICLES_CODE));
            output(name, "2010_20", F_Strings.PERSONNEL, env.data.vname2id.get(F_Strings.PERSONNEL), collectCounts(mAllPERSONNEL), collectCounts(mAllFATALITY_CASUALTY_PERSONNEL));
            output(name, "2010_20", F_Strings.PERSONNEL_CODE, env.data.vname2id.get(F_Strings.PERSONNEL_CODE), collectCounts(mAllPERSONNEL_CODE), collectCounts(mAllFATALITY_CASUALTY_PERSONNEL_CODE));
            output(name, "2010_20", F_Strings.STARTING_DELAY_DESCRIPTION, env.data.vname2id.get(F_Strings.STARTING_DELAY_DESCRIPTION), collectCounts(mAllSTARTING_DELAY_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_STARTING_DELAY_DESCRIPTION));
            output(name, "2010_20", F_Strings.ACTION_NON_FRS_DESCRIPTION, env.data.vname2id.get(F_Strings.ACTION_NON_FRS_DESCRIPTION), collectCounts(mAllACTION_NON_FRS_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_ACTION_NON_FRS_DESCRIPTION));
            output(name, "2010_20", F_Strings.ACTION_FRS_DESCRIPTION, env.data.vname2id.get(F_Strings.ACTION_FRS_DESCRIPTION), collectCounts(mAllACTION_FRS_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_ACTION_FRS_DESCRIPTION));
            output(name, "2010_20", F_Strings.CAUSE_OF_FIRE, env.data.vname2id.get(F_Strings.CAUSE_OF_FIRE), collectCounts(mAllCAUSE_OF_FIRE), collectCounts(mAllFATALITY_CASUALTY_CAUSE_OF_FIRE));
            output(name, "2010_20", F_Strings.IGNITION_POWER, env.data.vname2id.get(F_Strings.IGNITION_POWER), collectCounts(mAllIGNITION_POWER), collectCounts(mAllFATALITY_CASUALTY_IGNITION_POWER));
            output(name, "2010_20", F_Strings.SOURCE_OF_IGNITION, env.data.vname2id.get(F_Strings.SOURCE_OF_IGNITION), collectCounts(mAllSOURCE_OF_IGNITION), collectCounts(mAllFATALITY_CASUALTY_SOURCE_OF_IGNITION));
            output(name, "2010_20", F_Strings.ITEM_IGNITED, env.data.vname2id.get(F_Strings.ITEM_IGNITED), collectCounts(mAllITEM_IGNITED), collectCounts(mAllFATALITY_CASUALTY_ITEM_IGNITED));
            output(name, "2010_20", F_Strings.ITEM_CAUSING_SPREAD, env.data.vname2id.get(F_Strings.ITEM_CAUSING_SPREAD), collectCounts(mAllITEM_CAUSING_SPREAD), collectCounts(mAllFATALITY_CASUALTY_ITEM_CAUSING_SPREAD));
            output(name, "2010_20", F_Strings.RAPID_FIRE_GROWTH, env.data.vname2id.get(F_Strings.RAPID_FIRE_GROWTH), collectCounts(mAllRAPID_FIRE_GROWTH), collectCounts(mAllFATALITY_CASUALTY_RAPID_FIRE_GROWTH));
            output(name, "2010_20", F_Strings.CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION, env.data.vname2id.get(F_Strings.CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION), collectCounts(mAllCAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_CAUSE_SUBSTANCES_DANGEROUS_DESCRIPTION));
            output(name, "2010_20", F_Strings.CAUSE_EXPLOSION_INVOLVED, env.data.vname2id.get(F_Strings.CAUSE_EXPLOSION_INVOLVED), collectCounts(mAllCAUSE_EXPLOSION_INVOLVED), collectCounts(mAllFATALITY_CASUALTY_CAUSE_EXPLOSION_INVOLVED));
            output(name, "2010_20", F_Strings.CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION, env.data.vname2id.get(F_Strings.CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION), collectCounts(mAllCAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_CAUSE_SUBSTANCES_EXPLOSION_DESCRIPTION));
            output(name, "2010_20", F_Strings.CAUSE_EXPLOSION_STAGE_DESCRIPTION, env.data.vname2id.get(F_Strings.CAUSE_EXPLOSION_STAGE_DESCRIPTION), collectCounts(mAllCAUSE_EXPLOSION_STAGE_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_CAUSE_EXPLOSION_STAGE_DESCRIPTION));
            output(name, "2010_20", F_Strings.CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION, env.data.vname2id.get(F_Strings.CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION), collectCounts(mAllCAUSE_EXPLOSION_CONTAINERS_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_CAUSE_EXPLOSION_CONTAINERS_DESCRIPTION));
            output(name, "2010_20", F_Strings.BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION), collectCounts(mAllBUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_BUILDING_SPECIAL_CONSTRUCTION_DESCRIPTION));
            output(name, "2010_20", F_Strings.BUILDING_FLOORS_ABOVE_GROUND, env.data.vname2id.get(F_Strings.BUILDING_FLOORS_ABOVE_GROUND), collectCounts(mAllBUILDING_FLOORS_ABOVE_GROUND), collectCounts(mAllFATALITY_CASUALTY_BUILDING_FLOORS_ABOVE_GROUND));
            output(name, "2010_20", F_Strings.BUILDING_FLOORS_BELOW_GROUND, env.data.vname2id.get(F_Strings.BUILDING_FLOORS_BELOW_GROUND), collectCounts(mAllBUILDING_FLOORS_BELOW_GROUND), collectCounts(mAllFATALITY_CASUALTY_BUILDING_FLOORS_BELOW_GROUND));
            output(name, "2010_20", F_Strings.BUILDING_FLOOR_ORIGIN, env.data.vname2id.get(F_Strings.BUILDING_FLOOR_ORIGIN), collectCounts(mAllBUILDING_FLOOR_ORIGIN), collectCounts(mAllFATALITY_CASUALTY_BUILDING_FLOOR_ORIGIN));
            output(name, "2010_20", F_Strings.BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION), collectCounts(mAllBUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_BUILDING_ORIGIN_FLOOR_SIZE_DESCRIPTION));
            output(name, "2010_20", F_Strings.BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION), collectCounts(mAllBUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_BUILDING_ORIGIN_ROOM_SIZE_DESCRIPTION));
            output(name, "2010_20", F_Strings.FIRE_START_LOCATION, env.data.vname2id.get(F_Strings.FIRE_START_LOCATION), collectCounts(mAllFIRE_START_LOCATION), collectCounts(mAllFATALITY_CASUALTY_FIRE_START_LOCATION));
            output(name, "2010_20", F_Strings.FIRE_SIZE_ON_ARRIVAL, env.data.vname2id.get(F_Strings.FIRE_SIZE_ON_ARRIVAL), collectCounts(mAllFIRE_SIZE_ON_ARRIVAL), collectCounts(mAllFATALITY_CASUALTY_FIRE_SIZE_ON_ARRIVAL));
            output(name, "2010_20", F_Strings.OTHER_PROPERTY_AFFECTED_ON_ARRIVAL, env.data.vname2id.get(F_Strings.OTHER_PROPERTY_AFFECTED_ON_ARRIVAL), collectCounts(mAllOTHER_PROPERTY_AFFECTED_ON_ARRIVAL), collectCounts(mAllFATALITY_CASUALTY_OTHER_PROPERTY_AFFECTED_ON_ARRIVAL));
            output(name, "2010_20", F_Strings.BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION), collectCounts(mAllBUILDING_FIRE_DAMAGE_AREA_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_BUILDING_FIRE_DAMAGE_AREA_DESCRIPTION));
            output(name, "2010_20", F_Strings.BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION), collectCounts(mAllBUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_BUILDING_TOTAL_DAMAGE_AREA_DESCRIPTION));
            output(name, "2010_20", F_Strings.FIRE_SIZE_ON_ARRIVAL_DESCRIPTION, env.data.vname2id.get(F_Strings.FIRE_SIZE_ON_ARRIVAL_DESCRIPTION), collectCounts(mAllFIRE_SIZE_ON_ARRIVAL_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_FIRE_SIZE_ON_ARRIVAL_DESCRIPTION));
            output(name, "2010_20", F_Strings.other_property_affected_close_d, env.data.vname2id.get(F_Strings.other_property_affected_close_d), collectCounts(mAllother_property_affected_close_d), collectCounts(mAllFATALITY_CASUALTY_other_property_affected_close_d));
            output(name, "2010_20", F_Strings.spread_of_fire_d, env.data.vname2id.get(F_Strings.spread_of_fire_d), collectCounts(mAllspread_of_fire_d), collectCounts(mAllFATALITY_CASUALTY_spread_of_fire_d));
            output(name, "2010_20", F_Strings.RESPONSE_TIME, env.data.vname2id.get(F_Strings.RESPONSE_TIME), collectCounts(mAllRESPONSE_TIME), collectCounts(mAllFATALITY_CASUALTY_RESPONSE_TIME));
            output(name, "2010_20", F_Strings.RESPONSE_TIME_CODE, env.data.vname2id.get(F_Strings.RESPONSE_TIME_CODE), collectCounts(mAllRESPONSE_TIME_CODE), collectCounts(mAllFATALITY_CASUALTY_RESPONSE_TIME_CODE));
            output(name, "2010_20", F_Strings.TIME_AT_SCENE, env.data.vname2id.get(F_Strings.TIME_AT_SCENE), collectCounts(mAllTIME_AT_SCENE), collectCounts(mAllFATALITY_CASUALTY_TIME_AT_SCENE));
            output(name, "2010_20", F_Strings.TIME_AT_SCENE_CODE, env.data.vname2id.get(F_Strings.TIME_AT_SCENE_CODE), collectCounts(mAllTIME_AT_SCENE_CODE), collectCounts(mAllFATALITY_CASUALTY_TIME_AT_SCENE_CODE));
            output(name, "2010_20", F_Strings.RESCUES, env.data.vname2id.get(F_Strings.RESCUES), collectCounts(mAllRESCUES), collectCounts(mAllFATALITY_CASUALTY_RESCUES));
            output(name, "2010_20", F_Strings.EVACUATIONS, env.data.vname2id.get(F_Strings.EVACUATIONS), collectCounts(mAllEVACUATIONS), collectCounts(mAllFATALITY_CASUALTY_EVACUATIONS));
            output(name, "2010_20", F_Strings.EVACUATIONS_CODE, env.data.vname2id.get(F_Strings.EVACUATIONS_CODE), collectCounts(mAllEVACUATIONS_CODE), collectCounts(mAllFATALITY_CASUALTY_EVACUATIONS_CODE));
            output(name, "2010_20", F_Strings.BUILDING_EVACUATION_DELAY_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_EVACUATION_DELAY_DESCRIPTION), collectCounts(mAllBUILDING_EVACUATION_DELAY_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_BUILDING_EVACUATION_DELAY_DESCRIPTION));
            output(name, "2010_20", F_Strings.BUILDING_EVACUATION_TIME_DESCRIPTION, env.data.vname2id.get(F_Strings.BUILDING_EVACUATION_TIME_DESCRIPTION), collectCounts(mAllBUILDING_EVACUATION_TIME_DESCRIPTION), collectCounts(mAllFATALITY_CASUALTY_BUILDING_EVACUATION_TIME_DESCRIPTION));
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(F_Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
