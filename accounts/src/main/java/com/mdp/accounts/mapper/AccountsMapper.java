package com.mdp.accounts.mapper;

import com.mdp.accounts.dto.AccountsDTO;
import com.mdp.accounts.entity.Accounts;

public class AccountsMapper {
    public static Accounts mapToAccounts(Accounts accounts, AccountsDTO accountsDTO) {
        accounts.setAccountNumber(accountsDTO.getAccountNumber());
        accounts.setAccountType(accountsDTO.getAccountType());
        accounts.setBranchAddress(accountsDTO.getBranchAddress());
        return accounts;
    }
    public static AccountsDTO mapToAccountsDTO(Accounts accounts, AccountsDTO accountsDTO) {
        accountsDTO.setAccountNumber(accounts.getAccountNumber());
        accountsDTO.setAccountType(accounts.getAccountType());
        accountsDTO.setBranchAddress(accounts.getBranchAddress());
        return accountsDTO;
    }
}
