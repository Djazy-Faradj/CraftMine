import org.joml.Vector3f;

public class StaticHitbox {

	private static int idCount = 0;
	
	private Vector3f position;
	private int id;
	public static StaticHitbox[] instancedStaticHitboxes = {};
	
	public StaticHitbox(Vector3f position) {
		this.id = idCount++;
		this.position = position;
		addInstanceToArray();
	}		
	
	private void addInstanceToArray() {
		StaticHitbox[] temp = new StaticHitbox[instancedStaticHitboxes.length+1];
		System.arraycopy(instancedStaticHitboxes, 0, temp, 0, instancedStaticHitboxes.length);
		temp[instancedStaticHitboxes.length] = this;
		
		instancedStaticHitboxes = temp;
	}
	
	public void destroy() {
		for (int i = 0; i < instancedStaticHitboxes.length; i++) {
			if (instancedStaticHitboxes[i] == this) {
				StaticHitbox[] temp = new StaticHitbox[instancedStaticHitboxes.length-1];
				System.arraycopy(instancedStaticHitboxes, 0, temp, 0, i);
				System.arraycopy(instancedStaticHitboxes, i+1, temp, i, temp.length-i);
				
				instancedStaticHitboxes = temp;
				return;
			}
		}
	}
	
	public int getId() {
		return this.id;
	}
	
	public static void checkForCollisions() {
		for (int m = 0; m < instancedStaticHitboxes.length; m++) {
			StaticHitbox shb = StaticHitbox.instancedStaticHitboxes[m];
			
			for (int i = 0; i < DynamicHitbox.instancedDynamicHitboxes.length; i++) {
				DynamicHitbox dhb = DynamicHitbox.instancedDynamicHitboxes[i];
				Vector3f[] zoneBorders = dhb.getZoneBorders();
				
				dhb.update();	// First, update all dynamic hitboxes to have relevant zones to check for collisions
				
				// Now, check if shb is in zone and if so, check for collision
				for (int j = 0; j < zoneBorders.length; j++) {
					if ((zoneBorders[j].x - shb.position.x)*zoneBorders[j].x < 0.0f || (zoneBorders[j].y - shb.position.y)*zoneBorders[j].y < 0.0f || (zoneBorders[j].z - shb.position.z)*zoneBorders[j].z < 0.0f) {
						System.out.println("shb " + shb.getId() + " is not relevant to dhb " + dhb.getId());
						break; // Static hitbox is NOT relevant zone of this dhb
					}
					if (j == zoneBorders.length-1) {
						// THIS IS WHERE YOU HANDLE COLLISIONS with DHBs (Static hitbox is relevant)
						System.out.println("shb " + shb.getId() + " zone of dhb " + dhb.getId());
					}
				}
			}
		}
	}
}
