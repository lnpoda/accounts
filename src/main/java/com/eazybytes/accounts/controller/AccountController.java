package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.dto.AccountContactInfoDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.dto.ResponseDto;
import com.eazybytes.accounts.service.IAccountService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class AccountController {

    private IAccountService accountService;

    @Autowired
    private AccountContactInfoDto accountContactInfoDto;

    public AccountController(IAccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping("/account")
    public ResponseEntity<CustomerDto> getAccount(@RequestParam String mobileNumber) {
        CustomerDto customerDto = accountService.getAccount(mobileNumber);
        return new ResponseEntity<>(customerDto, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        accountService.createAccount(customerDto);
        return new ResponseEntity<>(new ResponseDto(HttpStatus.CREATED, "account created successfully"), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccount(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = accountService.updateAccount(customerDto);
        if (isUpdated) {
            return new ResponseEntity<>(new ResponseDto(HttpStatus.OK, "account updated"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "cannot update account"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam String mobileNumber) {
        boolean isDeleted = accountService.deleteAccount(mobileNumber);
        if (isDeleted) {
            return new ResponseEntity<>(new ResponseDto(HttpStatus.OK, "account updated"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "cannot updated account"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/contact-info")
    public ResponseEntity<AccountContactInfoDto> getContactInfo() {
        return new ResponseEntity<>(accountContactInfoDto, HttpStatus.OK);
    }
}
