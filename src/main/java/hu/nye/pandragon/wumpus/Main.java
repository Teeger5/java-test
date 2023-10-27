package hu.nye.pandragon.wumpus;

import hu.nye.pandragon.wumpus.ui.GameMainScreen;

public class Main {

	// Hogyan kaphatjuk meg a pályát a játék indításakor?
	// A betöltés fájlból vihez a játékhoz és a szerkesztőbe is
	// A szerkesztőben lehet lehetőség a tesztelésre egy paranccsal
	// A szerkesztés után a pályát a játéknak tárolnia kell, és a játék indításakor (a főmenüből) az töltődjön be?
	// Ekkor hol lehetne tárolni a pályát addig?
	// A játék vezérlése a képernyőkre ("Screen") épül, azaz egyikből át lehet lépni a másikba,
	// egyik vihet át adatot egy olyanba, amely belőle nyílik,
	// viszont visszafelé a jelen tervezés alapján ez nem lehetséges,
	// mivel az adatokat nem a képernyőket és az ottani viselkedést leírő osztályokban kell tárolni
	// Ha implementálva lenne a fájlból beolvasás, akkor a főmenüben a Játék menüpontot választva mindig a fájl töltődne be
	// Ez nem biztos, hogy az elvárt viselkedés, ha van lehetőség külön betöltésre
	// Nagyon jó lenne valahol a Játék lehetőséget választva a pályaszerkesztőben készült pályát betölteni

	public static void main(String[] args) {
		new GameMainScreen();
	}
}