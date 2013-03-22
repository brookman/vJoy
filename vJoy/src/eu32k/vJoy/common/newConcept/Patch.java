package eu32k.vJoy.common.newConcept;

import java.util.ArrayList;
import java.util.List;

public class Patch extends Compoundable {

   private int id;
   private List<Movable> movables = new ArrayList<Movable>();
   private List<Connection> connections = new ArrayList<Connection>();
   private int exitId;

   public Patch(String name, Type type) {
      super(name, type);
      id = IdGen.getUniqueId();
   }

   public int getId() {
      return id;
   }

   public int getExitId() {
      return exitId;
   }

   public void setExitId(int exitId) {
      this.exitId = exitId;
   }

   public List<Movable> getMovables() {
      return movables;
   }

   public List<Connection> getConnections() {
      return connections;
   }
}
