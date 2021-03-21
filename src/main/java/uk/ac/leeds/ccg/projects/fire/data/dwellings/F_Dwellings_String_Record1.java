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

public class F_Dwellings_String_Record1 extends F_Dwellings_String_Record0 {

    private static final long serialVersionUID = 1L;

    protected String tOCCUPANCY_TYPE;
    protected String tWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT;
    protected String tALARM_SYSTEM;
    protected String tALARM_SYSTEM_TYPE;
    protected String tALARM_REASON_FOR_POOR_OUTCOME;

    public F_Dwellings_String_Record1(F_RecordID i, String[] s) throws Exception {
        super(i);
        int n = init0(s);
        n = init1(s, n);
        n ++;
        check(s[n]);
        tBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT = s[n];
        n ++;
        check(s[n]);
        tOCCUPANCY_TYPE = s[n];
        n ++;
        check(s[n]);
        tOCCUPIED_NORMAL = s[n];
        n ++;
        check(s[n]);
        tWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT = s[n];
        n ++;
        check(s[n]);
        tALARM_SYSTEM = s[n];
        n ++;
        check(s[n]);
        tALARM_SYSTEM_TYPE = s[n];
        n ++;
        check(s[n]);
        tALARM_REASON_FOR_POOR_OUTCOME = s[n];
        init2(s, n);
    }

    
    
    /**
     * @return A String representation of this.
     */
    @Override
    public String toString() {
        String r = toString0();
        r += toString1();
        r += ", BUILDING_OCCUPIED_AT_TIME_OF_INCIDENT=" + tBUILDING_OCCUPIED_AT_TIME_OF_INCIDENT;
        r += ", OCCUPANCY_TYPE=" + tOCCUPANCY_TYPE;
        r += ", OCCUPIED_NORMAL=" + tOCCUPIED_NORMAL;
        r += ", WERE_ACTIVE_SAFETY_SYSTEMS_PRESENT=" + tWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT;
        r += ", ALARM_SYSTEM=" + tALARM_SYSTEM;
        r += ", ALARM_SYSTEM_TYPE=" + tALARM_SYSTEM_TYPE;
        r += ", ALARM_REASON_FOR_POOR_OUTCOME=" + tALARM_REASON_FOR_POOR_OUTCOME;
        r += toString2();
        return r;
    }
    
    public final String gettOCCUPANCY_TYPE() {
        return tOCCUPANCY_TYPE;
    }

    public final String gettWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT() {
        return tWERE_ACTIVE_SAFETY_SYSTEMS_PRESENT;
    }

    public final String gettALARM_SYSTEM() {
        return tALARM_SYSTEM;
    }

    public final String gettALARM_SYSTEM_TYPE() {
        return tALARM_SYSTEM_TYPE;
    }

    public final String gettALARM_REASON_FOR_POOR_OUTCOME() {
        return tALARM_REASON_FOR_POOR_OUTCOME;
    }
}
