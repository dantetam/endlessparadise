package tests;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;

import data.EntityData;
import data.Names;
import entity.Monster;
import entity.Player;
import levels.EntityGrid;
import lwjglEngine.fontRendering.TextMaster;
import lwjglEngine.models.LevelManager;
import lwjglEngine.models.RawModel;
import lwjglEngine.models.TexturedModel;

import lwjglEngine.render.*;
import lwjglEngine.shaders.StaticShader;
import system.BaseSystem;
import system.InputSystem;
import lwjglEngine.textures.ModelTexture;

public class MainGameLoop {

	public ArrayList<BaseSystem> systems = new ArrayList<BaseSystem>();
	public InputSystem inputSystem;
	public static final int rows = 100, cols = 100;
	
	public EntityGrid grid;
	
	public Loader loader;
	
	public static void main(String[] args)
	{
		new MainGameLoop();
	}

	public MainGameLoop()
	{
		GLFW.glfwInit();
		EntityData.init();
		Names.init();
		
		inputSystem = new InputSystem(this);
		systems.add(inputSystem);

		DisplayManager.createDisplay(this);
		loader = new Loader();
		loader.init();
		TextMaster.init(loader);
		Renderer renderer = new Renderer();
		StaticShader shader = new StaticShader();
		
		//Just do some silly stuff to make sure all the data is initialized.
		//NullPointerExceptions arise when data is declared and asked for in the incorrect order
		LevelManager lm = null;
		grid = new EntityGrid(loader,lm,rows,cols);
		Player player = new Player();
		player.location = grid.getTile(rows/2,cols/2);
		lm = new LevelManager(grid,loader,player);
		grid.lm = lm;
		grid.moveEntity(player,rows/2,cols/2);
		
		initWorld();
		
		//Keep updating the display until the user exits
		while (!DisplayManager.requestClose())
		{
			renderer.prepare();
			shader.start(); //Enable shader
			renderer.render(lm);
			//renderer.render(texturedModel);
			shader.stop(); //Disable shader when the draw is done
			for (int i = 0; i < systems.size(); i++)
				systems.get(i).tick();
			DisplayManager.updateDisplay();
		}

		//Clean up data
		shader.cleanUp();
		loader.cleanData();
		DisplayManager.closeDisplay();
	}
	
	public void initWorld()
	{
		for (int i = 0; i < rows*cols/6; i++)
		{
			Monster monster = new Monster();
			//monster.pictureFile = "bluePlasma";
			monster.pictureFile = loader.getRandomMonsterName();
			grid.moveEntity(monster,(int)(Math.random()*grid.rows),(int)(Math.random()*grid.cols));
		}
	}

}
