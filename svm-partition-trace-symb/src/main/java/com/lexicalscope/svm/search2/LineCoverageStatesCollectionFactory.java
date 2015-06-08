package com.lexicalscope.svm.search2;


public class LineCoverageStatesCollectionFactory implements StatesCollectionFactory {
   @Override public StatesCollection statesCollection(final TraceTreeSideObserver listener) {
      return new LineCoverageStatesCollection(listener);
   }
}
