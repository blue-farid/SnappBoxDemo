package com.snapp.boxdemo.repository;

import com.snapp.boxdemo.model.BoxOrder;
import org.springframework.data.repository.CrudRepository;

public interface BoxOrderRepository extends CrudRepository<BoxOrder, Long> {

}
