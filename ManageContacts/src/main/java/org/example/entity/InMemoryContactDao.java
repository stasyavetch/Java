package org.example.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.example.entity.Contact;
import org.example.entity.ContactDao;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Validated
@Repository
public class InMemoryContactDao implements ContactDao {
    long contactId = 1L;
    HashMap<Long, Contact> contactHashMap;

    public InMemoryContactDao() {
        this.contactHashMap = new HashMap<Long, Contact>();
    }

    public List<Contact> getContacts() {
        return new ArrayList<>(contactHashMap.values());
    }

    public Contact getContactById(Long idContact) {
        return contactHashMap.get(idContact);
    }

    public void createContact(@NotNull String name, @NotNull String surname,
                              @Min(89000000000L) @Max(89999999999L)long phoneNumber, @Email String email) {
        Contact newContact = new Contact(contactId, name, surname, phoneNumber, email);
        contactHashMap.put(contactId, newContact);
        ++contactId;
    }

    public boolean changeContact(Long idContact, String key, String newValue) throws Exception {
        Contact contact = contactHashMap.get(idContact);
        if (contact == null) {
            return false;
        }

        switch (key) {
            case "name":
                contact.setName(newValue);
                break;
            case "surname":
                contact.setSurname(newValue);
                break;
            case "phoneNumber":
                contact.setPhoneNumber(Long.parseLong(newValue));
                break;
            case "email":
                contact.setEmail(newValue);
            default:
                throw new Exception("Parameter not found");


        }

        return true;
    }

    @Override
    public boolean deleteContactById(Long idContact) {
        return false;
    }
}
