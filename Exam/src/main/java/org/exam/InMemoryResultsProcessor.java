package org.exam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class InMemoryResultsProcessor implements ResultsProcessor{
    private int result;

    private HashMap<Integer, PossibleKeys> idealKeys;

    private HashMap<Integer, PossibleKeys> factAnswer;

    @Autowired
    private Environment env;

    @Autowired
    InMemoryResultsProcessor(Environment env) throws FileNotFoundException {
        this.env = env;
        idealKeys = new HashMap<>();
        factAnswer = new HashMap<>();

        readFile("src/main/resources/Keys.txt", idealKeys);
        readFile("src/main/resources/Answers.txt", factAnswer);
        result = 0;
    }

    @Override
    public int getResult() {

        int oneGoupFirst = Integer.parseInt(env.getProperty("1group.first"));
        int oneGoupLast = Integer.parseInt(env.getProperty("1group.last"));
        int oneScore = Integer.parseInt(env.getProperty("1group"));

        int twoGoupFirst = Integer.parseInt(env.getProperty("2group.first"));
        int twoGoupLast = Integer.parseInt(env.getProperty("2group.last"));
        int twoScore = Integer.parseInt(env.getProperty("2group"));

        int threeGoupFirst = Integer.parseInt(env.getProperty("3group.first"));
        int threeGoupLast = Integer.parseInt(env.getProperty("3group.last"));
        int threeScore = Integer.parseInt(env.getProperty("3group"));

        for (Map.Entry<Integer, PossibleKeys> idealEntry: idealKeys.entrySet()) {
            for (Map.Entry<Integer, PossibleKeys> factEntry: factAnswer.entrySet()) {
                int num = idealEntry.getKey();
                if (Objects.equals(num, factEntry.getKey())) {
                    if (Objects.equals(idealEntry.getValue(), factEntry.getValue())) {
                        if (num >= oneGoupFirst && num <= oneGoupLast) {
                            result += oneScore;
                        } else if (num >= twoGoupFirst && num <= twoGoupLast) {
                            result += twoScore;
                        } else if (num >= threeGoupFirst && num <= threeGoupLast) {
                            result += threeScore;
                        } else {
                            System.out.println("Номера вопроса нет в списке ключей");
                        }
                    }
                }
            }
        }
        return result;
    }

    private void readFile(String filePath, HashMap<Integer, PossibleKeys> hashMap) throws FileNotFoundException {

//        hashMap = new HashMap<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
//                System.out.println(line.split(" - ")[0]);
                int number = Integer.parseInt(line.split(" - ")[0]);
                PossibleKeys key = PossibleKeys.valueOf(line.split(" - ")[1]);

                hashMap.put(number, key);
            }

        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
