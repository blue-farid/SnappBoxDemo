package com.snapp.boxdemo.model.dto;

import com.snapp.boxdemo.model.entity.Client;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A DTO for the {@link Client} entity
 */
@Data
public class ClientDto implements Serializable {
    private Long id;
    @NotBlank(message = "{error.valid.empty.sourceFullName}")
    @Size(max = 100, message = "{error.valid.length}")
    private String fullName;
    @Pattern(regexp = "^09\\d{9}$", message = "{error.valid.phone}")
    private String phoneNumber;
    @Pattern(regexp = "^\\S+@\\S+\\.\\S+$", message = "{error.valid.email}")
    private String email;
}