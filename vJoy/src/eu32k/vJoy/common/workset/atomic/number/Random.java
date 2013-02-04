package eu32k.vJoy.common.workset.atomic.number;

import com.badlogic.gdx.math.MathUtils;

import eu32k.vJoy.common.workset.atomic.NumberInstance;
import eu32k.vJoy.common.workset.atomic.NumberType;

public class Random extends NumberType {
   private static final long serialVersionUID = 3744548164075589547L;

   public Random() {
      super("Random");
   }

   @Override
   public NumberInstance instanciate(float x, float y) {
      return new NumberInstance(this, x, y) {
         private static final long serialVersionUID = 2620469920518471027L;

         @Override
         public float getValue() {
            return MathUtils.random();
         }
      };
   }
}
