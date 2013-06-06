package eu32k.vJoy.curveEditor.spectrum;

import java.io.File;

import com.badlogic.gdx.math.MathUtils;

import eu32k.vJoy.curveEditor.audio.AudioTrack;
import eu32k.vJoy.curveEditor.misc.Range;

public class SpectrumPixmap extends ExtendedPixmap {

   private Spectrum spectrum;
   private ColorGradient color;
   public float scaleZ = 1.0f;
   public float level = 0.02f;
   public float threshold = 0.5f;

   public SpectrumPixmap(int width, int height, AudioTrack track) {
      super(width, height, track, 1);

      spectrum = new Spectrum(track.getCombined(), SAMPLES, 128);
      color = new ColorGradient(0.0, 1.0, 0.1);

      File specFile = new File("orca.spec");
      if (!specFile.exists()) {
         spectrum.writeToFile(specFile);
      } else {
         spectrum.readFromFile(specFile);

      }
   }

   @Override
   protected void update(Range range, float position, int pass) {
      for (int i = 0; i < getWidth(); i++) {
         if (cancel) {
            return;
         }

         float normalizedPositionInSubRangeX = (float) i / (float) getWidth();
         float posX = range.fromX + range.getSizeX() * normalizedPositionInSubRangeX;

         for (int j = 0; j < getHeight(); j++) {
            float posY = (float) j / (float) getHeight();
            float magnitude = spectrum.getMagnitudeAt(posX, posY);

            setColor(0x000000ff | color.getColor(magnitude * scaleZ, 0.0, spectrum.getMax()) << 8);
            drawPixel(i, getHeight() - j);
         }

         double lowMagnitude = spectrum.getMagnitudeAt(posX, level) / spectrum.getMax();

         float m = MathUtils.clamp((float) lowMagnitude, 0.0f, 1.0f);
         int h = MathUtils.round(getHeight() * m);

         setColor(0xffffff55);
         if (m >= threshold) {
            setColor(0xff000055);
         }
         fillRectangle(i, getHeight() - h, 1, h);

         setChanged(true);
      }

      int x = MathUtils.round((position - range.fromX) / range.getSizeX() * getWidth());
      setColor(0xffffffff);
      fillRectangle(x, 0, 1, getHeight());

      int y = MathUtils.round(level * getHeight());
      fillRectangle(0, getHeight() - y, getWidth(), 1);

      setColor(0xff0000ff);
      int y2 = MathUtils.round(threshold * getHeight());
      fillRectangle(0, getHeight() - y2, getWidth(), 1);

   }

   // protected void update2(Range range, float position, int pass) {
   //
   // float[] spectrum = new float[SAMPLES / 2];
   //
   // for (int i = pass; i < getWidth(); i += passes) {
   // if (cancel) {
   // return;
   // }
   //
   // double normalizedPositionInSubRangeX = (double) i / (double) getWidth();
   // double normalizedPositionInGlobalRangeX = range.fromX + range.getSizeX() * normalizedPositionInSubRangeX;
   //
   // int centerX = (int) Math.round(normalizedPositionInGlobalRangeX * allSamples.length);
   // int fromX = MathUtils.clamp(centerX - SAMPLES / 2, 0, allSamples.length - 1);
   // int toX = MathUtils.clamp(centerX + SAMPLES / 2, 0, allSamples.length - 1);
   //
   // short[] rangeArray = track.getRange(fromX, toX, SAMPLES);
   //
   // ArrayTools.applyGauss(rangeArray, 16.0);
   //
   // fft.spectrum(rangeArray, spectrum);
   // FloatArray na = new FloatArray(spectrum);
   // double energy = na.sum();
   // maxEnergy = Math.max(maxEnergy, energy);
   //
   // for (int j = 0; j < getHeight(); j++) {
   //
   // double normalizedPositionInSubRangeY = (double) j / (double) getHeight();
   // double normalizedPositionInGlobalRangeY = range.fromY + range.getSizeY() * normalizedPositionInSubRangeY;
   // int posY = (int) Math.round(normalizedPositionInGlobalRangeY * spectrum.length);
   // posY = MathUtils.clamp(posY, 1, spectrum.length - 2);
   //
   // double magnitude = Math.sqrt(spectrum[posY] * spectrum[posY] + spectrum[posY + 1] + spectrum[posY + 1]);
   //
   // max = Math.max(max, magnitude);
   //
   // setColor(0x000000ff | getColor(magnitude / (max * 1.0)) << 8);
   // drawRectangle(i, getHeight() - j, passes - pass, 1);
   //
   // }
   //
   // int hPos = 30;
   //
   // double lowMagnitude = Math.sqrt(spectrum[hPos] * spectrum[hPos] + spectrum[hPos + 1] + spectrum[hPos + 1]) / 300.0;
   //
   // float m = MathUtils.clamp((float) lowMagnitude, 0.0f, 1.0f);
   // int h = MathUtils.round(getHeight() * m);
   //
   // setColor(0xffffffaa);
   // fillRectangle(i, getHeight() - h, 1, h);
   //
   // if (m > 0.5) {
   // setColor(0xff0000aa);
   // // fillRectangle(i, getHeight() - h, 1, h);
   // fillRectangle(i, 0, 1, getHeight());
   // }
   //
   // setChanged(true);
   // }
   //
   // int x = MathUtils.round((position - range.fromX) / range.getSizeX() * getWidth());
   // setColor(0xffffffff);
   // for (int j = 0; j < getHeight(); j++) {
   // drawPixel(x, j);
   // }
   // }

}
