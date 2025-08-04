package com.mdp.accounts.controller;

import com.mdp.accounts.dto.CustomerDTO;
import com.mdp.accounts.dto.ResponseDTO;
import com.mdp.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@AllArgsConstructor
public class AccountsController {
    private IAccountsService accountsService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createAccount(@RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.ok().body(accountsService.createAccount(customerDTO));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDTO> fetchAccount(@RequestParam String mobileNumber) {
        return ResponseEntity.ok().body(accountsService.fetchAccount(mobileNumber));
    }

}
