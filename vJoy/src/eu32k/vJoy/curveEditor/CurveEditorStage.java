package eu32k.vJoy.curveEditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import eu32k.vJoy.VJoyMain;
import eu32k.vJoy.curveEditor.audio.AudioTrack;
import eu32k.vJoy.curveEditor.spectrum.PixmapWidget;
import eu32k.vJoy.curveEditor.spectrum.SpectrumPixmap;
import eu32k.vJoy.curveEditor.spectrum.WaveformPixmap;

public class CurveEditorStage extends Stage {

   private AudioDevice device;
   private AudioTrack track;

   private WaveformPixmap waveform;
   private SpectrumPixmap spectrum;
   // private SpectrumPixmap pixmapChannel2;

   private MusicPlayer player;

   private Slider zoomSliderX;
   private Slider zoomSliderY;

   private float zoomX = 0.1f;
   private float zoomY = 0.1f;
   private Rectangle zoomRange = new Rectangle(0.003f, 0.03f, 1.0f, 1.0f);

   public CurveEditorStage(String filePath) {

      track = new AudioTrack(Gdx.files.absolute(filePath));

      device = Gdx.audio.newAudioDevice(track.getRate(), track.isMono());
      player = new MusicPlayer(device, track);
      waveform = new WaveformPixmap(1920, 1080, track);
      spectrum = new SpectrumPixmap(1920, 1080, track, true);

      Table table = new Table();
      table.setFillParent(true);

      zoomSliderX = new Slider(zoomRange.x, zoomRange.width, 0.0001f, false, VJoyMain.SKIN);
      zoomSliderX.setValue(zoomX);
      zoomSliderX.addListener(new ChangeListener() {
         @Override
         public void changed(ChangeEvent event, Actor actor) {
            setZoomX(zoomSliderX.getValue());
         }
      });

      zoomSliderY = new Slider(zoomRange.y, zoomRange.height, 0.0001f, false, VJoyMain.SKIN);
      zoomSliderY.setValue(zoomY);
      zoomSliderY.addListener(new ChangeListener() {
         @Override
         public void changed(ChangeEvent event, Actor actor) {
            setZoomY(zoomSliderY.getValue());
         }
      });

      table.add(player).colspan(2).fillX().expandX().pad(5);
      table.row();
      table.add(new PixmapWidget(waveform)).colspan(2).fill().expand().pad(5);
      table.row();
      table.add(new PixmapWidget(spectrum)).colspan(2).fill().expand().pad(5);
      // table.row();
      // table.add(new PixmapWidget(pixmapChannel2)).colspan(2).fill().expand().pad(5);
      table.row();
      table.add(new Label("Zoom X:", VJoyMain.SKIN)).left().pad(5);
      table.add(zoomSliderX).fillX().expandX().pad(5);
      table.row();
      table.add(new Label("Zoom Y:", VJoyMain.SKIN)).left().pad(5);
      table.add(zoomSliderY).fillX().expandX().pad(5);

      addActor(table);

      waveform.updatePixmap(new Rectangle(0.0f, 0.0f, zoomX, zoomY));
      spectrum.updatePixmap(new Rectangle(0.0f, 0.0f, zoomX, zoomY));
      // pixmapChannel2.updatePixmap(new Rectangle(0.0f, 0.0f, zoomX, zoomY));
   }

   @Override
   public boolean scrolled(int x) {
      setZoomX(zoomX + x * 0.01f);
      zoomSliderX.setValue(zoomX);
      return true;
   }

   private void setZoomX(float zoomX) {
      this.zoomX = MathUtils.clamp(zoomX, zoomRange.x, zoomRange.width);
   }

   private void setZoomY(float zoomY) {
      this.zoomY = MathUtils.clamp(zoomY, zoomRange.y, zoomRange.height);
   }

   boolean updated = false;
   boolean spaceArmed = true;
   private int lastPosition = -1;
   private float lastZoomX = -1;
   private float lastZoomY = -1;

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

      if (lastPosition != player.getPosition() || lastZoomX != zoomX || lastZoomY != zoomY) {
         lastPosition = player.getPosition();
         lastZoomX = zoomX;
         lastZoomY = zoomY;

         float fromX = (float) player.getPosition() / track.getAll().length - zoomX / 2.0f;

         fromX = MathUtils.clamp(fromX, 0.0f, 1.0f - zoomX / 2.0f);

         Rectangle rect = new Rectangle(fromX, 0, zoomX, zoomY);

         waveform.updatePixmap(rect);
         spectrum.updatePixmap(rect);
         // pixmapChannel2.updatePixmap(rect);
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
