package eu32k.vJoy.curveEditor.spectrum;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

public class PixmapWidget extends Widget {

   private SpectrumPixmap pixmap;
   private Texture texture;

   public PixmapWidget(SpectrumPixmap pixmap) {
      this.pixmap = pixmap;
      texture = new Texture(pixmap);
   }

   @Override
   public void draw(SpriteBatch batch, float parentAlpha) {
      float x = getX();
      float y = getY();
      float width = getWidth();
      float height = getHeight();

      texture.draw(pixmap, 0, 0);
      batch.draw(texture, x, y, width, height);
   }
}
