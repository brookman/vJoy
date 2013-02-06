package eu32k.vJoy.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import eu32k.common.net.NetworkListener;
import eu32k.common.net.Packet;
import eu32k.vJoy.common.AdvancedShader;
import eu32k.vJoy.common.Tools;
import eu32k.vJoy.common.workset.Instance;
import eu32k.vJoy.common.workset.Workset;
import eu32k.vJoy.common.workset.atomic.ImageInstance;
import eu32k.vJoy.common.workset.atomic.image.GenShader;
import eu32k.vJoy.common.workset.atomic.image.GenShader.ShaderType;
import eu32k.vJoy.common.workset.atomic.image.TextureImage;
import eu32k.vJoy.common.workset.atomic.image.TextureImage.TextureType;

public class ScreenStage extends Stage implements NetworkListener {

   // private NetworkModule net;

   private AdvancedShader shader;
   public static Texture DEFAULT_TEXTURE;

   public static long renderCount = 0;

   public ScreenStage(/* NetworkModule net */) {
      // this.net = net;
      DEFAULT_TEXTURE = Tools.getTextrue("vJoy");

      shader = Tools.getShader("quarter", "simple");
      TextureType t1 = TextureImage.TextureType.VJOY;
      ShaderType t2 = GenShader.ShaderType.DEBUG;

      // net.addNetworkListener(this);
      // net.start();
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

   @Override
   public void packetReceived(Packet packet) {
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
      // SliderUpdate update = (SliderUpdate) payload;
      // if (update.id == 0) {
      // speed = update.value / 255.0f * 5.0f;
      // }
   }
}
