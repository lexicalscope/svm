package com.lexicalscope.svm.j.instruction.symbolic;

import static com.lexicalscope.svm.j.instruction.symbolic.PcMetaKey.PC;

import java.util.Arrays;
import java.util.Collection;

import com.lexicalscope.svm.vm.StateSearch;
import com.lexicalscope.svm.vm.j.State;
import com.lexicalscope.svm.z3.FeasibilityChecker;

public class FeasibleBranchSearch implements StateSearch<State> {
   private final FeasibilityChecker feasibilityChecker;
   private final StateSearch<State> search;

   public FeasibleBranchSearch(
         final StateSearch<State> search,
         final FeasibilityChecker feasibilityChecker) {
      this.search = search;
      this.feasibilityChecker = feasibilityChecker;
   }

   @Override
   public void fork(final State[] states) {
      final State[] result = Arrays.copyOf(states, states.length);
      int resultCount = 0;
      for (final State state : states) {
         if(feasibilityChecker.satisfiable(state.getMeta(PC))) {
            result[resultCount] = state;
            resultCount++;
         }
      }
      search.fork(Arrays.copyOf(result, resultCount));
   }

   @Override
   public State pendingState() {
      return search.pendingState();
   }

   @Override
   public void initial(final State state) {
      search.initial(state);
   }

   @Override
   public void reachedLeaf() {
      search.reachedLeaf();
   }

   @Override
   public State firstResult() {
      return search.firstResult();
   }

   @Override
   public Collection<State> results() {
      return search.results();
   }

   @Override public void goal() {
      // nothing
   }
}
