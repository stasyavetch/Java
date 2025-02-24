package org.example.facade;

import org.example.entity.Contact;
import org.example.entity.ContactDao;
import org.example.entity.ContactDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactFacade {
    private ContactDao contactDao;

    @Autowired
    public ContactFacade(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    public List<ContactDto> getContacts() {
        return contactDao.getContacts().stream().map(ContactDto::new).toList();
    }

    public ContactDto getContactById(Long idContact) {
        Contact contact = contactDao.getContactById(idContact);
        return new ContactDto(contact);
    }

    public RequestResult createContact(String name, String surname, long phoneNumber, String email) {
        contactDao.createContact(name, surname, phoneNumber, email);
        return RequestResult.Success;
    }

    public RequestResult changeContact(Long idContact, String key, String newValue) throws Exception {
        contactDao.changeContact(idContact, key, newValue);
        return RequestResult.Success;
    }
}
