package hu.pte.prog2.szolgaltatas;

import hu.pte.prog2.adattar.MunkamenetTarolo;
import hu.pte.prog2.domain.ErvenytelenMunkamenetException;
import hu.pte.prog2.domain.LegzesiMunkamenet;
import hu.pte.prog2.domain.LegzesiStrategia;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MunkamenetSzolgaltatas {
    private final MunkamenetTarolo tarolo;
    private final List<LegzesiMunkamenet> munkamenetek;
    private int kovetkezoAzonosito;

    public MunkamenetSzolgaltatas(MunkamenetTarolo tarolo) throws IOException {
        this.tarolo = tarolo;
        this.munkamenetek = new ArrayList<>(tarolo.betolt());
        this.kovetkezoAzonosito = kovetkezoId();
    }

    public LegzesiMunkamenet ujMunkamenet(int percek, LegzesiStrategia strategia, String megjegyzes)
            throws ErvenytelenMunkamenetException, IOException {
        LegzesiMunkamenet uj = new LegzesiMunkamenet(
                kovetkezoAzonosito,
                LocalDateTime.now(),
                percek,
                strategia,
                megjegyzes
        );

        munkamenetek.add(uj);
        try {
            tarolo.mentes(munkamenetek);
        } catch (IOException e) {
            munkamenetek.remove(uj);
            throw e;
        }

        kovetkezoAzonosito++;
        return uj;
    }

    public List<LegzesiMunkamenet> getMunkamenetekIdorendben() {
        List<LegzesiMunkamenet> rendezett = new ArrayList<>(munkamenetek);
        rendezett.sort(Comparator.comparing(LegzesiMunkamenet::getIdopont).reversed());
        return rendezett;
    }

    public boolean torol(int azonosito) throws IOException {
        for (int i = 0; i < munkamenetek.size(); i++) {
            LegzesiMunkamenet m = munkamenetek.get(i);
            if (m.getAzonosito() == azonosito) {
                munkamenetek.remove(i);
                try {
                    tarolo.mentes(munkamenetek);
                } catch (IOException e) {
                    munkamenetek.add(i, m);
                    throw e;
                }
                return true;
            }
        }
        return false;
    }

    public int getMunkamenetekSzama() {
        return munkamenetek.size();
    }

    private int kovetkezoId() {
        int maxId = 0;
        for (LegzesiMunkamenet m : munkamenetek) {
            if (m.getAzonosito() > maxId) {
                maxId = m.getAzonosito();
            }
        }
        return maxId + 1;
    }
}