package eu32k.vJoy.common.workset.atomic.bool;

import eu32k.vJoy.common.workset.BooleanPort;
import eu32k.vJoy.common.workset.atomic.BooleanInstance;
import eu32k.vJoy.common.workset.atomic.BooleanType;

public class Xor extends BooleanType {

   private BooleanPort input1 = addPort(new BooleanPort("Input 1"));
   private BooleanPort input2 = addPort(new BooleanPort("Input 2"));

   public Xor() {
      super("Xor");
   }

   @Override
   public BooleanInstance instanciate(float x, float y) {
      return new BooleanInstance(this, x, y) {

         @Override
         public boolean getValue() {
            return xor(getPortValue(input1), getPortValue(input2));
         }
      };
   }

   private boolean xor(boolean x, boolean y) {
      return (x || y) && !(x && y);
   }
}
