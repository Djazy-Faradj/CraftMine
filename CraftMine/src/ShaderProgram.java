import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;
import org.joml.Matrix4f;

public class ShaderProgram {
	private final int programId;
	private final TextureHandler texture;
	private float aspectRatio;
	
	public ShaderProgram(String vertexShaderSource, String fragmentShaderSource) {
		int vertexShaderId = CompileShader(vertexShaderSource, GL30.GL_VERTEX_SHADER);
		int fragmentShaderId = CompileShader(fragmentShaderSource, GL30.GL_FRAGMENT_SHADER);
		texture = new TextureHandler("assets/terrain.png");
		
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
		texture.Bind(0); // Bind to texture unit 0
		GL30.glUniform1i(GL30.glGetUniformLocation(programId, "texture"), 0);
	}
	
	public int GetId() {
		return programId;
	}
	
	public void UpdateAspectRatio(int newWidth, int newHeight) {
		aspectRatio = (float) newWidth / newHeight;
		CreateProjectionMatrix();
	}
	
	public void CreateProjectionMatrix() {
		// Create orthographic projection matrix
		Matrix4f projection = new Matrix4f().ortho(
				-aspectRatio, aspectRatio, 	// Left, Right (scaled by aspect ratio)
				-1.0f, 1.0f,				// Bottom, Top
				-1.0f, 1.0f					// Near, Far (Not relevant)
				);
		
		// Send projection matrix to the shader
		int projectionLoc = GL30.glGetUniformLocation(programId, "projection");
		try (MemoryStack stack = MemoryStack.stackPush()) {
			FloatBuffer buffer = stack.mallocFloat(16); // 4x4 Matrix
			projection.get(buffer);
			GL30.glUniformMatrix4fv(projectionLoc, false, buffer);
		}
	}
	
	public void Delete() {
		texture.CleanUp();
		GL30.glDeleteProgram(programId);
	}
}
