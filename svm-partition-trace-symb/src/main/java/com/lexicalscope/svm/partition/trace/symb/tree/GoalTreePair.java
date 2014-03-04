package com.lexicalscope.svm.partition.trace.symb.tree;

import com.lexicalscope.svm.j.instruction.symbolic.symbols.BoolSymbol;
import com.lexicalscope.svm.search.Randomiser;

public interface GoalTreePair<T, S> extends InputSubset{
   S openPNode(Randomiser randomiser);
   S openQNode(Randomiser randomiser);
   boolean isOpen();

   GoalTree<T, S> pside();
   GoalTree<T, S> qside();

   void expandP(S[] states);
   void expandQ(S[] states);

   void reachedGoalP(T goal, S state, BoolSymbol pc);
   void reachedGoalQ(T goal, S state, BoolSymbol pc);
}
