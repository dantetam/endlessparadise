package system;

import java.util.Map.Entry;

import entity.Entity;
import entity.MoveAnimation;
import levels.Tile;
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
			for (Entry<Entity,TexturedModel> entry: main.lm.models.entrySet())
			{
				Entity en = entry.getKey();
				main.grid.attemptRandomMove(en);
				if (en.previousLocation != null && en.location != null)
				{
					if (!en.previousLocation.equals(en.location))
					{
						MoveAnimation anim = new MoveAnimation(entry.getValue(),main.lm);
						setMoveAnimationInDirection(anim,en.location,en.previousLocation);
						main.lm.models.get(en).animations.add(anim);
					}
				}
				en.previousLocation = en.location;
			}
		}
	}
	
	public void setMoveAnimationInDirection(MoveAnimation anim, Tile a, Tile b)
	{
		int dr = a.row - b.row, dc = a.col - b.col;
		if (dr == 0 && dc == 1)
			anim.left();
		else if (dr == 0 && dc == -1)
			anim.right();
		else if (dr == 1 && dc == 0)
			anim.down();
		else if (dr == -1 && dc == 0)
			anim.up();
	}

}
