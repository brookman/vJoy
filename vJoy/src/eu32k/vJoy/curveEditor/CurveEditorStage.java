package eu32k.vJoy.curveEditor;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.audio.analysis.KissFFT;
import com.badlogic.gdx.audio.io.Mpg123Decoder;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import eu32k.vJoy.common.AdvancedShader;
import eu32k.vJoy.common.Tools;

public class CurveEditorStage extends Stage {

   private static final int SAMPLING_RATE = 44100;
   // private AudioDevice device = Gdx.audio.newAudioDevice(SAMPLING_RATE, true);

   private final int SAMPLES = 4096 * 2;

   String FILE = "data/justice-new-lands.mp3";
   private Mpg123Decoder decoder;
   private AudioDevice device;
   private boolean playing = false;
   private short[] samples = new short[SAMPLES];
   private KissFFT fft;
   private float[] spectrum = new float[SAMPLES];

   private BufferedImage image = new BufferedImage(SAMPLES / 2, SAMPLES, BufferedImage.TYPE_INT_RGB);

   private AdvancedShader shader;
   private Texture texture;
   private Pixmap pixmap;

   public CurveEditorStage() {

      shader = Tools.getShader("simple", "simple");
      pixmap = new Pixmap(2048, 2048, Format.RGBA8888);
      texture = new Texture(pixmap);

      decoder = new Mpg123Decoder(Gdx.files.absolute("C:/setfos_ws/vJoy/vJoy-android/assets/sound/crazy.mp3"));
      final int rate = decoder.getRate();
      final int channels = decoder.getChannels();
      final short[] allSamples = new short[841000];
      // decoder.readAllSamples();
      decoder.readSamples(allSamples, 0, allSamples.length);
      decoder.dispose();

      new Thread(new Runnable() {

         @Override
         public void run() {
            double seconds = allSamples.length / (double) channels / rate;
            double minutes = seconds / 60.0;
            seconds = (minutes - Math.floor(minutes)) * 60.0;
            minutes = Math.floor(minutes);
            System.out.println("size = " + allSamples.length);
            System.out.println("rate = " + rate);
            System.out.println("length = " + (int) minutes + " min " + (int) seconds + " sec");

            final int SAMPLING_POINTS = 2048;
            final int SAMPLES = 4096;

            fft = new KissFFT(SAMPLES);
            float[] spectrum = new float[SAMPLES];

            float max = 0;

            for (int i = 0; i < SAMPLING_POINTS; i++) {
               int center = (int) Math.round((double) i * (double) allSamples.length / SAMPLING_POINTS);
               // MathUtils.clamp(point, 0, allSamples.length - 1);
               int from = MathUtils.clamp(center - SAMPLES / 2, 0, allSamples.length - 1);
               int to = MathUtils.clamp(from + SAMPLES, 0, allSamples.length - 1);

               short[] range = new short[SAMPLES];
               System.arraycopy(allSamples, from, range, 0, to - from);

               fft.spectrum(range, spectrum);

               for (int j = 0; j < spectrum.length - 2048; j++) {
                  max = Math.max(max, spectrum[j]);

                  Color color = getColor(spectrum[j] / 570.0);
                  pixmap.setColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 1.0f);
                  pixmap.drawPixel(i, 2048 - j);
               }
            }

            System.out.println("max = " + max);
         }
      }).start();

      // FileHandle externalFile = Gdx.files.external("tmp/audio-spectrum.mp3");
      // Gdx.files.internal("sound/pof.mp3").copyTo(externalFile);

      // device = Gdx.audio.newAudioDevice(decoder.getRate(), decoder.getChannels() == 1 ? true : false);

      // Thread playbackThread = new Thread(new Runnable() {
      // @Override
      // public void run() {
      // int readSamples = 0;
      //
      // short[] allSamples = decoder.readAllSamples();
      // System.out.println("size=" + allSamples.length);
      // System.out.println("numberOfSamples=" + allSamples.length / 2048);
      //
      // int column = 0;
      // float max = 0;
      // // read until we reach the end of the file
      // while (playing && (readSamples = decoder.readSamples(samples, 0, samples.length)) > 0) {
      // // get audio spectrum
      // fft.spectrum(samples, spectrum);
      // for (int i = 0; i < spectrum.length; i++) {
      // // System.out.print(spectrum[i] + " ");
      // max = Math.max(max, spectrum[i]);
      //
      // int intensity = MathUtils.clamp(Math.round(spectrum[i] * 2.0f), 0, 255);
      // image.setRGB(column, SAMPLES - i - 1, new Color(intensity, intensity, intensity).getRGB());
      // }
      // System.out.print(max + "\n");
      // column++;
      // if (column >= SAMPLES / 2) {
      // try {
      // ImageIO.write(image, "png", new File("spectrum.png"));
      // } catch (IOException e) {
      // e.printStackTrace();
      // }
      // System.exit(0);
      // }
      // // write the samples to the AudioDevice
      // // device.writeSamples(samples, 0, readSamples);
      // }
      // }
      // });
      // playbackThread.setDaemon(true);
      // playbackThread.start();
      // playing = true;

      // int latencyInSamples = device.getLatency();
      // System.out.println(latencyInSamples);
      //
      // TextButton playButton = new TextButton("Play sine 200hz", VJoyMain.SKIN);
      // playButton.setColor(Colors.DARK_YELLOW);
      // playButton.addListener(new InputListener() {
      // @Override
      // public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
      // playSample(200, 1.0f);
      // return false;
      // }
      // });
      //
      // TextButton playButton2 = new TextButton("Play sine 400hz", VJoyMain.SKIN);
      // playButton2.setColor(Colors.DARK_YELLOW);
      // playButton2.addListener(new InputListener() {
      // @Override
      // public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
      // playSample(400, 0.3f);
      // return false;
      // }
      // });
      //
      // TextButton playButton3 = new TextButton("Play sine 500hz", VJoyMain.SKIN);
      // playButton3.setColor(Colors.DARK_YELLOW);
      // playButton3.addListener(new InputListener() {
      // @Override
      // public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
      // playSample(500, 0.3f);
      // return false;
      // }
      // });
      //
      // Table table = new Table();
      // table.setFillParent(true);
      // table.add(playButton);
      // table.row();
      // table.add(playButton2);
      // table.row();
      // table.add(playButton3);
      //
      // addActor(table);

   }

   @Override
   public void draw() {
      super.draw();

      texture.dispose();
      texture = new Texture(pixmap);

      Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
      texture.bind();

      shader.begin();
      shader.renderToScreeQuad(new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
      shader.end();

   }

   public static Color getColor(double value) {
      return getColor(value, 0.0, 0.3, false);
   }

   public static Color getColor(double value, double min, double max, boolean isLog) {

      if (Double.isNaN(value)) {
         return new Color(0, 0, 0, 0);
      }

      value = Math.max(value, min);
      value = Math.min(value, max);

      double normalized = 0.0;

      if (isLog) {
         normalized = (Math.log10(value) - Math.log10(min)) / (Math.log10(max) - Math.log10(min));
      } else {
         normalized = (value - min) / (max - min);
      }

      return new Color(Color.HSBtoRGB((1.0f - (float) normalized) * 0.75f, 1.0f, 1.0f)); // cutoff at 0.75 because hue is "circular"
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
