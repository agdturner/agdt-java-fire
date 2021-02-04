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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.pdfbox.contentstream.operator.Operator;
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
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.graphics.PDPostScriptXObject;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

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
        String tag0 = "Metadata";
        System.out.println(getStartTag(tag0));
        print(getMetadata(pdf));
        System.out.println(getEndTag(tag0));
        tag0 = "Text";
        System.out.println(getStartTag(tag0));
        System.out.println(getText(pdf));
        System.out.println(getEndTag(tag0));
        try {
            //writeTextToFile(pdf, fn, "text.txt");
            //if (false) {
            if (true) {
                print(parse(pdf, fn));
            }
        } catch (IOException ex) {
            Logger.getLogger(PDFReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Print each element of {@code l} to a line of std.out.
     *
     * @param l
     */
    public void print(Collection<String> l) {
        l.forEach(s -> {
            System.out.println(s);
        });
    }

    /**
     * Attempt number 3. For each page the content stream and resources are
     * explored. Images are written out as PNG.
     *
     * @param path Input file path.
     * @throws IOException
     */
    public ArrayList<String> parse(Path path, String fn) throws IOException {
        ArrayList<String> r = new ArrayList<>();
        try ( PDDocument pdf = PDDocument.load(path.toFile())) {
            // Decrypt
            if (pdf.isEncrypted()) {
                pdf.setAllSecurityToBeRemoved(true);
            }
            // PDDocument
            r.add("Number of pages" + td + pdf.getNumberOfPages());
            r.add("Version" + td + pdf.getVersion());
            // COSDocument
            COSDocument doc = pdf.getDocument();
            r.add("Document Version" + td + doc.getVersion());
            List<COSObject> cos = doc.getObjects();
            r.add("Document Number of COSObjects" + td + cos.size());
            int i = 0;
            Iterator<COSObject> ite = cos.iterator();
            while (ite.hasNext()) {
                COSObject co = ite.next();
                long on = co.getObjectNumber();
                //System.out.println("COSObject " + i + " " + co.toString());
                COSBase cb = co.getObject();
                //System.out.println("COSBase" + td + cb.toString());
                String tag0 = "Document COSObjectNumber" + id + on;
                r.add(getStartTag(tag0));
                parseCOSBase(cb, r);
                r.add(getEndTag(tag0));
                i++;
            }
            // DocumentCatalog
            PDDocumentCatalog dc = pdf.getDocumentCatalog();
            r.add("Document Catalog Version" + td + dc.getVersion());
            r.add("Document Catalog Language" + td + dc.getLanguage());
            PDAcroForm af = dc.getAcroForm();
            if (af != null) {
                List<PDField> fs = af.getFields();
                r.add(format("Document Catalog AcroForm Fields", format(fs)));
                PDDocumentNameDictionary dcnames = dc.getNames();
                if (dcnames != null) {
                    r.add("PDDocumentNameDictionary" + td + dcnames.toString());
                }
            }
            // Go through each page.
            String tagp = "Pages";
            r.add(getStartTag(tagp));
            int p = 1;
            PDPageTree list = pdf.getPages();
            for (PDPage page : list) {
                String tag0 = "Page" + id + p;
                r.add(getStartTag(tag0));
                // PDFStreamParser
                String tag1 = "PDFStreamParser";
                r.add(getStartTag(tag1));
                PDFStreamParser parser = new PDFStreamParser(page);
                parser.parse();
                List<Object> tokens = parser.getTokens();
                List<COSBase> base = new ArrayList<>();
                HashSet<String> operatorNames = new HashSet<>();
                for (Object token : tokens) {
                    if (token instanceof COSObject) {
                        base.add(((COSObject) token).getObject());
                    } else if (token instanceof Operator) {
                        Operator op = (Operator) token;
                        processOperator(op, base, operatorNames);

                        COSDictionary ip = op.getImageParameters();
                        if (ip != null) {
                            System.out.println(ip.toString());
                            Collection<COSBase> c = ip.getValues();
                            System.out.println("ImageParameter values size" + td + c.size());
                        }
                        byte[] imd = op.getImageData();
                        if (imd != null) {
                            System.out.println("imd length" + td + imd.length);
                        }
                    } else {
                        base.add((COSBase) token);
                    }
                    token = parser.parseNextToken();
                }
                // print(operatorNames);
                r.add(getEndTag(tag1));
                // Content Streams
                r.add(getStartTag(tag0));
                tag1 = "Content Streams";
                r.add(getStartTag(tag1));
                Iterator<PDStream> itecs = page.getContentStreams();
                while (itecs.hasNext()) {
                    COSBase cb = itecs.next().getCOSObject().getCOSObject().getCOSObject();
                    parseCOSBase(cb, r);
                }
                r.add(getEndTag(tag1));
                // PDResources
                r.add(getStartTag(tag0));
                tag1 = "PDResources";
                r.add(getStartTag(tag1));
                PDResources pr = page.getResources();
                int j = 0;
                for (COSName name : pr.getXObjectNames()) {
                    Iterator<COSName> xoite = pr.getXObjectNames().iterator();
                    while (xoite.hasNext()) {
                        parseCOSName(xoite.next(), r);
                    }
                    PDXObject o = pr.getXObject(name);
                    if (o instanceof PDImageXObject) {
                        PDImageXObject image = (PDImageXObject) o;
                        System.out.println("PDImageXObject" + td + image.toString());
                        Path out = Paths.get(outDir.toString(), fn, "" + j + ".png");
                        ImageIO.write(image.getImage(), "png", out.toFile());
                        j++;
                    }
                    if (o instanceof PDFormXObject) {
                        PDFormXObject form = (PDFormXObject) o;
                        System.out.println("PDFormXObject" + td + form.toString());
                        PDResources formResources = form.getResources();
                        PDXObject fro = formResources.getXObject(name);
                        parseCOSBase(fro.getCOSObject().getCOSObject().getCOSObject(), r);
                    }
                    if (o instanceof PDPostScriptXObject) {
                        PDPostScriptXObject ps = (PDPostScriptXObject) o;
                        System.out.println("PDPostScriptXObject" + td + ps.toString());
                        parseCOSBase(ps.getCOSObject().getCOSObject().getCOSObject(), r);
                    }
                }
                r.add(getEndTag(tag0));
            }
            r.add(getEndTag(tagp));

        }
        return r;
    }

    /**
     * https://pdfbox.apache.org/docs/2.0.13/javadocs/org/apache/pdfbox/contentstream/operator/class-use/OperatorProcessor.html
     *
     * @param op
     * @param base
     * @param l
     */
    public void processOperator(Operator op, List<COSBase> base, HashSet<String> l) {
        String name = op.getName();
        l.add(name);
        switch (name) {
            /**
             * Concatenate cm: Concatenate matrix to current transformation
             * matrix.
             */
            case "cm":
                break;
            /**
             * Save q: Save the graphics state.
             */
            case "q":
                break;
            /**
             * SetNonStrokingDeviceRGBColor rg: Set the non-stroking colour
             * space to DeviceRGB and set the colour to use for non-stroking
             * operations.
             */
            case "rg":
                break;
            /**
             * MoveTo m Begins a new subpath.
             */
            case "m":
                break;
            /**
             * LineTo l Append straight line segment to path.
             */
            case "l":
                break;
            /**
             * ClosePath h Close the path.
             */
            case "h":
                break;
            /**
             * FillEvenOddRule f* Fill path using even odd rule.
             */
            case "f*":
                break;
            /**
             * Restore Q: Restore the graphics state.
             */
            case "Q":
                break;
            /**
             * FillNonZeroRule f Fill path using non zero winding rule.
             */
            case "f":
                break;
            /**
             * CurveTo c Append curved segment to path.
             * https://pdfbox.apache.org/docs/2.0.13/javadocs/org/apache/pdfbox/contentstream/operator/graphics/CurveTo.html
             */
            case "c":
                break;
            /**
             * ClipEvenOddRule W* Set clipping path using even odd rule.
             * https://pdfbox.apache.org/docs/2.0.13/javadocs/org/apache/pdfbox/contentstream/operator/graphics/ClipEvenOddRule.html
             */
            case "W*":
                break;
            /**
             * EndPath n End the path.
             * https://pdfbox.apache.org/docs/2.0.13/javadocs/org/apache/pdfbox/contentstream/operator/graphics/EndPath.html
             */
            case "n":
                break;
            /**
             * DrawObject Do: Draws an XObject.
             */
            case "Do":
                break;
            default:
                System.out.println("Unrecognised Operator" + td + op.toString());
                break;
        }
    }

    /**
     * For parsing {@code cb}.
     * @param cb The COSBase to parse.
     * @param l The list to add parsed information to.
     */
    public void parseCOSBase(COSBase cb, ArrayList<String> l) {
        String tag0;
        if (cb instanceof COSArray) {
            COSArray ca = (COSArray) cb;
            tag0 = "COSArray";
            l.add(getStartTag(tag0));
            Iterator<COSBase> ite = ca.iterator();
            int i = 0;
            while (ite.hasNext()) {
                COSBase cb2 = ite.next();
                String tag1 = "COSArray" + td + "item " + i;
                l.add(getStartTag(tag1));
                parseCOSBase(cb2, l);
                l.add(getEndTag(tag1));
                i++;
            }
        } else if (cb instanceof COSBoolean) {
            COSBoolean b = (COSBoolean) cb;
            tag0 = "COSBoolean";
            l.add(getStartTag(tag0));
            //l.add(Boolean.toString(b.getValue()));
            l.add(b.toString());
        } else if (cb instanceof COSDictionary) {
            COSDictionary cd = (COSDictionary) cb;
            tag0 = "COSDictionary" + td + cd.toString() + id + "size=" + cd.size();
            int i = 0;
            Iterator<COSName> ite = cd.keySet().iterator();
            while (ite.hasNext()) {
                COSName cn = ite.next();
                String tag1 = "COSName" + td + " item " + i;
                l.add(getStartTag(tag1));
                l.add("COSNameName" + td + cn.getName());
                l.add("COSNametoString()" + td + cn.toString());
                parseCOSBase(cd.getDictionaryObject(cn), l);
                l.add(getEndTag(tag1));
                i++;
            }
        } else if (cb instanceof COSDocument) {
            COSDocument cd = (COSDocument) cb;
            List<COSObject> cos = cd.getObjects();
            int n = 0;
            if (cos != null) {
                n = cos.size();
            }
            tag0 = "COSDocument N Objects = " + n;
            if (cos != null) {
                l.add(getStartTag(tag0));
                l.add("Nested Document with " + cos.size() + " Objects to parse!");
//                int i = 0;
//                Iterator<COSObject> ite = cos.iterator();
//                while (ite.hasNext()) {
//                    l.add("...");
//                    i++;
//                }
            }
        } else if (cb instanceof COSName) {
            COSName cn = (COSName) cb;
            tag0 = "COSName";
            l.add(getStartTag(tag0));
            parseCOSName(cn, l);
        } else if (cb instanceof COSNull) {
            COSNull cn = (COSNull) cb;
            tag0 = "COSNull";
            l.add(getStartTag(tag0));
            l.add("" + cn.toString());
        } else if (cb instanceof COSNumber) {
            COSNumber cn = (COSNumber) cb;
            tag0 = "COSNumber";
            l.add(getStartTag(tag0));
            l.add("" + cn.floatValue());
            //l.add("" + cn.intValue());
            //l.add("" + cn.longValue());
        } else if (cb instanceof COSString) {
            COSString cs = (COSString) cb;
            tag0 = "COSString";
            l.add(getStartTag(tag0));
            l.add("String" + td + cs.getString());
        } else if (cb instanceof COSObject) {
            COSObject co = (COSObject) cb;
            tag0 = "COSObject";
            l.add(getStartTag(tag0));
            l.add("String" + td + co.toString());
            //parseCOSBase(co.getCOSObject(), l); // Causes StackOverflowException
        } else {
            tag0 = "Unrecognised Type";
            l.add(getStartTag(tag0));
        }
        l.add(getEndTag(tag0));
    }

    public void parseCOSName(COSName cn, ArrayList<String> l) {
        l.add("Name" + td + cn.getName());
        l.add("String" + td + cn.toString());
    }

    /**
     * Attempt number 1. Write all the text to a file.
     *
     * @param path Input file path.
     * @throws IOException
     */
    public void writeTextToFile(Path path, String fn, String fn2) throws IOException {
        try ( PDDocument pdf = PDDocument.load(path.toFile())) {
            PDFTextStripper s = new PDFTextStripper();
            try ( BufferedWriter writer = Files.newBufferedWriter(
                    Paths.get(dataDir.toString(), "output", fn, fn2))) {
                s.writeText(pdf, writer);
            }
        }
    }

    /**
     * XML Start tag.
     *
     * @param s Tag.
     * @return {@code "<" + s + ">"}
     */
    public String getStartTag(String s) {
        return "<" + s + ">";
    }

    /**
     * XML End tag.
     *
     * @param s Tag.
     * @return {@code "</" + s + ">"}
     */
    public String getEndTag(String s) {
        return "</" + s + ">";
    }

    /**
     * For getting the text in PDF.
     *
     * @param path Input file path.
     * @return The text of the PDF.
     */
    public String getText(Path path) {
        String r = null;
        try ( PDDocument pdf = PDDocument.load(path.toFile())) {
            //PDFTextStripperByArea ts = new PDFTextStripperByArea();
            PDFTextStripper ts = new PDFTextStripper();
            /**
             * The render order may not be from top to bottom.
             */
            ts.setSortByPosition(true);
            r = ts.getText(pdf);
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
                        r.add(format("Creators", dc.getCreators()));
                        r.add(format("Dates", format(dc.getDates())));
                        r.add(format("Subjects", dc.getSubjects()));
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
    private String format(String s, List<String> l) {
        String r = s + td;
        if (l != null) {
            r = l.stream().map(i -> id + i).reduce(r, String::concat);
        }
        return r;
    }

    /**
     * For formatting.
     *
     * @param o The Object to format.
     * @return A String representation of {@code o}
     */
    private <T> ArrayList<String> format(List<T> c) {
        ArrayList<String> r = new ArrayList<>();
        if (c != null) {
            c.forEach(i -> {
                if (i instanceof Calendar) {
                    r.add("PDDate" + td + getTime((Calendar) i));
                } else if (i instanceof PDField) {
                    r.add("PDField" + td + ((PDField) i).getValueAsString());
                } else {
                    r.add("Unrecognised Type");
                }
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
