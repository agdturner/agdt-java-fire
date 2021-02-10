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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import uk.ac.leeds.ccg.data.core.Data_Environment;
import uk.ac.leeds.ccg.data.format.Data_ReadCSV;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.projects.fire.core.F_Environment;
import uk.ac.leeds.ccg.projects.fire.io.F_Files;
import uk.ac.leeds.ccg.projects.fire.core.F_Object;
import uk.ac.leeds.ccg.projects.fire.core.F_Strings;
import uk.ac.leeds.ccg.projects.fire.data.F_Collection;
import uk.ac.leeds.ccg.projects.fire.data.F_Data;
import uk.ac.leeds.ccg.projects.fire.data.dwellings.F_Dwellings_Record;
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
                    F_Strings.s_data);
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
            boolean doLoadData = true;
            //boolean doLoadData = false;
            if (doLoadData) {
                env.data = new F_Data(env);
                data = env.data;
                loadData();
            } else {
                env.data.env = env;
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
        Data_ReadCSV reader = new Data_ReadCSV(env.de);
        Path[] vfs = new Path[6];
        vfs[0] = Paths.get(indir.toString(), "dwellings data to send 10-11.csv");
        vfs[1] = Paths.get(indir.toString(), "dwellings data to send 11-12.csv");
        vfs[2] = Paths.get(indir.toString(), "dwellings data to send 12-14.csv");
        vfs[3] = Paths.get(indir.toString(), "dwellings data to send 14-16.csv");
        vfs[4] = Paths.get(indir.toString(), "dwellings data to send 16-18.csv");
        vfs[5] = Paths.get(indir.toString(), "dwellings data to send 18-20.csv");
        int syntax = 1;
        for (Path vf : vfs) {
            F_CollectionID cID = new F_CollectionID(data.cID2recIDs.size());
            HashSet<F_RecordID> recIDs = new HashSet<>();
            data.cID2recIDs.put(cID, recIDs);
            F_Collection c = new F_Collection(cID);
            data.data.put(cID, c);
            env.log("Reading " + vf.toString());
            long lf = 0;
            try (final BufferedReader br = Generic_IO.getBufferedReader(vf)) {
                reader.setStreamTokenizer(br, syntax);
                String line = reader.readLine();    // Skip header...
                env.log(line);                      // ... but log it.
                line = reader.readLine();
                while (line != null) {
                    String[] s = line.split(",");
                    if (s.length < 1) {
                        //int debug = 1;
                        //System.out.println("Line " + lf + " = " + s + " not loaded.");
                    } else {
                        F_RecordID recID = new F_RecordID(Long.valueOf(s[0]));
                        F_Dwellings_Record rec = new F_Dwellings_Record(recID, line);
                        recIDs.add(recID);
                        c.data.put(recID, rec);
                        Generic_Collections.addToMap(data.cID2recIDs, cID, recID);
                        data.recID2cID.put(recID, cID);
                        if (lf % 1000 == 0) {
                            env.log("loaded record " + lf);
                        }
                    }
                    lf++;
                    line = reader.readLine();
                }
                data.swapCollections();
            }
        }
        env.logEndTag(m);
        env.env.closeLog(env.logID);
        env.swapData();
    }
}
