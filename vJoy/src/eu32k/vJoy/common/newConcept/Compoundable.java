package eu32k.vJoy.common.newConcept;

import java.util.ArrayList;
import java.util.List;

public abstract class Compoundable extends Entity {

   private List<Port> ports = new ArrayList<Port>();

   public Compoundable(String name, Type type) {
      super(name, type);
   }

   public List<Port> getPorts() {
      return ports;
   }
}
