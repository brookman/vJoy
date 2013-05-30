package eu32k.vJoy;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.cedarsoftware.util.io.JsonReader;
import com.cedarsoftware.util.io.JsonWriter;

import eu32k.vJoy.architect.ArchitectStage;
import eu32k.vJoy.common.Tools;
import eu32k.vJoy.common.newConcept.Workset;
import eu32k.vJoy.curveEditor.curve.CurveEditorStage;
import eu32k.vJoy.screen.ScreenStage;

public class VJoyMain extends App2D {

   private Stage currentStage;

   public static Skin SKIN;

   private Stage mainMenuStage;
   private Stage controllerStage;
   private Stage architectStage;
   private Stage screenStage;
   private Stage debugStage;
   private Stage curveEditorStage;

   public VJoyMain(int size) {
      Tools.RESOLUTION = size;

      BigInteger number = new BigInteger("234234234234234");

      List<BigInteger> list = new ArrayList<BigInteger>();
      list.add(number);
      list.add(number);

      String json = JsonWriter.toJson(new Workset());
      System.out.println("json: " + json);

      Object object = JsonReader.toJava(json);
      System.out.println("object: " + object);

      String json2 = JsonWriter.toJson(new Workset());
      System.out.println("json2: " + json2);

   }

   @Override
   public void create() {
      SKIN = new Skin(Gdx.files.internal("data/uiskin.json"));

      mainMenuStage = new MainMenuStage(this);
      architectStage = new ArchitectStage();
      screenStage = new ScreenStage();
      curveEditorStage = new CurveEditorStage();

      changeStage(curveEditorStage);
   }

   // public void createController(String name, BroadcastAddress address) {
   // try {
   // NetworkModule net = new NetworkModule(name, ClientTypes.TYPE_CONTROLLER,
   // address);
   // changeStage(new ControllerStage(net));
   // } catch (IOException e) {
   // }
   // }

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

   // public void createDebug(String name, BroadcastAddress address) {
   // try {
   // NetworkModule net = new NetworkModule(name + " (DEBUG)",
   // ClientTypes.TYPE_DEBUG, address);
   // changeStage(new DebugStage(net));
   // } catch (IOException e) {
   // }
   // }

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

      // boolean drawHud = Gdx.graphics.getHeight() - Gdx.input.getY() > 10;
      // if (drawHud) {
      currentStage.act(Gdx.graphics.getDeltaTime());
      currentStage.draw();
      // }
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
