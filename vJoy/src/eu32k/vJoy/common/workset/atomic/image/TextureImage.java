package eu32k.vJoy.common.workset.atomic.image;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;

import eu32k.vJoy.common.workset.atomic.ImageInstance;
import eu32k.vJoy.common.workset.atomic.ImageType;

public class TextureImage extends ImageType {
   private static final long serialVersionUID = 2517352030698538471L;

   public static enum TextureType {
      FOREST("forest000"), DESERT("desert000"), TREE("tree000");

      private Texture texture;

      private TextureType(String texturePath) {
         texture = new Texture("textures/" + texturePath + ".png");
         texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
         texture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
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
      private static final long serialVersionUID = -1206257568705784528L;

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
