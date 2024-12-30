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
	
	private static float yOffset_text = 0.02941176f; // used for debugging and choosing square texture (to be deleted)
	
	private static Vector3f position = new Vector3f(0.0f, 0.0f, 0.0f);
	
	public static float[] vertices = {
		// // FRONT SQUARE 
			// Positions (x, y, z) 		// Texture Coordinate
		   position.x-0.5f,  position.y+0.5f, position.z+0.5f,	 		0.0f, 1.0f, // Top
		   position.x-0.5f,  position.y-0.5f, position.z+0.5f,			0.0f, 0.97058823f, // Bottom-left
		   position.x+0.5f,  position.y-0.5f, position.z+0.5f,			0.041666f, 0.97058823f, // Bottom-right
			
			// Positions 				// Texture Coordinate
		   position.x-0.5f,  position.y+0.5f, position.z+0.5f, 			0.0f, 1.0f, // Top
		   position.x+0.5f,  position.y-0.5f, position.z+0.5f,			0.041666f, 0.97058823f, // Bottom-left
		   position.x+0.5f,  position.y+0.5f, position.z+0.5f,			0.041666f, 1.0f, // Bottom-right

		// // BACK SQUARE 
			// Positions (x, y, z) 		// Texture Coordinate
		   position.x+0.5f,  position.y-0.5f, position.z-0.5f,	 		0.041666f, 0.97058823f-yOffset_text, // Top
		   position.x-0.5f,  position.y-0.5f, position.z-0.5f,			0.0f, 0.97058823f-yOffset_text, // Bottom-left
		   position.x-0.5f,  position.y+0.5f, position.z-0.5f,			0.0f, 1.0f-yOffset_text, // Bottom-right
			
			// Positions 				// Texture Coordinate
		   position.x+0.5f,  position.y+0.5f, position.z-0.5f, 			0.041666f, 1.0f-yOffset_text, // Top
		   position.x+0.5f,  position.y-0.5f, position.z-0.5f,			0.041666f, 0.97058823f-yOffset_text, // Bottom-left
		   position.x-0.5f,  position.y+0.5f, position.z-0.5f,			0.0f, 1.0f-yOffset_text, // Bottom-right
			
		// RIGHT SQUARE
			// Positions (x, y, z) 		// Texture Coordinate
		   position.x+0.5f,  position.y-0.5f, position.z+0.5f,	 		0.041666f, 0.97058823f-yOffset_text*2, // Top
		   position.x+0.5f,  position.y-0.5f, position.z-0.5f,			0.0f, 0.97058823f-yOffset_text*2, // Bottom-left
		   position.x+0.5f,  position.y+0.5f, position.z-0.5f,			0.0f, 1.0f-yOffset_text*2, // Bottom-right
		   
			// Positions 				// Texture Coordinate
		   position.x+0.5f,  position.y+0.5f, position.z+0.5f,			0.041666f, 1.0f-yOffset_text*2, // Top
		   position.x+0.5f,  position.y-0.5f, position.z+0.5f,			0.041666f, 0.97058823f-yOffset_text*2, // Bottom-left
		   position.x+0.5f,  position.y+0.5f, position.z-0.5f,			0.0f, 1.0f-yOffset_text*2, // Bottom-right

		// LEFT SQUARE
			// Positions (x, y, z) 		// Texture Coordinate
		   position.x-0.5f,  position.y+0.5f, position.z-0.5f,	 		0.0f, 1.0f-yOffset_text*2, // Top
		   position.x-0.5f,  position.y-0.5f, position.z-0.5f,			0.0f, 0.97058823f-yOffset_text*2, // Bottom-left
		   position.x-0.5f,  position.y-0.5f, position.z+0.5f,			0.041666f, 0.97058823f-yOffset_text*2, // Bottom-right
			
			// Positions 				// Texture Coordinate
		   position.x-0.5f,  position.y+0.5f, position.z-0.5f, 			0.0f, 1.0f-yOffset_text*2, // Top
		   position.x-0.5f,  position.y-0.5f, position.z+0.5f,			0.041666f, 0.97058823f-yOffset_text*2, // Bottom-left
		   position.x-0.5f,  position.y+0.5f, position.z+0.5f,			0.041666f, 1.0f-yOffset_text*2, // Bottom-right

		// TOP SQUARE
			// Positions (x, y, z) 		// Texture Coordinate
		    position.x+0.5f,  position.y+0.5f, position.z-0.5f,	 		0.0f, 1.0f-yOffset_text*3, // Top
		    position.x-0.5f,  position.y+0.5f, position.z-0.5f,			0.0f, 0.97058823f-yOffset_text*3, // Bottom-left
		    position.x-0.5f,  position.y+0.5f, position.z+0.5f,			0.041666f, 0.97058823f-yOffset_text*3, // Bottom-right
			
			// Positions 				// Texture Coordinate
		    position.x+0.5f,  position.y+0.5f, position.z-0.5f, 			0.0f, 1.0f-yOffset_text*3, // Top
		    position.x-0.5f,  position.y+0.5f, position.z+0.5f,			0.041666f, 0.97058823f-yOffset_text*3, // Bottom-left
		    position.x+0.5f,  position.y+0.5f, position.z+0.5f,			0.041666f, 1.0f-yOffset_text*3, // Bottom-right
		    
		// BOTTOM SQUARE
			// Positions (x, y, z) 		// Texture Coordinate
		    position.x-0.5f,  position.y-0.5f, position.z+0.5f,	 		0.041666f, 0.97058823f-yOffset_text*3, // Top
		    position.x-0.5f,  position.y-0.5f, position.z-0.5f,			0.0f, 0.97058823f-yOffset_text*3, // Bottom-left
		    position.x+0.5f,  position.y-0.5f, position.z-0.5f,			0.0f, 1.0f-yOffset_text*3, // Bottom-right
			
			// Positions 				// Texture Coordinate
		    position.x+0.5f,  position.y-0.5f, position.z+0.5f, 		0.041666f, 1.0f-yOffset_text*3, // Top
		    position.x-0.5f,  position.y-0.5f, position.z+0.5f,			0.041666f, 0.97058823f-yOffset_text*3, // Bottom-left
		    position.x+0.5f,  position.y-0.5f, position.z-0.5f,			0.0f, 1.0f-yOffset_text*3 // Bottom-right
	};
}
