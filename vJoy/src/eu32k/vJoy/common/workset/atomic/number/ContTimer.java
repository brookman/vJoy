package eu32k.vJoy.common.workset.atomic.number;

import com.badlogic.gdx.math.MathUtils;

import eu32k.vJoy.common.Time;
import eu32k.vJoy.common.workset.BooleanPort;
import eu32k.vJoy.common.workset.NumberPort;
import eu32k.vJoy.common.workset.atomic.NumberInstance;
import eu32k.vJoy.common.workset.atomic.NumberType;

public class ContTimer extends NumberType {

   private NumberPort speedPort = addPort(new NumberPort("Speed"));
   private BooleanPort switchPort = addPort(new BooleanPort("Switch"));

   public ContTimer() {
      super("Control Timer");
   }

   @Override
   public NumberInstance instanciate(float x, float y) {
      return new NumberInstance(this, x, y) {

         private float lastTime = Time.getTime();
         public float internalTime = 0.0f;

         @Override
         public float getValue() {
            float deltaTime = Time.getTime() - lastTime;
            float speed = getPortValue(speedPort);
            if (getPortValue(switchPort)) {
               internalTime += deltaTime * speed;
               internalTime = internalTime - MathUtils.floor(internalTime);
            }
            lastTime = Time.getTime();
            return internalTime;
         }
      };
   }
}
