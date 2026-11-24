package hu.pte.prog2.domain;

public class LegzesiStrategiak {

    public static LegzesiStrategia azonositoAlapjan(String azonosito) {
        if ("nyugtato".equals(azonosito)) {
            return new NyugtatoStrategia();
        }
        if ("egyenletes".equals(azonosito)) {
            return new EgyenletesStrategia();
        }
        throw new IllegalArgumentException("Ismeretlen légzési minta: " + azonosito);
    }
}