package laskin;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Erotus extends PerusKomento {

    public Erotus(TextField tuloskentta, TextField syotekentta, Button nollaa, Button undo, Sovelluslogiikka sovellus) {
        super(tuloskentta, syotekentta,  nollaa, undo, sovellus);
    }

    @Override
    protected void laske() {
        sovellus.miinus(syote);
    }
}
