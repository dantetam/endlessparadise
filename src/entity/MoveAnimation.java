package entity;

import lwjglEngine.models.LevelManager;
import lwjglEngine.models.TexturedModel;
import lwjglEngine.render.DisplayManager;

public class MoveAnimation extends Animation {

	public MoveAnimation(TexturedModel model, LevelManager lm) {
		super(model, lm, 0, 20);
		// TODO Auto-generated constructor stub
	}

	public Animation left()
	{
		return data(
				-DisplayManager.width/(Player.sightXHalf*2 + 1)/20f,
				0
				);
	}
	public Animation right()
	{
		return data(
				DisplayManager.width/(Player.sightXHalf*2 + 1)/20f,
				0
				);
	}
	public Animation up()
	{
		return data(
				0,
				-DisplayManager.height/(Player.sightYHalf*2 + 1)/20f
				);
	}
	public Animation down()
	{
		return data(
				0,
				DisplayManager.height/(Player.sightYHalf*2 + 1)/20f
				);
	}
	
	//xSpeed, ySpeed
	
	@Override
	public void behavior() {
		// TODO Auto-generated method stub
		//if (data.size() == 0)
		//Why no auto unbox?
		lm.adjustTextureManual(model, model.x + (float)(double)data.get(0), model.y + (float)(double)data.get(1), model.w, model.h);
	}
	
	
}
