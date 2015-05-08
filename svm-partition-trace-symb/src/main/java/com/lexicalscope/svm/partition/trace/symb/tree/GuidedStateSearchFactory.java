package com.lexicalscope.svm.partition.trace.symb.tree;

import static com.lexicalscope.svm.partition.trace.TraceBuilder.trace;
import static com.lexicalscope.svm.partition.trace.symb.tree.GoalTreeCorrespondenceImpl.root;

import com.lexicalscope.svm.j.instruction.symbolic.FeasibleBranchSearch;
import com.lexicalscope.svm.search.GoalTreeGuidedSearch;
import com.lexicalscope.svm.search.GuidedSearchObserver;
import com.lexicalscope.svm.search.RandomSeedPseudoRandomiser;
import com.lexicalscope.svm.vm.StateSearch;
import com.lexicalscope.svm.vm.conc.StateSearchFactory;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.z3.FeasibilityChecker;

public class GuidedStateSearchFactory implements StateSearchFactory {
   private final FeasibilityChecker feasibilityChecker;
   private final GuidedSearchObserver observer;

   public GuidedStateSearchFactory(
         final GuidedSearchObserver observer,
         final FeasibilityChecker feasibilityChecker) {
      this.feasibilityChecker = feasibilityChecker;
      this.observer = observer;
   }

   @Override public StateSearch<JState> search() {
      return new FeasibleBranchSearch(
               new GoalTreeGuidedSearch(
                     observer,
                     root(trace().build(), feasibilityChecker, new TraceGoalMapFactory(feasibilityChecker)),
                     new TraceMetaExtractor(),
                     new RandomSeedPseudoRandomiser()),
               feasibilityChecker);
   }
}
