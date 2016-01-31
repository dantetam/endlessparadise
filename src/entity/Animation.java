package entity;

import java.util.ArrayList;

import lwjglEngine.models.LevelManager;
import lwjglEngine.models.TexturedModel;

public abstract class Animation {

	public int frame, framesTotal;
	public ArrayList<Double> data = new ArrayList<Double>();
	public TexturedModel model;
	public static LevelManager lm = null;
	
	public Animation(TexturedModel m, LevelManager lm, int a, int b) {
		model = m;
		frame = a;
		framesTotal = b;
		if (this.lm == null)
			this.lm = lm;
	}
	
	public Animation data(double... theData)
	{
		for (double d: theData)
			data.add(d);
		return this;
	}
	
	public abstract void behavior();
	
}
