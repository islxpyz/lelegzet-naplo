# Felhasználói történetek

## Probléma és célközönség
A program azoknak segít, akik szeretnék egyszerűen naplózni, mikor és milyen légzési mintát gyakoroltak. A bejegyzések megmaradnak a program bezárása után is, és később visszanézhetők. A program nem végez élettani mérést és nem ad egészségügyi tanácsot.

## Történet 1: Gyakorlat feljegyzése
Felhasználóként szeretnék választani a két légzési minta közül, megadni a gyakorlás hosszát, és hozzáfűzni egy rövid megjegyzést, hogy később emlékezzek az alkalomra.

**Elfogadási feltételek:**
- Választható a Nyugtató 4-6 és az Egyenletes 4-4 minta.
- Az időtartam 1 és 120 perc közötti egész szám.
- A megjegyzés lehet üres, de legfeljebb 100 karakter hosszú.
- Mentés után a program kiírja a generált azonosítót.

## Történet 2: Korábbi alkalmak áttekintése
Felhasználóként szeretném látni a korábbi bejegyzéseimet időponttal, időtartammal és légzési mintával együtt.

**Elfogadási feltételek:**
- A bejegyzések legújabbtól a legrégebbi felé jelennek meg.
- Üres napló esetén a program ezt jelzi.
- Az összegzés megmutatja a bejegyzések számát és az összes percet.
- Újraindítás után a mentett adatok megmaradnak.

## Történet 3: Téves bejegyzés törlése
Felhasználóként szeretnék törölni egy hibásan rögzített bejegyzést az azonosítója alapján.

**Elfogadási feltételek:**
- Törlés előtt megjelenik a napló az azonosítókkal.
- Nem létező azonosító esetén a program hibát jelez.
- Sikeres törlés után a bejegyzés a fájlból is eltűnik.