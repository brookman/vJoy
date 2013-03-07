package eu32k.vJoy.controller;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import eu32k.vJoy.VJoyMain;
import eu32k.vJoy.common.Colors;

public class ControllerStage extends Stage {

   private List<Slider> sliders = new ArrayList<Slider>();

   private boolean internal = false;

   public ControllerStage() {

      for (int i = 0; i < 7; i++) {
         final Slider slider = new Slider(0, 2000, 1, false, VJoyMain.SKIN);
         slider.setColor(Colors.DARK_YELLOW);
         final int id = i;
         slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
               if (internal) {
                  internal = false;
                  return;
               }

               SliderUpdate update = new SliderUpdate();
               update.id = id;
               update.value = slider.getValue();
               // ControllerStage.this.net.broadcast(update);
            }
         });
         sliders.add(slider);
      }

      Table table = new Table();
      table.setFillParent(true);
      for (Slider slider : sliders) {
         table.add(slider).fill().pad(20).expandX();
         table.row();
      }
      addActor(table);

   }

   // @Override
   // public void packetReceived(Packet packet) {
   // if (packet.getPayload() == null) {
   // return;
   // }
   //
   // Object payload = null;
   // try {
   // payload = Serializer.deserialize(packet.getPayload());
   // } catch (Exception e1) {
   // // NOP
   // }
   //
   // if (payload == null) {
   // return;
   // }
   //
   // if (packet.getName().equals(net.getPacket().getName()) && payload
   // instanceof SliderUpdate) {
   // SliderUpdate update = (SliderUpdate) payload;
   // // if (update.value != slider0.getValue()) {
   // internal = true;
   // sliders.get(update.id).setValue(update.value);
   // // }
   // }
   // }
}
