package lwjglEngine.shaders;

public class StaticShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/lwjglEngine/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/lwjglEngine/shaders/fragmentShader.txt";
	
	public StaticShader() 
	{
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	protected void bindAttributes() 
	{
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}

	@Override
	protected void getAllUniformLocations() {
		// TODO Auto-generated method stub
		
	}

}
