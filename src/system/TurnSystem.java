package system;

import java.util.Map.Entry;

import entity.Entity;
import entity.MoveAnimation;
import entity.Player;
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
					if (!en.previousLocation.equals(en.location) && !(en instanceof Player))
					{
						TexturedModel model = main.lm.models.get(en);
						MoveAnimation anim = new MoveAnimation(entry.getValue(),main.lm,-20,20);
						int[] diff = setMoveAnimationInDirection(anim,en.location,en.previousLocation);
						anim.data(anim.data.get(0),anim.data.get(1),model.x - model.w*diff[0],model.y - model.h*diff[1]);
						//for (double d: anim.data)
							//System.out.print(" " + d);
						//System.out.println();
						model.animations.add(anim);
					}
				}
				//en.previousLocation = en.location;
			}
		}
	}
	
	public static int[] setMoveAnimationInDirection(MoveAnimation anim, Tile a, Tile b)
	{
		int dr = a.row - b.row, dc = a.col - b.col;
		if (dr == 0 && dc == 1)
			anim.down();
		else if (dr == 0 && dc == -1)
			anim.up();
		else if (dr == 1 && dc == 0)
			anim.right();
		else if (dr == -1 && dc == 0)
			anim.left();
		return new int[]{dr,dc};
	}

}
