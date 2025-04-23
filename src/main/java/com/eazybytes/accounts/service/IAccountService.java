package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.CustomerDto;

public interface IAccountService {

    public void createAccount(CustomerDto customerDto);

    public CustomerDto getAccount(String mobileNumber);

    public boolean updateAccount(CustomerDto customerDto);

    public boolean deleteAccount(String mobileNumber);
}
