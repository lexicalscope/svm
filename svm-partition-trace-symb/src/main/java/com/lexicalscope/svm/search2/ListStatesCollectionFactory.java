package com.lexicalscope.svm.search2;

import com.lexicalscope.svm.search.Randomiser;

public class ListStatesCollectionFactory implements StatesCollectionFactory {
   private final Randomiser randomiser;

   public ListStatesCollectionFactory(final Randomiser randomiser) {
      this.randomiser = randomiser;
   }

   @Override public StatesCollection statesCollection(final TraceTreeSideObserver listener) {
      return new ListStatesCollection(randomiser, listener);
   }
}
