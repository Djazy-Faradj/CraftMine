import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
	private Vector3f position, front, up, right;
	private float pitch, yaw;
    Vector3f movement = new Vector3f(0.0f, 0.0f, 0.0f); // Initially at zero
    public Vector3f direction = new Vector3f(0.0f, 0.0f, 0.0f);
	
	public Camera (Vector3f position) {
		this.position = position;
		this.front = new Vector3f(0.0f, 0.0f, -1.0f);
		this.up = new Vector3f(0.0f, 1.0f, 0.0f);
		this.pitch = 0.0f;
		this.yaw = -90.0f; // Looking along the negative Z-axis by default
	}
	
	public Matrix4f GetViewMatrix() {
		Vector3f center = new Vector3f();
		position.add(front, center); // center = position + front
		return new Matrix4f().lookAt(position, center, up);
	}
	
	public void ProcessKeyboard() {
		float velocity = Settings.CAMERA_VELOCITY * AppMain.deltaTime;
		movement.zero();

		if (direction.z != 0) {
	        movement.sub(new Vector3f(front).mul(direction.z * velocity)); // Forward/backward
	    }
	    if (direction.x != 0) {
	        right = new Vector3f(front).cross(up, new Vector3f()).normalize();
	        movement.add(new Vector3f(right).mul(direction.x * velocity)); // Left/right
	    }
	    // Limit
	    if (movement.x > 0.1f)
	    	movement.x = 0.1f;
	    if (movement.x < -0.1f)
	    	movement.x = -0.1f;
	    if (movement.z > 0.1f)
	    	movement.z = 0.1f;
	    if (movement.z < -0.1f)
	    	movement.z = -0.1f;
	}
	
	public void UpdateCameraPosition() { // To be called in the main loop
	    position.add(movement.x, 0.0f, movement.z);
		
	}
	
	public void ProcessMouse(float xOffset, float yOffset, boolean constraintPitch) {
		float sensitivity = Settings.CAMERA_SENSITIVITY;
		if (xOffset > -90 && xOffset < 90 && yOffset > -90 && yOffset < 90) { // This condition prevents camera from "snapping" every time game pauses and resumes
			yaw += (xOffset * sensitivity);
			yaw %= 360;
			pitch += yOffset * sensitivity;
		}
		
		if (constraintPitch) {
			if (pitch > 89.0f) pitch = 89.0f;
			if (pitch < -89.0f) pitch = -89.0f;
		}
		//System.out.println("pitch: " + pitch + "\nyaw: " + yaw);
		UpdateCameraVectors();
	}
	
	private void UpdateCameraVectors() {
		// Recalculate front vector based on yaw and pitch
		front.x = (float) Math.cos(Math.toRadians(yaw)) * (float) Math.cos(Math.toRadians(pitch));
		front.y = (float) Math.sin(Math.toRadians(pitch));
		front.z = (float) Math.sin(Math.toRadians(yaw)) * (float) Math.cos(Math.toRadians(pitch));
		front.normalize();
        right = new Vector3f(front).cross(up, new Vector3f()).normalize();
	}
}
