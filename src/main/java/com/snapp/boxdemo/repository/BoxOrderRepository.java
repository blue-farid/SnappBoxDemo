package com.snapp.boxdemo.repository;

import com.snapp.boxdemo.model.entity.BoxOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoxOrderRepository extends JpaRepository<BoxOrder, Long> {

}
