package levels;

import entity.CombatAnimation;
import entity.CombatEntity;
import entity.Entity;
import entity.Monster;
import entity.Player;
import lwjglEngine.models.LevelManager;
import lwjglEngine.models.TexturedModel;
import lwjglEngine.render.Loader;

public class EntityGrid extends ProtectedGrid<Tile,Entity> {

	public LevelManager lm;

	public EntityGrid(Loader loader, LevelManager lm, int rows, int cols) {
		super(rows, cols);
		this.lm = lm;
		//lm = new LevelManager(this, loader,);
		// TODO Auto-generated constructor stub
		tiles = new Tile[rows][cols];
		for (int r = 0; r < rows; r++)
		{
			for (int c = 0; c < cols; c++)
			{
				tiles[r][c] = new Tile(r,c);
			}
		}
	}

	public void moveEntity(Entity en, int row, int col)
	{
		en.previousLocation = en.location;
		Tile t = super.getTile(row, col);
		if (en.location != null)
		{
			en.location.entities.remove(en);
			en.location = null;
			lm.addEntity(en, en.pictureFile, row, col);
		}
		else
		{
			lm.moveEntity(en, row, col);
		}
		en.location = t;
		t.entities.add(en);
	}

	public void removeEntity(Entity en)
	{
		en.location.entities.remove(en);
		en.location = null;
		lm.removeEntity(en);
	}

	public void attackOrMove(Entity en, Tile t)
	{
		if (t.entities.isEmpty())
			this.moveEntity(en, t.row, t.col);
		else
		{
			for (Entity e: t.entities)
			{
				if (en instanceof Player && e instanceof Monster)
				{
					attack((CombatEntity)en, (CombatEntity)e);
				}
				else if (en instanceof Monster && e instanceof Player)
				{
					attack((CombatEntity)en, (CombatEntity)e);
				}
				else if (en instanceof Monster && e instanceof Monster)
				{
					if (Math.random() < 0.075)
					{
						attack((CombatEntity)en,(CombatEntity)e);
					}
				}
				else
				{
					//No movement.
				}
			}
		}
		
	}
	
	public void attack(CombatEntity atk, CombatEntity def)
	{
		TexturedModel atkModel = lm.models.get(atk), defModel = lm.models.get(def);
		if (atkModel.animations.isEmpty())
			atkModel.animations.add(new CombatAnimation(atkModel,lm,0,20).random());
		if (defModel.animations.isEmpty())
			defModel.animations.add(new CombatAnimation(defModel,lm,0,20).random());
		if (atk.hp == 0 || def.hp == 0) return;
	}
	
	public void attemptRandomMove(Entity en)
	{
		if (en instanceof Player) return;
		Tile t;
		do
		{
			double r = Math.random();
			if (r < 0.25)
				t = getTile(en.location.row - 1, en.location.col);
			else if (r < 0.5)
				t = getTile(en.location.row + 1, en.location.col);
			else if (r < 0.75)
				t = getTile(en.location.row, en.location.col - 1);
			else
				t = getTile(en.location.row, en.location.col + 1);
		} while (t == null);
		attackOrMove(en, t);
	}

}
