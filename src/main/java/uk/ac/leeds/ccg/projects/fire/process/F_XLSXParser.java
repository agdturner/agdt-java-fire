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
package uk.ac.leeds.ccg.projects.fire.process;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import uk.ac.leeds.ccg.generic.io.Generic_IO;
import uk.ac.leeds.ccg.generic.util.Generic_Collections;

/**
 * Developed as an attempt to parse a Microsoft Excel files such as might be
 * obtained via an FOI request.
 *
 * @author Andy Turner
 */
public class F_XLSXParser {

    public F_XLSXParser() {
    }

    public static void main(String[] args) {
        F_XLSXParser p = new F_XLSXParser();
        p.run();
    }

    public void run() {
        HashSet<String> externalFacingMaterialsOfInterest = new HashSet<>();
        externalFacingMaterialsOfInterest.add("Aluminium Composite Materials - Aluminium Composite Materials are usually made of two thin sheets of metal with a filler material between them.");
        externalFacingMaterialsOfInterest.add("Timber/wood - Timber or wood, including any wood cladding systems (but excluding any wood based HPL which should be included in High Pressure Laminate).");
        externalFacingMaterialsOfInterest.add("High Pressure Laminate - High Pressure Laminates (HPL) are panels made of a combination of wood or paper which are then impregnated with a resin and consolidated under heat and high pressure. They are available in a wide range of colours. Care should be taken to identify the precise fire properties of the panel used as similar panels may or may not have fire retardance.");
        externalFacingMaterialsOfInterest.add("Brick Slips - Brick slips are the faces of bricks which have been cut and then attached to the building to create the appearance of a brick finish.");
        externalFacingMaterialsOfInterest.add("Plastic - Any plastic cladding or panels");

        TreeMap<Integer, Integer> numberOfExternalWallsCount = new TreeMap<>();
        HashMap<String, Integer> externalFacingMaterials = new HashMap<>();
        HashMap<String, Integer> externalFacingMaterialsDetails = new HashMap<>();
        HashMap<String, Integer> externalFacingMaterialsDetailsOfInterest = new HashMap<>();
        HashMap<String, Integer> insulation = new HashMap<>();
        HashMap<String, Integer> insulationDetails = new HashMap<>();
        HashMap<String, Integer> externalWallDetails = new HashMap<>();
        int nExternalWallsMax = 0;
        Path dataDirPath = Paths.get("C:", "Users", "agdtu", "work",
                "research", "fire", "data");
        String dirName = "MA065AZX5";
        Path inDataDirPath = Paths.get(dataDirPath.toString(), "source", dirName);
        try {
            boolean interest = false;
            List<Path> l = Files.list(inDataDirPath).collect(Collectors.toList());
            System.out.println("Number of files=" + l.size());
            Iterator<Path> ite = l.iterator();
            while (ite.hasNext()) {
                int nExternalWalls = 0;
                Path p = ite.next();
                String fn = p.getFileName().toString();
                // Read the contents of the workbook
                Workbook wb = getWorkbook(p);
                if (wb == null) {
                    System.err.println(p.toString());
                } else {
                    DataFormatter df = new DataFormatter();
                    int i = 1;
                    for (Sheet sheet : wb) {
                        OUTER2:
                        // Write out to aggregate file
                        if (i == 1) {
                            // Write values
                            for (Row row : sheet) {
                                StringBuilder sb = new StringBuilder();
                                StringBuilder sbefd = new StringBuilder();
                                int r = row.getRowNum();
                                if (r == 29) {
                                    for (Cell cell : row) {
                                        int c = cell.getColumnIndex();
                                        if (c == 0) {
                                            String s = df.formatCellValue(cell);
                                            if (s.trim().equalsIgnoreCase("External Walls")) {
                                                sb.append("\"").append(s).append("\",\"");
                                            } else {
                                                int debug = 1;
                                            }
                                        } else {
                                            String s = df.formatCellValue(cell);
                                            if (!s.isBlank()) {
                                                sb.append(s).append("\",\"");
                                            }
                                        }
                                    }
                                    sb.substring(0, sb.length() - 3);
                                }
                                OUTER:
                                if (r >= 30) {
                                    for (Cell cell : row) {
                                        int c = cell.getColumnIndex();
                                        String s = df.formatCellValue(cell).trim();
                                        if (s.isBlank()) {
                                            break OUTER2;
                                        }
                                        switch (c) {
                                            case 1:
                                                if (s.isBlank()) {
                                                    break OUTER;
                                                } else {
                                                    Generic_Collections.addToCount(externalFacingMaterials, s, 1);
                                                    sbefd.append("externalFacingMaterials=").append(s.trim());
                                                    if (externalFacingMaterialsOfInterest.contains(s)) {
                                                        interest = true;
                                                    }
                                                    sb.append(s).append("\",\"");
                                                    nExternalWalls++;
                                                }
                                                break;
                                            case 3:
                                                if (interest) {
                                                    Generic_Collections.addToCount(externalFacingMaterialsDetailsOfInterest, s, 1);
                                                }
                                                interest = false;
                                                Generic_Collections.addToCount(externalFacingMaterialsDetails, s, 1);
                                                sbefd.append(", externalFacingMaterialsDetails=").append(s.trim());
                                                sb.append(s).append("\",\"");
                                                break;
                                            case 4:
                                                Generic_Collections.addToCount(insulation, s, 1);
                                                sbefd.append(", insulation=").append(s.trim());
                                                sb.append(s).append("\",\"");
                                                break;
                                            case 5:
                                                Generic_Collections.addToCount(insulationDetails, s, 1);
                                                sbefd.append(", insulationDetails=").append(s.trim());
                                                sb.append(s).append("\"");
                                                break;
                                            default:
                                                break;
                                        }
                                    }
                                    Generic_Collections.addToCount(externalWallDetails, sbefd.toString(), 1);
                                }
                                //System.out.println(sb.toString());
                            }
                        }
                        i++;
                    }
                }
                Generic_Collections.addToCount(numberOfExternalWallsCount, nExternalWalls, 1);
                nExternalWallsMax = Math.max(nExternalWallsMax, nExternalWalls);
            }
        } catch (IOException ex) {
            Logger.getLogger(F_XLSXParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("nExternalWallsMax=" + nExternalWallsMax);
        print("numberOfExternalWallsCount", numberOfExternalWallsCount);
        print("externalFacingMaterialsDetailsOfInterest", externalFacingMaterialsDetailsOfInterest);
//        print("externalFacingMaterials", externalFacingMaterials);
//        print("externalFacingMaterialsDetails", externalFacingMaterialsDetails);
//        print("insulation", insulation);
//        print("insulationDetails", insulationDetails);
//        print("externalWallDetails", externalWallDetails);
    }

    /**
     * Prints out all things in {@code m} to std.out.
     *
     * @param <T> The type.
     * @param s The set containing values to output out.
     */
    public <K> void print(String name, Map<K, Integer> m) {
        TreeMap<Integer, HashSet<K>> sorted = new TreeMap<>(Collections.reverseOrder());
        Iterator<K> ite = m.keySet().iterator();
        while (ite.hasNext()) {
            K k = ite.next();
            Integer i = m.get(k);
            if (sorted.containsKey(i)) {
                sorted.get(i).add(k);
            } else {
                HashSet<K> s = new HashSet<>();
                s.add(k);
                sorted.put(i, s);
            }
        }
        System.out.println(name + " number of values=" + m.size());
        System.out.println("count,value");
        Iterator<Integer> ite2 = sorted.keySet().iterator();
        while (ite2.hasNext()) {
            Integer i = ite2.next();
            HashSet<K> s = sorted.get(i);
            s.stream().forEach(k -> System.out.println("" + i + ", " + k));
        }
        System.out.println("");
    }

    /**
     * Prints out all things in {@code s} to std.out.
     *
     * @param <T> The type.
     * @param s The set containing values to output out.
     */
    public <T> void print(String name, Set<T> s) {
        System.out.println(name + ", size=" + s.size());
        s.stream().forEach(i -> System.out.println(i));
    }

    public void run0() {
        PrintWriter pw0 = null;
        try {
            Path dataDirPath = Paths.get("C:", "Users", "agdtu", "work",
                    "research", "fire", "data");
            String dirName = "MA065AZX5";
            Path inDataDirPath = Paths.get(dataDirPath.toString(), "source", dirName);
            Path outDataDirPath = Paths.get(dataDirPath.toString(), "output", dirName);
            Path out0 = Paths.get(outDataDirPath.toString(), "test.csv");
            pw0 = Generic_IO.getPrintWriter(out0, false);
            int mrow = 24;
            try {
                Files.createDirectories(outDataDirPath);
            } catch (IOException ex) {
                Logger.getLogger(F_XLSXParser.class.getName()).log(Level.SEVERE, null, ex);
            }
            boolean writeheader = true;
            try {
                List<Path> l = Files.list(inDataDirPath).collect(Collectors.toList());
                Iterator<Path> ite = l.iterator();
                while (ite.hasNext()) {
                    Path p = ite.next();
                    String fn = p.getFileName().toString();
                    Path out = Paths.get(outDataDirPath.toString(), fn.substring(0, fn.length() - 5) + ".txt");
                    // Read the contents of the workbook
                    try ( PrintWriter pw = Generic_IO.getPrintWriter(out, false)) {
                        // Read the contents of the workbook
                        Workbook wb = getWorkbook(p);
                        if (wb == null) {
                            System.err.println(p.toString());
                        } else {
                            DataFormatter df = new DataFormatter();
                            int i = 1;
                            int numberOfSheets = wb.getNumberOfSheets();
                            for (Sheet sheet : wb) {
                                // Write out to text file
                                String s = "Sheet " + i + " of " + numberOfSheets + ": " + sheet.getSheetName();
                                //System.out.println(s);
                                pw.println(s);
                                for (Row row : sheet) {
                                    int r = row.getRowNum();
                                    s = "\tRow " + r;
                                    //System.out.println(s);
                                    pw.println(s);
                                    for (Cell cell : row) {
                                        s = "\t\t" + cell.getAddress().formatAsString() + ": " + df.formatCellValue(cell);
                                        //System.out.println(s + ", " + cell.getColumnIndex());
                                        pw.println(s);
                                    }
                                }
                                // Write out to aggregate file
                                if (i == 1) {
                                    // Write header
                                    if (writeheader) {
                                        for (Row row : sheet) {
                                            int r = row.getRowNum();
                                            if (r >= 2 && r < mrow) {
                                                for (Cell cell : row) {
                                                    int c = cell.getColumnIndex();
                                                    if (c == 0) {
                                                        pw0.print("\"" + df.formatCellValue(cell) + "\",");
                                                    }
                                                }
                                            }
                                            if (r == mrow) {
                                                for (Cell cell : row) {
                                                    int c = cell.getColumnIndex();
                                                    if (c == 0) {
                                                        pw0.println("\"" + df.formatCellValue(cell) + "\"");
                                                    }
                                                }
                                            }
                                        }
                                        writeheader = false;
                                    }
                                    // Write values
                                    for (Row row : sheet) {
                                        int r = row.getRowNum();
                                        if (r >= 2 && r < mrow) {
                                            for (Cell cell : row) {
                                                int c = cell.getColumnIndex();
                                                if (c == 1) {
                                                    pw0.print("\"" + df.formatCellValue(cell) + "\",");
                                                }
                                            }
                                        }
                                        if (r == mrow) {
                                            for (Cell cell : row) {
                                                int c = cell.getColumnIndex();
                                                if (c == 1) {
                                                    pw0.println("\"" + df.formatCellValue(cell) + "\"");
                                                }
                                            }
                                        }
                                    }
                                }
                                i++;
                            }
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(F_XLSXParser.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(F_XLSXParser.class.getName()).log(Level.SEVERE, null, ex);
            }
            pw0.close();
        } catch (IOException ex) {
            Logger.getLogger(F_XLSXParser.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            pw0.close();
        }
    }

    /**
     * @param p The path to the file.
     * @return Workbook loaded from file or {@code null}
     */
    public static Workbook getWorkbook(Path p) {
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(new FileInputStream(p.toFile()));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(F_XLSXParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | EncryptedDocumentException ex) {
            Logger.getLogger(F_XLSXParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return wb;
    }
    
    /**
     * @param p The path to the file.
     * @return Workbook loaded from file or {@code null}
     */
    public static HSSFWorkbook getHSSFWorkbook(Path p) {
        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(new FileInputStream(p.toFile()));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(F_XLSXParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | EncryptedDocumentException ex) {
            Logger.getLogger(F_XLSXParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return wb;
    }
}
