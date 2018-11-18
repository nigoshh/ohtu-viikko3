package ohtu;

import java.util.List;

public class Submission implements Comparable<Submission> {
    private int week;
    private double hours;
    private List<Integer> exercises;
    private String course;
    private Course courseObj;

    public double getHours() {
        return hours;
    }

    public List<Integer> getExercises() {
        return exercises;
    }

    public String getCourse() {
        return course;
    }

    public void setCourseObj(Course courseObj) {
        this.courseObj = courseObj;
    }

    @Override
    public String toString() {
        return "viikko " + week + ":\n tehtyjä tehtäviä " + exercises.size()
                + "/" + courseObj.getExercises().get(week) + ", aikaa kului "
                + hours + " tuntia, tehdyt tehtävät: "
                + exercises.toString().replace("[", "").replace("]", "");
    }

    @Override
    public int compareTo(Submission o) {
        return course.compareTo(o.course);
    }
}
