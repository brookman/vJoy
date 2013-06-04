package eu32k.vJoy.curveEditor.spectrum;

import com.badlogic.gdx.audio.analysis.KissFFT;
import com.badlogic.gdx.graphics.Pixmap;

import eu32k.vJoy.curveEditor.audio.AudioTrack;
import eu32k.vJoy.curveEditor.misc.Range;

public abstract class ExtendedPixmap extends Pixmap {

   public final static int SAMPLES = 4096 * 2;

   protected AudioTrack track;
   protected KissFFT fft = new KissFFT(SAMPLES);
   protected int passes = 1;
   protected boolean cancel = false;

   private Thread updateThread;
   private boolean isUpdating = false;
   private boolean changed = false;

   public ExtendedPixmap(int width, int height, AudioTrack track, int passes) {
      this(width, height, track);
      this.passes = passes;
   }

   public ExtendedPixmap(int width, int height, AudioTrack track) {
      super(width, height, Format.RGBA8888);
      this.track = track;
   }

   public synchronized void updatePixmap(final Range range, final float position) {
      if (isUpdating) {
         cancel = true;
         try {
            updateThread.join();
         } catch (InterruptedException e) {
            // NOP
         }
      }
      cancel = false;
      isUpdating = true;

      updateThread = new Thread(new Runnable() {
         @Override
         public void run() {
            setColor(0x000000ff);
            fillRectangle(0, 0, getWidth(), getHeight());
            for (int i = 0; i < passes; i++) {
               update(range, position, i);
               if (cancel) {
                  break;
               }
            }
            setChanged(true);
            isUpdating = false;
         }
      });
      updateThread.start();
   }

   protected abstract void update(Range area, float position, int pass);

   public void setChanged(boolean changed) {
      this.changed = changed;
   }

   public boolean hasChanged() {
      return changed;
   }
}
