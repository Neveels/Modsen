package org.blueliner.authenticationservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.blueliner.authenticationservice.dto.AuthenticationRequest;
import org.blueliner.authenticationservice.dto.AuthenticationResponse;
import org.blueliner.authenticationservice.dto.RegisterRequest;
import org.blueliner.authenticationservice.exception.type.UserAlreadyExist;
import org.blueliner.authenticationservice.exception.type.UserNotFoundException;
import org.blueliner.authenticationservice.model.UserCredential;
import org.blueliner.authenticationservice.repository.UserCredentialRepository;
import org.blueliner.authenticationservice.service.AuthenticationService;
import org.blueliner.authenticationservice.utils.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final String USER_WITH_EMAIL_ALREADY_EXIST = "User with email %s already exist";
    private static final String USER_WITH_EMAIL_NOT_FOUND = "User with email %s not found";

    private final UserCredentialRepository userCredentialRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        if (userCredentialRepository.findByEmail(request.email()).isEmpty()) {
            UserCredential user = UserCredential.builder()
                    .name(request.name())
                    .email(request.email())
                    .password(passwordEncoder.encode(request.password()))
                    .build();
            userCredentialRepository.save(user);
            String jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } else {
            throw new UserAlreadyExist(
                    String.format(USER_WITH_EMAIL_ALREADY_EXIST,
                            request.email())
            );
        }
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        UserCredential user = userCredentialRepository.findByEmail(request.email())
                .orElseThrow(() -> new UserNotFoundException(
                                String.format(USER_WITH_EMAIL_NOT_FOUND,
                                        request.email())
                        )
                );
        String jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
