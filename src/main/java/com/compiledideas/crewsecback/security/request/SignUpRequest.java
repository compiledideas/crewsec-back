package com.compiledideas.crewsecback.security.request;

import com.compiledideas.crewsecback.utils.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String password;
    private String salt;
    private String passwordReset;

    private Role role;
}