package com.lexicalscope.svm.search;

import java.util.ArrayList;
import java.util.List;


public class ConsumingSearchCluster<S> implements SearchCluster<S> {
   private final List<S> candidates = new ArrayList<>();
   private final Randomiser randomiser;

   public ConsumingSearchCluster(final Randomiser randomiser) {
      this.randomiser = randomiser;
   }

   public ConsumingSearchCluster() {
      this(new RandomSeedPseudoRandomiser());
   }

   @Override
   public boolean isEmpty() {
      return candidates.isEmpty();
   }

   @Override
   public void add(final S candidate) {
      candidates.add(candidate);
   }

   @Override
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
