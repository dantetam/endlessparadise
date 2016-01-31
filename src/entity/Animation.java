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
		if (data.size() != theData.length)
			for (int i = 0; i < theData.length; i++)
				data.add(0D);
		for (int i = 0; i < theData.length; i++)
			if (theData[i] != -9999)
				data.set(i, theData[i]);
		return this;
	}
	
	public abstract void behavior();
	
}
