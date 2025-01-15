package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    @Autowired
    AccountService accountService;

    @Autowired
    MessageService messageService;

    @PostMapping("register")
    public ResponseEntity<Account> registerUser(@RequestBody Account account) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(accountService.registerAccount(account));
        } catch (JpaSystemException e) {
            e.getMessage();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (RuntimeException e) {
            e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); 
        }

    }

    @PostMapping("login")
    public ResponseEntity<Account> loginUser(@RequestBody Account account) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(accountService.loginAccount(account));
        } catch (RuntimeException e) {
            e.getMessage();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(messageService.createMessage(message));
        } catch (RuntimeException e) {
            e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("messages")
    public List<Message> displayAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("messages/{messageId}")
    public Message displayMesssageByID(@PathVariable String messageId) {
        return messageService.getMessageByID(Integer.parseInt(messageId));
    }

    @DeleteMapping("messages/{messageId}")
    public Integer deleteMessageByID(@PathVariable int messageId) {
        try {
            return messageService.deleteMessageByID(messageId);
        } catch (RuntimeException e) {
            e.getMessage();
            return null;
        }
    }

    @PatchMapping("messages/{messageId}")
    public ResponseEntity<Integer> updateMessage (@PathVariable int messageId, @RequestBody Message message) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(messageService.updateMessageByID(messageId, message.getMessageText()));
        } catch (RuntimeException e) {
            e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("accounts/{accountId}/messages")
    public List<Message> displayAllMessagesByUser(@PathVariable int accountId) {
        return messageService.getAllMessagesByAccountID(accountId);
    }

}
