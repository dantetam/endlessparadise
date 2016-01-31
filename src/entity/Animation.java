package entity;

import java.util.ArrayList;

import lwjglEngine.models.TexturedModel;

public abstract class Animation {

	public int frame, framesTotal;
	public ArrayList<Double> data = new ArrayList<Double>();
	public TexturedModel model;
	
	public Animation(TexturedModel m, int a, int b) {
		model = m;
		frame = a;
		framesTotal = b;
	}
	
	public Animation data(double... theData)
	{
		for (double d: theData)
			data.add(d);
		return this;
	}
	
	public abstract void behavior();
	
}
