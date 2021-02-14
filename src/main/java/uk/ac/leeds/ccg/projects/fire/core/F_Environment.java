/*
 * Copyright 2021 Centre for Computational Geography, University of Leeds.
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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import uk.ac.leeds.ccg.data.core.Data_Environment;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.projects.fire.data.F_Data;
import uk.ac.leeds.ccg.projects.fire.io.F_Files;
import uk.ac.leeds.ccg.generic.io.Generic_IO;
import uk.ac.leeds.ccg.generic.memory.Generic_MemoryManager;
import uk.ac.leeds.ccg.projects.fire.data.F_Data0;

/**
 * F_Environment
 * 
 * @author Andy Turner
 * @version 1.0.0
 */
public class F_Environment extends Generic_MemoryManager {

    private static final long serialVersionUID = 1L;

    public transient final Data_Environment de;
    public transient final Generic_Environment env;
    
    public int logID;
    //public final STATS19_Casualty_Handler ch;
    
    /**
     * This is the processed data with values and variable names turned into IDs.
     */
    public F_Data data;
    
    /**
     * This is the initial data loaded. Once this has been processed into IDs,
     * this is no longer used.
     */
    public F_Data0 data0;
    
    
    public F_Files files;
    
    public transient static final String EOL = System.getProperty("line.separator");
    
    public F_Environment(Data_Environment de) throws IOException, Exception {
        //Memory_Threshold = 3000000000L;
        this.de = de;
        this.env = de.env;
        Path dir = Paths.get(de.files.getDataDir().toString());
        files = new F_Files(dir);
        Path f  = files.getEnvDataFile();
        if (Files.exists(f)) {
            loadData();
        } else {
            data0 = new F_Data0(this);
            data = new F_Data(this);
        }
        logID = env.initLog(F_Strings.s_Fire);
    }

    /**
     * A method to try to ensure there is enough memory to continue.
     *
     * @return
     * @throws java.io.IOException
     */
    @Override
    public boolean checkAndMaybeFreeMemory() throws IOException {
        System.gc();
        while (getTotalFreeMemory() < Memory_Threshold) {
//            int clear = clearAllData();
//            if (clear == 0) {
//                return false;
//            }
            if (!swapSomeData()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 
     * @param hoome handleOutOfMemoryError
     * @return 
     * @throws java.io.IOException 
     */
    @Override
    public boolean swapSomeData(boolean hoome) throws IOException {
        try {
            boolean r = swapSomeData();
            checkAndMaybeFreeMemory();
            return r;
        } catch (OutOfMemoryError e) {
            if (hoome) {
                clearMemoryReserve(env);
                boolean r = swapSomeData(HOOMEF);
                initMemoryReserve(env);
                return r;
            } else {
                throw e;
            }
        }
    }

    /**
     * Currently this just tries to cache WaAS data.
     *
     * @return
     * @throws java.io.IOException
     */
    @Override
    public boolean swapSomeData() throws IOException {
        boolean r = clearSomeData();
        if (r) {
            return r;
        } else {
            System.out.println("No data to clear. Do some coding to try "
                    + "to arrange to clear something else if needs be. If the "
                    + "program fails then try providing more memory...");
            return r;
        }
    }

    public boolean clearSomeData() throws IOException {
        return data.clearSomeData();
    }

    public int clearAllData() throws IOException {
        return data.clearAllData();
    }
    
    public void cacheData() throws IOException {
        Path f = files.getEnvDataFile();
        String m = "Swap data to " + f;
        logStartTag(m);
        Generic_IO.writeObject(data, f);
        logEndTag(m);
    }

    public final void loadData() throws IOException, ClassNotFoundException {
        Path f = files.getEnvDataFile();
        String m = "loadData from " + f;
        logStartTag(m);
        data = (F_Data) Generic_IO.readObject(f);
        logEndTag(m);
    }

    /**
     * For convenience.
     * {@link Generic_Environment#logStartTag(java.lang.String, int)}
     *
     * @param s The tag name.
     */
    public final void logStartTag(String s) {
        env.logStartTag(s, logID);
    }

    /**
     * For convenience. {@link Generic_Environment#log(java.lang.String, int)}
     *
     * @param s The message to be logged.
     */
    public void log(String s) {
        env.log(s, logID);
    }

    /**
     * For convenience.
     * {@link Generic_Environment#logEndTag(java.lang.String, int)}
     *
     * @param s The tag name.
     */
    public final void logEndTag(String s) {
        env.logEndTag(s, logID);
    }
}
