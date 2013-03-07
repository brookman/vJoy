package eu32k.vJoy.common.workset.atomic.number;

import eu32k.vJoy.common.workset.atomic.NumberInstance;
import eu32k.vJoy.common.workset.atomic.NumberType;

public class MidiNumber extends NumberType {
   private static final long serialVersionUID = -9089546992564367546L;

   private static int counter = 1;

   public MidiNumber() {
      super("Midi");

   }

   @Override
   public NumberInstance instanciate(float x, float y) {
      return new MidiInstance(this, x, y);
   }

   public class MidiInstance extends NumberInstance {
      private static final long serialVersionUID = 3119144801505725107L;

      public float value = 0.0f;
      public float number;

      public MidiInstance(NumberType type, float x, float y) {
         super(type, x, y);
         number = counter++;
      }

      @Override
      public float getValue() {
         return value;
      }

   }
}
