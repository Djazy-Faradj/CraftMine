import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
	private Vector3f position, front, up;
	private float pitch, yaw;
	
	public Camera (Vector3f position) {
		this.position = position;
		this.front = new Vector3f(0.0f, 0.0f, -1.0f);
		this.up = new Vector3f(0.0f, 1.0f, 0.0f);
		this.pitch = 0.0f;
		this.yaw = 90.0f; // Looking along the negative Z-axis by default
	}
	
	public Matrix4f GetViewMatrix() {
		Vector3f center = new Vector3f(this.position);
		position.add(front, center); // center = position + front
		return new Matrix4f().lookAt(position, center, up);
	}
	
	public void ProcessKeyboard(Vector3f direction, float deltaTime) {
		float velocity = Settings.CAMERA_VELOCITY * deltaTime;
		if (direction.x != 0) position.x += direction.x * velocity;
		if (direction.y != 0) position.y += direction.y * velocity;
		if (direction.z != 0) position.z += direction.z * velocity;
		//System.out.println(position);
	}
	
	public void ProcessMouse(float xOffset, float yOffset, boolean constraintPitch) {
		float sensitivity = Settings.CAMERA_SENSITIVITY;
		yaw += (xOffset * sensitivity);
		yaw %= 360;
		pitch += yOffset * sensitivity;
		
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
	}
}
