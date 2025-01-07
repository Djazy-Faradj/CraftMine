import org.joml.Vector3f;

//	BLOCK TYPES
//		0: GRASSY_DIRT_BLOCK
//		1: DIRT_BLOCK
//		2: GRASS_BLOCK
//		3: COBBLESTONE_BLOCK


public class Block {
	private static int idCount = 0;
	public static Block highlightBlock;
	public static Block[] instancedBlocks = {};
	
	private int id;
	private int type;
	private float sizeFactor = 1.0f;
	private Vector3f position;
	private float health;
	private boolean collisionEnabled = false;
	private StaticHitbox shb;
	
	public boolean isHighlighted = false;
	
	private static float xOffset_value = 0.0416666666666667f, yOffset_value = 0.0294117647058824f; // will need to calculate it automatically later
	private float[] vertices;
	private int xOffset_top, yOffset_top, xOffset_bottom, yOffset_bottom, xOffset_sides, yOffset_sides;	
	
	public Block (Vector3f position, int type) {
		this.id = idCount++;
		this.position = new Vector3f(position);
		switch (type) {
		case -1: 
			this.sizeFactor = 1.001f;
			Wireframe_Block();
			break;
		case 0:
			addInstanceToArray();
			Grassy_Dirt_Block();
			break;
		case 1:
			addInstanceToArray();
			Dirt_Block();
			break;
		case 2: 
			addInstanceToArray();
			Grassy_Block();
			break;
		case 3:
			addInstanceToArray();
			Cobblestone_Block();
			break;
		default: 
			addInstanceToArray();
			Grassy_Block();
			break;
		}
		if (this.collisionEnabled)
			this.shb = new StaticHitbox(new Vector3f(position.x, position.y-0.5f*sizeFactor, position.z), 0.5f*sizeFactor, 0.5f*sizeFactor, sizeFactor);
		sendVerticesToBuffer();
	}
	

	private void addInstanceToArray() {
		Block[] temp = new Block[instancedBlocks.length+1];
		System.arraycopy(instancedBlocks, 0, temp, 0, instancedBlocks.length);
		temp[instancedBlocks.length] = this;
		
		instancedBlocks = temp;
	}
	
