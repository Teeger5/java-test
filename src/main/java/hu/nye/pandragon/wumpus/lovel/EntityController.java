package hu.nye.pandragon.wumpus.lovel;

import hu.nye.pandragon.wumpus.lovel.entities.LivingEntity;

import java.awt.*;

/**
 * Ez az osztály egy lény egy pályán való mozgatásáért felel
 */
public class EntityController {
	private final LivingEntity entity;
	private final Level level;
	private final Point entityPosition;

	public EntityController(Level level, LivingEntity entity) {
		this.level = level;
		this.entity = entity;
		this.entityPosition = entity.getPosition();
	}

	public void moveForward () {
		var direction = entity.getDirection();
		if (direction == Directions.North) {
			entityPosition.y--;
		}
		else if (direction == Directions.East) {
			entityPosition.x++;
		}
		else if (direction == Directions.South) {
			entityPosition.y++;
		}
		else if (direction == Directions.West) {
			entityPosition.x--;
		}
	}

	public void turnRight () {
		entity.setDirection(entity.getDirection().getClockwiseNext());
	}

	public void turnLeft () {
		entity.setDirection(entity.getDirection().getClockwisePrevious());
	}
}
