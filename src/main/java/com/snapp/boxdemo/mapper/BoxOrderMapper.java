package com.snapp.boxdemo.mapper;

import com.snapp.boxdemo.model.dto.BoxOrderDto;
import com.snapp.boxdemo.model.dto.DestinationNodeDto;
import com.snapp.boxdemo.model.entity.BoxOrder;
import com.snapp.boxdemo.model.entity.node.DestinationNode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BoxOrderMapper {
    BoxOrderMapper INSTANCE = Mappers.getMapper(BoxOrderMapper.class);

    @Mapping(target = "ownerId", source = "owner.id")
    @Mapping(target = "sourceAddressBase", source = "source.address.base")
    @Mapping(target = "sourcePhoneNumber", source = "source.phoneNumber")
    @Mapping(target = "sourceAddressHomeUnit", source = "source.address.homeUnit")
    @Mapping(target = "sourceAddressHouseNumber", source = "source.address.houseNumber")
    @Mapping(target = "sourceComment", source = "source.comment")
    @Mapping(target = "sourceFullName", source = "source.fullName")
    BoxOrderDto boxOrderToBoxOrderDto(BoxOrder order);

    @Mapping(source = "ownerId", target = "owner.id")
    @Mapping(source = "sourceAddressBase", target = "source.address.base")
    @Mapping(source = "sourcePhoneNumber", target = "source.phoneNumber")
    @Mapping(source = "sourceAddressHomeUnit", target = "source.address.homeUnit")
    @Mapping(source = "sourceAddressHouseNumber", target = "source.address.houseNumber")
    @Mapping(source = "sourceComment", target = "source.comment")
    @Mapping(source = "sourceFullName", target = "source.fullName")
    BoxOrder boxOrderDtoToBoxOrder(BoxOrderDto dto);

    @Mapping(target = "address.base", source = "addressBase")
    @Mapping(target = "address.homeUnit", source = "addressHomeUnit")
    @Mapping(target = "address.houseNumber", source = "addressHouseNumber")
    DestinationNode destinationNodeDtoToDestinationNode(DestinationNodeDto destinationNodeDto);

    @Mapping(source = "address.base", target = "addressBase")
    @Mapping(source = "address.homeUnit", target = "addressHomeUnit")
    @Mapping(source = "address.houseNumber", target = "addressHouseNumber")
    DestinationNodeDto destinationNodeDtoToDestinationNode(DestinationNode destinationNode);
}
