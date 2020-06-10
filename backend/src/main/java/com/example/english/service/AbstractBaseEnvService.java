package com.example.english.service;

import com.example.english.dto.UserPrincipal;
import org.slf4j.Logger;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class AbstractBaseEnvService {
    protected abstract Logger getLogger();

    protected UserPrincipal getUserPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }

        return (UserPrincipal) authentication.getPrincipal();
    }

    protected String getUsername() {

        UserPrincipal userPrincipal = getUserPrincipal();

        return userPrincipal == null ? null : userPrincipal.getUsername();
    }

    protected Long getUserId() {
        UserPrincipal userPrincipal = getUserPrincipal();

        return userPrincipal == null ? null : userPrincipal.getId();
    }
}
