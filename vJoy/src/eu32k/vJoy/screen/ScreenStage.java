package eu32k.vJoy.screen;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import eu32k.vJoy.common.AdvancedShader;
import eu32k.vJoy.common.Tools;
import eu32k.vJoy.common.workset.Instance;
import eu32k.vJoy.common.workset.Workset;
import eu32k.vJoy.common.workset.atomic.ImageInstance;
import eu32k.vJoy.common.workset.atomic.image.GenShader;
import eu32k.vJoy.common.workset.atomic.image.GenShader.ShaderType;
import eu32k.vJoy.common.workset.atomic.image.TextureImage;
import eu32k.vJoy.common.workset.atomic.image.TextureImage.TextureType;

public class ScreenStage extends Stage {

   private AdvancedShader shader;
   public static Texture DEFAULT_TEXTURE;

   public static long renderCount = 0;

   public ScreenStage() {
      DEFAULT_TEXTURE = Tools.getTextrue("vJoy");

      shader = Tools.getShader("quarter", "simple");
      TextureType t1 = TextureImage.TextureType.VJOY;
      ShaderType t2 = GenShader.ShaderType.DEBUG;

      Gdx.app.setLogLevel(Application.LOG_DEBUG);

      // try {
      // Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
      // while (interfaces.hasMoreElements()) {
      // NetworkInterface networkInterface = interfaces.nextElement();
      // Gdx.app.debug("NET", "interface: " + networkInterface);
      // if (networkInterface.isLoopback()) {
      // Gdx.app.debug("NET", "is loopback");
      // continue; // Don't want to broadcast to the loopback interface
      // }
      // for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
      // Gdx.app.debug("NET", "address: " + interfaceAddress);
      // InetAddress broadcast = interfaceAddress.getBroadcast();
      // Gdx.app.debug("NET", "broadcast: " + broadcast);
      // if (broadcast != null) {
      // // adresses.add(new
      // // BroadcastAddress(networkInterface.getDisplayName(),
      // // broadcast));
      // }
      // }
      // }
      // } catch (SocketException s) {
      // s.printStackTrace();
      // // NOP
      // }
      //
      // PeerToPeerClient client = null;
      //
      // List<BroadcastAddress> addr = PeerToPeerClient.findBroadcastAddresses();
      //
      // Gdx.app.debug("NET", "size: " + addr.size());
      // for (BroadcastAddress a : addr) {
      // System.out.println(a);
      // Gdx.app.debug("NET", "a");
      // // client = new PeerToPeerClient(a);
      // // if (a.getAddress().getHostAddress().equals("1.3.3.255")) {
      // // }
      // }
      // client = new PeerToPeerClient(addr);
      // client.register(NumberValue.class);
      //
      // client.addListener(new Listener() {
      // @Override
      // public void received(Connection connection, Object object) {
      // if (object instanceof NumberValue) {
      // NumberValue v = (NumberValue) object;
      // for (Instance instance : Workset.getInstance().getInstances()) {
      // if (instance instanceof MidiInstance) {
      // MidiInstance mi = (MidiInstance) instance;
      // if (mi.number == v.address) {
      // mi.value = v.value;
      // }
      // }
      // }
      // }
      // }
      // });
      // client.start();
   }

   @Override
   public void draw() {

      Instance exit = Workset.getInstance().getExitInstance();
      if (exit != null && ((ImageInstance) exit).getValue() != null) {
         ((ImageInstance) exit).render();
         Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
         ((ImageInstance) exit).getValue().bind();
      } else {
         Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
         DEFAULT_TEXTURE.bind();
      }

      shader.begin();
      shader.renderToScreeQuad(new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
      shader.end();

      renderCount++;
   }

   // @Override
   // public void packetReceived(Packet packet) {
   // // if (packet.getPayload() == null) {
   // // return;
   // // }
   // //
   // // Object payload = null;
   // // try {
   // // payload = Serializer.deserialize(packet.getPayload());
   // // } catch (Exception e1) {
   // // // NOP
   // // }
   // //
   // // if (payload == null) {
   // // return;
   // // }
   // //
   // // SliderUpdate update = (SliderUpdate) payload;
   // // if (update.id == 0) {
   // // speed = update.value / 255.0f * 5.0f;
   // // }
   // }
}
