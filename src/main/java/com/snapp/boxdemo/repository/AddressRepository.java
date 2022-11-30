package com.snapp.boxdemo.repository;

import com.snapp.boxdemo.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
