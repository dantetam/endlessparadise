package entity;

import java.util.ArrayList;

import lwjglEngine.gui.GuiTexture;
import lwjglEngine.models.LevelManager;
import lwjglEngine.models.TexturedModel;

public abstract class Animation {

	public int frame, framesTotal;
	public ArrayList<Double> data = new ArrayList<Double>();
	//public TexturedModel model;
	public GuiTexture texture;
	public static LevelManager lm = null;
	
	/*public Animation(TexturedModel m, LevelManager lm, int a, int b) {
		model = m;
		frame = a;
		framesTotal = b;
		if (Animation.lm == null)
			Animation.lm = lm;
	}*/
	
	public Animation(GuiTexture m, LevelManager lm, int a, int b) {
		texture = m;
		frame = a;
		framesTotal = b;
		if (Animation.lm == null)
			Animation.lm = lm;
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
