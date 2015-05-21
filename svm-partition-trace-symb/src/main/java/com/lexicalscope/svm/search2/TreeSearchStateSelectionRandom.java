package com.lexicalscope.svm.search2;

import java.util.List;

import com.lexicalscope.svm.search.Randomiser;
import com.lexicalscope.svm.vm.j.JState;

public class TreeSearchStateSelectionRandom implements TreeSearchStateSelection {
   private final Randomiser randomiser;

   public TreeSearchStateSelectionRandom(final Randomiser randomiser) {
      this.randomiser = randomiser;
   }

   @Override public TraceTree qnode(final List<TraceTree> qstatesAvailable) {
      return pickNode(qstatesAvailable);
   }

   @Override public JState qstate(final TraceTree selectedTree, final List<JState> states) {
      return selectedTree.removeQState(pickState(states));
   }

   @Override public TraceTree pnode(final List<TraceTree> pstatesAvailable) {
      return pickNode(pstatesAvailable);
   }

   @Override public JState pstate(final TraceTree selectedTree, final List<JState> states) {
      return selectedTree.removePState(pickState(states));
   }

   private TraceTree pickNode(final List<TraceTree> statesAvailable) {
      final int node = randomiser.random(statesAvailable.size());
      return statesAvailable.get(node);
   }

   private int pickState(final List<JState> states) {
      return randomiser.random(states.size());
   }
}
