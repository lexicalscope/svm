package com.lexicalscope.svm.partition.trace.symb.tree;

import org.hamcrest.Matcher;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;

public interface GoalTreeCorrespondence<T, S>  {
   boolean hasChildren();

   boolean isOpen();

   /**
    * potentially expensive
    */
   GoalTreePair<T, S> correspondence(T rootGoal);

   void reachedP(GoalTreePair<T, S> parent, T goal, S state, BoolSymbol childPc);

   void reachedQ(GoalTreePair<T, S> parent, T goal, S state, BoolSymbol childPc);

   boolean hasChild(Matcher<? super GoalTreePair<T, S>> childMatcher);

   int childCount();
}