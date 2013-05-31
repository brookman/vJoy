package eu32k.vJoy.curveEditor.misc;

public class ArrayGauss {
   public static void applyGauss(short[] data, double width) {
      for (int r = 0; r < data.length; r++) {
         double x = r / (double) data.length * width - width / 2.0;
         data[r] = (short) (data[r] * Math.exp(-1.0 * (x * x)));
      }
   }
}
