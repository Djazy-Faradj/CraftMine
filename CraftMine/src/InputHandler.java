import org.lwjgl.glfw.GLFW;

import org.joml.Vector3f;

public class InputHandler {
	public void Call(int key, int action, int scancode, Camera camera) {
		switch (AppMain.currentState) {
		case MENU: // Input handler when in "menu state"
			if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_PRESS)
				AppMain.UpdateGameState(AppMain.GAME_STATE.PLAY);
			break;
		case PLAY: // Input handler when in "play state"
			if (key == GLFW.GLFW_KEY_W) {
				if (action == GLFW.GLFW_PRESS)
					camera.direction.z -= 1.0f;
				camera.ProcessKeyboard();
					if (action == GLFW.GLFW_RELEASE) {
						camera.direction.z += 1.0f;
						camera.ProcessKeyboard();
					}
			}
			if (key == GLFW.GLFW_KEY_S) {
				if (action == GLFW.GLFW_PRESS)
					camera.direction.z += 1.0f;
				camera.ProcessKeyboard();
					if (action == GLFW.GLFW_RELEASE) {
						camera.direction.z -= 1.0f;
						camera.ProcessKeyboard();
					}
			}
			if (key == GLFW.GLFW_KEY_A) {
				if (action == GLFW.GLFW_PRESS)
					camera.direction.x -= 1.0f;
				camera.ProcessKeyboard();
					if (action == GLFW.GLFW_RELEASE) {
						camera.direction.x += 1.0f;
						camera.ProcessKeyboard();
					}
			}
			if (key == GLFW.GLFW_KEY_D) {
				if (action == GLFW.GLFW_PRESS)
					camera.direction.x += 1.0f;
				camera.ProcessKeyboard();
					if (action == GLFW.GLFW_RELEASE) {
						camera.direction.x -= 1.0f;
						camera.ProcessKeyboard();
					}
			}
			
			
			if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_PRESS)
				AppMain.UpdateGameState(AppMain.GAME_STATE.MENU);
			break;
		}
	}
}
