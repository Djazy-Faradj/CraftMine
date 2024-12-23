import org.lwjgl.opengl.GL20;

public class ShaderProgram {
	private final int programId;
	
	public ShaderProgram(String vertexShaderSource, String fragmentShaderSource) {
		int vertexShaderId = CompileShader(vertexShaderSource, GL20.GL_VERTEX_SHADER);
		int fragmentShaderId = CompileShader(fragmentShaderSource, GL20.GL_FRAGMENT_SHADER);
		
		// Link shaders into a program
		programId = GL20.glCreateProgram();
		GL20.glAttachShader(programId, vertexShaderId);
		GL20.glAttachShader(programId, fragmentShaderId);
		GL20.glLinkProgram(programId);
		
		if (GL20.glGetProgrami(programId, GL20.GL_LINK_STATUS) == GL20.GL_FALSE) {
			throw new RuntimeException("Error linking shader program: " + GL20.glGetProgramInfoLog(programId));
		}
		
		// Delete shaders after linking
		GL20.glDeleteShader(vertexShaderId);
		GL20.glDeleteShader(fragmentShaderId);		
	}
	
	private int CompileShader(String shaderSource, int type) {
		int shaderId = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderId, shaderSource);
		GL20.glCompileShader(shaderId);
		
		if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == GL20.GL_FALSE) {
			throw new RuntimeException("Error compiling shader: " + GL20.glGetShaderInfoLog(shaderId));
		}
		
		return shaderId;
	}
	
	public void Use() {
		GL20.glUseProgram(programId);
	}
	
	public void Delete() {
		GL20.glDeleteProgram(programId);
	}
}
