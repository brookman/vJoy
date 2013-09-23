package eu32k.vJoy.common.workset.atomic.image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

import eu32k.vJoy.common.AdvancedShader;
import eu32k.vJoy.common.Tools;
import eu32k.vJoy.common.workset.NumberPort;
import eu32k.vJoy.common.workset.atomic.ImageInstance;
import eu32k.vJoy.common.workset.atomic.ImageType;

public class Animation extends ImageType {

   private NumberPort time = addPort(new NumberPort("Time"));

   private List<String> texturePaths = new ArrayList<String>();
   private Texture[] textures;

   private AssetManager man;

   public Animation() {
      super("Animation");
      man = new AssetManager();
      File path = new File("D:/bt_sync/VJ/Clips/smoke");
      for (File file : path.listFiles()) {
         String s = "textures/smoke/" + file.getName();
         texturePaths.add(s);
         man.load(s, Texture.class);
         System.out.println("load " + s);
      }
      textures = new Texture[texturePaths.size()];
   }

   @Override
   public ImageInstance instanciate(float x, float y) {
      return new ImageInstance(this, x, y) {

         private AdvancedShader shader = Tools.getShader("mixer");

         @Override
         public Texture getValue() {
            man.update();
            int index = MathUtils.clamp(Math.round(getPortValue(time) * texturePaths.size()), 0, texturePaths.size() - 1);
            if (textures[index] != null) {
               return textures[index];
            }
            if (man.isLoaded(texturePaths.get(index))) {
               textures[index] = man.get(texturePaths.get(index), Texture.class);
               return textures[index];
            }
            return Tools.getTextrue("forest000");
         }

         @Override
         public void renderInternally() {
            // NOP
         }
      };
   }
}
