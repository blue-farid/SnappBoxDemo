package com.snapp.boxdemo.model.dto;

import com.snapp.boxdemo.model.entity.PriceRange;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A DTO for the {@link com.snapp.boxdemo.model.entity.node.DestinationNode} entity
 */
@Data
public class DestinationNodeDto implements Serializable {
    private Long id;
    @Size(max = 100, message = "{error.valid.length}")
    @NotBlank(message = "{error.valid.empty}")
    private String fullName;
    @Pattern(regexp = "^09\\d{9}$", message = "{error.valid}")
    private String phoneNumber;
    @Size(min = 10, max = 500, message = "{error.valid.length}")
    private String addressBase;
    @Pattern(regexp = "\\d{1,10}", message = "{error.valid}")
    private String addressHouseNumber;
    @Pattern(regexp = "\\d{1,10}", message = "{error.valid}")
    private String addressHomeUnit;
    @Size(max = 1000, message = "{error.valid.length}")
    private String comment;
    private PriceRange priceRange;
}
