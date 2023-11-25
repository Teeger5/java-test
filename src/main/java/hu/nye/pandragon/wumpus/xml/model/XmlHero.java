package hu.nye.pandragon.wumpus.xml.model;

import hu.nye.pandragon.wumpus.model.Directions;
import hu.nye.pandragon.wumpus.model.Items;
import hu.nye.pandragon.wumpus.model.entities.Entity;
import hu.nye.pandragon.wumpus.model.entities.Hero;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Hero")
public class XmlHero extends XmlEntity {
	@XmlAttribute
	private int arrows;
	@XmlAttribute
	private char direction;
	@XmlAttribute
	private boolean hasGold;

	public XmlHero () {}

	public XmlHero(Hero hero) {
		super(hero.getCompatibilitySymbol(), hero.getPosition());
		this.arrows = hero.getAmmoAmount();
		this.direction = hero.getDirection().getCompatibilitySymbol();
		this.hasGold = hero.hasItem(Items.Gold);
	}

	/**
	 * Létrehoz egy Hero objektumot az itt lévő adatok alapján
	 * @return egy új Hero objektum
	 */
	public Entity getEntity() {
		var hero = new Hero();
		hero.setPosition(getPosition());
		hero.setDirection(Directions.parseSymbol(direction));
		hero.setAmmoAmount(arrows);
		if (hasGold) {
			hero.addItem(Items.Gold);
		}
		return hero;
	}
}
