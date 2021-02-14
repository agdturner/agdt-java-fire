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
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
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
public class F_Data0 extends F_Object {

    private static final long serialVersionUID = 1L;

    public final Generic_FileStore fs;

    /**
     * The main data store. Keys are Collection IDs.
     */
    public Map<F_CollectionID, F_Collection0> data;

    /**
     * The main data store. Keys are Collection IDs.
     */
    public Map<F_CollectionID, Set<F_RecordID>> cID2recIDs;
    
    /**
     * The main data store. Keys are Collection IDs.
     */
    public Map<F_RecordID, F_CollectionID> recID2cID;
    
    /**
     *
     * @param cid
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public F_Collection0 getCollection(F_CollectionID cid)
            throws IOException, ClassNotFoundException {
        F_Collection0 r = data.get(cid);
        if (r == null) {
            r = loadCollection(cid);
            data.put(cid, r);
        }
        return r;
    }

    public void clearCollection(F_CollectionID cid) {
        data.put(cid, null);
    }

    public F_Data0(F_Environment env) throws IOException, Exception {
        super(env);
        String name = "Collections0";
        Path dir = env.files.getGeneratedDir();
        Path p = Paths.get(dir.toString(), name);
        if (Files.exists(p)) {
            fs = new Generic_FileStore(p);
        } else {
            short n = 100;
            fs = new Generic_FileStore(p.getParent(), name, n);
        }
        data = new HashMap<>();
        cID2recIDs = new HashMap<>();
        recID2cID = new HashMap<>();
    }

    public boolean clearSomeData() throws IOException {
        Iterator<F_CollectionID> ite = data.keySet().iterator();
        while (ite.hasNext()) {
            F_CollectionID cid = ite.next();
            F_Collection0 c = data.get(cid);
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
            F_Collection0 c = data.get(cid);
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
    public void cacheCollection(F_CollectionID cid, F_Collection0 c)
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
    public F_Collection0 loadCollection(F_CollectionID cid) throws IOException,
            ClassNotFoundException {
        F_Collection0 r = (F_Collection0) load(getCollectionPath(cid));
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
                F_Collection0 c = data.get(i);
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
