package eu32k.vJoy.curveEditor.spectrum;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Rectangle;

import eu32k.vJoy.curveEditor.audio.AudioTrack;
import eu32k.vJoy.curveEditor.misc.ArrayTools;

public class WaveformPixmap extends Pixmap {

   private boolean isUpdating = false;

   private Thread updateThread;
   private boolean cancel = false;

   private AudioTrack track;

   public WaveformPixmap(int width, int height, AudioTrack track) {
      super(width, height, Format.RGBA8888);
      this.track = track;
   }

   public synchronized void updatePixmap(final Rectangle area) {
      if (isUpdating) {
         cancel = true;
         try {
            updateThread.join();
         } catch (InterruptedException e) {
            // NOP
         }
      }
      cancel = false;
      isUpdating = true;

      updateThread = new Thread(new Runnable() {
         @Override
         public void run() {
            doUpdate(area);
            isUpdating = false;
         }
      });
      updateThread.start();
   }

   private void doUpdate(Rectangle area) {

      setColor(0, 0, 0, 1);
      fillRectangle(0, 0, getWidth(), getHeight());

      for (int i = 0; i < getWidth(); i++) {
         if (cancel) {
            return;
         }

         double normalizedPositionInSubRangeX = (double) i / (double) getWidth();
         double normalizedPositionInGlobalRangeX = area.getX() + area.getWidth() * normalizedPositionInSubRangeX;

         short value1 = ArrayTools.getNormalizedValue(track.getChannel1(), normalizedPositionInGlobalRangeX);
         short value2 = ArrayTools.getNormalizedValue(track.getChannel2(), normalizedPositionInGlobalRangeX);

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
      }
   }
}
