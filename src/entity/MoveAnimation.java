package entity;

import lwjglEngine.models.LevelManager;
import lwjglEngine.models.TexturedModel;
import lwjglEngine.render.DisplayManager;

public class MoveAnimation extends Animation {

	public MoveAnimation(TexturedModel model, LevelManager lm, int a, int b) {
		super(model, lm, a, b);
		// TODO Auto-generated constructor stub
	}

	public Animation left()
	{
		return data(
				-1D/(Player.sightXHalf*2D + 1)/20f,
				0
				);
	}
	public Animation right()
	{
		return data(
				1D/(Player.sightXHalf*2D + 1)/20f,
				0
				);
	}
	public Animation up()
	{
		return data(
				0,
				-1D/(Player.sightYHalf*2D + 1)/20f
				);
	}
	public Animation down()
	{
		return data(
				0,
				1D/(Player.sightYHalf*2D + 1)/20f
				);
	}
	
	//xSpeed, ySpeed
	
	@Override
	public void behavior() {
		// TODO Auto-generated method stub
		//if (data.size() == 0)
		//Why no auto unbox?
		if (frame < 0) return;
		lm.adjustTextureManual(model, model.x + (float)(double)data.get(0), model.y + (float)(double)data.get(1), model.w, model.h);
	}
	
	
}
