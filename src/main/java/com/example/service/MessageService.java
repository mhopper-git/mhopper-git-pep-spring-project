package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Message createMessage(Message message) throws RuntimeException{
        if (message.getMessageText() != "" &&
            message.getMessageText().length() <= 255 &&
            !accountRepository.findById(message.getPostedBy()).isEmpty()) {
                return messageRepository.save(message);
            }
        
        throw new RuntimeException("Message was invalid and could not be created.");
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageByID(int messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    public Integer deleteMessageByID(int messageId) throws RuntimeException{
        if(getMessageByID(messageId) != null) {
            messageRepository.deleteById(messageId);
            return 1;
        }

        throw new RuntimeException("No such message exists to be deleted.");
    }

    public Integer updateMessageByID(int messageId, String newMessageText) throws RuntimeException {
        Message possibleMessageToUpdate = getMessageByID(messageId);

        if (possibleMessageToUpdate != null &&
            newMessageText != null &&
            newMessageText != "" &&
            newMessageText.length() <= 255) {
            Message updatedMessage = possibleMessageToUpdate;
            updatedMessage.setMessageText(newMessageText);
            messageRepository.save(updatedMessage);
            return 1;
        }

        throw new RuntimeException("Message could not be updated, recheck message ID or new message.");
    }

    public List<Message> getAllMessagesByAccountID(int accountId) {
        return messageRepository.findByPostedBy(accountId);
    }
}
