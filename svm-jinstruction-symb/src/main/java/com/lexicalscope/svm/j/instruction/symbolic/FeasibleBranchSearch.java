package com.lexicalscope.svm.j.instruction.symbolic;

import static com.lexicalscope.svm.j.instruction.symbolic.PcMetaKey.PC;

import java.util.Arrays;
import java.util.Collection;

import com.lexicalscope.svm.vm.StateSearch;
import com.lexicalscope.svm.vm.j.JState;
import com.lexicalscope.svm.z3.FeasibilityChecker;

public class FeasibleBranchSearch implements StateSearch<JState> {
   private final FeasibilityChecker feasibilityChecker;
   private final StateSearch<JState> search;

   public FeasibleBranchSearch(
         final StateSearch<JState> search,
         final FeasibilityChecker feasibilityChecker) {
      this.search = search;
      this.feasibilityChecker = feasibilityChecker;
   }

   @Override
   public void fork(final JState[] states) {
      final JState[] result = Arrays.copyOf(states, states.length);
      int resultCount = 0;
      for (final JState state : states) {
         if(feasibilityChecker.satisfiable(state.getMeta(PC))) {
            result[resultCount] = state;
            resultCount++;
         }
      }
      search.fork(Arrays.copyOf(result, resultCount));
   }

   @Override
   public JState pendingState() {
      return search.pendingState();
   }

   @Override
   public void reachedLeaf() {
      search.reachedLeaf();
   }

   @Override
   public JState firstResult() {
      return search.firstResult();
   }

   @Override
   public Collection<JState> results() {
      return search.results();
   }

   @Override public void goal() {
      // nothing
   }

   @Override public void consider(final JState state) {
      search.consider(state);
   }
}
