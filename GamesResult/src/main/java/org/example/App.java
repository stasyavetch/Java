package org.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class App 
{
    public static void main( String[] args ) throws Exception {
        SpringApplication.run(App.class, args);

        String filePath = "src/main/java/resources/results.csv";

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(InMemoryResultsProcessor.class);
        InMemoryResultsProcessor resultsProcessor = applicationContext.getBean(InMemoryResultsProcessor.class);

        resultsProcessor.UploadFile(filePath);

        System.out.println(resultsProcessor.getFastestResultBySex(Sex.F, 4));
        System.out.println(resultsProcessor.getFastestResultByDistance(10, 5));
    }
}
