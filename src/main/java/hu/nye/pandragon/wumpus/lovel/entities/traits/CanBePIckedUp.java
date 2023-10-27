package hu.nye.pandragon.wumpus.lovel.entities.traits;

import hu.nye.pandragon.wumpus.lovel.Level;

public interface CanBePIckedUp {
	void onPickup (Level level, HasInventory entity);
}
