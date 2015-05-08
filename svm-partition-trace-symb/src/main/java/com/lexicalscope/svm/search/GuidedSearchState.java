package com.lexicalscope.svm.search;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreeCorrespondence;
import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreePair2;
import com.lexicalscope.svm.vm.j.JState;

interface GuidedSearchState<T1, S1> {
   void searchedSide(
         GoalTreeCorrespondence<T1, JState> correspondence,
         GoalTreePair2<T1> correspondenceUnderConsideration);

   boolean searchMore(GoalTreeCorrespondence<T1, JState> correspondence);

   GuidedSearchState<T1, ?> nextSide();

   GoalTreePair2<T1> pickCorrespondence(
         GoalTreeCorrespondence<T1, JState> correspondence,
         GoalTreePair2<T1> correspondenceUnderConsideration);

   boolean isOpen(GoalTreePair2<T1> correspondenceUnderConsideration);

   JState pickSearchNode(GoalTreePair2<T1> correspondenceUnderConsideration);

   void fork(GoalTreePair2<T1> correspondenceUnderConsideration, JState[] states);

   void goal(
         GoalTreeCorrespondence<T1, JState> correspondence,
         GoalTreePair2<T1> correspondenceUnderConsideration,
         T1 goal,
         JState pending,
         BoolSymbol pc);
}