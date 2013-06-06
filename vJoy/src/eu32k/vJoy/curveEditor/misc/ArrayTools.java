package eu32k.vJoy.curveEditor.misc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.badlogic.gdx.math.MathUtils;

public class ArrayTools {
   public static short getNormalizedValue(short[] array, float position) {
      return array[MathUtils.clamp(MathUtils.round(position * array.length), 0, array.length - 1)];
   }

   public static short getNormalizedValue(short[] array, double position) {
      return array[MathUtils.clamp((int) Math.round(position * array.length), 0, array.length - 1)];
   }

   public static float getNormalizedValue(float[] array, float position) {
      return array[MathUtils.clamp(MathUtils.round(position * array.length), 0, array.length - 1)];
   }

   public static float getNormalizedValue(float[] array, double position) {
      return array[MathUtils.clamp((int) Math.round(position * array.length), 0, array.length - 1)];
   }

   public static float[] getNormalizedValue(float[][] array, double position) {
      return array[MathUtils.clamp((int) Math.round(position * array.length), 0, array.length - 1)];
   }

   public static float[] getNormalizedValue(float[][] array, float position) {
      return array[MathUtils.clamp(Math.round(position * array.length), 0, array.length - 1)];
   }

   public static void applyGauss(short[] data, double width) {
      for (int r = 0; r < data.length; r++) {
         double x = r / (double) data.length * width - width / 2.0;
         data[r] = (short) (data[r] * Math.exp(-1.0 * (x * x)));
      }
   }

   public static void writeArray(float[][] array, int innerSize, File file) {
      DataOutputStream s = null;
      try {
         s = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
         for (int x = 0; x < array.length; x++) {
            for (int y = 0; y < innerSize; y++) {
               s.writeFloat(array[x][y]);
            }
         }
      } catch (IOException e) {
         // NOP
      } finally {
         if (s != null) {
            try {
               s.close();
            } catch (IOException e) {
               // NOP
            }
         }
      }
   }

   public static float[][] readArray(int innerSize, File file) {
      float[][] array = new float[(int) (file.length() / innerSize / (Float.SIZE / 8))][innerSize];
      DataInputStream s = null;
      try {
         s = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
         for (int x = 0; x < array.length; x++) {
            for (int y = 0; y < innerSize; y++) {
               array[x][y] = s.readFloat();
            }
         }
      } catch (IOException e) {
         // NOP
      } finally {
         if (s != null) {
            try {
               s.close();
            } catch (IOException e) {
               // NOP
            }
         }
      }
      return array;
   }
}
