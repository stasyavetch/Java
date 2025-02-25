package org.example.controller;

import org.example.entity.ChangeContactDto;
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
    public RequestResult createContact(@RequestBody ContactDto contactDto) {
        return contactFacade.createContact(contactDto.getName(), contactDto.getSurname(),
                contactDto.getPhoneNumber(), contactDto.getEmail());
    }

    @PostMapping("/{id}")
    public RequestResult changeContact(
            @PathVariable("id") Long id,
            @RequestBody ChangeContactDto changeContactDto) throws Exception {
        return contactFacade.changeContact(id, changeContactDto.getKey(), changeContactDto.getNewValue());
    }
}
