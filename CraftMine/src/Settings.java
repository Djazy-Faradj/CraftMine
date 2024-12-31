import org.joml.Vector3f;

public class Settings {
	public static final int 	WINDOW_WIDTH = 1000, 
								WINDOW_HEIGHT = 650;
	
	public static float 		
						 		CAMERA_SENSITIVITY = 0.15f,
						 		CAMERA_FOV = 90.0f,
						 		CAMERA_ZNEAR = 0.1f,
						 		CAMERA_ZFAR = 100.0f;
	
	public static float 		PLAYER_VELOCITY = 2.80f; // Default velocity for player walking state (Running: *=2.0f, Crouching: *=0.6f)
	
	public static final float 	FPS_LIMIT = 120.0f;
	
}
