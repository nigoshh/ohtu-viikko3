package ohtu;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.client.fluent.Request;

public class Main {

    public static void main(String[] args) throws IOException {
        // ÄLÄ laita githubiin omaa opiskelijanumeroasi
        String studentNr = "012345678";
        if (args.length > 0) {
            studentNr = args[0];
        }

        String url = "https://studies.cs.helsinki.fi/courses/courseinfo";
        String bodyText = Request.Get(url).execute().returnContent().asString();
        Gson mapper = new Gson();
        Map<String, Course> courses = new HashMap<>();
        for (Course course : mapper.fromJson(bodyText, Course[].class)) {
            courses.put(course.getName(), course);
        }

        JsonParser parser = new JsonParser();
        Map<String, JsonObject> stats = new HashMap<>();

        url = "https://studies.cs.helsinki.fi/courses/ohtu2018/stats";
        String statsResponse = Request.Get(url).execute().returnContent().asString();
        stats.put("ohtu2018", parser.parse(statsResponse).getAsJsonObject());

        url = "https://studies.cs.helsinki.fi/courses/rails2018/stats";
        statsResponse = Request.Get(url).execute().returnContent().asString();
        stats.put("rails2018", parser.parse(statsResponse).getAsJsonObject());

        url = "https://studies.cs.helsinki.fi/courses/students/" + studentNr + "/submissions";
        bodyText = Request.Get(url).execute().returnContent().asString();
        Submission[] subs = mapper.fromJson(bodyText, Submission[].class);
        Arrays.sort(subs);

        int exerciseCount = 0;
        double hourCount = 0.0;
        int totalExerciseCount = 0;
        String courseStr = "";
        Course courseObj = null;

        System.out.println("opiskelijanumero " + studentNr + "\n");

        for (Submission submission : subs) {
            if (!submission.getCourse().equals(courseStr)) {
                if (!courseStr.equals("")) {
                    printStats(exerciseCount, totalExerciseCount, hourCount,
                            stats.get(courseStr));
                }
                courseStr = submission.getCourse();
                courseObj = courses.get(submission.getCourse());
                exerciseCount = 0;
                hourCount = 0.0;
                totalExerciseCount = courseObj.getExercises().stream()
                        .mapToInt(e -> e).sum();
                System.out.println(courseObj + "\n");
            }
            submission.setCourseObj(courseObj);
            System.out.println(submission);
            exerciseCount += submission.getExercises().size();
            hourCount += submission.getHours();
        }

        printStats(exerciseCount, totalExerciseCount, hourCount,
                            stats.get(courseStr));
    }

    private static void printStats(int exerciseCount, int totalExerciseCount,
            double hourCount, JsonObject courseStats) {
        int students = courseStats.entrySet().stream()
                .mapToInt(e -> e.getValue().getAsJsonObject().get("students").getAsInt())
                .sum();
        int exercise_total = courseStats.entrySet().stream()
                .mapToInt(e -> e.getValue().getAsJsonObject().get("exercise_total").getAsInt())
                .sum();
        double hour_total = courseStats.entrySet().stream()
                .mapToDouble(e -> e.getValue().getAsJsonObject().get("hour_total").getAsDouble())
                .sum();
        System.out.println("\nyhteensä: " + exerciseCount + "/"
                + totalExerciseCount + " tehtävää " + hourCount + " tuntia\n\n"
                + "kurssilla yhteensä " + students + " palautusta, palautettuja tehtäviä "
                + exercise_total + " kpl, aikaa käytetty yhteensä " + hour_total + " tuntia\n");
    }
}
