package org.example;

import org.example.config.ApplicationConfig;
import org.example.controller.ContactService;
import org.example.entity.Contact;
import org.example.entity.ContactDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class App 
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);

        ContactDao contactDao = applicationContext.getBean(ContactDao.class);

        ContactService contactService = applicationContext.getBean(ContactService.class);
        contactService.loadContactsFromCsv("C:\\Users\\Анастасия\\Desktop\\папка для всего\\java обучение\\homework\\Java\\ManageContacts\\src\\main\\resources\\contacts.csv");

        Contact contact = contactDao.getContactById(1l);

        System.out.println(contact);
    }
}
