
public class Settings {
	public static final int 	WINDOW_WIDTH = 800, 
								WINDOW_HEIGHT = 600;
	
	private static int xOffset = 3, yOffset = 0;
	
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
