package com.lexicalscope.svm.partition.trace.symb.tree;

import com.lexicalscope.svm.search.Randomiser;
import com.lexicalscope.svm.vm.j.JState;

public interface GoalTreePair<T, S> extends InputSubset{
   JState openPNode(Randomiser randomiser);
   JState openQNode(Randomiser randomiser);

   boolean isOpen();
   boolean psideIsOpen();
   boolean qsideIsOpen();

   GoalTree<T, JState> pside();
   GoalTree<T, JState> qside();

   void expandP(JState[] states);
   void expandQ(JState[] states);
}
