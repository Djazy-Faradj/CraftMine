
public class ShaderSource {
	  public static String vertexShaderSource = """
	            #version 330 core
	            layout(location = 0) in vec3 position;
	            layout(location = 1) in vec2 texCoords; // for texture coordinates

	            out vec2 fragTexCoords;
	            
	            uniform mat4 projection;
	            uniform mat4 model;

	            void main() {
	                gl_Position = model * projection * vec4(position, 1.0);
	                fragTexCoords = texCoords; // Pass it to the fragment shader
	            }
	            """;

     public static String fragmentShaderSource = """
	            #version 330 core
	            in vec2 fragTexCoords; // Interpolated texture coordinates
	            
	            out vec4 color;
	            
	            uniform sampler2D texture0; // The texture

	            void main() {
	            	vec4 fragColor = texture(texture0, fragTexCoords); // Sample the texture
	                color = fragColor; // Combine with vertex color
	            }
	            """;
	
}
