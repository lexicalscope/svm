package com.lexicalscope.svm.search;

import java.util.ArrayList;
import java.util.List;

public class PreservingSearchCluster<S> implements SearchCluster<S> {
   private final List<S> candidates = new ArrayList<>();
   private final Randomiser randomiser;

   public PreservingSearchCluster(final Randomiser randomiser) {
      this.randomiser = randomiser;
   }

   @Override public boolean isEmpty() {
      return candidates.isEmpty();
   }

   @Override public void add(final S candidate) {
      candidates.add(candidate);
   }

   @Override public S candidate() {
      return candidates.get(randomiser.random(candidates.size()));
   }
}
