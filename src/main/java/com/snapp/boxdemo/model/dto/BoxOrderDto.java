package com.snapp.boxdemo.model.dto;

import com.snapp.boxdemo.model.entity.OrderType;
import com.snapp.boxdemo.model.entity.BoxOrder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link BoxOrder} entity
 */
@Data
public class BoxOrderDto implements Serializable {
    private Long id;
    @NotNull(message = "{error.valid.empty.ownerId}")
    private Long ownerId;
    @NotBlank(message = "{error.valid.empty.sourceFullName}")
    @Size(max = 100, message = "{error.valid.length}")
    private String sourceFullName;
    @Pattern(regexp = "^09\\d{9}$", message = "{error.valid.phone}")
    private String sourcePhoneNumber;
    @Size(min = 10, max = 500, message = "{error.valid.length}")
    private String sourceAddressBase;
    @Pattern(regexp = "\\d{1,10}", message = "{error.valid}")
    private String sourceAddressHouseNumber;
    @Pattern(regexp = "\\d{1,10}", message = "{error.valid}")
    private String sourceAddressHomeUnit;
    @Size(max = 1000, message = "{error.valid}")
    private String sourceComment;
    @NotNull(message = "{error.valid.empty}")
    private Double sourceX;
    @NotNull(message = "{error.valid.empty}")
    private Double sourceY;
    private Double price;
    @Valid
    private List<DestinationNodeDto> destinations;
    private OrderType orderType;
}