# Lélegzetnapló

Egyszerű, magyar nyelvű Java konzolprogram légzőgyakorlatok naplózására. A felhasználó kiválaszt egy légzési mintát, megadja az időtartamot és opcionálisan egy megjegyzést, a mentett bejegyzéseket pedig később visszanézheti vagy törölheti.

Ez egy Programozás 2 tantárgyi beadandó. Nem mér pulzust, nem használ kamerát, és nem ad egészségügyi tanácsot.

## Főbb funkciók
- Új légzésgyakorlat rögzítése opcionális megjegyzéssel.
- Két légzési minta közötti választás: Nyugtató 4-6 és Egyenletes 4-4.
- Korábbi bejegyzések listázása időrendben (legújabbtól a legrégebbi felé).
- Rövid összegzés (alkalmak száma és összes feljegyzett perc).
- Bejegyzés törlése azonosító alapján.
- Automatikus mentés az `adatok/legzesnaplo.tsv` fájlba.

## Előkészületek
- Java Development Kit (JDK) 21
- Apache Maven 3.9 vagy újabb

## Fordítás, tesztelés és indítás
A projekt gyökérmappájából:

```bash
mvn clean test
mvn package
java -jar target/lelegzet-naplo-1.0.0.jar
```

A tesztek külön is futtathatók a `mvn test` paranccsal.

## Használati példa
1. Válaszd az **1. Új bejegyzés** menüpontot.
2. Válassz légzési mintát (pl. `1` - Nyugtató 4-6).
3. Add meg az időtartamot percben (`1-120` között).
4. Írj be egy rövid megjegyzést (pl. `tanulás után`), vagy hagyd üresen.
5. A **2. Napló megtekintése** menüpontban látható a dátum, a választott minta és a becsült teljes légzési ciklusok száma.

## Megvalósított OOP követelmények
- **Encapsulation:** a `LegzesiMunkamenet` mezői `private final` láthatóságúak, a konstruktor validálja a bemenetet.
- **Abstraction:** a `LegzesiStrategia` és `MunkamenetTarolo` interfészek választják szét a szerződést a megvalósítástól.
- **Inheritance és polymorphism:** a `NyugtatoStrategia` és `EgyenletesStrategia` megvalósítja a `LegzesiStrategia` interfészt, a ciklusszámítás futásidőben a választott típustól függ.
- **Relationships:** minden `LegzesiMunkamenet` egy `LegzesiStrategia` objektumot használ, a `MunkamenetSzolgaltatas` pedig `0..*` munkamenetet kezel.
- **Collections, generics és sorting:** `ArrayList<LegzesiMunkamenet>` tárolás, időrendi rendezés `Comparator` segítségével.
- **Exceptions:** saját checked kivétel (`ErvenytelenMunkamenetException`) a domain szabályok megsértésére, valamint `IOException` kezelés fájlhibák esetén.
- **Persistence:** UTF-8 kódolású TSV fájlkezelés `FajlMunkamenetTarolo` osztályban, `try-with-resources` blokkokkal.

## Tervezési minta: Strategy
A `LegzesiStrategia` interfész definiálja a légzési minták közös műveleteit, amelyet a `NyugtatoStrategia` és az `EgyenletesStrategia` valósít meg. A `LegzesiMunkamenet` kizárólag az interfészen keresztül kéri le a be- és kilégzési időket a `getCiklusokSzama()` számításához. Így új légzéstechnika később egyszerűen hozzáadható a meglévő munkamenet-osztály módosítása nélkül.

## Dokumentációk
- [Felhasználói történetek](docs/felhasznaloi_tortenetek.md)
- [UML osztálydiagram és tervezési döntések](docs/uml.md)

## Ismert korlátok
- A bejegyzések kézi naplóadatok, nincs automatikus mérés vagy szenzor.
- A program egyetlen lokális TSV fájlt használ, több felhasználót nem kezel.

## Készítette
- Ocskai Alex (islxpyz)