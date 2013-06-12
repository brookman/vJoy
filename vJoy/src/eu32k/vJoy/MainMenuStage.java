package eu32k.vJoy;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import eu32k.common.net.BroadcastAddress;
import eu32k.common.net.PeerToPeerClient;
import eu32k.vJoy.common.Colors;

public class MainMenuStage extends Stage {

   private Object[] addresses = new Object[] {};

   private VJoyMain vJoy;

   public MainMenuStage(VJoyMain vJoy) {
      this.vJoy = vJoy;

      addresses = PeerToPeerClient.findBroadcastAddresses().toArray();

      final SelectBox broadcastSelection = new SelectBox(addresses, VJoyMain.SKIN);
      broadcastSelection.setColor(Colors.DARK_YELLOW);
      final TextField deviceName = new TextField("Device", VJoyMain.SKIN);
      deviceName.setColor(Colors.DARK_YELLOW);

      TextButton controllerButton = new TextButton("Controller [disabled]", VJoyMain.SKIN);
      controllerButton.setColor(Colors.DARK_YELLOW);
      TextButton architectButton = new TextButton("Architect", VJoyMain.SKIN);
      architectButton.setColor(Colors.DARK_YELLOW);
      TextButton screenButton = new TextButton("Screen [disabled]", VJoyMain.SKIN);
      screenButton.setColor(Colors.DARK_YELLOW);

      TextButton curveButton = new TextButton("Curve editor", VJoyMain.SKIN);
      curveButton.setColor(Colors.DARK_YELLOW);

      TextButton debugButton = new TextButton("Debug [disabled]", VJoyMain.SKIN);
      debugButton.setColor(Colors.DARK_YELLOW);

      Table table = new Table();
      table.setFillParent(true);
      table.add(new Label("Broadcast to:", VJoyMain.SKIN)).left().pad(5);
      table.add(broadcastSelection).left().fill().pad(5);
      table.row();
      table.add(new Label("Name:", VJoyMain.SKIN)).left().pad(5);
      table.add(deviceName).fill().pad(5);
      table.row();
      table.add(controllerButton).colspan(2).fill().pad(5);
      table.row();
      table.add(architectButton).colspan(2).fill().pad(5);
      table.row();
      table.add(screenButton).colspan(2).fill().pad(5);
      table.row();
      table.add(curveButton).colspan(2).fill().pad(5);
      table.row();
      table.add(debugButton).colspan(2).fill().pad(5);
      addActor(table);

      controllerButton.addListener(new InputListener() {
         @Override
         public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            // MainMenuStage.vJoy.createController(deviceName.getText(),
            // (BroadcastAddress)
            // addresses[broadcastSelection.getSelectionIndex()]);
            return false;
         }
      });

      architectButton.addListener(new InputListener() {
         @Override
         public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            MainMenuStage.this.vJoy.showArchitect((BroadcastAddress) addresses[broadcastSelection.getSelectionIndex()]);
            return false;
         }
      });

      screenButton.addListener(new InputListener() {
         @Override
         public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

            return false;
         }
      });

      curveButton.addListener(new InputListener() {
         @Override
         public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            MainMenuStage.this.vJoy.showCurveEditor((BroadcastAddress) addresses[broadcastSelection.getSelectionIndex()]);
            return false;
         }
      });

      debugButton.addListener(new InputListener() {
         @Override
         public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            return false;
         }
      });
   }
}
