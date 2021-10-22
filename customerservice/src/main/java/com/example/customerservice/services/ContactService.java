package com.example.customerservice.services;

import com.example.customerservice.entities.Contact;
import com.example.customerservice.repositories.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository){
        this.contactRepository = contactRepository;
    }

    public List<Contact> getContacts(){
        return contactRepository.findAll();
    }

    public void addContact(Contact contact){
        //print new contact
        System.out.println(contact);
        contactRepository.save(contact);
    }

    public Contact changeContact(Contact newContact, Long id){
        return contactRepository.findById(id)
                .map(contact -> {
                    contact.setName(newContact.getName());
                    contact.setPhone(newContact.getPhone());
                    contact.setEmail(newContact.getEmail());
                    contact.setPosition(newContact.getPosition());
                    return contactRepository.save(contact);
                }).orElseGet(() -> {
                    newContact.setId(id);
                    return contactRepository.save(newContact);
                });
    }

    public void deleteContact(Long contactId){
        boolean exists = contactRepository.existsById(contactId);
        if(!exists){
            throw new IllegalStateException(contactId + " does not exist");
        } else {
            contactRepository.deleteById(contactId);
            System.out.println(contactId + " has been deleted");
        }
    }
}
