package entity;

import java.util.ArrayList;

import data.Equipment;
import data.Weapon;

public abstract class CombatEntity extends Entity {

	public int hp;
	//Completely original combat system.
	public double atk, str, def, agi; 
	public double mag, rng;
	
	public double dmg, blk, par;
	
	public ArrayList<Weapon> weapons = new ArrayList<Weapon>();
	public ArrayList<Equipment> equipment = new ArrayList<Equipment>();
	
}
