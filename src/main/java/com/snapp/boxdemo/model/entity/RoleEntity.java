package com.snapp.boxdemo.model.entity;

import com.snapp.boxdemo.security.Role;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
public class RoleEntity implements GrantedAuthority {
    @Id
    private Long id;
    @Enumerated(EnumType.STRING)
    private Role name;

    @Override
    public String getAuthority() {
        return "ROLE_" + name.name();
    }
}
