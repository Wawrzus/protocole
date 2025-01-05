package com.example.protocole;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.TransactionTooLargeException;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.text.pdf.BaseFont;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CreateEmail {
    public static Paragraph createParagraph(String text, float fontSize, float marginLeft) throws Exception {
        PdfFont paragraphFont = PdfFontFactory.createFont("assets/arial.ttf", BaseFont.IDENTITY_H);
        Text paragraphText = new Text(text);
        paragraphText.setFont(paragraphFont);
        paragraphText.setFontSize(fontSize);
        Paragraph paragraph = new Paragraph(paragraphText);
        paragraph.setMarginLeft(marginLeft);
        return paragraph;
    }

    public static Paragraph createHeadline(String text, float fontSize, float marginLeft) throws Exception {
        PdfFont paragraphFont = PdfFontFactory.createFont("assets/arialbd.ttf", BaseFont.IDENTITY_H);
        Text paragraphText = new Text(text);
        paragraphText.setFont(paragraphFont);
        paragraphText.setFontSize(fontSize);
        return new Paragraph(paragraphText);
    }

    public static Table createImageTable(float[] columnSize, Image[] columnContent) {
        Table table = new Table(columnSize);
        for (int i = 0; i < columnContent.length; i++) {
            if(i == 0) {
                Cell cell = new Cell().setBorder(Border.NO_BORDER);
                cell.add(columnContent[i].scaleAbsolute(100, 40).setHorizontalAlignment(HorizontalAlignment.LEFT));
                table.addCell(cell);
            }
            if(i == 1) {
                Cell cell = new Cell().setBorder(Border.NO_BORDER);
                cell.add(columnContent[i].scaleAbsolute(100, 40).setHorizontalAlignment(HorizontalAlignment.RIGHT));
                table.addCell(cell);
            }
        }
        return table;
    }

    public static Table createTableNoBorder(float[] columnSize, String[] columnContent, float fontSize) {
        Table table = new Table(columnSize);
        for (int i = 0; i < columnContent.length; i++) {
            if(i == 0) {
                Cell cell = new Cell().setBorder(Border.NO_BORDER);
                cell.add(new Paragraph(columnContent[i]).setTextAlignment(TextAlignment.LEFT));
                cell.setFontSize(fontSize);
                table.addCell(cell);
            }
            if(i == 1) {
                Cell cell = new Cell().setBorder(Border.NO_BORDER);
                cell.add(new Paragraph(columnContent[i]).setTextAlignment(TextAlignment.RIGHT));
                cell.setFontSize(fontSize);
                table.addCell(cell);
            }
        }
        return table;
    }

    public static Table createTableCisnienie(float[] columnSize, String[] columnContent, float fontSize) throws IOException {
        PdfFont paragraphFont = PdfFontFactory.createFont("assets/arial.ttf", BaseFont.IDENTITY_H);
        Table table = new Table(columnSize);
        for (int i = 0; i < columnContent.length; i++) {
            if(i%2 == 1) {
                Cell cell = new Cell();
                cell.add(new Paragraph(columnContent[i]).setTextAlignment(TextAlignment.RIGHT));
                cell.setFont(paragraphFont);
                cell.setFontSize(fontSize);
                table.addCell(cell);
            }
            else {
                Cell cell = new Cell();
                cell.add(new Paragraph(columnContent[i]).setTextAlignment(TextAlignment.LEFT));
                cell.setFont(paragraphFont);
                cell.setFontSize(fontSize);
                table.addCell(cell);
            }
        }
        return table;
    }

    public static Table createTableTemp(float[] columnSize, String[] columnContent, float fontSize) throws IOException {
        PdfFont paragraphFont = PdfFontFactory.createFont("assets/arial.ttf", BaseFont.IDENTITY_H);
        Table table = new Table(columnSize);
        for (int i = 0; i < columnContent.length; i++) {
            if(i == 1 || i == 2 || i == 4 || i == 5) {
                Cell cell = new Cell();
                cell.add(new Paragraph(columnContent[i]).setTextAlignment(TextAlignment.CENTER));
                cell.setFont(paragraphFont);
                cell.setFontSize(fontSize);
                table.addCell(cell);
            }
            else {
                Cell cell = new Cell();
                cell.add(new Paragraph(columnContent[i]).setTextAlignment(TextAlignment.LEFT));
                cell.setFont(paragraphFont);
                cell.setFontSize(fontSize);
                table.addCell(cell);
            }
        }
        return table;
    }

    public static Table createPageLogos(Context context) throws Exception {
        float[] columnSize = {80, 400, 400, 80};
        PdfFont company = PdfFontFactory.createFont();
        PdfFont contact = PdfFontFactory.createFont();
        Table table = new Table(columnSize);

        Image[] images = getImageFromAssets(context);
        Image imkiusImg = images[0];
        Image groupImg = images[1];

        table.addCell(new Cell()
                .add(imkiusImg.setMaxHeight(50).setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell()
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph().add("PG IMKIUS Sp. z o.o.").setFont(company).setFontSize(10).setTextAlignment(TextAlignment.LEFT))
                .add(new Paragraph().add("43-317 Kaczyce, ul. Morcinka 7c").setFont(contact).setFontSize(8).setTextAlignment(TextAlignment.LEFT))
                .add(new Paragraph().add("tel:. +48 32 475 81 50, fax: +48 32 469 41 83").setFont(contact).setFontSize(8).setTextAlignment(TextAlignment.LEFT))
                .add(new Paragraph().add("poczta@imkius.pl. www.imkius.pl").setFont(contact).setFontSize(8).setTextAlignment(TextAlignment.LEFT)));
        table.addCell(new Cell()
                .setBorder(Border.NO_BORDER)
                .add(new Paragraph().add("PN-EN ISO 9001").setFont(contact).setFontSize(8).setTextAlignment(TextAlignment.RIGHT))
                .add(new Paragraph().add("PN-EN ISO 14001").setFont(contact).setFontSize(8).setTextAlignment(TextAlignment.RIGHT))
                .add(new Paragraph().add("PN-N-18001").setFont(contact).setFontSize(8).setTextAlignment(TextAlignment.RIGHT))
                .add(new Paragraph().add("PN-EN ISO/EC 80079-34").setFont(contact).setFontSize(8).setTextAlignment(TextAlignment.RIGHT)));
        table.addCell(new Cell()
                .add(groupImg.setMaxHeight(50).setMarginLeft(10)).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
        return table;
    }

    public static Image[] getImageFromAssets(Context context) throws IOException {
        InputStream pgimkius = context.getAssets().open("pgimkius.png");
        InputStream pggroup = context.getAssets().open("pggroup.png");
        Bitmap imkiusbmp = BitmapFactory.decodeStream(pgimkius);
        Bitmap groupbmp = BitmapFactory.decodeStream(pggroup);
        ByteArrayOutputStream imkiusstream = new ByteArrayOutputStream();
        ByteArrayOutputStream pggroupstream = new ByteArrayOutputStream();
        imkiusbmp.compress(Bitmap.CompressFormat.PNG, 100, imkiusstream);
        groupbmp.compress(Bitmap.CompressFormat.PNG, 100, pggroupstream);
        byte[] imkiusbmpArray = imkiusstream.toByteArray();
        byte[] groupbmpArray = pggroupstream.toByteArray();
        ImageData imkiusImageData = ImageDataFactory.create(imkiusbmpArray);
        ImageData groupImageData = ImageDataFactory.create(groupbmpArray);
        Image imkiusImg = new Image(imkiusImageData);
        Image groupImg = new Image(groupImageData);
        Image[] images = new Image[2];
        images[0] = imkiusImg;
        images[1] = groupImg;
        return images;
    }

    public static void createAndSend(Table pageLogos, Paragraph[] paragraphArrayBefore ,Table[] tableArray, Paragraph[] paragraphArray, Table[] signatureArray) throws Exception {
        File fileDelete = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "protokol.pdf");
        fileDelete.delete();
        String path;
        path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(path, "protokol.pdf");
        PdfWriter pdfWriter = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);
        document.add(pageLogos);
        for (Paragraph paragraph : paragraphArrayBefore) {
            paragraph.setMarginTop(5);
            document.add(paragraph);
        }
        for (Table table : tableArray) {
            table.setMarginTop(5);
            document.add(table);
        }
        for (Paragraph paragraph : paragraphArray) {
            paragraph.setMarginTop(5);
            document.add(paragraph);
        }
        for (Table table : signatureArray) {
            table.setMarginTop(5);
            document.add(table);
        }
        document.close();
    }
}
