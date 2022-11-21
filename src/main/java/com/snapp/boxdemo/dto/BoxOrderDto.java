package com.snapp.boxdemo.dto;

import com.snapp.boxdemo.model.OrderType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link com.snapp.boxdemo.model.BoxOrder} entity
 */
@Data
public class BoxOrderDto implements Serializable {
    private final Long id;
    @NotBlank(message = "ownerFullName can't empty!")
    @Size(max = 100, message = "ownerFullName max size is 100!")
    private final String ownerFullName;
    @Pattern(regexp = "^09\\d{9}$", message = "phone number is not valid!")
    private final String ownerPhoneNumber;
    @NotBlank(message = "sourceFullName can't be empty!")
    @Size(max = 100, message = "sourceFullName max size is 100!")
    private final String sourceFullName;
    @Pattern(regexp = "^09\\d{9}$", message = "sourcePhoneNumber is not valid!")
    private final String sourcePhoneNumber;
    @Size(min = 10, max = 500, message = "sourceAddressBase length should be between 10 and 500!")
    private final String sourceAddressBase;
    @Pattern(regexp = "\\d{1,10}", message = "sourceAddressHouseNumber is not valid!")
    private final String sourceAddressHouseNumber;
    @Pattern(regexp = "\\d{1,10}", message = "sourceAddressHouseUnit is not valid!")
    private final String sourceAddressHomeUnit;
    @Size(max = 1000, message = "source max size is 1000!")
    private final String sourceComment;
    private final List<DestinationNodeDto> destinations;
    private final OrderType orderType;
}