package com.lexicalscope.svm.search;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreeCorrespondence;
import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreePair;
import com.lexicalscope.svm.vm.j.JState;

final class GuidedSearchInitialState<T1, S1> implements GuidedSearchState<T1, S1> {
   private final Randomiser randomiser;

   public GuidedSearchInitialState(final Randomiser randomiser) {
      this.randomiser = randomiser;
   }

   @Override public void searchedSide(
         final GoalTreeCorrespondence<T1, JState> correspondence,
         final GoalTreePair<T1, JState> pair) {
      // nothing
   }

   @Override public boolean searchMore(final GoalTreeCorrespondence<T1, JState> correspondence) {
      return true;
   }

   @Override public GuidedSearchState<T1, JState> nextSide() {
      return new GuidedSearchSearchingP<T1, JState>(randomiser);
   }

   @Override public boolean isOpen(final GoalTreePair<T1, JState> correspondence) {
      throw new UnsupportedOperationException();
   }

   @Override public JState pickSearchNode(final GoalTreePair<T1, JState> correspondenceUnderConsideration) {
      throw new UnsupportedOperationException();
   }

   @Override public GoalTreePair<T1, JState> pickCorrespondence(final GoalTreeCorrespondence<T1, JState> correspondence, final GoalTreePair<T1, JState> correspondenceUnderConsideration) {
      throw new UnsupportedOperationException();
   }

   @Override public void fork(final GoalTreePair<T1, JState> correspondenceUnderConsideration, final JState[] states) {
      throw new UnsupportedOperationException();
   }

   @Override public void goal(final GoalTreeCorrespondence<T1, JState> correspondence, final GoalTreePair<T1, JState> correspondenceUnderConsideration, final T1 goal, final JState pending, final BoolSymbol pc) {
      throw new UnsupportedOperationException();
   }
}