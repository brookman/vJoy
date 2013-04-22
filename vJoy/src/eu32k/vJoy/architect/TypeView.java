package eu32k.vJoy.architect;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import eu32k.vJoy.VJoyMain;
import eu32k.vJoy.common.Colors;
import eu32k.vJoy.common.workset.DataType;
import eu32k.vJoy.common.workset.Instance;
import eu32k.vJoy.common.workset.Port;
import eu32k.vJoy.common.workset.Type;
import eu32k.vJoy.common.workset.atomic.NumberInstance;
import eu32k.vJoy.common.workset.atomic.image.Filter;
import eu32k.vJoy.common.workset.atomic.image.Filter.FilterInstance;
import eu32k.vJoy.common.workset.atomic.image.GenShader;
import eu32k.vJoy.common.workset.atomic.image.GenShader.GenShaderInstance;
import eu32k.vJoy.common.workset.atomic.image.TextureImage;
import eu32k.vJoy.common.workset.atomic.image.TextureImage.TextureInstance;
import eu32k.vJoy.common.workset.atomic.number.SimpleFunction;
import eu32k.vJoy.common.workset.atomic.number.SimpleFunction.SimpleFunctionInstance;
import eu32k.vJoy.common.workset.atomic.number.SimpleNumber;
import eu32k.vJoy.common.workset.atomic.number.SimpleNumber.Testomat;

public class TypeView extends Window {

   private Type type;
   private Instance instance;

   private HashMap<Port, Label> ports = new HashMap<Port, Label>();

   private ArchitectStage stage;
   private Slider slider;
   private SelectBox selectBox;

   public TypeView(ArchitectStage stage, Type type, Instance instance) {
      super(type.getName(), VJoyMain.SKIN);
      this.stage = stage;
      this.type = type;
      this.instance = instance;
      update();

      addListener(new InputListener() {
         @Override
         public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            TypeView.this.stage.setSelectedInstance(TypeView.this.instance);
            return false;
         }

         @Override
         public void touchDragged(InputEvent event, float x, float y, int pointer) {
         }

         @Override
         public boolean mouseMoved(InputEvent event, float x, float y) {
            return false;
         }

         // @Override
         // public boolean scrolled(InputEvent event, int amount) {
         // return false;
         // }

         @Override
         public boolean keyDown(InputEvent event, int keycode) {
            return false;
         }

         @Override
         public boolean keyUp(InputEvent event, int keycode) {
            return false;
         }

         @Override
         public boolean keyTyped(InputEvent event, char character) {
            return false;
         }
      });
   }

   public void update() {
      if (stage.getSelectedInstance() != null && stage.getSelectedInstance() == instance) {
         setColor(DataType.SELECTED_COLORS[type.getDataType()]);
      } else {
         setColor(DataType.NORMAL_COLORS[type.getDataType()]);
      }

      String titleString = type.getName();
      setTitle(titleString);

      ArrayList<Port> toRemove = new ArrayList<Port>();
      for (Port port : ports.keySet()) {
         if (!type.getPorts().contains(port)) {
            toRemove.add(port);
         }
      }

      for (Port port : toRemove) {
         ports.remove(port);
      }

      clear();
      if (type.getDataType() == DataType.NUMBER) {

         if (type instanceof SimpleFunction) {
            final SimpleFunctionInstance simpleFunctionInstance = (SimpleFunctionInstance) instance;
            if (selectBox == null) {
               selectBox = new SelectBox(SimpleFunction.FunctionType.values(), VJoyMain.SKIN);
               selectBox.setColor(Colors.DARK_YELLOW);
            } else {
               simpleFunctionInstance.functionType = SimpleFunction.FunctionType.values()[selectBox.getSelectionIndex()];
            }

            add(selectBox).fill();
            row();
         }
         if (slider == null) {
            slider = new Slider(0.0f, 1.0f, 0.001f, false, VJoyMain.SKIN);
            if (type instanceof SimpleNumber) {
               slider.addListener(new ChangeListener() {
                  @Override
                  public void changed(ChangeEvent event, Actor actor) {
                     ((Testomat) instance).value = slider.getValue();
                  }
               });
            } else {
               slider.setTouchable(Touchable.disabled);
            }
            slider.setColor(DataType.NORMAL_COLORS[type.getDataType()]);
         }
         if (!(type instanceof SimpleNumber)) {
            slider.setValue(MathUtils.clamp(((NumberInstance) instance).getValue(), 0.0f, 1.0f));
         }
         add(slider).width(100);// .fill();
         row();
      } else if (type.getDataType() == DataType.IMAGE) {
         if (type instanceof Filter) {
            final FilterInstance filterInstance = (FilterInstance) instance;
            if (selectBox == null) {
               selectBox = new SelectBox(Filter.FilterType.values(), VJoyMain.SKIN);
               selectBox.setColor(Colors.DARK_YELLOW);
            } else {
               filterInstance.filterType = Filter.FilterType.values()[selectBox.getSelectionIndex()];
            }

            add(selectBox).fill();
            row();
         } else if (type instanceof GenShader) {
            final GenShaderInstance genShaderInstance = (GenShaderInstance) instance;
            if (selectBox == null) {
               selectBox = new SelectBox(GenShader.ShaderType.values(), VJoyMain.SKIN);
               selectBox.setColor(Colors.DARK_YELLOW);
            } else {
               genShaderInstance.shaderType = GenShader.ShaderType.values()[selectBox.getSelectionIndex()];
            }

            add(selectBox).fill();
            row();
         } else if (type instanceof TextureImage) {
            final TextureInstance textureInstance = (TextureInstance) instance;
            if (selectBox == null) {
               selectBox = new SelectBox(TextureImage.TextureType.values(), VJoyMain.SKIN);
               selectBox.setColor(Colors.DARK_YELLOW);
            } else {
               textureInstance.textureType = TextureImage.TextureType.values()[selectBox.getSelectionIndex()];
            }

            add(selectBox).fill();
            row();
         }
      }

      left();

      for (final Port port : type.getPorts()) {
         Label label = ports.get(port);
         if (label == null) {
            label = new Label(port.getName(), VJoyMain.SKIN);
            label.addListener(new InputListener() {
               @Override
               public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                  stage.setSelectedPort(instance, port);
                  return false;
               }

               @Override
               public void touchDragged(InputEvent event, float x, float y, int pointer) {
               }

               @Override
               public boolean mouseMoved(InputEvent event, float x, float y) {
                  return false;
               }

               // @Override
               // public boolean scrolled(InputEvent event, int amount) {
               // return false;
               // }

               @Override
               public boolean keyDown(InputEvent event, int keycode) {
                  return false;
               }

               @Override
               public boolean keyUp(InputEvent event, int keycode) {
                  return false;
               }

               @Override
               public boolean keyTyped(InputEvent event, char character) {
                  return false;
               }
            });
            ports.put(port, label);
         }

         if (stage.getSelectedInstance() != null && stage.getSelectedInstance() == instance && stage.getSelectedPort() != null && stage.getSelectedPort() == port) {
            label.setColor(DataType.SELECTED_COLORS[port.getDataType()]);
         } else {
            label.setColor(DataType.NORMAL_COLORS[port.getDataType()]);
         }

         add(label).left();
         row();
      }
      pack();
   }

   public float getConnectorX() {
      return getX() + getWidth();
   }

   public float getConnectorY() {
      return getY() + getHeight() / 2.0f;
   }

   public float getPortX(Port port) {
      return getX();
   }

   public float getPortY(Port port) {
      return getY() + ports.get(port).getY() + 12.0f;
   }
}
