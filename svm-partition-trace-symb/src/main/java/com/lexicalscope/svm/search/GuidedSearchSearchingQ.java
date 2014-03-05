package com.lexicalscope.svm.search;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreeCorrespondence;
import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreePair;

class GuidedSearchSearchingQ<T1, S1> implements GuidedSearchState<T1, S1> {
private final GuidedSearchSearchingP<T1, S1> searchingP;
private final Randomiser randomiser;

public GuidedSearchSearchingQ(final GuidedSearchSearchingP<T1, S1> searchingP, final Randomiser randomiser) {
   this.searchingP = searchingP;
   this.randomiser = randomiser;
}

@Override public void searchedSide(
      final GoalTreeCorrespondence<T1, S1> correspondence,
      final GoalTreePair<T1, S1> pair) {
   if(pair.isOpen()) {
      correspondence.stillOpen(pair);
   }
}

@Override public boolean searchMore(final GoalTreeCorrespondence<T1, S1> correspondence) {
   return correspondence.hasOpenChildren();
}

@Override public GuidedSearchState<T1, S1> nextState() {
   return searchingP;
}

@Override public boolean isOpen(final GoalTreePair<T1, S1> correspondenceUnderConsideration) {
   return correspondenceUnderConsideration.qsideIsOpen();
}

@Override public S1 pickSearchNode(final GoalTreePair<T1, S1> correspondenceUnderConsideration) {
   return correspondenceUnderConsideration.openQNode(randomiser);
}

@Override public GoalTreePair<T1, S1> pickCorrespondence(
      final GoalTreeCorrespondence<T1, S1> correspondence,
      final GoalTreePair<T1, S1> correspondenceUnderConsideration) {
   return correspondenceUnderConsideration;
}

@Override public void fork(
      final GoalTreePair<T1, S1> correspondenceUnderConsideration, final S1[] states) {
   correspondenceUnderConsideration.expandQ(states);
}

@Override public void goal(
      final GoalTreeCorrespondence<T1, S1> correspondence,
      final GoalTreePair<T1, S1> parent,
      final T1 goal,
      final S1 state,
      final BoolSymbol pc) {
   correspondence.reachedQ(parent, goal, state, pc);
}}