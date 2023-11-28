package hu.nye.pandragon.wumpus.persistence;

import hu.nye.pandragon.wumpus.model.LevelVO;
import hu.nye.pandragon.wumpus.model.PlayernameVO;
import hu.nye.pandragon.wumpus.xml.model.XmlLevel;

public interface GameStateRepository {
	/**
	 * Játékállás mentése egy felhaszálóhoz társítva
	 * @param playername a játékos neve
	 * @param levelVO a pálya
	 */
	void save (PlayernameVO playername, LevelVO levelVO) throws Exception;

	/**
	 * Játékállás betöltése
	 * @param playername a játékos neve
	 * @return a pálya, ami mentve volt
	 */
	XmlLevel load (PlayernameVO playername) throws Exception;

	/**
	 * Erőforrások bezárása
	 */
	void close ();
}
