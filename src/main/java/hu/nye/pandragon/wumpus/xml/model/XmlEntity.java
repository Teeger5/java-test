package hu.nye.pandragon.wumpus.xml.model;

import hu.nye.pandragon.wumpus.model.Entities;
import hu.nye.pandragon.wumpus.model.entities.Entity;
import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

/**
 * Ez az osztály egy pályaelemet, mint XML node-ot ír le
 * Azért érdemes külön venni -- az egyszerűsége ellenére --,
 * mert a pozíció nem tulajdonsága egy pályaelemnek alapvetően,
 * nem része a belső állapotának, hanem a pályától függ inkább
 * Kivétel a lények, mert nekik a velük végzett műveleteknél
 * a könnyebbség miatt hasznos ezt tulajdonságként is kezelni,
 * mivel mozognak
 */
@Getter
@Setter
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlEntity {
	/**
	 * A pályaelem szimbóluma lesz a mező értéke
	 * Azért String, mert a char-t számként írja a kimenetben
	 */
	@XmlValue
	protected String compatibilitySymbol;
	/**
	 * A pozíciója pedig egy attribútuma
	 * Az X koordinátája a pályán
	 */
	@XmlAttribute
	protected int posX;
	/**
	 * Az Y koordinátája a pályán
	 */
	@XmlAttribute
	protected int posY;

	public XmlEntity() {}

	public XmlEntity(char compatibilitySymbol, Point position) {
		this.compatibilitySymbol = Character.toString(compatibilitySymbol);
		this.posX = position.x;
		this.posY = position.y;
	}

	public Point getPosition () {
		return new Point(posX, posY);
	}

	/**
	 * Visszaad egy példányt az itt leírt pályaelemből
	 * @return a pályaelem példánya
	 */
	public Entity getEntity() {
		var entity = Entities.parseSymbol(compatibilitySymbol.charAt(0));
		if (entity != null) {
			return entity.createNewInstance();
		}
		return null;
	}
}