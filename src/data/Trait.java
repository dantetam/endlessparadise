package data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import entity.Entity;

public class Trait {

	public String name;
	public String description;
	public static HashMap<Trait, Bonus[]> traitBonuses = new HashMap<Trait, Bonus[]>();
	
	public Trait(String n, String d)
	{
		name = n;
		description = d;
	}
	
	public static void init()
	{
		EntityData.init();
		for (Trait trait: EntityData.traits)
		{
			Bonus[] bonuses = new Bonus[(int)(Math.random()*4)];
			for (int i = 0; i < bonuses.length; i++)
			{
				Bonus b = new Bonus();
				b.setBonuses((int)(Math.random()*5),(int)(Math.random()*5),(int)(Math.random()*5),1+Math.random(),1+Math.random(),1+Math.random());
				b.setSkillBonuses((int)(Math.random()*5),(int)(Math.random()*5),(int)(Math.random()*5),(int)(Math.random()*5),(int)(Math.random()*5),(int)(Math.random()*5));
				bonuses[i] = b;
			}
			traitBonuses.put(trait, bonuses);
		}
	} 
	// Abilities should stack on each other in order! More important traits get to weigh more and stack (repeat eval bonuses?)

	public static Bonus[] traitOperateOn(Entity entity, String trait)
	{
		Bonus[] bonuses = null;
		for (Entry<Trait, Bonus[]> entry: traitBonuses.entrySet())
		{
			if (entry.getKey().name.equals(trait))
				bonuses = entry.getValue();
			//Remember to eval bonuses. Add to bonus stack in entity and then eval once all bonuses are recorded (and in correct order)
		}
		if (bonuses == null)
		{
			System.out.println("Trait " + trait + " not found in database");
			//return null;
		}		
		return bonuses;
	}

	public static Object[] getAllBonuses(Entity entity)
	{
		ArrayList<Bonus> bonuses = new ArrayList<Bonus>();
		ArrayList<String> bonusesDesc = new ArrayList<String>();
		for (Trait trait: entity.traits)
		{
			Bonus[] bonusesTrait = traitOperateOn(entity, trait.name);
			for (Bonus b: bonusesTrait)
			{
				bonuses.add(b);
				bonusesDesc.add("(" + trait.name + ") " + b.name + ": " + b.description);
			}
		}
		Object[] temp = new Object[2];
		temp[0] = bonuses; temp[1] = bonusesDesc;
		return temp;
	}

	/*public static ArrayList<String> operateOnAll(Entity entity)
	{
		ArrayList<String> bonusesDesc = new ArrayList<String>();
		ArrayList<Bonus> bonuses = getAllBonuses(entity); 
		for (Bonus b: bonuses)
		{
			bonusesDesc.add("(" + trait.name + ") " + b.name + ": " + b.description);
		}
		return bonusesDesc;
	}*/

}
