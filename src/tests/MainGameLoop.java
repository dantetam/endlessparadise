package tests;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;

import entity.Player;
import levels.EntityGrid;
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

	public EntityGrid grid;
	
	public static void main(String[] args)
	{
		new MainGameLoop();
	}

	public MainGameLoop()
	{
		GLFW.glfwInit();
		
		inputSystem = new InputSystem(this);
		systems.add(inputSystem);

		DisplayManager.createDisplay(this);
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		StaticShader shader = new StaticShader();
		
		//Just do some silly stuff to make sure all the data is initialized.
		//NullPointerExceptions arise when data is declared and asked for in the incorrect order
		LevelManager lm = null;
		grid = new EntityGrid(loader,lm,100,100);
		Player player = new Player();
		player.location = grid.getTile(50,50);
		lm = new LevelManager(grid,loader,player);
		grid.lm = lm;
		grid.moveEntity(player,50,50);
		
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

}
