package com.lexicalscope.svm.vm;

import java.util.Collection;

public interface StateSearch<S> {
   S pendingState();

   void initial(S state);
   void reachedLeaf();
   void fork(S[] states);
   void goal();

   S firstResult();
   Collection<S> results();
}