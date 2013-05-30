package eu32k.vJoy.common.newConcept;

public class PatchInstance implements Movable {
   private Patch patch;
   private Position position;

   public PatchInstance(Patch patch, Position position) {
      this.patch = patch;
      this.position = position;
   }

   public Patch getPatch() {
      return patch;
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
