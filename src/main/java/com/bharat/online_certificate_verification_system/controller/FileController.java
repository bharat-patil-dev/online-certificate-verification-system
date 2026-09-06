package com.bharat.online_certificate_verification_system.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/public/files")
public class FileController {

    private static final String PDF_DIRECTORY =
            "uploads/pdf/";

    private static final String QR_DIRECTORY =
            "uploads/qrcodes/";


    @GetMapping("/pdf/{fileName}")
    public ResponseEntity<Resource> getPdf(
            @PathVariable String fileName
    ) {

        return getFile(
                PDF_DIRECTORY,
                fileName,
                MediaType.APPLICATION_PDF
        );
    }


    @GetMapping("/qr/{fileName}")
    public ResponseEntity<Resource> getQrCode(
            @PathVariable String fileName
    ) {

        return getFile(
                QR_DIRECTORY,
                fileName,
                MediaType.IMAGE_PNG
        );
    }


    private ResponseEntity<Resource> getFile(
            String directory,
            String fileName,
            MediaType mediaType
    ) {

        try {

            Path filePath =
                    Paths.get(directory)
                            .resolve(fileName)
                            .normalize();

            Resource resource =
                    new UrlResource(
                            filePath.toUri()
                    );


            if (!resource.exists() ||
                    !resource.isReadable()) {

                return ResponseEntity
                        .notFound()
                        .build();
            }


            return ResponseEntity
                    .ok()
                    .contentType(mediaType)
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" +
                                    resource.getFilename() +
                                    "\""
                    )
                    .body(resource);

        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .build();
        }
    }
}
