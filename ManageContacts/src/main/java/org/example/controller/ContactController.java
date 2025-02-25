package org.example.controller;

import org.example.entity.ChangeContactDto;
import org.example.entity.ContactDto;
import org.example.facade.ContactFacade;
import org.example.facade.RequestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getContactById(@PathVariable("id") Long id) {
        ContactDto contactDto = contactFacade.getContactById(id);
        if (contactDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(contactDto, HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createContact(@RequestBody ContactDto contactDto) {

        RequestResult result = contactFacade.createContact(contactDto.getName(), contactDto.getSurname(),
                contactDto.getPhoneNumber(), contactDto.getEmail());
        if (result == RequestResult.Success) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/{id}")
    public RequestResult changeContact(
            @PathVariable("id") Long id,
            @RequestBody ChangeContactDto changeContactDto) throws Exception {
        return contactFacade.changeContact(id, changeContactDto.getKey(), changeContactDto.getNewValue());
    }
}
