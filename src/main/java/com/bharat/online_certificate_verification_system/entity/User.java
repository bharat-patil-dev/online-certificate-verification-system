package com.bharat.online_certificate_verification_system.entity;

import com.bharat.online_certificate_verification_system.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column( name = "full_name" ,nullable =false)
    private String fullName;

    @Column(nullable =false, unique =true)
    private String email;

    @Column(nullable =false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable =false)
    private Role role;

    @Column(nullable =false)
    private boolean enabled = true;
}
