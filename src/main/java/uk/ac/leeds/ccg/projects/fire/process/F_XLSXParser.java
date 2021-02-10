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
import org.apache.poi.ss.usermodel.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.apache.poi.EncryptedDocumentException;
import uk.ac.leeds.ccg.generic.io.Generic_IO;

/**
 * Developed as an attempt to parse a Microsoft Excel files such as might be
 * obtained via an FOI request.
 *
 * @author Andy Turner
 */
public class F_XLSXParser {

    public static void main(String[] args) {
        PrintWriter pw0 = null;
        try {
            Path dataDirPath = Paths.get("C:", "Users", "agdtu", "work", "research",
                    "fire", "data");
            String dirName = "MA065AZX5";
            Path inDataDirPath = Paths.get(dataDirPath.toString(), "source", dirName);
            Path outDataDirPath = Paths.get(dataDirPath.toString(), "output", dirName);
            Path out0 = Paths.get(outDataDirPath.toString(), "test.csv");
            pw0 = Generic_IO.getPrintWriter(out0, false);
//            pw0.println(""
//                    + "\"Building name/number\","
//                    + "\"Street\","
//                    + "\"City/Town\","
//                    + "\"Postcode\","
//                    + "\"Is this building 18m or higher?\"");
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
                        Workbook wb = null;
                        try {
                            wb = WorkbookFactory.create(new FileInputStream(p.toFile()));
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(F_XLSXParser.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException | EncryptedDocumentException ex) {
                            Logger.getLogger(F_XLSXParser.class.getName()).log(Level.SEVERE, null, ex);
                        }
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
}
