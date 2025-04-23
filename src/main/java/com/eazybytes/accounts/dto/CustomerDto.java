package com.eazybytes.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {

    @NotEmpty(message = "name cannot be empty")
    @Size(min = 2, max = 30, message = "name must be between 2 to 30 characters")
    private String name;

    @NotEmpty(message = "mobile number cannot be empty")
    private String mobileNumber;

    @NotEmpty(message = "email cannot be empty")
    private String email;

    private AccountDto accountDto;
}
