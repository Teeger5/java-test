package hu.nye.pandragon.wumpus.service.traits;

import hu.nye.pandragon.wumpus.model.Items;

import java.util.Map;

/**
 * Az ezt implementáló lények tudnak tárgyakat felvenni és gyűjteni
 */
public interface HasInventory {
	Map<Items, Integer> getInventory ();

	/**
	 * Tárgy hozzáadása (1 db)
	 * @param item a tárgy
	 */
	default void addItem (Items item) {
		addItem(item, 1);
	}

	/**
	 * Tárgy hozzáadása
	 * @param item a tárgy
	 * @param count mennyit
	 */
	default void addItem (Items item, int count) {
		var inv = getInventory();
		inv.put(item, switch (inv.get(item)) {
			case null -> count;
			case Integer currentCount -> currentCount + count;
		});
		if (inv.get(item) < 1) {
			inv.remove(item);
		}
	}

	/**
	 * Tárgy mennyiségének csökkentése 1-gyel
	 * @param item a tárgy típusa
	 */
	default void decreaseItem (Items item) {
		addItem(item, -1);
	}

	/**
	 * Minden ilyen típusú tárgy eltávolítása
	 * @param item
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
		return getInventory().get(item);
	}

	/**
	 * Beállítja, mennyi a lénynek ebből a tárgyból
	 * Ha nincs még ilyenje, akkor hozzá fogja adni
	 * @param item a tárgy
	 * @param count a mennyisége
	 */
	default void setItemCount (Items item, int count) {
		getInventory().put(item, count);
	}

	/**
	 * Visszaadja, van-e a lénynek az adott tárgyból
	 * @param item true, ha van a lénynek ilyen tárgya
	 * @return
	 */
	default boolean hasItem (Items item) {
		return getInventory().containsKey(item);
	}
}
