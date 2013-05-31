package eu32k.vJoy.curveEditor.audio;

import com.badlogic.gdx.audio.io.Mpg123Decoder;
import com.badlogic.gdx.files.FileHandle;

public class AudioTrack {

   private int rate;
   private int channels;
   private int samplesPerSecond;
   private int samplesPerChannel;
   private short[] allSamples;
   private short[][] audioSamples;

   public AudioTrack(FileHandle fileHandle, boolean mergeChannels) {
      Mpg123Decoder decoder = new Mpg123Decoder(fileHandle);
      rate = decoder.getRate();
      channels = decoder.getChannels();
      samplesPerSecond = rate * channels;
      allSamples = decoder.readAllSamples();
      samplesPerChannel = allSamples.length / channels;
      decoder.dispose();

      if (mergeChannels) {
         audioSamples = new short[1][samplesPerChannel];
         for (int i = 0; i < samplesPerChannel; i++) {
            short value = 0;
            for (int c = 0; c < channels; c++) {
               value = (short) Math.max(value, allSamples[i * channels + c]);
            }
            audioSamples[0][i] = value;
         }
      } else {
         audioSamples = new short[channels][samplesPerChannel];
         for (int i = 0; i < samplesPerChannel; i++) {
            for (int c = 0; c < channels; c++) {
               audioSamples[c][i] = allSamples[i * channels + c];
            }
         }
      }
   }

   public int getRate() {
      return rate;
   }

   public int getChannels() {
      return channels;
   }

   public int getSamplesPerSecond() {
      return samplesPerSecond;
   }

   public int getSamplesPerChannel() {
      return samplesPerChannel;
   }

   public short[] getAllSamples() {
      return allSamples;
   }

   public short[][] getAudioSamples() {
      return audioSamples;
   }

   public short[] getChannelSamples(int channel) {
      return audioSamples[channel];
   }

   public double getLengthInSeconds() {
      return (double) samplesPerChannel / (double) samplesPerSecond;
   }

   public short[] getRange(int channel, int from, int to) {
      int size = to - from;
      short[] range = new short[size];
      System.arraycopy(audioSamples[channel], from, range, 0, size);
      return range;
   }

   public short[] getRange(int from, int to) {
      int size = to - from;
      short[] range = new short[size];
      System.arraycopy(audioSamples[0], from, range, 0, size);
      return range;
   }
}
