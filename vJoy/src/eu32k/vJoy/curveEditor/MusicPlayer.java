package eu32k.vJoy.curveEditor;

import java.util.Arrays;

import com.badlogic.gdx.audio.AudioDevice;

public class MusicPlayer {

   private static final int CHUNK_SIZE = 256;

   private AudioDevice device;
   private short[] soundData;

   private int position = 0;
   private boolean running = true;
   private boolean playing = false;
   private Thread player;

   public MusicPlayer(AudioDevice device, short[] soundData) {
      this.device = device;
      this.soundData = soundData;

      player = new Thread(new Runnable() {
         @Override
         public void run() {
            while (running) {
               if (playing && position < MusicPlayer.this.soundData.length) {
                  int end = Math.min(position + CHUNK_SIZE, MusicPlayer.this.soundData.length - 1);
                  MusicPlayer.this.device.writeSamples(Arrays.copyOfRange(MusicPlayer.this.soundData, position, end), 0, end - position);
                  position += CHUNK_SIZE;
               } else {
                  try {
                     Thread.sleep(5);
                  } catch (InterruptedException e) {
                     // NOP
                  }
               }
            }
         }
      });
      player.start();
   }

   public void setPosition(int pos) {
      position = pos;
   }

   public void play() {
      playing = true;
   }

   public void pause() {
      playing = false;
   }

   public void setPlaying(boolean playing) {
      this.playing = playing;
   }

   public void shutdown() {
      playing = false;
      running = false;
      try {
         player.join();
      } catch (InterruptedException e) {
         // NOP
      }
   }

   public boolean isPlaying() {
      return playing;
   }

   public int getPosition() {
      return position;
   }
}
