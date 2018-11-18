package ohtu;

import java.util.List;

public class Course {
    private String name;
    private String term;
    private int year;
    private String fullName;
    private List<Integer> exercises;

    public String getName() {
        return name;
    }

    public List<Integer> getExercises() {
        return exercises;
    }

    @Override
    public String toString() {
        return fullName + " " + term + " " + year;
    }
}
