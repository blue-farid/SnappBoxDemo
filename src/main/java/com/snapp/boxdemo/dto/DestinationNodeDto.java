package com.snapp.boxdemo.dto;

import com.snapp.boxdemo.model.PriceRange;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A DTO for the {@link com.snapp.boxdemo.model.node.DestinationNode} entity
 */
@Data
public class DestinationNodeDto implements Serializable {
    private final Long id;
    @Size(max = 100, message = "fullName max size is 100!")
    @NotBlank(message = "fullName can't be empty!")
    private final String fullName;
    @Pattern(regexp = "^09\\d{9}$", message = "phoneNumber is not valid!")
    private final String phoneNumber;
    @Size(min = 10, max = 500, message = "addressBase length should be between 10 and 500!")
    private final String addressBase;
    @Pattern(regexp = "\\d{1,10}", message = "addressHouseNumber is not valid!")
    private final String addressHouseNumber;
    @Pattern(regexp = "\\d{1,10}", message = "addressHomeUnit is not valid!")
    private final String addressHomeUnit;
    @Size(max = 1000, message = "comment max size is 1000!")
    private final String comment;
    private final PriceRange priceRange;
}
