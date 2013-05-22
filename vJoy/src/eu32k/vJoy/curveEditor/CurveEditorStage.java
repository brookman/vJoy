package eu32k.vJoy.curveEditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import eu32k.vJoy.VJoyMain;
import eu32k.vJoy.common.Colors;

public class CurveEditorStage extends Stage {

   private static final int SAMPLING_RATE = 44100;
   private AudioDevice device = Gdx.audio.newAudioDevice(SAMPLING_RATE, true);

   public CurveEditorStage() {

      int latencyInSamples = device.getLatency();
      System.out.println(latencyInSamples);

      TextButton playButton = new TextButton("Play sine 200hz", VJoyMain.SKIN);
      playButton.setColor(Colors.DARK_YELLOW);
      playButton.addListener(new InputListener() {
         @Override
         public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            playSample(200, 1.0f);
            return false;
         }
      });

      TextButton playButton2 = new TextButton("Play sine 400hz", VJoyMain.SKIN);
      playButton2.setColor(Colors.DARK_YELLOW);
      playButton2.addListener(new InputListener() {
         @Override
         public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            playSample(400, 0.3f);
            return false;
         }
      });

      TextButton playButton3 = new TextButton("Play sine 500hz", VJoyMain.SKIN);
      playButton3.setColor(Colors.DARK_YELLOW);
      playButton3.addListener(new InputListener() {
         @Override
         public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            playSample(500, 0.3f);
            return false;
         }
      });

      Table table = new Table();
      table.setFillParent(true);
      table.add(playButton);
      table.row();
      table.add(playButton2);
      table.row();
      table.add(playButton3);

      addActor(table);

   }

   private void playSample(float herz, float length) {
      int samples = (int) Math.ceil(length * SAMPLING_RATE);
      final float[] floatPCM = new float[samples];
      for (int i = 0; i < samples; i++) {
         double value = i / (double) samples;
         floatPCM[i] = (float) Math.sin(value * Math.PI * 2.0 * herz);
      }
      device.writeSamples(floatPCM, 0, floatPCM.length);
   }
}
