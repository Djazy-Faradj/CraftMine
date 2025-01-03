import org.joml.Vector3f;

public class DynamicHitbox {
	
	private static int idCount = 0;
	public static DynamicHitbox[] instancedDynamicHitboxes = {};
	
	
	private Vector3f position;
	private int id;
	private Vector3f[] zoneBorders;
	
	public DynamicHitbox(Vector3f position) {
		this.id = idCount++;
		this.position = position;
		addInstanceToArray();
		generateDetectionZone();

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
	
	public void update() {
		generateDetectionZone();
	}
	
	private void generateDetectionZone() { // Generate a zone around the dynamic hitbox for which static hitboxes will check for collision
		float zoneRadius = Settings.HB_DETECTION_RAY_RADIUS;
		
		this.zoneBorders = new Vector3f[8];
		for (int i = -1, m = 0; i <= 1; i+=2) {
			for (int j = -1; j <= 1; j+=2) {
				for (int k = -1; k <= 1; k+=2, m++) {
					zoneBorders[m] = new Vector3f(this.position.x + zoneRadius*i, this.position.y + zoneRadius*j, this.position.z + zoneRadius*k);
				}
			}
		}
	}
	
	public Vector3f[] getZoneBorders() {
		return this.zoneBorders;
	}
	
	public int getId() {
		return this.id;
	}
	
}
