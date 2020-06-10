package com.example.english.service;

import com.example.english.dao.model.MenuEntity;
import com.example.english.dao.repository.MenuRepository;
import com.example.english.dto.LevelDTO;
import com.example.english.dto.MenuDTO;
import com.example.english.exception.BaseException;
import com.example.english.msg.Msg;
import org.apache.commons.lang3.ArrayUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class MenuServiceImpl extends AbstractBaseService<MenuEntity, MenuDTO, MenuRepository> implements MenuService {

    private final static Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

    private static ModelMapper modelMapper = null;

    @Autowired
    MenuRepository repository;

    @Override
    protected MenuRepository getRepository() {
        return repository;
    }

    @Override
    protected ModelMapper getModelMapper() {
        if (modelMapper == null) {
            modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel(Configuration.AccessLevel.PRIVATE).setMatchingStrategy(MatchingStrategies.STRICT);
        }
        return modelMapper;
    }

    @Override
    protected Class<MenuEntity> getEntityClass() {
        return MenuEntity.class;
    }

    @Override
    protected Class<MenuDTO> getDtoClass() {
        return MenuDTO.class;
    }

    @Override
    protected Logger getLogger() {
        return logger;
    }

    @Override
    public MenuDTO create(MenuDTO dto) {
        MenuEntity entity = mapToEntity(dto);
        if (entity.getActive() == false) {
            if (repository.existsByLocationAndLevelAndParentId(dto.getLocation(), dto.getLevel(), dto.getParentId()) && dto.getActive()) {
                throw new BaseException(1003, Msg.getMessage("exist.account.with.local"));
            }
        }

        MenuEntity checkName = repository.findByLocationAndLevelAndName(entity.getLocation(), entity.getLevel(), entity.getName());
        if (checkName != null && entity.getActive()) {
            throw new BaseException(1005, Msg.getMessage("Tồn tại menu có level là " + entity.getLevel() + " có vị trí là " + entity.getLocation()) + " với tên là" + entity.getName());
        }

        if (dto.getParentId() == null) {
            entity.setParentId(null);
        }

        entity.setParentId(dto.getParentId());

        if (entity.getLocation() == 0) {
            entity.setActive(false);
        }
        repository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public MenuDTO update(Long id, MenuDTO dto) {
        MenuEntity entity = getById(id);

        if (repository.existsByParentId(dto.getParentId())) {
            entity.setParentId(dto.getParentId());
        }

        if (!entity.getName().equals(dto.getName()) && ArrayUtils.isNotEmpty(repository.findByNameIgnoreCase(dto.getName()).toArray())) {
            throw new BaseException(1001, Msg.getMessage("exist.name"));
        }

        if (dto.getActive() == true) {
            if (!Objects.equals(entity.getLocation(), dto.getLocation()) || !Objects.equals(entity.getLevel(), dto.getLevel()) || !Objects.equals(entity.getParentId(), dto.getParentId())) {
                if (ArrayUtils.isNotEmpty(repository.findMenuByParentId(dto.getParentId(), dto.getId(), dto.getLocation(), dto.getLevel(), true).toArray())) {
                    throw new BaseException(1003, Msg.getMessage("exist.account.with.local"));
                }
            }
        }
        entity.setParentId(dto.getParentId());

        if (dto.getLocation() == 0) {
            entity.setLocation(0);
            entity.setActive(false);
        }

        if (dto.getActive() == false && dto.getLocation() == 0) {
            throw new BaseException(1002, Msg.getMessage("change.local"));
        }

        entity.setLocation(dto.getLocation());
        entity.setActive(dto.getActive());
        entity.setName(dto.getName());
        entity.setLink(dto.getLink());
        repository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public void delete(Long id) {
        MenuEntity entity = getById(id);

        if (entity.getChildren().size() > 0) {
            throw new BaseException(1002, Msg.getMessage("exist.menu.children"));
        }
        entity.setParentId(null);
        repository.delete(entity);
    }

    @Override
    public MenuDTO active(Long id) {
        MenuEntity entity = getById(id);
        if (entity == null) {
            throw new BaseException(1001, Msg.getMessage("not.exist"));
        }

        if (entity.getActive() == false && entity.getLocation() == 0) {
            throw new BaseException(1002, Msg.getMessage("change.local"));
        }
        if (entity.getActive() == false) {
            MenuEntity check = repository.findByByParentId(entity.getParentId(), entity.getLevel(), entity.getLocation());
            if (check != null) {
                throw new BaseException(1003, Msg.getMessage("exist.menu.children"));
            }
        }

        if (entity.getActive() == false) {
            List<MenuEntity> menus = entity.getChildren();
            for (MenuEntity menu : menus) {
                menu.setActive(false);
                menu.setLocation(0);
                List<MenuEntity> menus1 = menu.getChildren();
                for (MenuEntity menu1 : menus1) {
                    menu1.setActive(false);
                    menu1.setLocation(0);
                }

            }
        }

        entity.setActive(!entity.getActive());
        //Neu cac vi tri khac 0 va 1 sau khi huy active thi vi tri reset ve 0
        if (entity.getLocation() != 0 || entity.getLocation() != 1) {
            if (entity.getActive() == false) {
                entity.setLocation(0);
            }
        }
        repository.save(entity);
        return null;
    }
}
