# Wumpus
### Labirintusos játék egy Java nyelvű megvalósítása

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
- Implementálni kell az toplista megjelenítését
- Implementálni kell a játékállás adatbázisból való betöltését
- Meg kell csinálni az XML fájlba való mentést és betöltést

## Kérdések
- Szabad-e használni a Lombok-ot?
