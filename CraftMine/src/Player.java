import org.joml.Vector3f;

public class Player {

	public static Player[] instancedPlayers = {};
	
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
	private Vector3f size = new Vector3f(0.5f, 1.9f, 0.5f);
	private Vector3f position;
	private Camera playerCam;
	private DynamicHitbox playerHitbox;
	public boolean inAir = false;
	
	
	Player(Vector3f position) {
		this.id = playerIdCount++;
		this.position = position;
		this.playerCam = new Camera(new Vector3f(position).add(0.0f, size.y-0.2f, 0.0f)); // Offset camera from player a little bit under the top of body
		this.playerHitbox = new DynamicHitbox(new Vector3f(position), 0.4f, 0.4f, size.y); // Generate hitbox for player
		this.changePlayerState(PLAYER_STATE.RUNNING);
		
		addInstanceToArray();
	}
	
	private void addInstanceToArray() {
		Player[] temp = new Player[instancedPlayers.length+1];
		System.arraycopy(instancedPlayers, 0, temp, 0, instancedPlayers.length);
		temp[instancedPlayers.length] = this;
		
		instancedPlayers = temp;
	}
	
	public void destroy() {
		
		playerHitbox.destroy();
		for (int i = 0; i < instancedPlayers.length; i++) {
			if (instancedPlayers[i] == this) {
				Player[] temp = new Player[instancedPlayers.length-1];
				System.arraycopy(instancedPlayers, 0, temp, 0, i);
				System.arraycopy(instancedPlayers, i+1, temp, i, temp.length-i);
				
				instancedPlayers = temp;
				return;
			}
		}
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
	public static void update() {
		for (int i = 0; i < instancedPlayers.length; i++) {
			Player p = instancedPlayers[i];
			p.playerCam.displace(p.playerHitbox.getDisplacement());
			p.playerCam.updateCamera();
			
			if (p.playerHitbox.getDisplacement().y != 0.0f) { // Keeps track of whether player is standing on ground or not
				if (p.playerHitbox.getDisplacement().y > 0.0f) {
					p.inAir = false;
					if (p.getCamera().getVerticalSpeed() <= 0.0f) {
						p.getCamera().setCameraVerticalSpeed(0.0f);
					}
				} else if (p.playerHitbox.getDisplacement().y < 0.0f) {
					p.getCamera().setCameraVerticalSpeed(0.0f);
				}
			} else { 
				p.inAir = true;
			}

			p.playerHitbox.resetDisplacement();
			p.position = new Vector3f(p.getCamera().getPosition()).sub(0.0f, p.size.y-0.2f, 0.0f);	
			p.playerHitbox.setPosition(p.position);
			p.playerHitbox.inAir = p.inAir;
		}
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
	
	public DynamicHitbox getDynamicHitbox() {
		return this.playerHitbox;
	}
	
	// EQUALS
	public boolean equals(Player p) {
		if (p.id == this.id) return true;
		else return false;
	}
	
	// TO STRING
	public String toString() {
		return "Player: " + id + "\n" + position;
	}
}
