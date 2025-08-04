package com.mdp.accounts.service.imp;

import com.mdp.accounts.dto.AccountsDTO;
import com.mdp.accounts.dto.CustomerDTO;
import com.mdp.accounts.dto.ResponseDTO;
import com.mdp.accounts.entity.Accounts;
import com.mdp.accounts.entity.Customer;
import com.mdp.accounts.exception.CustomerAlreadyExistsException;
import com.mdp.accounts.exception.ResourceNotFoundException;
import com.mdp.accounts.mapper.AccountsMapper;
import com.mdp.accounts.mapper.CustomerMapper;
import com.mdp.accounts.repository.AccountsRepository;
import com.mdp.accounts.repository.CustomerRepository;
import com.mdp.accounts.service.IAccountsService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

import static com.mdp.accounts.constants.AccountsConstants.*;
import static com.mdp.accounts.mapper.CustomerMapper.mapToCustomer;

@Service
public class AccountsServiceImpl implements IAccountsService {

    private final CustomerRepository customerRepository;
    private final AccountsRepository accountsRepository;

    public AccountsServiceImpl(CustomerRepository customerRepository, AccountsRepository accountsRepository) {
        this.customerRepository = customerRepository;
        this.accountsRepository = accountsRepository;
    }

    /**
     * Method to create account
     *
     * @param customerDTO - Contains customer details:
     *                    1. Name
     *                    2. Email
     *                    3. Mobile number
     */
    @Override
    public ResponseDTO createAccount(CustomerDTO customerDTO) {
        boolean mobileNumberExists = customerRepository.existsByMobileNumber(customerDTO.getMobileNumber());
        if (mobileNumberExists) {
            throw new CustomerAlreadyExistsException("Customer with mobile number: " + customerDTO.getMobileNumber() + " already exists");
        }

        Customer customer = mapToCustomer(new Customer(), customerDTO);
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Admin");
        Customer savedCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(savedCustomer));
        return new ResponseDTO(HttpStatus.CREATED, MESSAGE_201);
    }

    /**
     * @param mobileNumber - Mobile number of customer
     * @return CustomerDTO - CustomerDTO object
     */
    @Override
    public CustomerDTO fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Accounts", "customerId", customer.getCustomerId().toString()));
        CustomerDTO customerDTO = CustomerMapper.mapToCustomerDTO(customer, new CustomerDTO());
        customerDTO.setAccountDetails(AccountsMapper.mapToAccountsDTO(accounts, new AccountsDTO()));
        return customerDTO;
    }

    /**
     * Method to create new account
     *
     * @param customer - Customer object with ID
     * @return - Account object containing:
     * 1. Account number
     * 2. Account type
     * 3. Branch address
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts accounts = new Accounts();
        accounts.setCustomerId(customer.getCustomerId());
        long randomAccountNumber = 1000000000L + new Random().nextInt(900000000);
        accounts.setAccountNumber(randomAccountNumber);
        accounts.setAccountType(ACCOUNT_TYPE_SAVINGS);
        accounts.setBranchAddress(NEWYORK);
        accounts.setCreatedAt(LocalDateTime.now());
        accounts.setCreatedBy("Admin");
        return accounts;
    }
}
