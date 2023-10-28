package hu.nye.pandragon.wumpus.service.traits;

import hu.nye.pandragon.wumpus.model.Items;

import java.util.List;

public interface HasInventory {
	List<Items> getInventory ();

	default void addItem (Items item) {
		getInventory().add(item);
	}

	default void removeItem (Items item) {
		getInventory().remove(item);
	}

	default int getItemCount (Items item) {
		return getInventory().size();
	}

	default boolean hasItem (Items item) {
		return getInventory().contains(item);
	}
}
