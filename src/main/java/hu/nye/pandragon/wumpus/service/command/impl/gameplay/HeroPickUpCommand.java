package hu.nye.pandragon.wumpus.service.command.impl.gameplay;

import hu.nye.pandragon.wumpus.lovel.EntityController;
import hu.nye.pandragon.wumpus.lovel.Level;
import hu.nye.pandragon.wumpus.lovel.entities.traits.CanBePIckedUp;
import hu.nye.pandragon.wumpus.service.command.CanProcessResult;
import hu.nye.pandragon.wumpus.service.command.Command;
import hu.nye.pandragon.wumpus.service.command.GameplayCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Command used to write a number to a given field of the map.
 */
public class HeroPickUpCommand implements Command {

	private static final Logger LOGGER = LoggerFactory.getLogger(HeroPickUpCommand.class);
	private final Level level;

	public HeroPickUpCommand(Level level) {
		this.level = level;
	}

	@Override
	public Optional<CanProcessResult> canProcess(String input) {
		return GameplayCommands.PickUpGold.matches(input);
	}

	@Override
	public void process(String input) {
		LOGGER.info("A hős megpróbálja felvenni a pozícióján lévő tárgyat");
		var hero = level.getHero();
		if (level.toLevelVO().getStaticEntities().get(hero.getPosition()) instanceof CanBePIckedUp item) {
			item.onPickup(level, hero);
			LOGGER.info("A hős felvett egy új tárgyat: {}, a tárgyai mosta: {}", item, hero.getInventory());
		}
		else {
			LOGGER.warn("Nincs felvehető tárgy ezen a pozíción: " + hero.getPosition());
			throw new RuntimeException("Itt nincs felvehető tárgy");
		}
	}
}
