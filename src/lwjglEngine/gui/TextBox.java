package lwjglEngine.gui;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import lwjglEngine.fontMeshCreator.FontType;
import lwjglEngine.fontRendering.TextMaster;
import lwjglEngine.gui.GuiTexture;
import lwjglEngine.render.DisplayManager;

public class TextBox extends GuiTexture {
	
	public String name;
	public ArrayList<String> tooltip;
	public ArrayList<String> display;
	public Menu menu;
	public String command;
	//public boolean enabled;
	
	//0-255
	public Vector3f textColor = new Vector3f(255,0,0); 
	
	//public String textString;
	public float fontSize;
	public int textMeshVao;
	public int vertexCount;
	public float lineMaxSize;
	public FontType font;

	public boolean centerText = false;

	/**
	 * Creates a new text, loads the text's quads into a VAO, and adds the text
	 * to the screen.
	 * 
	 * @param text
	 *            - the text.
	 * @param fontSize
	 *            - the font size of the text, where a font size of 1 is the
	 *            default size.
	 * @param font
	 *            - the font that this text should use.
	 * @param position
	 *            - the position on the screen where the top left corner of the
	 *            text should be rendered. The top left corner of the screen is
	 *            (0, 0) and the bottom right is (1, 1).
	 * @param maxLineLength
	 *            - basically the width of the virtual page in terms of screen
	 *            width (1 is full screen width, 0.5 is half the width of the
	 *            screen, etc.) Text cannot go off the edge of the page, so if
	 *            the text is longer than this length it will go onto the next
	 *            line. When text is centered it is centered into the middle of
	 *            the line, based on this line length value.
	 * @param centered
	 *            - whether the text should be centered or not.
	 */

	public void remove() {
		TextMaster.removeText(this);
	}
	
	public TextBox(int fontSize, FontType font, int maxLineLength, boolean centered, int texture, String text, String tip, float a, float b, float c, float d)
	{
		super(texture, new Vector2f(a,b), new Vector2f(c,d));
		display = new ArrayList<String>();
		display.add(text);
		tooltip = new ArrayList<String>();
		tooltip.add(tip);
		//tooltip.add(text);
		//enabled = false;
		
		this.fontSize = fontSize;
		//this.font = font;
		this.fontSize = 0.5f;
		this.lineMaxSize = 1f;
		//this.lineMaxSize = c/DisplayManager.width;
		this.centerText = true;
		if (TextMaster.init)
			TextMaster.loadText(this);
	}
	
	public boolean equals(TextBox o)
	{
		return pos.equals(o.pos) && size.equals(o.size);
	}
	
	public void move(float x, float y)
	{
		pos.x = x/DisplayManager.width;
		pos.y = y/DisplayManager.height;
		origPixelPos = new Vector2f(x,y);
		pixelPos.x = x;
		pixelPos.y = y; //pixelSize.y = y haha
		//String text = display.size() > 0 ? display.get(0) : null;
		//System.out.println("Moving: " + text + " " + pos + " " + size);
	}
	public void resize(float x, float y)
	{
		size.x = x/DisplayManager.width;
		size.y = y/DisplayManager.height;
		pixelSize = new Vector2f(x,y);
	}
	
	//Return itself for conveinence
	public TextBox color(float x, float y, float z) {color.x = x; color.y = y; color.z = z; return this;}
	public TextBox color(float w) {return color(w,w,w);}

}
