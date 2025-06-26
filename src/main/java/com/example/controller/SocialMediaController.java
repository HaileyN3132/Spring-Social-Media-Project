package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import java.util.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }


    //Task 1: Register NEW account
    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account){
        //check duplicate username
        if(accountService.isDuplicate(account)){
            //Status = 409 (Conflict)
            return ResponseEntity.status(409).body(null);
        }else{
            Account registeredAccount = accountService.resigterAccount(account);
            if(registeredAccount != null){
                return ResponseEntity.status(200).body(registeredAccount);      // Sucessfull
            }
        }
        return ResponseEntity.status(400).body(null);       // Failed to register
    }



    //Task 2: login 
    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account){
        Account returnedAccount = accountService.loginAccount(account);
        if(returnedAccount != null){
            return ResponseEntity.status(200).body(returnedAccount);
        }
        return ResponseEntity.status(401).body(null); // Login failed - Unauthorized

    }

    //Task 3: create NEW message
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message){
        // Check if Message's user is existed
        if(accountService.getAccountById(message.getPostedBy()) != null){
            Message createdMessage = messageService.createMessage(message);
            if(createdMessage != null){
                return ResponseEntity.status(200).body(createdMessage);         // Sucessfull
            }
            
        }
        // Failed to create message
        return ResponseEntity.status(400).body(null);  
    }
    

    //Task 4: retrieve ALL messages
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessage(){
        List<Message> messages = messageService.retrieveAllMessage();
        return ResponseEntity.status(200).body(messages);
    }
    

    // Task 5: get Message by messageId
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId){
        Message message = messageService.getMessageById(messageId);
        return ResponseEntity.status(200).body(message);
    }


    //Task 6: delete Message by messageId
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable Integer messageId){
        if(messageService.deleteMessageById(messageId)){
            return ResponseEntity.status(200).body(1);
        }
        return ResponseEntity.status(200).body(null);
    }


    //Task 7: update message
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessage(@PathVariable Integer messageId, @RequestBody Message message){
        
        if(messageService.updateMessage(message.getMessageText(), messageId)){
            return ResponseEntity.status(200).body(1);
        }
        return ResponseEntity.status(400).body(null);
    }

    //Task 8: get all messages for specific Account
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessagesFor(@PathVariable Integer accountId){
        List<Message> messages = messageService.getAllMessagesFor(accountId);
        return ResponseEntity.status(200).body(messages);
        
    }

}
