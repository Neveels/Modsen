package org.blueliner.authenticationservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.blueliner.authenticationservice.dto.AuthenticationRequest;
import org.blueliner.authenticationservice.dto.AuthenticationResponse;
import org.blueliner.authenticationservice.dto.RegisterRequest;
import org.blueliner.authenticationservice.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @Operation(
            summary = "Register a New User",
            description = "Registers a new user with the provided information.",
            tags = { "authentication" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User registered successfully",
                    content = @Content(schema = @Schema(implementation = AuthenticationResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request - Invalid user registration data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> addNewUser(
            @RequestBody RegisterRequest registerRequest
    ) {
        return ResponseEntity
                .ok()
                .body(authenticationService.register(registerRequest));
    }

    @Operation(
            summary = "User Authentication",
            description = "Allows the user to authenticate using their credentials.",
            tags = { "tutorials", "get" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Successful authentication",
                    content = @Content(schema = @Schema(implementation = AuthenticationResponse.class),
                            mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Authentication failed", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "403", description = "Access forbidden", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema()))
    })
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity
                .ok()
                .body(authenticationService.authenticate(request));
    }

}