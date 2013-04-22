package eu32k.vJoy.common.workset.atomic.number;

import eu32k.vJoy.common.workset.NumberPort;
import eu32k.vJoy.common.workset.atomic.NumberInstance;
import eu32k.vJoy.common.workset.atomic.NumberType;

public class Add extends NumberType {

   private NumberPort input1 = addPort(new NumberPort("Input 1"));
   private NumberPort input2 = addPort(new NumberPort("Input 2"));

   public Add() {
      super("Add");
   }

   @Override
   public NumberInstance instanciate(float x, float y) {
      return new NumberInstance(this, x, y) {

         @Override
         public float getValue() {
            return getPortValue(input1) + getPortValue(input2);
         }
      };
   }
}
