package org.example.controller;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.example.entity.Contact;
import org.example.entity.ContactDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class JdbcContactService implements ContactService{

    private final ContactDao contactDao;

    public JdbcContactService(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    @Override
    public void loadContactsFromCsv(String filePath) {
        List<Contact> dataList = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {

            String[] line;
            while ((line = reader.readNext()) != null) {

                Contact newContact = new Contact(Integer.parseInt(line[0]), line[1], line[2], Long.parseLong(line[3]), line[4]);
                dataList.add(newContact);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        contactDao.createContactsBatch(dataList);
    }
}
