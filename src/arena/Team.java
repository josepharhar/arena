package arena;

import java.awt.Color;

public class Team {
   private Color primary;
   private Color secondary;

   public Team(Color primary, Color secondary) {
      super();
      this.primary = primary;
      this.secondary = secondary;
   }

   public Color getPrimary() {
      return primary;
   }

   public void setPrimary(Color primary) {
      this.primary = primary;
   }

   public Color getSecondary() {
      return secondary;
   }

   public void setSecondary(Color secondary) {
      this.secondary = secondary;
   }
}