	public void destroy() {
		if (this.type != -1) {
			for (int i = 0; i < instancedBlocks.length; i++) {
				if (instancedBlocks[i] == this) {
					Block[] temp = new Block[instancedBlocks.length-1];
					System.arraycopy(instancedBlocks, 0, temp, 0, i);
					System.arraycopy(instancedBlocks, i+1, temp, i, temp.length-i);
					
					instancedBlocks = temp;
					shb.destroy();
				}
			}
		}
		Renderer.deleteBlockVertices(this.vertices);
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
	
	public void setPosition(Vector3f newPos) {
		Renderer.deleteBlockVertices(this.vertices);
		this.position = newPos;
		generateVertices();
		Renderer.addVertices(this.vertices);
	}
	
	// ACCESSORS
	public float getHealth() {
		return this.health;
	}
	
	public int getTypeValue() {
		return this.type;
	}
	
	public int getId() {
		return this.id;
	}
	
	public Vector3f getPosition() {
		return this.position;
	}
	
	// INTERFACE
	public int isBlockAt(Vector3f position) { // Returns -1 if block is not here, else returns the block id
		return this.position.equals(position) && this.type != -1 ? this.id : -1; // If type = -1, its highlight block, if id = -1, its no block
	}
	private void Wireframe_Block() { // Type = -1
		// Maps to the appropriate texture
		this.xOffset_top = 19;
		this.yOffset_top = 33;
		this.xOffset_bottom = 19;
		this.yOffset_bottom = 33;
		this.xOffset_sides = 19;
		this.yOffset_sides = 33;
		generateVertices();
	}	
	
	private void Grassy_Dirt_Block() { // Type = 0
		// Maps to the appropriate texture
		this.xOffset_top = 0;
		this.yOffset_top = 0;
		this.xOffset_bottom = 2;
		this.yOffset_bottom = 0;
		this.xOffset_sides = 3;
		this.yOffset_sides = 0;
		this.collisionEnabled = true;
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
		this.collisionEnabled = true;
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
		this.collisionEnabled = true;
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
		this.collisionEnabled = true;
		generateVertices();
		
		this.health = 30.0f;
	}

	void generateVertices() {
		float[] vertices = {
				// // FRONT SQUARE 
					// Positions (x, y, z) 		// Texture Coordinate
				   position.x-0.5f*sizeFactor,  position.y+0.5f*sizeFactor, position.z+0.5f*sizeFactor,	 		0.0f+xOffset_value*xOffset_sides, 1.0f-yOffset_value*yOffset_sides, // Top
				   position.x-0.5f*sizeFactor,  position.y-0.5f*sizeFactor, position.z+0.5f*sizeFactor,			0.0f+xOffset_value*xOffset_sides, 0.97058823f-yOffset_value*yOffset_sides, // Bottom-left
				   position.x+0.5f*sizeFactor,  position.y-0.5f*sizeFactor, position.z+0.5f*sizeFactor,			0.041666f+xOffset_value*xOffset_sides, 0.97058823f-yOffset_value*yOffset_sides, // Bottom-right
					
					// Positions 				// Texture Coordinate
				   position.x-0.5f*sizeFactor,  position.y+0.5f*sizeFactor, position.z+0.5f*sizeFactor, 			0.0f+xOffset_value*xOffset_sides, 1.0f-yOffset_value*yOffset_sides, // Top
				   position.x+0.5f*sizeFactor,  position.y-0.5f*sizeFactor, position.z+0.5f*sizeFactor,			0.041666f+xOffset_value*xOffset_sides, 0.97058823f-yOffset_value*yOffset_sides, // Bottom-left
				   position.x+0.5f*sizeFactor,  position.y+0.5f*sizeFactor, position.z+0.5f*sizeFactor,			0.041666f+xOffset_value*xOffset_sides, 1.0f-yOffset_value*yOffset_sides, // Bottom-right

				// // BACK SQUARE 
					// Positions (x, y, z) 		// Texture Coordinate
				   position.x+0.5f*sizeFactor,  position.y-0.5f*sizeFactor, position.z-0.5f*sizeFactor,	 		0.041666f+xOffset_value*xOffset_sides, 0.97058823f-yOffset_value*yOffset_sides, // Top
				   position.x-0.5f*sizeFactor,  position.y-0.5f*sizeFactor, position.z-0.5f*sizeFactor,			0.0f+xOffset_value*xOffset_sides, 0.97058823f-yOffset_value*yOffset_sides, // Bottom-left
				   position.x-0.5f*sizeFactor,  position.y+0.5f*sizeFactor, position.z-0.5f*sizeFactor,			0.0f+xOffset_value*xOffset_sides, 1.0f-yOffset_value*yOffset_sides, // Bottom-right
					
					// Positions 				// Texture Coordinate
				   position.x+0.5f*sizeFactor,  position.y+0.5f*sizeFactor, position.z-0.5f*sizeFactor, 			0.041666f+xOffset_value*xOffset_sides, 1.0f-yOffset_value*yOffset_sides, // Top
				   position.x+0.5f*sizeFactor,  position.y-0.5f*sizeFactor, position.z-0.5f*sizeFactor,			0.041666f+xOffset_value*xOffset_sides, 0.97058823f-yOffset_value*yOffset_sides, // Bottom-left
				   position.x-0.5f*sizeFactor,  position.y+0.5f*sizeFactor, position.z-0.5f*sizeFactor,			0.0f+xOffset_value*xOffset_sides, 1.0f-yOffset_value*yOffset_sides, // Bottom-right
					
				// RIGHT SQUARE
					// Positions (x, y, z) 		// Texture Coordinate
				   position.x+0.5f*sizeFactor,  position.y-0.5f*sizeFactor, position.z+0.5f*sizeFactor,	 		0.041666f+xOffset_value*xOffset_sides, 0.97058823f-yOffset_value*yOffset_sides, // Top
				   position.x+0.5f*sizeFactor,  position.y-0.5f*sizeFactor, position.z-0.5f*sizeFactor,			0.0f+xOffset_value*xOffset_sides, 0.97058823f-yOffset_value*yOffset_sides, // Bottom-left
				   position.x+0.5f*sizeFactor,  position.y+0.5f*sizeFactor, position.z-0.5f*sizeFactor,			0.0f+xOffset_value*xOffset_sides, 1.0f-yOffset_value*yOffset_sides, // Bottom-right
				   
					// Positions 				// Texture Coordinate
				   position.x+0.5f*sizeFactor,  position.y+0.5f*sizeFactor, position.z+0.5f*sizeFactor,			0.041666f+xOffset_value*xOffset_sides, 1.0f-yOffset_value*yOffset_sides, // Top
				   position.x+0.5f*sizeFactor,  position.y-0.5f*sizeFactor, position.z+0.5f*sizeFactor,			0.041666f+xOffset_value*xOffset_sides, 0.97058823f-yOffset_value*yOffset_sides, // Bottom-left
				   position.x+0.5f*sizeFactor,  position.y+0.5f*sizeFactor, position.z-0.5f*sizeFactor,			0.0f+xOffset_value*xOffset_sides, 1.0f-yOffset_value*yOffset_sides, // Bottom-right

				// LEFT SQUARE
					// Positions (x, y, z) 		// Texture Coordinate
				   position.x-0.5f*sizeFactor,  position.y+0.5f*sizeFactor, position.z-0.5f*sizeFactor,	 		0.0f+xOffset_value*xOffset_sides, 1.0f-yOffset_value*yOffset_sides, // Top
				   position.x-0.5f*sizeFactor,  position.y-0.5f*sizeFactor, position.z-0.5f*sizeFactor,			0.0f+xOffset_value*xOffset_sides, 0.97058823f-yOffset_value*yOffset_sides, // Bottom-left
				   position.x-0.5f*sizeFactor,  position.y-0.5f*sizeFactor, position.z+0.5f*sizeFactor,			0.041666f+xOffset_value*xOffset_sides, 0.97058823f-yOffset_value*yOffset_sides, // Bottom-right
					
					// Positions 				// Texture Coordinate
				   position.x-0.5f*sizeFactor,  position.y+0.5f*sizeFactor, position.z-0.5f*sizeFactor, 			0.0f+xOffset_value*xOffset_sides, 1.0f-yOffset_value*yOffset_sides, // Top
				   position.x-0.5f*sizeFactor,  position.y-0.5f*sizeFactor, position.z+0.5f*sizeFactor,			0.041666f+xOffset_value*xOffset_sides, 0.97058823f-yOffset_value*yOffset_sides, // Bottom-left
				   position.x-0.5f*sizeFactor,  position.y+0.5f*sizeFactor, position.z+0.5f*sizeFactor,			0.041666f+xOffset_value*xOffset_sides, 1.0f-yOffset_value*yOffset_sides, // Bottom-right

				// TOP SQUARE
					// Positions (x, y, z) 		// Texture Coordinate
				    position.x+0.5f*sizeFactor,  position.y+0.5f*sizeFactor, position.z-0.5f*sizeFactor,	 		0.0f+xOffset_value*xOffset_top, 1.0f-yOffset_value*yOffset_top, // Top
				    position.x-0.5f*sizeFactor,  position.y+0.5f*sizeFactor, position.z-0.5f*sizeFactor,			0.0f+xOffset_value*xOffset_top, 0.97058823f-yOffset_value*yOffset_top, // Bottom-left
				    position.x-0.5f*sizeFactor,  position.y+0.5f*sizeFactor, position.z+0.5f*sizeFactor,			0.041666f+xOffset_value*xOffset_top, 0.97058823f-yOffset_value*yOffset_top, // Bottom-right
					
					// Positions 				// Texture Coordinate
				    position.x+0.5f*sizeFactor,  position.y+0.5f*sizeFactor, position.z-0.5f*sizeFactor, 			0.0f+xOffset_value*xOffset_top, 1.0f-yOffset_value*yOffset_top, // Top
				    position.x-0.5f*sizeFactor,  position.y+0.5f*sizeFactor, position.z+0.5f*sizeFactor,			0.041666f+xOffset_value*xOffset_top, 0.97058823f-yOffset_value*yOffset_top, // Bottom-left
				    position.x+0.5f*sizeFactor,  position.y+0.5f*sizeFactor, position.z+0.5f*sizeFactor,			0.041666f+xOffset_value*xOffset_top, 1.0f-yOffset_value*yOffset_top, // Bottom-right
				    
				// BOTTOM SQUARE
					// Positions (x, y, z) 		// Texture Coordinate
				    position.x-0.5f*sizeFactor,  position.y-0.5f*sizeFactor, position.z+0.5f*sizeFactor,	 		0.041666f+xOffset_value*xOffset_bottom, 0.97058823f-yOffset_value*yOffset_bottom, // Top
				    position.x-0.5f*sizeFactor,  position.y-0.5f*sizeFactor, position.z-0.5f*sizeFactor,			0.0f+xOffset_value*xOffset_bottom, 0.97058823f-yOffset_value*yOffset_bottom, // Bottom-left
				    position.x+0.5f*sizeFactor,  position.y-0.5f*sizeFactor, position.z-0.5f*sizeFactor,			0.0f+xOffset_value*xOffset_bottom, 1.0f-yOffset_value*yOffset_bottom, // Bottom-right
					
					// Positions 				// Texture Coordinate
				    position.x+0.5f*sizeFactor,  position.y-0.5f*sizeFactor, position.z+0.5f*sizeFactor, 		0.041666f+xOffset_value*xOffset_bottom, 1.0f-yOffset_value*yOffset_bottom, // Top
				    position.x-0.5f*sizeFactor,  position.y-0.5f*sizeFactor, position.z+0.5f*sizeFactor,			0.041666f+xOffset_value*xOffset_bottom, 0.97058823f-yOffset_value*yOffset_bottom, // Bottom-left
				    position.x+0.5f*sizeFactor,  position.y-0.5f*sizeFactor, position.z-0.5f*sizeFactor,			0.0f+xOffset_value*xOffset_bottom, 1.0f-yOffset_value*yOffset_bottom // Bottom-right
			};
		this.vertices = vertices;
	}
	
	public float[] getVertices () {
		return this.vertices;
	}
	
	public void highlightBlock() {
		if (!isHighlighted) {
			if (highlightBlock == null) {
				highlightBlock = new Block(this.position, -1);
			}
			else
				highlightBlock.setPosition(this.position);
		}
		isHighlighted = true;
	}
	
	private void sendVerticesToBuffer() {
		Renderer.addVertices(this.vertices);
	}
}
