package org.exam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.FileNotFoundException;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App 
{
    public static void main( String[] args ) throws FileNotFoundException {
        SpringApplication.run(App.class, args);

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        ResultsProcessor resultsProcessor = applicationContext.getBean(ResultsProcessor.class);

        int result = resultsProcessor.getResult();
        System.out.println(result);
    }
}
