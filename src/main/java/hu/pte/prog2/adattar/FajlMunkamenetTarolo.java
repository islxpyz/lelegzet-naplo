package hu.pte.prog2.adattar;

import hu.pte.prog2.domain.ErvenytelenMunkamenetException;
import hu.pte.prog2.domain.LegzesiMunkamenet;
import hu.pte.prog2.domain.LegzesiStrategia;
import hu.pte.prog2.domain.LegzesiStrategiak;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class FajlMunkamenetTarolo implements MunkamenetTarolo {
    private final Path fajlUtvonal;

    public FajlMunkamenetTarolo(Path fajlUtvonal) {
        this.fajlUtvonal = fajlUtvonal;
    }

    @Override
    public List<LegzesiMunkamenet> betolt() throws IOException {
        List<LegzesiMunkamenet> lista = new ArrayList<>();
        if (!Files.exists(fajlUtvonal)) {
            return lista;
        }

        try (BufferedReader br = Files.newBufferedReader(fajlUtvonal, StandardCharsets.UTF_8)) {
            String sor;
            int sorszam = 0;
            while ((sor = br.readLine()) != null) {
                sorszam++;
                if (sor.isBlank()) {
                    continue;
                }

                String[] mezok = sor.split("\t", 5);
                if (mezok.length != 5) {
                    throw new IOException("Hibás adat a napló " + sorszam + ". sorában.");
                }

                try {
                    int id = Integer.parseInt(mezok[0]);
                    LocalDateTime idopont = LocalDateTime.parse(mezok[1]);
                    int percek = Integer.parseInt(mezok[2]);
                    LegzesiStrategia strategia = LegzesiStrategiak.azonositoAlapjan(mezok[3]);
                    lista.add(new LegzesiMunkamenet(id, idopont, percek, strategia, mezok[4]));
                } catch (IllegalArgumentException | DateTimeParseException | ErvenytelenMunkamenetException e) {
                    throw new IOException("Hibás adat a napló " + sorszam + ". sorában.", e);
                }
            }
        }

        return lista;
    }

    @Override
    public void mentes(List<LegzesiMunkamenet> munkamenetek) throws IOException {
        Path mappa = fajlUtvonal.toAbsolutePath().getParent();
        if (mappa != null) {
            Files.createDirectories(mappa);
        }

        try (BufferedWriter bw = Files.newBufferedWriter(fajlUtvonal, StandardCharsets.UTF_8)) {
            for (LegzesiMunkamenet m : munkamenetek) {
                String sor = m.getAzonosito()
                        + "\t" + m.getIdopont()
                        + "\t" + m.getPercek()
                        + "\t" + m.getStrategia().getAzonosito()
                        + "\t" + m.getMegjegyzes();
                bw.write(sor);
                bw.newLine();
            }
        }
    }
}