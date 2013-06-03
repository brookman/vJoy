package eu32k.vJoy.curveEditor.misc;

import com.badlogic.gdx.math.MathUtils;

public class ArrayTools {
   public static short getNormalizedValue(short[] array, float position) {
      return array[MathUtils.clamp(MathUtils.round(position * array.length), 0, array.length - 1)];
   }

   public static short getNormalizedValue(short[] array, double position) {
      return array[MathUtils.clamp((int) Math.round(position * array.length), 0, array.length - 1)];
   }

   public static void applyGauss(short[] data, double width) {
      for (int r = 0; r < data.length; r++) {
         double x = r / (double) data.length * width - width / 2.0;
         data[r] = (short) (data[r] * Math.exp(-1.0 * (x * x)));
      }
   }
}
