package hu.nye.pandragon.wumpus.service;

import hu.nye.pandragon.wumpus.lovel.Level;
import hu.nye.pandragon.wumpus.lovel.entities.Entity;
import hu.nye.pandragon.wumpus.lovel.entities.Hero;
import hu.nye.pandragon.wumpus.lovel.entities.LivingEntity;
import hu.nye.pandragon.wumpus.model.LevelVO;

public class PlaceEntityValidator {
	public static boolean valudate (LevelVO levelVO, Entity entity) {
/*		var entites = entity instanceof LivingEntity ? levelVO.getLivingEntities() : levelVO.getStaticEntities();
		if (entity.isUnique()) {
			var e = (LivingEntity) removeEntityIfExists(entity, livingEntities);
			if (e != null) {
				entity = e;
			}
			logger.debug(String.format("Unique entity megvan, pozíció -> %d %d", x, y));
			logger.debug(String.format("Key: " + livingEntities.get(e.getPosition())));
			if (entity instanceof Hero) {
				determineStartPoint();
			}
		}*/
		return false;
	}
}
