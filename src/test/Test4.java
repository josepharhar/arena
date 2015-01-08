package test;

import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;

import javax.imageio.ImageIO;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Test4 {
    
    private GLFWErrorCallback errorCallback;
    private long id;
    
    public static void main(String[] args) {
        new Test4().run();
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
        //glOrtho(-4, 4, -4, 4, -4, 4);
        glFrustum(-50.0f, 50.0f, -50.0f, 50.0f, -50.0f, 50.0f);
        glMatrixMode(GL_PROJECTION);
        
        
        glEnable(GL_TEXTURE_2D);
        glShadeModel(GL_SMOOTH);
        glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
        glClearDepth(1.0f);
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }
    
    private void loop() {
//        float rotation = 0.0f;
        
        
        BufferedImage grassImage = getBufferedImage("c:\\users\\joey\\desktop\\grass.png");
        ByteBuffer grassBuffer = getByteBuffer(grassImage);
        
        
        int textureID = glGenTextures();
        
        //Nearest Filtered Texture
        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR_MIPMAP_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, grassImage.getWidth(), grassImage.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, grassBuffer);

        FloatBuffer lightAmbient = BufferUtils.createFloatBuffer(4);
        lightAmbient.put(new float[]{0.5f, 0.5f, 0.5f, 1.0f});
        lightAmbient.flip();

        FloatBuffer lightDiffuse = BufferUtils.createFloatBuffer(4);
        lightDiffuse.put(new float[]{1.0f, 1.0f, 1.0f, 1.0f});
        lightDiffuse.flip();
        
        FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(new float[]{0.0f, 0.0f, 2.0f, 1.0f});
        lightPosition.flip();

        glLight(GL_LIGHT1, GL_AMBIENT, lightAmbient);
        glLight(GL_LIGHT1, GL_DIFFUSE, lightDiffuse);
        glLight(GL_LIGHT1, GL_POSITION, lightPosition);
        
//        glEnable(GL_LIGHT1);
        
        // Key variables
        boolean kpressed = false;
        
        // Mouse (rotation) Variables
        double downx = 0.0;
        double downy = 0.0;
        boolean pressed = false;
        float diffx = 0.0f;
        float diffy = 0.0f;
        float rotation = 0.0f;
        
        
        while (glfwWindowShouldClose(id) == GL_FALSE) {
            
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glLoadIdentity();
            
            //debug
            System.out.println("diffx: " + diffx + ", diffy: " + diffy + ", rotation: " + rotation);
            
            //Get key/mouse input
            glfwPollEvents();
            
            
            if (glfwGetKey(id, GLFW_KEY_F) == GLFW_PRESS) {
                if (!kpressed) {
                    kpressed = true;
                    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
                    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR_MIPMAP_NEAREST);
                    glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, grassImage.getWidth(), grassImage.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, grassBuffer);
                }
            } else {
                kpressed = false;
            }
            
            if (glfwGetMouseButton(id, GLFW_MOUSE_BUTTON_1) == GLFW_PRESS) {
                if (!pressed) {
                    // Just clicked down for the first time, need to store this cursor location
                    pressed = true;
                    DoubleBuffer b1 = BufferUtils.createDoubleBuffer(1);
                    DoubleBuffer b2 = BufferUtils.createDoubleBuffer(1);
                    glfwGetCursorPos(id, b1, b2);
                    downx = b1.get(0);
                    downy = b2.get(0);
                } else {
                    // Being held down, need to update rotation relative to init location
                    DoubleBuffer b1 = BufferUtils.createDoubleBuffer(1);
                    DoubleBuffer b2 = BufferUtils.createDoubleBuffer(1);
                    glfwGetCursorPos(id, b1, b2);
                    diffy = (float) -(b1.get(0) - downx);
                    diffx = (float) -(b2.get(0) - downy);
                    
                    //total distance cursor has traveled to be used for the rotation
                    double distance = Math.sqrt(diffx * diffx + diffy * diffy);
                    //20 pixels will be 180 degrees of rotation
                    //glRotatef uses DEGREES, NOT RADIANS
                    rotation = (float) (distance / 3.0);
                }
            } else if (glfwGetMouseButton(id, GLFW_MOUSE_BUTTON_1) == GLFW_RELEASE) {
                if (pressed) {
                    // Just released click
                    pressed = false;
                }
            }
            
            
            glRotatef(rotation, diffx, diffy, 0.0f);
            
            
            glBegin(GL_QUADS);
            // Front Face
            glNormal3f(0.0f, 0.0f, 0.5f);
            glTexCoord2f(0.0f, 0.0f); glVertex3f(-0.5f, -0.5f,  0.5f);  // Bottom Left Of The Texture and Quad
            glTexCoord2f(0.5f, 0.0f); glVertex3f( 0.5f, -0.5f,  0.5f);  // Bottom Right Of The Texture and Quad
            glTexCoord2f(0.5f, 0.5f); glVertex3f( 0.5f,  0.5f,  0.5f);  // Top Right Of The Texture and Quad
            glTexCoord2f(0.0f, 0.5f); glVertex3f(-0.5f,  0.5f,  0.5f);  // Top Left Of The Texture and Quad
            // Back Face
            glNormal3f(0.0f, 0.0f, -0.5f);
            glTexCoord2f(0.5f, 0.0f); glVertex3f(-0.5f, -0.5f, -0.5f);  // Bottom Right Of The Texture and Quad
            glTexCoord2f(0.5f, 0.5f); glVertex3f(-0.5f,  0.5f, -0.5f);  // Top Right Of The Texture and Quad
            glTexCoord2f(0.0f, 0.5f); glVertex3f( 0.5f,  0.5f, -0.5f);  // Top Left Of The Texture and Quad
            glTexCoord2f(0.0f, 0.0f); glVertex3f( 0.5f, -0.5f, -0.5f);  // Bottom Left Of The Texture and Quad
            // Top Face
            glNormal3f(0.0f, 0.5f, 0.0f);
            glTexCoord2f(0.0f, 0.5f); glVertex3f(-0.5f,  0.5f, -0.5f);  // Top Left Of The Texture and Quad
            glTexCoord2f(0.0f, 0.0f); glVertex3f(-0.5f,  0.5f,  0.5f);  // Bottom Left Of The Texture and Quad
            glTexCoord2f(0.5f, 0.0f); glVertex3f( 0.5f,  0.5f,  0.5f);  // Bottom Right Of The Texture and Quad
            glTexCoord2f(0.5f, 0.5f); glVertex3f( 0.5f,  0.5f, -0.5f);  // Top Right Of The Texture and Quad
            // Bottom Face
            glNormal3f(0.0f, -0.5f, 0.0f);
            glTexCoord2f(0.5f, 0.5f); glVertex3f(-0.5f, -0.5f, -0.5f);  // Top Right Of The Texture and Quad
            glTexCoord2f(0.0f, 0.5f); glVertex3f( 0.5f, -0.5f, -0.5f);  // Top Left Of The Texture and Quad
            glTexCoord2f(0.0f, 0.0f); glVertex3f( 0.5f, -0.5f,  0.5f);  // Bottom Left Of The Texture and Quad
            glTexCoord2f(0.5f, 0.0f); glVertex3f(-0.5f, -0.5f,  0.5f);  // Bottom Right Of The Texture and Quad
            // Right face
            glNormal3f(0.5f, 0.0f, 0.0f);
            glTexCoord2f(0.5f, 0.0f); glVertex3f( 0.5f, -0.5f, -0.5f);  // Bottom Right Of The Texture and Quad
            glTexCoord2f(0.5f, 0.5f); glVertex3f( 0.5f,  0.5f, -0.5f);  // Top Right Of The Texture and Quad
            glTexCoord2f(0.0f, 0.5f); glVertex3f( 0.5f,  0.5f,  0.5f);  // Top Left Of The Texture and Quad
            glTexCoord2f(0.0f, 0.0f); glVertex3f( 0.5f, -0.5f,  0.5f);  // Bottom Left Of The Texture and Quad
            // Left Face
            glNormal3f(-0.5f, 0.0f, 0.0f);
            glTexCoord2f(0.0f, 0.0f); glVertex3f(-0.5f, -0.5f, -0.5f);  // Bottom Left Of The Texture and Quad
            glTexCoord2f(0.5f, 0.0f); glVertex3f(-0.5f, -0.5f,  0.5f);  // Bottom Right Of The Texture and Quad
            glTexCoord2f(0.5f, 0.5f); glVertex3f(-0.5f,  0.5f,  0.5f);  // Top Right Of The Texture and Quad
            glTexCoord2f(0.0f, 0.5f); glVertex3f(-0.5f,  0.5f, -0.5f);  // Top Left Of The Texture and Quad
            glEnd();
            

            glfwSwapBuffers(id);
        }
    }
    
    
    private static ByteBuffer getByteBuffer(BufferedImage image) {
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8 ) & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }
        buffer.flip();
        
        return buffer;
    }
    
    private static BufferedImage getBufferedImage(String filePath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
    
}