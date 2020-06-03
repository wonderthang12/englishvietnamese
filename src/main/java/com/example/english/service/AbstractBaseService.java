package com.example.english.service;

import com.example.english.dao.model.BaseEntity;
import com.example.english.dao.repository.BaseRepository;
import com.example.english.dto.BaseDTO;
import com.example.english.exception.NotFoundEntityException;
import com.example.english.msg.Msg;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractBaseService<X extends BaseEntity, Y extends BaseDTO, Z extends BaseRepository<X, Y, Long>> extends AbstractBaseEnvService implements BaseService<Y> {
    protected abstract Z getRepository();

    protected abstract ModelMapper getModelMapper();

    protected abstract Class<X> getEntityClass();

    protected abstract Class<Y> getDtoClass();

    final protected X mapToEntity(Y dto) {
        if (dto == null) {
            return null;
        }

        X entity = getModelMapper().map(dto, getEntityClass());

        if (dto.getActive() == null && dto.getId() != null && dto.getId() > 0) {
            entity.setActive(getRepository().getActiveById(dto.getId()));
        }

        specificMapToEntity(dto, entity);

        return entity;
    }

    final protected void mapToEntity(Y dto, X entity) {
        if (dto == null) {
            return;
        }

        getModelMapper().map(dto, entity);

        if (dto.getActive() == null && entity.getActive() != null && entity.getId() != null && entity.getId() > 0) {
            dto.setActive(entity.getActive());
        }

        specificMapToEntity(dto, entity);
    }

    final protected Y mapToDTO(X entity) {
        if (entity == null) {
            return null;
        }

        Y dto = getModelMapper().map(entity, getDtoClass());

        specificMapToDTO(entity, dto);

        return dto;
    }

    final protected void mapToDTO(X entity, Y dto) {
        if (entity == null) {
            return;
        }

        getModelMapper().map(entity, dto);

        specificMapToDTO(entity, dto);
    }

    protected void specificMapToDTO(X entity, Y dto) {

    }

    protected void specificMapToEntity(Y dto, X entity) {

    }

    protected List<X> update(List<X> entities, List<Y> dtos) {
        if ((entities == null || entities.size() == 0) && (dtos == null || dtos.size() == 0)) {
            return new ArrayList<>();
        }

        if ((entities == null || entities.size() == 0) && !(dtos == null || dtos.size() == 0)) {
            return dtos.stream().map(this::mapToEntity).collect(Collectors.toList());
        }

        if (!(entities == null || entities.size() == 0) && (dtos == null || dtos.size() == 0)) {
            entities.stream().forEach(line -> getRepository().delete(line));
            return new ArrayList<>();
        }

        entities.stream().filter(entity -> !dtos.stream().anyMatch(dto -> entity.getId().equals(dto.getId())))
                .forEach(line -> getRepository().delete(line));

        List<X> removedEntities = entities.stream().filter(entity -> dtos.stream().anyMatch(dto -> entity.getId().equals(dto.getId()))).map(entity -> {
            mapToEntity(dtos.stream().filter(dto -> entity.getId().equals(dto.getId())).findFirst().orElse(null), entity);
            return entity;
        }).collect(Collectors.toList());

        removedEntities.addAll(dtos.stream().filter(dto -> !removedEntities.stream().anyMatch(entity -> entity.getId().equals(dto.getId())))
                .map(dto -> mapToEntity(dto)).collect(Collectors.toList()));

        return removedEntities;
    }

    protected X getById(Long id) {
        return getRepository().findById(id).orElseThrow(() -> new NotFoundEntityException(Msg.getMessage("not.found.record", new Object[] {String.valueOf(id)})));
    }

    @Override
    public Y create(Y dto) {
        X model = mapToEntity(dto);

        return mapToDTO(getRepository().save(model));
    }

    @Override
    public Y update(Long id, Y dto) {
        if (id == null || id <= 0) {
            throw new NotFoundEntityException(Msg.getMessage("not.found.record", new Object[] {String.valueOf(id)}));
        }

        X model = getById(id);

        mapToEntity(dto, model);

        model.setId(id);

        return mapToDTO(getRepository().save(model));
    }

    @Override
    public void delete(Long id) {
        X model = getById(id);

        getRepository().delete(model);
    }

    @Override
    public Y findById(Long id) {
        X model = getById(id);

        return mapToDTO(model);
    }

    @Override
    public boolean existsById(Long id) {
        return getRepository().existsById(id);
    }

    @Override
    public Page<Y> search(Y dto, Pageable pageable) {
        return getRepository().search(dto, pageable).map(this::mapToDTO);
    }
}
