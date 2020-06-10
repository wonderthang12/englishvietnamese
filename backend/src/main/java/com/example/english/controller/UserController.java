package com.example.english.controller;

import com.example.english.dto.ResponseMsg;
import com.example.english.dto.UserDTO;
import com.example.english.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users")
@CrossOrigin(origins = "*")
public class UserController extends BaseController<UserDTO, UserService> {
    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService service;

    @Override
    protected UserService getService() {
        return service;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @Override
    public ResponseEntity<ResponseMsg> create(@RequestBody UserDTO dto) {
        return super.create(dto);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @Override
    public ResponseEntity<ResponseMsg> update(@PathVariable("id") Long id, @RequestBody UserDTO dto) {
        return super.update(id, dto);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @Override
    public ResponseEntity<ResponseMsg> delete(@PathVariable("id") Long id) {
        return super.delete(id);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @PutMapping("{id}/block")
    public ResponseEntity<?> block(@PathVariable("id") Long id) {
        return response(service.block(id));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @PutMapping(path = "/{id}/reset-password")
    public ResponseEntity<?> resetPassword(@PathVariable("id") Long id) {
        return response(service.resetPassword(id));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @PutMapping(path = "/{id}/password")
    public ResponseEntity<?> updatePassword(@PathVariable("id") Long id, @RequestBody UserDTO userDto) {
        return response(service.updatePassword(id, userDto));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @PutMapping(path = "/{id}/email")
    public ResponseEntity<?> updateEmail(@PathVariable("id") Long id, @RequestBody UserDTO userDto) {
        return response(service.updateEmail(id, userDto));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @PutMapping(path = "/{id}/avatar")
    public ResponseEntity<?> updateAvatar(@PathVariable("id") Long id, @RequestBody UserDTO userDto) {
        return response(service.updateAvatar(id, userDto));
    }
}
