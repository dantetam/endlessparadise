package entity;

import levels.Tile;

public class Player extends CombatEntity {

	public static int sightXHalf = 8, sightYHalf = 4;

	public Player()
	{
		location = new Tile(50,50);
		pictureFile = "AngelRed";
	}
	
}
