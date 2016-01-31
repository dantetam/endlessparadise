package data;

public class Bonus {

	public String name;
	public String description;
	
	public double dmgF, blkF, parF; //Flat bonus
	public double dmgP, blkP, parP; //Proportion bonus 
	
	public double atkF, strF, defF, agiF; //Flat skill bonus
	public double magF, rngF;
	
	public void setBonuses(double a, double b, double c, double d, double e, double f)
	{
		dmgF = a; blkF = b; parF = c;
		dmgP = d; blkP = e; parP = f;
	}
	
	public void setSkillBonuses(double a, double b, double c, double d, double e, double f)
	{
		atkF = a; strF = b; defF = c; agiF = d; 
		magF = e; rngF = f;
	}
	
	public static Bonus getRandom()
	{
		Bonus b = new Bonus();
		b.setBonuses((int)(Math.random()*5),(int)(Math.random()*5),(int)(Math.random()*5),1+Math.random(),1+Math.random(),1+Math.random());
		b.setSkillBonuses((int)(Math.random()*5),(int)(Math.random()*5),(int)(Math.random()*5),(int)(Math.random()*5),(int)(Math.random()*5),(int)(Math.random()*5));
		return b;
	}
}
