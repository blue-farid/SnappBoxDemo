package com.snapp.boxdemo.service;

import com.snapp.boxdemo.dto.BoxOrderDto;

public interface BoxOrderService {
    BoxOrderDto getBoxOrder(long id);
    void removeBoxOrder(long id);
    void saveOrUpdateBoxOrder(BoxOrderDto dto);
    boolean exist(long id);
}
