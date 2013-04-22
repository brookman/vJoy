package eu32k.vJoy.common.workset.atomic.image;

import eu32k.vJoy.common.AdvancedShader;
import eu32k.vJoy.common.Tools;
import eu32k.vJoy.common.workset.atomic.ImageInstance;
import eu32k.vJoy.common.workset.atomic.ImageType;

public class GenShader extends ImageType {

   public static enum ShaderType {
      DEBUG("debug"), CREATION("creation"), EYE("eye"), META_BLOB("metablob"), MONJORI("monjori"), NAUTILUS("nautilus"), PLASMA("plasma");

      private AdvancedShader shader;

      private ShaderType(String fragName) {
         shader = Tools.getShader(fragName);
      }

      public AdvancedShader getShader() {
         return shader;
      }
   }

   public GenShader() {
      super("Gen Shader");
   }

   @Override
   public ImageInstance instanciate(float x, float y) {
      return new GenShaderInstance(this, x, y);
   }

   public class GenShaderInstance extends ImageInstance {

      public ShaderType shaderType = ShaderType.EYE;

      public GenShaderInstance(ImageType type, float x, float y) {
         super(type, x, y);
      }

      @Override
      public void renderInternally() {

         AdvancedShader shader = shaderType.getShader();
         shader.begin();
         shader.renderToQuad(frameBuffer);
      }
   }
}