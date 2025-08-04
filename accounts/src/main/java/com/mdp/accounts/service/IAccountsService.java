package com.mdp.accounts.service;

import com.mdp.accounts.dto.CustomerDTO;
import com.mdp.accounts.dto.ResponseDTO;

public interface IAccountsService {
    /**
     *
     * @param customerDTO - Customer DTO object
     */
    ResponseDTO createAccount(CustomerDTO customerDTO);

    /**
     *
     * @param mobileNumber - Mobile number of customer
     * @return CustomerDTO - CustomerDTO object
     */
    CustomerDTO fetchAccount(String mobileNumber);
}
