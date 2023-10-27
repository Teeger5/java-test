package hu.nye.pandragon.wumpus.lovel.entities;

import hu.nye.pandragon.wumpus.model.Items;
import hu.nye.pandragon.wumpus.lovel.Level;
import hu.nye.pandragon.wumpus.lovel.entities.traits.CanBePIckedUp;
import hu.nye.pandragon.wumpus.lovel.entities.traits.HasInventory;

/**
 * Ez az osztály az aranyat, mint pályaelemet írja le
 */
public class Gold extends Entity implements CanBePIckedUp {

	public Gold() {
		super(false, "Arany", 'G', true, false);
	}

	@Override
	public Entity clone() {
		return new Gold();
	}

	@Override
	public void onPickup(Level level, HasInventory entity) {
		entity.addItem(Items.Gold);
	}
}
