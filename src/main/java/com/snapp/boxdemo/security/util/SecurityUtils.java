package com.snapp.boxdemo.security.util;

import com.snapp.boxdemo.model.security.ClientUserDetails;
import com.snapp.boxdemo.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Locale;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

    private final MessageSource source;

    private boolean hasRole(Collection<? extends GrantedAuthority> grantedAuthorities, Role role) {
        for (GrantedAuthority grantedAuthority : grantedAuthorities) {
            if (grantedAuthority.getAuthority().equals(role.name()))
                return true;
        }
        return false;
    }

    public void checkOwner(Long ownerId, Locale locale) {
        ClientUserDetails principal = (ClientUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!hasRole(principal.getAuthorities(), Role.ROLE_ADMIN) && !Objects.isNull(ownerId) &&
                !principal.getUsername().equals(ownerId.toString())) {
            throw new AccessDeniedException(source.getMessage("access.denied.ownerId", null, locale));
        }
    }


}
