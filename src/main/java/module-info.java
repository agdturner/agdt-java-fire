module uk.ac.leeds.ccg.projects.fire {
    //requires transitive java.logging;
    requires java.desktop;
    requires org.apache.pdfbox;
    requires org.apache.fontbox;
    requires org.apache.xmpbox;
    requires transitive uk.ac.leeds.ccg.generic;
    requires transitive uk.ac.leeds.ccg.math;
    requires transitive uk.ac.leeds.ccg.data;
    requires transitive org.apache.poi.poi;
    requires transitive org.apache.poi.ooxml;
    requires transitive org.apache.poi.ooxml.schemas;
    
    exports uk.ac.leeds.ccg.projects.fire.core;
    exports uk.ac.leeds.ccg.projects.fire.data;
    exports uk.ac.leeds.ccg.projects.fire.data.dwellings;
    exports uk.ac.leeds.ccg.projects.fire.data.ehs;
    exports uk.ac.leeds.ccg.projects.fire.id;
    exports uk.ac.leeds.ccg.projects.fire.io;
    exports uk.ac.leeds.ccg.projects.fire.process;
    
}