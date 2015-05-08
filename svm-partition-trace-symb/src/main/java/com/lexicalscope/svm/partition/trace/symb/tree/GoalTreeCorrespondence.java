package com.lexicalscope.svm.partition.trace.symb.tree;

import java.util.Collection;

import org.hamcrest.Matcher;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.search.Randomiser;
import com.lexicalscope.svm.vm.j.JState;

public interface GoalTreeCorrespondence<T, S> extends Iterable<GoalTreePair>  {
   boolean hasChildren();

   boolean isOpen();

   /**
    * potentially expensive
    */
   GoalTreePair correspondence(T rootGoal);

   GoalTreePair reachedP(GoalTreePair parent, T goal, JState state, BoolSymbol childPc);

   GoalTreePair reachedQ(GoalTreePair parent, T goal, JState state, BoolSymbol childPc);

   boolean hasChild(Matcher<? super GoalTreePair> childMatcher);

   int childCount();

   /**
    * @param pstate0 an initial state in p
    */
   void pInitial(JState pstate0);

   /**
    * @param qstate0 an initial state in q
    */
   void qInitial(JState qstate0);

   Collection<GoalTreePair> children();

   void stillOpen(GoalTreePair node);
   boolean hasOpenChildren();
   GoalTreePair randomOpenChild(Randomiser randomiser);
}