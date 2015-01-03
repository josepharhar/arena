package arena;

import static org.lwjgl.opengl.GL11.glVertex2f;

import java.awt.Color;


public abstract class Unit {
   protected Team team;
   protected Location location;
   protected double size = 25;
   
   private double health = 100;
   private double damage = 0;
   
   public Unit(Team team, Location location) {
      this.team = team;
      this.location = location;
   }

   public Location getLocation() {
      return location;
   }

   public void setLocation(Location location) {
      this.location = location;
   }
   
   public void drawHealthBar() {
      glVertex2f((float) (location.getX() - health / 2), (float) (location.getY() + size + 13));
      glVertex2f((float) (location.getX() + health / 2 - damage), (float) (location.getY() + size + 13));
      glVertex2f((float) (location.getX() + health / 2 - damage), (float) (location.getY() + size + 10));
      glVertex2f((float) (location.getX() - health / 2), (float) (location.getY() + size + 10));
      
   }
}
