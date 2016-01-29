package system;

import tests.MainGameLoop;

public abstract class BaseSystem {

	protected MainGameLoop main;
	
	public BaseSystem(MainGameLoop game)
	{
		main = game;
	}
	
	public abstract void tick();
	
}