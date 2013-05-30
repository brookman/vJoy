package eu32k.vJoy.curveEditor.curve;

import java.util.TreeSet;

public class ExtendedTreeSet<T extends Comparable<T>> extends TreeSet<T> {
   private static final long serialVersionUID = 5237549260078992621L;

   public void needsResorting(T element) {
      remove(element);
      add(element);
   }

   public T getOriginal(T element) {
      T next = ceiling(element);
      if (next == null) {
         return last();
      }
      return floor(next);
   }
}
