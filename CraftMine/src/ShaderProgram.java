import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;
import org.joml.Matrix4f;

public class ShaderProgram {
	private final int programId;
	private final TextureHandler texture;
	private float aspectRatio;
	private Matrix4f projection;
	
	public ShaderProgram(String vertexShaderSource, String fragmentShaderSource) {
		int vertexShaderId = compileShader(vertexShaderSource, GL30.GL_VERTEX_SHADER);
		int fragmentShaderId = compileShader(fragmentShaderSource, GL30.GL_FRAGMENT_SHADER);
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
	
	private int compileShader(String shaderSource, int type) {
		int shaderId = GL30.glCreateShader(type);
		GL30.glShaderSource(shaderId, shaderSource);
		GL30.glCompileShader(shaderId);
		
		if (GL30.glGetShaderi(shaderId, GL30.GL_COMPILE_STATUS) == GL30.GL_FALSE) {
			throw new RuntimeException("Error compiling shader: " + GL30.glGetShaderInfoLog(shaderId));
		}
		
		return shaderId;
	}
	
	public void use() {
		GL30.glUseProgram(programId);
		texture.bind(0); // Bind to texture unit 0
		GL30.glUniform1i(GL30.glGetUniformLocation(programId, "texture"), 0);
	}
	
	public int getId() {
		return programId;
	}
	
	public void updateAspectRatio(int newWidth, int newHeight) {
		aspectRatio = (float) newWidth / newHeight;
		getProjectionMatrix();
	}
	
	private void getProjectionMatrix() {
		// Create perspective projection matrix
		projection = new Matrix4f().perspective(
				(float)	Math.toRadians(Settings.CAMERA_FOV),
				aspectRatio,
				Settings.CAMERA_ZNEAR,
				Settings.CAMERA_ZFAR
				);
	}
	
	public void sendMatricesToShader(Camera camera, Transform transform) {
			// Send matrices to shader
			int projectionLoc = GL30.glGetUniformLocation(programId, "projection"); // PROJECTION MATRIX
			try (MemoryStack stack = MemoryStack.stackPush()) {
				FloatBuffer buffer = stack.mallocFloat(16); // 4x4 Matrix
				projection.get(buffer);
				GL30.glUniformMatrix4fv(projectionLoc, false, buffer);
			}
			int modelLoc = GL30.glGetUniformLocation(programId, "model");			// MODEL MATRIX
			try (MemoryStack stack = MemoryStack.stackPush()) {
				FloatBuffer buffer = stack.mallocFloat(16);
				transform.getModelMatrix().get(buffer);
				GL30.glUniformMatrix4fv(modelLoc, false, buffer);
			}
			int viewLoc = GL30.glGetUniformLocation(programId, "view");				// VIEW MATRIX
			try (MemoryStack stack = MemoryStack.stackPush()) {
				FloatBuffer buffer = stack.mallocFloat(16);
				camera.getViewMatrix().get(buffer);
				GL30.glUniformMatrix4fv(viewLoc, false, buffer);
			}
	}
	
	public void delete() {
		texture.cleanUp();
		GL30.glDeleteProgram(programId);
	}
}
