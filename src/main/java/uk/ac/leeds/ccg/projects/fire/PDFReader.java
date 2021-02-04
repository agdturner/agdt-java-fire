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
package uk.ac.leeds.ccg.projects.fire;

import java.io.BufferedWriter;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSBoolean;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSNull;
import org.apache.pdfbox.cos.COSNumber;
import org.apache.pdfbox.cos.COSObject;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.graphics.PDPostScriptXObject;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import org.apache.xmpbox.XMPMetadata;
import org.apache.xmpbox.schema.AdobePDFSchema;
import org.apache.xmpbox.schema.DublinCoreSchema;
import org.apache.xmpbox.schema.XMPBasicSchema;
import org.apache.xmpbox.xml.DomXmpParser;
import org.apache.xmpbox.xml.XmpParsingException;

/**
 * This class was originally developed as an attempt to parse a PDF document.
 *
 * @author Andy Turner
 */
public class PDFReader {

    Path dataDir;
    Path inDir;
    Path outDir;
    
    /**
     * Title delimiter.
     */
    String td;

    /**
     * Item delimiter.
     */
    String id;

    public PDFReader(Path dataDir, String td, String id) {
        this.dataDir = dataDir;
        this.inDir = Paths.get(dataDir.toString(), "input");
        this.outDir = Paths.get(dataDir.toString(), "output");
        this.td = td;
        this.id = id;
    }

    public static void main(String[] args) {
        PDFReader r = new PDFReader(Paths.get("C:", "Users", "agdtu", "work",
                "research", "fire", "data"), ": ", " ");
        r.run();
    }

