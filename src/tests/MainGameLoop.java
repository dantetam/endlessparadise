package tests;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;

import entity.Player;
import levels.EntityGrid;
import levels.PlayerCamera;
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
	
	public LevelManager lm;
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
		
		grid = new EntityGrid(loader,null,100,100);
		Player player = new Player();
		grid.addEntity(player,50,50);
		lm = new LevelManager(grid,loader,player);
		grid.lm = lm;
		
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
