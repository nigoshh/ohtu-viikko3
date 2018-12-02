package laskin;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public abstract class PerusKomento implements Komento {

    protected TextField tuloskentta, syotekentta;
    protected Button nollaa, undo;
    protected Sovelluslogiikka sovellus;
    protected int vanhaTulos, syote;

    public PerusKomento(TextField tuloskentta, TextField syotekentta,
            Button nollaa, Button undo, Sovelluslogiikka sovellus) {
        this.tuloskentta = tuloskentta;
        this.syotekentta = syotekentta;
        this.nollaa = nollaa;
        this.undo = undo;
        this.sovellus = sovellus;
        this.vanhaTulos = 0;
        this.syote = 0;
    }

    @Override
    public void suorita() {
        vanhaTulos = sovellus.tulos();
        syote = 0;
        try {
            syote = Integer.parseInt(syotekentta.getText());
        } catch (NumberFormatException e) {
        }

        laske();

        int laskunTulos = sovellus.tulos();

        syotekentta.setText("");
        tuloskentta.setText("" + laskunTulos);

        if (laskunTulos == 0) {
            nollaa.disableProperty().set(true);
        } else {
            nollaa.disableProperty().set(false);
        }
        undo.disableProperty().set(false);
    }

    @Override
    public void peru() {
        sovellus.nollaa();
        sovellus.plus(vanhaTulos);

        syotekentta.setText("" + syote);
        tuloskentta.setText("" + vanhaTulos);

        if (vanhaTulos == 0) {
            nollaa.disableProperty().set(true);
        } else {
            nollaa.disableProperty().set(false);
        }
        undo.disableProperty().set(true);
    }

    protected abstract void laske();
}
