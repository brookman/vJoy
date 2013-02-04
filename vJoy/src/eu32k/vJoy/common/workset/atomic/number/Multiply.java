package eu32k.vJoy.common.workset.atomic.number;

import eu32k.vJoy.common.workset.NumberPort;
import eu32k.vJoy.common.workset.atomic.NumberInstance;
import eu32k.vJoy.common.workset.atomic.NumberType;

public class Multiply extends NumberType {
   private static final long serialVersionUID = 3744548164075589547L;

   private NumberPort input1 = addPort(new NumberPort("Input 1"));
   private NumberPort input2 = addPort(new NumberPort("Input 2"));

   public Multiply() {
      super("Multiply");
   }

   @Override
   public NumberInstance instanciate(float x, float y) {
      return new NumberInstance(this, x, y) {
         private static final long serialVersionUID = 2620469920518471027L;

         @Override
         public float getValue() {
            return getPortValue(input1) * getPortValue(input2);
         }
      };
   }
}
