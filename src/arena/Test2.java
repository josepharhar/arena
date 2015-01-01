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
		while (glfwWindowShouldClose(id) == GL_FALSE) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			glfwPollEvents();
			
//			glViewport(0, 0, 800, 600);
//			glColor3f(0.0f, 255.0f, 0.0f);
//	        glBegin(GL_QUADS);
//	        	glVertex2f(100, 100);
//	        	glVertex2f(300, 100);
//	        	glVertex2f(300, 300);
//	        	glVertex2f(100, 300);
//	        glEnd();
			
//			 glClearColor(0.0f, 255.0f, 0.0f, 0.0f);
//	          glLoadIdentity();
//	          glTranslatef(-1.5f, 0.0f, -6.0f);
//	          System.out.println("starting triangle");
//	          glBegin(GL_TRIANGLES);
//	          glVertex3f( 0.0f, 1.0f, 0.0f);
//	          glVertex3f(-1.0f,-1.0f, 0.0f);
//	          glVertex3f( 1.0f,-1.0f, 0.0f);
//	          glEnd();
//	          System.out.println("finished triangle");
			
//			glClearColor(0, 255, 0, 0);
//			glColor3f(0, 0, 255);
//			glTranslatef(1f, 1f, 0f);
//			glBegin(GL_QUADS);
//				glVertex2f(20, 20);
//				glVertex2f(30, 20);
//				glVertex2f(30, 30);
//				glVertex2f(20, 30);
//			glEnd();
			// set the color of the quad (R,G,B,A)
	        GL11.glColor3f(0.5f,0.5f,1.0f);
	             
	        // draw quad
	        GL11.glBegin(GL11.GL_POLYGON);
//	            GL11.glVertex2f(100,100);
//	        GL11.glVertex2f(100+200,100);
//	        GL11.glVertex2f(100+200,100+200);
//	        GL11.glVertex2f(100,100+200);
	        	glVertex3f(100, 100, 0);
	        	glVertex3f(100, 400, 0);
	        	glVertex3f(400, 400, 0);
	        	glVertex3f(400, 100, 0);
	        GL11.glEnd();

			glfwSwapBuffers(id);
			
		}
	}
	
}