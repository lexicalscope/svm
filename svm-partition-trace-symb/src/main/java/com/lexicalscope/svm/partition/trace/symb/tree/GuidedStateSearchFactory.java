package com.lexicalscope.svm.partition.trace.symb.tree;

import static com.lexicalscope.svm.partition.trace.TraceBuilder.trace;
import static com.lexicalscope.svm.partition.trace.symb.tree.GoalTreeCorrespondenceImpl.root;

import com.lexicalscope.svm.j.instruction.symbolic.FeasibleBranchSearch;
import com.lexicalscope.svm.partition.trace.Trace;
import com.lexicalscope.svm.search.GoalTreeGuidedSearch;
import com.lexicalscope.svm.search.RandomSeedPseudoRandomiser;
import com.lexicalscope.svm.vm.StateSearch;
import com.lexicalscope.svm.vm.conc.StateSearchFactory;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.z3.FeasibilityChecker;

public class GuidedStateSearchFactory implements StateSearchFactory {
   private final FeasibilityChecker feasibilityChecker;

   public GuidedStateSearchFactory(final FeasibilityChecker feasibilityChecker) {
      this.feasibilityChecker = feasibilityChecker;
   }

   @Override public StateSearch<JState> search() {
      return new FeasibleBranchSearch(
               new GoalTreeGuidedSearch<Trace, JState>(
                     root(trace().build(), feasibilityChecker, new TraceGoalMapFactory(feasibilityChecker), JState.class),
                     new TraceMetaExtractor(),
                     new RandomSeedPseudoRandomiser()),
               feasibilityChecker);
   }
}
