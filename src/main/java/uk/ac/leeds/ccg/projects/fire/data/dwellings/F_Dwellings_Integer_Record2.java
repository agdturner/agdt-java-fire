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

import uk.ac.leeds.ccg.projects.fire.core.F_Strings;
import uk.ac.leeds.ccg.projects.fire.data.F_Data;

public class F_Dwellings_Integer_Record2 extends F_Dwellings_Integer_Record0 {

    private static final long serialVersionUID = 1L;

    public Integer tFSO_APPLY;
    public Integer tSAFETY_SYSTEM;

    public F_Dwellings_Integer_Record2(F_Dwellings_String_Record2 r, F_Data d) throws Exception {
        super(r, d);
        tFSO_APPLY = d.name2ids.get(d.vname2id.get(F_Strings.FSO_APPLY)).get(r.tFSO_APPLY);
        tSAFETY_SYSTEM = d.name2ids.get(d.vname2id.get(F_Strings.SAFETY_SYSTEM)).get(r.tSAFETY_SYSTEM);
    }

    /**
     * @return A String representation of this.
     */
    @Override
    public String toString(F_Data data) {
        String r = toString0(data);
        r += ", FSO_APPLY=" + getFSO_APPLY(data);
        r += toString1(data);
        r += ", SAFETY_SYSTEM=" + getSAFETY_SYSTEM(data);
        r += ", BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT=" + getBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT(data);
        r += ", OCCUPIED_NORMAL=" + getOCCUPIED_NORMAL(data);
        r += toString2(data);
        return r;
    }

    @Override
    public String getCSVHeader() {
        return getCSVHeader0()
                + "FSO_APPLY,"
                + getCSVHeader1()
                + "SAFETY_SYSTEM,"
                + "BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT,"
                + "OCCUPIED_NORMAL,"
                + getCSVHeader2();
    }
    
    @Override
    public String toCSVString(F_Data data) {
        String r = toCSVString0(data);
        r += "\",\"" + getFSO_APPLY(data);
        r += toCSVString1(data);
        r += "\",\"" + getSAFETY_SYSTEM(data);
        r += "\",\"" + getBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT(data);
        r += "\",\"" + getOCCUPIED_NORMAL(data);
        r += toCSVString2(data);
        return r;
    }

    public String getFSO_APPLY(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.FSO_APPLY)).get(tFSO_APPLY);
    }

    public String getSAFETY_SYSTEM(F_Data data) {
        return data.id2names.get(data.vname2id.get(F_Strings.SAFETY_SYSTEM)).get(tSAFETY_SYSTEM);
    }

}
