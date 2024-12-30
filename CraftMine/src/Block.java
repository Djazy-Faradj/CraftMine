import org.joml.Vector3f;
import org.lwjgl.opengl.GL30;

//	BLOCK TYPES
//		0: GRASSY_DIRT_BLOCK
//		1: DIRT_BLOCK
//		2: GRASS_BLOCK
//		3: COBBLESTONE_BLOCK

public class Block {
	private int type;
	private Vector3f position;
	private float health;
	
	private static float xOffset_value = 0.041666f, yOffset_value = 0.02941176f; // will need to calculate it automatically later
	private float[] vertices;
	private int xOffset_top, yOffset_top, xOffset_bottom, yOffset_bottom, xOffset_sides, yOffset_sides;	
	
	public Block (float xPos, float yPos, float zPos, int type) {
		this.position = new Vector3f(xPos, yPos, zPos);
		switch (type) {
		case 0:
			Grassy_Dirt_Block();
			break;
		case 1:
			Dirt_Block();
			break;
		case 2: 
			Grassy_Block();
			break;
		case 3:
			Cobblestone_Block();
			break;
		default: 
			Grassy_Block();
			break;
		}
	}
	
	public void damage(float value) {
		this.health -= value;
	}
	
	// MUTATORS
	public void setHealth(float health) {
		this.health = health;
	}
	
	public void setTypeValue(int type) {
		this.type = type;
	}
	
	public void setPosition(float x, float y, float z) {
		this.position.x = x;
		this.position.y = y;
		this.position.z = z;
	}
	
	// ACCESSORS
	public float getHealth() {
		return this.health;
	}
	
	public int getTypeValue() {
		return this.type;
	}
	
	public Vector3f getPosition() {
		return this.position;
	}
	
	private void Grassy_Dirt_Block() { // Type = 0
		// Maps to the appropriate texture
		this.xOffset_top = 0;
		this.yOffset_top = 0;
		this.xOffset_bottom = 2;
		this.yOffset_bottom = 0;
		this.xOffset_sides = 3;
		this.yOffset_sides = 0;
		generateVertices();
		
		this.health = 10.0f;
	}	
	private void Dirt_Block() { // Type = 1
		// Maps to the appropriate texture
		this.xOffset_top = 2;
		this.yOffset_top = 0;
		this.xOffset_bottom = 2;
		this.yOffset_bottom = 0;
		this.xOffset_sides = 2;
		this.yOffset_sides = 0;
		generateVertices();
		
		this.health = 10.0f;
	}
	private void Grassy_Block() { // Type = 2
		// Maps to the appropriate texture
		this.xOffset_top = 0;
		this.yOffset_top = 0;
		this.xOffset_bottom = 0;
		this.yOffset_bottom = 0;
		this.xOffset_sides = 0;
		this.yOffset_sides = 0;
		generateVertices();
		
		this.health = 10.0f;
	}
	private void Cobblestone_Block() { // Type = 3
		// Maps to the appropriate texture
		this.xOffset_top = 0;
		this.yOffset_top = 1;
		this.xOffset_bottom = 0;
		this.yOffset_bottom = 1;
		this.xOffset_sides = 0;
		this.yOffset_sides = 1;
		generateVertices();
		
		this.health = 30.0f;
	}

