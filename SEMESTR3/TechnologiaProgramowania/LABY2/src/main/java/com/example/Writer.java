package example;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class Writer {

    private Invoice invoice;

    public Writer(Invoice invoice) {
        this.invoice = invoice;
    }

    public void writeToPDF() {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (
                PDPageContentStream contentStream = new PDPageContentStream(
                    document,
                    page
                )
            ) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12.0f);

                contentStream.newLineAtOffset(100, 700);
                String[] data = invoice.toString().split("\n");
                for (String line : data) {
                    contentStream.showText(line);
                    contentStream.newLineAtOffset(0, -15); // move down 15 points
                }

                contentStream.endText();
            } catch (IOException e) {
                throw new RuntimeException("Failed to write content to PDF", e);
            }

            try {
                document.save("invoice.pdf");
            } catch (IOException e) {
                throw new RuntimeException("Failed to save PDF document", e);
            }
        } catch (IOException e) {
            throw new RuntimeException(
                "Failed to create or close PDF document",
                e
            );
        }
    }
}
