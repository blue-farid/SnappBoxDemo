package com.snapp.boxdemo.model.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class ClientUserDetails implements UserDetails {
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;
    private String password;
    private String username;
    private Collection<? extends GrantedAuthority> authorities;
}
