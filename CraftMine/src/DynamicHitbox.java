import org.joml.Vector3f;

public class DynamicHitbox {
	
	private static int idCount = 0;
	public static DynamicHitbox[] instancedDynamicHitboxes = {};
	private Vector3f displacement = new Vector3f(); // Displacement required for the attached entity to correct collision offset
																				// RADIALLY FOR X AND Z				// FROM ORIGIN FOR Y
																			  // z							  // y
																				//----------------------//		//----------------------//
	private Vector3f position;													//		| zSize			//		//			|	height 	//
	private int id;																//		|				//		//			|			//
	private float height;														//	----D---- xSize		//		//			|			//
	private float xSize; // Radius of dynamic hitbox (for player specifically)	//		|				//		//			|			//
	private float zSize;														//		|				//		//		----D---- xSize	//
																				//----------------------// x	//----------------------// x
	public DynamicHitbox(Vector3f position, float xSize, float zSize, float height) {
		this.id = idCount++;
		this.position = position;
		this.height = height;
		this.xSize = xSize;
		this.zSize = zSize;
		addInstanceToArray();

	}
	
	private void addInstanceToArray() {
		DynamicHitbox[] temp = new DynamicHitbox[instancedDynamicHitboxes.length+1];
		System.arraycopy(instancedDynamicHitboxes, 0, temp, 0, instancedDynamicHitboxes.length);
		temp[instancedDynamicHitboxes.length] = this;
		
		instancedDynamicHitboxes = temp;
	}
	
	public void destroy() {
		for (int i = 0; i < instancedDynamicHitboxes.length; i++) {
			if (instancedDynamicHitboxes[i] == this) {
				DynamicHitbox[] temp = new DynamicHitbox[instancedDynamicHitboxes.length-1];
				System.arraycopy(instancedDynamicHitboxes, 0, temp, 0, i);
				System.arraycopy(instancedDynamicHitboxes, i+1, temp, i, temp.length-i);
				
				instancedDynamicHitboxes = temp;
				return;
			}
		}
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	public Vector3f getPosition() {
		return this.position;
	}
	
	public float getHeight() {
		return this.height;
	}
	
	public float getSizeX() {
		return this.xSize;
	}
	
	public float getSizeZ() {
		return this.zSize;
	}
	
	public void setDisplacement(Vector3f d) {
		this.displacement = new Vector3f(d);
	}
	
	public Vector3f getDisplacement() {
		return this.displacement;
	}
	
	public int getId() {
		return this.id;
	}
	
}
