package arena;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Game {
   public List<Unit> units;
   
   public void draw() {
      for (Unit unit : units) {
         if (unit instanceof Graphic) {
            ((Graphic) unit).draw();
         }
      }
   }
   
   public void drawHealthBars() {
      for (Unit unit : units) {
         unit.drawHealthBar();
      }
   }
   
   public Game() {
      Team team1 = new Team(Color.RED, Color.BLUE);
      Team team2 = new Team(Color.BLUE, Color.WHITE);
      units = new ArrayList<>();
      units.add(new TriangleFighter(team1, new Location(100,100,0)));
      units.add(new TriangleFighter(team2, new Location(100,70,Math.PI)));
   }
}
