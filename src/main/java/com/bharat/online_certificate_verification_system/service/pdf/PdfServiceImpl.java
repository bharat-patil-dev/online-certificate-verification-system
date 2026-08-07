package com.bharat.online_certificate_verification_system.service.pdf;

import com.bharat.online_certificate_verification_system.entity.Certificate;
import com.bharat.online_certificate_verification_system.exception.ResourceNotFoundException;
import com.bharat.online_certificate_verification_system.repositories.CertificateRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class PdfServiceImpl implements PdfService {

    private final CertificateRepository certificateRepository;

    private static final String PDF_DIRECTORY = "uploads/pdf/";

    @Override
    public String generateCertificatePdf(Long certificateDbId) {

        try {

            Certificate certificate = certificateRepository.findById(certificateDbId)
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Certificate not found"));

            Files.createDirectories(Paths.get(PDF_DIRECTORY));

            String fileName = certificate.getCertificateId() + ".pdf";

            Path path = Paths.get(PDF_DIRECTORY + fileName);

            Document document = new Document(PageSize.A4);

            PdfWriter.getInstance(document, new FileOutputStream(path.toFile()));

            document.open();

            Font titleFont = new Font(Font.HELVETICA, 24, Font.BOLD);
            Font headingFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Font normalFont = new Font(Font.HELVETICA, 14);

            Paragraph institution =
                    new Paragraph(
                            certificate.getInstitution().getInstitutionName(),
                            titleFont
                    );

            institution.setAlignment(Element.ALIGN_CENTER);

            document.add(institution);

            document.add(new Paragraph("\n"));

            Paragraph title =
                    new Paragraph("CERTIFICATE OF COMPLETION", headingFont);

            title.setAlignment(Element.ALIGN_CENTER);

            document.add(title);

            document.add(new Paragraph("\n"));

            document.add(new Paragraph(
                    "This is to certify that",
                    normalFont));

            document.add(new Paragraph("\n"));

            Paragraph recipient =
                    new Paragraph(
                            certificate.getRecipientName(),
                            headingFont
                    );

            recipient.setAlignment(Element.ALIGN_CENTER);

            document.add(recipient);

            document.add(new Paragraph("\n"));

            document.add(new Paragraph(
                    "has successfully completed",
                    normalFont));

            document.add(new Paragraph("\n"));

            Paragraph course =
                    new Paragraph(
                            certificate.getCourseName(),
                            headingFont
                    );

            course.setAlignment(Element.ALIGN_CENTER);

            document.add(course);

            document.add(new Paragraph("\n\n"));

            document.add(new Paragraph(
                    "Certificate ID : " + certificate.getCertificateId(),
                    normalFont));

            document.add(new Paragraph(
                    "Issue Date : " + certificate.getIssueDate(),
                    normalFont));

            document.close();

            return path.toString();

        } catch (Exception e) {
            throw new RuntimeException("Unable to generate PDF", e);
        }
    }
}
