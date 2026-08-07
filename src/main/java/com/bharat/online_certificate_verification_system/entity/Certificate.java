package com.bharat.online_certificate_verification_system.entity;

import com.bharat.online_certificate_verification_system.enums.CertificateStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="certificates")
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false,unique=true)
    private String certificateId;

    @Column(nullable = false)
    private String recipientName;
    @Column(nullable=false)
    private String courseName;

    @Column(nullable = false)
    private String recipientEmail;

    @Column(nullable =false)
    private String certificateTitle;

    @Column(nullable = false)
    private LocalDate issueDate;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String qrCodeUrl;

    @Column(length =500)
    private String pdfUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CertificateStatus status;

    @ManyToOne(fetch =FetchType.LAZY)
    @JoinColumn(name="Institution_id", nullable =false)
    private Institution institution;



}
