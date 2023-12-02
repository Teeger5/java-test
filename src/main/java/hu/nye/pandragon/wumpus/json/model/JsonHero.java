package hu.nye.pandragon.wumpus.json.model;

import hu.nye.pandragon.wumpus.model.Items;
import hu.nye.pandragon.wumpus.model.entities.Hero;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Data
@RequiredArgsConstructor
@Jacksonized
public class JsonHero {
	private int arrows;
	private char direction;
	private boolean hasGold;

	public JsonHero (Hero hero) {
		arrows = hero.getAmmoAmount();
		direction = hero.getDirection().getCompatibilitySymbol();
		hasGold = hero.hasItem(Items.Gold);
	}
}
