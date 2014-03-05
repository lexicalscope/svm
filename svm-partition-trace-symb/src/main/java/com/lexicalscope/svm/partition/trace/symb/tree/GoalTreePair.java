package com.lexicalscope.svm.partition.trace.symb.tree;

import com.lexicalscope.svm.search.Randomiser;

public interface GoalTreePair<T, S> extends InputSubset{
   S openPNode(Randomiser randomiser);
   S openQNode(Randomiser randomiser);

   boolean isOpen();
   boolean psideIsOpen();
   boolean qsideIsOpen();

   GoalTree<T, S> pside();
   GoalTree<T, S> qside();

   void expandP(S[] states);
   void expandQ(S[] states);
}
