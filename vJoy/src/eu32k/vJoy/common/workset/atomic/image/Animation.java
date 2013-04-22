package eu32k.vJoy.common.workset.atomic.image;

import com.badlogic.gdx.graphics.Texture;

import eu32k.vJoy.common.AdvancedShader;
import eu32k.vJoy.common.Tools;
import eu32k.vJoy.common.workset.NumberPort;
import eu32k.vJoy.common.workset.atomic.ImageInstance;
import eu32k.vJoy.common.workset.atomic.ImageType;

public class Animation extends ImageType {

   private NumberPort time = addPort(new NumberPort("Time"));

   public Animation() {
      super("Animation");
   }

   @Override
   public ImageInstance instanciate(float x, float y) {
      return new ImageInstance(this, x, y) {

         private AdvancedShader shader = Tools.getShader("mixer");

         @Override
         public Texture getValue() {
            return Tools.getTextrue("forest000");
         }

         @Override
         public void renderInternally() {
            // NOP
         }
      };
   }
}
