package eu32k.vJoy.common.workset.atomic.image;

import eu32k.vJoy.common.AdvancedShader;
import eu32k.vJoy.common.Tools;
import eu32k.vJoy.common.workset.NumberPort;
import eu32k.vJoy.common.workset.atomic.ImageInstance;
import eu32k.vJoy.common.workset.atomic.ImageType;

public class ColorShader extends ImageType {

   private NumberPort red = addPort(new NumberPort("Red"));
   private NumberPort green = addPort(new NumberPort("Green"));
   private NumberPort blue = addPort(new NumberPort("Blue"));
   private NumberPort alpha = addPort(new NumberPort("Alpha"));

   public ColorShader() {
      super("Color Shader");
   }

   @Override
   public ImageInstance instanciate(float x, float y) {
      return new ImageInstance(this, x, y) {

         private AdvancedShader shader = Tools.getShader("color");

         @Override
         public void renderInternally() {
            shader.begin();

            shader.setUniformf("uRed", getPortValue(red));
            shader.setUniformf("uGreen", getPortValue(green));
            shader.setUniformf("uBlue", getPortValue(blue));
            shader.setUniformf("uAlpha", getPortValue(alpha));

            shader.renderToQuad(frameBuffer);
         }
      };
   }
}