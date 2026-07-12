package com.quizapp.service.impl;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;
import com.quizapp.entity.QuizResult;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;

/**
 * Renders a "Certificate of Achievement" PDF for a passed quiz attempt.
 * One file per QuizResult, saved under uploads/certificates/.
 */
@Service
public class CertificateService {

    private static final String CERT_DIR = "uploads/certificates/";

    private static final Color NAVY = new Color(30, 41, 89);
    private static final Color GOLD = new Color(191, 149, 63);
    private static final Color LIGHT_GRAY = new Color(107, 114, 128);

    /**
     * Generates (or re-generates) the certificate PDF for the given result
     * and returns the path it was written to.
     */
    public String generate(QuizResult result) throws IOException {
        Files.createDirectories(Path.of(CERT_DIR));
        String fileName = "Certificate_" + result.getId() + ".pdf";
        String filePath = CERT_DIR + fileName;

        Document document = new Document(PageSize.A4.rotate(), 40, 40, 40, 40);

        try (FileOutputStream out = new FileOutputStream(filePath)) {
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();

            float pageWidth = document.getPageSize().getWidth();
            float pageHeight = document.getPageSize().getHeight();
            PdfContentByte canvas = writer.getDirectContentUnder();

            drawBorder(canvas, pageWidth, pageHeight);
            addContent(document, result);

            document.close();
        } catch (DocumentException e) {
            throw new IOException("Failed to generate certificate PDF", e);
        }

        return filePath;
    }

    private void drawBorder(PdfContentByte canvas, float pageWidth, float pageHeight) {
        // outer thick navy border
        canvas.setColorStroke(NAVY);
        canvas.setLineWidth(6f);
        canvas.rectangle(24, 24, pageWidth - 48, pageHeight - 48);
        canvas.stroke();

        // inner thin gold border for a "framed" look
        canvas.setColorStroke(GOLD);
        canvas.setLineWidth(1.5f);
        canvas.rectangle(36, 36, pageWidth - 72, pageHeight - 72);
        canvas.stroke();
    }

    private void addContent(Document document, QuizResult result) throws DocumentException {
        Font brandFont = new Font(Font.HELVETICA, 12, Font.BOLD, LIGHT_GRAY);
        Font titleFont = new Font(Font.HELVETICA, 34, Font.BOLD, NAVY);
        Font subtitleFont = new Font(Font.HELVETICA, 13, Font.NORMAL, LIGHT_GRAY);
        Font nameFont = new Font(Font.HELVETICA, 28, Font.BOLDITALIC, NAVY);
        Font bodyFont = new Font(Font.HELVETICA, 13, Font.NORMAL, Color.DARK_GRAY);
        Font quizFont = new Font(Font.HELVETICA, 16, Font.BOLD, NAVY);
        Font scoreFont = new Font(Font.HELVETICA, 13, Font.BOLD, GOLD.darker());
        Font smallFont = new Font(Font.HELVETICA, 10, Font.NORMAL, LIGHT_GRAY);
        Font signatureFont = new Font(Font.HELVETICA, 12, Font.BOLD, NAVY);

        addSpacer(document, 5f);

        Paragraph brand = new Paragraph("ARMONEXUS GROUPS", brandFont);
        brand.setAlignment(Element.ALIGN_CENTER);
        document.add(brand);

        addSpacer(document, 5f);

        Paragraph title = new Paragraph("CERTIFICATE OF ACHIEVEMENT", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        addSpacer(document, 3f);

        Paragraph subtitle = new Paragraph("This certificate is proudly presented to", subtitleFont);
        subtitle.setAlignment(Element.ALIGN_CENTER);
        document.add(subtitle);

        addSpacer(document, 4f);

        Paragraph name = new Paragraph(result.getUser().getFullName(), nameFont);
        name.setAlignment(Element.ALIGN_CENTER);
        document.add(name);

        addSpacer(document, 3f);

        // decorative underline beneath the name
        Paragraph underline = new Paragraph("\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014\u2014",
                new Font(Font.HELVETICA, 12, Font.NORMAL, GOLD));
        underline.setAlignment(Element.ALIGN_CENTER);
        document.add(underline);

        addSpacer(document, 1f);

        Paragraph body = new Paragraph(
                "for successfully completing the quiz", bodyFont);
        body.setAlignment(Element.ALIGN_CENTER);
        document.add(body);

        addSpacer(document, 4f);

        Paragraph quizTitle = new Paragraph("\u201C" + result.getQuiz().getTitle() + "\u201D", quizFont);
        quizTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(quizTitle);

        addSpacer(document, 2f);

        String scoreText = String.format("Score achieved: %.1f / %d",
                result.getScoreObtained(), result.getTotalMarks());
        Paragraph score = new Paragraph(scoreText, scoreFont);
        score.setAlignment(Element.ALIGN_CENTER);
        document.add(score);

        addSpacer(document, 5f);

        String dateText = result.getSubmittedAt() != null
                ? result.getSubmittedAt().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
                : "";

        // signature / date row
        PdfPTableWrapper.addSignatureRow(document, dateText, "Date Issued",
                "ARMONEXUS SMART QUIZ", "Authorized Signature", signatureFont, smallFont);

        addSpacer(document, 5f);

        Paragraph certId = new Paragraph("Certificate ID: ANT-" + result.getId() + "-" +
                (result.getQuiz().getId() != null ? result.getQuiz().getId() : ""), smallFont);
        certId.setAlignment(Element.ALIGN_CENTER);
        document.add(certId);
    }

    private void addSpacer(Document document, float size) throws DocumentException {
        Paragraph spacer = new Paragraph(" ");
        spacer.setSpacingAfter(size);
        document.add(spacer);
    }
}
