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
public class MessageService {
    MessageRepository messageRepository;
    AccountService accountService;
    /**
     * The MessageRepository has been autowired into this class via constructor injection. 
     * @param messageRepository
     * @param accountService
     */
    @Autowired
    public MessageService(MessageRepository messageRepository, AccountService accountService){
        this.messageRepository = messageRepository;
        this.accountService = accountService;
    }

    /**
     * gets all messages 
     * @return List of every message on the app
     */
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    /**
     * Gets all messages made by an account
     * @param accountId ID of the account that made the messages
     * @return List of messages posted by the account
     */
    public List<Message> getMessagesFromAccount(int accountId){
        return messageRepository.findAllByPostedBy(accountId);
    }

    /**
     * Gets a message by id
     * @param messageId Id of message
     * @return Message with matching id
     */
    public Message getMessage(int messageId){
        return messageRepository.findByMessageId(messageId);
    }
    /**
     * Deletes a message by its ID
     * @param messageId Id of message to delete
     * @return number of changed rows
     */
    public int deleteMessage(int messageId){
        Message message = getMessage(messageId);
        if(message != null)
        {
            messageRepository.delete(message);
            return 1;
        }
        return 0;
    }
    /**
     * Updates message with id
     * @param messageId Message to change
     * @param messageText New message text
     * @return Number of changed rows
     * 
     */
    public int updateMessage(int messageId, String messageText){
        Message message = getMessage(messageId);
        
        if(message != null && messageText.length() < 256 && messageText.length() > 0)
        {
            message.setMessageText(messageText);
            messageRepository.save(message);
            return 1;
        }
        return 0;
    }
    /**
     * Posts a new message
     * @param message Message to post
     * @return null if not successful
     */
    public Message postMessage(Message message){        
        if(message.getMessageText().length() < 256 && message.getMessageText().length() > 0 && accountService.getAccount(message.getPostedBy()) != null)
        {
            messageRepository.save(message);
            return message;
        }
        return null;
    }
}