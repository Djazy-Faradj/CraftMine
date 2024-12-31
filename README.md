# Minecraft Clone Project - LWJGL

This project is a 3D game built with the **Lightweight Java Game Library (LWJGL)**, inspired by Minecraft. The objective is to develop a functional Minecraft clone, focusing on rendering, camera movement, input handling, and block management. Here's an overview of the current progress and features.

---

## Features Implemented

### **Rendering Engine**
- A rendering pipeline using OpenGL that draws textured and colored triangles.
- Depth testing enabled to ensure proper visibility of objects based on their distance from the camera.
- A perspective projection matrix to create a realistic 3D view.

### **Camera System**
- First-person camera implemented with full control over position and orientation.
- Smooth mouse-based look mechanics.
- Movement speed independent of camera pitch.
- Delta time integration for consistent movement across varying frame rates.

### **Texture Mapping**
- Textures are loaded using `stb_image` and mapped onto 3D objects.
- Support for multiple textures, with example implementation on a square.
- Texture coordinates properly defined to map textures without distortion.

### **Block System (Initial Implementation)**
- A `Block` class has been introduced to represent individual blocks in the game.
- Each block holds data for its position and type (e.g., texture ID).
- Blocks are rendered as textured quads (two triangles forming a square).

### **Input Handling**
- Keyboard input for WASD movement.
- Mouse input for camera rotation.
- Efficient input handling optimized to prevent lag when moving the mouse frequently.

---

## Current Challenges

1. **Efficient Vertex Management**:
   - Managing and updating the vertex data for large numbers of blocks dynamically is still being refined.
   - Exploring solutions like global vertex arrays or instanced rendering.

2. **Block Visibility and Occlusion**:
   - Ensuring that hidden faces of blocks are not rendered to optimize performance.

---

## Next Steps

### **Short-Term Goals**
1. **Block Management System**:
   - Develop a system for dynamically adding and removing blocks.
   - Optimize the vertex data update process to handle large block worlds efficiently.

2. **Face Culling**:
   - Introduce block occlusion to prevent rendering of faces hidden by neighboring blocks.

3. **Lighting and Shading**:
   - Begin work on basic lighting models to add depth and realism to the scene.

### **Long-Term Goals**
1. **Chunk System**:
   - Organize blocks into chunks for efficient rendering and memory management.
2. **Physics and Interactions**:
   - Implement collision detection and basic interactions with blocks.
3. **World Generation**:
   - Develop a procedural terrain generation system.

---

## How to Run

### **Prerequisites**
- Java Development Kit (JDK) 17 or higher.
- LWJGL 3.3+ set up in your IDE (e.g., Eclipse or IntelliJ IDEA).
- GLFW and OpenGL drivers installed on your system.

### **Setup**
1. Clone the repository:
   ```bash
   git clone <repository-url>
   ```
2. Open the project in your IDE.
3. Ensure that LWJGL libraries are correctly linked in your build configuration.
4. Run the `AppMain` class to start the program.

---

## Contributing
If you'd like to contribute:
- Fork the repository.
- Create a feature branch.
- Commit your changes and submit a pull request.

---

## Acknowledgments
- **LWJGL**: For providing the backbone library for this project.
- **stb_image**: For texture loading capabilities.

---

## Contact
For any questions or suggestions, feel free to reach out to me!

