package eu32k.vJoy.common.workset;

import java.io.Serializable;

import com.badlogic.gdx.graphics.Color;

import eu32k.vJoy.common.Colors;

public enum DataType implements Serializable {
   NUMBER(Colors.DARK_BLUE, Colors.BLUE), IMAGE(Colors.DARK_GREEN, Colors.GREEN), BOOLEAN(Colors.DARK_PURPLE, Colors.PURPLE);

   private final Color normal;
   private final Color selected;

   DataType(Color normal, Color selected) {
      this.normal = normal;
      this.selected = selected;
   }

   public Color getNormalColor() {
      return normal;
   }

   public Color getSelectedColor() {
      return selected;
   }
}
