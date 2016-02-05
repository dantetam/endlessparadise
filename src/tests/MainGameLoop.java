package tests;

import java.util.ArrayList;

import org.lwjgl.glfw.GLFW;

import data.EntityData;
import data.Names;
import entity.Building;
import entity.Monster;
import entity.Player;
import levels.EntityGrid;
import levels.Tile;
import lwjglEngine.fontRendering.TextMaster;
import lwjglEngine.models.LevelManager;
import lwjglEngine.models.RawModel;
import lwjglEngine.models.TexturedModel;

import lwjglEngine.render.*;
import lwjglEngine.shaders.StaticShader;
import system.*;
import lwjglEngine.textures.ModelTexture;

public class MainGameLoop {

	public ArrayList<BaseSystem> systems = new ArrayList<BaseSystem>();
	public InputSystem inputSystem;
	public MenuSystem menuSystem;
	public AnimationSystem animationSystem;
	public TurnSystem turnSystem;
	public static final int rows = 14, cols = 14;

	public EntityGrid grid;

	public Loader loader;
	public LevelManager lm;
	public Player player;

	public int frame = 0;
	
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
		turnSystem = new TurnSystem(this);
		animationSystem = new AnimationSystem(this);
		menuSystem = new MenuSystem(this);

		systems.add(inputSystem);
		systems.add(turnSystem);
		//systems.add(animationSystem);
		//systems.add(menuSystem);

		DisplayManager.createDisplay(this);
		loader = new Loader();
		loader.init();
		TextMaster.init(loader);
		Renderer renderer = new Renderer();
		StaticShader shader = new StaticShader();

		//Just do some silly stuff to make sure all the data is initialized.
		//NullPointerExceptions arise when data is declared and asked for in the incorrect order
		lm = null;
		grid = new EntityGrid(loader,lm,rows,cols);
		player = new Player();
		player.location = grid.getTile(rows/2,cols/2);
		lm = new LevelManager(grid,loader,player);
		grid.lm = lm;
		grid.moveEntity(player,rows/2,cols/2);

		initWorld();

		//Keep updating the display until the user exits
		while (!DisplayManager.requestClose())
		{
			for (int i = 0; i < systems.size(); i++)
				systems.get(i).tick();
			renderer.prepare();
			shader.start(); //Enable shader
			renderer.render(lm);
			TextMaster.render();
			//renderer.render(texturedModel);
			shader.stop(); //Disable shader when the draw is done
			DisplayManager.updateDisplay();
			//if (frame == 200) System.out.println("LevelManager: " + lm.models.entrySet().size());
		}

		//Clean up data
		shader.cleanUp();
		loader.cleanData();
		DisplayManager.closeDisplay();
	}

	public void initWorld()
	{
		boolean[][] buildings = makeBoxedDungeons(rows,cols);
		for (int r = 0; r < buildings.length; r++)
		{
			for (int c = 0; c < buildings[0].length; c++)
			{
				if (buildings[r][c])
				{
					Building building = new Building();
					building.pictureFile = loader.getRandomTileName();
					grid.moveEntity(building,r,c);
				}
				/*if (buildings[r][c])
					System.out.print("X");
				else
					System.out.print(" ");*/
			}
			//System.out.println();
		}
		for (int i = 0; i < rows*cols/6; i++)
		{
			Monster monster = new Monster();
			//monster.pictureFile = "bluePlasma";
			monster.pictureFile = loader.getRandomMonsterName();
			Tile spawn = null;
			do
			{
				spawn = grid.getTile((int)(Math.random()*grid.rows),(int)(Math.random()*grid.cols));
			} while (spawn.containsBuilding());

			grid.moveEntity(monster,spawn.row,spawn.col);
		}
	}

	public boolean[][] makeBoxedDungeons(int rows, int cols)
	{
		boolean[][] data = fillAll(rows,cols);
		for (int i = 0; i < (int)(rows/7); i++)
		{
			clearRandomPatches(data,1,rows/15 + (int)(Math.random()*(rows/5)-rows/10),rows/15 + (int)(Math.random()*(rows/5)-rows/10));
		}
		for (int i = 0; i < 10; i++)
		{
			//clearPatch(data,0,0,(int)(Math.random()*rows),cols);
		}
		//data = clearRandomPatches(data,1,rows/15 + (int)(Math.random()*(rows/5)-rows/10),rows/15 + (int)(Math.random()*(rows/5)-rows/10));
		data[rows/2][cols/2] = false;
		return data;
	}

	public void clearRandomPatches(boolean[][] data, int times, int w, int h) {
		//HashMap<Integer,Integer> usedRows = new HashMap<Integer,Integer>();\
		int r = 0, c = 0;
		for (int i = 0; i < times; i++)
		{
			r = (int)(Math.random()*rows);
			c = (int)(Math.random()*cols);
			clearPatch(data,r,c,r+w,c+h);
		}
	}

	public boolean[][] fillAll(int rows, int cols)
	{
		boolean[][] temp = new boolean[rows][cols];
		for (int r = 0; r < rows; r++)
			for (int c = 0; c < cols; c++)
				temp[r][c] = false;
		return temp;
	}

	public void clearPatch(boolean[][] data, int r1, int c1, int r2, int c2)
	{
		for (int r = 0; r < data.length; r++)
		{
			for (int c = 0; c < data[0].length; c++)
			{
				if (r >= r1 && r <= r2 && c >= c1 && c <= c2)
					data[r][c] = true;
			}
		}
	}

}
