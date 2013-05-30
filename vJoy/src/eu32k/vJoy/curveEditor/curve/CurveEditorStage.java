package eu32k.vJoy.curveEditor.curve;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.audio.io.Mpg123Decoder;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import eu32k.vJoy.VJoyMain;
import eu32k.vJoy.curveEditor.MusicPlayer;
import eu32k.vJoy.curveEditor.spectrum.PixmapWidget;
import eu32k.vJoy.curveEditor.spectrum.SpectrumPixmap;

public class CurveEditorStage extends Stage {

   private AudioDevice device;

   private short[] allSamples;
   private short[] samplesChannel1;
   private short[] samplesChannel2;
   private int rate;
   private int channels;
   private int samplesPerSecond;

   private SpectrumPixmap pixmapChannel1;
   private SpectrumPixmap pixmapChannel2;

   private MusicPlayer player;

   private Slider zoomSliderX;
   private Slider zoomSliderY;

   private int from = 0;
   private boolean hasToUpdate = false;

   private float zoomX = 0.05f;
   private float zoomY = 0.05f;
   private Rectangle zoomRange = new Rectangle(0.01f, 0.05f, 1.0f, 1.0f);

   public CurveEditorStage() {

      Mpg123Decoder decoder = new Mpg123Decoder(Gdx.files.absolute("C:/java_workspace/vJoy/vJoy-android/assets/sound/orca.mp3"));
      // Mpg123Decoder decoder = new
      // Mpg123Decoder(Gdx.files.absolute("C:/Users/pc0/Desktop/sound/Airi_-_Smyle-2011-UPE/03-airi_-_rainbox-upe.mp3"));

      rate = decoder.getRate();
      channels = decoder.getChannels();
      samplesPerSecond = rate * channels;
      allSamples = decoder.readAllSamples();
      decoder.dispose();

      samplesChannel1 = new short[allSamples.length / 2];
      samplesChannel2 = new short[allSamples.length / 2];
      for (int i = 0; i < allSamples.length; i++) {
         if (i % 2 == 0) {
            samplesChannel1[i / 2] = allSamples[i];
         } else {
            samplesChannel2[i / 2] = allSamples[i];
         }
      }

      double seconds = allSamples.length / (double) channels / rate;
      double minutes = seconds / 60.0;
      double seconds2 = (minutes - Math.floor(minutes)) * 60.0;
      minutes = Math.floor(minutes);
      System.out.println("size = " + allSamples.length);
      System.out.println("rate = " + rate);
      System.out.println("channels = " + channels);
      System.out.println("length = " + (int) seconds + " sec");
      System.out.println("length = " + (int) minutes + " min " + (int) seconds2 + " sec");

      device = Gdx.audio.newAudioDevice(rate, channels == 1);
      player = new MusicPlayer(device, allSamples);
      pixmapChannel1 = new SpectrumPixmap(1920, 1080, samplesChannel1);
      pixmapChannel2 = new SpectrumPixmap(1920, 1080, samplesChannel2);

      Table table = new Table();
      table.setFillParent(true);

      zoomSliderX = new Slider(zoomRange.x, zoomRange.width, zoomX, false, VJoyMain.SKIN);
      zoomSliderX.setValue(zoomX);
      zoomSliderX.addListener(new ChangeListener() {
         @Override
         public void changed(ChangeEvent event, Actor actor) {
            zoomX = zoomSliderX.getValue();
            hasToUpdate = true;
         }
      });

      table.add(player).fillX().expandX();
      table.row();
      table.add(zoomSliderX).fillX().expandX();
      table.row();
      table.add(new PixmapWidget(pixmapChannel1)).fill().expand();
      table.row();
      table.add(new PixmapWidget(pixmapChannel2)).fill().expand();
      table.row();

      addActor(table);

      pixmapChannel1.updatePixmap(new Rectangle(0.0f, 0.0f, zoomX, zoomY));
      pixmapChannel2.updatePixmap(new Rectangle(0.0f, 0.0f, zoomX, zoomY));
   }

   @Override
   public boolean scrolled(int x) {
      zoomX += x * 0.005f;
      // if (x < 0) {
      // zoomX = Math.round(zoomX * 0.99f * Math.abs(x));
      // } else {
      // zoomX = Math.round(zoomX * 1.01f * Math.abs(x));
      // }

      zoomX = MathUtils.clamp(zoomX, zoomRange.x, zoomRange.width);

      zoomSliderX.setValue(zoomX);
      hasToUpdate = true;
      return true;
   }

   boolean updated = false;
   boolean spaceArmed = true;

   @Override
   public void draw() {
      if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
         if (spaceArmed) {
            if (player.isPlaying()) {
               player.pause();
               player.setPosition(from);
            } else {
               player.play();
            }
            spaceArmed = false;
         }
      } else {
         spaceArmed = true;
      }

      if (hasToUpdate) {
         hasToUpdate = false;
         float fromD = (float) from / (float) allSamples.length;
         float f = Math.max(0.0f, fromD - zoomX / 2.0f);
         float t = Math.min(fromD + zoomX / 2.0f, 1.0f);

         pixmapChannel1.updatePixmap(new Rectangle(f, 0, t - f, zoomY));
         pixmapChannel2.updatePixmap(new Rectangle(f, 0, t - f, zoomY));
      }

      player.update();
      super.draw();
   }

   @Override
   public void dispose() {
      player.shutdown();
      device.dispose();
   }
}
