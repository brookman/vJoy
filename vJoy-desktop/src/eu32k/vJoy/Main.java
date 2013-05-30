package eu32k.vJoy;

import java.awt.Toolkit;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
   public static void main(String[] args) {

      // new DebugWindow();

      LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
      cfg.title = "vJoy";
      cfg.useGL20 = true;
      cfg.useCPUSynch = true;
      cfg.samples = 4;
      cfg.useGL20 = true;
      cfg.vSyncEnabled = true;
      cfg.resizable = true;
      cfg.addIcon("textures/icon_small.png", FileType.Local);

      int width = Toolkit.getDefaultToolkit().getScreenSize().width - 100;
      int height = Toolkit.getDefaultToolkit().getScreenSize().height - 100;
      if (args.length == 1 && args[0].equals("-fullscreen")) {
         cfg.fullscreen = true;
         width = Toolkit.getDefaultToolkit().getScreenSize().width;
         height = Toolkit.getDefaultToolkit().getScreenSize().height;
      }
      cfg.width = width;
      cfg.height = height;

      VJoyMain vjoy1 = new VJoyMain(height);
      // VJoyMain vjoy2 = new VJoyMain();
      //
      // LwjglAWTCanvas canvas1 = new LwjglAWTCanvas(vjoy1, true);
      // LwjglAWTCanvas canvas2 = new LwjglAWTCanvas(vjoy2, true);
      // JFrame frame1 = new JFrame("frame1");
      // frame1.add(canvas1.getCanvas());
      // frame1.setSize(800, 600);
      // frame1.setVisible(true);
      // JFrame frame2 = new JFrame("frame2");
      // frame2.add(canvas2.getCanvas());
      // frame2.setSize(800, 600);
      // frame2.setVisible(true);

      LwjglApplication app1 = new LwjglApplication(vjoy1, cfg);
   }
}
