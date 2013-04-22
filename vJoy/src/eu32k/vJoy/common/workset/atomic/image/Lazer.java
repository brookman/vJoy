package eu32k.vJoy.common.workset.atomic.image;

import eu32k.vJoy.common.AdvancedShader;
import eu32k.vJoy.common.Tools;
import eu32k.vJoy.common.workset.NumberPort;
import eu32k.vJoy.common.workset.atomic.ImageInstance;
import eu32k.vJoy.common.workset.atomic.ImageType;

public class Lazer extends ImageType {

   private NumberPort speed1 = addPort(new NumberPort("Speed 1"));
   private NumberPort speed2 = addPort(new NumberPort("Speed 2"));
   private NumberPort speed3 = addPort(new NumberPort("Speed 3"));

   public Lazer() {
      super("Lazer");
   }

   @Override
   public ImageInstance instanciate(float x, float y) {
      return new LazerInstance(this, x, y);
   }

   public class LazerInstance extends ImageInstance {

      private AdvancedShader shader = Tools.getShader("lazer");

      public LazerInstance(ImageType type, float x, float y) {
         super(type, x, y);
      }

      @Override
      public void renderInternally() {
         shader.setClear(false);
         shader.begin();

         shader.setUniformf("uSpeed1", getPortValue(speed1));
         shader.setUniformf("uSpeed2", getPortValue(speed2));
         shader.setUniformf("uSpeed3", getPortValue(speed3));

         shader.renderToQuad(frameBuffer);
      }
   }
}