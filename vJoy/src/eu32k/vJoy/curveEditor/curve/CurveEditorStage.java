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
            setZoomX(zoomSliderX.getValue());
         }
      });

      table.add(player).fillX().expandX();
      table.row();
      table.add(new PixmapWidget(pixmapChannel1)).fill().expand();
      table.row();
      table.add(new PixmapWidget(pixmapChannel2)).fill().expand();
      table.row();
      table.add(zoomSliderX).fillX().expandX();

      addActor(table);

      pixmapChannel1.updatePixmap(new Rectangle(0.0f, 0.0f, zoomX, zoomY));
      pixmapChannel2.updatePixmap(new Rectangle(0.0f, 0.0f, zoomX, zoomY));
   }

   @Override
   public boolean scrolled(int x) {
      setZoomX(zoomX + x * 0.01f);
      zoomSliderX.setValue(zoomX);
      return true;
   }

   private void setZoomX(float zoomX) {
      this.zoomX = MathUtils.clamp(zoomX, zoomRange.x, zoomRange.width);
      hasToUpdate = true;
   }

   boolean updated = false;
   boolean spaceArmed = true;

   @Override
   public void draw() {
      if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
         if (spaceArmed) {
            if (player.isPlaying()) {
               player.pause();
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
         float fromX = (float) player.getPosition() / allSamples.length - zoomX / 2.0f;

         fromX = MathUtils.clamp(fromX, 0.0f, 1.0f - zoomX / 2.0f);

         Rectangle rect = new Rectangle(fromX, 0, zoomX, zoomY);
         pixmapChannel1.updatePixmap(rect);
         pixmapChannel2.updatePixmap(rect);
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
