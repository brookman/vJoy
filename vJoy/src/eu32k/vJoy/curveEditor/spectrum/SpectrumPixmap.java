package eu32k.vJoy.curveEditor.spectrum;

import java.awt.Color;

import com.badlogic.gdx.math.MathUtils;

import eu32k.vJoy.curveEditor.audio.AudioTrack;
import eu32k.vJoy.curveEditor.misc.ArrayTools;
import eu32k.vJoy.curveEditor.misc.FloatArray;
import eu32k.vJoy.curveEditor.misc.Range;

public class SpectrumPixmap extends ExtendedPixmap {

   private short[] allSamples;
   private float[][] processed;

   private double max = 1;
   private double maxEnergy = 1;

   public SpectrumPixmap(int width, int height, AudioTrack track) {
      super(width, height, track, 8);
      allSamples = track.getCombined();

      processed = new float[allSamples.length / 200][400];
      float[] spectrum = new float[SAMPLES / 2];

      for (int i = 0; i < allSamples.length / 200; i++) {
         int pos = i * 200;
         short[] rangeArray = AudioTrack.getRange(track.getCombined(), i, i + SAMPLES, SAMPLES);
         ArrayTools.applyGauss(rangeArray, 16.0);
         fft.spectrum(rangeArray, spectrum);
         processed[i] = new float[400];
         if (Math.random() < 0.001) {
            System.out.println(i + " of " + allSamples.length / 200);
         }
      }
   }

   @Override
   protected void update(Range range, float position, int pass) {

      float[] spectrum = new float[SAMPLES / 2];

      for (int i = pass; i < getWidth(); i += passes) {
         if (cancel) {
            return;
         }

         double normalizedPositionInSubRangeX = (double) i / (double) getWidth();
         double normalizedPositionInGlobalRangeX = range.fromX + range.getSizeX() * normalizedPositionInSubRangeX;

         int centerX = (int) Math.round(normalizedPositionInGlobalRangeX * allSamples.length);
         int fromX = MathUtils.clamp(centerX - SAMPLES / 2, 0, allSamples.length - 1);
         int toX = MathUtils.clamp(centerX + SAMPLES / 2, 0, allSamples.length - 1);

         short[] rangeArray = track.getRange(fromX, toX, SAMPLES);

         ArrayTools.applyGauss(rangeArray, 16.0);

         fft.spectrum(rangeArray, spectrum);
         FloatArray na = new FloatArray(spectrum);
         double energy = na.sum();
         maxEnergy = Math.max(maxEnergy, energy);

         for (int j = 0; j < getHeight(); j++) {

            double normalizedPositionInSubRangeY = (double) j / (double) getHeight();
            double normalizedPositionInGlobalRangeY = range.fromY + range.getSizeY() * normalizedPositionInSubRangeY;
            int posY = (int) Math.round(normalizedPositionInGlobalRangeY * spectrum.length);
            posY = MathUtils.clamp(posY, 1, spectrum.length - 2);

            double magnitude = Math.sqrt(spectrum[posY] * spectrum[posY] + spectrum[posY + 1] + spectrum[posY + 1]);

            max = Math.max(max, magnitude);

            setColor(0x000000ff | getColor(magnitude / (max * 1.0)) << 8);
            drawRectangle(i, getHeight() - j, passes - pass, 1);

         }

         int hPos = 30;

         double lowMagnitude = Math.sqrt(spectrum[hPos] * spectrum[hPos] + spectrum[hPos + 1] + spectrum[hPos + 1]) / 300.0;

         float m = MathUtils.clamp((float) lowMagnitude, 0.0f, 1.0f);
         int h = MathUtils.round(getHeight() * m);

         setColor(0xffffffaa);
         fillRectangle(i, getHeight() - h, 1, h);

         if (m > 0.5) {
            setColor(0xff0000aa);
            // fillRectangle(i, getHeight() - h, 1, h);
            fillRectangle(i, 0, 1, getHeight());
         }

         setChanged(true);
      }

      int x = MathUtils.round((position - range.fromX) / range.getSizeX() * getWidth());
      setColor(0xffffffff);
      for (int j = 0; j < getHeight(); j++) {
         drawPixel(x, j);
      }
   }

   private static int getColor(double value) {
      return getColor(value, 0.0, 1.0, false);
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

      return Color.HSBtoRGB((1.0f - (float) normalized) * 0.75f, 1.0f, (float) brightness);
   }
}
