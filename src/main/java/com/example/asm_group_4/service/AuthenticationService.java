package com.example.asm_group_4.service;

import com.example.asm_group_4.model.Account;
import com.example.asm_group_4.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

    AccountRepository accountRepository;

    public Account validAccount(String username, String password) {
        return accountRepository.findByUsernameAndPassword(username, password);
    }



}
