package com.compiledideas.crewsecback.security.service.implementation;


import com.compiledideas.crewsecback.security.models.User;
import com.compiledideas.crewsecback.security.request.SignInRequest;
import com.compiledideas.crewsecback.security.request.SignUpRequest;
import com.compiledideas.crewsecback.security.response.JwtAuthenticationResponse;
import com.compiledideas.crewsecback.security.service.AuthenticationService;
import com.compiledideas.crewsecback.security.service.JwtService;
import com.compiledideas.crewsecback.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    private final UserService service;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        log.info("sign up new user {}", request.getEmail());
        var salt = RandomStringUtils.random(20, true, true);
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .salt(salt)
                .phone(request.getPhone())
                .role(request.getRole())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        service.addUser(user);

        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).user(user).build();
    }

    @Override
    public JwtAuthenticationResponse signin(SignInRequest request) {
        log.info("sign in user {}", request.getEmail());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        var user = service.findByEmail(request.getEmail());
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).user(user).build();
    }
}
