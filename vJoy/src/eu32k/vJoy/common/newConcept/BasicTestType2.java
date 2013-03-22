package eu32k.vJoy.common.newConcept;

public class BasicTestType2 extends BasicType {

   public BasicTestType2() {
      super("Image gen", Type.IMAGE);
      getPorts().add(new Port("Number In 1", Type.NUMBER));
      getPorts().add(new Port("Number In 2", Type.NUMBER));
   }

}
