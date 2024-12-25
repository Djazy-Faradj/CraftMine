import org.lwjgl.glfw.GLFW;
import org.joml.Vector3f;

public class InputHandler {
	public void Call(int key, int action, int scancode, Camera camera) {
		switch (AppMain.currentState) {
		case MENU: // Input handler when in "menu state"
			break;
		case PLAY: // Input handler when in "play state"
			if (key == GLFW.GLFW_KEY_W)
				camera.ProcessKeyboard(new Vector3f(0.0f, 0.0f, -1.0f), AppMain.deltaTime);
			if (key == GLFW.GLFW_KEY_S)
				camera.ProcessKeyboard(new Vector3f(0.0f, 0.0f, 1.0f), AppMain.deltaTime);
			if (key == GLFW.GLFW_KEY_A)
				camera.ProcessKeyboard(new Vector3f(-1.0f, 0.0f, 0.0f), AppMain.deltaTime);
			if (key == GLFW.GLFW_KEY_D)
				camera.ProcessKeyboard(new Vector3f(1.0f, 0.0f, 0.0f), AppMain.deltaTime);
		}
	}
}
