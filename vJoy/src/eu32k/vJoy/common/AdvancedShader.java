package eu32k.vJoy.common;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;

import eu32k.vJoy.screen.PrimitivesFactory;

public class AdvancedShader extends ShaderProgram {

   public AdvancedShader(String vertexShader, String fragmentShader) {
      super(vertexShader, fragmentShader);
   }

   public void renderToScreeQuad(Vector2 resolution) {
      renderToQuad(null, false, resolution);
   }

   public void renderToQuad(FrameBuffer frameBuffer) {
      renderToQuad(frameBuffer, true, new Vector2(Tools.RESOLUTION, Tools.RESOLUTION));
   }

   public void renderToQuad(FrameBuffer frameBuffer, boolean flip, Vector2 resolution) {
      if (hasUniform("time")) {
         setUniformf("time", Time.getTime());

      }
      if (hasUniform("resolution")) {
         setUniformf("resolution", resolution);
      }

      if (frameBuffer != null) {
         frameBuffer.begin();
      }
      if (flip) {
         PrimitivesFactory.QUAD_FLIPPED.render(this, GL20.GL_TRIANGLE_FAN);
      } else {
         PrimitivesFactory.QUAD_NORMAL.render(this, GL20.GL_TRIANGLE_FAN);
      }

      if (frameBuffer != null) {
         frameBuffer.end();
      }
      end();
   }
}
