package eu32k.vJoy.common.workset.atomic.bool;

import eu32k.vJoy.common.workset.atomic.BooleanInstance;
import eu32k.vJoy.common.workset.atomic.BooleanType;

public class True extends BooleanType {
   private static final long serialVersionUID = 3744548164075589547L;

   public True() {
      super("True");
   }

   @Override
   public BooleanInstance instanciate(float x, float y) {
      return new BooleanInstance(this, x, y) {
         private static final long serialVersionUID = 7278625058018706784L;

         @Override
         public boolean getValue() {
            return true;
         }
      };
   }
}
