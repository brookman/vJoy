package eu32k.vJoy.common.workset.atomic.number;

import com.badlogic.gdx.math.MathUtils;

import eu32k.vJoy.common.workset.NumberPort;
import eu32k.vJoy.common.workset.atomic.NumberInstance;
import eu32k.vJoy.common.workset.atomic.NumberType;

public class SimpleFunction extends NumberType {
   private static final long serialVersionUID = 3744548164075589547L;

   public static enum FunctionType {
      NEUTRAL, NEG, SIN, COS, NEG_SIN, NEG_COS, TRIANGLE
   }

   private NumberPort input = addPort(new NumberPort("Input"));

   public SimpleFunction() {
      super("Simple Function");
   }

   @Override
   public NumberInstance instanciate(float x, float y) {
      return new SimpleFunctionInstance(this, x, y);
   }

   public class SimpleFunctionInstance extends NumberInstance {
      private static final long serialVersionUID = 765797734513673619L;

      public FunctionType functionType = FunctionType.NEUTRAL;

      public SimpleFunctionInstance(NumberType type, float x, float y) {
         super(type, x, y);
      }

      @Override
      public float getValue() {
         float value = getPortValue(input);
         if (functionType == FunctionType.NEUTRAL) {
            return value;
         } else if (functionType == FunctionType.NEG) {
            return 1.0f - value;
         } else if (functionType == FunctionType.SIN) {
            return (MathUtils.sin(value * MathUtils.PI * 2.0f) + 1.0f) / 2.0f;
         } else if (functionType == FunctionType.COS) {
            return (MathUtils.cos(value * MathUtils.PI * 2.0f) + 1.0f) / 2.0f;
         } else if (functionType == FunctionType.NEG_SIN) {
            return 1.0f - (MathUtils.sin(value * MathUtils.PI * 2.0f) + 1.0f) / 2.0f;
         } else if (functionType == FunctionType.NEG_COS) {
            return 1.0f - (MathUtils.cos(value * MathUtils.PI * 2.0f) + 1.0f) / 2.0f;
         } else if (functionType == FunctionType.TRIANGLE) {
            if (value <= 0.5f) {
               return 2.0f * value;
            } else {
               return 2.0f * (1.0f - value);
            }
         }
         return 0.0f;
      }
   }
}