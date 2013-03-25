package eu32k.vJoy.common.newConcept;

public class BasicType extends Compoundable implements Movable {

   private Position position;

   public BasicType(String name, Type type) {
      super(name, type);
   }

   @Override
   public Position getPosition() {
      return position;
   }

   @Override
   public void setPosition(Position position) {
      this.position = position;
   }

}
