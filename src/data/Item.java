package data;

public class Item {
	
	public Bonus bonus;
	
	public double atkR, strR, defR, agiR; //Requirement
	public double magicR, rangedR;
	
	public void setBonuses(double a, double b, double c, double d, double e, double f)
	{
		bonus.setBonuses(a,b,c,d,e,f);
	}
	
	public void setReq(double a, double b, double c, double d, double e, double f)
	{
		atkR = a; strR = b; defR = c; agiR = d; 
		magicR = e; rangedR = f;
	}
	
}
