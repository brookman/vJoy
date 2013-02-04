package eu32k.vJoy.common.workset.atomic.number;

import com.badlogic.gdx.math.MathUtils;

import eu32k.vJoy.common.Time;
import eu32k.vJoy.common.workset.BooleanPort;
import eu32k.vJoy.common.workset.NumberPort;
import eu32k.vJoy.common.workset.atomic.NumberInstance;
import eu32k.vJoy.common.workset.atomic.NumberType;

public class TriTimer extends NumberType {
   private static final long serialVersionUID = -7956241978407245944L;

   private NumberPort speedPort = addPort(new NumberPort("Speed"));
   private BooleanPort triggerPort = addPort(new BooleanPort("Trigger"));

   public TriTimer() {
      super("Trigger Timer");
   }

   @Override
   public NumberInstance instanciate(float x, float y) {
      return new NumberInstance(this, x, y) {
         private static final long serialVersionUID = 2620469920518471027L;

         private float lastTime = Time.getTime();
         private float internalTime = 0.0f;
         private boolean lastTriggerValue = false;

         @Override
         public float getValue() {
            float deltaTime = Time.getTime() - lastTime;
            float speed = getPortValue(speedPort);
            boolean trigger = getPortValue(triggerPort);
            if (trigger && !lastTriggerValue) {
               internalTime = 0.0f;
            }
            lastTriggerValue = trigger;
            if (internalTime < 1.0f) {
               internalTime += deltaTime * speed;
               internalTime = MathUtils.clamp(internalTime, 0.0f, 1.0f);
            }
            lastTime = Time.getTime();
            return internalTime;
         }
      };
   }
}
