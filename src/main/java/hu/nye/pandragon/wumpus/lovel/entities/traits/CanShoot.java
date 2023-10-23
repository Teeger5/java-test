package hu.nye.pandragon.wumpus.lovel.entities.traits;

public interface CanShoot {
	void onShoot ();
	int getAmmoAmount ();
	void setAmmoAmount (int amount);
}
