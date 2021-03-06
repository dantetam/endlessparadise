package lwjglEngine.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.lwjgl.util.vector.Vector2f;

import entity.Building;
import entity.Entity;
import entity.Monster;
import entity.Player;
import levels.ProtectedGrid;
import levels.Tile;
import lwjglEngine.gui.GuiTexture;
import lwjglEngine.render.DiamondSquare;
import lwjglEngine.render.DisplayManager;
import lwjglEngine.render.Loader;
import lwjglEngine.textures.ModelTexture;
import tests.MainGameLoop;

public class LevelManager {

	public HashMap<Entity,GuiTexture> models = new HashMap<Entity,GuiTexture>();
	public HashMap<Tile,GuiTexture> tiles = new HashMap<Tile,GuiTexture>();
	public ProtectedGrid<Tile,Entity> grid;
	public Loader loader;
	public Player camera;

	public LevelManager(ProtectedGrid<Tile,Entity> g, Loader l, Player cam)
	{
		grid = g;
		loader = l;
		camera = cam;

		int width = 64;
		double[][] temp = DiamondSquare.makeTable(50,50,50,50,width+1);
		DiamondSquare ds = new DiamondSquare(temp);
		ds.seed(870691);
		//ds.diamond(0, 0, 4);
		ds.dS(0, 0, width, 100, 0.5, false, true);
		//ds.printTable(ds.t);
		
		for (int r = 0; r < grid.rows; r++)
		{
			for (int c = 0; c < grid.cols; c++)
			{
				Tile t = grid.getTile(r,c);
				double numTiles = loader.tileNames.size();
				GuiTexture model = generateTexture("./res/tiles/"+loader.tileNames.get(Math.min((int)(numTiles-1),(int)(Math.min(1,ds.t[r][c]/300D)*numTiles)))+".png");
				tiles.put(t, model);
				adjustTexture(model, r, c);
			}
		}
	}

	public void update()
	{		
		for (Entry<Tile, GuiTexture> entry: tiles.entrySet())
		{
			Tile t = entry.getKey();
			GuiTexture model = entry.getValue();
			adjustTexture(model, t.row, t.col);
		}
		for (Entry<Entity, GuiTexture> entry: models.entrySet())
		{
			GuiTexture model = entry.getValue();
			if (model.animations.size() > 0) continue;
			Entity en = entry.getKey();
			adjustTexture(model, en.location.row, en.location.col);
		}
	}

	public void addEntity(Entity en, String fileName, int r, int c)
	{
		if (fileName == null)
			fileName = loader.getRandomMonsterName();
		en.name = fileName;
		//System.out.println(fileName);
		GuiTexture model = null;
		if (en instanceof Monster)
			model = generateTexture("res/monsters/"+fileName+".png");
		else if (en instanceof Building)
			model = generateTexture("res/tiles/"+fileName+".png");
		else
			model = generateTexture("res/monsters/"+fileName+".png");
		//System.out.println("model -> " + model);
		models.put(en, model);
		//System.out.println(model.getTexture().textureID);
		adjustTexture(model, r, c);
	}

	public void moveEntity(Entity en, int r, int c)
	{
		GuiTexture model = models.get(en);
		if (model == null) 
		{
			addEntity(en,en.pictureFile,r,c);
			//System.out.println(en + " <- model not defined");
			return;
		}
		adjustTexture(model, r, c);
	}

	public void removeEntity(Entity en)
	{
		GuiTexture removing = models.remove(en);
		removing.active = false;
	}

	public GuiTexture generateTexture(String textureName)
	{
		/*//counter clockwise vertices
		float[] vertices = {
				//Left bottom and top right, resp.
				-0.5f, 0.5f, 0f,	
				-0.5f, -0.5f, 0f,
				0.5f, -0.5f, 0f,
				0.5f, 0.5f, 0f
		};

		//order in which to transverse the vertices
		int[] indices = {0,1,3,3,1,2};

		//respective u,v vertex of texture to map to
		float[] textureCoords = {0,0,0,1,1,1,1,0};

		RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
		ModelTexture texture;
		if (textureName.contains("/tile"))
			texture = new ModelTexture(loader.loadTexture(textureName));
		//texture = new ModelTexture(loader.loadTexture(textureName,0,0,12,12));
		else
			texture = new ModelTexture(loader.loadTexture(textureName));
		TexturedModel texturedModel = new TexturedModel(model, texture);*/
		GuiTexture guiTexture = new GuiTexture(loader.loadTexture(textureName),null,null);
		return guiTexture;
	}

	public void adjustTexture(GuiTexture model, int r, int c)
	{
		//System.out.println(camera.location);
		Tile t = camera.previousLocation == null ? camera.location : camera.previousLocation;
		if (t == null) 
		{
			t = grid.getTile(MainGameLoop.rows/2, MainGameLoop.cols/2);
			camera.location = t;
		}

		int minX = t.row - camera.sightXHalf, maxX = t.row + camera.sightXHalf;
		int minY = t.col - camera.sightYHalf, maxY = t.col + camera.sightYHalf;
		if (r >= minX && r <= maxX && c >= minY && c <= maxY)
		{
			model.active = true;
			float width = 1f/(float)(1 + 2*camera.sightXHalf);
			float height = 1f/(float)(1 + 2*camera.sightYHalf);
			
			//This is causing the memory leak. I am creating too many VAOs. Use shaders to move GUIs. Look in orig 2d early tutorials or GUI shaders?
			model.adjustByProportion(new Vector2f((float)((r-minX)*width), (float)((c-minY)*height)), new Vector2f(width, height));
		}
		else 
		{
			model.active = false;
		}
	}

	public void adjustTextureManual(GuiTexture model, float x, float y, float width, float height)
	{
		//x = x*2 - 1; y = y*2 - 1;
		//width *= 2; height *= 2;
		if (x >= -1 && y >= -1 && x <= 1 && y <= 1)
		{
			model.active = true;
			model.adjustByProportion(new Vector2f(x, y), new Vector2f(width, height));
		}
		else
		{
			model.active = false;
		}
	}

	/*public void generateVertices(GuiTexture texturedModel, float x, float y, float width, float height)
	{
		//texturedModel.set(x,y,width,height);

		x = x*2 - 1; y = y*2 - 1;
		width *= 2; height *= 2;
		//counter clockwise vertices
		float[] vertices = {
				//Left top* and top right, resp.
				x, y, 0f,	
				x, y+height, 0f,
				x+width, y+height, 0f,
				x+width, y, 0f
		};
		//order in which to transverse the vertices
		int[] indices = {0,1,3,3,1,2};
		//respective u,v vertex of texture to map to
		float[] textureCoords = {0,0,0,1,1,1,1,0};

		RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
		//RawModel model = loader.loadToVAO(vertices);
		//texturedModel.rawModel = model;
		//return texturedModel;
	}*/

}
