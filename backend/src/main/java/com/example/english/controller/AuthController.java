package com.example.english.controller;

import com.example.english.dto.LoginDTO;
import com.example.english.dto.ResponseMsg;
import com.example.english.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController extends BaseResponseController {
    private final static Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @Override
    protected Logger getLogger() {
        return this.logger;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseMsg> login(@Valid @RequestBody LoginDTO loginDto) {
        return response(userService.validateLogin(loginDto));
    }
}
