package com.lexicalscope.svm.search;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreeCorrespondence;
import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreePair;

class GuidedSearchSearchingP<T1, S1> implements GuidedSearchState<T1, S1> {
private final GuidedSearchState<T1, S1> searchingQy;
private final Randomiser randomiser;

public GuidedSearchSearchingP(final Randomiser randomiser) {
   this.randomiser = randomiser;
   searchingQy = new GuidedSearchSearchingQ<T1, S1>(this, randomiser);
}

@Override public void searchedSide(
      final GoalTreeCorrespondence<T1, S1> correspondence,
      final GoalTreePair<T1, S1> pair) {
}

@Override public GoalTreePair<T1, S1> pickCorrespondence(
      final GoalTreeCorrespondence<T1, S1> correspondence,
      final GoalTreePair<T1, S1> correspondenceUnderConsideration) {
   return correspondence.randomOpenChild(randomiser);
}

@Override public boolean searchMore(final GoalTreeCorrespondence<T1, S1> correspondence) {
   return true;
}

@Override public GuidedSearchState<T1, S1> nextState() {
   return searchingQy;
}

@Override public boolean isOpen(final GoalTreePair<T1, S1> correspondenceUnderConsideration) {
   return correspondenceUnderConsideration.psideIsOpen();
}

@Override public S1 pickSearchNode(final GoalTreePair<T1, S1> correspondenceUnderConsideration) {
   return correspondenceUnderConsideration.openPNode(randomiser);
}

@Override public void fork(
      final GoalTreePair<T1, S1> correspondenceUnderConsideration, final S1[] states) {
   correspondenceUnderConsideration.expandP(states);
}

@Override public void goal(
      final GoalTreeCorrespondence<T1, S1> correspondence,
      final GoalTreePair<T1, S1> parent,
      final T1 goal,
      final S1 state,
      final BoolSymbol pc) {
   correspondence.reachedP(parent, goal, state, pc);
}}