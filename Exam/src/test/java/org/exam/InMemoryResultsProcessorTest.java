package org.exam;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
class InMemoryResultsProcessorTest {

    @Autowired
    private InMemoryResultsProcessor resultsProcessor;

    @Autowired
    private Environment env;

//    @Before
//    public void init() throws FileNotFoundException {
//        resultsProcessor = new InMemoryResultsProcessor();
//    }

    @Test
    void getResult() throws FileNotFoundException {
        resultsProcessor = new InMemoryResultsProcessor(env);
        int factResult = resultsProcessor.getResult();
        Assertions.assertEquals(11, factResult);
    }
}