package eu32k.vJoy.curveEditor.spectrum;

import java.awt.Color;

import com.badlogic.gdx.audio.analysis.KissFFT;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import eu32k.vJoy.curveEditor.audio.AudioTrack;
import eu32k.vJoy.curveEditor.misc.ArrayTools;
import eu32k.vJoy.curveEditor.misc.FloatArray;

public class SpectrumPixmap extends Pixmap {

   private final static int SAMPLES = 4096 * 2;

   private KissFFT fft = new KissFFT(SAMPLES);
   private boolean isUpdating = false;

   private Thread updateThread;
   private boolean cancel = false;

   private short[] allSamples;

   private double max = 1;
   private double maxEnergy = 1;

   private AudioTrack track;

   public SpectrumPixmap(int width, int height, AudioTrack track) {
      super(width, height, Format.RGBA8888);
      this.track = track;
      allSamples = track.getCombined();
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
            for (int i = 0; i < 8; i++) {
               doUpdate(area, 8, i);
            }

            isUpdating = false;
         }
      });
      updateThread.start();
   }

   private void doUpdate(Rectangle area, int passes, int pass) {
      float[] spectrum = new float[SAMPLES / 2 + 1];

      for (int i = pass; i < getWidth(); i += passes) {
         if (cancel) {
            return;
         }

         double normalizedPositionInSubRangeX = (double) i / (double) getWidth();
         double normalizedPositionInGlobalRangeX = area.getX() + area.getWidth() * normalizedPositionInSubRangeX;

         int centerX = (int) Math.round(normalizedPositionInGlobalRangeX * allSamples.length);
         int fromX = MathUtils.clamp(centerX - SAMPLES / 2, 0, allSamples.length - 1);
         int toX = MathUtils.clamp(centerX + SAMPLES / 2, 0, allSamples.length - 1);

         short[] range = track.getRange(fromX, toX);

         ArrayTools.applyGauss(range, 16.0);

         fft.spectrum(range, spectrum);
         FloatArray na = new FloatArray(spectrum);
         double energy = na.sum();
         maxEnergy = Math.max(maxEnergy, energy);
         // System.out.println("energy " + energy + " max " + maxEnergy);

         for (int j = 0; j < getHeight(); j++) {
            if (cancel) {
               return;
            }

            double normalizedPositionInSubRangeY = (double) j / (double) getHeight();
            double normalizedPositionInGlobalRangeY = area.getY() + area.getHeight() * normalizedPositionInSubRangeY;
            int posY = (int) Math.round(normalizedPositionInGlobalRangeY * spectrum.length);
            posY = MathUtils.clamp(posY, 0, spectrum.length - 2);

            double magnitude = Math.sqrt(spectrum[posY] * spectrum[posY] + spectrum[posY + 1] + spectrum[posY + 1]);

            max = Math.max(max, magnitude);

            setColor(0x000000ff | getColor(magnitude / (max * 1.1)) << 8);
            drawRectangle(i, getHeight() - j, passes - pass, 1);
         }

      }
   }

   private static int getColor(double value) {
      return getColor(value, 0.0, 0.3, false);
   }

   private static int getColor(double value, double min, double max, boolean isLog) {

      if (Double.isNaN(value)) {
         return new Color(0, 0, 0, 0).getRGB();
      }

      value = Math.max(value, min);
      value = Math.min(value, max);

      double normalized = 0.0;

      if (isLog) {
         normalized = (Math.log10(value) - Math.log10(min)) / (Math.log10(max) - Math.log10(min));
      } else {
         normalized = (value - min) / (max - min);
      }

      double fadeoff = 0.1;
      double brightness = normalized < fadeoff ? normalized * (1.0 / fadeoff) : 1.0;

      return Color.HSBtoRGB((1.0f - (float) normalized) * 0.75f, 1.0f, (float) brightness); // cutoff
                                                                                            // at
                                                                                            // 0.75
                                                                                            // because
                                                                                            // hue
                                                                                            // is
                                                                                            // "circular"
   }
}
