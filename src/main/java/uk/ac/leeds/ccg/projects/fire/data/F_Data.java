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
package uk.ac.leeds.ccg.projects.fire.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import uk.ac.leeds.ccg.projects.fire.core.F_Environment;
import uk.ac.leeds.ccg.projects.fire.core.F_Object;
import uk.ac.leeds.ccg.projects.fire.core.F_Strings;
import uk.ac.leeds.ccg.projects.fire.id.F_CollectionID;
import uk.ac.leeds.ccg.projects.fire.id.F_RecordID;
import uk.ac.leeds.ccg.generic.io.Generic_FileStore;
import uk.ac.leeds.ccg.generic.io.Generic_IO;

/**
 * F_Data
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class F_Data extends F_Object {

    private static final long serialVersionUID = 1L;

    public final Generic_FileStore fs;

    /**
     * The main data store. Keys are Collection IDs.
     */
    public Map<F_CollectionID, F_Collection> data;

    /**
     * The main data store. Keys are Collection IDs.
     */
    public Map<F_CollectionID, Set<F_RecordID>> cID2recIDs;

    /**
     * The main data store. Keys are Collection IDs.
     */
    public Map<F_RecordID, F_CollectionID> recID2cID;

    /**
     * For looking up the variable name id from the variable name.
     */
    public HashMap<String, Integer> vname2id;

    /**
     * For looking up the variable name from the variable name id.
     */
    public HashMap<Integer, String> id2vname;

    /**
     * Keys are variable name ids. Keys of values are names of variable values,
     * values of values are variable value ids.
     */
    public HashMap<Integer, HashMap<String, Integer>> name2ids;

    /**
     * Keys are variable name ids. Keys of values are names of variable values,
     * values of values are variable value ids.
     */
    public HashMap<Integer, Map<Integer, String>> id2names;

    /**
     *
     * @param cid
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public F_Collection getCollection(F_CollectionID cid)
            throws IOException, ClassNotFoundException {
        F_Collection r = data.get(cid);
        if (r == null) {
            r = loadCollection(cid);
            data.put(cid, r);
        }
        return r;
    }

    public void clearCollection(F_CollectionID cid) {
        data.put(cid, null);
    }

    public F_Data(F_Environment env) throws IOException, Exception {
        super(env);
        String name = "Collections";
        Path dir = env.files.getGeneratedDir();
        Path p = Paths.get(dir.toString(), name);
        if (Files.exists(p)) {
            fs = new Generic_FileStore(p);
        } else {
            short n = 100;
            fs = new Generic_FileStore(p.getParent(), name, n);
        }
    }
        
    public F_Data(F_Environment env, int startYear, int endYear) 
            throws IOException, Exception {
        super(env);
        String name = "Collections";
        Path dir = env.files.getGeneratedDir();
        Path p = Paths.get(dir.toString(), name);
        if (Files.exists(p)) {
            fs = new Generic_FileStore(p);
        } else {
            short n = 100;
            fs = new Generic_FileStore(p.getParent(), name, n);
        }
        data = new TreeMap<>();
        cID2recIDs = new HashMap<>();
        recID2cID = new HashMap<>();
        vname2id = new HashMap<>();
        id2vname = new HashMap<>();
        vname2id.put(F_Strings.FINANCIAL_YEAR, 0);
        id2vname.put(0, F_Strings.FINANCIAL_YEAR);
        name2ids = new HashMap<>();
        id2names = new HashMap<>();
        HashMap<String, Integer> name2id = new HashMap<>();
        TreeMap<Integer, String> id2name = new TreeMap<>();
        for (int year = startYear; year <= endYear; year ++) {
            int id = name2id.size();
            String yearName = "" + year + "/" + (year - 1999);
            name2id.put(yearName, id);
            id2name.put(id, yearName);
            F_CollectionID cid = new F_CollectionID(id);
            data.put(cid, new F_Collection(cid));
            cID2recIDs.put(cid, new HashSet<>());
        }
        name2ids.put(0, name2id);
        id2names.put(0, id2name);
    }

    public boolean clearSomeData() throws IOException {
        Iterator<F_CollectionID> ite = data.keySet().iterator();
        while (ite.hasNext()) {
            F_CollectionID cid = ite.next();
            F_Collection c = data.get(cid);
            cacheCollection(cid, c);
            data.put(cid, null);
            return true;
        }
        return false;
    }

    public int clearAllData() throws IOException {
        int r = 0;
        Iterator<F_CollectionID> ite = data.keySet().iterator();
        while (ite.hasNext()) {
            F_CollectionID cid = ite.next();
            F_Collection c = data.get(cid);
            cacheCollection(cid, c);
            data.put(cid, null);
            r++;
        }
        return r;
    }

    /**
     *
     * @param cid the F_CollectionID
     * @param c the F_Collection0
     * @throws java.io.IOException
     */
    public void cacheCollection(F_CollectionID cid, F_Collection c)
            throws IOException {
        cache(getCollectionPath(cid), c);
    }

    public Path getCollectionPath(F_CollectionID cid) throws IOException {
        return Paths.get(fs.getPath(cid.id).toString(), F_Strings.s_F
                + cid.id + F_Strings.symbol_dot + F_Strings.s_dat);
    }

    /**
     *
     * @param cid The F_CollectionID
     * @return
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    public F_Collection loadCollection(F_CollectionID cid) throws IOException,
            ClassNotFoundException {
        F_Collection r = (F_Collection) load(getCollectionPath(cid));
        r.env = env;
        return r;
    }

    /**
     *
     * @param f the Path to load from.
     * @return
     * @throws java.io.IOException If encountered.
     * @throws java.lang.ClassNotFoundException If encountered.
     */
    protected Object load(Path f) throws IOException, ClassNotFoundException {
        String m = "load " + f.toString();
        env.logStartTag(m);
        Object r = Generic_IO.readObject(f);
        env.logEndTag(m);
        return r;
    }

    /**
     *
     * @throws java.io.IOException
     */
    public void swapCollections() throws IOException {
        data.keySet().stream().forEach(i -> {
            try {
                F_Collection c = data.get(i);
                if (c != null) {
                    cacheCollection(i, data.get(i));
                    data.put(i, null);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        });
//        Iterator<F_CollectionID> ite = data.keySet().iterator();
//        while (ite.hasNext()) {
//            F_CollectionID cid = ite.next();
//            F_Collection0 c = data.get(cid);
//            cacheCollection(cid, c);
//            data.put(cid, null);
//        }
    }

    /**
     *
     * @param f the value of cf
     * @param o the value of o
     * @throws java.io.IOException If encountered.
     */
    protected void cache(Path f, Object o) throws IOException {
        String m = "cache " + f.toString();
        env.logStartTag(m);
        Files.createDirectories(f.getParent());
        Generic_IO.writeObject(o, f);
        env.logEndTag(m);
    }

}
