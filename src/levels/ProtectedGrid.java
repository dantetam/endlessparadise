package levels;

public class ProtectedGrid<T> {

	protected T[][] tiles;
	public int rows, cols;
	
	public ProtectedGrid(int r, int c)
	{
		rows = r;
		cols = c;
	}
	
	public T getTile(int r, int c)
	{
		if (r < 0 || c < 0 || r >= tiles.length || c >= tiles[0].length)
			return null;
		return tiles[r][c];
	}
	
}
