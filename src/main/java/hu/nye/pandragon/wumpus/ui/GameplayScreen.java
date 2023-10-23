package hu.nye.pandragon.wumpus.ui;

import hu.nye.pandragon.wumpus.Utils;
import hu.nye.pandragon.wumpus.lovel.EntityController;
import hu.nye.pandragon.wumpus.lovel.Items;
import hu.nye.pandragon.wumpus.lovel.Level;
import hu.nye.pandragon.wumpus.lovel.entities.Hero;

import java.util.List;

/**
 * Ez az osztály a játékot, mint a játékmenetet írja le és irányítja
 */
public class GameplayScreen {
	private final Level level;
	private final Hero hero;
	private int numberOfMoves;
	private final String playerName;
	private final List<EntityController> entityControllers;

	public GameplayScreen(Level level, String playerName) {
		this.level = level;
		this.playerName = playerName;
		numberOfMoves = 0;
		this.hero = level.getHero();
		this.entityControllers = level.getEntityControllers();
	}

	public void start () {
		readCommands();
	}

	private void readCommands () {
		while (true) {
			if (!hero.isAlive()) {
				System.out.printf("Sajnos meghalt a karaktered.\n%d lépést tettél meg.\n", numberOfMoves);
				System.out.println("Nyomj meg egy billentyűt a folytatáshoz...");
				Utils.readFromConsole();
				break;
			}
			if (hero.getPosition().equals(level.getStartPoint()) && hero.hasItem(Items.Gold)) {
				System.out.printf("Győztél, sikeresen visszahoztad az aranyat a kiindulási helyre\n Megtettél %d lépést.\n", numberOfMoves);
				break;
			}
			System.out.printf(
					"Hős: %s | %d %s | %d nyíl\n",
					hero.getDisplaySymbol(),
					hero.getPosition().x,
					(char) (hero.getPosition().y + 64),
					hero.getAmmoAmount());
			System.out.print("> ");
			var command = Utils.readFromConsole();

		}
	}
}
