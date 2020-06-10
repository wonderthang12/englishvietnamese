package com.example.english.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Setter
@Getter
public class UserPrincipal implements UserDetails, Serializable {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private BaseCategoryDTO mainDepartment;
    private String password;
    private boolean isEnabled;
    private List<String> privileges;
    private BaseCategoryDTO role;
    private BaseCategoryDTO department;

    public UserPrincipal(Long id, String username, String fullName, String email, String password, boolean isEnabled) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.isEnabled = isEnabled;

        this.privileges = new ArrayList<>();
    }


    public UserPrincipal(Long id, String username, String fullName, String email, String password, boolean isEnabled, List<String> privileges, BaseCategoryDTO role, BaseCategoryDTO department) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.isEnabled = isEnabled;
        this.privileges = privileges;
        this.role = role;
        this.department = department;
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
        if (privileges != null) {
            return AuthorityUtils.createAuthorityList(privileges.toArray(new String[privileges.size()]));
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
