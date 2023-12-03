package hu.nye.pandragon.wumpus.json.model;

import hu.nye.pandragon.wumpus.model.Items;
import hu.nye.pandragon.wumpus.model.entities.Hero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@AllArgsConstructor
@Builder
@Jacksonized
public class JsonHero {
	private int arrows;
	private char direction;
	private boolean hasGold;

	public JsonHero (Hero hero) {
		this(hero.getAmmoAmount(), hero.getDirection().getCompatibilitySymbol(), hero.hasItem(Items.Gold));
	}
}
