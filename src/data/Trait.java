package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import entity.Entity;

public class Trait {

	public String name;
	public String description;
	public static HashMap<Trait, Bonus[]> traitBonuses = new HashMap<Trait, Bonus[]>();
	
	public static ArrayList<String> traitNames = new ArrayList<String>();
	
	public static void init()
	{
		
	} Abilities should stack on each other in order! More important traits get to weigh more and stack (repeat eval bonuses?)
	
	private static Bonus[] traitOperateOn(Entity entity, String trait)
	{
		Bonus[] bonuses = null;
		for (Entry<Trait, Bonus[]> entry: traitBonuses.entrySet())
		{
			if (entry.getKey().name.equals(trait))
				bonuses = entry.getValue();
			Remember to eval bonuses. Add to bonus stack in entity and then eval once all bonuses are recorded (and in correct order)
		}
		if (bonuses == null)
		{
			System.out.println("Trait " + trait + " not found in database");
			//return null;
		}
		return bonuses;
	}
	
	public static ArrayList<String> operateOn(Entity entity)
	{
		ArrayList<String> bonusesDesc = new ArrayList<String>();
		for (Trait trait: entity.traits)
		{
			Bonus[] bonuses = traitOperateOn(entity, trait.name);
			for (Bonus b: bonuses)
			{
				bonusesDesc.add("(" + trait.name + ") " + b.name + ": " + b.description);
			}
		}
		return bonusesDesc;
	}
	
}
