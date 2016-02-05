package system;

import java.util.Map.Entry;

import entity.Animation;
import entity.Entity;
import lwjglEngine.gui.GuiTexture;
import lwjglEngine.models.TexturedModel;
import tests.MainGameLoop;

public class AnimationSystem extends BaseSystem {

	public AnimationSystem(MainGameLoop game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void tick() {
		main.frame++;
		for (Entry<Entity,GuiTexture> entry: main.lm.models.entrySet())
		{
			Entity en = entry.getKey();
			GuiTexture model = entry.getValue();
			if (!model.animations.isEmpty())
			{
				Animation anim = model.animations.get(0);
				anim.behavior();
				/*if (anim.data.size() > 0)
					System.out.println("An anim" + anim.data.get(0));
				else
					System.out.println("No data in anim");
				 */				
				anim.frame++;
				if (anim.frame >= anim.framesTotal)
				{
					model.animations.remove(0);
				}
			}
		}
	}

}
