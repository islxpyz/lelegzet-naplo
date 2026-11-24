package hu.pte.prog2.felulet;

import hu.pte.prog2.domain.EgyenletesStrategia;
import hu.pte.prog2.domain.ErvenytelenMunkamenetException;
import hu.pte.prog2.domain.LegzesiMunkamenet;
import hu.pte.prog2.domain.LegzesiStrategia;
import hu.pte.prog2.domain.NyugtatoStrategia;
import hu.pte.prog2.szolgaltatas.MunkamenetSzolgaltatas;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class KonzolosFelulet {
    private static final DateTimeFormatter IDO_FORMATUM =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final MunkamenetSzolgaltatas szolgaltatas;
    private final Scanner sc;

    public KonzolosFelulet(MunkamenetSzolgaltatas szolgaltatas, Scanner sc) {
        this.szolgaltatas = szolgaltatas;
        this.sc = sc;
    }

    public void indit() {
        boolean fut = true;
        while (fut) {
            kiirFejlec();
            System.out.println("  1. Új bejegyzés");
            System.out.println("  2. Napló megtekintése");
            System.out.println("  3. Bejegyzés törlése");
            System.out.println("  4. Rövid összegzés");
            System.out.println("  0. Kilépés");
            System.out.print("\nVálasztás: ");

            String valasz = sc.nextLine().trim();
            switch (valasz) {
                case "1":
                    ujMunkamenet();
                    break;
                case "2":
                    naploMegjelenitese();
                    break;
                case "3":
                    munkamenetTorlese();
                    break;
                case "4":
                    osszegzesMegjelenitese();
                    break;
                case "0":
                    fut = false;
                    System.out.println("\nA bejegyzések el vannak mentve. Viszlát!");
                    break;
                default:
                    System.out.println("\nEzt a menüpontot nem ismerem.");
            }
        }
    }

    private void ujMunkamenet() {
        LegzesiStrategia strategia = strategiaValasztasa();
        if (strategia == null) {
            return;
        }

        int percek = egeszSzamBeolvasasa("Időtartam percben (1-120): ");
        System.out.print("Megjegyzés (legfeljebb 100 karakter, lehet üres): ");
        String megjegyzes = sc.nextLine();

        try {
            LegzesiMunkamenet m = szolgaltatas.ujMunkamenet(percek, strategia, megjegyzes);
            System.out.println("\nA bejegyzés elmentve. Azonosító: " + m.getAzonosito());
        } catch (ErvenytelenMunkamenetException e) {
            System.out.println("\nNem sikerült elmenteni: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("\nA naplófájlt nem sikerült elmenteni: " + e.getMessage());
        }
    }

    private LegzesiStrategia strategiaValasztasa() {
        LegzesiStrategia nyugtato = new NyugtatoStrategia();
        LegzesiStrategia egyenletes = new EgyenletesStrategia();

        while (true) {
            System.out.println("\nVálassz légzési mintát:");
            System.out.println("  1. " + nyugtato.getNev() + " - " + nyugtato.getLeiras());
            System.out.println("  2. " + egyenletes.getNev() + " - " + egyenletes.getLeiras());
            System.out.println("  0. Vissza");
            System.out.print("Választás: ");
            String valasz = sc.nextLine().trim();

            if ("1".equals(valasz)) {
                return nyugtato;
            }
            if ("2".equals(valasz)) {
                return egyenletes;
            }
            if ("0".equals(valasz)) {
                return null;
            }
            System.out.println("Kérlek, az 1, 2 vagy 0 lehetőséget válaszd.");
        }
    }

    private int egeszSzamBeolvasasa(String kerdes) {
        while (true) {
            System.out.print(kerdes);
            String szoveg = sc.nextLine().trim();
            try {
                return Integer.parseInt(szoveg);
            } catch (NumberFormatException e) {
                System.out.println("Egész számot írj be, például 5-öt.");
            }
        }
    }

    private void naploMegjelenitese() {
        List<LegzesiMunkamenet> lista = szolgaltatas.getMunkamenetekIdorendben();
        if (lista.isEmpty()) {
            System.out.println("\nA napló még üres.");
            return;
        }

        System.out.println("\n--- Legutóbbi bejegyzések ---");
        for (LegzesiMunkamenet m : lista) {
            System.out.println(munkamenetSor(m));
        }
    }

    private void munkamenetTorlese() {
        naploMegjelenitese();
        if (szolgaltatas.getMunkamenetekSzama() == 0) {
            return;
        }

        int id = egeszSzamBeolvasasa("\nA törlendő azonosító: ");
        try {
            if (szolgaltatas.torol(id)) {
                System.out.println("A bejegyzés törölve lett.");
            } else {
                System.out.println("Nincs ilyen azonosítójú bejegyzés.");
            }
        } catch (IOException e) {
            System.out.println("Nem sikerült frissíteni a naplófájlt: " + e.getMessage());
        }
    }

    private void osszegzesMegjelenitese() {
        List<LegzesiMunkamenet> lista = szolgaltatas.getMunkamenetekIdorendben();
        int osszesPerc = 0;
        for (LegzesiMunkamenet m : lista) {
            osszesPerc += m.getPercek();
        }
        System.out.println("\n--- Összegzés ---");
        System.out.println("Bejegyzések száma: " + lista.size());
        System.out.println("Összes feljegyzett idő: " + osszesPerc + " perc");
    }

    private String munkamenetSor(LegzesiMunkamenet m) {
        String sor = String.format(
                "[%d] %s | %d perc | %s | kb. %d teljes ciklus",
                m.getAzonosito(),
                m.getIdopont().format(IDO_FORMATUM),
                m.getPercek(),
                m.getStrategia().getNev(),
                m.getCiklusokSzama()
        );

        if (!m.getMegjegyzes().isEmpty()) {
            sor += " | " + m.getMegjegyzes();
        }
        return sor;
    }

    private void kiirFejlec() {
        System.out.println("\n==================================");
        System.out.println("          LÉLEGZETNAPLÓ");
        System.out.println("==================================");
        System.out.println("Feljegyzett alkalmak: " + szolgaltatas.getMunkamenetekSzama());
        System.out.println("----------------------------------");
    }
}