package com.lexicalscope.svm.j.instruction.symbolic;

import static com.lexicalscope.svm.j.instruction.symbolic.PcMetaKey.PC;

import java.util.Arrays;
import java.util.Collection;

import com.lexicalscope.svm.vm.FlowNode;
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
   public void fork(final FlowNode<State>[] states) {
      final FlowNode<State>[] result = Arrays.copyOf(states, states.length);
      int resultCount = 0;
      for (final FlowNode<State> state : states) {
         if(feasibilityChecker.satisfiable(state.state().getMeta(PC))) {
            result[resultCount] = state;
            resultCount++;
         }
      }
      search.fork(Arrays.copyOf(result, resultCount));
   }

   @Override
   public boolean searching() {
      return search.searching();
   }

   @Override
   public FlowNode<State> pendingState() {
      return search.pendingState();
   }

   @Override
   public void initial(final FlowNode<State> state) {
      search.initial(state);
   }

   @Override
   public void reachedLeaf() {
      search.reachedLeaf();
   }

   @Override
   public FlowNode<State> firstResult() {
      return search.firstResult();
   }

   @Override
   public Collection<FlowNode<State>> results() {
      return search.results();
   }

   @Override public void goal() {
      // nothing
   }
}
