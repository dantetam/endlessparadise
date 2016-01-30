package lwjglEngine.models;

import lwjglEngine.textures.ModelTexture;

public class TexturedModel {

	public RawModel rawModel;
	public ModelTexture texture;
	public boolean active = true;
	
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
