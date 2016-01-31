package system;

import java.util.ArrayList;

import lwjglEngine.fontRendering.TextMaster;
import lwjglEngine.gui.Menu;
import lwjglEngine.gui.TextBox;
import system.InputSystem.Click;
import system.InputSystem.KeyPress;
import tests.MainGameLoop;

public class MenuSystem extends BaseSystem {

	public ArrayList<Menu> menus = new ArrayList<Menu>();
	public ArrayList<TextBox> textboxes = new ArrayList<TextBox>();
	
	public MenuSystem(MainGameLoop game) {
		super(game);
		
		Menu menu0 = new Menu("Inventory");
		
	}

	public void tick() {
		//TextMaster.render();
	}
	
	public ArrayList<Click> clicks = new ArrayList<Click>();
	public ArrayList<KeyPress> presses = new ArrayList<KeyPress>();

}
