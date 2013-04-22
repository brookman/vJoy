package eu32k.vJoy.common.workset.atomic.bool;

import eu32k.vJoy.common.workset.atomic.BooleanInstance;
import eu32k.vJoy.common.workset.atomic.BooleanType;

public class False extends BooleanType {

   public False() {
      super("False");
   }

   @Override
   public BooleanInstance instanciate(float x, float y) {
      return new BooleanInstance(this, x, y) {

         @Override
         public boolean getValue() {
            return false;
         }
      };
   }
}
