package com.snapp.boxdemo.repository;

import com.snapp.boxdemo.model.entity.RoleEntity;
import com.snapp.boxdemo.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleEntityRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(Role name);
}
