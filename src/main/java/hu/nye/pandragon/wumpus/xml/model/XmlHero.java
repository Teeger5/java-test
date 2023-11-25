package hu.nye.pandragon.wumpus.xml.model;

import hu.nye.pandragon.wumpus.model.Directions;
import hu.nye.pandragon.wumpus.model.Items;
import hu.nye.pandragon.wumpus.model.entities.Entity;
import hu.nye.pandragon.wumpus.model.entities.Hero;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.awt.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Hero")
public class XmlHero {
	@XmlAttribute
	private int arrows;
	@XmlAttribute
	private String direction;
	@XmlAttribute
	private String hasGold;

	public XmlHero () {}

	public XmlHero(Hero hero) {
		this.arrows = hero.getAmmoAmount();
		this.direction = Character.toString(hero.getDirection().getCompatibilitySymbol());
		this.hasGold = Boolean.toString(hero.hasItem(Items.Gold));
	}

	/**
	 * Létrehoz egy Hero objektumot az itt lévő adatok alapján
	 * @return egy új Hero objektum
	 */
	public Entity getEntity(Point position) {
		var hero = new Hero();
		hero.setPosition(position);
		hero.setDirection(Directions.parseSymbol(direction.charAt(0)));
		hero.setAmmoAmount(arrows);
		if (Boolean.getBoolean(hasGold)) {
			hero.addItem(Items.Gold);
		}
		return hero;
	}
}
