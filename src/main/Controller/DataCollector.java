package main.Controller;

import java.time.Duration;
import java.time.LocalTime;

public class DataCollector {
    private static final Duration LECTURE_DURATION = Duration.ofHours(2);
    LocalTime start;
    LocalTime end;
    int monstersShoot = 0;

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
        lectureScore = Duration.between(start, end).toMinutes() /
                LECTURE_DURATION.toMinutes() * monstersShoot;
    }


}

