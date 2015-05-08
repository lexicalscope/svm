package com.lexicalscope.svm.search;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreeCorrespondence;
import com.lexicalscope.svm.partition.trace.symb.tree.GoalTreePair;
import com.lexicalscope.svm.vm.j.JState;

interface GuidedSearchState<T1, S1> {
   void searchedSide(
         GoalTreeCorrespondence<T1, JState> correspondence,
         GoalTreePair correspondenceUnderConsideration);

   boolean searchMore(GoalTreeCorrespondence<T1, JState> correspondence);

   GuidedSearchState<T1, ?> nextSide();

   GoalTreePair pickCorrespondence(
         GoalTreeCorrespondence<T1, JState> correspondence,
         GoalTreePair correspondenceUnderConsideration);

   boolean isOpen(GoalTreePair correspondenceUnderConsideration);

   JState pickSearchNode(GoalTreePair correspondenceUnderConsideration);

   void fork(GoalTreePair correspondenceUnderConsideration, JState[] states);

   void goal(
         GoalTreeCorrespondence<T1, JState> correspondence,
         GoalTreePair correspondenceUnderConsideration,
         T1 goal,
         JState pending,
         BoolSymbol pc);
}