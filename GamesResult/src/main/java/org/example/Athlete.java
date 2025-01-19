package org.example;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.Objects;


public class Athlete implements Comparable<Athlete> {

    private String name;

    private Sex sex;

    private int distance;

    private LocalTime time;

    public Athlete(String name, Sex sex, int distance, LocalTime time) throws Exception {
        this.name = name;
        this.sex = sex;
        if (distance == 5 || distance == 10) {
            this.distance = distance;
        } else {
            throw new Exception("Errror with distance");
        }
        this.time = time;
    }

    @Override
    public String toString() {
        return "Athlete{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", distance=" + distance +
                ", time=" + time.toString() +
                '}';
    }

    public String getName() {
        return name;
    }

    public Sex getSex() {
        return sex;
    }

    public int getDistance() {
        return distance;
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public int compareTo(Athlete o) {

        if (this.time != o.time) {
            return time.compareTo(o.time);
        } else {
            return name.compareTo(o.name);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Athlete)) return false;

        Athlete athlete = (Athlete) o;
        return sex == athlete.sex &&
                distance == athlete.distance &&
                Objects.equals(name, athlete.name) &&
                Objects.equals(time, athlete.time);
    }
}
