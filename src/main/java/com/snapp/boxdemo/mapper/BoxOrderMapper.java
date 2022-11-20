package com.snapp.boxdemo.mapper;

import com.snapp.boxdemo.dto.BoxOrderDto;
import com.snapp.boxdemo.model.BoxOrder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BoxOrderMapper {
    BoxOrderMapper INSTANCE = Mappers.getMapper(BoxOrderMapper.class);

    BoxOrderDto boxOrderToBoxOrderDto(BoxOrder order);

    BoxOrder boxOrderDtoToBoxOrder(BoxOrderDto dto);
}
