package arena;

import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

public class TriangleFighter extends MeleeUnit implements Graphic {
   
   public TriangleFighter(Team team, Location location) {
      super(team, location);
   }

   @Override
   public int getGLBeginMode() {
      return GL11.GL_TRIANGLES;
   }

   @Override
   public void draw() {
      Location front = new Location(location);
      front.move(size);
      Location left = new Location(location);
      left.rotate(Math.PI / 3 * 2);
      left.move(size);
      Location right = new Location(location);
      right.rotate(-Math.PI / 3 * 2);
      right.move(size);
      
      Color primary = team.getPrimary();
      Color secondary = team.getSecondary();
      
      glColor3f(primary.getRed() / 255f, primary.getGreen() / 255f, primary.getBlue() / 255f);
      glVertex2f((float) front.getX(), (float) front.getY());
      glColor3f(secondary.getRed() / 255f, secondary.getGreen() / 255f, secondary.getBlue() / 255f);
      glVertex2f((float) right.getX(), (float) right.getY());
      glVertex2f((float) left.getX(), (float) left.getY());
   }

}
