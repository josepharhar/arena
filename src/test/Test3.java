package test;

import org.lwjgl.Sys;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Test3 {
	
	private GLFWErrorCallback errorCallback;
	private long id;
	
	public static void main(String[] args) {
		new Test3().run();
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
		glOrtho(-2, 2, -2, 2, -2, 2);
		glMatrixMode(GL_MODELVIEW);
	}
	
	private void loop() {
	    float rotation = 0.0f;
	    
		while (glfwWindowShouldClose(id) == GL_FALSE) {
			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glLoadIdentity();
			glfwPollEvents();
			
			

			glRotatef(rotation, 1.0f, 1.0f, 1.0f);

			// Draw a cube
	        glBegin(GL_QUADS);
	            //Top - Green
	        	glColor3f(0.0f, 1.0f, 0.0f);
	        	glVertex3f( 1.0f, 1.0f,-1.0f);
	        	glVertex3f(-1.0f, 1.0f,-1.0f);
	        	glVertex3f(-1.0f, 1.0f, 1.0f);
	        	glVertex3f( 1.0f, 1.0f, 1.0f);
	        glEnd();
	        glBegin(GL_QUADS);
	        	//Bottom - Orange
                glColor3f(1.0f, 0.5f, 0.0f);
                glVertex3f( 1.0f,-1.0f, 1.0f);
                glVertex3f(-1.0f,-1.0f, 1.0f);
                glVertex3f(-1.0f,-1.0f,-1.0f);
                glVertex3f( 1.0f,-1.0f,-1.0f);
                //Front - Red
                glColor3f(1.0f, 0.0f, 0.0f);
                glVertex3f( 1.0f, 1.0f, 1.0f);
                glVertex3f(-1.0f, 1.0f, 1.0f);
                glVertex3f(-1.0f,-1.0f, 1.0f);
                glVertex3f( 1.0f,-1.0f, 1.0f);
                //Back - Yellow
                glColor3f(1.0f, 1.0f, 0.0f);
                glVertex3f( 1.0f,-1.0f,-1.0f);
                glVertex3f(-1.0f,-1.0f,-1.0f);
                glVertex3f(-1.0f, 1.0f,-1.0f);
                glVertex3f( 1.0f, 1.0f,-1.0f);
                //Left - Blue
                glColor3f(0.0f, 0.0f, 1.0f);
                glVertex3f(-1.0f, 1.0f, 1.0f);
                glVertex3f(-1.0f, 1.0f,-1.0f);
                glVertex3f(-1.0f,-1.0f,-1.0f);
                glVertex3f(-1.0f,-1.0f, 1.0f);
                //Right - Violet
                glColor3f(1.0f, 0.0f, 1.0f);
                glVertex3f( 1.0f, 1.0f,-1.0f);
                glVertex3f( 1.0f, 1.0f, 1.0f);
                glVertex3f( 1.0f,-1.0f, 1.0f);
                glVertex3f( 1.0f,-1.0f,-1.0f);
            glEnd();
	        
//	        GL11.glBegin(GL11.GL_POLYGON);
//	        	glColor3f(0, 0, 255);
//	        	glVertex3f(100, 100, 0);
//	        	glColor3f(0, 255, 0);
//	        	glVertex3f(100, 400, 0);
//	        	glColor3f(255, 0, 0);
//	        	glVertex3f(400, 400, 0);
//	        	glColor3f(255, 255, 255);
//	        	glVertex3f(400, 100, 0);
//	        GL11.glEnd();
	        rotation -= 0.3f;

			glfwSwapBuffers(id);
		}
	}
	
}