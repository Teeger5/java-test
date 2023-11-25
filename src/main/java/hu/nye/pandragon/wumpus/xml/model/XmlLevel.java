package hu.nye.pandragon.wumpus.xml.model;

import hu.nye.pandragon.wumpus.model.LevelVO;
import hu.nye.pandragon.wumpus.model.entities.Entity;
import hu.nye.pandragon.wumpus.model.entities.Hero;
import hu.nye.pandragon.wumpus.model.entities.LivingEntity;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@XmlRootElement(name = "Level")
public class XmlLevel {
	@XmlAttribute
	private int size;
	@XmlElement(name = "Hero")
	private XmlHero xmlHero;
	@XmlElementWrapper(name = "Entities")
	@XmlElement(name = "Entity")
	private List<XmlEntity> entities;

	public XmlLevel() {}

	public XmlLevel(int size, List<XmlEntity> entities, Hero hero) {
		this.size = size;
		this.entities = entities;
		this.xmlHero = new XmlHero(hero);
	}

	public XmlLevel(LevelVO levelVO) {
		this.size = levelVO.getSize();
		this.entities = new ArrayList<>();
		this.entities.addAll(levelVO.getStaticEntities().entrySet().stream()
				.map(entry -> new XmlEntity(entry.getValue().getCompatibilitySymbol(), entry.getKey()))
				.collect(Collectors.toList()));
		this.entities.addAll(levelVO.getLivingEntities().entrySet().stream()
				.map(entry -> new XmlEntity(entry.getValue().getCompatibilitySymbol(), entry.getKey()))
				.collect(Collectors.toList()));
	}

	public LevelVO toLevelVO () {
		var staticEntities = new HashMap<Point, Entity>();
		var livingEntites = new HashMap<Point, LivingEntity>();
		var entities = this.entities.stream()
				.collect(Collectors.toMap(XmlEntity::getPosition, XmlEntity::getEntity));
		for (Map.Entry<Point, Entity> entry : entities.entrySet()) {
			var entity = entry.getValue();
			if (entity instanceof LivingEntity livingEntity) {
				if (livingEntity instanceof Hero) {
					entity = xmlHero.getEntity(entry.getKey());
				}
				livingEntites.put(entry.getKey(), livingEntity);
			}
			else {
				staticEntities.put(entry.getKey(), entity);
			}
		}
		return new LevelVO(staticEntities, livingEntites, size);
	}
}
