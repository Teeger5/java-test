package hu.nye.pandragon.wumpus.lovel;

import hu.nye.pandragon.wumpus.lovel.entities.LivingEntity;
import hu.nye.pandragon.wumpus.model.Directions;
import hu.nye.pandragon.wumpus.model.TurnDirections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

/**
 * Ez az osztály egy lény egy pályán való mozgatásáért felel
 */
public class EntityController {

	private final Logger logger = LoggerFactory.getLogger(EntityController.class);

	private final LivingEntity entity;
	private final Level level;
	private final Point entityPosition;

	public EntityController(Level level, LivingEntity entity) {
		this.level = level;
		this.entity = entity;
		this.entityPosition = entity.getPosition();
	}

	public boolean moveForward () {
		var direction = entity.getDirection();
		var possibleDirections = level.getPossibleMoves(entityPosition);
		logger.debug("Lehetséges járható irányok: " + possibleDirections);
		if (!possibleDirections.containsKey(direction)) {
			return false;
		}
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
		level.placeEntity(entityPosition.x, entityPosition.y, entity);
		return true;
	}

	public void turn (TurnDirections direction) {
		if (direction == TurnDirections.Left) {
			turnLeft();
		}
		else {
			turnRight();
		}
	}

	public void turnRight () {
		entity.setDirection(entity.getDirection().getClockwiseNext());
	}

	public void turnLeft () {
		entity.setDirection(entity.getDirection().getClockwisePrevious());
	}
}
