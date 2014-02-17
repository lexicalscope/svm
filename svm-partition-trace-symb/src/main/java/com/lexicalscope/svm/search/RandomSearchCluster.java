package com.lexicalscope.svm.search;

import java.util.ArrayList;
import java.util.List;


public class RandomSearchCluster<S> {
   private final List<S> candidates = new ArrayList<>();
   private final Randomiser randomiser;

   public RandomSearchCluster(final Randomiser randomiser) {
      this.randomiser = randomiser;
   }

   public RandomSearchCluster() {
      this(new RandomSeedPseudoRandomiser());
   }

   public boolean isEmpty() {
      return candidates.isEmpty();
   }

   public void add(final S candidate) {
      candidates.add(candidate);
   }

   public S candidate() {
      final int size = candidates.size();
      final int lastIndex = size - 1;
      final int selected = randomiser.random(size);
      final S last = candidates.remove(lastIndex);
      if(selected == lastIndex) {
         return last;
      }
      return candidates.set(selected, last);
   }
}
