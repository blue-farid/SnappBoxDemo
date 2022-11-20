package com.snapp.boxdemo.dto;

import com.snapp.boxdemo.model.OrderType;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * A DTO for the {@link com.snapp.boxdemo.model.BoxOrder} entity
 */
@Data
public class BoxOrderDto implements Serializable {
    private final String ownerFullName;
    private final String ownerPhoneNumber;
    private final String sourceFullName;
    private final String sourcePhoneNumber;
    private final String sourceAddressBase;
    private final Integer sourceAddressHouseNumber;
    private final Integer sourceAddressHomeUnit;
    private final String sourceComment;
    private final List<DestinationNodeDto> destinations;
    private final OrderType orderType;
    private final Date creationDate;
}