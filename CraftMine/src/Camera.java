import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
	private static int cameraIdCount = 1; // Starts at Id 1
	private int id;
	private Vector3f position, front, up, right;
	private float pitch, yaw;
    public Vector3f direction = new Vector3f(0.0f, 0.0f, 0.0f);
    private float cameraSpeed;
    
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
	    cameraSpeed = playerVelocity * AppMain.kdeltaTime;
	}
	
	public void updateCamera() { // To be called every frame
	    Vector3f horizontalFront = new Vector3f(front.x, 0.0f, front.z).normalize(); // Ignores camera pitch when moving 
	    
	    if (direction.z == 1.0f) 
	    	position.add(new Vector3f(horizontalFront).mul(new Vector3f(cameraSpeed, 0.0f, cameraSpeed)));
	    if (direction.z == -1.0f) 
	    	position.sub(new Vector3f(horizontalFront).mul(new Vector3f(cameraSpeed, 0.0f, cameraSpeed)));
	    if (direction.x == 1.0f) 
	    	position.add(new Vector3f(right).mul(new Vector3f(cameraSpeed, 0.0f, cameraSpeed)));
	    if (direction.x == -1.0f) 
	    	position.sub(new Vector3f(right).mul(new Vector3f(cameraSpeed, 0.0f, cameraSpeed)));
		
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
	}
	
	private void UpdateCameraVectors() {
		// Recalculate front vector based on yaw and pitch
		front.x = (float)Math.sin((double)yaw * (double)(Math.PI / 180)) * -1 * (1-Math.abs((float)Math.cos((double)pitch * (double)(Math.PI / 180) - 90 * (Math.PI / 180)) * -1));
		front.y = (float)Math.cos((double)pitch * (double)(Math.PI / 180) - 90 * (Math.PI / 180)) * -1;
		front.z = (float)Math.cos((double)yaw * (double)(Math.PI / 180)) * -1 * (1- Math.abs((float)Math.cos((double)pitch * (double)(Math.PI / 180) - 90 * (Math.PI / 180)) * -1));
		front.normalize();
        right = new Vector3f(front).cross(up, new Vector3f()).normalize();
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
	
	public boolean equals(Camera c) {
		if (c.id == this.id) return true;
		else return false;
	}
	
	public String toString() {
		return "Camera " + this.id + "\n\tCamera Pos.: " + this.position + "\n\tYaw: " + this.yaw + "\n\tPitch: " + this.pitch;
	}
}
