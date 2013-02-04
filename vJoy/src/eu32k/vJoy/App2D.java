package eu32k.vJoy;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public abstract class App2D implements ApplicationListener, InputProcessor {

   private List<Disposable> disposables = new ArrayList<Disposable>();

   protected <T extends Disposable> T tag(T disposable) {
      disposables.add(disposable);
      return disposable;
   }

   private float zoom = 1.0f;
   protected OrthographicCamera camera;
   protected SpriteBatch batch;

   private float aspectRatio;
   private int width;
   private int height;

   public abstract void init();

   public abstract void draw(float delta);

   @Override
   public void create() {
      batch = new SpriteBatch();
      init();
      Gdx.input.setInputProcessor(this);
      Gdx.graphics.setVSync(true);
   }

   @Override
   public void render() {
      draw(Gdx.graphics.getDeltaTime());
   }

   public float getZoom() {
      return zoom;
   }

   public void setZoom(float zoom) {
      this.zoom = zoom;
      resetCamera();
   }

   @Override
   public boolean keyDown(int keycode) {
      return false;
   }

   @Override
   public boolean keyUp(int keycode) {
      return false;
   }

   @Override
   public boolean keyTyped(char character) {
      return false;
   }

   @Override
   public boolean touchDown(int x, int y, int pointer, int button) {
      return false;
   }

   @Override
   public boolean touchUp(int x, int y, int pointer, int button) {
      return false;
   }

   @Override
   public boolean touchDragged(int x, int y, int pointer) {
      return false;
   }

   @Override
   public boolean mouseMoved(int screenX, int screenY) {
      return false;
   }

   @Override
   public boolean scrolled(int amount) {
      return false;
   }

   @Override
   public void resize(int width, int height) {
      aspectRatio = (float) width / (float) height;
      this.width = width;
      this.height = height;
      resetCamera();
   }

   private void resetCamera() {
      camera = new OrthographicCamera(width, -height);
      camera.translate(width / 2, height / 2);
      camera.zoom = zoom;
      camera.update();
   }

   @Override
   public void pause() {
   }

   @Override
   public void resume() {
   }

   @Override
   public void dispose() {
      for (Disposable disposable : disposables) {
         disposable.dispose();
      }
   }
}
