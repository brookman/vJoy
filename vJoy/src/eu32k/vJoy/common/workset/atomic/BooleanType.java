package eu32k.vJoy.common.workset.atomic;

import eu32k.vJoy.common.workset.DataType;
import eu32k.vJoy.common.workset.Type;

public abstract class BooleanType extends Type {
   private static final long serialVersionUID = -6320609234037230979L;

   public BooleanType(String name) {
      super(name, DataType.BOOLEAN);
   }

   @Override
   public abstract BooleanInstance instanciate(float x, float y);
}
