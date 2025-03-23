package com.example.demo.controller;

import com.example.demo.dto.Message;
import com.example.demo.dto.Person;
import com.example.demo.repository.PersonRepository;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class  PersonController {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private PersonService service;

    @GetMapping("/person")
    public Iterable<Person> getPerson() {
        return repository.findAll();
    }

    @PostMapping("/person")
    public Person addPerson(@RequestBody Person person) {
        return repository.save(person);
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<?> findPersonById(@PathVariable("id") int id) {
        try {
            Person person = service.getPersonById(id);
            return ResponseEntity.ok(person);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/person/{id}")
    public ResponseEntity<?> updatePerson(@PathVariable("id") int id, @RequestBody Person person) {
        try {
            Person updated = service.updatePerson(id, person);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/person/{id}")
    public void deletePerson(@PathVariable("id") int id) {
        repository.deleteById(id);
    }

    @PostMapping("person/{id}/message")
    public ResponseEntity<?> addMessage(@PathVariable("id") int id, @RequestBody Message message) {
        try {
            Person person = service.addMessageToPerson(id, message);
            return ResponseEntity.ok(person);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("person/{idP}/message/{idM}")
    public ResponseEntity<?> deleteMessage(@PathVariable("idP") int personId, @PathVariable("idM") int messageId) {
        try {
            service.deleteMessageFromPerson(personId, messageId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/person/{id}/messages/")
    public ResponseEntity<?> getPersonMessages(@PathVariable("id") int id) {
        try {
            List<Message> messages = service.getAllMessages(id);
            return ResponseEntity.ok(messages);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("person/{idP}/message/{idM}")
    public ResponseEntity<?> getMessage(@PathVariable("idP") int personId, @PathVariable("idM") int messageId) {
        try {
            Message message = service.getPersonMessage(personId, messageId);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
