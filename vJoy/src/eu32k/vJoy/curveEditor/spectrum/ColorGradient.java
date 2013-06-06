package eu32k.vJoy.curveEditor.spectrum;

import java.awt.Color;

public class ColorGradient {

   private double min;
   private double max;
   private double fadeoff;

   public ColorGradient(double min, double max, double fadeoff) {
      this.min = min;
      this.max = max;
      this.fadeoff = fadeoff;
   }

   public int getColor(double value) {
      return getColor(value, min, max);
   }

   public int getColor(double value, double min, double max) {

      if (Double.isNaN(value)) {
         return new Color(0, 0, 0, 0).getRGB();
      }

      value = Math.max(value, min);
      value = Math.min(value, max);

      double normalized = 0.0;

      normalized = (value - min) / (max - min);

      double alpha = normalized < fadeoff ? normalized * (1.0 / fadeoff) : 1.0;

      return Color.HSBtoRGB((1.0f - (float) normalized) * 0.75f, 1.0f, (float) alpha);
   }

}
