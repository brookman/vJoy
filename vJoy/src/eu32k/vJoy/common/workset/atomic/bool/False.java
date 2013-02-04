package eu32k.vJoy.common.workset.atomic.bool;

import eu32k.vJoy.common.workset.atomic.BooleanInstance;
import eu32k.vJoy.common.workset.atomic.BooleanType;

public class False extends BooleanType {
   private static final long serialVersionUID = 2245107899228029129L;

   public False() {
      super("False");
   }

   @Override
   public BooleanInstance instanciate(float x, float y) {
      return new BooleanInstance(this, x, y) {
         private static final long serialVersionUID = 7278625058018706784L;

         @Override
         public boolean getValue() {
            return false;
         }
      };
   }
}
