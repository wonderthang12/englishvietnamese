package com.example.english.dao.repository;

import com.example.english.dao.model.MenuEntity;
import com.example.english.dto.LevelDTO;
import com.example.english.dto.MenuDTO;
import com.example.english.enums.LevelEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends BaseRepository<MenuEntity, MenuDTO, Long>  {

    @Query(value = "select n from MenuEntity n" +
            " where 1 = 1 " +
            " and ( n.parentId  = :parentId or :parentId is null)" +
            " and ( n.id = :id or :id is null)" +
            " and ( n.location = :location or :location is null)" +
            " and ( n.level = :level or :level is null)" +
            " and ( n.active = :active)")
    List<MenuEntity> findMenuByParentId(Long parentId, Long id, Integer location, LevelEnum level, Boolean active);

    List<MenuEntity> findByLevelAndName(LevelEnum level, String name);

    List<MenuEntity> findByNameIgnoreCase(String name);

    MenuEntity findFirstByNameIgnoreCase(String name);

    boolean existsByName(String name);

    MenuEntity findByLink(String link);

    boolean existsByLink(String link);

//    @Override
//    @Query(value = "select distinct n from MenuEntity n" +
//            " left join MenuEntity mp on mp.parentId = n.id" +
//            " where 1 = 1 " +
//            " and ( n.menuType = :#{#dto.menuType} or :#{#dto.menuType} is null)" +
//            " and ( lower(n.name) like lower(concat('%', cast(:#{#dto.name} as string ), '%')) or :#{#dto.name} is null)" +
//            " and ( n.level = :#{#dto.level} or :#{#dto.level} is null)" +
//            " and ( n.parentId = :#{#dto.parentId} or :#{#dto.parentId} is null)" +
//            " and ( lower(n.link) like lower(concat('%', cast(:#{#dto.link} as string), '%')) or :#{#dto.link} is null)" +
//            " and ( n.location = :#{#dto.location} or :#{#dto.location} is null)" +
//            " and ( n.active = :#{#dto.active} or :#{#dto.active} is null)" +
//            " and ( lower(n.name) like lower(concat('%',cast(:#{#dto.name} as string ), '%')) or :#{#dto.name} is null)" +
//            " and ( lower(mp.name) like lower(concat('%',cast(:#{#dto.subMenu} as string ), '%')) or :#{#dto.subMenu} is null)" +
//            " and ( n.role = :#{#dto.role} or :#{#dto.role} is null)" +
//            " and ( trunc(n.createdAt)  = :#{#dto.createdAt} or cast(:#{#dto.createdAt} as string) is null )")
//    Page<MenuEntity> search(MenuDTO dto, Pageable pageable);

    @Query(value = " select distinct n from MenuEntity n" +
            " left join MenuEntity m on m.parentId = n.id" +
            " where 1 = 1" +
            " and (n.parentId = :parentId)")
    MenuEntity findMenuEntityByParentId(Long parentId);

    boolean existsByLocationAndLevelAndParentId(Integer location, LevelEnum level, Long parentId);

    boolean existsByParentId(Long parentId);

    boolean existsByLocationAndActive(Integer location, Boolean active);

    @Query(value = "select n from MenuEntity n" +
            " where 1 = 1" +
            " and (n.level = :level or :level is null )" +
            " and (n.location = :location or :location is null )" +
            " and (n.active = :active)")
    List<MenuEntity> findByLevelAndLocationAAndActive(LevelEnum level, Integer location, Boolean active);

    @Query(value = " select n from MenuEntity n" +
            " where 1 = 1" +
            " and (n.name = :name or :name is null)")
    MenuEntity findByChildren(String name);

    @Query(value = "select n from MenuEntity n" +
            " where 1 = 1" +
            " and (n.id = :id or :id is null )")
    MenuEntity findMenuEntityById(Long id);

    @Query(value = " select n from MenuEntity n" +
            " where 1 = 1" +
            " and (n.parentId = :parentId)" +
            " and (n.level = :level)" +
            " and (n.location = :location)" +
            " and (n.active = true)")
    MenuEntity findByByParentId(Long parentId, LevelEnum level, Integer location);

    List<MenuEntity> findByActiveAndLevel(Boolean active , LevelEnum levelEnum);

    @Query("select e from MenuEntity e " +
            " where 1 = 1" +
            " and ( e.active = :#{#dto.active} or :#{#dto.active} is null)" +
            " and ( e.level = :#{#dto.level} or :#{#dto.level} is null)")
    List<MenuEntity> findMenu(LevelDTO dto);

    MenuEntity findByLocationAndLevelAndName(Integer location, LevelEnum levelEnum, String name);
}
