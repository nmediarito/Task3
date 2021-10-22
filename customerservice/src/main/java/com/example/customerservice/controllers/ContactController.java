package com.example.customerservice.controllers;

import com.example.customerservice.entities.Contact;
import com.example.customerservice.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService){
        this.contactService = contactService;
    }

    @GetMapping("/contacts")
    public List<Contact> getContacts(){
        return contactService.getContacts();
    }

    @PostMapping("/contacts")
    public void registerNewContact(@RequestBody Contact contact){
        contactService.addContact(contact);
    }

    @PutMapping("/contacts/{id}")
    public Contact updateContact(@RequestBody Contact newContact, @PathVariable Long id){
        return contactService.changeContact(newContact, id);
    }

    @DeleteMapping("/contacts/{id}")
    public void deleteContact(@PathVariable("id") Long id){
        contactService.deleteContact(id);
    }


}
