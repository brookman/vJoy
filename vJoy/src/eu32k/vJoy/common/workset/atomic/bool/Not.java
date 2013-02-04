package eu32k.vJoy.common.workset.atomic.bool;

import eu32k.vJoy.common.workset.BooleanPort;
import eu32k.vJoy.common.workset.atomic.BooleanInstance;
import eu32k.vJoy.common.workset.atomic.BooleanType;

public class Not extends BooleanType {
   private static final long serialVersionUID = 2001995347809638829L;

   private BooleanPort input = addPort(new BooleanPort("Input"));

   public Not() {
      super("Not");
   }

   @Override
   public BooleanInstance instanciate(float x, float y) {
      return new BooleanInstance(this, x, y) {
         private static final long serialVersionUID = -1623397275102697638L;

         @Override
         public boolean getValue() {
            return !getPortValue(input);
         }
      };
   }
}
