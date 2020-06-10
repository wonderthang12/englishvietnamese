package com.example.english.dao.model;

import com.example.english.enums.LevelEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "menu")
@Where(clause = "deleted=0")
@Setter
@Getter
public class MenuEntity extends BaseEntity {
    @Id
    @GeneratedValue(generator = "menu_generator")
    @SequenceGenerator(
            name = "menu_generator",
            sequenceName = "menu_sq",
            initialValue = 1
    )
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private Integer location;

    @Column(name = "link")
    private String link;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "parent")
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    private List<MenuEntity> children;

    @Column(name = "parent_id")
    private Long parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    @JsonIgnore
    @NotFound(action = NotFoundAction.IGNORE)
    private MenuEntity parent;

    @Column(name = "level")
    private LevelEnum level = LevelEnum.LEVEL1;

    private String subMenu;
}
