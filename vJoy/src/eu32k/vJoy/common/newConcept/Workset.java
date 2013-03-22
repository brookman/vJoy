package eu32k.vJoy.common.newConcept;

import java.util.ArrayList;
import java.util.List;

public class Workset {
   private List<Patch> patches = new ArrayList<Patch>();
   private int mainPatchId = 0;

   public Workset() {
      Patch mainPatch = new Patch("main", Type.IMAGE);
      mainPatch.getPorts().add(new Port("Midi 1", Type.NUMBER));
      mainPatch.getPorts().add(new Port("Midi 2", Type.NUMBER));
      patches.add(mainPatch);
      mainPatchId = mainPatch.getId();
   }

   public List<Patch> getPatches() {
      return patches;
   }

   public int getMainPatchId() {
      return mainPatchId;
   }
}
