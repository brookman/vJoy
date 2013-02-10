package eu32k.vJoy.common.workset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import eu32k.vJoy.common.workset.atomic.bool.And;
import eu32k.vJoy.common.workset.atomic.bool.False;
import eu32k.vJoy.common.workset.atomic.bool.Not;
import eu32k.vJoy.common.workset.atomic.bool.Or;
import eu32k.vJoy.common.workset.atomic.bool.Threshold;
import eu32k.vJoy.common.workset.atomic.bool.True;
import eu32k.vJoy.common.workset.atomic.bool.Xor;
import eu32k.vJoy.common.workset.atomic.image.AddAlpha;
import eu32k.vJoy.common.workset.atomic.image.AddImages;
import eu32k.vJoy.common.workset.atomic.image.BlackAndWhiteFilter;
import eu32k.vJoy.common.workset.atomic.image.ColorShader;
import eu32k.vJoy.common.workset.atomic.image.Filter;
import eu32k.vJoy.common.workset.atomic.image.GenShader;
import eu32k.vJoy.common.workset.atomic.image.HSVFilter;
import eu32k.vJoy.common.workset.atomic.image.Lazer;
import eu32k.vJoy.common.workset.atomic.image.MaskMixer;
import eu32k.vJoy.common.workset.atomic.image.RotateZoomFilter;
import eu32k.vJoy.common.workset.atomic.image.SimpleMixer;
import eu32k.vJoy.common.workset.atomic.image.TextureImage;
import eu32k.vJoy.common.workset.atomic.number.Add;
import eu32k.vJoy.common.workset.atomic.number.ContTimer;
import eu32k.vJoy.common.workset.atomic.number.Multiply;
import eu32k.vJoy.common.workset.atomic.number.Random;
import eu32k.vJoy.common.workset.atomic.number.SimpleFunction;
import eu32k.vJoy.common.workset.atomic.number.SimpleNumber;
import eu32k.vJoy.common.workset.atomic.number.Timer;
import eu32k.vJoy.common.workset.atomic.number.TriTimer;

public class Workset implements Serializable {
   private static final long serialVersionUID = -1234823133890876777L;

   public List<Type> types = new ArrayList<Type>();

   public List<Type> booleanTypes = new ArrayList<Type>();
   public List<Type> numberTypes = new ArrayList<Type>();
   public List<Type> imageTypes = new ArrayList<Type>();

   private List<Instance> instances = new ArrayList<Instance>();

   private Instance exitInstance;

   public List<Instance> getInstances() {
      return instances;
   }

   public Instance getExitInstance() {
      return exitInstance;
   }

   public void setExitInstance(Instance exitInstance) {
      this.exitInstance = exitInstance;
   }

   private static Workset instance;

   public static Workset getInstance() {
      if (instance == null) {
         instance = new Workset();
      }
      return instance;
   }

   private Workset() {

      SimpleNumber simpleNumber = new SimpleNumber();
      Add add = new Add();
      Multiply multiply = new Multiply();
      Random random = new Random();
      SimpleFunction simpleFunction = new SimpleFunction();
      Timer timer = new Timer();
      TriTimer triTimer = new TriTimer();
      ContTimer contTimer = new ContTimer();
      TextureImage simpleImage = new TextureImage();
      GenShader eyeShader = new GenShader();
      ColorShader colorShader = new ColorShader();
      HSVFilter hsvFilter = new HSVFilter();
      Filter grayscaleFilter = new Filter();
      BlackAndWhiteFilter blackAndWhiteFilter = new BlackAndWhiteFilter();
      RotateZoomFilter rotateZoomFilter = new RotateZoomFilter();
      SimpleMixer simpleMixer = new SimpleMixer();
      AddImages addImages = new AddImages();
      AddAlpha stackImages = new AddAlpha();
      MaskMixer advancedMixer = new MaskMixer();
      Lazer lazer = new Lazer();

      types.add(simpleNumber);
      types.add(add);
      types.add(multiply);
      types.add(random);
      types.add(simpleFunction);
      types.add(timer);
      types.add(triTimer);
      types.add(contTimer);
      types.add(simpleImage);
      types.add(eyeShader);
      types.add(colorShader);
      types.add(hsvFilter);
      types.add(grayscaleFilter);
      types.add(blackAndWhiteFilter);
      types.add(rotateZoomFilter);
      types.add(simpleMixer);
      types.add(addImages);
      types.add(stackImages);
      types.add(advancedMixer);
      types.add(lazer);

      numberTypes.add(simpleNumber);
      numberTypes.add(add);
      numberTypes.add(multiply);
      numberTypes.add(random);
      numberTypes.add(simpleFunction);
      numberTypes.add(timer);
      numberTypes.add(triTimer);
      numberTypes.add(contTimer);

      imageTypes.add(simpleImage);
      imageTypes.add(eyeShader);
      imageTypes.add(colorShader);
      imageTypes.add(hsvFilter);
      imageTypes.add(grayscaleFilter);
      imageTypes.add(blackAndWhiteFilter);
      imageTypes.add(rotateZoomFilter);
      imageTypes.add(simpleMixer);
      imageTypes.add(addImages);
      imageTypes.add(stackImages);
      imageTypes.add(advancedMixer);
      imageTypes.add(lazer);

      True trueType = new True();
      False falseType = new False();
      And and = new And();
      Or or = new Or();
      Not not = new Not();
      Xor xor = new Xor();
      eu32k.vJoy.common.workset.atomic.bool.Random boolRandom = new eu32k.vJoy.common.workset.atomic.bool.Random();
      Threshold threshold = new Threshold();

      types.add(trueType);
      types.add(falseType);
      types.add(and);
      types.add(or);
      types.add(not);
      types.add(xor);
      types.add(boolRandom);
      types.add(threshold);

      booleanTypes.add(trueType);
      booleanTypes.add(falseType);
      booleanTypes.add(and);
      booleanTypes.add(or);
      booleanTypes.add(not);
      booleanTypes.add(xor);
      booleanTypes.add(boolRandom);
      booleanTypes.add(threshold);
   }

   private Instance addInstance(Type type, float x, float y) {
      Instance instance = type.instanciate(x, y);
      getInstances().add(instance);
      return instance;
   }
}
