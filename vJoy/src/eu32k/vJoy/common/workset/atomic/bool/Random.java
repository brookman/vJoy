package eu32k.vJoy.common.workset.atomic.bool;

import com.badlogic.gdx.math.MathUtils;

import eu32k.vJoy.common.workset.atomic.BooleanInstance;
import eu32k.vJoy.common.workset.atomic.BooleanType;

public class Random extends BooleanType {

   public Random() {
      super("Random");
   }

   @Override
   public BooleanInstance instanciate(float x, float y) {
      return new BooleanInstance(this, x, y) {

         @Override
         public boolean getValue() {
            return MathUtils.randomBoolean();
         }
      };
   }
}
