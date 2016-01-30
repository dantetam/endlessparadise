package entity;

import levels.Tile;

public class Player extends CombatEntity {

	public static int sightXHalf = 1, sightYHalf = 1;

	public Player()
	{
		location = new Tile(50,50);
	}
	
}
