
public class Settings {
	public static final int 	WindowWidth = 800, 
								WindowHeight = 600;
	
	public static float[] vertices = {
			// SQUARE COMPOSED OF TWO TRIANGLES
			// Positions (x, y, z) 		// Texture Coordinate
		   -0.5f,  0.5f, 0.0f,	 		0.0f, 1.0f, // Top
		   -0.5f, -0.5f, 0.0f,			0.0f, 0.0f, // Bottom-left
			0.5f, -0.5f, 0.0f,			1.0f, 0.0f, // Bottom-right
			
			// Positions 				// Texture Coordinate
		   -0.5f,  0.5f, 0.0f, 			0.0f, 1.0f, // Top
			0.5f, -0.5f, 0.0f,			1.0f, 0.0f, // Bottom-left
			0.5f,  0.5f, 0.0f,			1.0f, 1.0f // Bottom-right
	};
}
