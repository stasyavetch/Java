package org.example;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class InMemoryResultsProcessor implements ResultsProcessor{

    private TreeSet<Athlete> allResults = new TreeSet<>(
            Comparator.comparing(Athlete::getTime)
                    .thenComparing(Athlete::getName)
    );


    @Override
    public void UploadFile(String filePath) throws Exception {
        CSVReader csvReader = new CSVReaderBuilder(new FileReader(filePath)).build();
        String[] nextLine;

        while ((nextLine = csvReader.readNext()) != null) {
            String name = nextLine[0];
            Sex sex = Sex.valueOf(nextLine[1]);
            int dis = Integer.parseInt(nextLine[2]);
            LocalTime time = LocalTime.parse(nextLine[3]);

            Athlete newAthlete = new Athlete(name, sex, dis, time);
//            System.out.println(newAthlete.toString());
            allResults.add(newAthlete);
        }

        csvReader.close();

    }

    @Override
    public List<Athlete> getFastestResultBySex(Sex sex, int count) {
        return allResults.stream().filter(athlete -> athlete.getSex().equals(sex)).limit(count).collect(Collectors.toList());
    }

    @Override
    public List<Athlete> getFastestResultByDistance(int dist, int count) {
        return allResults.stream().filter(athlete -> athlete.getDistance() == dist).limit(count).collect(Collectors.toList());
    }



    public boolean contains(Athlete athlete) {
        return allResults.contains(athlete);
    }
}
