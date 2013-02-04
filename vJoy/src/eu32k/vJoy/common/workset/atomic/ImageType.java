package eu32k.vJoy.common.workset.atomic;

import eu32k.vJoy.common.workset.DataType;
import eu32k.vJoy.common.workset.Type;

public abstract class ImageType extends Type {
   private static final long serialVersionUID = -1917531164380560970L;

   public ImageType(String name) {
      super(name, DataType.IMAGE);
   }

   @Override
   public abstract ImageInstance instanciate(float x, float y);
}
