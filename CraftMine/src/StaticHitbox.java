import org.joml.Vector3f;

public class StaticHitbox {

	public static Vector3f[] relevantStaticHBZone = {};
	private Vector3f position;
	
	public StaticHitbox(Vector3f position) {
		this.position = position;
	}
	
	public static void addLocsToArray(Vector3f[] relevantLocs) {		// Add this dynamic hitbox's position to the static collection of positions
		Vector3f[] temp = new Vector3f[relevantStaticHBZone.length+relevantLocs.length];
		System.arraycopy(relevantStaticHBZone, 0, temp, 0, relevantStaticHBZone.length);
		System.arraycopy(relevantLocs, 0, temp, relevantStaticHBZone.length, relevantLocs.length);
		
		
		relevantStaticHBZone = temp;
	}
	
	
	public boolean checkCollision() {
		for (int i = 0; i < relevantStaticHBZone.length; i++) {
			
		}
	}
}
