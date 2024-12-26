import org.lwjgl.stb.STBImage;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL30;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class TextureHandler {
	
	private int textureId;
	
	public TextureHandler(String filePath) {
		textureId = GL30.glGenTextures();
		GL30.glBindTexture(GL30.GL_TEXTURE_2D, textureId);
		
		// Texture parameters
		GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_WRAP_S, GL30.GL_REPEAT);
		GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_WRAP_T, GL30.GL_REPEAT);
		GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MIN_FILTER, GL30.GL_NEAREST);
		GL30.glTexParameteri(GL30.GL_TEXTURE_2D, GL30.GL_TEXTURE_MAG_FILTER, GL30.GL_NEAREST);
		
		// Load Image
		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		IntBuffer channels = BufferUtils.createIntBuffer(1);
		
		STBImage.nstbi_set_flip_vertically_on_load(textureId);
		ByteBuffer image = STBImage.stbi_load(filePath, width, height, channels, 4);
		if (image == null) {
			throw new RuntimeException("Failed to load image texture " + STBImage.nstbi_failure_reason());
		}
		
		// Upload texture to GPU
		GL30.glTexImage2D(GL30.GL_TEXTURE_2D, 0, GL30.GL_RGBA, width.get(0), height.get(0), 0, GL30.GL_RGBA, GL30.GL_UNSIGNED_BYTE, image);
		GL30.glGenerateMipmap(GL30.GL_TEXTURE_2D);
		
		// Free image memory
		STBImage.stbi_image_free(image);
	}
	
	public void bind(int unit) {
		GL30.glActiveTexture(textureId + unit);
		GL30.glBindTexture(GL30.GL_TEXTURE_2D, textureId);
	}
	
	public void cleanUp() {
		GL30.glDeleteTextures(textureId);
	}
	
	public int getId() {
		return textureId;
	}
}
