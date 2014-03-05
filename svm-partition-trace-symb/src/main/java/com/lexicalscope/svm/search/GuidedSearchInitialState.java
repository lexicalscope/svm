package com.lexicalscope.svm.search;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreeCorrespondence;
import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreePair;

final class GuidedSearchInitialState<T1, S1> implements GuidedSearchState<T1, S1> {
private final Randomiser randomiser;

public GuidedSearchInitialState(final Randomiser randomiser) {
   this.randomiser = randomiser;
}

@Override public void searchedSide(
      final GoalTreeCorrespondence<T1, S1> correspondence,
      final GoalTreePair<T1, S1> pair) {
   // nothing
}

@Override public boolean searchMore(final GoalTreeCorrespondence<T1, S1> correspondence) {
   return true;
}

@Override public GuidedSearchState<T1, S1> nextState() {
   return new GuidedSearchSearchingP<T1, S1>(randomiser);
}

@Override public boolean isOpen(final GoalTreePair<T1, S1> correspondence) {
   throw new UnsupportedOperationException();
}

@Override public S1 pickSearchNode(final GoalTreePair<T1, S1> correspondenceUnderConsideration) {
   throw new UnsupportedOperationException();
}

@Override public GoalTreePair<T1, S1> pickCorrespondence(final GoalTreeCorrespondence<T1, S1> correspondence, final GoalTreePair<T1, S1> correspondenceUnderConsideration) {
   throw new UnsupportedOperationException();
}

@Override public void fork(final GoalTreePair<T1, S1> correspondenceUnderConsideration, final S1[] states) {
   throw new UnsupportedOperationException();
}

@Override public void goal(final GoalTreeCorrespondence<T1, S1> correspondence, final GoalTreePair<T1, S1> correspondenceUnderConsideration, final T1 goal, final S1 pending, final BoolSymbol pc) {
   throw new UnsupportedOperationException();
}}