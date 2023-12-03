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
- szétterül-e egy cellában (a térképen kirajzoláskor be kell-e teríteni a mezőt a karakterével, 
- vagy elég középre rajzolni egyet. A verem szétterül az érthetőség miatt)

Minden pályaelem rendelkezik ezekkel a tulajdonságokkal, és lehetnek egyedi tulajdonságaik is 
(pl. a fal formája, ami a megjelenített karaktert is meghatárorozza).

A játékban a képességeket trait-eknek nevezik, és interface-ek írják le őket. 
Trait például a lövés képessége (CanShoot), és a tárgyak tárolásának a képessége (HasInventory).

A HasInventory-nak érdekessége leht, hogy default interface metódusokat használ. 
Azaz, egy kötelezően implementálandó metódusa van az interface-nek (getInventory()), 
a többi metódus ezt felhasználva hozzá tud férni a tárgyakhoz, és tud módosításokat is végezni
a játékos tárgyain (változtatni a mennyiségen, elkérni a számukat, és ellenőrizni egy tárgy meglétét).

A CanShoot implementációja a hősnél egy `arrows` változóval történik, ami tárolja a nyilak számát.
Egy későbbi verzióban érdemes lehet a nyilat is tárggyá tenni, mert ezzel jobban beleillene a kialakításba; 
lehet úgy is implementálni, hogy az eszköztárban lévő nyilak, mint tárgyak számát vegye figyelembe.

A parancsok feldolgozása és értelmezése elsőre bonyolultnak tűnt a Sudoku projektnél, 
viszont miután sikerült megérteni, hogyan működik, itt is jól lehet alkalmazni egy hasonló
megközelítést, ami kicsit egyszerűbb. Itt kimaradtak a `Performer`-ek, és 
a parancsban leírt műveletet a parancs implementációja végzi el közvetlenül.

Az adatok mentése az adatbázisban XML-ben történik. A JSON csak teszteknél van használatban.

### Pályaszerkesztő parancsai
`legyen hős|fal|verem|wumpus|arany oszlop sor`
Új pályaelem létrehozása a megadott pozíción

`törlés oszlop sor`
Pályaelem törlése a megadott pozícióról

`hős fordul N|E|S|W`
Hős forgatása a megadott égtáj irányába

`kész`
Pálya mentése, és kilépés a pályaszerkesztőből

### Játék parancsai
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
