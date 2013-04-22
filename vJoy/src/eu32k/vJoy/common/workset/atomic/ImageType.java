package eu32k.vJoy.common.workset.atomic;

import eu32k.vJoy.common.workset.DataType;
import eu32k.vJoy.common.workset.Type;

public abstract class ImageType extends Type {

   public ImageType(String name) {
      super(name, DataType.IMAGE);
   }

   @Override
   public abstract ImageInstance instanciate(float x, float y);
}
