import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class InputHandler {
	public void call(int key, int action, int scancode, Player player) {
		switch (AppMain.currentGameState) {
		case MENU: // Input handler when in "menu state"
			if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_PRESS) {
				AppMain.changeGameState(AppMain.GAME_STATE.PLAY);
			System.out.println(player);}
			break;
		case PLAY: // Input handler when in "play state"
			if (key == GLFW.GLFW_KEY_W) {
				if (action == GLFW.GLFW_PRESS) {
					player.getCamera().processKeyboard(player.getVelocity());
					player.getCamera().direction = player.getCamera().direction.add(new Vector3f(0.0f, 0.0f, 1.0f));
				}
				if (action == GLFW.GLFW_RELEASE)
					player.getCamera().direction = player.getCamera().direction.add(new Vector3f(0.0f, 0.0f, -1.0f));
			}
			if (key == GLFW.GLFW_KEY_S) {
				if (action == GLFW.GLFW_PRESS) {
					player.getCamera().processKeyboard(player.getVelocity());
					player.getCamera().direction = player.getCamera().direction.add(new Vector3f(0.0f, 0.0f, -1.0f));
				}
				if (action == GLFW.GLFW_RELEASE)
					player.getCamera().direction = player.getCamera().direction.add(new Vector3f(0.0f, 0.0f, 1.0f));
			}
			if (key == GLFW.GLFW_KEY_A) {
				if (action == GLFW.GLFW_PRESS) {
					player.getCamera().processKeyboard(player.getVelocity());
					player.getCamera().direction = player.getCamera().direction.add(new Vector3f(-1.0f, 0.0f, 0.0f));
				}
				if (action == GLFW.GLFW_RELEASE)
					player.getCamera().direction = player.getCamera().direction.add(new Vector3f(1.0f, 0.0f, 0.0f));
			}
			if (key == GLFW.GLFW_KEY_D) {
				if (action == GLFW.GLFW_PRESS) {
					player.getCamera().processKeyboard(player.getVelocity());
					player.getCamera().direction = player.getCamera().direction.add(new Vector3f(1.0f, 0.0f, 0.0f));
				}
				if (action == GLFW.GLFW_RELEASE)
					player.getCamera().direction = player.getCamera().direction.add(new Vector3f(-1.0f, 0.0f, 0.0f));
			}
			if (key == GLFW.GLFW_KEY_SPACE) {
				if (action == GLFW.GLFW_PRESS) {
					if (player.inAir == false) {
						player.getCamera().setCameraVerticalSpeed(0.08f);
					}
				}
			}
			
			
			if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_PRESS)
				AppMain.changeGameState(AppMain.GAME_STATE.MENU);
			break;
		}
	}
}
