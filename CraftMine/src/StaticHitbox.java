import org.joml.Vector3f;

public class StaticHitbox {

	private Vector3f position;
	
	public StaticHitbox(Vector3f position) {
		this.position = position;
		checkCollision();
	}		
	
	public void checkCollision() {
		for (int i = 0; i < DynamicHitbox.instancedDynamicHitboxes.length; i++) {
			DynamicHitbox dhb = DynamicHitbox.instancedDynamicHitboxes[i];
			Vector3f[] zoneBorders = dhb.getZoneBorders();
			
			dhb.update();	// First, update all dynamic hitboxes to have relevant zones to check for collisions
			
			// Now, check if shb is in zone and if so, check for collision
			for (int j = 0; j < zoneBorders.length; j++) {
				if ((zoneBorders[j].x - this.position.x)*zoneBorders[j].x < 0.0f || (zoneBorders[j].y - this.position.y)*zoneBorders[j].y < 0.0f || (zoneBorders[j].z - this.position.z)*zoneBorders[j].z < 0.0f) {
					System.out.println("Not in zone");
					return; // Static hitbox is NOT relevant
				}
			}
			// THIS IS WHERE YOU HANDLE COLLISIONS with DHBs (Static hitbox is relevant)
			System.out.println("In zone!");
			return; // Make sure not to return here so it collides with EVERY dhb by looping through them
		}
		return; // Static hitbox is NOT relevant
	}
}
