import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class TriangleRenderer {
	
	private long window;
	
	public void Run() {
		Init();
		Loop();
		// Free resources and terminate
		GLFW.glfwDestroyWindow(window);
		GLFW.glfwTerminate();
	}
	
	private void Init() {
		// Initialize GLFW
		if (!GLFW.glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");
		
		// Configure GLFW
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE); // Window stays hidden after creation
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
		
		// Create the window
		window = GLFW.glfwCreateWindow(800, 600, "CraftMine", 0, 0);
		if (window == 0) throw new RuntimeException("Failed to create GLFW window");
		
		// Center the window
		GLFW.glfwSetWindowPos(window, 100, 100);
		
		// Make the OpenGL context current
		GLFW.glfwMakeContextCurrent(window);
		GL.createCapabilities();
		
		// Show the window
		GLFW.glfwShowWindow(window);
	}
	
	private void Loop() {
		while (!GLFW.glfwWindowShouldClose(window)) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // Clear the framebuffer
			
			// Swap the color buffers
			GLFW.glfwSwapBuffers(window);
			
			// Poll for window events
			GLFW.glfwPollEvents();
		}
	}
}
