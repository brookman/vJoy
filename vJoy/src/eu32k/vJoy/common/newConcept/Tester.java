package eu32k.vJoy.common.newConcept;

public class Tester {

   public static void main(String[] args) {
      Workset workset = new Workset();
      Patch mainPatch = workset.getPatches().get(0);

      Patch testPatch = new Patch("Test", Type.IMAGE);
      testPatch.getPorts().add(new Port("Port 1", Type.NUMBER));
      testPatch.getPorts().add(new Port("Port 2", Type.NUMBER));

      BasicTestType n1 = new BasicTestType();
      BasicTestType n2 = new BasicTestType();
      BasicTestType2 i1 = new BasicTestType2();

      testPatch.getMovables().add(n1);
      testPatch.getMovables().add(n2);
      testPatch.getMovables().add(i1);
   }
}
