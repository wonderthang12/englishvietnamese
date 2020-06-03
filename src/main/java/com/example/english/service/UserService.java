package com.example.english.service;

import com.example.english.dto.LoginDTO;
import com.example.english.dto.UserDTO;

public interface UserService extends BaseService<UserDTO> {
    String validateLogin(LoginDTO loginDTO);

    UserDTO block(Long id);

    UserDTO resetPassword(Long id);

    UserDTO updatePassword(Long id, UserDTO userDto);

    UserDTO updateEmail(Long id, UserDTO userDto);

    UserDTO updateAvatar(Long id, UserDTO userDto);

    void init();

    String getUsernameById(Long id);
}
