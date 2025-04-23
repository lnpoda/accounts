package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.AccountDto;
import com.eazybytes.accounts.dto.CustomerDto;
import com.eazybytes.accounts.entity.Account;
import com.eazybytes.accounts.entity.Customer;
import com.eazybytes.accounts.exception.CustomerAlreadyExistsException;
import com.eazybytes.accounts.exception.ResourceNotFoundException;
import com.eazybytes.accounts.mapper.AccountMapper;
import com.eazybytes.accounts.mapper.CustomerMapper;
import com.eazybytes.accounts.repository.AccountRepository;
import com.eazybytes.accounts.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService{

    private CustomerRepository customerRepository;
    private AccountRepository accountRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already exists with the mobile number: "+ customerDto.getMobileNumber());
        }
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());

        Customer savedCustomer = customerRepository.save(customer);
        accountRepository.save(createNewAccount(savedCustomer));

    }

    @Override
    public CustomerDto getAccount(String mobileNumber) {
        Optional<Customer> customer = customerRepository.findByMobileNumber(mobileNumber);
        if (customer.isEmpty()) {
            throw new ResourceNotFoundException("customer", "mobile number", mobileNumber);
        }

        Optional<Account> account = accountRepository.findByCustomerId(customer.get().getCustomerId());
        if (account.isEmpty()) {
            throw new ResourceNotFoundException("account", "customer id", customer.get().getCustomerId().toString());
        }

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer.get(), new CustomerDto());
        customerDto.setAccountDto(AccountMapper.mapToAccountDto(account.get(), new AccountDto()));

        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountDto accountDto = customerDto.getAccountDto();
        Account account = null;
        if (accountDto != null) {
            account = accountRepository.findById(accountDto.getAccountNumber())
                    .orElseThrow(() -> new ResourceNotFoundException("account", "account number", accountDto.getAccountNumber().toString()));

            AccountMapper.mapToAccount(accountDto, account);
            accountRepository.save(account);
            Long customerId = account.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(()->new ResourceNotFoundException("customer", "customer id", customerId.toString()));
            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }


        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        boolean isDeleted = false;
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(()-> new ResourceNotFoundException("customer", "mobile number", mobileNumber));
        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        isDeleted = true;

        return isDeleted;
    }

    private Account createNewAccount(Customer customer) {
        Account newAccount = new Account();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType("savings");
        newAccount.setBranchAddress("earth");

        return newAccount;
    }
}
