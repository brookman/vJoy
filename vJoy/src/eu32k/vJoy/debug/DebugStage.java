package eu32k.vJoy.debug;

import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import eu32k.common.net.Client;
import eu32k.common.net.NetworkListener;
import eu32k.common.net.NetworkModule;
import eu32k.common.net.Packet;
import eu32k.common.net.ThreadUtil;
import eu32k.vJoy.VJoyMain;

public class DebugStage extends Stage implements NetworkListener {

   private NetworkModule net;
   private Label leftLabel;
   private Label rightLabel;
   private String log = "";
   private ScrollPane scrollPane;

   public DebugStage(NetworkModule net) {
      this.net = net;

      leftLabel = new Label("Debug1", VJoyMain.SKIN);
      leftLabel.setWrap(true);
      leftLabel.setAlignment(Align.top | Align.left);

      rightLabel = new Label("Debug2", VJoyMain.SKIN);
      rightLabel.setWrap(true);
      rightLabel.setAlignment(Align.top | Align.left);

      scrollPane = new ScrollPane(rightLabel, VJoyMain.SKIN);
      Table table = new Table();
      table.setFillParent(true);
      table.add(leftLabel).fill().pad(20).expand();
      table.add(scrollPane).fill().pad(20).expand();
      addActor(table);

      net.addNetworkListener(this);
      net.start();
      ThreadUtil.startLoopThread(new Runnable() {
         @Override
         public void run() {
            update();
         }
      }, 500);
   }

   private void update() {
      String newText = "";

      newText += "Me: ";
      newText += net.getPacket().getName() + " ";
      newText += "(" + net.getPacket().getType() + ") ";
      newText += net.getPacket().getId() + "\n";

      Map<Long, Client> clients = net.getKnownClients();
      synchronized (clients) {
         for (Client client : clients.values()) {
            newText += client.isActive() ? "" : "! ";
            newText += client.getPacket().getName() + " ";
            newText += "(" + client.getPacket().getType() + ") ";
            newText += client.getAddress().getHostAddress() + " ";
            newText += client.getPacket().getId() + "\n";
         }
      }
      leftLabel.setText(newText);
      rightLabel.setText(log);
   }

   @Override
   public void packetReceived(Packet packet) {
      log += packet.getName() + " " + packet.getId() + "\n";
   }
}
