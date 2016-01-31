package lwjglEngine.render;

import lwjglEngine.models.LevelManager;
import lwjglEngine.models.RawModel;
import lwjglEngine.models.TexturedModel;

import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import entity.Building;
import entity.Entity;
import entity.Monster;
import entity.Player;

public class Renderer {

	public void prepare()
	{
		GL11.glClearColor(0,0,0,0);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}
	
	public void render(LevelManager lm)
	{
		lm.update();
		for (Entry<Entity,TexturedModel> entry: lm.models.entrySet())
		{
			if (entry.getKey() instanceof Monster || entry.getKey() instanceof Player) continue;
			TexturedModel model = entry.getValue();
			if (model.active)
				render(model);
		}
		for (Entry<Entity,TexturedModel> entry: lm.models.entrySet())
		{
			if (entry.getKey() instanceof Building) continue;
			TexturedModel model = entry.getValue();
			if (model.active)
				render(model);
		}
	}
	
	//The textured model encapsulates a RawModel
	//Access a VAO by using the data contained within a RawModel object
	public void render(TexturedModel texturedModel)
	{
		RawModel model = texturedModel.getRawModel();
		
		//Whenever a VAO is edited, it must be bound
		GL30.glBindVertexArray(model.vaoID);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		
		//Texture bank 0
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().textureID);
		
		//0 indicates the first vertex to be drawn in triangles
		//vertexCount property indicates upper bound
		
		//GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.vertexCount); 
		//replace the above method because we are using
		GL11.glDrawElements(GL11.GL_TRIANGLES, model.vertexCount, GL11.GL_UNSIGNED_INT, 0);
		
		//Disable after finished rendering
		GL20.glDisableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		
		GL30.glBindVertexArray(0); //Unbind the current bound VAO
	}

}
