package eu32k.vJoy.common.workset.atomic;

import eu32k.vJoy.common.workset.DataType;
import eu32k.vJoy.common.workset.Type;

public abstract class NumberType extends Type {
   private static final long serialVersionUID = -1917531164380560970L;

   public NumberType(String name) {
      super(name, DataType.NUMBER);
   }

   @Override
   public abstract NumberInstance instanciate(float x, float y);
}
