package com.snapp.boxdemo.dto;

import com.snapp.boxdemo.model.PriceRange;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link com.snapp.boxdemo.model.node.DestinationNode} entity
 */
@Data
public class DestinationNodeDto implements Serializable {
    private final String fullName;
    private final String phoneNumber;
    private final String addressBase;
    private final Integer addressHouseNumber;
    private final Integer addressHomeUnit;
    private final String comment;
    private final PriceRange priceRange;
}
