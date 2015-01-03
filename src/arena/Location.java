package arena;

public class Location {
   //positive = right
   private double x;
   //positive = up
   private double y;
   //radians, 0 = directly to the right
   private double angle;
   
   public Location(double x, double y, double angle) {
      this.angle = angle;
      this.x = x;
      this.y = y;
   }
   
   public Location(Location other) {
      this.angle = other.angle;
      this.x = other.x;
      this.y = other.y;
   }

   public double getAngle() {
      return angle;
   }
   
   public double getX() {
      return x;
   }
   
   public double getY() {
      return y;
   }
   
   public void setX(double x) {
      this.x = x;
   }
   
   public void setY(double y) {
      this.y = y;
   }
   
   public void setAngle(double angle) {
      this.angle = angle;
   }
   
   public void rotate(double amount) {
      this.angle += amount;
   }
   
   public void move(double amount) {
      this.x += amount * Math.cos(angle);
      this.y += amount * Math.sin(angle);
   }
   
   public void move(double x, double y) {
      this.x += x;
      this.y += y;
   }
}
