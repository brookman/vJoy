package eu32k.vJoy.common.workset.atomic.number;

import com.badlogic.gdx.math.MathUtils;

import eu32k.vJoy.common.Time;
import eu32k.vJoy.common.workset.NumberPort;
import eu32k.vJoy.common.workset.atomic.NumberInstance;
import eu32k.vJoy.common.workset.atomic.NumberType;

public class Timer extends NumberType {

   private NumberPort speedPort = addPort(new NumberPort("Speed"));

   public Timer() {
      super("Timer");
   }

   @Override
   public NumberInstance instanciate(float x, float y) {
      return new NumberInstance(this, x, y) {

         private float lastTime = Time.getTime();
         public float internalTime = 0.0f;

         @Override
         public float getValue() {
            float deltaTime = Time.getTime() - lastTime;
            internalTime += deltaTime * getPortValue(speedPort);
            internalTime = internalTime - MathUtils.floor(internalTime);
            lastTime = Time.getTime();
            return internalTime;
         }
      };
   }
}
