package eu32k.vJoy.common.newConcept;

public class PatchInstance implements Movable {
   private int patchId;
   private Position position;

   public PatchInstance(int patchId, Position position) {
      this.patchId = patchId;
      this.position = position;
   }

   public int getPatchId() {
      return patchId;
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
