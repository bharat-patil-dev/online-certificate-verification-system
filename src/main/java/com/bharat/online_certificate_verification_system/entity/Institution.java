package com.bharat.online_certificate_verification_system.entity;

import com.bharat.online_certificate_verification_system.enums.InstitutionStatus;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="Institutions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Institution extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="institution_name",nullable =false)
    private String institutionName;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String website;

    @Column(nullable = false)
    private String address;

    @Column (name = "logo_url",length=500)
    private String logoUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InstitutionStatus status;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn (name = "user_id" ,nullable =false , unique =true)
    private User user;
}
