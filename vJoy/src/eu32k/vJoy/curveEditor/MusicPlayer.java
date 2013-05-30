package eu32k.vJoy.curveEditor;

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

   public MusicPlayer(AudioDevice device, short[] soundData) {
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

      label = new Label("wut", VJoyMain.SKIN);

      add(playButton).left().pad(5);
      add(stopButton).left().pad(5);
      add(label).left().pad(5);
      add(slider).left().pad(5).expandX().fillX();

      createPlayer();
   }

   private void createPlayer() {
      player = new Thread(new Runnable() {
         @Override
         public void run() {
            while (running) {
               if (playing && position < soundData.length) {
                  int end = Math.min(position + CHUNK_SIZE, soundData.length - 1);
                  device.writeSamples(Arrays.copyOfRange(soundData, position, end), 0, end - position);
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

   public void update() {
      slider.setValue(MathUtils.clamp(position, 0, soundData.length - 1));

      double seconds = position / (double) 2 / 44100;
      double minutes = seconds / 60.0;
      double seconds2 = (minutes - Math.floor(minutes)) * 60.0;

      seconds2 = Math.floor(seconds2 * 100.0) / 100.0;
      minutes = Math.floor(minutes);
      label.setText((int) minutes + " min " + seconds2 + " sec");
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
