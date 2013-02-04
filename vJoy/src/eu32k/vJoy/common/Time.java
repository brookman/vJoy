package eu32k.vJoy.common;

public class Time {

   private static long startTime = System.currentTimeMillis();

   public static float getTime() {
      long difference = System.currentTimeMillis() - startTime;
      double seconds = difference / 1000.0;
      return (float) seconds;
   }
}
