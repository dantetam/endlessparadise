package levels;

import entity.Entity;
import lwjglEngine.models.LevelManager;
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
		Tile t = super.getTile(row, col);
		if (en.location != null)
		{
			en.location.entities.remove(en);
			en.location = null;
			lm.addEntity(en, row, col);
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

}
