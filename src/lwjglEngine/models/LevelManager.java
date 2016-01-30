package lwjglEngine.models;

import java.util.ArrayList;
import java.util.HashMap;

import entity.Entity;
import entity.Player;
import levels.ProtectedGrid;
import levels.Tile;
import lwjglEngine.render.DisplayManager;
import lwjglEngine.render.Loader;
import lwjglEngine.textures.ModelTexture;

public class LevelManager {

	public HashMap<Entity,TexturedModel> models = new HashMap<Entity,TexturedModel>();
	public ProtectedGrid<Tile,Entity> grid;
	public Loader loader;
	public Player camera;

	public LevelManager(ProtectedGrid<Tile,Entity> g, Loader l, Player c)
	{
		grid = g;
		loader = l;
		camera = c;
	}

	public void addEntity(Entity en, int r, int c)
	{
		TexturedModel model = models.put(en, generateTexture("bluePlasma"));
		adjustTexture(model, camera, r, c);
	}

	public void moveEntity(Entity en, int r, int c)
	{
		TexturedModel model = models.get(en);
		adjustTexture(model, camera, r, c);
	}

	public void removeEntity(Entity en)
	{
		TexturedModel removing = models.remove(en);
		removing.active = false;
	}

	public TexturedModel generateTexture(String textureName)
	{
		//counter clockwise vertices
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
		ModelTexture texture = new ModelTexture(loader.loadTexture(textureName));
		TexturedModel texturedModel = new TexturedModel(model, texture);
		return texturedModel;
	}

	public void adjustTexture(TexturedModel model, Player camera, int r, int c)
	{
		int minX = camera.location.row - camera.sightXHalf, maxX = camera.location.row + camera.sightXHalf;
		int minY = camera.location.col - camera.sightYHalf, maxY = camera.location.col + camera.sightYHalf;
		if (r >= minX && r <= maxX && c >= minY && c <= maxY)
		{
			model.active = true;
			float width = 1f/(float)(1 + 2*camera.sightXHalf);
			float height = 1f/(float)(1 + 2*camera.sightYHalf);
			generateVertices(model, (float)((r-minX)*width), (float)((c-minY)*height), width, height);
		}
		else
		{
			model.active = false;
		}
	}

	public void generateVertices(TexturedModel texturedModel, float x, float y, float width, float height)
	{
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
		texturedModel.rawModel = model;
		//return texturedModel;
	}

}
