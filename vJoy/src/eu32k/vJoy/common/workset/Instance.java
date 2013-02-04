package eu32k.vJoy.common.workset;

import java.io.Serializable;
import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;

import eu32k.vJoy.common.workset.atomic.BooleanInstance;
import eu32k.vJoy.common.workset.atomic.ImageInstance;
import eu32k.vJoy.common.workset.atomic.NumberInstance;
import eu32k.vJoy.screen.ScreenStage;

public class Instance implements Serializable {
   private static final long serialVersionUID = -3667425348135474848L;

   private Type type;
   private HashMap<Port, Instance> portMapping = new HashMap<Port, Instance>();

   private float x;
   private float y;

   public Instance(Type type, float x, float y) {
      setType(type);
      this.x = x;
      this.y = y;
   }

   public Type getType() {
      return type;
   }

   public void setType(Type type) {
      this.type = type;
   }

   public float getX() {
      return x;
   }

   public void setX(float x) {
      this.x = x;
   }

   public float getY() {
      return y;
   }

   public void setY(float y) {
      this.y = y;
   }

   public HashMap<Port, Instance> getPortMapping() {
      return portMapping;
   }

   public boolean getPortValue(BooleanPort port) {
      Instance instance = getPortMapping().get(port);
      if (instance != null && instance instanceof BooleanInstance) {
         BooleanInstance booleanInstance = (BooleanInstance) instance;
         return booleanInstance.getValue();
      }
      return false;
   }

   public float getPortValue(NumberPort port) {
      Instance instance = getPortMapping().get(port);
      if (instance != null && instance instanceof NumberInstance) {
         NumberInstance numberInstance = (NumberInstance) instance;
         return numberInstance.getValue();
      }
      return 0.0f;
   }

   public Texture getPortValue(ImagePort port) {
      Instance instance = getPortMapping().get(port);
      if (instance != null && instance instanceof ImageInstance) {
         ImageInstance imageInstance = (ImageInstance) instance;
         return imageInstance.getValue();
      }
      return ScreenStage.tex1;
   }

   public void renderPort(ImagePort port) {
      Instance instance = getPortMapping().get(port);
      if (instance != null && instance instanceof ImageInstance) {
         ImageInstance imageInstance = (ImageInstance) instance;
         imageInstance.render();
      }
   }
}
