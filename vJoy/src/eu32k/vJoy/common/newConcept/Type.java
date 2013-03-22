package eu32k.vJoy.common.newConcept;

import com.badlogic.gdx.graphics.Color;

import eu32k.vJoy.common.Colors;

public enum Type {
   NUMBER(Colors.DARK_BLUE, Colors.BLUE), IMAGE(Colors.DARK_GREEN, Colors.GREEN), BOOLEAN(Colors.DARK_PURPLE, Colors.PURPLE);

   private final Color normal;
   private final Color selected;

   Type(Color normal, Color selected) {
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
