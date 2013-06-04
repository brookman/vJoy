package eu32k.vJoy.curveEditor.misc;

import com.badlogic.gdx.math.MathUtils;

public class Range {

   public float fromX = 0.0f;
   public float toX = 1.0f;
   public float fromY = 0.0f;
   public float toY = 1.0f;

   public Range() {

   }

   public Range(float fromX, float toX, float fromY, float toY) {
      this.fromX = fromX;
      this.toX = toX;
      this.fromY = fromY;
      this.toY = toY;
      clamp();
   }

   public void clamp() {
      fromX = MathUtils.clamp(fromX, 0.0f, 1.0f);
      toX = MathUtils.clamp(toX, 0.0f, 1.0f);
      fromY = MathUtils.clamp(fromY, 0.0f, 1.0f);
      toY = MathUtils.clamp(toY, 0.0f, 1.0f);
   }

   public float getSizeX() {
      return toX - fromX;
   }

   public float getSizeY() {
      return toY - fromY;
   }
}
