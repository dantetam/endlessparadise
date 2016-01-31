package system;

import java.util.Map.Entry;

import entity.Entity;
import lwjglEngine.models.TexturedModel;
import tests.MainGameLoop;

public class TurnSystem extends BaseSystem {

	public int turn = 0;
	public boolean requestUpdate = false;
	
	public TurnSystem(MainGameLoop game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		if (requestUpdate)
		{
			requestUpdate = false;
			turn++;
			for (Entity en: main.lm.models.keySet())
			{
				en.previousLocation = en.location;
			}
		}
	}

}
