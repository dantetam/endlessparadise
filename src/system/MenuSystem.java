package system;

import java.util.ArrayList;

import lwjglEngine.fontRendering.TextMaster;
import lwjglEngine.gui.Menu;
import lwjglEngine.gui.TextBox;
import tests.MainGameLoop;

public class MenuSystem extends BaseSystem {

	public ArrayList<Menu> menus = new ArrayList<Menu>();
	public ArrayList<TextBox> textboxes = new ArrayList<TextBox>();
	
	public MenuSystem(MainGameLoop game) {
		super(game);
	}

	public void tick() {
		TextMaster.render();
	}

}
