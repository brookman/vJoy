package eu32k.vJoy.curveEditor.visualization.waveform;

import eu32k.vJoy.curveEditor.misc.ArrayTools;
import eu32k.vJoy.curveEditor.misc.Range;
import eu32k.vJoy.curveEditor.visualization.ExtendedPixmap;

public class WaveformPixmap extends ExtendedPixmap {

   private short[] channel1;
   private short[] channel2;

   public WaveformPixmap(int width, int height, short[] channel1, short[] channel2) {
      super(width, height);
      this.channel1 = channel1;
      this.channel2 = channel2;
   }

   @Override
   protected void update(Range range, float position, int pass) {

      setColor(0, 0, 0, 1);
      fillRectangle(0, 0, getWidth(), getHeight());

      for (int i = 0; i < getWidth(); i++) {
         if (cancel) {
            return;
         }

         double normalizedPositionInSubRangeX = (double) i / (double) getWidth();
         double normalizedPositionInGlobalRangeX = range.fromX + range.getSizeX() * normalizedPositionInSubRangeX;

         short value1 = ArrayTools.getNormalizedValue(channel1, normalizedPositionInGlobalRangeX);
         short value2 = ArrayTools.getNormalizedValue(channel2, normalizedPositionInGlobalRangeX);

         int h1 = (int) Math.round((double) value1 / (double) Short.MAX_VALUE * getHeight() * 0.5);
         int h2 = (int) Math.round((double) value2 / (double) Short.MAX_VALUE * getHeight() * 0.5);

         setColor(0xff0000ff);
         drawRectangle(i, (int) Math.round(getHeight() * 0.5), 1, Math.abs(h1) > Math.abs(h2) ? h1 : h2);
         setColor(0x00ff00ff);
         drawRectangle(i, (int) Math.round(getHeight() * 0.5), 1, Math.abs(h1) > Math.abs(h2) ? h2 : h1);

         // for (int j = 0; j < getHeight(); j++) {
         // if (cancel) {
         // return;
         // }
         //
         // double normalizedPositionInSubRangeY = (double) j / (double) getHeight();
         // double normalizedPositionInGlobalRangeY = area.getY() + area.getHeight() * normalizedPositionInSubRangeY;
         // int posY = (int) Math.round(normalizedPositionInGlobalRangeY * spectrum.length);
         // posY = MathUtils.clamp(posY, 2, spectrum.length - 2);
         //
         // double magnitude = Math.sqrt(spectrum[posY] * spectrum[posY] + spectrum[posY + 1] + spectrum[posY + 1]);
         //
         // max = Math.max(max, magnitude);
         //
         // setColor(0x000000ff | getColor(magnitude / (max * 1.1)) << 8);
         // drawRectangle(i, getHeight() - j, passes - pass, 1);
         // }
         setChanged(true);
      }

   }
}
