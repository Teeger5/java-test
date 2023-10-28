package hu.nye.pandragon.wumpus.service.traits;

import hu.nye.pandragon.wumpus.service.game.Level;

public interface CanBePIckedUp {
	void onPickup (Level level, HasInventory entity);
}
