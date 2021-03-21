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
package uk.ac.leeds.ccg.projects.fire.data.dwellings;

import uk.ac.leeds.ccg.projects.fire.id.F_RecordID;

public class F_Dwellings_String_Record2 extends F_Dwellings_String_Record0 {

    private static final long serialVersionUID = 1L;
             
    protected String tFSO_APPLY;
    protected String tSAFETY_SYSTEM;

    public F_Dwellings_String_Record2(F_RecordID i, String[] s) throws Exception {
        super(i);
        int n = init0(s);
        n ++;
        check(s[n]);
        tFSO_APPLY = s[n];
        n = init1(s, n);
        n ++;
        check(s[n]);
        tSAFETY_SYSTEM = s[n];
        n ++;
        check(s[n]);
        tBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT = s[n];
        n ++;
        check(s[n]);
        tOCCUPIED_NORMAL = s[n];
        init2(s, n);
    }

    /**
     * @return A String representation of this.
     */
    @Override
    public String toString() {
        String r = toString0();
        r += ", FSO_APPLY=" + tFSO_APPLY;        
        r += toString1();
        r += ", SAFETY_SYSTEM=" + tSAFETY_SYSTEM;
        r += ", BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT=" + tBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT;
        r += ", OCCUPIED_NORMAL=" + tOCCUPIED_NORMAL;
        r += toString2();
        return r;
    }
    
    public final String gettFSO_APPLY() {
        return tFSO_APPLY;
    }
    
    public final String gettSAFETY_SYSTEM() {
        return tSAFETY_SYSTEM;
    }
}
