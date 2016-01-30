package data;

import java.util.ArrayList;

public class Names {

	public static String[] adjectives;
	public static String[] nouns;
	public static String[] prepositions;
	public static ArrayList<ArrayList<String>> structures;

	public boolean init = false;
	public void init()
	{
		if (!init)
		{
			init = true;
			structures = new ArrayList<ArrayList<String>>();
			structures.add(list("a","n"));
			structures.add(list("a","a","n"));
			structures.add(list("a","n","p","n"));
			structures.add(list("a","n","p","a","n"));
			structures.add(list("n","p","n"));
			structures.add(list("n","p","a","n"));
			structures.add(list("n","p","a"));
			structures.add(list("a","n","p","a"));
		}
	}

	public static String randomName()
	{
		String temp = "";
		ArrayList<String> structure = structures.get((int)(Math.random()*structures.size()));
		for (String s: structure)
		{
			String[] candidates;
			switch (s)
			{
			case "a":
				candidates = adjectives;
				break;
			case "n":
				candidates = nouns;
				break;
			case "p":
				candidates = prepositions;
				break;
			default:
				candidates = null;
				break;
			}
			temp += candidates[(int)(Math.random()*candidates.length)] + " ";
		}
		return temp.substring(0,temp.length()); //Remove trailing space
	}

	public ArrayList<String> list(String... looseStrings)
	{
		ArrayList<String> temp = new ArrayList<String>();
		for (String s: looseStrings)
			temp.add(s);
		return temp;
	}

}
