package com.quizapp.service.impl;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;

/**
 * Small helper isolating the two-column "Date Issued / Authorized Signature"
 * footer table so CertificateService stays readable.
 */
final class PdfPTableWrapper {

    private PdfPTableWrapper() {
    }

    static void addSignatureRow(Document document,
                                 String leftValue, String leftLabel,
                                 String rightValue, String rightLabel,
                                 Font valueFont, Font labelFont) throws DocumentException {

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(70);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);

        table.addCell(borderlessCell(leftValue, valueFont, leftLabel, labelFont));
        table.addCell(borderlessCell(rightValue, valueFont, rightLabel, labelFont));

        document.add(table);
    }

    private static PdfPCell borderlessCell(String value, Font valueFont, String label, Font labelFont) {
        com.lowagie.text.Paragraph combined = new com.lowagie.text.Paragraph();
        combined.add(new com.lowagie.text.Chunk(value + "\n", valueFont));
        combined.add(new com.lowagie.text.Chunk(label, labelFont));
        combined.setAlignment(Element.ALIGN_CENTER);

        PdfPCell cell = new PdfPCell();
        cell.setBorder(PdfPCell.TOP);
        cell.setBorderWidthTop(1f);
        cell.setPaddingTop(6f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.addElement(combined);
        return cell;
    }
}
