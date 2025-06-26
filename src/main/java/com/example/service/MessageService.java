package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import java.util.*;

@Service
public class MessageService {
    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

     
    //Task 3: Create NEW message
    public Message createMessage(Message message){
        // Perform validation for messageText
        if((!message.getMessageText().isEmpty()) && (message.getMessageText().length() <= 255 )){
            return  messageRepository.save(message);
        }
        return null;
    }
    

    //Task 4: Get ALL messages
    public List<Message> retrieveAllMessage(){
        return messageRepository.findAll();
    }


    //Task 5: Get message by messageID
    public Message getMessageById(Integer messageId){
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if(messageOptional.isPresent()){
            return messageOptional.get();
        }
        return null;
    }


    //Task 6: Delete message with given messageId
    public Boolean deleteMessageById(Integer messageId){
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if(messageOptional.isPresent()){
            messageRepository.deleteById(messageId);
            return true;
        }
        return false;
    }

    //Task 7: Update message 
    public Boolean updateMessage(String messageText, Integer messageId){
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if((messageOptional.isPresent()) && (!messageText.isEmpty()) && (messageText.length() <= 255)){
            Message message = messageOptional.get();
            message.setMessageText(messageText);
            messageRepository.save(message);    // Update in DB
            return true;    // Sucessfull updated
        }
        return false;   //Failed
    }


    //Task 8: Retrieve ALL message for specific Account
    public List<Message> getAllMessagesFor(Integer accountId){
        return messageRepository.findAllByPostedBy(accountId);
    }
}
