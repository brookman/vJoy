package eu32k.vJoy.curveEditor;

import java.text.DecimalFormat;
import java.util.Arrays;

import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import eu32k.vJoy.VJoyMain;

public class MusicPlayer extends Table {

   private static final int CHUNK_SIZE = 256;

   private AudioDevice device;
   private short[] soundData;

   private int position = 0;
   private boolean running = true;
   private boolean playing = false;
   private Thread player;

   private TextButton playButton;
   private Slider slider;
   private Label label;
   private Slider volume;

   private static final DecimalFormat decimalFormatMin = new DecimalFormat("00");
   private static final DecimalFormat decimalFormatSec = new DecimalFormat("00.000");

   public MusicPlayer(final AudioDevice device, short[] soundData) {
      this.device = device;
      this.soundData = soundData;

      playButton = new TextButton("Play / Pause", VJoyMain.SKIN);
      playButton.addListener(new InputListener() {
         @Override
         public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if (isPlaying()) {
               pause();
            } else {
               play();
            }
            return false;
         }
      });

      TextButton stopButton = new TextButton("Stop", VJoyMain.SKIN);
      stopButton.addListener(new InputListener() {
         @Override
         public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            shutdown();
            running = true;
            setPosition(0);
            createPlayer();
            return false;
         }
      });

      slider = new Slider(0, soundData.length, 1, false, VJoyMain.SKIN);
      slider.addListener(new ChangeListener() {
         @Override
         public void changed(ChangeEvent event, Actor actor) {
            setPosition((int) slider.getValue());
         }
      });

      volume = new Slider(0.0f, 1.0f, 0.01f, false, VJoyMain.SKIN);
      volume.setValue(1.0f);
      volume.addListener(new ChangeListener() {
         @Override
         public void changed(ChangeEvent event, Actor actor) {
            device.setVolume(volume.getValue());
         }
      });

      label = new Label("", VJoyMain.SKIN);

      add(playButton).left().pad(5);
      add(stopButton).left().pad(5);
      add(label).left().pad(5);
      add(slider).left().pad(5).expandX().fillX();
      add(new Label("Volume:", VJoyMain.SKIN)).left().pad(5);
      add(volume).left().pad(5);

      createPlayer();
   }

   private void createPlayer() {
      player = new Thread(new Runnable() {
         @Override
         public void run() {
            while (running) {
               int pos = 0;
               synchronized (this) {
                  pos = position;
               }
               if (playing && pos < soundData.length) {
                  int end = Math.min(pos + CHUNK_SIZE, soundData.length - 1);
                  device.writeSamples(Arrays.copyOfRange(soundData, pos, end), 0, end - pos);
                  synchronized (this) {
                     position += CHUNK_SIZE;
                  }
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

   public void update() {
      int realPos = MathUtils.clamp(position, 0, soundData.length - 1);
      slider.setValue(realPos);
      label.setText(getTimeString(realPos, 2, 44100) + " / " + getTimeString(soundData.length, 2, 44100));
   }

   private static String getTimeString(int position, int channels, int samplesPerSecond) {
      double minutes = (double) position / (double) channels / samplesPerSecond / 60.0;
      double minutesFloor = Math.floor(minutes);
      double seconds = (minutes - minutesFloor) * 60.0;
      return decimalFormatMin.format(minutesFloor) + ":" + decimalFormatSec.format(seconds);
   }

   public void setPosition(int pos) {
      synchronized (this) {
         position = pos;
      }
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
