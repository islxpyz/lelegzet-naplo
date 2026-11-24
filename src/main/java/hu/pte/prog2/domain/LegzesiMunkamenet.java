package hu.pte.prog2.domain;

import java.time.LocalDateTime;

public class LegzesiMunkamenet {
    private static final int MIN_PERC = 1;
    private static final int MAX_PERC = 120;
    private static final int MAX_MEGJEGYZES = 100;

    private final int azonosito;
    private final LocalDateTime idopont;
    private final int percek;
    private final LegzesiStrategia strategia;
    private final String megjegyzes;

    public LegzesiMunkamenet(int azonosito, LocalDateTime idopont, int percek,
                             LegzesiStrategia strategia, String megjegyzes) throws ErvenytelenMunkamenetException {
        if (azonosito < 1) {
            throw new ErvenytelenMunkamenetException("Az azonosítónak pozitív számnak kell lennie.");
        }
        if (idopont == null) {
            throw new ErvenytelenMunkamenetException("Az időpont nem lehet üres.");
        }
        if (percek < MIN_PERC || percek > MAX_PERC) {
            throw new ErvenytelenMunkamenetException("Az időtartam 1 és 120 perc közé essen.");
        }
        if (strategia == null) {
            throw new ErvenytelenMunkamenetException("Válassz egy légzési mintát.");
        }

        // Tabulatorok es sortoresek eltavolitasa a TSV mentes miatt
        String tisztaMegjegyzes = megjegyzes == null ? "" : megjegyzes.trim();
        tisztaMegjegyzes = tisztaMegjegyzes.replace('\t', ' ').replace('\n', ' ').replace('\r', ' ');
        if (tisztaMegjegyzes.length() > MAX_MEGJEGYZES) {
            throw new ErvenytelenMunkamenetException("A megjegyzés legfeljebb 100 karakter lehet.");
        }

        this.azonosito = azonosito;
        this.idopont = idopont;
        this.percek = percek;
        this.strategia = strategia;
        this.megjegyzes = tisztaMegjegyzes;
    }

    public int getAzonosito() {
        return azonosito;
    }

    public LocalDateTime getIdopont() {
        return idopont;
    }

    public int getPercek() {
        return percek;
    }

    public LegzesiStrategia getStrategia() {
        return strategia;
    }

    public String getMegjegyzes() {
        return megjegyzes;
    }

    public int getCiklusokSzama() {
        int egyCiklusMp = strategia.getBellegzesMasodperc() + strategia.getKillegzesMasodperc();
        return (percek * 60) / egyCiklusMp;
    }
}