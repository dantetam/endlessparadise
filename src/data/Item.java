package data;

import java.util.ArrayList;

public class Item {
	
	public String name;
	
	public ArrayList<Bonus> bonus = new ArrayList<Bonus>();
	
	public double atkR, strR, defR, agiR; //Requirement
	public double magicR, rangedR;
	
	/*public void setBonuses(double a, double b, double c, double d, double e, double f)
	{
		bonus.setBonuses(a,b,c,d,e,f);
	}
	
	public void setReq(double a, double b, double c, double d, double e, double f)
	{
		atkR = a; strR = b; defR = c; agiR = d; 
		magicR = e; rangedR = f;
	}*/
	
}
