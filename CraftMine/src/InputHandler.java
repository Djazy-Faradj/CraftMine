import org.lwjgl.glfw.GLFW;

public class InputHandler {
	public void Call(int key, int action, int scancode, Transform transform) {
		switch (AppMain.currentState) {
		case MENU: // Input handler when in "menu state"
			break;
		case PLAY: // Input handler when in "play state"
			switch (key) {
			case GLFW.GLFW_KEY_W:
				System.out.println("test");
				transform.Translate(0.0f, 0.02f, 0.0f);
				break;
			case GLFW.GLFW_KEY_S:
				transform.Translate(0.0f, -0.02f, 0.0f);
				break;
			case GLFW.GLFW_KEY_R:
				transform.Reset();
				break;
			}
		}
	}
}
