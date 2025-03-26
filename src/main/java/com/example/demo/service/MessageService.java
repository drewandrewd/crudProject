package com.example.demo.service;

import com.example.demo.dto.Message;
import com.example.demo.dto.Person;
import com.example.demo.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository repository;

    @Transactional(readOnly = true)
    public Iterable<Message> getAllMessages() {
        return repository.findAll();
    }

    @Transactional
    public Message addMessage(Message message) {
        return repository.save(message);
    }

    public Message getMessageById(Integer id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Message not found");
        }
        return repository.findById(id).get();
    }

    @Transactional
    public Message updatePerson(int id, Message message) {
        Message old = getMessageById(id);
        message.setId(old.getId());
        return repository.save(message);
    }

    @Transactional
    public void deleteMessage(int id) {
        repository.deleteById(id);
    }
}
