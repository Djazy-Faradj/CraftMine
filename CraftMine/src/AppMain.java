// Djazy Faradj
// Last Updated: 2024-12-23

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL30;

public class AppMain {

	public static void main(String[] args) {
		System.out.println("Opening Craftmine...");
		
		if (!GLFW.glfwInit()) {
			throw new IllegalStateException("Failed to initialize GLFW");
		}
		
		long window = GLFW.glfwCreateWindow(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT, "Craftmine", 0, 0);
		GLFW.glfwMakeContextCurrent(window);
		GL.createCapabilities();
		
		// Instantiate a shader program and the renderer
		ShaderProgram shaderProgram = new ShaderProgram(ShaderSource.vertexShaderSource, ShaderSource.fragmentShaderSource);
		Renderer renderer = new Renderer(Settings.vertices);
		
		// Program loop
		while (!GLFW.glfwWindowShouldClose(window)) {
			GL30.glClear(GL30.GL_COLOR_BUFFER_BIT);
			
			shaderProgram.Use();
			renderer.Render();
			
			GLFW.glfwSetFramebufferSizeCallback(window, (win, width, height) -> {
				GL30.glViewport(0, 0, width, height);
				float newAspectRatio = (float) width / height;
				shaderProgram.UpdateAspectRatio(newAspectRatio);
			});
			
			GLFW.glfwSwapBuffers(window);
			GLFW.glfwPollEvents();
		}
		
		renderer.CleanUp();
		shaderProgram.Delete();
		GLFW.glfwDestroyWindow(window);
		GLFW.glfwTerminate();
		
		System.out.println("Closing Craftmine...");
	}

}
