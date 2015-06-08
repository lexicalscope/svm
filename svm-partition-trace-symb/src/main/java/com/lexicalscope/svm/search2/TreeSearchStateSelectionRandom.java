package com.lexicalscope.svm.search2;

import java.util.List;

import com.lexicalscope.svm.search.Randomiser;

public class TreeSearchStateSelectionRandom implements TreeSearchStateSelection {
   private final Randomiser randomiser;
   private final StatesCollectionFactory statesCollectionFactory;

   public TreeSearchStateSelectionRandom(
         final Randomiser randomiser,
         final StatesCollectionFactory statesCollectionFactory) {
      this.randomiser = randomiser;
      this.statesCollectionFactory = statesCollectionFactory;
   }

   public TreeSearchStateSelectionRandom(final Randomiser randomiser) {
      this(randomiser, new ListStatesCollectionFactory());
   }

   @Override public TraceTree node(final List<TraceTree> statesAvailable) {
      final int node1 = randomiser.random(statesAvailable.size());
      return statesAvailable.get(node1);
   }

   @Override public StatesCollection statesCollection(final TraceTreeSideObserver listener) {
      return statesCollectionFactory.statesCollection(randomiser, listener);
   }
}
