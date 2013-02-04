package eu32k.vJoy.architect;

import com.badlogic.gdx.scenes.scene2d.ui.Window;

import eu32k.vJoy.VJoyMain;
import eu32k.vJoy.common.workset.Port;

public class PortView extends Window {

   private Port port;

   public PortView(Port port) {
      super(port.getName(), VJoyMain.SKIN);
      this.port = port;
      setMovable(false);
   }

   public void update() {
      setTitle(port.getName());
      setColor(port.getDataType().getNormalColor());
      pack();
   }

   public float getConnectorX() {
      return getX() + getWidth();
   }

   public float getConnectorY() {
      return getY() + getHeight() / 2.0f;
   }
}