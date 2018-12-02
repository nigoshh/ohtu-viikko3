package statistics.matcher;

public class Querybuilder {

    private Matcher matcher;

    public Querybuilder() {
        matcher = new All();
    }

    public Matcher build() {
        Matcher builtMatcher = matcher;
        matcher = new All();
        return builtMatcher;
    }

    public Querybuilder playsIn(String team) {
        matcher = new And(matcher, new PlaysIn(team));
        return this;
    }

    public Querybuilder hasAtLeast(int value, String category) {
        matcher = new And(matcher, new HasAtLeast(value, category));
        return this;
    }

    public Querybuilder hasFewerThan(int value, String category) {
        matcher = new And(matcher, new HasFewerThan(value, category));
        return this;
    }

    public Querybuilder oneOf(Matcher... matchers) {
        matcher = new Or(matchers);
        return this;
    }
}
