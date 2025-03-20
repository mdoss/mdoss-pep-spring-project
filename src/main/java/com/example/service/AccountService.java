package com.example.service;


import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AccountService {
    AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository AccountRepository){
        this.accountRepository = AccountRepository;
    }

    /**
     * Handles regristration of a new account
     * @param account new account
     * @return null if account cannot be added
     */
    public Account handleRegistration(Account account){
        if(account.getUsername().length() > 0 && account.getPassword().length() > 4)
        {
            Account acc = accountRepository.save(account);
            return acc;
        }
        return null;
    }
    /**
     * Handles login of an existing account
     * @param account 
     * @return null if account doesn't exist
     */
    public Account handleLogin(Account account){
        Account acc = accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
        return acc;
    }
    
    /**
     * Checks if a username is taken
     * @param account 
     * @return true if username is not taken
     */
    public Boolean usernameNotInUse(Account account) {
        if(accountRepository.findByUsername(account.getUsername()) == null)
            return true;
        return false;
    }
    /**
     * Gets all accounts in the database
     * @return
     */
    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }
    /**
     * Get an account
     * @param id ID to search for account
     * @return account with matching id
     */
    public Account getAccount(int id){
        return accountRepository.findByAccountId(id);
    }
    
}