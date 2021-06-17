package main.Controller;

import javax.xml.crypto.Data;
import java.io.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class DataCollector {
    private static final String FILE_FOR_DATA = "resources//gameData.csv";
    private static final Duration LECTURE_DURATION = Duration.ofHours(2);
    private LocalTime start;
    private LocalTime end;
    private int monstersShoot = 0;

    // how boring the lecture was
    // more lectureScore -> more boring
    long lectureScore;


    void incrementMonstersShoot() {
        monstersShoot++;
    }


    /**
     * Evaluates data at the end and stores it if necessary
     * Here : Very Naive score evaluation
     */
    void evaluate() {
        lectureScore = (Duration.between(start, end).toMinutes() /
                LECTURE_DURATION.toMinutes()) * monstersShoot;
    }

    String calculateFinalLectureScore() {

//        String row = "";
//        long finalScore = 0;
//        BufferedReader csvReader = new BufferedReader(new FileReader(FILE_FOR_DATA));
//        int cnt = 0;
//        while ((row = csvReader.readLine()) != null) {
//            String[] data = row.split(",");
//            finalScore += Long.parseLong(data[4]);
//            cnt++;
//        }
//        finalScore += lectureScore;
//        finalScore /= cnt;
//        csvReader.close();
        return Long.toString(0);
    }

    /**
     *
     */
    void writeData() throws IOException {

        evaluate();

        List<String> rows = Arrays.asList(start.toString(), end.toString(), Integer.toString(monstersShoot)
                , Long.toString(lectureScore), calculateFinalLectureScore());

        FileWriter csvWriter = new FileWriter(FILE_FOR_DATA, true);
        csvWriter.append(String.join(",", rows));
        csvWriter.append("\n");
        csvWriter.flush();
        csvWriter.close();
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }


}

