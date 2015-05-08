package com.lexicalscope.svm.partition.trace.symb.tree;

import java.util.Collection;

import org.hamcrest.Matcher;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.search.Randomiser;
import com.lexicalscope.svm.vm.j.JState;

public interface GoalTreeCorrespondence<T, S> extends Iterable<GoalTreePair<T, JState>>  {
   boolean hasChildren();

   boolean isOpen();

   /**
    * potentially expensive
    */
   GoalTreePair<T, JState> correspondence(T rootGoal);

   GoalTreePair<T, JState> reachedP(GoalTreePair<T, JState> parent, T goal, JState state, BoolSymbol childPc);

   GoalTreePair<T, JState> reachedQ(GoalTreePair<T, JState> parent, T goal, JState state, BoolSymbol childPc);

   boolean hasChild(Matcher<? super GoalTreePair<T, JState>> childMatcher);

   int childCount();

   /**
    * @param pstate0 an initial state in p
    */
   void pInitial(JState pstate0);

   /**
    * @param qstate0 an initial state in q
    */
   void qInitial(JState qstate0);

   Collection<GoalTreePair<T, JState>> children();

   void stillOpen(GoalTreePair<T, JState> node);
   boolean hasOpenChildren();
   GoalTreePair<T, JState> randomOpenChild(Randomiser randomiser);
}