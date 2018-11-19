package ohtu.verkkokauppa;

import java.util.ArrayList;

public interface Logger {

    ArrayList<String> getTapahtumat();

    void lisaaTapahtuma(String tapahtuma);

}
