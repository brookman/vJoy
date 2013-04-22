package eu32k.vJoy.common.workset.atomic;

import eu32k.vJoy.common.workset.DataType;
import eu32k.vJoy.common.workset.Type;

public abstract class NumberType extends Type {

   public NumberType(String name) {
      super(name, DataType.NUMBER);
   }

   @Override
   public abstract NumberInstance instanciate(float x, float y);
}
