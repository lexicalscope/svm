package com.lexicalscope.svm.vm;

import java.util.Collection;

public interface StateSearch<S> {
   FlowNode<S> pendingState();

   void initial(FlowNode<S> state);
   void reachedLeaf();
   void fork(FlowNode<S>[] states);
   void goal();

   FlowNode<S> firstResult();
   Collection<FlowNode<S>> results();
}