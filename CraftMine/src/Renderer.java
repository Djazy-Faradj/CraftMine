import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

public class Renderer {
	
	private final int vaoId;
	private final int vboId;
	
	private static float[] vertices = {};
	
	public Renderer() {
		// Depth testing to avoid drawing stuff in the background at the front
		GL30.glEnable(GL30.GL_DEPTH_TEST);
		GL30.glDepthFunc(GL30.GL_LESS);
		
		GL30.glPolygonMode(GL30.GL_FRONT, GL30.GL_FILL);
		
		// Culling
		GL30.glEnable(GL30.GL_CULL_FACE);
		GL30.glCullFace(GL30.GL_BACK);
		GL30.glFrontFace(GL30.GL_CCW);
		
		// Allows for transparency in textures
		GL30.glEnable(GL30.GL_BLEND);
		GL30.glBlendFunc(GL30.GL_SRC_ALPHA, GL30.GL_ONE_MINUS_SRC_ALPHA);
		
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
		GL30.glDrawArrays(GL30.GL_TRIANGLES, 0, vertices.length/5);
	}
	
	public void cleanUp() {
		GL30.glDeleteVertexArrays(vaoId);
	}
	
	public void clearVertices() {
		vertices = new float[0];
	}
	
	public static void addVertices(float[] additionalVertices) {
		int ogLen = vertices.length;
		float[] newArr = new float[ogLen + additionalVertices.length];
		System.arraycopy(vertices, 0, newArr, 0, ogLen);
		System.arraycopy(additionalVertices, 0, newArr, ogLen, additionalVertices.length);
		vertices = newArr;
		updateBuffer();
	}
	
	// Check for block vertices and remove from array
	public static boolean deleteBlockVertices(float[] blockVertices) {
		for (int i = 0; i < vertices.length; i+=5*36) {
			for (int j = i; j < i+5*36; j++) {
				if (blockVertices[j-i] != vertices[j]) break;
				else if (j == i+5*36-1)								// DELETE VERTICES FROM STATIC ARRAY
				{
					j++;
					float[] temp = new float[vertices.length-5*36];
					System.arraycopy(vertices, 0, temp, 0, j-(5*36));
					System.arraycopy(vertices, j, temp, j-(5*36), vertices.length-(j));
					vertices = temp;
					return true;
				}
			}
		}
		return false;
	}
	
	private static void updateBuffer() {
		FloatBuffer vertexBuffer = MemoryUtil.memAllocFloat(vertices.length);
		vertexBuffer.put(vertices).flip();
		GL30.glBufferData(GL30.GL_ARRAY_BUFFER, vertices, GL30.GL_STATIC_DRAW);
		MemoryUtil.memFree(vertexBuffer);
	}
}
