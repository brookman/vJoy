package eu32k.vJoy.common.workset.atomic.number;

import eu32k.vJoy.common.workset.atomic.NumberInstance;
import eu32k.vJoy.common.workset.atomic.NumberType;

public class MidiNumber extends NumberType {

   private static int counter = 1;

   public MidiNumber() {
      super("Midi");

   }

   @Override
   public NumberInstance instanciate(float x, float y) {
      return new MidiInstance(this, x, y);
   }

   public class MidiInstance extends NumberInstance {

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
