package com.example.english.dao.model;

import com.example.english.enums.BlockEnum;
import com.example.english.enums.GenderEnum;
import com.example.english.enums.UserTypeEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Where(clause = "deleted=0")
@Setter
@Getter
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(generator = "user_generator")
    @SequenceGenerator(
            name = "user_generator",
            sequenceName = "user_sq",
            initialValue = 1
    )
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "gender")
    private GenderEnum gender;

    @Column(name = "avatar", columnDefinition = "TEXT")
    private String avatar;

    @Column(name = "block")
    private BlockEnum block;

    @Column(name = "user_type")
    private UserTypeEnum userType;

    @Column(name = "salt")
    @Audited
    private String salt;

    @Transient
    private String loginToken;

}
