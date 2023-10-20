package hu.nye.pandragon.wumpus.lovel.entities;

import hu.nye.pandragon.wumpus.lovel.Entity;

public class Empty extends Entity {

	private Entity containedEntity;

	public Empty() {
		super(false, "Út", ' ', false);
	}

	public Entity getContainedEntity() {
		return containedEntity;
	}

	public void setContainedEntity(Entity containedEntity) {
		this.containedEntity = containedEntity;
	}
}
