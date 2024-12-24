import org.joml.Matrix4f;

public class Transform {

	private Matrix4f modelMatrix;
	
	public Transform() {
		modelMatrix = new Matrix4f();
	}
	
	public Matrix4f GetModelMatrix() {
		return modelMatrix;
	}
	
	public void Reset() {
		modelMatrix.identity();
	}
	
	public Transform Translate(float x, float y, float z) {
		modelMatrix.translate(x, y, z);
		return this;
	}
	
	public Transform Rotate(float angle, float x, float y, float z) {
		modelMatrix.rotate(angle, x, y, z);
		return this;
	}
	
	public Transform Scale(float factor) {
		modelMatrix.scale(factor);
		return this;
	}
}
