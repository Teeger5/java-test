package hu.nye.pandragon.wumpus;

import hu.nye.pandragon.wumpus.lovel.Entities;
import hu.nye.pandragon.wumpus.lovel.LevelEditor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Scanner;

import static hu.nye.pandragon.wumpus.Utils.readFromConsole;

/**
 * Ez az osztály felel a játék irányításáért. Először a főmenü elemeinek kezelését kell megoldani
 */
public class GameMain {
	private String playerName;

	public GameMain() {
		onStart();
		enterMenu();
	}

	private void onStart () {
		System.out.println("Üdvözöllek Wumpus világában");
		requestPlayerName();
		System.out.printf("GG, %s! Kezdjünk hozzű!\n", playerName);
	}

	private void requestPlayerName () {
		System.out.print("Add meg a játékosneved: ");
		playerName = readFromConsole();
	}

	public void enterMenu () {
		System.out.printf("%20s\n", "Menü");
		showMenuOptions();
		while (true) {
			System.out.print("> ");
			var command = Utils.readFromConsole();
			if (command.equals("1")) {
				enterEditor();
				showMenuOptions();
			}
			else if (command.equals("2")) {
				exit();
			}
			else {
				System.out.println("Ismeretlen opció: " + command);
			}
		}
	}

	private void showMenuOptions () {
		System.out.println("1. Pályaszerkesztő\n2. Kilépés\n");
	}

	public void enterEditor () {
		new LevelEditor();
	}

	public void enterGame () {

	}

	public void exit () {
		System.out.printf("Viszontlátásra, %s!\n", playerName);
		System.exit(0);
	}

	/*
	A mentést és a betöltést meg kell kérdeznem, nem egyértelmű, mire vonatkozik
	 */
}
