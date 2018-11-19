package ohtu.verkkokauppa;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class KauppaTest {

    Kauppa k;
    Pankki pankki;
    Viitegeneraattori viitegen;
    Varasto varasto;

    @Before
    public void setUp() {
        pankki = mock(Pankki.class);
        viitegen = mock(Viitegeneraattori.class);
        varasto = mock(Varasto.class);
        k = new Kauppa(varasto, pankki, viitegen);
    }

    @Test
    public void yhdenTuotteenOstoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaanOikeillaParametreilla() {
        when(viitegen.uusi()).thenReturn(42);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.tilimaksu("pekka", "12345");

        verify(pankki).tilisiirto(eq("pekka"), eq(42), eq("12345"), anyString(), eq(5));
    }

    @Test
    public void kahdenEriTuotteenOstoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaanOikeillaParametreilla() {
        when(viitegen.uusi()).thenReturn(24);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.saldo(3)).thenReturn(23);
        when(varasto.haeTuote(3)).thenReturn(new Tuote(3, "kaurajuoma", 3));

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(3);
        k.tilimaksu("mikko", "23456");

        verify(pankki).tilisiirto(eq("mikko"), eq(24), eq("23456"), anyString(), eq(8));
    }

    @Test
    public void kahdenSamanTuotteenOstoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaanOikeillaParametreilla() {
        when(viitegen.uusi()).thenReturn(29);
        when(varasto.saldo(3)).thenReturn(19);
        when(varasto.haeTuote(3)).thenReturn(new Tuote(3, "kaurajuoma", 2));

        k.aloitaAsiointi();
        k.lisaaKoriin(3);
        k.lisaaKoriin(3);
        k.tilimaksu("pekka", "12345");

        verify(pankki).tilisiirto(eq("pekka"), eq(29), eq("12345"), anyString(), eq(4));
    }

    @Test
    public void loppuneenTuotteenOstoksenPaaytyttyaPankinMetodiaTilisiirtoKutsutaanOikeillaParametreilla() {
        when(viitegen.uusi()).thenReturn(35);
        when(varasto.saldo(1)).thenReturn(0);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.saldo(3)).thenReturn(27);
        when(varasto.haeTuote(3)).thenReturn(new Tuote(3, "kaurajuoma", 2));

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(3);
        k.tilimaksu("pekka", "12345");

        verify(pankki).tilisiirto(eq("pekka"), eq(35), eq("12345"), anyString(), eq(2));
    }

    @Test
    public void metodiAloitaAsiointiNollaaEdellistenOstoksenTiedot() {
        when(varasto.saldo(1)).thenReturn(3);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.saldo(3)).thenReturn(15);
        when(varasto.haeTuote(3)).thenReturn(new Tuote(3, "kaurajuoma", 2));

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(3);
        k.tilimaksu("pekka", "12345");

        verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(), eq(7));

        k.aloitaAsiointi();
        k.lisaaKoriin(3);
        k.lisaaKoriin(3);
        k.lisaaKoriin(3);
        k.tilimaksu("mikko", "23456");

        verify(pankki).tilisiirto(eq("mikko"), anyInt(), eq("23456"), anyString(), eq(6));
    }

    @Test
    public void pyydetaanUusiViiteJokaiseenMaksuun() {
        when(varasto.saldo(1)).thenReturn(3);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.saldo(3)).thenReturn(15);
        when(varasto.haeTuote(3)).thenReturn(new Tuote(3, "kaurajuoma", 2));

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(3);
        k.tilimaksu("pekka", "12345");

        verify(viitegen, times(1)).uusi();

        k.aloitaAsiointi();
        k.lisaaKoriin(3);
        k.lisaaKoriin(3);
        k.lisaaKoriin(3);
        k.tilimaksu("mikko", "23456");

        verify(viitegen, times(2)).uusi();

        k.aloitaAsiointi();
        k.lisaaKoriin(3);
        k.tilimaksu("pekka", "12345");

        verify(viitegen, times(3)).uusi();
    }

    @Test
    public void metodiPoistaKoristaPalauttaaTuoteenVarastoon() {
        when(varasto.saldo(1)).thenReturn(3);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));
        when(varasto.saldo(3)).thenReturn(15);
        when(varasto.haeTuote(3)).thenReturn(new Tuote(3, "kaurajuoma", 2));

        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(3);
        k.poistaKorista(1);

        verify(varasto).palautaVarastoon(anyObject());
    }
}
