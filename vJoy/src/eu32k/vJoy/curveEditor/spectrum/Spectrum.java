package eu32k.vJoy.curveEditor.spectrum;

import java.io.File;

import com.badlogic.gdx.audio.analysis.KissFFT;
import com.badlogic.gdx.math.MathUtils;

import eu32k.vJoy.curveEditor.audio.AudioTrack;
import eu32k.vJoy.curveEditor.misc.ArrayTools;

public class Spectrum {

   public static final int SIZE_Y = 1024;

   private short[] samples;
   private int fftWindowSize;
   private int samplesPerDataPoint;
   private KissFFT fft;

   private float[][] spectrum;
   private float max = 0;

   public Spectrum(short[] samples, int fftWindowSize, int samplesPerDataPoint) {
      this.samples = samples;
      this.fftWindowSize = fftWindowSize;
      this.samplesPerDataPoint = samplesPerDataPoint;
   }

   public void generateSpectrum() {
      fft = new KissFFT(fftWindowSize);
      int sizeX = samples.length / samplesPerDataPoint;

      spectrum = new float[sizeX][SIZE_Y];

      float[] tempSpectrum = new float[fftWindowSize / 2];

      for (int i = 0; i < sizeX; i++) {
         int pos = i * samplesPerDataPoint;
         int from = pos - fftWindowSize / 2;
         int to = pos + fftWindowSize / 2;
         if (from < 0) {
            from = 0;
            to = fftWindowSize;
         }
         if (to >= samples.length) {
            from = samples.length - 1 - fftWindowSize;
            to = samples.length - 1;
         }
         short[] rangeArray = AudioTrack.getRange(samples, from, to, fftWindowSize);
         ArrayTools.applyGauss(rangeArray, 16.0);
         fft.spectrum(rangeArray, tempSpectrum);
         for (int j = 0; j < SIZE_Y; j++) {
            spectrum[i][j] = tempSpectrum[j];
         }
      }
      fft.dispose();
      updateMax();
   }

   public void writeToFile(File file) {
      if (spectrum == null) {
         generateSpectrum();
      }
      ArrayTools.writeArray(spectrum, SIZE_Y, file);
   }

   public void readFromFile(File file) {
      spectrum = ArrayTools.readArray(SIZE_Y, file);
      updateMax();
   }

   private float updateMax() {
      max = 0;
      for (int i = 0; i < spectrum.length; i++) {
         for (int j = 0; j < SIZE_Y; j++) {
            max = Math.max(max, spectrum[i][j]);
         }
      }
      return max;
   }

   public float[][] getSpectrum() {
      return spectrum;
   }

   public float getMagnitudeAt(float x, float y) {
      float[] col = ArrayTools.getNormalizedValue(spectrum, x);
      int posY = MathUtils.clamp(Math.round(y * SIZE_Y - 1), 0, SIZE_Y - 2);
      float magnitude = (float) Math.sqrt(col[posY] * col[posY] + col[posY + 1] + col[posY + 1]);
      return magnitude;
   }

   public float getMax() {
      return max;
   }
}
