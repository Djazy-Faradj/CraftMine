import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

public class InputHandler {
	public void callKeyboard(int key, int action, int scancode, Player player) {
		switch (AppMain.currentGameState) {
		case MENU: // Input handler when in "menu state"
			if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_PRESS) {
				AppMain.changeGameState(AppMain.GAME_STATE.PLAY);
				break;
			}
		default: // Input handler when in "play state" (default)
			if (key == GLFW.GLFW_KEY_W) {																			 	// W
				if (action == GLFW.GLFW_PRESS) {
					player.getCamera().processKeyboard(player.getVelocity());
					player.getCamera().direction = player.getCamera().direction.add(new Vector3f(0.0f, 0.0f, 1.0f));
				}
				if (action == GLFW.GLFW_RELEASE)
					player.getCamera().direction = player.getCamera().direction.add(new Vector3f(0.0f, 0.0f, -1.0f));
			}
			if (key == GLFW.GLFW_KEY_S) {																				// S
				if (action == GLFW.GLFW_PRESS) {
					player.getCamera().processKeyboard(player.getVelocity());
					player.getCamera().direction = player.getCamera().direction.add(new Vector3f(0.0f, 0.0f, -1.0f));
				}
				if (action == GLFW.GLFW_RELEASE)
					player.getCamera().direction = player.getCamera().direction.add(new Vector3f(0.0f, 0.0f, 1.0f));
			}
			if (key == GLFW.GLFW_KEY_A) {																				// A
				if (action == GLFW.GLFW_PRESS) {
					player.getCamera().processKeyboard(player.getVelocity());
					player.getCamera().direction = player.getCamera().direction.add(new Vector3f(-1.0f, 0.0f, 0.0f));
				}
				if (action == GLFW.GLFW_RELEASE)
					player.getCamera().direction = player.getCamera().direction.add(new Vector3f(1.0f, 0.0f, 0.0f));
			}
			if (key == GLFW.GLFW_KEY_D) {																				// D
				if (action == GLFW.GLFW_PRESS) {
					player.getCamera().processKeyboard(player.getVelocity());
					player.getCamera().direction = player.getCamera().direction.add(new Vector3f(1.0f, 0.0f, 0.0f));
				}
				if (action == GLFW.GLFW_RELEASE)
					player.getCamera().direction = player.getCamera().direction.add(new Vector3f(-1.0f, 0.0f, 0.0f));
			}
			if (key == GLFW.GLFW_KEY_SPACE) {																			// SPACE
				if (action == GLFW.GLFW_PRESS) {
					if (player.inAir == false) {
						if (player.getPlayerState() != Player.PLAYER_STATE.CROUCHING)
							player.getCamera().setCameraVerticalSpeed(Settings.JUMP_VEL);
						else
							player.toggleCrouching();
					}
				}
			}
			if (key == GLFW.GLFW_KEY_LEFT_SHIFT) {																		// SHIFT_L
				if (action == GLFW.GLFW_PRESS) {
					if (player.inAir == false && !player.getCamera().getDirection().equals(new Vector3f())) {
						player.toggleRunning();
					}
				}
			}
			if (key == GLFW.GLFW_KEY_LEFT_CONTROL) {																	// CTRL_L
				if (action == GLFW.GLFW_PRESS) {
					if (player.inAir == false) {
						player.toggleCrouching();
					}
				}
			}
			if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_PRESS)
				AppMain.changeGameState(AppMain.GAME_STATE.MENU);
			
			// DEBUG INPUTS
			if (key == GLFW.GLFW_KEY_R && action == GLFW.GLFW_PRESS)
				player.getCamera().setPosition(new Vector3f(0.0f, 4.0f, 0.0f));
		}
	}

	public void callMouseButton(int button, int action, Player player) { // Handles mouse input
		if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
			System.out.println("asdf");
			if (player.inAir == false && player.getCurrentHightlightedBlock() != null) {
				player.getCurrentHightlightedBlock().destroy();
			}
		}
	}
}
