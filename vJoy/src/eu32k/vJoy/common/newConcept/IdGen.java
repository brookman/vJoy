package eu32k.vJoy.common.newConcept;

public class IdGen {

   private static Integer counter = 0;

   public static int getUniqueId() {
      int id;
      synchronized (counter) {
         id = counter;
         counter++;
      }
      return id;
   }
}
