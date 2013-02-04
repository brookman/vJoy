package eu32k.vJoy.common;

import com.badlogic.gdx.graphics.Color;

public class Colors {

   public static Color GREY = new Color(0.3f, 0.3f, 0.3f, 1.0f);

   // Android colors
   public static Color BLUE = makeLightColor(0x33, 0xB5, 0xE5);
   public static Color PURPLE = makeLightColor(0xAA, 0x66, 0xCC);
   public static Color GREEN = makeLightColor(0x99, 0xCC, 0x00);
   public static Color YELLOW = makeLightColor(0xFF, 0xBB, 0x44);
   public static Color RED = makeLightColor(0xFF, 0x44, 0x44);
   public static Color DARK_BLUE = makeDarkColor(0x00, 0x99, 0xCC);
   public static Color DARK_PURPLE = makeDarkColor(0x99, 0x33, 0xCC);
   public static Color DARK_GREEN = makeDarkColor(0x66, 0x99, 0x00);
   public static Color DARK_YELLOW = makeDarkColor(0xFF, 0x88, 0x00);
   public static Color DARK_RED = makeDarkColor(0xCC, 0x00, 0x00);

   public static Color makeLightColor(int r, int g, int b) {
      return makeColor(r, g, b, 1.8f);
   }

   public static Color makeDarkColor(int r, int g, int b) {
      return makeColor(r, g, b, 1.0f);
   }

   public static Color makeColor(int r, int g, int b, float shift) {
      float rf = (float) r / (float) 0xFF;
      float gf = (float) g / (float) 0xFF;
      float bf = (float) b / (float) 0xFF;

      rf = Math.min(rf * shift, 1.0f);
      gf = Math.min(gf * shift, 1.0f);
      bf = Math.min(bf * shift, 1.0f);

      return new Color(rf, gf, bf, 1.0f);
   }
}
