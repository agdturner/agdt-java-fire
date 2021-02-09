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
import org.apache.poi.ss.usermodel.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.EncryptedDocumentException;

/**
 *
 * @author Andy Turner
 */
public class F_Test {

    public static void main(String[] args) {
        try {
            String fn = "3 whitehall quay Leeds City Council External Wall Systems Form (2).xlsx";
            Path p = Paths.get("C:", "Users", "agdtu", "work", "research",
                    "fire", "data", "source", "MA065AZX5", fn);
            if (Files.exists(p)) {
                System.out.println(p.toString());
                
            }
            // Read the contents of the workbook
            Workbook wb = WorkbookFactory.create(new FileInputStream(p.toFile()));
            DataFormatter formatter = new DataFormatter();
            int i = 1;
            int numberOfSheets = wb.getNumberOfSheets();
            for (Sheet sheet : wb) {
                System.out.println("Sheet " + i + " of " + numberOfSheets + ": " + sheet.getSheetName());
                for (Row row : sheet) {
                    System.out.println("\tRow " + row.getRowNum());
                    for (Cell cell : row) {
                        System.out.println("\t\t" + cell.getAddress().formatAsString() + ": " + formatter.formatCellValue(cell));
                    }
                }
                
            }
        } catch (IOException | EncryptedDocumentException ex) {
            Logger.getLogger(F_Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
