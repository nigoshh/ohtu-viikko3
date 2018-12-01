package ohtu;

public class TennisGame {

    private int m_score1 = 0;
    private int m_score2 = 0;
    private final String player1Name;
    private final String player2Name;

    private final String LOVE = "Love";
    private final String FIFTEEN = "Fifteen";
    private final String THIRTY = "Thirty";
    private final String FORTY = "Forty";
    private final String ALL = "All";
    private final String DEUCE = "Deuce";
    private final String ADVANTAGE = "Advantage ";
    private final String WIN = "Win for ";
    private final String DIVIDER = "-";

    private final int LOVE_SCORE = 0;
    private final int FIFTEEN_SCORE = 1;
    private final int THIRTY_SCORE = 2;
    private final int FORTY_SCORE = 3;
    private final int ADVANTAGE_MARGIN = 1;
    private final int WIN_MARGIN = 2;

    public TennisGame(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
    }

    public void wonPoint(String playerName) {
        if (playerName.equals(player1Name))
            m_score1++;
        else if  (playerName.equals(player2Name))
            m_score2++;
    }

    public String getScore() {
        if (m_score1 == m_score2)
            return getEvenScore();
        else if (m_score1 > FORTY_SCORE || m_score2 > FORTY_SCORE)
            return getUnevenScoreAfterForty();
        return getUnevenScoreUntilForty();
    }

    private String getEvenScore() {
        if (m_score1 > FORTY_SCORE)
            return DEUCE;
        return getSinglePlayerScoreUntilForty(m_score1) + DIVIDER + ALL;
    }

    private String getUnevenScoreAfterForty() {
        int scoreDiff = m_score1 - m_score2;
        if (scoreDiff == ADVANTAGE_MARGIN)
            return ADVANTAGE + player1Name;
        else if (scoreDiff == -ADVANTAGE_MARGIN)
            return ADVANTAGE + player2Name;
        else if (scoreDiff >= WIN_MARGIN)
            return WIN + player1Name;
        return WIN + player2Name;
    }

    private String getUnevenScoreUntilForty() {
        return getSinglePlayerScoreUntilForty(m_score1) + DIVIDER
                + getSinglePlayerScoreUntilForty(m_score2);
    }

    private String getSinglePlayerScoreUntilForty(int score) {
        switch (score) {
            case LOVE_SCORE:
                return LOVE;
            case FIFTEEN_SCORE:
                return FIFTEEN;
            case THIRTY_SCORE:
                return THIRTY;
            case FORTY_SCORE:
                return FORTY;
            default:
                return "method argument \"score\" must be between "
                        + LOVE_SCORE + " and " + FORTY_SCORE;
        }
    }
}
