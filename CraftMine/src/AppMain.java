// Djazy Faradj
// Last Updated: 2024-12-25
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL30;
import org.joml.Vector3f;

public class AppMain {

	public static enum GAME_STATE {
		PLAY,
		MENU
	}
	
	public static GAME_STATE currentState;

	// To start game timer
	static float lastFrame = 0.0f, deltaTime = 0.0f;

	private static float lastX = 0.0f, lastY = 0.0f;
	
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
		
		// Instantiate camera
		Camera camera = new Camera(new Vector3f(0.0f, 0.0f, 1.0f));
		
		// Program loop
		while (!GLFW.glfwWindowShouldClose(window)) {
			GL30.glClear(GL30.GL_COLOR_BUFFER_BIT);
			
			// per-frame timing
			float currentFrame = (float) GLFW.glfwGetTime();
			deltaTime = currentFrame - lastFrame;
			lastFrame = currentFrame;
			System.out.printf("FPS: %.2f\n", 1/deltaTime); // Print FPS
			
			
			shaderProgram.SendMatricesToShader(camera, transform);
			shaderProgram.Use();
			renderer.Render();

			// Gets called when key is pressed
			GLFW.glfwSetKeyCallback(window, (win, key, scancode, action, mods) -> {
				inputHandler.Call(key, action, scancode, camera);
			});
			// Gets called when mouse moves
			GLFW.glfwSetCursorPosCallback(window, (win, xpos, ypos) -> {
				if (currentState == GAME_STATE.PLAY) {
					float xOffset = (float) xpos - lastX ;
					float yOffset = lastY - (float) ypos; // Reversed since y-coordinates go from bottom to top
					lastX = (float) xpos;
					lastY = (float) ypos;
					camera.ProcessMouse(xOffset, yOffset, true);
				}
			});
			
			camera.UpdateCameraPosition();
			
			// Gets called when window is resized
			GLFW.glfwSetFramebufferSizeCallback(window, (win, nwidth, nheight) -> {
				GL30.glViewport(0, 0, nwidth, nheight);
				shaderProgram.UpdateAspectRatio(nwidth, nheight);
			});
			
			// Upon changing game state, change input mode of cursor
			if (currentState == GAME_STATE.PLAY && GLFW.glfwGetInputMode(window, GLFW.GLFW_CURSOR) == GLFW.GLFW_CURSOR_NORMAL)
				GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
			if (currentState == GAME_STATE.MENU && GLFW.glfwGetInputMode(window, GLFW.GLFW_CURSOR) == GLFW.GLFW_CURSOR_DISABLED)
				GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
			
			GLFW.glfwSwapBuffers(window);
			GLFW.glfwPollEvents();
		}
		
		renderer.CleanUp();
		shaderProgram.Delete();
		GLFW.glfwDestroyWindow(window);
		GLFW.glfwTerminate();
		
		System.out.println("Closing Craftmine...");
	}
	
	public static void UpdateGameState(GAME_STATE newState) { // Takes care of game state changes (ie cursor visibility, camera mobility, etc)
		//System.out.println("Updating game state to: " + newState);
		currentState = newState;
	}

}
