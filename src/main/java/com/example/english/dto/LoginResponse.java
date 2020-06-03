package com.example.english.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LoginResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("main_department")
    private Department mainDepartment;

    @JsonProperty("departments")
    private Department[] departments;

    @JsonProperty("roles")
    private Role[] roles;

    @JsonProperty("email")
    private String email;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("expires_in")
    private Long expiresIn;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("jti")
    private String jti;

    @Setter
    @Getter
    @NoArgsConstructor
    public static class Department {
        private Long id;

        private String value;

        private String name;

        private Boolean active;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    public static class Role {
        private Long id;

        private String value;

        private String name;

        private Boolean active;
    }
}
