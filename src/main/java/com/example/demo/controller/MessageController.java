package com.example.demo.controller;

import com.example.demo.dto.Message;
import com.example.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {

    @Autowired
    private MessageService service;

    @GetMapping("/message")
    public Iterable<Message> getMessages() {
        return service.getAllMessages();
    }

    @GetMapping("/message/{id}")
    public Message findById(@PathVariable("id") int id) {
        return service.getMessageById(id);
    }

    @PostMapping("/message")
    public Message addMessage(@RequestBody Message message) {
        return service.addMessage(message);
    }

    @PutMapping("/message/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable("id") int id, @RequestBody Message message) {
        try {
            Message updated = service.updatePerson(id, message);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/message/{id}")
    public void deleteMessage(@PathVariable("id") int id) {
        service.deleteMessage(id);
    }
}
