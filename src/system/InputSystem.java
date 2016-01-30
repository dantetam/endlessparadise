package system;

import java.util.ArrayList;

import tests.MainGameLoop;

public class InputSystem extends BaseSystem {

	public ArrayList<Click> clicks = new ArrayList<Click>();
	public ArrayList<KeyPress> presses = new ArrayList<KeyPress>();

	public InputSystem(MainGameLoop game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		while (clicks.size() > 0)
		{
			Click click = clicks.remove(0);
			System.out.println("Click position: " + click.posX + " " + click.posY);
		}
		while (presses.size() > 0)
		{
			KeyPress press = presses.remove(0);
			System.out.println("Key " + press.key);
		}
	}

	public class Click {
		float posX, posY; int action, button; 
		public Click(float x, float y, int a, int b) {posX = x; posY = y; action = a; button = b;}
	}
	public class KeyPress {int key, action; public KeyPress(int k, int a) {key = k; action = a;}}
	
}
