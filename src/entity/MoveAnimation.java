package entity;

import lwjglEngine.gui.GuiTexture;
import lwjglEngine.models.LevelManager;
import lwjglEngine.models.TexturedModel;
import lwjglEngine.render.DisplayManager;

public class MoveAnimation extends Animation {

	/*public MoveAnimation(TexturedModel model, LevelManager lm, int a, int b) {
		super(model, lm, a, b);
	}*/
	
	public MoveAnimation(GuiTexture texture, LevelManager lm, int a, int b) {
		super(texture, lm, a, b);
	}

	public Animation left()
	{
		return data(
				-1D/(Player.sightXHalf*2D + 1)/20f,
				0, -9999, -9999
				);
	}
	public Animation right()
	{
		return data(
				1D/(Player.sightXHalf*2D + 1)/20f,
				0, -9999, -9999
				);
	}
	public Animation up()
	{
		return data(
				0,
				-1D/(Player.sightYHalf*2D + 1)/20f, -9999, -9999
				);
	}
	public Animation down()
	{
		return data(
				0,
				1D/(Player.sightYHalf*2D + 1)/20f, -9999, -9999
				);
	}
	
	//xSpeed, ySpeed, origX, origY
	
	@Override
	public void behavior() {
		// TODO Auto-generated method stub
		//if (data.size() == 0)
		//Why no auto unbox?
		if (frame < 0) return;
		lm.adjustTextureManual(texture, (float)(double)(data.get(2)+data.get(0)*frame), (float)(double)(data.get(3)+data.get(1)*frame), texture.size.x, texture.size.y);
	}
	
	
}
