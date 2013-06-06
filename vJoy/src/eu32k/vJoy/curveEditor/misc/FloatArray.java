package eu32k.vJoy.curveEditor.misc;

import com.badlogic.gdx.math.MathUtils;

public class FloatArray {

   private float[] array;

   public FloatArray(float[] array) {
      this.array = array;
   }

   public float getValueAt(float position) {
      return getValueAt((double) position);
   }

   public float getValueAt(double position) {
      int index = MathUtils.clamp((int) Math.round(array.length * position), 0, array.length - 1);
      return array[index];
   }

   public float sum(float from, float to) {
      int fromInt = MathUtils.round(from * array.length);
      int toInt = MathUtils.round(to * array.length);
      float sum = 0;
      for (int i = fromInt; i < toInt; i++) {
         sum += array[i];
      }
      return sum;
   }

   public float sum() {
      float sum = 0;
      for (int i = 0; i < array.length; i++) {
         sum += array[i];
      }
      return sum;
   }

   public int size() {
      return array.length;
   }

   public int length() {
      return array.length;
   }
}
