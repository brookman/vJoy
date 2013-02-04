package eu32k.vJoy.common.workset.atomic.bool;

import com.badlogic.gdx.math.MathUtils;

import eu32k.vJoy.common.workset.atomic.BooleanInstance;
import eu32k.vJoy.common.workset.atomic.BooleanType;

public class Random extends BooleanType {
   private static final long serialVersionUID = 1539835461757777322L;

   public Random() {
      super("Random");
   }

   @Override
   public BooleanInstance instanciate(float x, float y) {
      return new BooleanInstance(this, x, y) {
         private static final long serialVersionUID = 2620469920518471027L;

         @Override
         public boolean getValue() {
            return MathUtils.randomBoolean();
         }
      };
   }
}
