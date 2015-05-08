package com.lexicalscope.svm.partition.trace.symb.tree;

import java.util.Collection;

import org.hamcrest.Matcher;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.search.Randomiser;
import com.lexicalscope.svm.vm.j.JState;

public interface GoalTreeCorrespondence<T, S> extends Iterable<GoalTreePair2<T>>  {
   boolean hasChildren();

   boolean isOpen();

   /**
    * potentially expensive
    */
   GoalTreePair2<T> correspondence(T rootGoal);

   GoalTreePair2<T> reachedP(GoalTreePair2<T> parent, T goal, JState state, BoolSymbol childPc);

   GoalTreePair2<T> reachedQ(GoalTreePair2<T> parent, T goal, JState state, BoolSymbol childPc);

   boolean hasChild(Matcher<? super GoalTreePair2<T>> childMatcher);

   int childCount();

   /**
    * @param pstate0 an initial state in p
    */
   void pInitial(JState pstate0);

   /**
    * @param qstate0 an initial state in q
    */
   void qInitial(JState qstate0);

   Collection<GoalTreePair2<T>> children();

   void stillOpen(GoalTreePair2<T> node);
   boolean hasOpenChildren();
   GoalTreePair2<T> randomOpenChild(Randomiser randomiser);
}