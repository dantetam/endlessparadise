package levels;

import java.util.ArrayList;

import entity.Building;
import entity.Entity;

public class Tile {

	public ArrayList<Entity> entities = new ArrayList<Entity>();
	public int row, col;
	public String fileName = "/res/tiles/iceTexture.png";
	
	public Tile(int r, int c)
	{
		row = r;
		col = c;
	}
	
	public boolean containsBuilding()
	{
		for (Entity en: entities)
			if (en instanceof Building)
				return true;
		return false;
	}
	
}
