package statistics;

import statistics.matcher.*;

public class Main {

    public static void main(String[] args) {
        Statistics stats = new Statistics(new PlayerReaderImpl("http://nhlstats-2013-14.herokuapp.com/players.txt"));

        Querybuilder query = new Querybuilder();
        Matcher m = new And(new HasAtLeast(10, "goals"),
                new HasAtLeast(10, "assists"),
                new PlaysIn("PHI")
        );
        printQuery(stats, m);

        m = new Not(new HasAtLeast(1, "goals"));
        printQuery(stats, m);

        m = new Or(new HasAtLeast(40, "goals"),
                new HasAtLeast(60, "assists"),
                new HasAtLeast(85, "points")
        );
        printQuery(stats, m);

        m = new HasFewerThan(1, "goals");
        printQuery(stats, m);

        m = query.build();
        printQuery(stats, m);

        m = query.playsIn("NYR").build();
        printQuery(stats, m);

        m = query.playsIn("NYR")
                .hasAtLeast(10, "goals")
                .hasFewerThan(25, "goals").build();
        printQuery(stats, m);


        m = query.playsIn("EDM")
                .hasAtLeast(60, "points").build();
        printQuery(stats, m);

        Matcher m1 = query.playsIn("PHI")
                .hasAtLeast(10, "goals")
                .hasFewerThan(20, "assists").build();

        Matcher m2 = query.playsIn("EDM")
                .hasAtLeast(60, "points").build();

        m = query.oneOf(m1, m2).build();
        printQuery(stats, m);
    }

    private static void printQuery(Statistics stats, Matcher m) {
        stats.matches(m).forEach(System.out::println);
        System.out.println("---------------------------------");
    }
}
