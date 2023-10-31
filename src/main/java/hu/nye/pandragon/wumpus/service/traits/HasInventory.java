package hu.nye.pandragon.wumpus.service.traits;

import hu.nye.pandragon.wumpus.model.Items;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Az ezt implementáló lények tudnak tárgyakat felvenni és gyűjteni
 */
public interface HasInventory {
	List<Items> getInventory ();

	/**
	 * Új tárgy hozzáadása
	 * @param item
	 */
	default void addItem (Items item) {
		getInventory().add(item);
	}

	/**
	 * Tárgy eltávolítása (1-et, azaz nem feltétlenül mindet)
	 * @param item a tárgy típusa
	 */
	default void removeItem (Items item) {
		getInventory().remove(item);
	}

	/**
	 * Visszaadja, hány db van a lénynek az adott tárgyből
	 * @param item a tárgy
	 * @return a mennyisége
	 */
	default int getItemCount (Items item) {
		return getInventory().stream().filter(e -> e.getClass() == item.getClass()).collect(Collectors.toList()).size();
	}

	/**
	 * Visszaadja, hány tárgya van a lénynek összesen
	 * @return a tárgyainak száma összesen
	 */
	default int getItemCount () {
		return getInventory().size();
	}

	/**
	 * Visszaadja, van-e a lénynek az adott tárgyból
	 * @param item true, ha van a lénynek ilyen tárgya
	 * @return
	 */
	default boolean hasItem (Items item) {
		return getInventory().contains(item);
	}
}
