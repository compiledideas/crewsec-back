package com.compiledideas.crewsecback.security.configs;

import com.compiledideas.crewsecback.security.entrypoints.AppAccessDeniedHandler;
import com.compiledideas.crewsecback.security.entrypoints.AppBasicAuthenticationEntryPoint;
import com.compiledideas.crewsecback.security.entrypoints.AppBearerTokenAuthenticationEntryPoint;
import com.compiledideas.crewsecback.security.filter.JwtAuthenticationFilter;
import com.compiledideas.crewsecback.security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
@EnableMethodSecurity(securedEnabled  = true)
public class AppSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService service;
    private final AppBasicAuthenticationEntryPoint authenticationEntryPoint;
    private final AppBearerTokenAuthenticationEntryPoint bearerAuthenticationEntryPoint;
    private final AppAccessDeniedHandler accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/v1/auth/**")
                        .permitAll()
                        .anyRequest()
                        .permitAll()
                ).cors(Customizer.withDefaults())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(
                        authenticationProvider()
                )
                .httpBasic(
                        httpBasic -> httpBasic.authenticationEntryPoint(authenticationEntryPoint)
                )
                .exceptionHandling(
                        ex -> ex.accessDeniedHandler(accessDeniedHandler)
                                .authenticationEntryPoint(bearerAuthenticationEntryPoint)
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(service);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
