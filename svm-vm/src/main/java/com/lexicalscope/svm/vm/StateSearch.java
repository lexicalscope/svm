package com.lexicalscope.svm.vm;

import java.util.Collection;

public interface StateSearch<S> {
   S pendingState();

   void reachedLeaf();
   void fork(S[] states);
   void goal();

   S firstResult();
   Collection<S> results();

   void consider(S state);
}