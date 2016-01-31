package entity;

import lwjglEngine.models.TexturedModel;
import lwjglEngine.render.DisplayManager;

public class MoveAnimation extends Animation {

	public MoveAnimation(TexturedModel model) {
		super(model, 0, 20);
		// TODO Auto-generated constructor stub
	}

	public void left()
	{
		data(
				-DisplayManager.width/(Player.sightXHalf*2 + 1)/20f,
				0
				);
	}
	public void right()
	{
		data(
				DisplayManager.width/(Player.sightXHalf*2 + 1)/20f,
				0
				);
	}
	public void up()
	{
		data(
				0,
				-DisplayManager.height/(Player.sightYHalf*2 + 1)/20f
				);
	}
	public void down()
	{
		data(
				0,
				DisplayManager.height/(Player.sightYHalf*2 + 1)/20f
				);
	}
	
	//xSpeed, ySpeed
	
	@Override
	public void behavior() {
		// TODO Auto-generated method stub
		//if (data.size() == 0)
		model.	
		
	}
	
	
}
