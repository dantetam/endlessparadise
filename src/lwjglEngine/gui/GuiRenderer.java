package lwjglEngine.gui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import lwjglEngine.models.RawModel;
import lwjglEngine.render.DisplayManager;
import lwjglEngine.render.Loader;

public class GuiRenderer {

	private final RawModel quad; //Same model, will be moved and scaled across screen
	private GuiShader shader;
	//private UnicodeFont unicodeFont;

	public ArrayList<GuiTexture> guisActive = new ArrayList<GuiTexture>();
	
	public GuiRenderer(Loader loader) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		float[] positions = {-1,1,-1,-1,1,1,1,-1};
		quad = loader.loadToVAO(positions);
		shader = new GuiShader();
	}
	
	public void render()
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

	public int getFontHeight()
	{
		return 12;
	}
	public int getLineOffset()
	{
		return 0;
	}

	public void cleanUp()
	{
		//memFree(cdata);
		shader.cleanUp();
	}

}
