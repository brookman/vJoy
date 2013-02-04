package eu32k.vJoy.common.workset.atomic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

import eu32k.vJoy.common.Tools;
import eu32k.vJoy.common.workset.Instance;

public abstract class ImageInstance extends Instance {
   private static final long serialVersionUID = 5081927975759923189L;

   protected FrameBuffer frameBuffer = Tools.makeFrameBuffer();

   private long lastRenderCount = -1;

   public ImageInstance(ImageType type, float x, float y) {
      super(type, x, y);
   }

   public void render() {
      // if (ScreenStage.renderCount != lastRenderCount) {
      renderInternally();
      // lastRenderCount = ScreenStage.renderCount;
      // }
   }

   public abstract void renderInternally();

   public Texture getValue() {
      return frameBuffer.getColorBufferTexture();
   }
}
