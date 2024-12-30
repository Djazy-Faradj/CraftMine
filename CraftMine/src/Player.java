import org.joml.Vector3f;

public class Player {
	public static enum PLAYER_STATE {
		WALKING,
		RUNNING,
		CROUCHING
	} private PLAYER_STATE currentPlayerState;
	
	private float runningSpeedFactor = 2.0f;
	private float crouchingSpeedFactor = 0.6f;
	
	private static int playerIdCount = 1; // Start from 1
	private int id;
	private float velocity = Settings.PLAYER_VELOCITY;
	private Vector3f size = new Vector3f(0.5f, 1.4f, 0.5f);
	private Vector3f position;
	public Camera playerCam;
	
	Player(Vector3f position) {
		this.id = playerIdCount++;
		this.position = position;
		this.playerCam = new Camera(position.add(0.0f, size.y-0.2f, 0.0f)); // Offset camera from player a little bit under the top of body
		this.changePlayerState(PLAYER_STATE.WALKING);
	}
	
	public void changePlayerState(PLAYER_STATE newState) { // Change various settings depending on new given player state
		this.currentPlayerState = newState;
		
		switch(currentPlayerState) {
			case PLAYER_STATE.WALKING:
				this.setVelocity(Settings.PLAYER_VELOCITY);
				break;
			case PLAYER_STATE.RUNNING:
				this.setVelocity(Settings.PLAYER_VELOCITY * runningSpeedFactor);
				break;
			case PLAYER_STATE.CROUCHING:
				this.setVelocity(Settings.PLAYER_VELOCITY * crouchingSpeedFactor);
				break;
		}
	}
	
	// The way it works is that the player mostly just follows the camera which is what is being controlled by the player
	public void updatePlayer() {
		this.playerCam.updateCamera();
		this.position = new Vector3f(playerCam.getPosition()).sub(0.0f, size.y-0.2f, 0.0f);
	}
	
	// MUTATORS
	public void setCamera(Camera c) {
		this.playerCam = c;
	}
	public void setVelocity(float v) {
		this.velocity = v;
	}
	
	// ACCESSORS
	public int getId() {
		return this.id;
	}

	public Camera getCamera() {
		return this.playerCam;
	}
	
	public float getVelocity() {
		return this.velocity;
	}
	
	// EQUALS
	public boolean equals(Player p) {
		if (p.id == this.id) return true;
		else return false;
	}
	
	// TO STRING
	public String toString() {
		return "Player: " + id + "\n\tPlayer Pos.: " + position + "\n\t" + playerCam;
	}
}