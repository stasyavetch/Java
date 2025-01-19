package org.example;

import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.TreeSet;

public interface ResultsProcessor {

    void UploadFile(String filePath) throws Exception;

    List<Athlete> getFastestResultBySex(Sex sex, int count);

    List<Athlete> getFastestResultByDistance(int dist, int count);
}
