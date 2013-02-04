package eu32k.vJoy.common.workset.atomic.bool;

import eu32k.vJoy.common.workset.BooleanPort;
import eu32k.vJoy.common.workset.atomic.BooleanInstance;
import eu32k.vJoy.common.workset.atomic.BooleanType;

public class Or extends BooleanType {
   private static final long serialVersionUID = 2348931216670861787L;

   private BooleanPort input1 = addPort(new BooleanPort("Input 1"));
   private BooleanPort input2 = addPort(new BooleanPort("Input 2"));

   public Or() {
      super("Or");
   }

   @Override
   public BooleanInstance instanciate(float x, float y) {
      return new BooleanInstance(this, x, y) {
         private static final long serialVersionUID = 2620469920518471027L;

         @Override
         public boolean getValue() {
            return getPortValue(input1) || getPortValue(input2);
         }
      };
   }
}
