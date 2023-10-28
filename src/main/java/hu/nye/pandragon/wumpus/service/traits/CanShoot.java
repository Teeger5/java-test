package hu.nye.pandragon.wumpus.service.traits;

public interface CanShoot {
	void onShoot ();
	int getAmmoAmount ();
	void setAmmoAmount (int amount);
}
