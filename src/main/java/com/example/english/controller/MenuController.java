package com.example.english.controller;

import com.example.english.dto.MenuDTO;
import com.example.english.service.MenuService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/menus")
public class MenuController extends BaseController<MenuDTO, MenuService> {

    private final static Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private MenuService service;

    @Override
    protected MenuService getService() {
        return service;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "Authorization", value = "Authorization token",
                    required = true, dataType = "string", paramType = "header")})
    @PutMapping("/{id}/active")
    public ResponseEntity<?> active(@PathVariable Long id) {
        return response(service.active(id));
    }
}
