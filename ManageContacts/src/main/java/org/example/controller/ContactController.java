package org.example.controller;

import org.example.entity.ContactDto;
import org.example.facade.ContactFacade;
import org.example.facade.RequestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactFacade contactFacade;

    @Autowired
    public ContactController(ContactFacade contactFacade) {
        this.contactFacade = contactFacade;
    }

    @GetMapping
    public List<ContactDto> getContacts() {
        return contactFacade.getContacts();
    }

    @GetMapping("/{id}")
    public ContactDto getContactById(@PathVariable("id") Long id) {
        return contactFacade.getContactById(id);
    }

    @PostMapping
    public RequestResult createContact(
            @RequestParam("name") String name, @RequestParam("surname") String surname,
            @RequestParam("phoneNumber")  long phoneNumber, @RequestParam("email") String email) {
        return contactFacade.createContact(name, surname, phoneNumber, email);
    }

    @PostMapping("/{id}")
    public RequestResult changeContact(
            @RequestParam("id") Long id,
            @RequestParam("key") String key, @RequestParam("newValue") String newValue) throws Exception {
        return contactFacade.changeContact(id, key, newValue);
    }
}
