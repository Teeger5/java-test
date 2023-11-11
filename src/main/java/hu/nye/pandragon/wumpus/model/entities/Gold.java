package hu.nye.pandragon.wumpus.model.entities;

import hu.nye.pandragon.wumpus.model.Items;
import hu.nye.pandragon.wumpus.service.game.Level;
import hu.nye.pandragon.wumpus.service.traits.CanBePIckedUp;
import hu.nye.pandragon.wumpus.service.traits.HasInventory;
import hu.nye.pandragon.wumpus.service.traits.StaticEntity;

/**
 * Ez az osztály az aranyat, mint pályaelemet írja le
 */
public class Gold extends Entity implements StaticEntity, CanBePIckedUp {

	public Gold() {
		super(false, "Arany", 'G', true, false);
	}

	@Override
	public Entity clone() {
		return new Gold();
	}

	@Override
	public void onPickup(Level level, HasInventory entity) {
		if (entity instanceof LivingEntity livingEntity) {
			entity.addItem(Items.Gold);
			level.removeStaticEntity(livingEntity.getPosition());
		}
	}
}
