package entity;

import java.util.ArrayList;

import data.Bonus;
import data.Equipment;
import data.Trait;
import data.Weapon;

public abstract class CombatEntity extends Entity {

	public int hp;
	//Completely original combat system.
	public double atk, str, def, agi; 
	public double mag, rng;
	
	public double dmg, blk, par;
	
	public ArrayList<Weapon> weapons = new ArrayList<Weapon>();
	public ArrayList<Equipment> equipment = new ArrayList<Equipment>();
	
	public void setStats(double a, double b, double c, double d, double e, double f)
	{
		
	}
	
	public int[] eval()
	{
		Object[] temp = Trait.getAllBonuses(this);
		ArrayList<Bonus> bonus = (ArrayList<Bonus>)temp[0];
		ArrayList<String> bonusesDesc = (ArrayList<String>)temp[1];
		int[] baseCombat = new int[3]; //dmg, blk, par
		double[] baseSkills = {atk, str, def, agi, mag, rng};
		for (Bonus b: bonus)
		{
			baseSkills[0] += b.atkF;
			baseSkills[1] += b.strF;
			baseSkills[2] += b.defF;
			baseSkills[3] += b.agiF;
			baseSkills[4] += b.magF;
			baseSkills[5] += b.rngF;
		}
		return baseCombat;
	}
	
}
