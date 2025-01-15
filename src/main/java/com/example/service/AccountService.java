package com.example.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account registerAccount(Account account) throws JpaSystemException, RuntimeException{
        if (account.getUsername() != "" || account.getPassword().length() >= 4) {
            if (accountRepository.findByUsername(account.getUsername()).isEmpty()) {
                return accountRepository.save(account);
            }
            else {
                throw new JpaSystemException(new RuntimeException("Username already in use."));
            }
        }
        else {
            throw new RuntimeException("Username or password not valid.");
        }
    }

    public Account loginAccount(Account account) throws RuntimeException{
        if (!accountRepository.findByUsername(account.getUsername()).isEmpty()) {
            Account foundAccount = accountRepository.findByUsername(account.getUsername()).get(0);
            if (foundAccount.getPassword().equals(account.getPassword())) {
                return foundAccount;
            }
        }

        throw new RuntimeException("Incorrect username or password.");
    }
}
