package eu32k.vJoy.curveEditor.visualization.curve;

import com.badlogic.gdx.math.MathUtils;

import eu32k.vJoy.curveEditor.misc.ArrayTools;
import eu32k.vJoy.curveEditor.misc.Range;
import eu32k.vJoy.curveEditor.visualization.ExtendedPixmap;

public class CurvePixmap extends ExtendedPixmap {

   private float[] data;
   private float scaling;

   public CurvePixmap(int width, int height) {
      super(width, height);
   }

   public void setData(float[] data, float scaling) {
      this.data = data;
      this.scaling = scaling;
   }

   @Override
   protected void update(Range range, float position, int pass) {
      if (data == null) {
         return;
      }

      for (int i = 0; i < getWidth(); i++) {
         if (cancel) {
            return;
         }

         float normalizedPositionInSubRangeX = (float) i / (float) getWidth();
         float posX = range.fromX + range.getSizeX() * normalizedPositionInSubRangeX;

         float value = MathUtils.clamp(ArrayTools.getNormalizedValue(data, posX) / scaling, 0.0f, 1.0f);
         int h = MathUtils.round(getHeight() * value);

         setColor(0xffffffff);

         fillRectangle(i, getHeight() - h, 1, h);

         setChanged(true);
      }
   }
}
