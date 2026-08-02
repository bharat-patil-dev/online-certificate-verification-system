package com.bharat.online_certificate_verification_system.service.Impl;

import com.bharat.online_certificate_verification_system.dto.InstitutionRegistrationRequest;
import com.bharat.online_certificate_verification_system.dto.auth.LoginRequest;
import com.bharat.online_certificate_verification_system.dto.auth.LoginResponse;
import com.bharat.online_certificate_verification_system.entity.Institution;
import com.bharat.online_certificate_verification_system.entity.User;
import com.bharat.online_certificate_verification_system.enums.InstitutionStatus;
import com.bharat.online_certificate_verification_system.enums.Role;
import com.bharat.online_certificate_verification_system.exception.ResourceAlreadyExistsException;
import com.bharat.online_certificate_verification_system.repositories.InstitutionRepository;
import com.bharat.online_certificate_verification_system.repositories.UserRepository;
import com.bharat.online_certificate_verification_system.security.JwtService;
import com.bharat.online_certificate_verification_system.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final InstitutionRepository institutionRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;



    @Override
    public void registerInstitution(InstitutionRegistrationRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already exists.");
        }
            User user = User.builder()
                    .fullName(request.getFullName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.INSTITUTION)
                    .enabled(false)
                    .build();
            Institution institution = Institution.builder()
                    .institutionName(request.getInstitutionName())
                    .phone(request.getPhone())
                    .website(request.getWebsite())
                    .address(request.getAddress())
                    .status(InstitutionStatus.PENDING)
                    .user(user)
                    .build();

            institutionRepository.save(institution);
    }

    @Override
    public LoginResponse login(LoginRequest request){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(()-> new UsernameNotFoundException("User not Found"));

        String token = jwtService.generateToken(userDetails);

        return LoginResponse.builder()
                .token(token)
                .type("Bearer")
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole().name())
                .build();
    }

}
