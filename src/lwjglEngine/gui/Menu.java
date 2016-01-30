package lwjglEngine.gui;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;

import lwjglEngine.fontMeshCreator.FontType;

public class Menu {

	public ArrayList<TextBox> buttons;
	public String name;
	private boolean active;
	public boolean noShortcuts = false;

	public Menu(String name)
	{
		this.name = name;
		buttons = new ArrayList<TextBox>();
		active = false;
	}

	public TextBox addButton(TextBox temp)
	{
		temp.menu = this;
		buttons.add(temp);
		return temp;
	}

	public TextBox findButtonByName(String name)
	{
		for (int i = 0; i < buttons.size(); i++)
			if (buttons.get(i).name.equals(name))
				return buttons.get(i);
		return null;
	}

	public String click(float mouseX, float mouseY)
	{
		if (this.name.equals("UnitMenu"))
			System.out.println(buttons.size());
		for (int i = 0; i < buttons.size(); i++)
		{
			TextBox b = buttons.get(i);
			if (this.name.equals("UnitMenu"))
			{
				System.out.println(b.display.get(0) + "; Pos: " + b.pixelPos + "; Size: " + b.pixelSize + "; Bounding Box: " + new Vector2f(b.pixelPos.x + b.pixelSize.x, b.pixelPos.y + b.pixelSize.y) + "; Mouse: " + mouseX + "," + mouseY);
			}
				if (b.within(mouseX, mouseY)) //mouseX > b.pos.x && mouseX < b.pos.x+b.size.x && mouseY > b.pos.y && mouseY < b.pos.y+b.size.y
					return b.command;
		}
		return null;
	}

	public TextBox within(float mouseX, float mouseY)
	{
		for (int i = 0; i < buttons.size(); i++)
		{
			TextBox b = buttons.get(i);
			if (b.within(mouseX, mouseY))
				return b;
		}
		return null;
	}
	
	public boolean equals(Menu other)
	{
		return name.equals(other.name);
	}
	
}
