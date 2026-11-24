# UML és tervezési döntések

## Osztálydiagram

```mermaid
classDiagram
direction LR

class App
class KonzolosFelulet {
  -MunkamenetSzolgaltatas szolgaltatas
  -Scanner sc
  +indit()
}
class MunkamenetSzolgaltatas {
  -MunkamenetTarolo tarolo
  -List~LegzesiMunkamenet~ munkamenetek
  -int kovetkezoAzonosito
  +ujMunkamenet(percek, strategia, megjegyzes)
  +getMunkamenetekIdorendben()
  +torol(azonosito)
  +getMunkamenetekSzama()
}
class LegzesiMunkamenet {
  -int azonosito
  -LocalDateTime idopont
  -int percek
  -LegzesiStrategia strategia
  -String megjegyzes
  +getCiklusokSzama()
}
class LegzesiStrategia {
  <<interface>>
  +getAzonosito() String
  +getNev() String
  +getBellegzesMasodperc() int
  +getKillegzesMasodperc() int
  +getLeiras() String
}
class NyugtatoStrategia
class EgyenletesStrategia
class LegzesiStrategiak
class MunkamenetTarolo {
  <<interface>>
  +betolt()
  +mentes(munkamenetek)
}
class FajlMunkamenetTarolo {
  -Path fajlUtvonal
}
class ErvenytelenMunkamenetException

App --> KonzolosFelulet : elindítja
App --> MunkamenetSzolgaltatas : összeállítja
KonzolosFelulet --> MunkamenetSzolgaltatas : menüműveletek
MunkamenetSzolgaltatas --> MunkamenetTarolo : betölt és ment
MunkamenetTarolo <|.. FajlMunkamenetTarolo
MunkamenetSzolgaltatas "1" o-- "0..*" LegzesiMunkamenet : naplólista
LegzesiMunkamenet --> "1" LegzesiStrategia : választott minta
LegzesiStrategia <|.. NyugtatoStrategia
LegzesiStrategia <|.. EgyenletesStrategia
LegzesiStrategiak ..> LegzesiStrategia : azonosítóból létrehozza
LegzesiMunkamenet ..> ErvenytelenMunkamenetException : hibás adatnál dobja
```

## A Strategy minta szerepe
A két légzési mód eltérő belégzési és kilégzési idővel dolgozik. A `LegzesiStrategia` interfész rögzíti a közös műveleteket, amelyeket a `NyugtatoStrategia` és az `EgyenletesStrategia` valósít meg. A `LegzesiMunkamenet` az interfészen keresztül kéri le a másodperceket a ciklusszámításhoz, így a viselkedés futásidőben a választott stratégiától függ. Új minta hozzáadásához elég egy új implementációt írni, a meglévő kód módosítása nélkül.

## Fő tervezési döntések
- A konzolos felület (`KonzolosFelulet`) külön rétegben marad, nem nyúl közvetlenül fájlokhoz, mindent a szolgáltatáson (`MunkamenetSzolgaltatas`) keresztül intéz.
- A mentés UTF-8 kódolású, tabulátorral elválasztott (TSV) fájlba történik (`azonosító`, `időpont`, `perc`, `mintaazonosító`, `megjegyzés`).
- A naplófájlt nem kell előre létrehozni; az első mentés automatikusan létrehozza az `adatok` mappát és a fájlt.
- Ha a fájlba mentés közben `IOException` történik, a szolgáltatás visszaállítja a memóriában lévő listát, hogy ne maradjon inkonzisztens állapot.

## Korlátok
- Egyetlen lokális fájlt kezel, nincs többfelhasználós vagy hálózati támogatás.
- Sérült fájlsor esetén a betöltés hibával leáll és kiírja a hibás sor számát.
- A ciklusszám matematikai becslés a megadott időtartam és minta alapján.