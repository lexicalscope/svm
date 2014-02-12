package com.lexicalscope.svm.vm;

import java.util.Collection;

public interface StateSearch<S> {
   boolean searching();
   FlowNode<S> pendingState();

   void initial(FlowNode<S> state);
   void reachedLeaf();
   void fork(FlowNode<S>[] states);

   FlowNode<S> firstResult();
   Collection<FlowNode<S>> results();
}