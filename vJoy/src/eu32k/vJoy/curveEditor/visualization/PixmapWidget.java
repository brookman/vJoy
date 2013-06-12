package eu32k.vJoy.curveEditor.visualization;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;


public class PixmapWidget extends Widget {

   private ExtendedPixmap pixmap;
   private Texture texture;

   public PixmapWidget(ExtendedPixmap pixmap) {
      this.pixmap = pixmap;
      texture = new Texture(pixmap);
      texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
   }

   @Override
   public void draw(SpriteBatch batch, float parentAlpha) {
      float x = getX();
      float y = getY();
      float width = getWidth();
      float height = getHeight();

      if (pixmap.hasChanged()) {
         pixmap.setChanged(false);
         texture.draw(pixmap, 0, 0);
      }
      batch.draw(texture, x, y, width, height);
   }
}
