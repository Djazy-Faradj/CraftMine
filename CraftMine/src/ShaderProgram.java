import org.lwjgl.opengl.GL30;

public class ShaderProgram {
	private final int programId;
	
	public ShaderProgram(String vertexShaderSource, String fragmentShaderSource) {
		int vertexShaderId = CompileShader(vertexShaderSource, GL30.GL_VERTEX_SHADER);
		int fragmentShaderId = CompileShader(fragmentShaderSource, GL30.GL_FRAGMENT_SHADER);
		
		// Link shaders into a program
		programId = GL30.glCreateProgram();
		GL30.glAttachShader(programId, vertexShaderId);
		GL30.glAttachShader(programId, fragmentShaderId);
		GL30.glLinkProgram(programId);
		
		if (GL30.glGetProgrami(programId, GL30.GL_LINK_STATUS) == GL30.GL_FALSE) {
			throw new RuntimeException("Error linking shader program: " + GL30.glGetProgramInfoLog(programId));
		}
		
		// Delete shaders after linking
		GL30.glDeleteShader(vertexShaderId);
		GL30.glDeleteShader(fragmentShaderId);		
	}
	
	private int CompileShader(String shaderSource, int type) {
		int shaderId = GL30.glCreateShader(type);
		GL30.glShaderSource(shaderId, shaderSource);
		GL30.glCompileShader(shaderId);
		
		if (GL30.glGetShaderi(shaderId, GL30.GL_COMPILE_STATUS) == GL30.GL_FALSE) {
			throw new RuntimeException("Error compiling shader: " + GL30.glGetShaderInfoLog(shaderId));
		}
		
		return shaderId;
	}
	
	public void Use() {
		GL30.glUseProgram(programId);
	}
	
	public int GetId() {
		return programId;
	}
	
	public void Delete() {
		GL30.glDeleteProgram(programId);
	}
}
