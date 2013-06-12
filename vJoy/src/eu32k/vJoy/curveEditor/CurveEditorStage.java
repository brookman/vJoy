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

import eu32k.common.net.BroadcastAddress;
import eu32k.common.net.PeerToPeerClient;
import eu32k.vJoy.ClientTypes;
import eu32k.vJoy.VJoyMain;
import eu32k.vJoy.curveEditor.audio.AudioTrack;
import eu32k.vJoy.curveEditor.audio.MusicPlayer;
import eu32k.vJoy.curveEditor.misc.KeyPressEvent;
import eu32k.vJoy.curveEditor.misc.Range;
import eu32k.vJoy.curveEditor.visualization.PixmapWidget;
import eu32k.vJoy.curveEditor.visualization.curve.CurvePixmap;
import eu32k.vJoy.curveEditor.visualization.spectrum.SpectrumPixmap;
import eu32k.vJoy.curveEditor.visualization.waveform.WaveformPixmap;

public class CurveEditorStage extends Stage {

   private AudioDevice device;
   private AudioTrack track;

   private WaveformPixmap waveform;
   private SpectrumPixmap spectrum;
   private CurvePixmap curve;

   private MusicPlayer player;

   private Slider zoomSliderX;
   private Slider zoomSliderY;
   private Slider scaleSliderZ;
   private Slider levelSlider;
   private Slider threshSlider;

   private boolean redraw = true;
   private float zoomX = 0.1f;
   private float zoomY = 0.1f;
   private Rectangle zoomRange = new Rectangle(0.003f, 0.03f, 1.0f, 1.0f);

   private PeerToPeerClient net;

   public CurveEditorStage(String filePath, BroadcastAddress address) {

      net = new PeerToPeerClient(address, ClientTypes.TYPE_CONTROLLER);

      net.sendToTypeUdp(ClientTypes.TYPE_ARCHITECT, new Float(0.5f));

      track = new AudioTrack(Gdx.files.absolute(filePath));

      device = Gdx.audio.newAudioDevice(track.getRate(), track.isMono());
      player = new MusicPlayer(device, track);
      waveform = new WaveformPixmap(1920, 800, track.getChannel1(), track.getChannel2());
      spectrum = new SpectrumPixmap(1920, 800, track.getCombined());

      curve = new CurvePixmap(1920, 800);

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

      scaleSliderZ = new Slider(0.1f, 2.0f, 0.0001f, false, VJoyMain.SKIN);
      scaleSliderZ.setValue(spectrum.scaleZ);
      scaleSliderZ.addListener(new ChangeListener() {
         @Override
         public void changed(ChangeEvent event, Actor actor) {
            spectrum.scaleZ = scaleSliderZ.getValue();
            redraw = true;
         }
      });

      levelSlider = new Slider(0.0f, 1.0f, 0.0001f, false, VJoyMain.SKIN);
      levelSlider.setValue(spectrum.level);
      levelSlider.addListener(new ChangeListener() {
         @Override
         public void changed(ChangeEvent event, Actor actor) {
            spectrum.level = levelSlider.getValue();
            regenerate();
            redraw = true;
         }
      });

      threshSlider = new Slider(0.0f, 1.0f, 0.0001f, false, VJoyMain.SKIN);
      threshSlider.setValue(spectrum.threshold);
      threshSlider.addListener(new ChangeListener() {
         @Override
         public void changed(ChangeEvent event, Actor actor) {
            spectrum.threshold = threshSlider.getValue();
            regenerate();
            redraw = true;
         }
      });

      table.add(player).colspan(2).fillX().expandX().pad(5);
      table.row();
      table.add(new PixmapWidget(waveform)).colspan(2).fill().height(100).pad(5);
      table.row();
      table.add(new PixmapWidget(spectrum)).colspan(2).fill().expand().pad(5);
      table.row();
      table.add(new PixmapWidget(curve)).colspan(2).fill().expand().pad(5);
      table.row();
      table.add(new Label("Zoom X:", VJoyMain.SKIN)).left().pad(5);
      table.add(zoomSliderX).fillX().expandX().pad(5);
      // table.row();
      // table.add(new Label("Zoom Y:", VJoyMain.SKIN)).left().pad(5);
      // table.add(zoomSliderY).fillX().expandX().pad(5);
      table.row();
      table.add(new Label("Scale Z:", VJoyMain.SKIN)).left().pad(5);
      table.add(scaleSliderZ).fillX().expandX().pad(5);
      table.row();
      table.add(new Label("Level:", VJoyMain.SKIN)).left().pad(5);
      table.add(levelSlider).fillX().expandX().pad(5);
      table.row();
      table.add(new Label("Threshold:", VJoyMain.SKIN)).left().pad(5);
      table.add(threshSlider).fillX().expandX().pad(5);

      addActor(table);

      Range range = new Range(0.0f, zoomX, 0.0f, zoomY);

      waveform.updatePixmap(range, 0);
      spectrum.updatePixmap(range, 0);
      // pixmapChannel2.updatePixmap(new Rectangle(0.0f, 0.0f, zoomX, zoomY));

      new KeyPressEvent(Input.Keys.SPACE) {
         @Override
         public void onPress() {
            if (player.isPlaying()) {
               player.pause();
            } else {
               player.play();
            }
         }
      };
   }

   private void regenerate() {
      curve.setData(spectrum.spectrum.getMagnitudeAt(spectrum.level), spectrum.spectrum.getMax());
   }

   @Override
   public boolean scrolled(int x) {
      setZoomX(zoomX + x * 0.01f);
      zoomSliderX.setValue(zoomX);
      return true;
   }

   private void setZoomX(float zoomX) {
      this.zoomX = MathUtils.clamp(zoomX, zoomRange.x, zoomRange.width);
      redraw = true;
   }

   private void setZoomY(float zoomY) {
      this.zoomY = MathUtils.clamp(zoomY, zoomRange.y, zoomRange.height);
      redraw = true;
   }

   private float lastPosition = -1;

   @Override
   public void draw() {
      KeyPressEvent.update();

      float np = (float) player.getNormalizedPosition();
      if (lastPosition != np) {
         lastPosition = np;
         redraw = true;
      }
      if (redraw) {
         float low = np - zoomX / 2.0f;
         float high = np + zoomX / 2.0f;
         if (low < 0) {
            low = 0;
            high = zoomX;
         } else if (high > 1) {
            high = 1;
            low = 1 - zoomX;
         }

         Range range = new Range(low, high, 0, zoomY);

         waveform.updatePixmap(range, np);
         spectrum.updatePixmap(range, np);
         curve.updatePixmap(range, np);
         redraw = false;
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
