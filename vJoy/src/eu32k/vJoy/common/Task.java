package eu32k.vJoy.common;

public abstract class Task {

   private String name;
   private boolean running = true;
   private boolean paused = false;
   private boolean finished = false;
   private float percentage = 0.0f;
   private Thread thread;

   public Task(String name) {
      this.name = name;
   }

   protected abstract boolean step();

   public void start() {
      clear();

      thread = new Thread(new Runnable() {
         @Override
         public void run() {
            while (running) {
               if (paused) {
                  try {
                     Thread.sleep(1);
                  } catch (InterruptedException e) {
                     // NOP
                  }
               } else if (!step()) {
                  finished = true;
                  break;
               }
            }
         }
      });
      thread.start();
   }

   private void clear() {
      running = true;
      paused = false;
      finished = false;
      percentage = 0.0f;
   }

   public void stop() {
      running = false;
      try {
         thread.join();
      } catch (InterruptedException e) {
         // NOP
      }
      clear();
   }

   public void setPecentage(float percentage) {
      this.percentage = percentage;
   }

   public float getPecentage() {
      return percentage;
   }

   public void setPaused(boolean paused) {
      this.paused = paused;
   }

   public boolean getPaused() {
      return paused;
   }

   public boolean getFinished() {
      return finished;
   }

   public String getName() {
      return name;
   }
}
