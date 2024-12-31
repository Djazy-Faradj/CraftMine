import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

public class Renderer {
	
	private final int vaoId;
	private final int vboId;
	
	private static float[] vertices = {};
	
	public Renderer(float[] vertices) {
		vaoId = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoId);
		
		vboId = GL30.glGenBuffers();
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboId);
		
		updateBuffer();
		
		// Position Attribute
		GL30.glVertexAttribPointer(0, 3, GL30.GL_FLOAT, false, 5 * Float.BYTES, 0);
		GL30.glEnableVertexAttribArray(0);
		
		// Texture Coordinate Attribute
		GL30.glVertexAttribPointer(1, 2, GL30.GL_FLOAT, false, 5 * Float.BYTES, 3 * Float.BYTES);
		GL30.glEnableVertexAttribArray(1);
	}
	
	public void render() {
		GL30.glBindVertexArray(vaoId);
		GL30.glDrawArrays(GL30.GL_TRIANGLES, 0, this.vertices.length/5);
	}
	
	public void cleanUp() {
		GL30.glDeleteVertexArrays(vaoId);
	}
	
	public void clearVertices() {
		this.vertices = new float[0];
	}
	
	public void addVertices(float[] additionalVertices) {
		int ogLen = vertices.length;
		float[] newArr = new float[ogLen + additionalVertices.length];
		System.arraycopy(this.vertices, 0, newArr, 0, ogLen);
		System.arraycopy(additionalVertices, 0, newArr, ogLen, additionalVertices.length);
		this.vertices = newArr;
		updateBuffer();
	}
	
	private void updateBuffer() {
		FloatBuffer vertexBuffer = MemoryUtil.memAllocFloat(this.vertices.length);
		vertexBuffer.put(this.vertices).flip();
		GL30.glBufferData(GL30.GL_ARRAY_BUFFER, this.vertices, GL30.GL_STATIC_DRAW);
		MemoryUtil.memFree(vertexBuffer);
		
	}
}
