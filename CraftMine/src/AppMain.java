// Djazy Faradj
// Last Updated: 2024-12-23
import java.nio.FloatBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

public class AppMain {

	public static enum GAME_STATE {
		PLAY,
		MENU
	}
	
	public static GAME_STATE currentState;
	
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
		Transform transform = new Transform();
		int modelLoc;
		InputHandler inputHandler = new InputHandler();
		
		// Get initial framebuffer size
		int[] width = new int[1];
		int[] height = new int[1];
		GLFW.glfwGetFramebufferSize(window, width, height);
		
		
		// Prevents screen from being black upon opening program
		shaderProgram.Use();
		shaderProgram.UpdateAspectRatio(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
		
		// Set initial game state to "play"
		UpdateGameState(GAME_STATE.PLAY); 
		
		// Program loop
		while (!GLFW.glfwWindowShouldClose(window)) {
			GL30.glClear(GL30.GL_COLOR_BUFFER_BIT);
			
			shaderProgram.Use();
			renderer.Render();

			// Gets called when key is pressed
			GLFW.glfwSetKeyCallback(window, (win, key, scancode, action, mods) -> {
				inputHandler.Call(key, action, scancode, transform);
			});
			
			// Apply tranformations
			modelLoc = GL30.glGetUniformLocation(shaderProgram.GetId(), "model");
			try (MemoryStack stack = MemoryStack.stackPush()) {
				FloatBuffer buffer = stack.mallocFloat(16);
				transform.GetModelMatrix().get(buffer);
				GL30.glUniformMatrix4fv(modelLoc, false, buffer);
			}
			
			
			// Gets called when window is resized
			GLFW.glfwSetFramebufferSizeCallback(window, (win, nwidth, nheight) -> {
				GL30.glViewport(0, 0, nwidth, nheight);
				shaderProgram.UpdateAspectRatio(nwidth, nheight);
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
	
	public static void UpdateGameState(GAME_STATE newState) {
		System.out.println("Updating game state..");
		currentState = newState;
	}

}
