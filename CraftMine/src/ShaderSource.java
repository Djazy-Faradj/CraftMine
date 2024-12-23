
public class ShaderSource {
	  public static String vertexShaderSource = """
	            #version 330 core
	            layout(location = 0) in vec2 position;
	            layout(location = 1) in vec3 color;

	            out vec3 fragColor;

	            void main() {
	                gl_Position = vec4(position, 0.0, 1.0);
	                fragColor = color;
	            }
	            """;

     public static String fragmentShaderSource = """
	            #version 330 core
	            in vec3 fragColor;
	            out vec4 color;

	            void main() {
	                color = vec4(fragColor, 1.0);
	            }
	            """;
	
}
