package com.compiledideas.crewsecback.security.request;

import com.compiledideas.crewsecback.security.models.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    private String firstname;
    private String lastname;
    private String displayName;
    private String avatar;
    private String email;
    private String password;
    private String phone;
    private List<Role> roles;
}