package eu32k.vJoy.common.workset.atomic;

import eu32k.vJoy.common.workset.Instance;

public abstract class NumberInstance extends Instance {

   public NumberInstance(NumberType type, float x, float y) {
      super(type, x, y);
   }

   public abstract float getValue();
}
