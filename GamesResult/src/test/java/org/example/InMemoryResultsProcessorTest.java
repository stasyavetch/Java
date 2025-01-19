package org.example;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import static org.junit.Assert.*;

public class InMemoryResultsProcessorTest {

    private InMemoryResultsProcessor allResults;

    @Before
    public void init() {
        allResults = new InMemoryResultsProcessor();
    }

    @Test
    public void uploadFile() throws Exception {

        Assertions.assertThrows(FileNotFoundException.class, () -> allResults.UploadFile("../../resources/notFile.csv"));
        allResults.UploadFile("src/test/java/resources/resultsForTest.csv");

        Athlete athlete1 = new Athlete("Ivan Ivanov", Sex.M,10, LocalTime.of(0, 55, 20));
        Athlete athlete2 = new Athlete("Maria Sidorova", Sex.F, 5, LocalTime.of(0, 35, 45));
        Athlete athlete3 = new Athlete("Valeria Petrova", Sex.F, 10, LocalTime.of(1, 25, 30));
        Athlete athlete4 = new Athlete("Petr Petov", Sex.M, 5, LocalTime.of(0, 25, 15));

        Assertions.assertTrue(allResults.contains(athlete1));
        Assertions.assertTrue(allResults.contains(athlete2));
        Assertions.assertTrue(allResults.contains(athlete3));
        Assertions.assertTrue(allResults.contains(athlete4));
    }

    @Test
    public void getFastestResultBySex() throws Exception {
        uploadFile();

        Athlete athlete1 = new Athlete("Ivan Ivanov", Sex.M, 10, LocalTime.of(0, 55, 20));
        Athlete athlete4 = new Athlete("Petr Petov", Sex.M, 5, LocalTime.of(0, 25, 15));

        List<Athlete> expectedItem = new ArrayList<>();
        expectedItem.add(athlete4);

        List<Athlete> factResult = allResults.getFastestResultBySex(Sex.M, 1);

        Assertions.assertEquals(expectedItem, factResult);

        expectedItem.add(athlete1);
        List<Athlete> factResult2 = allResults.getFastestResultBySex(Sex.M, 2);
        Assertions.assertEquals(expectedItem, factResult2);


    }

    @Test
    public void getFastestResultByDistance() throws Exception {
        uploadFile();

        Athlete athlete2 = new Athlete("Maria Sidorova", Sex.F, 5, LocalTime.of(0, 35, 45));
        Athlete athlete4 = new Athlete("Petr Petov", Sex.M, 5, LocalTime.of(0, 25, 15));

        List<Athlete> expectedItem = new ArrayList<>();
//        TreeSet<Athlete> expectedItem = new TreeSet<>();
        expectedItem.add(athlete4);


        List<Athlete> factResult = allResults.getFastestResultByDistance(5, 1);

        Assertions.assertEquals(expectedItem, factResult);

        expectedItem.add(athlete2);
        List<Athlete> factResult2 = allResults.getFastestResultByDistance(5, 2);
        Assertions.assertEquals(expectedItem, factResult2);
    }
}