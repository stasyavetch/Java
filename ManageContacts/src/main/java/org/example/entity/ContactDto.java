package org.example.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.entity.Contact;

public class ContactDto {
    @JsonProperty("id")
    final long id;

    @JsonProperty("name")
    String name;

    @JsonProperty("surname")
    String surname;

    @JsonProperty("phoneNumber")
    long phoneNumber;

    @JsonProperty("email")
    String email;

    public ContactDto(Contact contact) {
        this.id = contact.getId();
        this.name = contact.getName();
        this.surname = contact.getSurname();
        this.phoneNumber = contact.getPhoneNumber();
        this.email = contact.getEmail();
    }
}
