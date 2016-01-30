package data;

import java.util.ArrayList;

public class Names {

	public static String[] adjectives;
	public static String[] nouns;
	public static String[] prepositions;
	public static ArrayList<ArrayList<String>> structures;

	public static boolean init = false;
	public static void init()
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
		//adjectives = new String[]{"a","b","c"};
		adjectives = readAdjectives();
		nouns = new String[]{"x","y","z"};
		prepositions = new String[]{"of","at","in","from"};
	}

	private static String[] readAdjectives()
	{
		In in = new In("./res/traits.txt");
		int line = 0;
		String[] temp = null;
		while (!in.isEmpty()) 
		{
			if (line == 0)
				temp = new String[in.readInt()];
			else if (line <= temp.length)
				temp[line-1] = in.readString();
			else
				break;
			line++;
		}
		return temp;
	}

	public static String randomName()
	{
		String temp = "";
		ArrayList<String> structure = structures.get((int)(Math.random()*structures.size()));
		for (String s: structure)
		{
			String[] candidates;
			boolean capitalize = true;
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
				capitalize = false;
				break;
			default:
				candidates = null;
				capitalize = false;
				break;
			}
			String word = candidates[(int)(Math.random()*candidates.length)];
			if (capitalize)
			{
				String tempString = "" + Character.toUpperCase(word.charAt(0));
				if (word.length() > 1) tempString += word.substring(1);
				word = tempString;
			}
			temp += word + " ";
		}
		return temp.substring(0,temp.length()); //Remove trailing space
	}

	public static ArrayList<String> list(String... looseStrings)
	{
		ArrayList<String> temp = new ArrayList<String>();
		for (String s: looseStrings)
			temp.add(s);
		return temp;
	}

}
