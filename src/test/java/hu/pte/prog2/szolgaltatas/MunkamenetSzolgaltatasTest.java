package hu.pte.prog2.szolgaltatas;

import hu.pte.prog2.adattar.FajlMunkamenetTarolo;
import hu.pte.prog2.domain.EgyenletesStrategia;
import hu.pte.prog2.domain.LegzesiMunkamenet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MunkamenetSzolgaltatasTest {

    @TempDir
    Path tempDir;

    @Test
    void bejegyzesMegmaradUjrainditasUtan() throws Exception {
        Path fajl = tempDir.resolve("adatok/naplo.tsv");
        MunkamenetSzolgaltatas szolgaltatas1 = new MunkamenetSzolgaltatas(new FajlMunkamenetTarolo(fajl));
        szolgaltatas1.ujMunkamenet(3, new EgyenletesStrategia(), "Pihenő");

        MunkamenetSzolgaltatas szolgaltatas2 = new MunkamenetSzolgaltatas(new FajlMunkamenetTarolo(fajl));
        List<LegzesiMunkamenet> lista = szolgaltatas2.getMunkamenetekIdorendben();

        assertEquals(1, lista.size());
        assertEquals(1, lista.get(0).getAzonosito());
        assertEquals("Pihenő", lista.get(0).getMegjegyzes());
    }

    @Test
    void torlesUtanUresLesz() throws Exception {
        MunkamenetSzolgaltatas szolgaltatas = new MunkamenetSzolgaltatas(
                new FajlMunkamenetTarolo(tempDir.resolve("torles/naplo.tsv"))
        );
        szolgaltatas.ujMunkamenet(3, new EgyenletesStrategia(), "");

        assertTrue(szolgaltatas.torol(1));
        assertFalse(szolgaltatas.torol(1));
        assertEquals(0, szolgaltatas.getMunkamenetekSzama());
    }
}