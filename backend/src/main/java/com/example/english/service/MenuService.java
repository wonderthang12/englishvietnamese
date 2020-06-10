package com.example.english.service;

import com.example.english.dto.LevelDTO;
import com.example.english.dto.MenuDTO;

import java.util.List;
import java.util.Map;

public interface MenuService extends BaseService<MenuDTO> {
    MenuDTO active(Long id);
}
