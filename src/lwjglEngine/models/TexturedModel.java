package lwjglEngine.models;

import java.util.ArrayList;

import entity.Animation;
import lwjglEngine.textures.ModelTexture;

public class TexturedModel {

	public RawModel rawModel;
	public ModelTexture texture;
	public boolean active = true;
	
	//public float x, y, w, h;
	//public void set(float a, float b, float c, float d) {x=a;y=b;w=c;h=d;}
	
	public ArrayList<Animation> animations = new ArrayList<Animation>();
	
	public TexturedModel(RawModel m, ModelTexture t)
	{
		rawModel = m;
		texture = t;
	}

	public RawModel getRawModel() {
		return rawModel;
	}

	public ModelTexture getTexture() {
		return texture;
	}

}
