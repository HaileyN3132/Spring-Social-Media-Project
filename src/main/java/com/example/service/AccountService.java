package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }


    // Task 1: Register NEW account
    public Account resigterAccount(Account account){
        //Validate infor
        if((!account.getUsername().isEmpty()) && (account.getPassword().length() >= 4) ){
            return accountRepository.save(account);
        }
        return null;
    }


    //Helper method: check for duplication username
    public Boolean isDuplicate(Account account){
        if(accountRepository.findAccountByUsername(account.getUsername()) == null){ // Not existed
            return false;
        }
        return true;        
    }


    //Task 2: Login Account
    public Account loginAccount(Account account){
        Account registedAccount = accountRepository.findAccountByUsername(account.getUsername());
        if((registedAccount != null) && (registedAccount.getUsername().equals(account.getUsername())) 
        && (registedAccount.getPassword().equals(account.getPassword()))){    
            return registedAccount;               // Login Sucessfull
        }
        return null;
    }

    //Helper method
    public Account getAccountById(Integer accountId){
        return accountRepository.findAccountByAccountId(accountId);
    }

}
