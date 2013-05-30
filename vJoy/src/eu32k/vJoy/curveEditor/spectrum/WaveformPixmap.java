package eu32k.vJoy.curveEditor.spectrum;

import com.badlogic.gdx.graphics.Pixmap;

public class WaveformPixmap extends Pixmap {

   private short[] samples;

   public WaveformPixmap(int width, int height, short[] samples) {
      super(width, height, Format.RGBA8888);
      this.samples = samples;
   }

   private void doUpdate(int from, int to) {

   }

}
