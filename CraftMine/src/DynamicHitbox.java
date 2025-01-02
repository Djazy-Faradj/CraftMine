import org.joml.Vector3f;

public class DynamicHitbox {
	
	public static Vector3f[] dynamicHBLocs = {};
	
	
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
		for (int m = 0; m < dynamicHBLocs.length; m++) {
			Vector3f[] relevantLocs = new Vector3f[27];
			for (int i = -1, p = 0; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					for (int k = -1; k <= 1; k++, p++) {
						relevantLocs[p] = new Vector3f(dynamicHBLocs[m]).add(new Vector3f(i, j, k));
					}
				}
			}
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
