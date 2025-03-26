package com.example.demo.service;

import com.example.demo.dto.Message;
import com.example.demo.dto.Person;
import com.example.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    public Person getPersonById(int id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("User with id " + id + "  not found");
        }
        return repository.findById(id).get();
    }

    @Transactional
    public Person addMessageToPerson(int id, Message message) {
        Person person = getPersonById(id);
        message.setPerson(person);
        message.setTime(LocalDateTime.now());
        person.getMessageList().add(message);
        return repository.save(person);
    }

    @Transactional(readOnly = true)
    public List<Message> getAllMessages(int personId) {
        Person person = getPersonById(personId);
        return person.getMessageList();
    }

    public Message getPersonMessage(int personId, int messageId) {
        Message message = getAllMessages(personId).stream()
                .filter(m -> m.getId() == messageId).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Message with id " + messageId + " not found"));
        return message;
    }

    @Transactional
    public void deleteMessageFromPerson(int personId, int messageId) {
        Person person = getPersonById(personId);
        Message message = getPersonMessage(personId, messageId);
        person.getMessageList().remove(message);
        repository.save(person);
    }

    @Transactional
    public Person updatePerson(int id, Person person) {
        Person old = getPersonById(id);
        person.setId(old.getId());
        person.setMessageList(old.getMessageList());
        return repository.save(person);
    }

    @Transactional
    public void deletePersonById(int id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Iterable<Person> getAllPersons() {
        return repository.findAll();
    }

    @Transactional
    public Person addPerson(Person person) {
        return repository.save(person);
    }
}
