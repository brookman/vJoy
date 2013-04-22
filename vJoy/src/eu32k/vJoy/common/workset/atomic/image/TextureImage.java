package eu32k.vJoy.common.workset.atomic.image;

import com.badlogic.gdx.graphics.Texture;

import eu32k.vJoy.common.Tools;
import eu32k.vJoy.common.workset.atomic.ImageInstance;
import eu32k.vJoy.common.workset.atomic.ImageType;

public class TextureImage extends ImageType {

   public static enum TextureType {
      VJOY("vJoy"), FOREST("forest000"), DESERT("desert000"), TREE("tree000");

      private Texture texture;

      private TextureType(String texturePath) {
         texture = Tools.getTextrue(texturePath);
      }

      public Texture getTexture() {
         return texture;
      }
   }

   public TextureImage() {
      super("Texture");
   }

   @Override
   public ImageInstance instanciate(float x, float y) {
      return new TextureInstance(this, x, y);
   }

   public class TextureInstance extends ImageInstance {

      public TextureType textureType = TextureType.FOREST;

      public TextureInstance(ImageType type, float x, float y) {
         super(type, x, y);
      }

      @Override
      public Texture getValue() {
         return textureType.getTexture();
      }

      @Override
      public void renderInternally() {
         // NOP
      }
   }
}
