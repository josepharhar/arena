package arena;

import org.lwjgl.Sys;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Test2 {

	private int frames = 1;
	
	private GLFWErrorCallback errorCallback;
	private long id;
	
	public static void main(String[] args) {
		new Test2().run();
	}
	
	public void run() {
		init();
		loop();
	}
	
	private void init() {
		glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));
		if ( glfwInit() != GL_TRUE ) {
		    throw new IllegalStateException("Unable to initialize GLFW");
		}
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GL_TRUE);
		glfwWindowHint(GLFW_RESIZABLE, true ? GL_TRUE : GL_FALSE);
		
		id = glfwCreateWindow(800, 600, "MyTitle", NULL, NULL);
		if (id == NULL) throw new RuntimeException("failed to create new window");
		
		glfwMakeContextCurrent(id);
		GLContext.createFromCurrent();
		
		glfwSwapInterval(1);
		glfwShowWindow(id);
		
		glfwMakeContextCurrent(id);
		GLContext.createFromCurrent();
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, 800, 0, 600, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}
	
	private void loop() {
		long start = System.nanoTime();
		
		float rotation = 0;
		
		while (glfwWindowShouldClose(id) == GL_FALSE) {
			
			System.out.println((System.nanoTime() - start) / frames / 1000000l);
			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			glfwPollEvents();
			
	        GL11.glColor3f(0.5f,0.5f,1.0f);
	             
	        //glTranslatef(1f, 1f, 0f);
	        
	        GL11.glRotatef(rotation, 0f, 1f, 0f);

	        GL11.glBegin(GL11.GL_POLYGON);
	        	glColor3f(0, 0, 255);
	        	glVertex3f(100, 100, 0);
	        	glColor3f(0, 255, 0);
	        	glVertex3f(100, 400, 0);
	        	glColor3f(255, 0, 0);
	        	glVertex3f(400, 400, 0);
	        	glColor3f(255, 255, 255);
	        	glVertex3f(400, 100, 0);
	        GL11.glEnd();

			glfwSwapBuffers(id);
			
			System.out.println(frames);
			frames++;
			rotation += .02f;
		}
	}
	
}