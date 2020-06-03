package com.example.english.dto;

import com.example.english.enums.BlockEnum;
import com.example.english.enums.GenderEnum;
import com.example.english.enums.UserTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDTO extends BaseDTO {
    private String name;

    private String email;

    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private GenderEnum gender;

    private String avatar;

    private BlockEnum block;

    private UserTypeEnum userType;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String salt;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String newPassword;

    private String createdName;

    private String updatedName;
}
