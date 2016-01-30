package entity;

import java.util.ArrayList;

import data.Trait;
import levels.Tile;

public abstract class Entity {

	public Tile location;
	public String name;
	public ArrayList<Trait> traits = new ArrayList<Trait>();
	
	public String pictureFile;
	
}
