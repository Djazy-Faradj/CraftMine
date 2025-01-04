import org.joml.Vector3f;

public class StaticHitbox {

	private static int idCount = 0;
	
	private Vector3f position;
	private int id;
	public static StaticHitbox[] instancedStaticHitboxes = {};
	private float xSize, zSize, height;
	
	public StaticHitbox(Vector3f position, float xSize, float zSize, float height) {
		this.id = idCount++;
		this.position = position;
		this.xSize = xSize;
		this.zSize = zSize;
		this.height = height;
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
				if     ((shb.position.x < dhb.getPosition().x+dhb.getSizeX()+shb.xSize && shb.position.x > dhb.getPosition().x-dhb.getSizeX()-shb.xSize) && // Colliding in both x
						(shb.position.y < dhb.getPosition().y+dhb.getHeight() && shb.position.y > dhb.getPosition().y-shb.height) &&						// y
						(shb.position.z < dhb.getPosition().z+dhb.getSizeZ()+shb.zSize && shb.position.z > dhb.getPosition().z-dhb.getSizeZ()-shb.zSize)) {	// and z
					
					float xVal1 = dhb.getPosition().x+dhb.getSizeX()+shb.xSize-shb.position.x; // from ie left border
					float xVal2 = dhb.getPosition().x-dhb.getSizeX()-shb.xSize-shb.position.x; // from the other (right) border
					float xDisplacement = Math.min(Math.abs(xVal1), Math.abs(xVal2));
					xDisplacement = (Math.pow(xVal1, 2) - Math.pow(xDisplacement, 2) < 0.0001f) ? -xVal1 : -xVal2 ;
					
					float yVal1 = dhb.getPosition().y+dhb.getHeight()-shb.position.y; // from ie left border
					float yVal2 = dhb.getPosition().y-shb.height-shb.position.y; // from the other (right) border
					float yDisplacement = Math.min(Math.abs(yVal1), Math.abs(yVal2));
					yDisplacement = (Math.pow(yVal1, 2) - Math.pow(yDisplacement, 2) < 0.0001f) ? -yVal1 : -yVal2 ;
					
					float zVal1 = dhb.getPosition().z+dhb.getSizeZ()+shb.zSize-shb.position.z; // from ie left border
					float zVal2 = dhb.getPosition().z-dhb.getSizeZ()-shb.zSize-shb.position.z; // from the other (right) border
					float zDisplacement = Math.min(Math.abs(zVal1), Math.abs(zVal2));
					zDisplacement = (Math.pow(zVal1, 2) - Math.pow(zDisplacement, 2) < 0.0001f) ? -zVal1 : -zVal2 ;
					
					
					float t = Math.min(Math.abs(xDisplacement), Math.abs(zDisplacement));
					if (t - Math.abs(xDisplacement) < 0.0001f)
						dhb.setDisplacement(new Vector3f(xDisplacement, 0.0f, 0.0f));
					else
						System.out.println("asl;dkjsd");
						//dhb.setDisplacement(new Vector3f(0.0f, 0.0f, zDisplacement));
					//dhb.setDisplacement(new Vector3f(xDisplacement, yDisplacement, zDisplacement)); // Send required displacement to the dhb in question
				} else {
					dhb.setDisplacement(new Vector3f(0.0f, 0.0f, 0.0f)); // No displacement required since hitbox is NOT colliding
				}
			}
		}
	}
}
