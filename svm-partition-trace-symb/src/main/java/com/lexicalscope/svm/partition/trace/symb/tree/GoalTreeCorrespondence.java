package com.lexicalscope.svm.partition.trace.symb.tree;

import java.util.Collection;

import org.hamcrest.Matcher;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.search.Randomiser;

public interface GoalTreeCorrespondence<T, S> extends Iterable<GoalTreePair<T, S>>  {
   boolean hasChildren();

   boolean isOpen();

   /**
    * potentially expensive
    */
   GoalTreePair<T, S> correspondence(T rootGoal);

   GoalTreePair<T, S> reachedP(GoalTreePair<T, S> parent, T goal, S state, BoolSymbol childPc);

   GoalTreePair<T, S> reachedQ(GoalTreePair<T, S> parent, T goal, S state, BoolSymbol childPc);

   boolean hasChild(Matcher<? super GoalTreePair<T, S>> childMatcher);

   int childCount();

   /**
    * @param pstate0 an initial state in p
    */
   void pInitial(S pstate0);

   /**
    * @param qstate0 an initial state in q
    */
   void qInitial(S qstate0);

   Collection<GoalTreePair<T, S>> children();

   void stillOpen(GoalTreePair<T, S> node);
   boolean hasOpenChildren();
   GoalTreePair<T, S> randomOpenChild(Randomiser randomiser);
}