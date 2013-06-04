package eu32k.vJoy.curveEditor.misc;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;

public abstract class KeyPressEvent {
   private static List<KeyPressEvent> events = new ArrayList<KeyPressEvent>();

   private boolean armed = true;
   private int key;

   public KeyPressEvent(int key) {
      this.key = key;
      events.add(this);
   }

   public static void update() {
      for (KeyPressEvent event : events) {
         event.updateInternal();
      }
   }

   private void updateInternal() {
      if (Gdx.input.isKeyPressed(key)) {
         if (armed) {
            onPress();
         }
         armed = false;
      } else {
         if (!armed) {
            onRelease();
         }
         armed = true;
      }
   }

   public void onPress() {
      // override this
   }

   public void onRelease() {
      // override this
   }
}
