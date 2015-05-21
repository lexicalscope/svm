package com.lexicalscope.svm.search2;

import com.lexicalscope.svm.search.Randomiser;
import com.lexicalscope.svm.vm.j.JState;

public interface StatesCollection extends Iterable<JState> {
   void add(JState state);

   boolean isEmpty();

   JState pickState(Randomiser randomiser);
}
