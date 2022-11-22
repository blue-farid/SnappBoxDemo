package com.snapp.boxdemo.mapper;

import com.snapp.boxdemo.dto.BoxOrderDto;
import com.snapp.boxdemo.model.BoxOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BoxOrderMapper {
    BoxOrderMapper INSTANCE = Mappers.getMapper(BoxOrderMapper.class);

    @Mapping(target = "ownerFullName", source = "owner.fullName")
    @Mapping(target = "ownerPhoneNumber", source = "owner.phoneNumber")
    @Mapping(target = "sourceAddressBase", source = "source.address.base")
    @Mapping(target = "sourcePhoneNumber", source = "source.phoneNumber")
    @Mapping(target = "sourceAddressHomeUnit", source = "source.address.homeUnit")
    @Mapping(target = "sourceAddressHouseNumber", source = "source.address.houseNumber")
    @Mapping(target = "sourceComment", source = "source.comment")
    @Mapping(target = "sourceFullName", source = "source.fullName")
    BoxOrderDto boxOrderToBoxOrderDto(BoxOrder order);
    @Mapping(source = "ownerFullName", target = "owner.fullName")
    @Mapping(source = "ownerPhoneNumber", target = "owner.phoneNumber")
    @Mapping(source = "sourceAddressBase", target = "source.address.base")
    @Mapping(source = "sourcePhoneNumber", target = "source.phoneNumber")
    @Mapping(source = "sourceAddressHomeUnit", target = "source.address.homeUnit")
    @Mapping(source = "sourceAddressHouseNumber", target = "source.address.houseNumber")
    @Mapping(source = "sourceComment", target = "source.comment")
    @Mapping(source = "sourceFullName", target = "source.fullName")
    BoxOrder boxOrderDtoToBoxOrder(BoxOrderDto dto);
}
