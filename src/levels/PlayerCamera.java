package levels;

public class PlayerCamera {

	public Tile location;
	public int sightXHalf, sightYHalf;
	
	public PlayerCamera(Tile t, int x, int y)
	{
		location = t; 
		sightXHalf = x; sightYHalf = y;
	}
	
}
