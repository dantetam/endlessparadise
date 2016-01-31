package system;

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
			
		}
	}

}
