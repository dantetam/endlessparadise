package lwjglEngine.models;

import java.util.ArrayList;

import levels.ProtectedGrid;
import levels.Tile;

public class LevelManager {

	public ArrayList<TexturedModel> models = new ArrayList<TexturedModel>();
	public ProtectedGrid<Tile> grid;
	
	public LevelManager(ProtectedGrid<Tile> g)
	{
		grid = g;
	}
	
	public void addEntity()
	{
		
	}
	
	public void moveEntity()
	{
		
	}
	
	public void removeEntity()
	{
		
	}
	
}
