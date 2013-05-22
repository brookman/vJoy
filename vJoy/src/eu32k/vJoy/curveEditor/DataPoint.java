package eu32k.vJoy.curveEditor;

import com.badlogic.gdx.math.Interpolation;

public class DataPoint {// implements Comparable<DataPoint> {

   public enum InterPolationType {
      LINEAR(Interpolation.linear), POW(Interpolation.pow4), POW_IN(Interpolation.pow4In), POW_OUT(Interpolation.pow4Out), CEIL(new Interpolation() {
         @Override
         public float apply(float a) {
            return 1.0f;
         }
      });

      private Interpolation interpolation;

      private InterPolationType(Interpolation interpolation) {
         this.interpolation = interpolation;
      }

      public Interpolation getInterpolation() {
         return interpolation;
      }
   }

   // public int x;
   public int y;
   public InterPolationType connectionType = InterPolationType.LINEAR;

   public DataPoint(int y) {
      // this.x = x;
      this.y = y;
   }

   // @Override
   // public int compareTo(DataPoint o) {
   // return Integer.valueOf(x).compareTo(o.x);
   // }
}
