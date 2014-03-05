package com.lexicalscope.svm.search;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreeCorrespondence;
import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreePair;

interface GuidedSearchState<T1, S1> {
   void searchedSide(
         GoalTreeCorrespondence<T1, S1> correspondence,
         GoalTreePair<T1, S1> correspondenceUnderConsideration);

   boolean searchMore(GoalTreeCorrespondence<T1, S1> correspondence);

   GuidedSearchState<T1, S1> nextState();

   GoalTreePair<T1, S1> pickCorrespondence(
         GoalTreeCorrespondence<T1, S1> correspondence,
         GoalTreePair<T1, S1> correspondenceUnderConsideration);

   boolean isOpen(GoalTreePair<T1, S1> correspondenceUnderConsideration);

   S1 pickSearchNode(GoalTreePair<T1, S1> correspondenceUnderConsideration);

   void fork(GoalTreePair<T1, S1> correspondenceUnderConsideration, S1[] states);

   void goal(
         GoalTreeCorrespondence<T1, S1> correspondence,
         GoalTreePair<T1, S1> correspondenceUnderConsideration,
         T1 goal,
         S1 pending,
         BoolSymbol pc);
}