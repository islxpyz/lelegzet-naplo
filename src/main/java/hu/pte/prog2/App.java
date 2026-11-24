package hu.pte.prog2;

import hu.pte.prog2.adattar.FajlMunkamenetTarolo;
import hu.pte.prog2.adattar.MunkamenetTarolo;
import hu.pte.prog2.felulet.KonzolosFelulet;
import hu.pte.prog2.szolgaltatas.MunkamenetSzolgaltatas;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class App {
    private static final Path NAPLO_UTVONAL = Path.of("adatok", "legzesnaplo.tsv");

    public static void main(String[] args) {
        try {
            MunkamenetTarolo tarolo = new FajlMunkamenetTarolo(NAPLO_UTVONAL);
            MunkamenetSzolgaltatas szolgaltatas = new MunkamenetSzolgaltatas(tarolo);

            try (Scanner sc = new Scanner(System.in)) {
                KonzolosFelulet felulet = new KonzolosFelulet(szolgaltatas, sc);
                felulet.indit();
            }
        } catch (IOException e) {
            System.out.println("A naplófájlt nem sikerült megnyitni: " + e.getMessage());
        }
    }
}