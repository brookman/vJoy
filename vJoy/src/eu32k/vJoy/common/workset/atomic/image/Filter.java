package eu32k.vJoy.common.workset.atomic.image;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import eu32k.vJoy.common.AdvancedShader;
import eu32k.vJoy.common.Tools;
import eu32k.vJoy.common.workset.ImagePort;
import eu32k.vJoy.common.workset.atomic.ImageInstance;
import eu32k.vJoy.common.workset.atomic.ImageType;

public class Filter extends ImageType {

   public static enum FilterType {
      GRAY_SCALE("gray"), INVERT("invert"), BLUR_H("blur_h"), BLUR_V("blur_v"), FLY("fly"), PULSE("pulse"), STAR("star"), KALEIDOSCOPE("kaleidoscope"), ASCII("ascii");

      private AdvancedShader shader;

      private FilterType(String fragName) {
         shader = Tools.getShader(fragName);
      }

      public AdvancedShader getShader() {
         return shader;
      }
   }

   private ImagePort image = addPort(new ImagePort("Input"));

   public Filter() {
      super("Filter");
   }

   @Override
   public ImageInstance instanciate(float x, float y) {
      return new FilterInstance(this, x, y);
   }

   public class FilterInstance extends ImageInstance {

      public FilterType filterType = FilterType.GRAY_SCALE;

      public FilterInstance(ImageType type, float x, float y) {
         super(type, x, y);
      }

      @Override
      public void renderInternally() {
         renderPort(image);

         Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
         getPortValue(image).bind();

         AdvancedShader shader = filterType.getShader();

         shader.begin();

         shader.setUniformi("uTexture", 0);

         shader.renderToQuad(frameBuffer);

      }
   }
}