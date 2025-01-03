import org.joml.Vector3f;

public class DynamicHitbox {
	
	public static Vector3f[] dynamicHBLocs = {};
	public static DynamicHitbox[] instancedDynamicHitboxes = {};
	
	
	private Vector3f position;
	
	private void addLocToArray() {		// Add this dynamic hitbox's position to the static collection of positions
		Vector3f[] temp = new Vector3f[dynamicHBLocs.length+1];
		System.arraycopy(dynamicHBLocs, 0, temp, 0, dynamicHBLocs.length);
		temp[dynamicHBLocs.length] = this.position;
		
		dynamicHBLocs = temp;
	}
	
	private void removeLocFromArray() {	// Removes the position of this dynamic hitbox from the collection of all the dynamic hitboxes' position
		for (int i = 0; i < dynamicHBLocs.length; i++) {
			if (this.position.equals(dynamicHBLocs[i])) {
				Vector3f[] temp = new Vector3f[dynamicHBLocs.length-1];
				System.arraycopy(dynamicHBLocs, 0, temp, 0, i);
				System.arraycopy(dynamicHBLocs, i+1, temp, i, dynamicHBLocs.length-i-1);
				dynamicHBLocs = temp;
			}
		}
	}
	
	private void updateReleventLocs() { // Determines which position is relevant for checking static hitbox collision
		int detectionZoneRadius = Settings.HB_DETECTION_RAY_RADIUS;
		int detectionZoneDiameter = Settings.HB_DETECTION_RAY_RADIUS*2+1;
		int detectionZoneVolume = detectionZoneDiameter*detectionZoneDiameter*detectionZoneDiameter;
		
		// Clear current relevant locs to generate new ones
		StaticHitbox.relevantStaticHBZone = new Vector3f[0];
		
		// Regenerate relevant locations
		for (int m = 0; m < dynamicHBLocs.length; m++) {
			Vector3f[] relevantLocs = new Vector3f[detectionZoneVolume];
			for (int i = -detectionZoneRadius, p = 0; i <= detectionZoneRadius; i++) {
				for (int j = -detectionZoneRadius; j <= detectionZoneRadius; j++) {
					for (int k = -detectionZoneRadius; k <= detectionZoneRadius; k++, p++) {
						relevantLocs[p] = new Vector3f(dynamicHBLocs[m]).add(new Vector3f(i, j, k));
					}
				}
			}
			StaticHitbox.addLocsToArray(relevantLocs);
		}
		for (int i = 0; i < StaticHitbox.relevantStaticHBZone.length; i++) {
			System.out.println(StaticHitbox.relevantStaticHBZone[i]);
		}
	}
	
	public DynamicHitbox(Vector3f position) {
		this.position = position;
		addLocToArray();
		updateReleventLocs();
		
	}
		

	public void destroy() {
		removeLocFromArray();
		updateReleventLocs();		// Update the relevant locs so that useless static hitboxes from the now deleted dynamic hitbox are discarded
	}
}
