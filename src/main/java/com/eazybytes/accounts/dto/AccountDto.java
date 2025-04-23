package com.eazybytes.accounts.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AccountDto {


    @NotEmpty(message = "account type cannot be empty")
    private String accountType;

    @NotEmpty(message = "account number cannot be empty")
    private Long accountNumber;

    @NotEmpty(message = "branch address cannot be empty")
    private String branchAddress;
}
