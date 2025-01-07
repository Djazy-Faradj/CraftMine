import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
    public Vector3f direction = new Vector3f(0.0f, 0.0f, 0.0f);
    
    private Vector3f[] scannedBlockLocs = new Vector3f[1];
	private static int cameraIdCount = 1; // Starts at Id 1
	private float rayLength = 3.0f, rayPrecision = 0.1f; // Higher the precision, more ressource intensive is the ray
	private int id;
	private Vector3f position, front, up, right;
	private float pitch, yaw;
    private float cameraSpeed;
    private float cameraVerticalSpeed = 0.0f;
    
	public Camera (Vector3f position) {
		this.id = cameraIdCount++;
		this.position = position;
		this.front = new Vector3f(0.0f, 0.0f, -1.0f);
		this.up = new Vector3f(0.0f, 1.0f, 0.0f);
		this.pitch = 0.0f;
		this.yaw = -90.0f; // Looking along the negative Z-axis by default
	}
	
	public Matrix4f getViewMatrix() {
		Vector3f center = new Vector3f();
		position.add(front, center); // center = position + front
		return new Matrix4f().lookAt(position, center, up);
	}
	
	
	public void processKeyboard(float playerVelocity) {
	    cameraSpeed = playerVelocity;
	}
	
	public void updateCamera() { // To be called every frame
	    Vector3f horizontalFront = new Vector3f(front.x, 0.0f, front.z).normalize(); // Ignores camera pitch when moving 
	    
	    cameraVerticalSpeed += Settings.GRAVITY_ACC / AppMain.kdeltaTime;
	    
	    cameraVerticalSpeed = (cameraVerticalSpeed < Settings.TERMINAL_VEL) ? Settings.TERMINAL_VEL : cameraVerticalSpeed;
	    
	    if (direction.z == 1.0f) 
	    	position.add(new Vector3f(horizontalFront).mul(new Vector3f(cameraSpeed * AppMain.kdeltaTime, 0.0f, cameraSpeed * AppMain.kdeltaTime)));
	    if (direction.z == -1.0f) 
	    	position.sub(new Vector3f(horizontalFront).mul(new Vector3f(cameraSpeed * AppMain.kdeltaTime, 0.0f, cameraSpeed * AppMain.kdeltaTime)));
	    if (direction.x == 1.0f) 
	    	position.add(new Vector3f(right).mul(new Vector3f(cameraSpeed * AppMain.kdeltaTime, 0.0f, cameraSpeed * AppMain.kdeltaTime)));
	    if (direction.x == -1.0f) 
	    	position.sub(new Vector3f(right).mul(new Vector3f(cameraSpeed * AppMain.kdeltaTime, 0.0f, cameraSpeed * AppMain.kdeltaTime)));
	 
	    position.y += cameraVerticalSpeed;
	}

	public void displace(Vector3f d) { // Used for displacing when colliding against objects
		position.add(d);
	}
	
	public void processMouse(float xOffset, float yOffset, boolean constraintPitch) {
		if (xOffset > -90 && xOffset < 90 && yOffset > -90 && yOffset < 90) { // This condition prevents camera from "snapping" every time game pauses and resumes
			yaw -= (xOffset * Settings.CAMERA_SENSITIVITY);
			yaw %= 360;
			pitch -= yOffset * Settings.CAMERA_SENSITIVITY;
		}
		
		if (constraintPitch) {
			if (pitch > 89.0f) pitch = 89.0f;
			if (pitch < -89.0f) pitch = -89.0f;
		}
		UpdateCameraVectors();
		UpdateCameraRay();
	}
	
	private void UpdateCameraVectors() {
		// Recalculate front vector based on yaw and pitch
		front.x = (float)Math.sin((double)yaw * (double)(Math.PI / 180)) * -1 * (1-Math.abs((float)Math.cos((double)pitch * (double)(Math.PI / 180) - 90 * (Math.PI / 180)) * -1));
		front.y = (float)Math.cos((double)pitch * (double)(Math.PI / 180) - 90 * (Math.PI / 180)) * -1;
		front.z = (float)Math.cos((double)yaw * (double)(Math.PI / 180)) * -1 * (1- Math.abs((float)Math.cos((double)pitch * (double)(Math.PI / 180) - 90 * (Math.PI / 180)) * -1));
		front.normalize();
        right = new Vector3f(front).cross(up, new Vector3f()).normalize();
	}
	
	private void UpdateCameraRay() {
		resetScannedBlockLocs();
		for (float r = 0.0f; r < rayLength; r+=rayPrecision) {
			Vector3f rayPos = (new Vector3f(this.position).add(new Vector3f(this.front).mul(r)).add(0.5f, 0.5f, 0.5f).floor());
			addScannedBlockLoc(rayPos);
		}
	}
	
	private void resetScannedBlockLocs() { 
		this.scannedBlockLocs = new Vector3f[1];
		this.scannedBlockLocs[0] = this.position;
	}
	
	private void addScannedBlockLoc(Vector3f newBlockPos) {
		for (int i = 0; i < this.scannedBlockLocs.length; i++) { 
			if (this.scannedBlockLocs[i].equals(newBlockPos)) return;
		}
		Vector3f[] temp = new Vector3f[this.scannedBlockLocs.length + 1];
		System.arraycopy(this.scannedBlockLocs, 0, temp, 0, this.scannedBlockLocs.length);
		temp[temp.length-1] = newBlockPos;
		this.scannedBlockLocs = temp;
	}
	
	public int getId() {
		return this.id;
	}
	
	public float getYaw() {
		return this.yaw;
	}
	
	public Vector3f getPosition() {
		return this.position;
	}
	
	public float getVerticalSpeed() {
		return this.cameraVerticalSpeed;
	}
	
	public Vector3f[] getScannedBlockLocs() {
		return this.scannedBlockLocs;
	}
	
	public Block scanForBlock(Block[] blocksInView) {
		for (int j = 0; j < getScannedBlockLocs().length; j++) {
			for (int i = 0; i < blocksInView.length; i++) {
				if (blocksInView[i].isBlockAt(getScannedBlockLocs()[j]) != -1) {
					return blocksInView[i];
				}
			}
		}
		return null;
	}
	
	public void setCameraVerticalSpeed(float v) {
		this.cameraVerticalSpeed = v;
	}
	
	public boolean equals(Camera c) {
		if (c.id == this.id) return true;
		else return false;
	}
	
	public String toString() {
		return "Camera " + this.id + "\n\tCamera Pos.: " + this.position + "\n\tYaw: " + this.yaw + "\n\tPitch: " + this.pitch;
	}
}
