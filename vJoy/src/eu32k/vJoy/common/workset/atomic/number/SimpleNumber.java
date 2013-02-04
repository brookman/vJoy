package eu32k.vJoy.common.workset.atomic.number;

import eu32k.vJoy.common.workset.atomic.NumberInstance;
import eu32k.vJoy.common.workset.atomic.NumberType;

public class SimpleNumber extends NumberType {
   private static final long serialVersionUID = -5335050847175328302L;

   public SimpleNumber() {
      super("Number");
   }

   @Override
   public NumberInstance instanciate(float x, float y) {
      return new Testomat(this, x, y);
   }

   public class Testomat extends NumberInstance {
      private static final long serialVersionUID = 572047342102308310L;

      public Testomat(NumberType type, float x, float y) {
         super(type, x, y);
      }

      public float value = 0.0f;

      @Override
      public float getValue() {
         return value;
      }

   }
}
