package lwjglEngine.gui;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import entity.Animation;
import lwjglEngine.render.DisplayManager;

//Completely scrap t

public class GuiTexture {

	public int texture;
	public Vector2f pos, size;
	public Vector2f origPixelPos;
	public Vector2f pixelPos, pixelSize;
	public boolean active = false;
	public Vector4f color = new Vector4f(0,0,0,255);
	public ArrayList<Animation> animations = new ArrayList<Animation>();
	
	public GuiTexture(int t, Vector2f p, Vector2f s) {
		texture = t;
		pixelPos = p; pixelSize = s;
		origPixelPos = p; 
		pos = new Vector2f(p.x/DisplayManager.width, p.y/DisplayManager.height); size = new Vector2f(s.x/DisplayManager.width, s.y/DisplayManager.height);
		active = true;
	}
	
	public void adjustByPixel(Vector2f p, Vector2f s)
	{
		pixelPos = p; pixelSize = s;
		origPixelPos = p; 
		pos = new Vector2f(p.x/DisplayManager.width, p.y/DisplayManager.height); size = new Vector2f(s.x/DisplayManager.width, s.y/DisplayManager.height);
	}
	public void adjustByProportion(Vector2f p, Vector2f s)
	{
		pos = p; size = s;
		origPixelPos = new Vector2f(p.x*DisplayManager.width, p.y*DisplayManager.height);
		pixelPos = origPixelPos;
		pixelSize = new Vector2f(s.x*DisplayManager.width, s.y*DisplayManager.height);
	}
	
	public boolean within(float x, float y)
	{
		return x > pixelPos.x && x < pixelPos.x + pixelSize.x && y > pixelPos.y && y < pixelPos.y + pixelSize.y;
	}


}
