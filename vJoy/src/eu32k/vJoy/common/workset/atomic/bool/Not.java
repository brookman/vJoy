package eu32k.vJoy.common.workset.atomic.bool;

import eu32k.vJoy.common.workset.BooleanPort;
import eu32k.vJoy.common.workset.atomic.BooleanInstance;
import eu32k.vJoy.common.workset.atomic.BooleanType;

public class Not extends BooleanType {

   private BooleanPort input = addPort(new BooleanPort("Input"));

   public Not() {
      super("Not");
   }

   @Override
   public BooleanInstance instanciate(float x, float y) {
      return new BooleanInstance(this, x, y) {

         @Override
         public boolean getValue() {
            return !getPortValue(input);
         }
      };
   }
}
