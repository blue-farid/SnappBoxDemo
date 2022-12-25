package com.snapp.boxdemo.repository;

import com.snapp.boxdemo.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByEmail(String mail);
}