	void generateVertices() {
		float[] vertices = {
				// // FRONT SQUARE 
					// Positions (x, y, z) 		// Texture Coordinate
				   position.x-0.5f,  position.y+0.5f, position.z+0.5f,	 		0.0f+xOffset_value*xOffset_sides, 1.0f-yOffset_value*yOffset_sides, // Top
				   position.x-0.5f,  position.y-0.5f, position.z+0.5f,			0.0f+xOffset_value*xOffset_sides, 0.97058823f-yOffset_value*yOffset_sides, // Bottom-left
				   position.x+0.5f,  position.y-0.5f, position.z+0.5f,			0.041666f+xOffset_value*xOffset_sides, 0.97058823f-yOffset_value*yOffset_sides, // Bottom-right
					
					// Positions 				// Texture Coordinate
				   position.x-0.5f,  position.y+0.5f, position.z+0.5f, 			0.0f+xOffset_value*xOffset_sides, 1.0f-yOffset_value*yOffset_sides, // Top
				   position.x+0.5f,  position.y-0.5f, position.z+0.5f,			0.041666f+xOffset_value*xOffset_sides, 0.97058823f-yOffset_value*yOffset_sides, // Bottom-left
				   position.x+0.5f,  position.y+0.5f, position.z+0.5f,			0.041666f+xOffset_value*xOffset_sides, 1.0f-yOffset_value*yOffset_sides, // Bottom-right

				// // BACK SQUARE 
					// Positions (x, y, z) 		// Texture Coordinate
				   position.x+0.5f,  position.y-0.5f, position.z-0.5f,	 		0.041666f+xOffset_value*xOffset_sides, 0.97058823f-yOffset_value*yOffset_sides, // Top
				   position.x-0.5f,  position.y-0.5f, position.z-0.5f,			0.0f+xOffset_value*xOffset_sides, 0.97058823f-yOffset_value*yOffset_sides, // Bottom-left
				   position.x-0.5f,  position.y+0.5f, position.z-0.5f,			0.0f+xOffset_value*xOffset_sides, 1.0f-yOffset_value*yOffset_sides, // Bottom-right
					
					// Positions 				// Texture Coordinate
				   position.x+0.5f,  position.y+0.5f, position.z-0.5f, 			0.041666f+xOffset_value*xOffset_sides, 1.0f-yOffset_value*yOffset_sides, // Top
				   position.x+0.5f,  position.y-0.5f, position.z-0.5f,			0.041666f+xOffset_value*xOffset_sides, 0.97058823f-yOffset_value*yOffset_sides, // Bottom-left
				   position.x-0.5f,  position.y+0.5f, position.z-0.5f,			0.0f+xOffset_value*xOffset_sides, 1.0f-yOffset_value*yOffset_sides, // Bottom-right
					
				// RIGHT SQUARE
					// Positions (x, y, z) 		// Texture Coordinate
				   position.x+0.5f,  position.y-0.5f, position.z+0.5f,	 		0.041666f+xOffset_value*xOffset_sides, 0.97058823f-yOffset_value*yOffset_sides, // Top
				   position.x+0.5f,  position.y-0.5f, position.z-0.5f,			0.0f+xOffset_value*xOffset_sides, 0.97058823f-yOffset_value*yOffset_sides, // Bottom-left
				   position.x+0.5f,  position.y+0.5f, position.z-0.5f,			0.0f+xOffset_value*xOffset_sides, 1.0f-yOffset_value*yOffset_sides, // Bottom-right
				   
					// Positions 				// Texture Coordinate
				   position.x+0.5f,  position.y+0.5f, position.z+0.5f,			0.041666f+xOffset_value*xOffset_sides, 1.0f-yOffset_value*yOffset_sides, // Top
				   position.x+0.5f,  position.y-0.5f, position.z+0.5f,			0.041666f+xOffset_value*xOffset_sides, 0.97058823f-yOffset_value*yOffset_sides, // Bottom-left
				   position.x+0.5f,  position.y+0.5f, position.z-0.5f,			0.0f+xOffset_value*xOffset_sides, 1.0f-yOffset_value*yOffset_sides, // Bottom-right

				// LEFT SQUARE
					// Positions (x, y, z) 		// Texture Coordinate
				   position.x-0.5f,  position.y+0.5f, position.z-0.5f,	 		0.0f+xOffset_value*xOffset_sides, 1.0f-yOffset_value*yOffset_sides, // Top
				   position.x-0.5f,  position.y-0.5f, position.z-0.5f,			0.0f+xOffset_value*xOffset_sides, 0.97058823f-yOffset_value*yOffset_sides, // Bottom-left
				   position.x-0.5f,  position.y-0.5f, position.z+0.5f,			0.041666f+xOffset_value*xOffset_sides, 0.97058823f-yOffset_value*yOffset_sides, // Bottom-right
					
					// Positions 				// Texture Coordinate
				   position.x-0.5f,  position.y+0.5f, position.z-0.5f, 			0.0f+xOffset_value*xOffset_sides, 1.0f-yOffset_value*yOffset_sides, // Top
				   position.x-0.5f,  position.y-0.5f, position.z+0.5f,			0.041666f+xOffset_value*xOffset_sides, 0.97058823f-yOffset_value*yOffset_sides, // Bottom-left
				   position.x-0.5f,  position.y+0.5f, position.z+0.5f,			0.041666f+xOffset_value*xOffset_sides, 1.0f-yOffset_value*yOffset_sides, // Bottom-right

				// TOP SQUARE
					// Positions (x, y, z) 		// Texture Coordinate
				    position.x+0.5f,  position.y+0.5f, position.z-0.5f,	 		0.0f+xOffset_value*xOffset_top, 1.0f-yOffset_value*yOffset_top, // Top
				    position.x-0.5f,  position.y+0.5f, position.z-0.5f,			0.0f+xOffset_value*xOffset_top, 0.97058823f-yOffset_value*yOffset_top, // Bottom-left
				    position.x-0.5f,  position.y+0.5f, position.z+0.5f,			0.041666f+xOffset_value*xOffset_top, 0.97058823f-yOffset_value*yOffset_top, // Bottom-right
					
					// Positions 				// Texture Coordinate
				    position.x+0.5f,  position.y+0.5f, position.z-0.5f, 			0.0f+xOffset_value*xOffset_top, 1.0f-yOffset_value*yOffset_top, // Top
				    position.x-0.5f,  position.y+0.5f, position.z+0.5f,			0.041666f+xOffset_value*xOffset_top, 0.97058823f-yOffset_value*yOffset_top, // Bottom-left
				    position.x+0.5f,  position.y+0.5f, position.z+0.5f,			0.041666f+xOffset_value*xOffset_top, 1.0f-yOffset_value*yOffset_top, // Bottom-right
				    
				// BOTTOM SQUARE
					// Positions (x, y, z) 		// Texture Coordinate
				    position.x-0.5f,  position.y-0.5f, position.z+0.5f,	 		0.041666f+xOffset_value*xOffset_bottom, 0.97058823f-yOffset_value*yOffset_bottom, // Top
				    position.x-0.5f,  position.y-0.5f, position.z-0.5f,			0.0f+xOffset_value*xOffset_bottom, 0.97058823f-yOffset_value*yOffset_bottom, // Bottom-left
				    position.x+0.5f,  position.y-0.5f, position.z-0.5f,			0.0f+xOffset_value*xOffset_bottom, 1.0f-yOffset_value*yOffset_bottom, // Bottom-right
					
					// Positions 				// Texture Coordinate
				    position.x+0.5f,  position.y-0.5f, position.z+0.5f, 		0.041666f+xOffset_value*xOffset_bottom, 1.0f-yOffset_value*yOffset_bottom, // Top
				    position.x-0.5f,  position.y-0.5f, position.z+0.5f,			0.041666f+xOffset_value*xOffset_bottom, 0.97058823f-yOffset_value*yOffset_bottom, // Bottom-left
				    position.x+0.5f,  position.y-0.5f, position.z-0.5f,			0.0f+xOffset_value*xOffset_bottom, 1.0f-yOffset_value*yOffset_bottom // Bottom-right
			};
		this.vertices = vertices;
	}
	
	void sendVerticesToBuffer() {
		GL30.glBufferData(GL30.GL_ARRAY_BUFFER, vertices, GL30.GL_STATIC_DRAW);
	}
}
