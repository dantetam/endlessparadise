package data;

import java.util.ArrayList;

import entity.*;

public class EntityData {

	public static ArrayList<Trait> traits = new ArrayList<Trait>();

	public static void init()
	{
		initTraits();
	}

	private static void initTraits()
	{
		In in = new In("./res/traits.txt");

		int line = 0;
		int items = 0;
		
		/* Keep looking until the file is empty. */
		while (!in.isEmpty()) 
		{
			if (line == 0)
				items = in.readInt();
			else if (line <= items)
			{
				traits.add(new Trait(in.readString(),""));
			}
			else
				break;
			line++;
		}
	}

}
