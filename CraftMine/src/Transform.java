import org.joml.Matrix4f;

public class Transform {

	private Matrix4f modelMatrix;
	
	public Transform() {
		modelMatrix = new Matrix4f();
	}
	
	public Matrix4f getModelMatrix() {
		return modelMatrix;
	}
	
	public void reset() {
		modelMatrix.identity();
	}
	
	public Transform translate(float x, float y, float z) {
		modelMatrix.translate(x, y, z);
		return this;
	}
	
	public Transform rotate(float angle, float x, float y, float z) {
		modelMatrix.rotate(angle, x, y, z);
		return this;
	}
	
	public Transform scale(float factor) {
		modelMatrix.scale(factor);
		return this;
	}
}
