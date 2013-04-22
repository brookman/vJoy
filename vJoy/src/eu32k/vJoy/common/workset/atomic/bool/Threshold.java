package eu32k.vJoy.common.workset.atomic.bool;

import eu32k.vJoy.common.workset.NumberPort;
import eu32k.vJoy.common.workset.atomic.BooleanInstance;
import eu32k.vJoy.common.workset.atomic.BooleanType;

public class Threshold extends BooleanType {

   private NumberPort input = addPort(new NumberPort("Input"));
   private NumberPort threshold = addPort(new NumberPort("Threshold"));

   public Threshold() {
      super("Threshold");
   }

   @Override
   public BooleanInstance instanciate(float x, float y) {
      return new BooleanInstance(this, x, y) {

         @Override
         public boolean getValue() {
            return getPortValue(input) >= getPortValue(threshold);
         }
      };
   }
}
