package lwjglEngine.render;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import lwjglEngine.models.RawModel;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Loader {

	//Store VAOs and VBOs indices as reference for future clean up
	private ArrayList<Integer> vaos = new ArrayList<Integer>();
	private ArrayList<Integer> vbos = new ArrayList<Integer>();
	private ArrayList<Integer> textures = new ArrayList<Integer>();
	private ArrayList<String> respTextureNames = new ArrayList<String>();

	private ArrayList<String> monsterNames = new ArrayList<String>();

	private boolean init = false;
	public void init()
	{
		if (!init) init = true; else return;
		File folder = new File("./res/monsters/");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				//System.out.println("File " + listOfFiles[i].getName());
				String name = listOfFiles[i].getName();
				if (!name.contains("Thumbs"))
				{
					monsterNames.add(name.substring(0,name.length()-4));
					loadTexture("res/bluePlasma.png");
				}
			}
		}
	}

	//Create a new model from float data, which accessed by the renderer
	public RawModel loadToVAO(float[] pos, float[] textureCoords, int[] indices)
	{
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeData(0, 3, pos); //Store the position (3-tuples) in pos 0 of the VAO
		storeData(1, 2, textureCoords);
		unbindVAO();
		//There are repeats in the old pos[] so indices now contains the correct number of indices
		return new RawModel(vaoID,indices.length);
	}

	//For 2D text
	public int loadToVAO(float[] positions, float[] textureCoords) {
		int vaoID = createVAO();
		storeData(0, 2, positions);
		storeData(1, 2, textureCoords);
		unbindVAO();
		return vaoID;
	}

	public RawModel loadToVAO(float[] positions)
	{
		int vaoID = createVAO();
		this.storeData(0,2,positions);
		unbindVAO();
		return new RawModel(vaoID,positions.length/2);
	}

	/*public int loadTexture(String fileName)
	{
		Texture texture = null;
		try 
		{
			texture = TextureLoader.getTexture("PNG",new FileInputStream("res/"+fileName+".png"));
		} 
		catch (Exception e) {e.printStackTrace();}
		int textureID = texture.getTextureID();
		textures.add(textureID);
		return textureID;
	}*/

	public int loadTexture(String fileName)
	{
		//if (fileName == null) return -1;
		for (int i = 0; i < respTextureNames.size(); i++) //Check to see if we already loaded the texture
		{
			if (respTextureNames.get(i).equals(fileName))
				return textures.get(i);
		}

		BufferedImage image = null;
		try {
			File file = new File(fileName);
			image = ImageIO.read(file); 
			/*else
			{
				//System.out.println("file doesn't exist -> " + file);
				file = new File(fileName);
				image = ImageIO.read(file); 
				return -1;
			}*/
		} catch (IOException e) {
			//System.out.println("error");
			System.out.println("Can't find file -> " + fileName);
			e.printStackTrace();
		}

		final int BYTES_PER_PIXEL = 4;

		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
		
		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL); //4 for RGBA, 3 for RGB
		for (int y = 0; y < image.getHeight(); y++)
			for (int x = 0; x < image.getWidth(); x++)
			{
				int i = y;
				if (fileName.contains("/monster"))
					i = image.getHeight() - y;
				int pixel = pixels[y * image.getWidth() + x];
				//byte a = (byte)((pixel >> 24) & 0xFF);
				byte r = (byte)((pixel >> 16) & 0xFF);
				byte g = (byte)((pixel >> 8) & 0xFF);
				byte b = (byte)(pixel & 0xFF);
				//if (Math.random() < 0.001)
				//System.out.println(a);
				/*if (r > 0xEE && g == 0x00 && b > 0xEE) //If bright purple/pink, industry standard for sprites
				{
					r = (byte)0xff; g = 0; b = 0;
					a = (byte)0xfe;
				}
				else if (r == -1 && g == 0 && b == -1)
				{
					r = (byte)0xff; g = 0; b = 0;
					a = (byte)0xfe;
				}*/
				/*if (Math.random() < 0.001)
				{
					System.out.println((byte)r + " " + (byte)g + " " + (byte)b + " " + (byte)a);
					System.out.println((r == -1 && g == 0 && b == -1));
				}*/
				/*if (r > 0xEE && g < 0x0f && b > 0xEE) //If bright purple/pink, industry standard for sprites
				{
					r = (byte)0xff; g = 0; b = 0;
					a = (byte)0;
				}*/
				byte a = (byte)0xff;
				if (r == -1 && g == 0 && b == -1)
					a = (byte)0x00;
				buffer.put(r);   
				buffer.put(g);      
				buffer.put(b);            
				buffer.put(a);   
			}
		buffer.flip();

		int textureID = GL11.glGenTextures(); //Generate texture ID
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID); //Bind texture ID

		GL11.glEnable(GL11.GL_BLEND);

		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		//Setup wrap mode
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);

		//Setup texture scaling filtering
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

		//Send texel data to OpenGL
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

		//Store later for deleting
		textures.add(textureID);
		respTextureNames.add(fileName);
		//Return the texture ID so we can bind it later again
		return textureID;
	}

	//Delete VAOs and VBOs by finding their vertices
	public void cleanData()
	{
		for (int i: vaos)
			GL30.glDeleteVertexArrays(i);
		for (int i: vbos)
			GL15.glDeleteBuffers(i);
		for (int i: textures)
			GL11.glDeleteTextures(i);
		respTextureNames.clear();
	}

	//Request a new VAO id, store that ID, and bind it
	private int createVAO()
	{
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}

	private void storeData(int attribNum, int coordinateSize, float[] data)
	{
		int vboID = GL15.glGenBuffers(); //Request a VBO id
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID); //Bind it for use
		FloatBuffer buffer = toFloatBuffer(data); //Convert the data to a float buffer

		//Store the buffer in the VBO, for use in a static draw (vs. a dynamic draw)
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

		//Indicate that triangles are being drawn
		GL20.glVertexAttribPointer(attribNum,coordinateSize,GL11.GL_FLOAT,false,0,0);

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); //Unbind the current VBO being used
	}

	//Unbind the current VAO being used
	private void unbindVAO()
	{
		GL30.glBindVertexArray(0);
	}

	private void bindIndicesBuffer(int[] indices)
	{
		int vboID = GL15.glGenBuffers(); //Request a VBO id
		vbos.add(vboID); 
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID); //Note "ELEMENT_ARRAY" not "ARRAY"
		IntBuffer buffer = toIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW); //Store the data in the bound VBO
	}

	//Convert arrays of numbers to the respective buffers
	private IntBuffer toIntBuffer(int[] data)
	{
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	private FloatBuffer toFloatBuffer(float[] data)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	public String getRandomMonsterName() 
	{
		init();
		if (monsterNames.size() == 0) 
			return null;
		return monsterNames.get((int)(Math.random()*monsterNames.size()));
	}

}
