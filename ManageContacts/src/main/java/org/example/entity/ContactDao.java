package org.example.entity;

import java.util.List;

public interface ContactDao {
    List<Contact> getContacts();

    Contact getContactById(Long idContact);

    void createContact(String name, String surname, long phoneNumber, String email);

    boolean changeContact(Long idContact, String key, String newValue) throws Exception;

    boolean deleteContactById(Long idContact);
}
