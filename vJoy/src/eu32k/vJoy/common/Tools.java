package eu32k.vJoy.common;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

public class Tools {

   public static int RESOLUTION = 512;

   private static HashMap<String, AdvancedShader> map = new HashMap<String, AdvancedShader>();

   public static AdvancedShader getShader(String fragmentName) {
      return getShader("simple", fragmentName);
   }

   public static AdvancedShader getShader(String vertexName, String fragmentName) {
      String combined = vertexName + fragmentName;
      AdvancedShader shader = map.get(combined);
      if (shader == null) {
         shader = new AdvancedShader(Gdx.files.internal("shaders/" + vertexName + ".vsh").readString(), Gdx.files.internal("shaders/" + fragmentName + ".fsh").readString());
         System.out.println(shader.getLog());
         map.put(combined, shader);
      }
      return shader;
   }

   public static FrameBuffer makeFrameBuffer() {
      FrameBuffer frameBuffer = new FrameBuffer(Format.RGBA8888, RESOLUTION, RESOLUTION, false);
      frameBuffer.getColorBufferTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
      frameBuffer.getColorBufferTexture().setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
      return frameBuffer;
   }
}
