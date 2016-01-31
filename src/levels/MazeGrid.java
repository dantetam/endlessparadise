/*package levels;

import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Rectangle;

import info.iacna.run.Runner;
import info.iacna.tiles.*;
import info.iacna.actors.*;
import info.iacna.data.*;

public class MazeGrid extends Grid {

	private ArrayList<Node> nodes;

	private boolean waitMode;
	private int generationMode = 2;
	public int difficulty;

	//Mazes must be square and have an odd number of rows and cols
	public MazeGrid(World world, int rows, int cols, int difficulty, boolean generate) {
		super(world, rows, cols);
		nodes = new ArrayList<Node>();
		tiles = new Tile[rows+1][cols+1];

		if (generate)
		{
			if (generationMode == 0)
			{
				generateCells();

				carvePath(nodes.get((int) (Math.random()*nodes.size())));
				Node spawn = clearRandomPatches(true,rows/8,rows/15 + (int)(Math.random()*(rows/5)-rows/10),rows/15 + (int)(Math.random()*(rows/5)-rows/10));
				setSpawn(spawn.x, spawn.y);
				//fillInWalls();
				//clearRandomPatches(true,5,1,10);

				clearRandomPatches(true,rows/5 + (int)(Math.random()*(rows/5)-rows/10),0,(int)(Math.random()*10));
				clearRandomPatches(true,rows/5 + (int)(Math.random()*(rows/5)-rows/10),0,(int)(Math.random()*10));
				//fillPatch(1,10,6,16);
				fillRandomPatches(true,rows/5,rows/10 + (int)(Math.random()*(rows/5)-rows/10),rows/10 + (int)(Math.random()*(rows/5)-rows/10));

				cleanStandaloneBlocks();

				int r,c;
				for (int i = 0; i < rows/10; i++)
				{
					r = (int)(Math.random()*rows);
					c = (int)(Math.random()*cols);
					clearPatch(r,0,r,cols);
					clearPatch(0,c,rows,c);
				}


				//placeRandomHealingTile(15);
				//clearSmallRooms();

				Merchant temp = new Merchant(this);
			temp.moveTo(5, 5);
			temp.addStoreItem(world.getRunner().getCopyOfName("Blue Potion"),5);

				nodes.clear();
			}
			else if (generationMode == 1)
			{
				fillAll();
				makeWalledRooms((int) (Math.random()*5)+7,false);
				Node spawn = clearRandomPatches(true,rows/8,rows/15 + (int)(Math.random()*(rows/5)-rows/10),rows/15 + (int)(Math.random()*(rows/5)-rows/10));
				//setSpawn(spawn.x, spawn.y);
				nodes.clear();
			}
			else if (generationMode == 2)
			{
				fillAll();
				Bound temp = new Bound(0,0,rows,cols,null);
				temp.recursiveSplit(temp, 10, 7, Math.random() < 0.5);
				materalizeAllBounds(temp);
				int r,c;
				for (int i = 0; i < rows/10; i++)
				{
					r = (int)(Math.random()*rows);
					c = (int)(Math.random()*cols);
					clearPatch(r,0,r,cols);
					clearPatch(0,c,rows,c);
				}
				Node spawn = clearRandomPatches(true,rows/8,rows/15 + (int)(Math.random()*(rows/5)-rows/10),rows/15 + (int)(Math.random()*(rows/5)-rows/10));
				setSpawn(spawn.x, spawn.y);
				//joinAllBounds(temp);
				//return;
			}
			else if (generationMode == 3)
			{
				//loadLevel(Data.level0);
			}
		}
		placeTiles();

		Location location;
		for (int i = 0; i < 20; i++)
		{
			location = findSpot();
			fillPatch(location.r, location.c,location.r+(int)(Math.random()*rows/10),location.c+(int)(Math.random()*rows/10),"Water");
		}

		placeTilesOf("SpikeTile",rows/4);
		placeTilesOf("TrapTile",rows/4);
		placeTilesOf("ChestTile",rows/8);
		placeTilesOf("SlimeTile",rows/2);

		placeDoors(rows*cols/100);

		for (int i = 0; i < rows/3; i++)
		{
			new Boulder(this).moveTo(findSpot());
		}

		Location pl = findSpot();
		clearPatch(pl.r,0,pl.r,cols);
		clearPatch(0,pl.c,rows,pl.c);
		new Portal(this,Runner.difficulty).moveTo(pl);

		fillInWalls();
		difficulty = (int)(Math.random()*3)+1;
		for (int i = 0; i < 5; i++)
		{
			if (Math.random() < 1/Runner.difficulty)
			{
				Merchant temp = new Merchant(this);
				temp.moveTo(findSpot());
				for (int j = 0; j < (int)(Math.random()*5); j++)
				{
					Item tempItem = world.getRunner().getRandomCopy();
					temp.addStoreItem(tempItem, tempItem.evaluateCost());
				}
			}
		}
		for (int i = 0; i < (int)(Math.random()*3)+4; i++)
		{
			new Altar(this).moveTo(findSpot());
		}
		makeWalledRooms(7,true);
		//System.out.println(difficulty);
		int theDifficulty = difficulty == 1 ? difficulty : difficulty - 1;
		spawnRandomEnemies(rows*(theDifficulty));

	}

	protected void findVisibleTiles(int dist, int r, int c) {
		if (!isValid(new Location(r,c))) return;
		if (visibleTiles[r][c]) return;
		if (dist < 0) return;
		visibleTiles[r][c] = true;
		if (findActor(r,c) instanceof Obstacle && !(findActor(r,c) instanceof Water)) return;
		//if (findActor(r,c) instanceof Water) dist++;
		if (findActor(r,c) instanceof Door)
		{
			if (!((Door)findActor(r,c)).isOpen())
				return;
		}
		findVisibleTiles(dist-1,r+1,c+1);
		findVisibleTiles(dist-1,r+1,c);
		findVisibleTiles(dist-1,r+1,c-1);
		findVisibleTiles(dist-1,r,c-1);
		findVisibleTiles(dist-1,r-1,c-1);
		findVisibleTiles(dist-1,r-1,c);
		findVisibleTiles(dist-1,r-1,c+1);
		findVisibleTiles(dist-1,r,c+1);
	}

	public void joinAllBounds(Bound bound)
	{
		if (bound.bound1 != null && bound.bound2 != null)
		{
			joinAllBounds(bound.bound1);
			joinAllBounds(bound.bound2);
		}
		recursiveJoinBounds(bound);
	}

	//Find sister bound and join up. If no sister, end.
	public void recursiveJoinBounds(Bound bound)
	{
		if (bound.parentBound == null) return;
		Bound s = bound.parentBound.bound1.equals(bound) ? bound.parentBound.bound2 : bound.parentBound.bound1;
		boolean notJoined = false;
		boolean north = false;
		boolean west = false; 
		boolean south = false; 
		boolean east = false;

		int a = 2;
		do
		{
			double mode = Math.random(); 
			//sister is _____ of bound
			//north,west,south,east respectively
			if (mode < 0.25 && new Rectangle(s.x,s.y,s.width,s.height+rows).intersects(bound.getRectangle())) 
			{
				clearPatch(new Rectangle(s.x,s.y,s.width+a,s.height+a));
				break;
			}
			else if (mode < 0.5 && new Rectangle(s.x,s.y,s.width+cols,s.height).intersects(bound.getRectangle()))
			{
				clearPatch(new Rectangle(s.x,s.y,s.width+a,s.height+a));
				break;
			}
			else if (mode < 0.75 && new Rectangle(bound.x,bound.y,bound.width,bound.height+rows).intersects(s.getRectangle())) 
			{
				clearPatch(new Rectangle(bound.x,bound.y,bound.width+a,bound.height+a));
				break;
			}
			else if (new Rectangle(bound.x,bound.y,bound.width+cols,bound.height).intersects(s.getRectangle()))
			{
				clearPatch(new Rectangle(bound.x,bound.y,bound.width+a,bound.height+a));
				break;
			}
			if (mode < 0.25) north = true;
			else if (mode < 0.5) west = true;
			else if (mode < 0.75) south = true;
			else east = true;
			if (north && west && south && east)
			{
				notJoined = true;
				break;
			}
		} while (true);
		recursiveJoinBounds(bound.parentBound);
	}

	public void materalizeAllBounds(Bound bound)
	{
		if (bound.bound1 == null && bound.bound2 == null)
		{
			//TODO: Removing the +1 and -1 adjusts results
			if (Math.random() < 0.25)
				clearPatch(bound.x,bound.y,bound.x+bound.width-1,bound.y+bound.height-1);
			else
				clearPatch(bound.x+1,bound.y+1,bound.x+bound.width-1,bound.y+bound.height-1);
			//System.out.println((bound.x) + "," + (bound.y) + "," + (bound.width) + "," + (bound.height));
		}
		else
		{
			materalizeAllBounds(bound.bound1);
			materalizeAllBounds(bound.bound2);
		}
	}

	public class Node
	{
		public int x,y;
		public boolean visited;
		private ArrayList<Node> container;

		public Node(int x, int y, ArrayList<Node> container)
		{
			this.x = x;
			this.y = y;
			this.container = container;
			container.add(this);
			visited = false;
		}

		public Node(int r, int c) {
			x = c;
			y = r;
			visited = false;
			container = null;
		}

		public boolean hasBeenVisited()
		{
			return visited;
		}

		public int checkPathTo(Node node)
		{
			if (Math.abs(node.x - x) != 2 && Math.abs(node.y - y) != 2) return -1;
			int cX = (node.x + x)/2;
			int cY = (node.y + y)/2;
			if (findActor(cX,cY) == null) return 1;
			return 0;
		}

		public void connectNode(Node node)
		{
			int cX = (node.x + x)/2;
			int cY = (node.y + y)/2;
			if (findActor(cX,cY) != null) findActor(cX,cY).removeSelfFromWorld();
			//System.out.println("Connected nodes " + "(" + x + "," + y + "),(" + node.x + "," + node.y + ")");
		}

		public ArrayList<Node> findNewNeighboringNodes()
		{
			ArrayList<Node> temp = new ArrayList<Node>();
			//temp.add(findNodeInContainer(x-2,y-2));
			temp.add(findNodeInContainer(x-2,y));
			//temp.add(findNodeInContainer(x-2,y+2));
			temp.add(findNodeInContainer(x,y+2));
			//temp.add(findNodeInContainer(x+2,y+2));
			temp.add(findNodeInContainer(x+2,y));
			//temp.add(findNodeInContainer(x+2,y-2));
			temp.add(findNodeInContainer(x,y-2));
			for (int i = temp.size() - 1; i >= 0; i--)
			{
				if (temp.get(i) == null) temp.remove(i);
				else if (temp.get(i).hasBeenVisited()) temp.remove(i);
			}
			return temp;
		}

		public ArrayList<Actor> findImmediateNeighbors()
		{
			ArrayList<Actor> temp = new ArrayList<Actor>();
			//temp.add(findActor(x-1,y-1));
			temp.add(findActor(x-1,y));
			//temp.add(findActor(x-1,y+1));
			temp.add(findActor(x,y+1));
			//temp.add(findActor(x+1,y+1));
			temp.add(findActor(x+1,y));
			//temp.add(findActor(x+1,y-1));
			temp.add(findActor(x,y-1));
			for (int i = temp.size() - 1; i >= 0; i--)
			{
				if (temp.get(i) == null) temp.remove(i);
			}
			return temp;
		}

		public Node findFirstVisitedNeighboringNode()
		{
			ArrayList<Node> temp = new ArrayList<Node>();
			//temp.add(findNodeInContainer(x-2,y-2));
			temp.add(findNodeInContainer(x-2,y));
			//temp.add(findNodeInContainer(x-2,y+2));
			temp.add(findNodeInContainer(x,y+2));
			//temp.add(findNodeInContainer(x+2,y+2));
			temp.add(findNodeInContainer(x+2,y));
			//temp.add(findNodeInContainer(x+2,y-2));
			temp.add(findNodeInContainer(x,y-2));
			for (int i = temp.size() - 1; i >= 0; i--)
			{
				if (temp.get(i) == null) temp.remove(i);
				else if (temp.get(i).hasBeenVisited()) return temp.get(i);
			}
			return null;
		}

		private Node findNodeInContainer(int x, int y)
		{
			for (int i = 0; i < container.size(); i++)
			{
				if (container.get(i).x == x && container.get(i).y == y)
					return container.get(i);
			}
			return null;
		}
	}



	private ArrayList<Location> nodesChecked = new ArrayList<Location>();
	public void clearNodes() {nodesChecked.clear();}
	public int findAreaOfRoom(int r, int c)
	{
		for (int i = 0; i < nodesChecked.size(); i++)
		{
			if (nodesChecked.get(i).equals(new Location(r,c)))
			{
				nodesChecked.add(new Location(r,c));
				return 0;
			}
		}
		if (findActor(r,c) instanceof Obstacle)
		{
			nodesChecked.add(new Location(r,c));
			return 0;
		}
		if (r >= rows || c >= cols || r == 0 || c == 0)
		{
			nodesChecked.add(new Location(r,c));
			return 0;
		}
		nodesChecked.add(new Location(r,c));
		return findAreaOfRoom(r+1,c) + findAreaOfRoom(r-1,c) + findAreaOfRoom(r,c+1) + findAreaOfRoom(r,c-1) + 1;
	}

	public void clearSmallRooms()
	{
		for (int r = 0; r < rows; r++)
		{
			for (int c = 0; c < cols; c++)
			{
				if (r % 2 == 0 && c % 2 == 0)
				{
					int area = findAreaOfRoom(r,c);
					clearNodes();
					//System.out.println(area);
					if (area < 20 && !(findActor(r,c) instanceof Obstacle)) 
					{
						new Obstacle(this).moveTo(r,c);
					}
				}
			}
		}
	}

	public Location findSpot()
	{
		int r,c;
		do 
		{
			r = (int)(Math.random()*rows);
			c = (int)(Math.random()*cols);
		} while (findActor(r,c) != null);
		return new Location(r,c);
	}

	public void findASpawn(boolean clearSpawn)
	{
		ArrayList<Location> possibleSpawns = new ArrayList<Location>();		
		for (int r = 0; r < rows; r++)
		{
			for (int c = 0; c < cols; c++)
			{
				if (r % 2 == 0 && c % 2 == 0)
				{
					int area = findAreaOfRoom(r,c);
					clearNodes();
					//System.out.println(area);
					if (area < 5) 
					{
						//new Obstacle(this).moveTo(r,c);
					}
					else if (area > 50)
					{
						//System.out.println(r + "," + c);
						possibleSpawns.add(new Location(r,c));
					}
				}
			}
		}
		int randomIndex = (int) (Math.random()*possibleSpawns.size());
		setSpawn(possibleSpawns.get(randomIndex));
		Location temp = findSpot();
		int sr = temp.r;
		int sc = temp.c;
		if (clearSpawn)
		{
			for (int r = sr - 3; r <= sr + 3; r++)
			{
				for (int c = sc - 3; c < sc + 3; c++)
				{
					if (findActor(r,c) != null)
					{
						if (findActor(r,c) instanceof Enemy)
						{
							((Enemy)findActor(r,c)).removeSelfFromWorldClean();
						}
					}
				}
			}
		}
	}

	public void setSpawn(Location location) {
		setSpawn(location.r, location.c);
	}

	public Node findNodeInContainer(int x, int y)
	{
		for (int i = 0; i < nodes.size(); i++)
		{
			if (nodes.get(i).x == x && nodes.get(i).y == y)
				return nodes.get(i);
		}
		return null;
	}

	public void generateCells()
	{
		for (int r = 0; r < rows; r++)
		{
			if (r % 2 == 0)
			{
				for (int c = 0; c < cols; c++)
					new Obstacle(this).moveTo(r, c);
			}
			else
			{
				for (int c = 0; c < cols; c += 2)
					new Obstacle(this).moveTo(r, c);
				for (int c = 1; c < cols; c += 2)
					new Node(r,c,nodes);
			}
		}
	}

	public void spawnRandomEnemies(int times)
	{
		for (int i = 0; i < times; i++)
		{
			int random = (int)(Math.random()*Data.enemyNames.length);
			//System.out.println(random);
			Enemy temp = new Enemy(this,Data.enemyLevels[random] + (int)(Math.random()*Runner.difficulty),Data.enemyNames[random],Data.behavior[random],random);
			boolean moved = false;
			do
			{
				int r = (int)(Math.random()*rows);
				int c = (int)(Math.random()*cols);
				if (findActor(r, c) == null)
				{
					temp.moveTo(r, c);
					moved = true;
				}
			} while (!moved);
		}
	}

	public void clearPatch(int x1, int y1, int x2, int y2)
	{
		for (int i = occupants.size() - 1; i >= 0; i--)
		{
			if (occupants.get(i).getX() >= x1 && occupants.get(i).getX() <= x2 && occupants.get(i).getY() >= y1 && occupants.get(i).getY() <= y2)
			{
				occupants.remove(i);
			}
		}
	}

	//doesn't actually clear the whole rectangle
	public void clearPatch(Rectangle r)
	{

	}

	public void fillPatch(int x1, int y1, int x2, int y2, String stringy)
	{
		for (int r = y1; r <= y2; r++)
		{
			for (int c = x1; c <= x2; c++)
			{
				if (findActor(r,c) == null) 
				{
					if (stringy.equals("Obstacle"))
						new Obstacle(this).moveTo(r, c);
					else if (stringy.equals("Water"))
						new Water(this).moveTo(r, c);
				}
			}
		}
	}

	public Node clearRandomPatches(boolean walls, int times, int w, int h) {
		//HashMap<Integer,Integer> usedRows = new HashMap<Integer,Integer>();\
		int r = 1; int c = 1;
		for (int i = 0; i < times; i++)
		{
			r = (int)(Math.random()*rows);
			c = (int)(Math.random()*cols);
			clearPatch(r,c,r+w,c+h);
			if (walls)
			{

			}
		}
		return new Node(r,c);
	}

	public void fillRandomPatches(boolean walls, int times, int w, int h) {
		int r = 1; int c = 1;
		for (int i = 0; i < times; i++)
		{
			r = (int)(Math.random()*rows);
			c = (int)(Math.random()*cols);
			fillPatch(r,c,r+w,c+h,"Obstacle");
			if (walls)
			{

			}
		}
	}

	public void placeTiles()
	{
		for (int r = 0; r <= rows; r++)
		{
			for (int c = 0; c <= cols; c++)
			{
				tiles[r][c] = new StoneTile(this);
			}
		}
	}

	public void placeTilesOf(String stringy, int times)
	{
		for (int i = 0; i < times; i++)
		{
			Location temp = findSpot();
			//there must be a way to do this
			if (stringy.equals("StoneTile"))
				tiles[temp.r][temp.c] = new StoneTile(this);
			else if (stringy.equals("ChestTile"))
				tiles[temp.r][temp.c] = new ChestTile(this);
			else if (stringy.equals("HealingTile"))
				tiles[temp.r][temp.c] = new HealingTile(this);
			else if (stringy.equals("LavaTile"))
				tiles[temp.r][temp.c] = new LavaTile(this);
			else if (stringy.equals("SlimeTile"))
				tiles[temp.r][temp.c] = new SlimeTile(this);
			else if (stringy.equals("SpikeTile"))
				tiles[temp.r][temp.c] = new SpikeTile(this);
			else if (stringy.equals("TrapTile"))
				tiles[temp.r][temp.c] = new TrapTile(this);
		}
	}

	private void cleanStandaloneBlocks() {
		for (int r = 0; r < rows; r += 2)
		{
			for (int c = 0; c < cols; c += 2)
			{
				if (findNodeInContainer(c,r+1) == null && findNodeInContainer(c,r-1) == null && findNodeInContainer(c+1,r) == null && findNodeInContainer(c-1,r) == null)
				{
					int numRandomNodes = 1;
					ArrayList<Node> temp = new ArrayList<Node>();
					new Node(c-1,r-1,temp);
					new Node(c-1,r,temp);
					new Node(c-1,r+1,temp);
					new Node(c,r+1,temp);
					new Node(c+1,r+1,temp);
					new Node(c+1,r,temp);
					new Node(c+1,r-1,temp);
					new Node(c,r-1,temp);
					for (int i = 0; i < numRandomNodes; i++)
					{
						Node n = temp.get((int)(Math.random()*temp.size()));
						new Obstacle(this).moveTo(n.x, n.y);
					}
					temp.clear();
				}
			}
		}
		int changed = 0;
		for (int r = 0; r < rows; r++)
		{
			for (int c = 0; c < cols; c++)
			{
				if (findActor(c,r) != null && new Node(r,c).findImmediateNeighbors().size() == 0)
				{
					//System.out.println(r + "," + c);
					findActor(c,r).removeSelfFromWorld();
					changed++;
				}
				if (new Node(c,r).findImmediateNeighbors().size() >= 7)
				{
					new Obstacle(this).moveTo(r,c);
					changed++;
				}
			}
		}
		//if (changed > 0) cleanStandaloneBlocks();
	}

	public void placeDoors(int times) 
	{
		int i = 0;
		do
		{
			int r = (int)(Math.random()*rows);
			int c = (int)(Math.random()*cols);
			if (((findActor(r-1,c) != null && findActor(r+1,c) != null) || (findActor(r,c+1) != null && findActor(r,c-1) != null)) && findActor(r,c) == null)
			{
				new Door(this).moveTo(r,c);
				i++;
			}
		} while (i < times);
	}

	public void makeWalledRooms(int times, boolean breakable)
	{
		for (int i = 0; i < times; i++)
		{
			int startr = 0; 
			int startc = 0;
			do
			{
				startr = (int)(Math.random()*rows);
				startc = (int)(Math.random()*cols);
			} while (startr % 2 == 1 || startc % 2 == 1);
			int size = (int)(Math.random()*rows/5);

			Actor wall;
			if (breakable)
				wall = new WeakObstacle(this,50);
			else
				wall = new Obstacle(this);

			clearPatch(startr,startc,size,size);
			for (int r = startr; r <= startr + size; r++)
			{
				if (findActor(r,startc) == null)
					wall.moveTo(r, startc);
				if (findActor(r,startc + size) == null)
					wall.moveTo(r, startc + size);
			}
			for (int c = startc; c <= startc + size; c++)
			{
				if (findActor(startr, c) == null)
					wall.moveTo(startr, c);
				if (findActor(startr + size, c) == null)
					wall.moveTo(startr + size, c);
			}
		}
	}

	public void fillInWalls() {
		for (int r = 0; r <= rows; r++)
		{
			if (findActor(r,0) == null) new Obstacle(this).moveTo(r,0);
			if (findActor(r,cols) == null) new Obstacle(this).moveTo(r,cols);
		}
		for (int c = 0; c <= cols; c++)
		{
			if (findActor(0,c) == null) new Obstacle(this).moveTo(0,c);
			if (findActor(rows,c) == null) new Obstacle(this).moveTo(rows,c);
		}
	}

	public void makeWay(Actor actor, int len)
	{
		int sr = actor.getX();
		int sc = actor.getY();
		for (int r = sr - len; r <= sr + len; r++)
		{
			for (int c = sc - len; c < sc + len; c++)
			{
				if (findActor(r,c) != null && r > 0 && r < rows && c > 0 && c < cols)
				{
					if (findActor(r,c) instanceof Enemy)
					{
						((Enemy)findActor(r,c)).removeSelfFromWorldClean();
					}
					else if (!(findActor(r,c) instanceof Player) && !(findActor(r,c) instanceof Portal) && !(findActor(r,c) instanceof Pet))
					{
						findActor(r,c).removeSelfFromWorld();
					}
				}
			}
		}
		double temp = Math.random();
		if (temp < 0.25)
		{
			new Portal(this,Runner.difficulty).moveTo(sr,sc+2);
		}
		else if (temp < 0.5)
		{
			new Portal(this,Runner.difficulty).moveTo(sr,sc-2);
		}
		else if (temp < 0.75)
		{
			new Portal(this,Runner.difficulty).moveTo(sr+2,sc);
		}
		else
		{
			new Portal(this,Runner.difficulty).moveTo(sr-2,sc);
		}
	}

	public Location generatePortals(int times)
	{
		if (times == 0) return null;
		Location[] locations = new Location[times];
		for (int i = 0; i < times; i++)
		{
			Portal temp = new Portal(this,Runner.difficulty);
			temp.moveTo(findSpot());
			locations[i] = temp.getLocation();
			makeWay(temp,1);
		}
		return locations[0];
	}

	public void fillAll()
	{
		for (int r = 0; r < rows; r++)
		{
			for (int c = 0; c < cols; c++)
			{
				new Obstacle(this).moveTo(r,c);
			}
		}
	}

	public void carvePath(Node start)
	{
		start.visited = true;
		ArrayList<Node> neighboringNodes = start.findNewNeighboringNodes();
		if (neighboringNodes.size() == 0) 
		{
			for (int r = 1; r < rows; r += 2)
			{
				for (int c = 1; c < cols; c += 2)
				{
					//System.out.println(r + "," + c);
					Node newStart = nodes.get(0).findNodeInContainer(r, c);
					if (newStart != null)
					{
						if (!newStart.visited && newStart.findFirstVisitedNeighboringNode() != null)
						{
							newStart.connectNode(newStart.findFirstVisitedNeighboringNode());
							if (waitMode)
								try {Thread.sleep(200);} catch (InterruptedException e) {}
							carvePath(newStart);
						}
					}
				}
			}
			return;
		}
		Node random = neighboringNodes.get((int)(Math.random()*neighboringNodes.size()));
		start.connectNode(random);
		//try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
		carvePath(random);
	}

}
*/