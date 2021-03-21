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
package uk.ac.leeds.ccg.projects.fire.data;

import java.util.HashMap;
import uk.ac.leeds.ccg.projects.fire.core.F_Object;
import uk.ac.leeds.ccg.projects.fire.data.dwellings.F_Dwellings_String_Record1;
import uk.ac.leeds.ccg.projects.fire.data.dwellings.F_Dwellings_String_Record2;
import uk.ac.leeds.ccg.projects.fire.id.F_CollectionID;
import uk.ac.leeds.ccg.projects.fire.id.F_RecordID;

/**
 * F_Collection0
 * 
 * @author Andy Turner
 * @version 1.0.0
 */
public class F_Collection0 extends F_Object {

    private static final long serialVersionUID = 1L;

    public final F_CollectionID id;

    /**
     * Normal Dwellings
     */
    public final HashMap<F_RecordID, F_Dwellings_String_Record1> data1;
    
    /** 
     * Other Dwellings
     */
    public final HashMap<F_RecordID, F_Dwellings_String_Record2> data2;

    public F_Collection0(F_CollectionID i) {
        this.id = i;
        data1 = new HashMap<>();
        data2 = new HashMap<>();
    }

}
