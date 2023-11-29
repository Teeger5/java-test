# Wumpus
### Labirintusos játék egy Java nyelvű megvalósítása
A játékos a hőst irányítja, a célja pedig megszerezni, és visszahozni a kiinduló helyére az aranyat, ami a pályán van valahol.
# A játék kialakítása
A pályaelemeknek 2 fajtája van: statikus pályaelemek (a fal, a verem, és az arany), és lények (a hős és a Wumpus)
A pályaelemek tulajdonságai alapvetően:
- név
- szimbólum (megjelenített és egységesített / kompatibilitási)
- blokkol-e (át lehet-e menni rajta vagy sem, a verem és az arany nem blokkol, a fal igen)
- egyedi-e (csak egy lehet belőle a pályán)
- szétterül-e egy cellában (a térképen kirajzoláskor be kell-e teríteni a mezőt a karakterével, vagy elég középre rajzolni egyet. A verem szétterül az érthetőség miatt)

A fal egy érdekes eset viszont: a megjelenített karakterek illeszkednek egymáshoz. Azaz, ha 3 fal sarkot formálva van a pályán egymás mellett, L betű szerűen, akkor az egyik karaktere függőleges, a másiké vízszintes, a sarkon lévőe pedig sarok dobozrajzoló karakter lesz. Sok ilyen eshetőség van, pl. formázhatnak elágazást, + jelet is.


### Pályaszerkesztő parancsok
`legyen hős|fal|verem|wumpus|arany oszlop sor`
Új pályaelem létrehozása a megadott pozíción

`törlés oszlop sor`
Pályaelem törlése a megadott pozícióról

`hős fordul N|E|S|W`
Hős forgatása a megadott égtáj irányába

`kész`
Pálya mentése, és kilépés a pályaszerkesztőből

### Játék parancsok
`lép`
A hős előre lép egyet, azaz az előtte lévő pozícióra

`fordul jobbra|balra`
A hős jobbra / balra fordul

`aranyat felszed`
A hős felveszi az aranyat, ha egy pozíción van vele

`lő`
A hős lő, a nyíl a nézési irányában halad, és eltalálja az első útjába eső pályaelemet

`felad`
Kilépés a játékból

`halasztás`
Játék mentése az adatbázisba

## Hátra lévő fontos feladatok
A jelenlegi állapot alapján
- Teszteket kell írni, minél többet
- Megjelölni a kezdőhelyet a pályán kirajzoláskor
