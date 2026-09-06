package com.bharat.online_certificate_verification_system.service.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class QrCodeServiceImpl implements QrCodeService {

    @Value("${app.base-url}")
    private String baseUrl;

    private static final String QR_DIRECTORY = "uploads/qrcodes/";

    @Override
    public String generateQrCode(String certificateId) {

        try {

            Files.createDirectories(Paths.get(QR_DIRECTORY));

            String verificationUrl =
                    baseUrl +  "/api/public/certificates/verify/" + certificateId;

            QRCodeWriter qrCodeWriter = new QRCodeWriter();

            BitMatrix bitMatrix = qrCodeWriter.encode(
                    verificationUrl,
                    BarcodeFormat.QR_CODE,
                    300,
                    300
            );

            String fileName = certificateId + ".png";

            Path path = Paths.get(QR_DIRECTORY + fileName);

            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

            return path.toString();

        } catch (Exception e) {
            throw new RuntimeException("Unable to generate QR Code", e);
        }
    }
}