import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

public class Renderer {
	private final int vaoId;
	private final int vboId;
	
	public Renderer(float[] vertices) {
		vaoId = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vaoId);
		
		vboId = GL30.glGenBuffers();
		GL30.glBindBuffer(GL30.GL_ARRAY_BUFFER, vboId);
		
		FloatBuffer vertexBuffer = MemoryUtil.memAllocFloat(Settings.vertices.length);
		vertexBuffer.put(vertices).flip();
		GL30.glBufferData(GL30.GL_ARRAY_BUFFER, vertices, GL30.GL_STATIC_DRAW);
		MemoryUtil.memFree(vertexBuffer);
		
		// Position Attribute
		GL30.glVertexAttribPointer(0, 2, GL30.GL_FLOAT, false, 5 * Float.BYTES, 0);
		GL30.glEnableVertexAttribArray(0);

		// Color Attribute
		GL30.glVertexAttribPointer(1, 3, GL30.GL_FLOAT, false, 5 * Float.BYTES, 2 * Float.BYTES);
		GL30.glEnableVertexAttribArray(1);
	}
	
	public void Render() {
		GL30.glBindVertexArray(vaoId);
		GL30.glDrawArrays(GL30.GL_TRIANGLES, 0, 3);
		GL30.glBindVertexArray(0);
	}
	
	public void CleanUp() {
		GL30.glDeleteVertexArrays(vaoId);
	}
}
