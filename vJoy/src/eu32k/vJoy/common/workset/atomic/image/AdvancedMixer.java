package eu32k.vJoy.common.workset.atomic.image;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import eu32k.vJoy.common.AdvancedShader;
import eu32k.vJoy.common.Tools;
import eu32k.vJoy.common.workset.ImagePort;
import eu32k.vJoy.common.workset.atomic.ImageInstance;
import eu32k.vJoy.common.workset.atomic.ImageType;

public class AdvancedMixer extends ImageType {
   private static final long serialVersionUID = 5513833461098942443L;

   private ImagePort imageA = addPort(new ImagePort("Image A"));
   private ImagePort imageB = addPort(new ImagePort("Image B"));
   private ImagePort mask = addPort(new ImagePort("Mask"));

   public AdvancedMixer() {
      super("Advanced Mixer");
   }

   @Override
   public ImageInstance instanciate(float x, float y) {
      return new ImageInstance(this, x, y) {
         private static final long serialVersionUID = 2620469920518471027L;

         private AdvancedShader shader = Tools.getShader("mixer2");

         @Override
         public void renderInternally() {
            renderPort(imageA);
            renderPort(imageB);
            renderPort(mask);

            Gdx.gl.glActiveTexture(GL20.GL_TEXTURE2);
            getPortValue(mask).bind();

            Gdx.gl.glActiveTexture(GL20.GL_TEXTURE1);
            getPortValue(imageB).bind();

            Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
            getPortValue(imageA).bind();

            shader.begin();

            shader.setUniformi("uTexture3", 2);
            shader.setUniformi("uTexture2", 1);
            shader.setUniformf("uTexture1", 0);

            shader.renderToQuad(frameBuffer);
         }
      };
   }
}