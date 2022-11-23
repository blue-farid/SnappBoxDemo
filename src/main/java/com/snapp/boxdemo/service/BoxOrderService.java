package com.snapp.boxdemo.service;

import com.snapp.boxdemo.model.dto.BoxOrderDto;
import com.snapp.boxdemo.model.search.BoxOrderSearchWrapper;

import java.util.List;

public interface BoxOrderService {
    BoxOrderDto getBoxOrder(long id);
    void removeBoxOrder(long id);
    BoxOrderDto updateBoxOrder(BoxOrderDto dto);

    BoxOrderDto saveBoxOrder(BoxOrderDto dto);
    boolean exist(long id);

    List<BoxOrderDto> getAll();

    List<BoxOrderDto> searchBoxOrders(BoxOrderSearchWrapper wrapper, int page);
}
