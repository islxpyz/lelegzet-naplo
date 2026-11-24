package hu.pte.prog2.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LegzesiMunkamenetTest {

    @Test
    void nullaPercElutasitasa() {
        assertThrows(ErvenytelenMunkamenetException.class,
                () -> new LegzesiMunkamenet(1, LocalDateTime.now(), 0, new NyugtatoStrategia(), ""));
    }

    @Test
    void strategiaValtoztatjaACiklusokat() throws Exception {
        LegzesiMunkamenet nyugtato = new LegzesiMunkamenet(
                1, LocalDateTime.now(), 2, new NyugtatoStrategia(), "");
        LegzesiMunkamenet egyenletes = new LegzesiMunkamenet(
                2, LocalDateTime.now(), 2, new EgyenletesStrategia(), "");

        assertEquals(12, nyugtato.getCiklusokSzama());
        assertEquals(15, egyenletes.getCiklusokSzama());
    }
}