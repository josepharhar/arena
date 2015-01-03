package arena;

import org.lwjgl.Sys;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Arena {
   
   private Game game = new Game();;
   private long id;

   // We need to strongly reference callback instances.
   private GLFWErrorCallback errorCallback;
   private GLFWKeyCallback keyCallback;

   // The window handle
   private long window;

   public void run() {
      System.out.println("Hello LWJGL " + Sys.getVersion() + "!");

      try {
         init();
         loop();

         // Release window and window callbacks
         glfwDestroyWindow(window);
         keyCallback.release();
      } finally {
         // Terminate GLFW and release the GLFWerrorfun
         glfwTerminate();
         errorCallback.release();
      }
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
         
         GL11.glBegin(GL11.GL_TRIANGLES);
         game.draw();
         GL11.glEnd();
         
         
         GL11.glBegin(GL11.GL_QUADS);
         glColor3f(1, 0, 0);
         game.drawHealthBars();
         GL11.glEnd();

         glfwSwapBuffers(id);
      }
   }

   public static void main(String[] args) {
      new Arena().run();
   }

}