package eu32k.vJoy;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import eu32k.common.net.BroadcastAddress;
import eu32k.common.net.NetworkModule;
import eu32k.common.net.NetworkSettings;
import eu32k.vJoy.architect.ArchitectStage;
import eu32k.vJoy.common.Tools;
import eu32k.vJoy.controller.ControllerStage;
import eu32k.vJoy.debug.DebugStage;
import eu32k.vJoy.screen.ScreenStage;

public class VJoyMain extends App2D {

   private Stage currentStage;

   public static Skin SKIN;

   private Stage mainMenuStage;
   private Stage controllerStage;
   private Stage architectStage;
   private Stage screenStage;
   private Stage debugStage;

   public VJoyMain() {
   }

   public VJoyMain(int size) {
      Tools.RESOLUTION = size;
   }

   @Override
   public void create() {
      NetworkSettings.compressData = false;
      NetworkSettings.setMagicWord("vJoy-packet|");
      SKIN = new Skin(Gdx.files.internal("data/uiskin.json"));

      mainMenuStage = new MainMenuStage(this);
      architectStage = new ArchitectStage();
      screenStage = new ScreenStage();

      changeStage(architectStage);
   }

   public void createController(String name, BroadcastAddress address) {
      try {
         NetworkModule net = new NetworkModule(name, ClientTypes.TYPE_CONTROLLER, address);
         changeStage(new ControllerStage(net));
      } catch (IOException e) {
      }
   }

   // public void createArchitect(String name, BroadcastAddress address) {
   // try {
   // // NetworkModule net = new NetworkModule(name, ClientTypes.TYPE_ARCHITECT,
   // address);
   // // architectStage = new ArchitectStage(net);
   // changeStage(architectStage);
   // } catch (IOException e) {
   // }
   // }

   // public void createScreen(String name, BroadcastAddress address) {
   // try {
   // // NetworkModule net = new NetworkModule(name, ClientTypes.TYPE_SCREEN,
   // address);
   // changeStage(screenStage);
   // } catch (IOException e) {
   // }
   // }

   public void createDebug(String name, BroadcastAddress address) {
      try {
         NetworkModule net = new NetworkModule(name + " (DEBUG)", ClientTypes.TYPE_DEBUG, address);
         changeStage(new DebugStage(net));
      } catch (IOException e) {
      }
   }

   private void changeStage(Stage stage) {
      Gdx.input.setInputProcessor(stage);
      Stage oldStage = currentStage;
      currentStage = stage;
      if (oldStage != null) {
         // oldStage.dispose();
      }
   }

   public void showArchitect() {
      changeStage(architectStage);
   }

   public void showScreen() {
      changeStage(screenStage);
   }

   @Override
   public void draw(float delta) {
      Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

      Gdx.gl.glEnable(GL20.GL_BLEND);
      Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

      screenStage.draw();

      boolean drawHud = !Gdx.input.isKeyPressed(Input.Keys.SPACE) && Gdx.graphics.getHeight() - Gdx.input.getY() > 10;
      if (drawHud) {
         currentStage.act(Gdx.graphics.getDeltaTime());
         currentStage.draw();
      }
   }

   @Override
   public void resize(int width, int height) {
      currentStage.setViewport(width, height, true);
   }

   @Override
   public void dispose() {
      currentStage.dispose();
   }

   @Override
   public void init() {
   }
}
