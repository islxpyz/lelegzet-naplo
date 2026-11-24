package hu.pte.prog2.adattar;

import hu.pte.prog2.domain.LegzesiMunkamenet;
import hu.pte.prog2.domain.NyugtatoStrategia;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FajlMunkamenetTaroloTest {

    @TempDir
    Path tempDir;

    @Test
    void mentesEsVisszatoltes() throws Exception {
        Path fajl = tempDir.resolve("naplo.tsv");
        FajlMunkamenetTarolo tarolo = new FajlMunkamenetTarolo(fajl);
        LegzesiMunkamenet eredeti = new LegzesiMunkamenet(
                7,
                LocalDateTime.of(2026, 11, 16, 18, 30),
                5,
                new NyugtatoStrategia(),
                "Tanulás után"
        );

        tarolo.mentes(List.of(eredeti));
        List<LegzesiMunkamenet> betoltott = tarolo.betolt();

        assertEquals(1, betoltott.size());
        assertEquals(7, betoltott.get(0).getAzonosito());
        assertEquals("Tanulás után", betoltott.get(0).getMegjegyzes());
        assertEquals("nyugtato", betoltott.get(0).getStrategia().getAzonosito());
    }

    @Test
    void hianyzoFajlEsetenUresLista() throws Exception {
        FajlMunkamenetTarolo tarolo = new FajlMunkamenetTarolo(tempDir.resolve("nincs.tsv"));
        assertTrue(tarolo.betolt().isEmpty());
    }
}