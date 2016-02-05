package lwjglEngine.render;

import lwjglEngine.gui.GuiShader;
import lwjglEngine.gui.GuiTexture;
import lwjglEngine.models.LevelManager;
import lwjglEngine.models.RawModel;
import lwjglEngine.models.TexturedModel;

import java.util.ArrayList;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entity.Building;
import entity.Entity;
import entity.Monster;
import entity.Player;
import levels.Tile;

public class Renderer {

	private final RawModel quad; //Same model, will be moved and scaled across screen
	private GuiShader shader;
	//private UnicodeFont unicodeFont;

	public ArrayList<GuiTexture> guisActive = new ArrayList<GuiTexture>();

	public Renderer(Loader loader) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		float[] positions = {-1,1,-1,-1,1,1,1,-1};
		quad = loader.loadToVAO(positions);
		shader = new GuiShader();
	}

	public void prepare()
	{
		GL11.glClearColor(0,0,0,0);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}

	public void render(LevelManager lm)
	{
		lm.update();
		for (Entry<Tile,TexturedModel> entry: lm.tiles.entrySet())
		{
			TexturedModel model = entry.getValue();
			if (model.active)
				render(model);
		}
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
		shader.start();
		GL30.glBindVertexArray(quad.vaoID);
		GL20.glEnableVertexAttribArray(0);
		for (GuiTexture gui: guisActive)
		{
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.texture);
			Matrix4f matrix = createTransformationMatrix(
					normalize(new Vector2f(gui.pixelPos.x + gui.pixelSize.x/2, DisplayManager.height - (gui.pixelPos.y + gui.pixelSize.y/2))), 
					normalizeSize(gui.pixelSize)
					);
			shader.loadColor(new Vector4f(gui.color.x/255f, gui.color.y/255f, gui.color.z/255f, gui.color.w/255f));
			//if (gui instanceof TextBox)
			//System.out.println(gui.color + " " + ((TextBox)gui).display.get(0));
			shader.loadTransformation(matrix);
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.vertexCount);
		}
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		/*for (GuiTexture gui: guis)
		{
			if (gui instanceof TextBox)
				showText((TextBox)gui);
		}*/
		shader.stop();
		/*for (GuiTexture gui: guis)
		{
			fpsFont.drawString(280.0F, 300.0F, "", Color.red);
		}*/
		/*RawModel model = texturedModel.getRawModel();

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
		 */	
	}
	private Vector2f normalize(Vector2f v)
	{
		return new Vector2f(v.x*2/DisplayManager.width - 1, v.y*2/DisplayManager.height - 1);
	}
	private Vector2f normalizeSize(Vector2f v)
	{
		return new Vector2f(v.x/DisplayManager.width, v.y/DisplayManager.height);
	}

	//For 2D GUIs
	public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
		return matrix;
	}


}
