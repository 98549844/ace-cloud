package com.ace.utilities;

import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.*;
import com.spire.pdf.FileFormat;
import com.spire.pdf.PdfDocument;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.ace.constants.constant.PDF;

/**
 * @Classname: PdfUtil
 * @Date: 2023/9/26 上午 09:55
 * @Author: kalam_au
 * @Description:
 */


public class PdfUtil {
    private static final Logger log = LogManager.getLogger(PdfUtil.class.getName());

    public static void main(String[] args) throws IOException {
        PdfUtil pdfUtil = new PdfUtil();
        String p = "C:\\Users\\Garlam.Au\\IdeaProjects\\ace-cloud\\ace-utilities\\src\\main\\resources\\file\\images\\img.png";
        String p2 = "C:\\Users\\Garlam.Au\\IdeaProjects\\ace-cloud\\ace-utilities\\src\\main\\resources\\file\\images\\img1.png";
        List<String> ls = new ArrayList<>();
        ls.add(p);
        ls.add(p2);

        String dest = "C:\\Users\\Garlam.Au\\IdeaProjects\\ace-cloud\\ace-utilities\\src\\main\\resources\\file\\images\\aaa.pdf";
        PdfUtil.toPdf(ls, dest);

        //jpgsMergeToPdf(p, dest);
        //try {
        //    List<InputStream> pdfs = new LinkedList<>();
        //    pdfs.add(new FileInputStream("C:\\Users\\Garlam.Au\\IdeaProjects\\ace\\src\\main\\java\\com\\ace\\utils\\PdfUtil-Sample.pdf"));
        //    pdfs.add(new FileInputStream("C:\\Users\\Garlam.Au\\IdeaProjects\\ace\\src\\main\\java\\com\\ace\\utils\\PdfUtil-Sample1.pdf"));
        //    pdfs.add(new FileInputStream("C:\\Users\\Garlam.Au\\IdeaProjects\\ace\\src\\main\\java\\com\\ace\\utils\\PdfUtil-Sample2.pdf"));
        //    OutputStream output = new FileOutputStream("C:\\ace\\misc\\merge.pdf");
        //    pdfUtil.concatPDFs(pdfs, output, true);
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}
    }

    /** pdf转excel
     * @param filePath
     * @param outputExcel
     */
    public static void toExcel(String filePath, String outputExcel){
        PdfDocument pdfDocument = new PdfDocument();
        pdfDocument.loadFromFile(filePath);
        pdfDocument.saveToFile(outputExcel, FileFormat.XLSX);
    }

    /**
     * https://blog.csdn.net/ThinkPet/article/details/131256428
     * txt文本文件  转pdf文件
     *
     * @param textPath F:/data/te616.txt
     * @param pdfPath  F:/data/aet618.pdf
     * @throws com.itextpdf.text.DocumentException
     * @throws IOException
     */
    public static void textToPdf(String textPath, String pdfPath) throws com.itextpdf.text.DocumentException, IOException {
        com.itextpdf.text.Document doc = new com.itextpdf.text.Document();
        OutputStream os = new FileOutputStream(pdfPath);
        com.itextpdf.text.pdf.PdfWriter.getInstance(doc, os);
        doc.open();
        //指定 使用内置的中文字体
        com.itextpdf.text.pdf.BaseFont baseFont = com.itextpdf.text.pdf.BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", com.itextpdf.text.pdf.BaseFont.EMBEDDED);
        Font font = new Font(baseFont, 12, Font.NORMAL);
        //指定输出编码为UTF-8
        InputStreamReader isr = new InputStreamReader(new FileInputStream(textPath), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        String str;
        while ((str = br.readLine()) != null) {
            doc.add(new Paragraph(str, font));
        }
        isr.close();
        br.close();
        doc.close();
    }


    public static String readPdfBoxV3(String filePath) throws IOException {
        // 1、加载指定PDF文档
        PDDocument document = org.apache.pdfbox.Loader.loadPDF(new File(filePath));
        // 2、创建文本提取对象
        PDFTextStripper stripper = new PDFTextStripper();
        // 3、获取指定页面的文本内容
        String text = stripper.getText(document);
        System.out.println("获取文本内容: " + text);
        // 4、关闭
        document.close();
        return text;
    }

    /**
     * 读取pdf文件的内容
     *
     * @param filePath
     * @return String
     */
    public static String readItextPdfv5(String filePath) {
        StringBuilder result = new StringBuilder();
        try {
            FileInputStream fis = new FileInputStream(filePath);
            com.itextpdf.text.pdf.PdfReader pdfReader = new com.itextpdf.text.pdf.PdfReader(fis);

            int countPage = pdfReader.getNumberOfPages();
            for (int i = 1; i <= countPage; i++) {
                result.append(PdfTextExtractor.getTextFromPage(pdfReader, i));
                pdfReader.releasePage(i);
            }
            pdfReader.close(); // 关闭 PdfReader 对象
            fis.close(); // 关闭 FileInputStream 对象
        } catch (IOException e) {
            log.error("Error reading PDF file: ", e);
            e.printStackTrace();
        }
        return result.toString();
    }

    public static String readItextPdfv7(String filePath) {
        StringBuilder result = new StringBuilder();
        try {
            FileInputStream fis = new FileInputStream(filePath);
            com.itextpdf.kernel.pdf.PdfReader reader = new com.itextpdf.kernel.pdf.PdfReader(fis);
            com.itextpdf.kernel.pdf.PdfDocument pdfDocument = new com.itextpdf.kernel.pdf.PdfDocument(reader);

            int pageSize = pdfDocument.getNumberOfPages();
            for (int pageNum = 1; pageNum <= pageSize; pageNum++) {
                result.append(com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor.getTextFromPage(pdfDocument.getPage(pageNum)));
                System.out.println(com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor.getTextFromPage(pdfDocument.getPage(pageNum)));
            }
            pdfDocument.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }


    /**
     * 重載concatPDFs方法, 功能是一樣的
     *
     * @param streamArrayOfPdfFiles
     * @param outputStream
     * @param pagination
     */
    public void concatPDFs(InputStream[] streamArrayOfPdfFiles, OutputStream outputStream, boolean pagination) {
        List<InputStream> streamOfPdfFiles = new LinkedList<>(Arrays.asList(streamArrayOfPdfFiles));
        concatPDFs(streamOfPdfFiles, outputStream, pagination);
    }


    /**
     * 多份pdf串连成一份pdf
     *
     * @param streamOfPdfFiles
     * @param outputStream
     * @param pagination
     */
    public void concatPDFs(List<InputStream> streamOfPdfFiles, OutputStream outputStream, boolean pagination) {
        Document document = new Document();
        try {
            List<InputStream> pdfs = streamOfPdfFiles;
            List<PdfReader> readers = new LinkedList<>();
            int totalPages = 0;

            // Create Readers for the pdfs.
            for (InputStream pdf : pdfs) {
                PdfReader pdfReader = new PdfReader(pdf);
                readers.add(pdfReader);
                totalPages += pdfReader.getNumberOfPages();
            }
            // Create a writer for the outputStream
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);

            document.open();
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            PdfContentByte cb = writer.getDirectContent(); // Holds the PDF

            PdfImportedPage page;
            int currentPageNumber = 0;
            int pageOfCurrentReaderPDF = 0;

            // Loop through the PDF files and add to the output.
            for (PdfReader pdfReader : readers) {
                // Create a new page in the target for each source page.
                while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
                    document.newPage();
                    pageOfCurrentReaderPDF++;
                    currentPageNumber++;
                    page = writer.getImportedPage(pdfReader, pageOfCurrentReaderPDF);
                    cb.addTemplate(page, 0, 0);
                    // Code for pagination.
                    if (pagination) {
                        cb.beginText();
                        cb.setFontAndSize(bf, 9);
                        cb.showTextAligned(PdfContentByte.ALIGN_CENTER, currentPageNumber + " of " + totalPages, 520, 5, 0);
                        cb.endText();
                    }
                }
                pageOfCurrentReaderPDF = 0;
            }
            outputStream.flush();
            document.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (document.isOpen()) document.close();
            try {
                if (outputStream != null) outputStream.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    /**
     * 读取url的html然后render html to pdf
     *
     * @param targetUrl
     * @param pdfLocation
     * @throws IOException
     * @throws DocumentException
     */
    public static void htmlToPdf(String targetUrl, String pdfLocation) throws Exception {
        OutputStream outputStream = new FileOutputStream(pdfLocation);
        HtmlUtil htmlUtil = new HtmlUtil();
        String html = htmlUtil.getHtmlFromUrl(targetUrl);
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlUtil.convertToXHtml(html));
        ITextFontResolver fontResolver = renderer.getFontResolver();
        if (OsUtil.getOsName().contains(OsUtil.WINDOWS)) {
            fontResolver.addFont("C:/WINDOWS/Fonts/SIMSUN.TTC", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        } else if (OsUtil.getOsName().contains(OsUtil.LINUX)) {
            throw new Exception("Linux系统没有处理");
        } else if (OsUtil.getOsName().contains(OsUtil.MAC)) {
            throw new Exception("MacOS系统没有处理");
        } else {
            throw new Exception("未知系统 !");
        }
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.flush();
        outputStream.close();
    }


    /**
     * 支持一张或多张图片转换成pdf
     *
     * @param inputPath
     * @param outputPath
     * @throws IOException
     */
    public static void toPdf(String inputPath, String outputPath) throws IOException {
        File f = new File(inputPath);
        if (f.exists()) {
            if (new File(outputPath).isDirectory()) {
                outputPath = outputPath + "pdf_ver_" + RandomUtil.getInt(100) + ".pdf";
            }

            if (f.isDirectory()) {
                imagesConcatPdf(inputPath, outputPath);
            } else {
                imageToPdf(inputPath, outputPath);
            }
        }
    }

    /**
     * 多张图片转换成pdf
     *
     * @param fileNames
     * @param outputPath
     * @throws IOException
     */
    public static void toPdf(List<String> fileNames, String outputPath) throws IOException {
        try {
            // 图片文件夹地址
            // 图片地址
            String imagePath;
            // PDF文件保存地址
            // 输入流
            if (!outputPath.endsWith(PDF)) {
                throw new Exception("this is not a PDF !");
            }
            FileOutputStream fos = new FileOutputStream(outputPath);
            // 创建文档
            Document doc = new Document(null, 0, 0, 0, 0);
            //doc.open();
            // 写入PDF文档
            PdfWriter.getInstance(doc, fos);
            // 读取图片流
            BufferedImage img;
            // 实例化图片
            Image image;
            // 循环获取图片文件夹内的图片
            for (String p : fileNames) {
                // 获取图片文件对象
                File f = new File(p);
                if (f.getName().endsWith(".png") || f.getName().endsWith(".jpg") || f.getName().endsWith(".gif") || f.getName().endsWith(".jpeg") || f.getName().endsWith(".tif")) {
                    System.out.println(f.getName());
                    imagePath = p;
                    // 读取图片流
                    img = ImageIO.read(new FileInputStream(imagePath));
                    doc.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
                    // 根据图片大小设置文档大小
                    doc.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
                    // 实例化图片
                    image = Image.getInstance(imagePath);
                    // 添加图片到文档
                    doc.open();
                    doc.add(image);
                }
            }
            // 关闭文档
            doc.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * 使用pdfbox将jpg转成pdf
     *
     * @param input   jpg输入流
     * @param pdfPath pdf文件存储路径
     * @throws IOException IOException
     */
    private static void imageToPdf(String input, String pdfPath) throws IOException {

        InputStream jpgStream = new FileInputStream(input);

        PDDocument pdDocument = new PDDocument();
        BufferedImage image = ImageIO.read(jpgStream);
        PDPage pdPage = new PDPage(new PDRectangle(image.getWidth(), image.getHeight()));
        pdDocument.addPage(pdPage);
        PDImageXObject pdImageXObject = LosslessFactory.createFromImage(pdDocument, image);
        PDPageContentStream contentStream = new PDPageContentStream(pdDocument, pdPage);
        contentStream.drawImage(pdImageXObject, 0, 0, image.getWidth(), image.getHeight());
        contentStream.close();
        pdDocument.save(pdfPath);
        pdDocument.close();
    }


    /**
     * 文件夹下多张图片转成一个pdf(多页文件)
     *
     * @param imageFolderPath
     * @param pdfPath
     */
    private static void imagesConcatPdf(String imageFolderPath, String pdfPath) {
        try {
            // 图片文件夹地址
            // 图片地址
            String imagePath;
            // PDF文件保存地址
            // 输入流
            if (!pdfPath.endsWith(PDF)) {
                throw new Exception("this is not a PDF !");
            }
            FileOutputStream fos = new FileOutputStream(pdfPath);
            // 创建文档
            Document doc = new Document(null, 0, 0, 0, 0);
            //doc.open();
            // 写入PDF文档
            PdfWriter.getInstance(doc, fos);
            // 读取图片流
            BufferedImage img;
            // 实例化图片
            Image image;
            // 获取图片文件夹对象
            File file = new File(imageFolderPath);
            File[] files = file.listFiles();
            // 循环获取图片文件夹内的图片
            for (File f : files) {
                if (f.getName().endsWith(".png") || f.getName().endsWith(".jpg") || f.getName().endsWith(".gif") || f.getName().endsWith(".jpeg") || f.getName().endsWith(".tif")) {
                    System.out.println(f.getName());
                    imagePath = imageFolderPath + PathUtil.separator + f.getName();
                    // 读取图片流
                    img = ImageIO.read(new FileInputStream(imagePath));
                    doc.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
                    // 根据图片大小设置文档大小
                    doc.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
                    // 实例化图片
                    image = Image.getInstance(imagePath);
                    // 添加图片到文档
                    doc.open();
                    doc.add(image);
                }
            }
            // 关闭文档
            doc.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

