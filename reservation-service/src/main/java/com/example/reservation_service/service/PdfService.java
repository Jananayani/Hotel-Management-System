package com.example.reservation_service.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import javax.swing.text.Document;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfService {
    public ByteArrayInputStream generateReservationPdf(String reservationDetails) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(100, 700); // Set initial position

                // Split reservation details into lines and write each line
                String[] lines = reservationDetails.split("\n");
                for (String line : lines) {
                    contentStream.showText(line); // Add line text
                    contentStream.newLineAtOffset(0, -15); // Move down for the next line
                }

                contentStream.endText();
            }

            document.save(out); // Save document to output stream
        }

        return new ByteArrayInputStream(out.toByteArray()); // Return PDF as InputStream
    }
}
