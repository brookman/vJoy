package eu32k.vJoy.common.workset.atomic.image;

import eu32k.vJoy.common.AdvancedShader;
import eu32k.vJoy.common.Tools;
import eu32k.vJoy.common.workset.NumberPort;
import eu32k.vJoy.common.workset.atomic.ImageInstance;
import eu32k.vJoy.common.workset.atomic.ImageType;

public class ColorShader extends ImageType {
   private static final long serialVersionUID = -5739501646468746216L;

   private NumberPort red = addPort(new NumberPort("Red"));
   private NumberPort green = addPort(new NumberPort("Green"));
   private NumberPort blue = addPort(new NumberPort("Blue"));

   public ColorShader() {
      super("Color Shader");
   }

   @Override
   public ImageInstance instanciate(float x, float y) {
      return new ImageInstance(this, x, y) {
         private static final long serialVersionUID = -423553906443936788L;

         private AdvancedShader shader = Tools.getShader("color");

         @Override
         public void renderInternally() {
            shader.begin();

            shader.setUniformf("uRed", getPortValue(red));
            shader.setUniformf("uGreen", getPortValue(green));
            shader.setUniformf("uBlue", getPortValue(blue));

            shader.renderToQuad(frameBuffer);
         }
      };
   }
}