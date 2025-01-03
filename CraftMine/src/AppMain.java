// Djazy Faradj
// Last Updated: 2025-01-03
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL30;

import org.joml.Vector3f;

public class AppMain {

	public static enum GAME_STATE {
		PLAY,
		MENU
	}
	
	public static GAME_STATE currentGameState;

	// Game timer
	public static float lastFrame = 0.0f, deltaTime = 0.0f, gdeltaTime = 0.0f, kdeltaTime = 0.0f;

	// Used for cursor movement
	private static float lastX = 0.0f, lastY = 0.0f, xOffset = 0.0f, yOffset = 0.0f;
	
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
		Renderer renderer = new Renderer();
		Transform transform = new Transform();
		InputHandler inputHandler = new InputHandler();
		
		// Get initial framebuffer size
		int[] width = new int[1];
		int[] height = new int[1];
		GLFW.glfwGetFramebufferSize(window, width, height);
		
		
		// Prevents screen from being black upon opening program
		shaderProgram.use();
		shaderProgram.updateAspectRatio(Settings.WINDOW_WIDTH, Settings.WINDOW_HEIGHT);
		
		// Set initial game state to "play"
		changeGameState(GAME_STATE.PLAY); 
		
		// Instantiate player
		Player p1 = new Player(new Vector3f(0.0f, 0.0f, 0.0f));
		
		// TEST (Instantiate a block) ***********
		Block[] blocks = new Block[256];
		int k = 0;
		for (float i = -8.0f; i < 8.0f; i++) {
			for(float j = -8.0f; j < 8.0f; j++) {
				for(float l = -1.0f; l < 0.0f; l++, k++) {
				Block b = new Block(new Vector3f(i, l, j), 0);
				//System.out.println(k);
				blocks[k] = b;
				}
			}
			//System.out.println(k);
		}
		
		DynamicHitbox dhb = new DynamicHitbox(new Vector3f(0.5f, 0.0f, 0.0f));
		// **************************************
		
		// Gets called when mouse moves
		GLFW.glfwSetCursorPosCallback(window, (win, xpos, ypos) -> {
			xOffset += (float) xpos - lastX ;
			yOffset += lastY - (float) ypos; // Reversed since y-coordinates go from bottom to top
			lastX = (float) xpos;
			lastY = (float) ypos;
		});
		
		// Gets called when window is resized
		GLFW.glfwSetFramebufferSizeCallback(window, (win, nwidth, nheight) -> {
			GL30.glViewport(0, 0, nwidth, nheight);
			shaderProgram.updateAspectRatio(nwidth, nheight);
		});

		// Gets called when key is pressed
		GLFW.glfwSetKeyCallback(window, (win, key, scancode, action, mods) -> {
			inputHandler.call(key, action, scancode, p1);
		});
		
		float lastFrame = (float) GLFW.glfwGetTime();
		
		Block lastHighlightedBlock = null;
		
		// Program loop
		while (!GLFW.glfwWindowShouldClose(window)) {
			GL30.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);

			
			// per-frame timing
			float currentFrame = (float) GLFW.glfwGetTime();
			deltaTime = currentFrame - lastFrame;
			lastFrame = currentFrame;
			
			gdeltaTime += deltaTime;
			
			
			if (gdeltaTime >= 1.0f / Settings.FPS_LIMIT) { // Gets executed once per frame, limited by fps
				if (currentGameState == GAME_STATE.PLAY) {
					kdeltaTime = gdeltaTime;
					p1.getCamera().processMouse(xOffset, yOffset, true);
					xOffset = 0.0f;
					yOffset = 0.0f;


					// Update all player at each frame
					p1.updatePlayer();
					Block currentHighlightedBlock = p1.getCamera().scanForBlock(blocks);
					
					if (currentHighlightedBlock != null) {
						if(currentHighlightedBlock != lastHighlightedBlock ) {
							if (lastHighlightedBlock != null)
								lastHighlightedBlock.isHighlighted = false;
							if (Block.highlightBlock != null)
								Block.highlightBlock.destroy();
							currentHighlightedBlock.highlightBlock();
							lastHighlightedBlock = currentHighlightedBlock;
						}
					}
					else {	
						if(lastHighlightedBlock != null)
							lastHighlightedBlock.isHighlighted = false;
						lastHighlightedBlock = null;
						if (Block.highlightBlock != null) {
							Block.highlightBlock.destroy();
							Block.highlightBlock = null;
						}
					}
				}
				// Upon changing game state, change input mode of cursor
				if (currentGameState == GAME_STATE.PLAY && GLFW.glfwGetInputMode(window, GLFW.GLFW_CURSOR) == GLFW.GLFW_CURSOR_NORMAL)
					GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
				if (currentGameState == GAME_STATE.MENU && GLFW.glfwGetInputMode(window, GLFW.GLFW_CURSOR) == GLFW.GLFW_CURSOR_DISABLED)
					GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
				

				gdeltaTime = 0;
			}
			
			shaderProgram.sendMatricesToShader(p1.getCamera(), transform);
			shaderProgram.use();
			renderer.render();
			
			GLFW.glfwSwapBuffers(window);
			GLFW.glfwPollEvents();
		}
		
		renderer.cleanUp();
		shaderProgram.delete();
		GLFW.glfwDestroyWindow(window);
		GLFW.glfwTerminate();
		
		System.out.println("Closing Craftmine...");
	}
	
	public static void changeGameState(GAME_STATE newState) { // Takes care of game state changes (ie cursor visibility, camera mobility, etc)
		currentGameState = newState;
		System.out.println(newState);
	}

}
