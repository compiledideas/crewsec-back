package com.compiledideas.crewsecback.security.service;

import com.compiledideas.crewsecback.security.request.SignInRequest;
import com.compiledideas.crewsecback.security.request.SignUpRequest;
import com.compiledideas.crewsecback.security.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);
    JwtAuthenticationResponse signin(SignInRequest request);
}
