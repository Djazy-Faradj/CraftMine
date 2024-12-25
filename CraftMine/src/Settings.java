
public class Settings {
	public static final int 	WINDOW_WIDTH = 800, 
								WINDOW_HEIGHT = 600;
	
	public static float 	CAMERA_VELOCITY = 95.0f,
						 	CAMERA_SENSITIVITY = 1.0f,
							CAMERA_FOV = 90.0f,
							CAMERA_ZNEAR = 0.1f,
							CAMERA_ZFAR = 100.0f;
	
	private static int xOffset = 3, yOffset = 0; // used for debugging and choosing square texture (to be deleted)
	
	
	public static float[] vertices = {
			// SQUARE COMPOSED OF TWO TRIANGLES
			// Positions (x, y, z) 		// Texture Coordinate
		   -0.5f,  0.5f, 0.0f,	 		0.0f+0.041666f*xOffset, 1.0f-0.02941176f*yOffset, // Top
		   -0.5f, -0.5f, 0.0f,			0.0f+0.041666f*xOffset, 0.97058823f-0.02941176f*yOffset, // Bottom-left
			0.5f, -0.5f, 0.0f,			0.041666f+0.041666f*xOffset, 0.97058823f-0.02941176f*yOffset, // Bottom-right
			
			// Positions 				// Texture Coordinate
		   -0.5f,  0.5f, 0.0f, 			0.0f+0.041666f*xOffset, 1.0f-0.02941176f*yOffset, // Top
			0.5f, -0.5f, 0.0f,			0.041666f+0.041666f*xOffset, 0.97058823f-0.02941176f*yOffset, // Bottom-left
			0.5f,  0.5f, 0.0f,			0.041666f+0.041666f*xOffset, 1.0f-0.02941176f*yOffset // Bottom-right
	};
}
