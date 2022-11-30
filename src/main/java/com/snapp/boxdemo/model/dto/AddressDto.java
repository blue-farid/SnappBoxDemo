package com.snapp.boxdemo.model.dto;

import com.snapp.boxdemo.model.entity.Address;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link Address} entity
 */
@Data
public class AddressDto implements Serializable {
    private final Long id;
    private final String base;
    private final String houseNumber;
    private final String homeUnit;
}