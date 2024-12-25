package com.compiledideas.crewsecback.security.controller;

import com.compiledideas.crewsecback.security.request.SignInRequest;
import com.compiledideas.crewsecback.security.request.SignUpRequest;
import com.compiledideas.crewsecback.security.service.AuthenticationService;
import com.compiledideas.crewsecback.security.service.JwtService;
import com.compiledideas.crewsecback.security.service.UserService;
import com.compiledideas.crewsecback.utils.ResponseHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final JwtService jwtService;
    Logger logger = LogManager.getLogger(AuthenticationController.class);

    @PostMapping("/signup")
    public ResponseEntity<Object> register(@RequestBody SignUpRequest request) {
        logger.info("New user registered with email: {}", request.getEmail());
        return ResponseHandler.generateResponse(
                "registering a new user",
                HttpStatus.OK,
                authenticationService.signup(request)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody SignInRequest request) {
        logger.info("user with phone {} trying to login.", request.getPhone());

        return ResponseHandler.generateResponse(
                "login successfully",
                HttpStatus.OK,
                authenticationService.signin(request)
        );
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> getProfile(@NonNull HttpServletRequest request) {
        String username = jwtService.extractUsername(request.getHeader("Authorization").substring(7));
        logger.info("user with email {} requested token validation", username);
        return ResponseHandler.generateResponse(
                "Token is valid and user is logged in.",
                HttpStatus.OK,
                userService.findByUsername(username)
        );
    }
}
