package system;

import java.util.Map.Entry;

import entity.Animation;
import entity.Entity;
import lwjglEngine.models.TexturedModel;
import tests.MainGameLoop;

public class AnimationSystem extends BaseSystem {

	public int frame = 0;
	
	public AnimationSystem(MainGameLoop game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick() {
		frame++;
		for (Entry<Entity,TexturedModel> entry: main.lm.models.entrySet())
		{
			Entity en = entry.getKey();
			TexturedModel model = entry.getValue();
			if (!model.animations.isEmpty())
			{
				Animation anim = model.animations.get(0);
				anim.behavior();
				anim.frame++;
			}
		}
	}

}