    public void run() {
        String fn = "9 Byron Court";
        Path pdf = Paths.get(inDir.toString(), fn + ".pdf");
        Path out = Paths.get(outDir.toString(), fn);
        try {
            Files.createDirectories(out);
        } catch (IOException ex) {
            Logger.getLogger(PDFReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<String> metadata = getMetadata(pdf);
        metadata.forEach(s -> {
            System.out.println(s);
        });

        try {
            parse1(pdf, fn);
            parse2(pdf);
            parse3(pdf, fn);
        } catch (IOException ex) {
            Logger.getLogger(PDFReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Attempt number 3.
     *
     * @param path Input file path.
     * @throws IOException
     */
    public void parse3(Path path, String fn) throws IOException {
        try ( PDDocument document = PDDocument.load(path.toFile())) {
            PDPageTree list = document.getPages();
            for (PDPage page : list) {
                Iterator<PDStream> itecs = page.getContentStreams();
                while (itecs.hasNext()) {
                    COSBase cb = itecs.next().getCOSObject().getCOSObject().getCOSObject();
                    System.out.println(getCOSBaseType(cb));
                }
                PDResources pdResources = page.getResources();
                int i = 0;
                for (COSName name : pdResources.getXObjectNames()) {
                    Iterator<COSName> ite = pdResources.getXObjectNames().iterator();
                    while (ite.hasNext()) {
                        parseCOSName(ite.next());
                    }
                    PDXObject pdo = pdResources.getXObject(name);
                    if (pdo instanceof PDImageXObject) {
                        PDImageXObject image = (PDImageXObject) pdo;
                        Path out = Paths.get(outDir.toString(), fn, "" + i + ".png");
                        ImageIO.write(image.getImage(), "png", out.toFile());
                        i++;
                    }
                    if (pdo instanceof PDFormXObject) {
                        PDFormXObject form = (PDFormXObject) pdo;
                        System.out.println(form.toString());
                        PDResources formResources = form.getResources();
                        PDXObject fro = formResources.getXObject(name);
                        System.out.println(getCOSBaseType(fro.getCOSObject().getCOSObject().getCOSObject()));
                    }
                    if (pdo instanceof PDPostScriptXObject) {
                        PDPostScriptXObject ps = (PDPostScriptXObject) pdo;
                        System.out.println(ps.toString());
                        System.out.println(getCOSBaseType(ps.getCOSObject().getCOSObject().getCOSObject()));
                    }

                }

            }
        }
    }

    /**
     * Attempt number 2.
     *
     * @param path Input file path.
     * @throws IOException
     */
    public void parse2(Path path) throws IOException {
        try ( PDDocument document = PDDocument.load(path.toFile())) {
            COSDocument cd = document.getDocument();
            System.out.println("Version" + td + cd.getVersion());
            List<COSObject> cos = cd.getObjects();
            System.out.println("Number of COSObjects" + td + cos.size());
            int i = 0;
            Iterator<COSObject> ite = cos.iterator();
            while (ite.hasNext()) {
                COSObject co = ite.next();
                //System.out.println("COSObject " + i + " " + co.toString());
                COSBase cb = co.getObject();
                //System.out.println("COSBase" + td + cb.toString());
                System.out.println("COSBaseTypeName" + td + getCOSBaseType(cb));
                i++;
            }
            PDDocumentCatalog dc = document.getDocumentCatalog();
            System.out.println("Language" + td + dc.getLanguage());
            PDDocumentNameDictionary dcnames = dc.getNames();
            if (dcnames != null) {
                System.out.println("PDDocumentNameDictionary" + td + dcnames.toString());
            }
        }
    }

    public String getCOSBaseType(COSBase cb) {
        if (cb instanceof COSArray) {
            COSArray ca = (COSArray) cb;
            Iterator<COSBase> ite = ca.iterator();
            while (ite.hasNext()) {
                COSBase cb2 = ite.next();
                System.out.println("COSBaseTypeName" + td + getCOSBaseType(cb2));
            }
            return "COSArray";
        }
        if (cb instanceof COSBoolean) {
            return "COSBoolean";
        }
        if (cb instanceof COSDictionary) {
            COSDictionary cd = (COSDictionary) cb;
            Iterator<COSName> ite = cd.keySet().iterator();
            while (ite.hasNext()) {
                COSName cn = ite.next();
                System.out.println("COSNameName" + td + cn.getName());
                System.out.println("COSNametoString()" + td + cn.toString());
                COSBase cb2 = cd.getDictionaryObject(cn);
                System.out.println("COSBaseTypeName" + td + getCOSBaseType(cb2));
            }
            return "COSDictionary";
        }
        if (cb instanceof COSDocument) {
            COSDocument cd = (COSDocument) cb;
            List<COSObject> cos = cd.getObjects();
            int n = 0;
            if (cos != null) {
                n = cos.size();
            }
            return "COSDocument N Objects = " + n;
        }
        if (cb instanceof COSName) {
            COSName cn = (COSName) cb;
            parseCOSName(cn);
            return "COSName";
        }
        if (cb instanceof COSNull) {
            COSNull cn = (COSNull) cb;
            System.out.println(cn.toString());
            return "COSNull";
        }
        if (cb instanceof COSNumber) {
            COSNumber cn = (COSNumber) cb;
            System.out.println("" + cn.floatValue());
            System.out.println("" + cn.intValue());
            System.out.println("" + cn.longValue());
            return "COSNumber";
        }
        if (cb instanceof COSString) {
            COSString cs = (COSString) cb;
            System.out.println("" + cs.getString());
            return "COSString";
        }
        if (cb instanceof COSObject) {
            COSObject co = (COSObject) cb;
            System.out.println("" + co.toString());
//            COSBase cb2 = co.getCOSObject();
//            System.out.println(getCOSBase(cb2));
            return "COSObject";
        }
        return "Unrecognised Type!";
    }

    public void parseCOSName(COSName cn) {
        String name = cn.getName();
        System.out.println("COSNameName" + td + name);
        System.out.println("COSNametoString()" + td + cn.toString());
    }

    /**
     * Attempt number 1. Th
     *
     * @param path Input file path.
     * @throws IOException
     */
    public void parse1(Path path, String fn) throws IOException {
        try ( PDDocument document = PDDocument.load(path.toFile())) {
            PDFTextStripper s = new PDFTextStripper();
            
            try ( BufferedWriter writer = Files.newBufferedWriter(
                    Paths.get(dataDir.toString(), "output", fn, "text.txt"))) {
                s.writeText(document, writer);
            }
        }
    }

    /**
     * For getting the text in PDF.
     *
     * @param path Input file path.
     * @return The text of the PDF.
     */
    public String getText(Path path) {
        String r = null;
        try ( PDDocument document = PDDocument.load(path.toFile())) {
            PDFTextStripperByArea ts = new PDFTextStripperByArea();
            //PDFTextStripper ts = new PDFTextStripper();
            /**
             * The order of the text tokens in a PDF file may not be in the same
             * as rendered.
             */
            ts.setSortByPosition(true);
            r = ts.getText(document);
        } catch (IOException ex) {
            Logger.getLogger(PDFReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    /**
     * For getting the PDF metadata.
     *
     * @param path The file path of the PDF to parse.
     */
    public ArrayList<String> getMetadata(Path path) {
        ArrayList<String> r = new ArrayList<>();
        try ( PDDocument document = PDDocument.load(path.toFile())) {
            PDDocumentCatalog catalog = document.getDocumentCatalog();
            PDMetadata meta = catalog.getMetadata();
            if (meta != null) {
                try {
                    DomXmpParser xmpParser = new DomXmpParser();
                    XMPMetadata metadata = xmpParser.parse(meta.toByteArray());
                    DublinCoreSchema dc = metadata.getDublinCoreSchema();
                    if (dc != null) {
                        r.add("Title" + td + dc.getTitle());
                        r.add("Description" + td + dc.getDescription());
                        r.add(format("Creators", dc.getCreators(), id));
                        r.add(format("Dates", format(dc.getDates()), id));
                        r.add(format("Subjects", dc.getSubjects(), id));
                    }
                    AdobePDFSchema apf = metadata.getAdobePDFSchema();
                    if (apf != null) {
                        r.add("Keywords:" + td + apf.getKeywords());
                        r.add("PDFVersion" + td + apf.getPDFVersion());
                        r.add("Producer" + td + apf.getProducer());
                    }
                    XMPBasicSchema xbs = metadata.getXMPBasicSchema();
                    if (xbs != null) {
                        r.add("CreateDate" + td + xbs.getCreateDate());
                        r.add("ModifyDate" + td + getTime(xbs.getModifyDate()));
                        r.add("CreatorTool" + td + xbs.getCreatorTool());
                    }
                } catch (XmpParsingException ex) {
                    Logger.getLogger(PDFReader.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                // The pdf doesn't contain any metadata, try to use the
                // document information instead
                PDDocumentInformation di = document.getDocumentInformation();
                if (di != null) {
                    r.add("Title" + td + di.getTitle());
                    r.add("Subject" + td + di.getSubject());
                    r.add("Author" + td + di.getAuthor());
                    r.add("Creator" + td + di.getCreator());
                    r.add("Producer" + td + di.getProducer());
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(PDFReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    /**
     * @param s title
     * @param d title-list delimiter
     * @param l list
     * @param l list delimiter
     * @return s""
     */
    private String format(String s, List<String> l, String ld) {
        String r = s + td;
        if (l != null) {
            r = l.stream().map(i -> ld + i).reduce(r, String::concat);
        }
        return r;
    }

    /**
     * For formatting.
     * @param o The Object to format.
     * @return A String representation of {@code o}
     */
    private ArrayList<String> format(List<Calendar> c) {
        ArrayList<String> r = new ArrayList<>();
        if (c != null) {
            c.forEach(i -> {
                r.add(getTime(i));
            });
        }
        return r;
    }
    
    /**
     * @param c The Calendar to represent the time as a String.
     * @return A String representation of the time.
     */
    public String getTime(Calendar c) {
        return DateFormat.getDateInstance().format(c.getTime());
    }
}
