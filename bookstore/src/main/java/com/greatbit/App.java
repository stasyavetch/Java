package com.greatbit;

import com.greatbit.models.Book;
import com.greatbit.models.BookStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class App 
{
    public static void main( String[] args )
    {
        BookStorage.getBooks().add(
                new Book("Учения Лона Хуана", "Карлос Костанеда", 400)
        );
        BookStorage.getBooks().add(
                new Book("Богатый папа, бедный папа", "Роберт Каосаки", 300)
        );

        SpringApplication.run(App.class, args);
    }
}
