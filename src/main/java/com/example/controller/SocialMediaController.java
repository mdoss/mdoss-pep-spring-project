package com.example.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.entity.Message;
import com.example.service.MessageService;
import com.example.entity.Account;

import com.example.service.AccountService;

/**
 * A RESTController allows for the creation of endpoints that will, by default, allow the developer to easily follow
 * RESTful conventions, such as the descriptive use of HTTP verbs (get, post, put, patch, delete), and will also
 * automatically convert variables returned from the endpoint's handler to a JSON response body, which was previously
 * done by including the @ResponseBody annotation.
 */
@RestController
public class SocialMediaController {

    private MessageService messageService;
    private AccountService accountService;

    @Autowired
    public SocialMediaController(MessageService messageService, AccountService accountService) {
        this.messageService = messageService;
        this.accountService = accountService;
    }
    /**
     * POST localhost:8080/register
     * @param account
     * @return 409 if duplicate username, 200 if successful, 400 if other
     */
    @PostMapping(value = "/register")
    public ResponseEntity<Account> postRegister(@RequestBody Account account){
        if(accountService.usernameNotInUse(account))
        {
            Account acc = accountService.handleRegistration(account);
            if(acc != null)
                return ResponseEntity.ok(acc);
            return ResponseEntity.status(400).body(null);
        }
        else
        {
            return ResponseEntity.status(409).body(null);
        }
    }
    /**
     * POST localhost:8080/login
     * @param account
     * @return 200 if ok, 401 if login not successful
     */
    @PostMapping(value = "/login")
    public ResponseEntity<Account> postLogin(@RequestBody Account account){
        Account acc = accountService.handleLogin(account);
        if(acc != null)
            return ResponseEntity.ok(acc);
        return ResponseEntity.status(401).body(null);
    }
    /**
     * GET localhost:8080/accounts/{accountId}/messages
     * @param accountId Only finds messages posted by this account
     * @return always returns 200. Empty if no messages
     */
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesFromAccount(@PathVariable int accountId){
        return ResponseEntity.ok(messageService.getMessagesFromAccount(accountId));
    }

    /**
     * GET localhost:8080/messages
     * @return always returns 200. Empty if no messages
     */
    @GetMapping(value = "messages")
    public ResponseEntity<List<Message>> getMessages(){
        return ResponseEntity.ok(messageService.getAllMessages());
    } 
    /**
     * POST localhost:8080/messages
     * @param message message to submit
     * @return 200 if ok, 400 if didn't post
     */
    @PostMapping(value = "/messages")
    public ResponseEntity<Message> postMessage(@RequestBody Message message){
        Message msg = messageService.postMessage(message);
        if(msg != null)
            return ResponseEntity.ok(msg);
        return ResponseEntity.status(400).body(null);
    }
    /**
     * GET localhost:8080/messages/{messageId}
     * @param messageId message to get
     * @return always 200. Empty if message doesn't exist
     */
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessage(@PathVariable int messageId){
        return ResponseEntity.ok(messageService.getMessage(messageId));
    }
    /**
     * DELETE localhost:8080/messages/{messageId}
     * @param messageId message to delete
     * @return number of rows edited
     */
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int messageId){
        int res = messageService.deleteMessage(messageId);
        if(res == 1)
            return ResponseEntity.ok(res);
        return ResponseEntity.ok(null);
    }
    /**
     * PATCH localhost:8080/messages/{messageId}
     * @param messageId existing message id to change
     * @param message new text
     * @return number of rows edited
     */
    @PatchMapping(value = "/messages/{messageId}")
    public ResponseEntity<Integer> patchMessage(@PathVariable int messageId, @RequestBody Message message){
        int res = messageService.updateMessage(messageId, message.getMessageText());
        if(res == 1)
            return ResponseEntity.ok(res);
        return ResponseEntity.status(400).body(null);
    }
    
}