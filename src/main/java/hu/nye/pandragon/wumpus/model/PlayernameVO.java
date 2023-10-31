package hu.nye.pandragon.wumpus.model;

/**
 * Ez az osztály tárolja a játékos nevét.
 * Komolyan
 * Olyan szempontból jó ötlet, hogy nem lehet összekeverni más adattal,
 * ha valahol a játékos nevére van szükség, akkor ez biztosan azt fogja tartalmazni
 * De ilyenből tetszőleges helyen létre lehet hozni bármennyit,
 * ha esetleg mégis rossz adatot szeretnénk megadni valahol a kódban
 */
public class PlayernameVO {
	private final String playerName;

	public PlayernameVO(String playerName) {
		this.playerName = playerName;
	}

	@Override
	public String toString() {
		return playerName;
	}
}
