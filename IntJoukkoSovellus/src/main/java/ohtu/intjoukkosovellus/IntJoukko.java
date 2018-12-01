
package ohtu.intjoukkosovellus;

import java.util.Arrays;
import java.util.stream.IntStream;

public class IntJoukko {

    public final static int OLETUS_KAPASITEETTI = 5, // aloitustalukon koko
                            OLETUS_KASVATUS = 5;     // luotava uusi taulukko on
                                                     // näin paljon isompi kuin vanha
    private int kasvatuskoko;  // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] alkiot;      // Joukon luvut säilytetään taulukon alkupäässä.
    private int alkioidenLkm;  // Tyhjässä joukossa alkioiden_määrä on nolla.

    public IntJoukko() {
        this(OLETUS_KAPASITEETTI, OLETUS_KASVATUS);
    }

    public IntJoukko(int kapasiteetti) {
        this(kapasiteetti, OLETUS_KASVATUS);
    }

    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        if (kapasiteetti < 0) {
            throw new IndexOutOfBoundsException("Kapasitteetti ei voi olla negatiivinen");
        }
        if (kasvatuskoko < 0) {
            throw new IndexOutOfBoundsException("Kasvatuskoko ei voi olla negatiivinen");
        }
        alkiot = new int[kapasiteetti];
        alkioidenLkm = 0;
        this.kasvatuskoko = kasvatuskoko;
    }

    public boolean lisaa(int luku) {
        if (!kuuluu(luku)) {
            alkiot[alkioidenLkm++] = luku;
            if (alkioidenLkm == alkiot.length) {
                alkiot = Arrays.copyOf(alkiot, alkiot.length + kasvatuskoko);
            }
            return true;
        }
        return false;
    }

    public boolean kuuluu(int luku) {
        return IntStream.of(alkiot).anyMatch(x -> x == luku);
    }

    public boolean poista(int luku) {
        int luvunIndeksi = getLuvunIndeksi(luku);
        if (luvunIndeksi == -1) {
            return false;
        }
        alkiot[luvunIndeksi] = 0;
        swapAlkiot(luvunIndeksi, --alkioidenLkm);
        return true;
    }

    // returns -1 if the number luku isn't found in array alkiot
    private int getLuvunIndeksi(int luku) {
        int luvunIndeksi = -1;
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == alkiot[i]) {
                luvunIndeksi = i;
                break;
            }
        }
        return luvunIndeksi;
    }

    private void swapAlkiot(int i, int j) {
        int apu = alkiot[i];
        alkiot[i] = alkiot[j];
        alkiot[j] = apu;
    }

    public int mahtavuus() {
        return alkioidenLkm;
    }

    @Override
    public String toString() {
        String s = "{";
        if (alkioidenLkm > 0) {
            for (int i = 0; i < alkioidenLkm - 1; i++) {
                s += alkiot[i] + ", ";
            }
            s += alkiot[alkioidenLkm - 1];
        }
        return s + "}";
    }

    public int[] toIntArray() {
        return Arrays.copyOf(alkiot, alkioidenLkm);
    }

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        IntJoukko joukko = new IntJoukko();
        IntStream.of(a.toIntArray()).forEach(x -> joukko.lisaa(x));
        IntStream.of(b.toIntArray()).forEach(x -> joukko.lisaa(x));
        return joukko;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        IntJoukko joukko = new IntJoukko();
        IntStream.of(a.toIntArray())
                .filter(x -> b.kuuluu(x))
                .forEach(x -> joukko.lisaa(x));
        return joukko;
    }

    public static IntJoukko erotus (IntJoukko a, IntJoukko b) {
        IntJoukko joukko = new IntJoukko();
        IntStream.of(a.toIntArray())
                .filter(x -> !b.kuuluu(x))
                .forEach(x -> joukko.lisaa(x));
        return joukko;
    }
}
