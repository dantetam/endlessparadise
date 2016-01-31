package entity;

public abstract class Animation {

	public int frame, framesTotal;
	
	public Animation(int a, int b) {
		frame = a;
		framesTotal = b;
	}
	
	public abstract void behavior();
	
}
