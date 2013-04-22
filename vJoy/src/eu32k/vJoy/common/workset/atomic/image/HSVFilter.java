package eu32k.vJoy.common.workset.atomic.image;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import eu32k.vJoy.common.AdvancedShader;
import eu32k.vJoy.common.Tools;
import eu32k.vJoy.common.workset.ImagePort;
import eu32k.vJoy.common.workset.NumberPort;
import eu32k.vJoy.common.workset.atomic.ImageInstance;
import eu32k.vJoy.common.workset.atomic.ImageType;

public class HSVFilter extends ImageType {

   private ImagePort image = addPort(new ImagePort("Input"));
   private NumberPort h = addPort(new NumberPort("H"));
   private NumberPort s = addPort(new NumberPort("S"));
   private NumberPort v = addPort(new NumberPort("V"));

   public HSVFilter() {
      super("HSV Filter");
   }

   @Override
   public ImageInstance instanciate(float x, float y) {
      return new ImageInstance(this, x, y) {

         private AdvancedShader shader = Tools.getShader("hsv");

         @Override
         public void renderInternally() {
            renderPort(image);

            Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
            getPortValue(image).bind();

            shader.begin();

            shader.setUniformi("uTexture", 0);
            shader.setUniformf("uH", getPortValue(h));
            shader.setUniformf("uS", getPortValue(s));
            shader.setUniformf("uV", getPortValue(v));

            shader.renderToQuad(frameBuffer);
         }
      };
   }
}