package data;

import java.util.HashMap;

public class ItemData {

	public HashMap<String,Weapon> weapons = new HashMap<String,Weapon>();
	public HashMap<String,Equipment> equipment = new HashMap<String,Equipment>();
	
	public Weapon getWeapon(String w)
	{
		return weapons.get(w);
	}
	
	public Equipment getEqu(String e)
	{
		return equipment.get(e);
	}
	
}
