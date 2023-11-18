package hu.nye.pandragon.wumpus.service.game;

import hu.nye.pandragon.wumpus.model.Directions;
import hu.nye.pandragon.wumpus.model.TurnDirections;
import hu.nye.pandragon.wumpus.model.entities.Entity;
import hu.nye.pandragon.wumpus.model.entities.Hero;
import hu.nye.pandragon.wumpus.model.entities.LivingEntity;
import hu.nye.pandragon.wumpus.service.traits.ActionOnLivingEntityEnters;
import hu.nye.pandragon.wumpus.service.traits.CanShoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.Objects;

/**
 * Ez az osztály egy lény egy pályán való mozgatásáért felel
 */
public class EntityController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EntityController.class);

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
		LOGGER.debug("Lehetséges járható irányok: " + possibleDirections);
		if (!possibleDirections.containsKey(direction)) {
			return false;
		}
		int x = entityPosition.x, y = entityPosition.y;
		if (direction == Directions.North) {
			y--;
		}
		else if (direction == Directions.East) {
			x++;
		}
		else if (direction == Directions.South) {
			y++;
		}
		else if (direction == Directions.West) {
			x--;
		}
		if (entity instanceof Hero hero) {
			var levelVO = level.toLevelVO();
			var point = new Point(x, y);
			Entity nextEntity = levelVO.getLivingEntities().get(point);
			if (nextEntity == null) {
				nextEntity = levelVO.getStaticEntities().get(point);
			}
			LOGGER.debug("hero move next entity: " + nextEntity);
			if (nextEntity instanceof ActionOnLivingEntityEnters location) {
				location.onLivingEntityEnters(hero);
			}
		}
		LOGGER.debug("moveForward: ", entity);
		level.removeEntityIfExists(entity);
		if (entity.isAlive()) {
			level.placeEntity(x, y, entity);
		}
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

	public void shoot () {
		if (entity instanceof CanShoot shooter) {
			var target = level.getFirstEntityInDirection(entityPosition, entity.getDirection(), true);
			System.out.println("target: " + target.getClass());
			if (target instanceof LivingEntity livingEntity) {
				livingEntity.kill(level);
			}
			shooter.onShoot();
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		EntityController that = (EntityController) o;
		return Objects.equals(entity, that.entity) && Objects.equals(level, that.level) && Objects.equals(entityPosition, that.entityPosition);
	}

	@Override
	public int hashCode() {
		return Objects.hash(entity, level, entityPosition);
	}
}
