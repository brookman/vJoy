package eu32k.vJoy.common.newConcept;

public abstract class Entity {

   private String name;
   private Type type;

   public Entity(String name, Type type) {
      this.name = name;
      this.type = type;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Type getType() {
      return type;
   }

   public void setType(Type type) {
      this.type = type;
   }

   @Override
   public String toString() {
      return name;
   }
}
